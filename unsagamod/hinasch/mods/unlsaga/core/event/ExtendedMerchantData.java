package hinasch.mods.unlsaga.core.event;

import hinasch.mods.unlsaga.Unsaga;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class ExtendedMerchantData implements IExtendedEntityProperties{

	protected ItemStack[] merchantInventory = new ItemStack[10];
	protected boolean initMerchandice = false;
	public long recentPurchaseDate; 
	public static String VILLAGER = "unsaga.villager";
	
	@SubscribeEvent
	public void attachDataEvent(EntityConstructing e){
	
		if(e.entity instanceof EntityVillager){
			EntityVillager ep = (EntityVillager)e.entity;
			ep.registerExtendedProperties(VILLAGER, new ExtendedMerchantData());
		}
	}
	
	public ItemStack getMerchantInventory(int par1){
		return this.merchantInventory[par1];
	}
	
	public void setMerchantInventory(int par1,ItemStack is){
		this.merchantInventory[par1] = is;
	}
	
	public boolean hasMerchandiceInitialized(){
		return this.initMerchandice;
	}
	
	public void markMerchandiceInitialized(boolean par1){
		this.initMerchandice = par1;
	}
	@Override
	public void saveNBTData(NBTTagCompound compound) {
		// TODO 自動生成されたメソッド・スタブ
		NBTTagList tagList = new NBTTagList();
		//Unsaga.debug("nbtへセーブ");
		for(int i=0;i<9;i++){
			if(this.merchantInventory[i]!=null){
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte)i);
                this.merchantInventory[i].writeToNBT(nbttagcompound1);
                tagList.appendTag(nbttagcompound1);
			}
		}
		
		compound.setTag("Bartering.Items", tagList);
		compound.setLong("Bartering.recentPurchase", this.recentPurchaseDate);
		
		compound.setBoolean("initialized", initMerchandice);
	}

	@Override
	public void loadNBTData(NBTTagCompound compound) {
		Unsaga.debug("nbtから読み込み");
        NBTTagList nbttaglist = compound.getTagList("Bartering.Items",10);
        this.merchantInventory = new ItemStack[10];
        for (int i = 0; i < nbttaglist.tagCount(); ++i)
        {
            NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist.getCompoundTagAt(i);
            int j = nbttagcompound1.getByte("Slot") & 255;

            if (j >= 0 && j < this.merchantInventory.length)
            {
                this.merchantInventory[j] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
            }
        }
		
        this.initMerchandice = compound.getBoolean("initialized");
        this.recentPurchaseDate = compound.getLong("Bartering.recentPurchase");
	}

	@Override
	public void init(Entity entity, World world) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

}

package hinasch.mods.unlsaga.core.event;

import hinasch.mods.unlsaga.Unsaga;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

import com.google.common.base.Optional;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class ExtendedPlayerData implements IExtendedEntityProperties{

	protected ItemStack[] acsInventory;

	protected ItemStack tabletInventory;

	//TODO:タブレットインベントリ
	protected EntityVillager merchant;
	public final static String key = "unsaga.equipment";
	
	protected int getInventoryLength(){
		return 3;
	}
	
	public ItemStack getAccessory(int par1){
		if(par1==2){
			return this.tabletInventory;
		}
		return this.acsInventory[par1];
	}
	
	public ItemStack[] getAccessories(){
		return acsInventory;
	}
	
	
	public ItemStack getTablet(){
		return this.tabletInventory;
	}
	public Optional<EntityVillager> getMerchant(){
		if(this.merchant!=null){
			return Optional.of(this.merchant);
		}
		return Optional.absent();
	}
	
	public void setMerchant(EntityVillager villager){
		this.merchant = villager;
	}
	
	public void setAccessory(int par1,ItemStack par2){
		if(par1==2){
			this.tabletInventory = par2;
			return;
		}
		this.acsInventory[par1] = par2;
	}
	public void setAccessories(ItemStack i1,ItemStack i2){
		this.acsInventory[0] = i1;
		this.acsInventory[1] = i2;
	}
	
	public void setTablet(ItemStack tablet){
		this.tabletInventory = tablet;
	}
	@SubscribeEvent
	public void attachDataEvent(EntityConstructing e){
	
		if(e.entity instanceof EntityPlayer){
			EntityPlayer ep = (EntityPlayer)e.entity;
			if(ep.getExtendedProperties(key)==null){
				ep.registerExtendedProperties(key, new ExtendedPlayerData());
			}
			
		}
	}
	
	@Override
	public void saveNBTData(NBTTagCompound compound) {
		
		NBTTagList tagList = new NBTTagList();
		Unsaga.debug("nbtへセーブ");
		for(int i=0;i<3;i++){
			if(this.getAccessory(i)!=null){
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte)i);
                this.getAccessory(i).writeToNBT(nbttagcompound1);
                tagList.appendTag(nbttagcompound1);
			}
		}
		
		compound.setTag("Items", tagList);
	}

	@Override
	public void loadNBTData(NBTTagCompound compound) {
		// TODO 自動生成されたメソッド・スタブ
		Unsaga.debug("nbtから読み込み");
        NBTTagList nbttaglist = compound.getTagList("Items",10);
        this.init(null, null);
        for (int i = 0; i < nbttaglist.tagCount(); ++i)
        {
            NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist.getCompoundTagAt(i);
            int j = nbttagcompound1.getByte("Slot") & 255;

            if (j >= 0 && j < this.getInventoryLength())
            {
            	this.setAccessory(j, ItemStack.loadItemStackFromNBT(nbttagcompound1));
                //this.acsInventory[j] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
            }
        }
	}

	@Override
	public void init(Entity entity, World world) {
		// TODO 自動生成されたメソッド・スタブ
		this.acsInventory = new ItemStack[2];
		this.tabletInventory = null;
	}
	
	public static Optional<ExtendedPlayerData> getData(EntityPlayer ep){
		ExtendedPlayerData data = (ExtendedPlayerData)ep.getExtendedProperties(key);
		if(data!=null){
			return Optional.of(data);
		}
		return Optional.absent();
	}
	
	public static void dropAccessoriesOnDeath(LivingDeathEvent e) {
		if(!(e.entityLiving instanceof EntityPlayer))return;
		EntityPlayer ep = (EntityPlayer)e.entityLiving;
		if(ExtendedPlayerData.getData(ep).isPresent()){
			ExtendedPlayerData data = ExtendedPlayerData.getData(ep).get();
			for(ItemStack is:data.getAccessories()){
				if(is!=null){
					ep.entityDropItem(is, 0.1F);
					
				}
			}
			if(data.getTablet()!=null){
				ep.entityDropItem(data.getTablet(), 0.1F);
			}
			
		}
		
	}


}

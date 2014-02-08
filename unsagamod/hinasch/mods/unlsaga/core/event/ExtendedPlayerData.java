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
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;

import com.google.common.base.Optional;

public class ExtendedPlayerData implements IExtendedEntityProperties{

	protected ItemStack[] acsInventory = new ItemStack[2];
	protected EntityVillager merchant;
	public final static String key = "unsaga.equipment";
	
	public ItemStack getItemStack(int par1){
		return this.acsInventory[par1];
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
	
	public void setItemStack(ItemStack i1,ItemStack i2){
		this.acsInventory[0] = i1;
		this.acsInventory[1] = i2;
	}
	
	@ForgeSubscribe
	public void attachDataEvent(EntityConstructing e){
	
		if(e.entity instanceof EntityPlayer){
			EntityPlayer ep = (EntityPlayer)e.entity;
			ep.registerExtendedProperties(key, new ExtendedPlayerData());
		}
	}
	
	@Override
	public void saveNBTData(NBTTagCompound compound) {
		
		NBTTagList tagList = new NBTTagList();
		Unsaga.debug("nbtへセーブ");
		for(int i=0;i<2;i++){
			if(this.acsInventory[i]!=null){
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte)i);
                this.acsInventory[i].writeToNBT(nbttagcompound1);
                tagList.appendTag(nbttagcompound1);
			}
		}
		
		compound.setTag("Items", tagList);
	}

	@Override
	public void loadNBTData(NBTTagCompound compound) {
		// TODO 自動生成されたメソッド・スタブ
		Unsaga.debug("nbtから読み込み");
        NBTTagList nbttaglist = compound.getTagList("Items");
        this.acsInventory = new ItemStack[2];
        for (int i = 0; i < nbttaglist.tagCount(); ++i)
        {
            NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist.tagAt(i);
            int j = nbttagcompound1.getByte("Slot") & 255;

            if (j >= 0 && j < this.acsInventory.length)
            {
                this.acsInventory[j] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
            }
        }
	}

	@Override
	public void init(Entity entity, World world) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

}

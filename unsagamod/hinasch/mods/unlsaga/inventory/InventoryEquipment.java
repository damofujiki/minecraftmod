package hinasch.mods.unlsaga.inventory;

import hinasch.mods.unlsaga.Unsaga;
import hinasch.mods.unlsaga.core.event.ExtendedPlayerData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class InventoryEquipment implements IInventory{

	protected ItemStack[] theInventory = new ItemStack[3];
	protected EntityPlayer thePlayer;
	
	
	public InventoryEquipment(EntityPlayer ep){
		this.thePlayer = ep;
	}
	
	@Override
	public int getSizeInventory() {
		// TODO 自動生成されたメソッド・スタブ
		return this.theInventory.length;
	}

	@Override
	public ItemStack getStackInSlot(int i) {

		return this.theInventory[i];
	}

	@Override
	public ItemStack decrStackSize(int i, int j) {
		// TODO 自動生成されたメソッド・スタブ
		if(this.theInventory[i]!=null){
			ItemStack is = this.theInventory[i];
			this.theInventory[i] = null;
			return is;
		}
		return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		// TODO 自動生成されたメソッド・スタブ
		if(this.theInventory[i]!=null){
			ItemStack is = this.theInventory[i];
			this.theInventory[i] = null;
			return is;
		}
		return null;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {

		this.theInventory[i] = itemstack;
		
        if (itemstack != null && itemstack.stackSize > this.getInventoryStackLimit())
        {
            itemstack.stackSize = this.getInventoryStackLimit();
        }
	}





	@Override
	public int getInventoryStackLimit() {
		// TODO 自動生成されたメソッド・スタブ
		return 1;
	}



	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		// TODO 自動生成されたメソッド・スタブ
		
		return entityplayer.openContainer != entityplayer.inventoryContainer;
	}



	@Override
	public void closeInventory() {
		// TODO 自動生成されたメソッド・スタブ
		Unsaga.debug("データ書き込みました");
		if(this.thePlayer.getExtendedProperties("unsaga.equipment")==null){
			ExtendedPlayerData newdata = new ExtendedPlayerData();
			newdata.setAccessories(this.getStackInSlot(0), this.getStackInSlot(1));
			newdata.setTablet(this.getStackInSlot(2));
			this.thePlayer.registerExtendedProperties("unsaga.equipment", newdata);
		}else{
			ExtendedPlayerData data = (ExtendedPlayerData) this.thePlayer.getExtendedProperties("unsaga.equipment");
			data.setAccessories(this.getStackInSlot(0),this.getStackInSlot(1));
			data.setTablet(this.getStackInSlot(2));
		}

	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}

	@Override
	public String getInventoryName() {
		// TODO 自動生成されたメソッド・スタブ
		return "unsaga.inventory.equipment";
	}

	@Override
	public boolean hasCustomInventoryName() {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}

	@Override
	public void markDirty() {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	public void openInventory() {
		// TODO 自動生成されたメソッド・スタブ
		
	}


}

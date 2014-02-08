package hinasch.mods.unlsaga.inventory;

import net.minecraft.entity.IMerchant;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class InventorySmithUnsaga implements IInventory{

	protected ItemStack[] invSlot = new ItemStack[4];
	protected IMerchant theSmith;
	public InventorySmithUnsaga(EntityPlayer ep, IMerchant theMerchant) {
		// TODO 自動生成されたコンストラクター・スタブ
		this.theSmith = theMerchant;
		
	}

	@Override
	public int getSizeInventory() {
		// TODO 自動生成されたメソッド・スタブ
		return this.invSlot.length;
	}

	public ItemStack getPayment(){
		return this.getStackInSlot(0);
	}
	
	public ItemStack getBaseItem(){
		return this.getStackInSlot(1);
	}
	
	public ItemStack getMaterialItemStack(){
		return this.getStackInSlot(2);
	}
	
	public ItemStack getForgedItemStack(){
		return this.getStackInSlot(3);
	}
	@Override
	public ItemStack getStackInSlot(int i) {
		// TODO 自動生成されたメソッド・スタブ
		return this.invSlot[i];
	}

	@Override
	public ItemStack decrStackSize(int i, int j) {
		// TODO 自動生成されたメソッド・スタブ
		if(this.invSlot[i]!=null){
			ItemStack is = this.invSlot[i];
			this.invSlot[i] = null;
			return is;
		}
		return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		// TODO 自動生成されたメソッド・スタブ
		if(this.invSlot[i]!=null){
			ItemStack is = this.invSlot[i];
			this.invSlot[i] = null;
			return is;
		}
		return null;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		this.invSlot[i] = itemstack;
		
        if (itemstack != null && itemstack.stackSize > this.getInventoryStackLimit())
        {
            itemstack.stackSize = this.getInventoryStackLimit();
        }
		
	}

	@Override
	public String getInvName() {
		// TODO 自動生成されたメソッド・スタブ
		return "unsaga.smith.inventory";
	}

	@Override
	public boolean isInvNameLocalized() {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		// TODO 自動生成されたメソッド・スタブ
		return 1;
	}

	@Override
	public void onInventoryChanged() {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		// TODO 自動生成されたメソッド・スタブ
		return this.theSmith.getCustomer() == entityplayer;
	}

	@Override
	public void openChest() {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	public void closeChest() {

		
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}

}

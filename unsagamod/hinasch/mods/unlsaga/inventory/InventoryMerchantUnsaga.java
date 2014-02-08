package hinasch.mods.unlsaga.inventory;



import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class InventoryMerchantUnsaga implements IInventory{

	protected ItemStack[] bartering = new ItemStack[10];
	protected ItemStack[] merchandise = new ItemStack[10];
	protected Container eventHandler ;
	protected EntityPlayer theCustomer;
	
	public InventoryMerchantUnsaga(Container par1){
		this.eventHandler = par1;
		
	}
	@Override
	public int getSizeInventory() {
		// TODO 自動生成されたメソッド・スタブ
		return this.bartering.length + this.merchandise.length;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		// TODO 自動生成されたメソッド・スタブ
		if(i>9){
			return this.merchandise[i];
		}
		if(i<=9){
			return this.bartering[i];
		}
		return null;
	}

	public ItemStack getMerchandise(int par1){
		return this.getStackInSlot(10+par1);					
	}
	public ItemStack getBartering(int par1){
		return this.getStackInSlot(par1);
	}
	@Override
	public ItemStack decrStackSize(int i, int request) {
		// TODO 自動生成されたメソッド・スタブ
		if(i<=9){
			if(this.bartering[i]!=null){
				int stack = request;
				ItemStack is;
				if(this.bartering[i].stackSize<=request){
					is = this.bartering[i];
					this.bartering[i] = null;
					
					return is;
				}else{
					is = this.bartering[i].splitStack(request);
					if(this.bartering[i].stackSize == 0){
						this.bartering[i] = null;
					}
					
					return is;
				}
				
			}
		}
		if(i>9){
			if(this.merchandise[i]!=null){
				if(this.canBuy()){
					ItemStack bought;
					bought = this.merchandise[i];
					this.merchandise[i] = null;
					//ここに購入処理
					return bought;
				}
			}
		}
		return null;
	}

	public boolean canBuy(){
		return false;
		
	}
	
	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		// TODO 自動生成されたメソッド・スタブ

		
	}

	@Override
	public String getInvName() {
		// TODO 自動生成されたメソッド・スタブ
		return "unsaga.merchant.inventory";
	}

	@Override
	public boolean isInvNameLocalized() {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		// TODO 自動生成されたメソッド・スタブ
		return 64;
	}

	@Override
	public void onInventoryChanged() {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		// TODO 自動生成されたメソッド・スタブ
		return this.theCustomer == entityplayer;
	}

	@Override
	public void openChest() {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	public void closeChest() {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}

}

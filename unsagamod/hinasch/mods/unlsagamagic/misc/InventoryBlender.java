package hinasch.mods.unlsagamagic.misc;

import hinasch.mods.unlsaga.misc.util.UtilItem;
import hinasch.mods.unlsagamagic.item.ItemBlender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class InventoryBlender implements IInventory{

	protected ItemStack[] invBlender = new ItemStack[9];
	
	@Override
	public int getSizeInventory() {
		// TODO 自動生成されたメソッド・スタブ
		return this.invBlender.length;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		// TODO 自動生成されたメソッド・スタブ
		return this.invBlender[i];
	}

	@Override
	public ItemStack decrStackSize(int i, int j) {
		if(this.invBlender[i]!=null){
			ItemStack is = this.invBlender[i];
			this.invBlender[i] = null;
			return is;
		}
		return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		if(this.invBlender[i]!=null){
			ItemStack is = this.invBlender[i];
			this.invBlender[i] = null;
			return is;
		}
		return null;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		this.invBlender[i] = itemstack;
		
        if (itemstack != null && itemstack.stackSize > this.getInventoryStackLimit())
        {
            itemstack.stackSize = this.getInventoryStackLimit();
        }
	}

	@Override
	public String getInvName() {
		// TODO 自動生成されたメソッド・スタブ
		return "inventory.blender";
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
		return UtilItem.hasItemInstance(entityplayer, ItemBlender.class);
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

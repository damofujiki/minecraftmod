package hinasch.mods.unlsaga.inventory;

import hinasch.mods.unlsaga.core.event.ExtendedPlayerData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerEquipment extends Container{

	protected EntityPlayer player;
	protected InventoryEquipment invEquipment;
	
	public ContainerEquipment(InventoryPlayer par1InventoryPlayer, EntityPlayer ep)
	{
		this.player = ep;
		this.invEquipment = new InventoryEquipment(ep);
		if(ep.getExtendedProperties("unsaga.equipment")!=null){
			ExtendedPlayerData data = (ExtendedPlayerData) ep.getExtendedProperties("unsaga.equipment");
			
			this.invEquipment.setInventorySlotContents(0, data.getItemStack(0));
			this.invEquipment.setInventorySlotContents(1, data.getItemStack(1));
		}
		this.addSlotToContainer(new Slot(this.invEquipment, 0, 28, 53-(18*2))); //Base Material
		this.addSlotToContainer(new Slot(this.invEquipment, 1, 28+(18*2)-8, 53-(18*2))); //Material2
		//this.addSlotToContainer(new SlotMerchantResult(par1InventoryPlayer.player, par2IMerchant, this.merchantInventory, 2, 120, 53));
		int i;

		for (i = 0; i < 3; ++i)
		{
			for (int j = 0; j < 9; ++j)
			{
				this.addSlotToContainer(new Slot(par1InventoryPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		for (i = 0; i < 9; ++i)
		{
			this.addSlotToContainer(new Slot(par1InventoryPlayer, i, 8 + i * 18, 142));
		}
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		// TODO 自動生成されたメソッド・スタブ
		return this.player.openContainer != player.inventoryContainer;
	}
	
	@Override
    public void onContainerClosed(EntityPlayer par1EntityPlayer)
    {
		this.invEquipment.closeChest();
		return;
    }
	
	@Override
    public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
    {
        return null;
    }
	
	

}

package hinasch.mods.unlsaga.inventory;

import hinasch.mods.unlsaga.Unsaga;
import hinasch.mods.unlsaga.misc.bartering.MerchandiseInfo;
import hinasch.mods.unlsaga.network.PacketHandler;

import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.entity.IMerchant;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.google.common.io.ByteArrayDataInput;

import cpw.mods.fml.common.network.PacketDispatcher;

public class ContainerBartering extends Container{

	protected World worldobj;
	protected EntityPlayer theCustomer;
	protected IMerchant theMerchant;
	protected SlotMerchandise[] merchandiseSlot;

	protected InventoryMerchantUnsaga invMerchant;

	protected byte id;

	public ContainerBartering(World world,EntityPlayer ep,IMerchant merchant){

		this.worldobj = world;
		this.theCustomer = ep;
		this.theMerchant = merchant;
		this.invMerchant = new InventoryMerchantUnsaga(ep,merchant);
		this.merchandiseSlot = new SlotMerchandise[9];


		for (int i = 0; i < 9; ++i)
		{
			//this.merchandiseSlot[i] = new SlotMerchandise(this.invMerchant, i+10, 8 + i * 18, 63-(18*3));
			this.addSlotToContainer(new SlotMerchandise(this.invMerchant, i+10, 8 + i * 18, 63-(18*3)));
		}

		for (int i = 0; i < 9; ++i)
		{
			this.addSlotToContainer(new Slot(this.invMerchant, i, 8 + i * 18, 62));
		}

		for (int i = 0; i < 3; ++i)
		{
			for (int j = 0; j < 9; ++j)
			{
				this.addSlotToContainer(new Slot(ep.inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		for (int i = 0; i < 9; ++i)
		{
			this.addSlotToContainer(new Slot(ep.inventory, i, 8 + i * 18, 142));
		}


	}

	//	@Override
	//    public void onCraftMatrixChanged(IInventory par1IInventory)
	//    {
	//    	super.onCraftMatrixChanged(par1IInventory);
	//    	Unsaga.debug("呼ばれてるoncraftmatrix");
	//    }
	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		// TODO 自動生成されたメソッド・スタブ
		return this.theCustomer == entityplayer;
	}

	public int getSellPrice(){
		return this.invMerchant.getCurrentPriceToSell();
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
	{

		return null;

	}

	@Override
	public void onContainerClosed(EntityPlayer par1EntityPlayer)
	{
		super.onContainerClosed(par1EntityPlayer);
		this.invMerchant.closeChest();
		this.theMerchant.setCustomer((EntityPlayer)null);
		super.onContainerClosed(par1EntityPlayer);
		if(!par1EntityPlayer.worldObj.isRemote){
			for(int i=0;i<9;i++){
				ItemStack is = this.invMerchant.getStackInSlotOnClosing(i);
				if(is!=null){
					par1EntityPlayer.dropPlayerItem(is);
				}
			}
		}
	}


	public void onButtonPushed(int id) {
		PacketDispatcher.sendPacketToServer(PacketHandler.getPacket(this,id));

	}

	public static void writePacketData(DataOutputStream dos, byte id) {
		try {
			dos.writeInt((int)PacketHandler.GUI_BARTERING_BUTTON);
			dos.writeByte((byte)id);
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

	}

	public void readPacketData(ByteArrayDataInput data) {
		try
		{
			// byte型の読み込み
			this.id = (byte)data.readByte();
			Unsaga.debug(this.id);

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public boolean canBuy(ItemStack is){
		if(!MerchandiseInfo.hasBuyPriceTag(is)){
			return false;
		}
		MerchandiseInfo info = new MerchandiseInfo(is);
		int priceBuy = info.getBuyPriceTag();
		int priceSell = this.getSellPrice();
		if(priceSell>=priceBuy){
			return true;
		}
		return false;
	}
	public void onPacketData() {

		if(this.invMerchant.getMerchandise(this.id)!=null){
			if(canBuy(this.invMerchant.getMerchandise(this.id))){
				ItemStack bought = this.invMerchant.getMerchandise(this.id);
				MerchandiseInfo.removePriceTag(bought);
				if(!this.theCustomer.worldObj.isRemote){
					this.theCustomer.dropPlayerItem(bought);
				}
				this.invMerchant.decrStackSize(this.id+10, this.invMerchant.getMerchandise(id).stackSize);
				Unsaga.debug(this.worldobj.getTotalWorldTime());
				this.cleanBarteringInv();
			}
		}
		
		

	}
	
	public void cleanBarteringInv(){
		for(int i=0;i<9;i++){
			if(this.invMerchant.getBartering(i)!=null){
				if(MerchandiseInfo.isPossibleToSell(this.invMerchant.getBartering(i))){
					this.invMerchant.setBartering(i, null);
				}
			}
		}
	}
}

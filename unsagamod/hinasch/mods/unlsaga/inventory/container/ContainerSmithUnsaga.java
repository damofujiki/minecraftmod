package hinasch.mods.unlsaga.inventory.container;

import hinasch.lib.StaticWords;
import hinasch.mods.unlsaga.DebugUnsaga;
import hinasch.mods.unlsaga.Unsaga;
import hinasch.mods.unlsaga.client.gui.GuiSmithUnsaga;
import hinasch.mods.unlsaga.core.init.UnsagaItems;
import hinasch.mods.unlsaga.core.init.UnsagaMaterial;
import hinasch.mods.unlsaga.inventory.InventorySmithUnsaga;
import hinasch.mods.unlsaga.misc.smith.ForgingTool;
import hinasch.mods.unlsaga.misc.smith.MaterialInfo;
import hinasch.mods.unlsaga.misc.smith.ValidPayment;
import hinasch.mods.unlsaga.misc.smith.ValidPayment.EnumPayValues;
import hinasch.mods.unlsaga.misc.util.EnumUnsagaTools;
import hinasch.mods.unlsaga.network.packet.PacketGuiButton;
import io.netty.buffer.ByteBuf;

import java.util.Iterator;

import net.minecraft.entity.Entity;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ContainerSmithUnsaga extends Container{

	protected IMerchant theMerchant;
	protected EntityPlayer ep;
	protected World worldobj;
	protected InventorySmithUnsaga inventorySmith;
	protected final int iPAYMENT = 0;
	protected final int iBASE = 1;
	protected final int iMATERIAL = 2;
	protected final int iFORGED = 3;
	protected byte currentCategory = 0; //GUI側と同期される
	protected DebugUnsaga debug;



	protected int id = 0;



	public ContainerSmithUnsaga(IMerchant par1,World par2,EntityPlayer ep){

		if(debug==null){
			debug = new DebugUnsaga();
		}
		//this.helper = new SmithHelper();
		this.theMerchant = par1;
		Unsaga.debug("Container:"+this.theMerchant);
		this.worldobj = par2;
		this.inventorySmith = new InventorySmithUnsaga(ep,this.theMerchant);
		this.addSlotToContainer(new Slot(this.inventorySmith, this.iPAYMENT, 28, 53)); //Emerald
		this.addSlotToContainer(new Slot(this.inventorySmith, this.iBASE, 28, 53-(18*2))); //Base Material
		this.addSlotToContainer(new Slot(this.inventorySmith, this.iMATERIAL, 28+(18*2)-8, 53-(18*2))); //Material2
		this.addSlotToContainer(new Slot(this.inventorySmith, this.iFORGED, 28+(18*6)+1, 52)); 

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
	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		// TODO 自動生成されたメソッド・スタブ
		return this.theMerchant.getCustomer() == entityplayer;
	}

	@Override
	public void onContainerClosed(EntityPlayer par1EntityPlayer)
	{
		super.onContainerClosed(par1EntityPlayer);
		this.inventorySmith.closeInventory();
		this.theMerchant.setCustomer((EntityPlayer)null);
		super.onContainerClosed(par1EntityPlayer);
		if(!par1EntityPlayer.worldObj.isRemote){
			for(int i=0;i<this.inventorySmith.invSlot.length;i++){
				ItemStack is = this.inventorySmith.getStackInSlotOnClosing(i);
				if(is!=null){
					par1EntityPlayer.entityDropItem(is,0.1F);
				}
			}
		}
		//GUIを閉じた時の動作
		//		super.onContainerClosed(this.ep);
		//		World worldObj = this.ep.worldObj;
		//
		//		if(this.inventorySmith!=null){
		//			this.theMerchant.setCustomer((EntityPlayer)null);
		//			if (!worldObj.isRemote)
		//			{
		//				for (int var2 = 0; var2<this.inventorySmith.getSizeInventory(); ++var2)
		//				{
		//					ItemStack var3 = this.inventorySmith.getStackInSlotOnClosing(var2);
		//
		//
		//					if (var3 != null)
		//					{
		//
		//
		//						this.ep.dropPlayerItem(var3);
		//					}
		//				}
		//			}
		//			//UnsagaCore.instance.clearTileEntityBlender(entityplayer);
		//		}
	}
	public void onButtonPushed(int id, byte category) {
		PacketGuiButton pb = new PacketGuiButton(PacketGuiButton.GUI_FORGE,id,category);
		Unsaga.packetPipeline.sendToServer(pb);
		//PacketDispatcher.sendPacketToServer(pb.getPacket());

	}
	public static void writePacketData(ByteBuf buffer, int id,byte category) {


		buffer.writeInt((int)id);
		if(id==GuiSmithUnsaga.CATEGORY){
			buffer.writeByte((byte)category);
		}


	}
	public void onPacketData() {
		Unsaga.debug(this.id);
		EnumUnsagaTools category = EnumUnsagaTools.toolArray.get(currentCategory);
		MaterialInfo baseItemInfo = null;
		MaterialInfo subItemInfo = null;

		if(this.id==GuiSmithUnsaga.DOFORGE){
			byte flagcount = 0;
			if(this.inventorySmith.getPayment()!=null){
				for(Iterator<ValidPayment> ite=ValidPayment.validPayList.iterator();ite.hasNext();){
					if(ite.next().compare(this.inventorySmith.getPayment())){
						Unsaga.debug("会いました");
						flagcount += 1;
					}
				}
			}
			if(this.inventorySmith.getBaseItem()!=null){
				baseItemInfo = new MaterialInfo(this.inventorySmith.getBaseItem());
				if(baseItemInfo.getMaterial().isPresent()){
					UnsagaMaterial material = baseItemInfo.getMaterial().get();
					if(UnsagaItems.isValidItemForMaterial(category, material)){
						Unsaga.debug("ベースおｋです");
						flagcount +=1;
					}
				}
			}
			if(this.inventorySmith.getMaterialItemStack()!=null){
				subItemInfo = new MaterialInfo(this.inventorySmith.getMaterialItemStack());
				if(subItemInfo.getMaterial().isPresent()){
					Unsaga.debug("素材に使えます");
					flagcount += 1;
				}
			}
			if(this.inventorySmith.getForgedItemStack()==null){
				flagcount += 1;
			}
			if(flagcount>=4){
				this.worldobj.playSoundAtEntity((Entity) this.theMerchant, StaticWords.soundAnvilUse, 1.0F, 1.0F);
				ForgingTool newforge = new ForgingTool(category,baseItemInfo, subItemInfo,this.worldobj.rand);
				newforge.decideForgedMaterial();
				newforge.calcForgedDamage();
				newforge.prepareTransplantEnchant(this.getPaymentValue());
				newforge.calcForgedWeight();
				ItemStack newstack = newforge.getForgedItemStack();
				newforge.transplantAbilities(newstack, this.ep, this.getPaymentValue());
				this.inventorySmith.setInventorySlotContents(iFORGED, newstack);
				this.inventorySmith.decrStackSize(iBASE, 1);
				this.inventorySmith.decrStackSize(iMATERIAL, 1);
				this.inventorySmith.decrStackSize(iPAYMENT, 1);
			}
		}

	}

	public EnumPayValues getPaymentValue(){
		if(this.inventorySmith.getPayment()!=null){
			return ValidPayment.getValueFromItemStack(this.inventorySmith.getPayment());
		}
		return null;
	}

	public void readPacketData(Object[] args)
	{

			// byte型の読み込み
			this.id = (Integer)args[0];
			if(id==GuiSmithUnsaga.CATEGORY){
				this.currentCategory = (Byte)args[1];
				Unsaga.debug("Container側"+this.currentCategory);
				Unsaga.debug(EnumUnsagaTools.toolArray.get(this.currentCategory));
			}

	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
	{

		return null;

		//        Slot slot = (Slot)this.inventorySlots.get(par2);
		//        return slot != null ? slot.getStack() : null;
	}

}

package hinasch.mods.unlsagamagic.misc;

import hinasch.mods.unlsaga.Unsaga;
import hinasch.mods.unlsaga.misc.util.UtilItem;
import hinasch.mods.unlsaga.network.packet.PacketGuiButton;
import hinasch.mods.unlsagamagic.UnsagaMagic;
import hinasch.mods.unlsagamagic.client.GuiBlender;
import hinasch.mods.unlsagamagic.item.ItemBlender;
import hinasch.mods.unlsagamagic.item.ItemSpellBook;
import hinasch.mods.unlsagamagic.misc.spell.Spell;
import hinasch.mods.unlsagamagic.misc.spell.SpellBlend;
import hinasch.mods.unlsagamagic.misc.spell.SpellMixTable;
import hinasch.mods.unlsagamagic.misc.spell.Spells;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import com.google.common.base.Optional;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerBlender extends Container
{
	public static String AMP = "amplifier";

	public static String COST = "cost";
	public static String ELEMENTS = "elements";
	private int id = 1;
	protected InventoryBlender invBlender;
	
	private InventoryPlayer invp;
	private EntityPlayer playerBlending;
	private World world;

	public ContainerBlender(EntityPlayer player,World world)
	{
		int slotnum = 0;
		//is.world  = world;

		this.invBlender = new InventoryBlender();
		this.world = player.worldObj;
		this.playerBlending = player;
		this.invp = player.inventory;

		for(int i=0;i<3 ;i++){
			for(int j=0;j<3 ;j++){
				addSlotToContainer(new SlotBlender(this.invBlender,j + i * 3, 62 + j * 18, 17 + i * 18));
			}
		}




		slotnum = 0;

		for(int i = 0; i < 9; i++)
		{
			addSlotToContainer(new Slot(player.inventory, slotnum++, 8 + i * 18, 142));
			//プレイヤーの所持アイテム(下段)
		}
		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j < 9; j++)
			{
				addSlotToContainer(new Slot(player.inventory, slotnum++, 8 + j * 18, 84 + i * 18));
				//プレイヤーの所持アイテム(上段)
			}
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityPlayer)
	{


		return UtilItem.hasItemInstance(playerBlending, ItemBlender.class);
	}

	public void consumeSpellBooks(){
		for(int i=0;i<this.invBlender.getSizeInventory();i++){
			ItemStack is = this.invBlender.getStackInSlot(i);
			if(is!=null){
				this.invBlender.decrStackSize(i, 1);
			}
		}
	}




	

    
    public void doBlendSpells(){
		Spell transformed = this.getSpellTransformed().get();
		HashMap<String,SpellMixTable> tables = this.getCurrentAllElement();
		float costfloat = tables.get(COST).get(transformed.element);
		float ampfloat = tables.get(AMP).get(transformed.element);
		float blendedcost = 1;
		float blendedamp = 1;
		blendedcost += blendedcost * (costfloat/100);
		blendedamp += blendedamp * (ampfloat/100);
		blendedcost = MathHelper.clamp_float(blendedcost, 0.1F, 4.0F);
		blendedamp = MathHelper.clamp_float(blendedamp, 0.1F, 4.0F);
		
		ItemStack isBlended = new ItemStack(UnsagaMagic.itemSpellBook,1);
		this.initializeNewBlendedSpell(isBlended, transformed, blendedamp, blendedcost);
		List<Spell> spellList = new ArrayList();
		this.gatherSpellsForHistory(spellList);
		ItemSpellBook.setBlendedSpellsHistory(isBlended, spellList);
		if(!this.world.isRemote){
			this.playerBlending.entityDropItem(isBlended,0.2F);
		}
		if(!this.playerBlending.capabilities.isCreativeMode){
			this.consumeSpellBooks();
		}
	}
	
	public void gatherSpellsForHistory(List<Spell> spellList){
		for(int i=0;i<this.invBlender.getSizeInventory();i++){
			if(this.invBlender.getStackInSlot(i)!=null){
				spellList.add(ItemSpellBook.getSpell(this.invBlender.getStackInSlot(i)));
			}
		}
	}
	
	public int getAmountSlotSpellBook(){
		int amount = 0;
		for(int i=0;i<this.invBlender.getSizeInventory();i++){
			if(this.invBlender.getStackInSlot(i)!=null){
				amount +=1;
			}
		}
		return amount;
	}

	public Optional<Spell> getBaseMagic(){
		if(this.invBlender.getStackInSlot(0)!=null){
			return Optional.of(ItemSpellBook.getSpell(this.invBlender.getStackInSlot(0)));
		}
		return Optional.absent();
	}
	public ItemStack getBeseSpellBookItemStack(){
		return this.invBlender.getStackInSlot(0);
	}

	public HashMap<String,SpellMixTable> getCurrentAllElement(){
		SpellMixTable table = new SpellMixTable(0,0,0,0,0,0);
		SpellMixTable cost = new SpellMixTable(0,0,0,0,0,0);
		SpellMixTable amp = new SpellMixTable(0,0,0,0,0,0);
		HashMap<String,SpellMixTable> allMap = new HashMap();
		for(int i=0;i<this.invBlender.getSizeInventory();i++){
			ItemStack is = this.invBlender.getStackInSlot(i);
			if(is!=null){
				Spell spell = ItemSpellBook.getSpell(is);
				if(!(spell instanceof SpellBlend)){
					table.add(spell.elementsTable);
					if(i!=0){
						cost.add(spell.elementsCost);
						amp.add(spell.elementsAmp);
					}

				}
			}
		}
		table.cut(0, 50);
		cost.cut(-99, 300);
		amp.cut(-99, 200);
		allMap.put(ELEMENTS, table);
		allMap.put(AMP, amp);
		allMap.put(COST, cost);
		return allMap;
	}
	public SpellMixTable getCurrentElement(){
		return this.getCurrentAllElement().get(ELEMENTS);
	}
	
	public Optional<Spell> getSpellTransformed(){
		if(this.getBaseMagic().isPresent()){
			for(SpellBlend blend:Spells.blendSet){
				if(blend.getRequireMap().containsKey(this.getBaseMagic().get())){
					Unsaga.debug("キーがありました");
					if(this.getCurrentElement().isBiggerThan(blend.getRequireMap().get(this.getBaseMagic().get()))){
						Unsaga.debug("でかい");
						return Optional.of((Spell)blend);
					}
				}
			}
			return Optional.of(this.getBaseMagic().get());
		}

		return Optional.absent();
	}
	public boolean hasNoBlendSpell(){
		boolean flag = true;
		for(int i=0;i<this.invBlender.getSizeInventory();i++){
			ItemStack is = this.invBlender.getStackInSlot(i);
			if(is!=null){
				if(ItemSpellBook.hasMixed(is)){
					flag = false;
				}
			}
		}
		return flag;
	}

	public void initializeNewBlendedSpell(ItemStack isBlended,Spell transformed,float blendedamp,float blendedcost){
		ItemSpellBook.writeSpell(isBlended,transformed);
		ItemSpellBook.setAmp(isBlended, blendedamp);
		ItemSpellBook.setCost(isBlended, blendedcost);
		ItemSpellBook.setBlended(isBlended, true);
	}
	@SideOnly(Side.CLIENT)
	public void onButtonPushed(int id) {
		
		this.id = (int)id;
		
		/*
		 サーバー側ではボタンは押されないため, クライアント側とサーバー側の同期を取るため
		 クライアントからサーバーにボタンIDを送る.
		 このメソッドをコメントアウトすれば, すぐ上でbuttonIdをGUIからの入力に更新していても
		 サーバーとの同期が取れないため期待通りに動かないことが容易に確認できる.
		*/ 
		PacketGuiButton pgb = new PacketGuiButton(PacketGuiButton.GUI_BLEND,(int)this.id);
		Unsaga.packetPipeline.sendToServer(pgb);
//		PacketDispatcher.sendPacketToServer(PacketHandler.getBlendButtonPacket(this, (int)this.id));
		// TODO Auto-generated method stub

//		if(id==-2){
//			ItemStack var1 = this.blender.doBlend();
//
//			if(var1!=null){
//				
//				if(!this.ep.worldObj.isRemote){
//				this.ep.dropPlayerItem(var1);
//				this.ep.closeScreen();
//				}
//			}
//		}
	}
	
	@Override
	public void onContainerClosed(EntityPlayer entityplayer)
	{
		//GUIを閉じた時の動作
		super.onContainerClosed(entityplayer);
		World worldObj = entityplayer.worldObj;

			if (!worldObj.isRemote)
			{
				for (int var2 = 0; var2 < 9; ++var2)
				{
					ItemStack var3 = this.invBlender.getStackInSlotOnClosing(var2);

					
					if (var3 != null)
					{
						

						entityplayer.entityDropItem(var3,0.2F);
					}
				}
			}
			//UnsagaCore.instance.clearTileEntityBlender(entityplayer);
		
	}
	
	//パケット受け取った時に一緒に実効される
	public void onPacketData() {
		// TODO Auto-generated method stub
		if(this.id==GuiBlender.BUTTON_UNDO){
			if(getBeseSpellBookItemStack()!=null){
				if(ItemSpellBook.hasMixed(this.invBlender.getStackInSlot(0))){

					this.undoBlendedSpell();
				}
			}
		}
		if(this.id==GuiBlender.BUTTON_BLEND){
			if(this.hasNoBlendSpell() && this.getAmountSlotSpellBook()>1){
				if(this.getSpellTransformed().isPresent()){

					this.doBlendSpells();

				}
				
			}
		}
	}
	


	// パケットの読み込み(パケットの受け取りはPacketHandlerで行う)
	public void readPacketData(Object[] args)
	{

			// byte型の読み込み
			this.id = (Integer)args[0];

	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
	{

		return null;
	}
	
	public void undoBlendedSpell(){
		List<Spell> spells = ItemSpellBook.getBlendedSpells(this.invBlender.getStackInSlot(0));
		for(Spell spell:spells){
			ItemStack newstack = new ItemStack(UnsagaMagic.itemSpellBook,1);
			ItemSpellBook.writeSpell(newstack, spell);
			if(!this.world.isRemote){
				this.playerBlending.entityDropItem(newstack,0.2F);
			}
		}
		this.invBlender.decrStackSize(0, 1);
	}
	

}

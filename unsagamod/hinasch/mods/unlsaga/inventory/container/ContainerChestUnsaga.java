package hinasch.mods.unlsaga.inventory.container;

import hinasch.lib.HSLibs;
import hinasch.mods.unlsaga.Unsaga;
import hinasch.mods.unlsaga.client.gui.GuiChest;
import hinasch.mods.unlsaga.misc.ability.Ability;
import hinasch.mods.unlsaga.misc.ability.AbilityRegistry;
import hinasch.mods.unlsaga.misc.ability.HelperAbility;
import hinasch.mods.unlsaga.misc.translation.Translation;
import hinasch.mods.unlsaga.misc.util.ChatMessageHandler;
import hinasch.mods.unlsaga.misc.util.HelperChestUnsaga;
import hinasch.mods.unlsaga.network.packet.PacketGuiButton;
import hinasch.mods.unlsaga.tileentity.TileEntityChestUnsagaNew;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

public class ContainerChestUnsaga extends Container{

	public TileEntityChestUnsagaNew chest;
	public HelperChestUnsaga helper;
	protected EntityPlayer openPlayer;
	protected boolean setClose;

	protected int id;


	public ContainerChestUnsaga(TileEntityChestUnsagaNew chest, EntityPlayer ep) {
		this.openPlayer = ep;
		this.chest = chest;
		this.setClose = false;
		this.helper = new HelperChestUnsaga(chest);
	}
	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		if(this.chest==null){
			return false;
		}
		if(this.chest.hasChestOpened()){
			return false;
		}
		if(this.openPlayer.openContainer==this){
			if(this.setClose){
				return false;
			}
			return this.openPlayer==entityplayer;
		}

		return false;

	}





	public void onButtonPushed(int id) {
		PacketGuiButton pgb = new PacketGuiButton(PacketGuiButton.GUI_CHEST,id);
		Unsaga.packetPipeline.sendToServer(pgb);
		//PacketDispatcher.sendPacketToServer(PacketHandler.getChestButtonPacket(id));

	}
	public void readPacketData(Object... args) {
		this.id = (Integer)args[0];


	}
	public void onPacketData() {
		HSLibs.closeScreen(openPlayer);
		if(id==GuiChest.OPEN){
			this.chest.activateChest(this.openPlayer);


		}
		if(id==GuiChest.UNLOCK){

			if(HelperAbility.hasAbilityLiving(openPlayer, AbilityRegistry.unlock)>0){
				this.helper.tryUnlock(openPlayer);
			}else{
				ChatMessageHandler.sendChatToPlayer(openPlayer, Translation.localize("msg.has.noability"));
			}
			

		}
		if(id==GuiChest.DEFUSE){

			if(HelperAbility.hasAbilityLiving(openPlayer, AbilityRegistry.defuse)>0){
				this.helper.tryDefuse(openPlayer);
			}else{
				ChatMessageHandler.sendChatToPlayer(openPlayer, Translation.localize("msg.has.noability"));
			}

		}
		if(id==GuiChest.DIVINATION){

			if(HelperAbility.hasAbilityLiving(openPlayer, AbilityRegistry.divination)>0){
				this.helper.divination(openPlayer);
			}else{
				ChatMessageHandler.sendChatToPlayer(openPlayer, Translation.localize("msg.has.noability"));
			}
			
		}
	}
	public boolean playerHasAbility(Ability ab) {
		// TODO 自動生成されたメソッド・スタブ
		return HelperAbility.hasAbilityLiving(openPlayer, ab)>0;
	}
}

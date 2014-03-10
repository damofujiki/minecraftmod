package hinasch.mods.unlsaga.inventory.container;

import hinasch.mods.unlsaga.Unsaga;
import hinasch.mods.unlsaga.client.gui.GuiChest;
import hinasch.mods.unlsaga.misc.ability.Ability;
import hinasch.mods.unlsaga.misc.ability.AbilityRegistry;
import hinasch.mods.unlsaga.misc.ability.HelperAbility;
import hinasch.mods.unlsaga.misc.translation.Translation;
import hinasch.mods.unlsaga.misc.util.ChatMessageHandler;
import hinasch.mods.unlsaga.network.packet.PacketGuiButton;
import hinasch.mods.unlsaga.tileentity.TileEntityChestUnsaga;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

public class ContainerChestUnsaga extends Container{

	public TileEntityChestUnsaga chest;
	protected EntityPlayer openPlayer;

	protected int id;


	public ContainerChestUnsaga(TileEntityChestUnsaga chest, EntityPlayer ep) {
		this.openPlayer = ep;
		this.chest = chest;
	}
	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		// TODO 自動生成されたメソッド・スタブ
		return this.openPlayer==entityplayer;
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
		if(id==GuiChest.OPEN){
			openPlayer.openContainer = openPlayer.inventoryContainer;
			boolean flag = this.chest.activateChest(openPlayer);
			if(flag){
				this.chest.setItemsToChest(this.chest.getWorldObj().rand);
				this.chest.chestFunc(this.openPlayer);
			}

		}
		if(id==GuiChest.UNLOCK){
			openPlayer.openContainer = openPlayer.inventoryContainer;
			if(HelperAbility.hasAbilityLiving(openPlayer, AbilityRegistry.unlock)>0){
				this.chest.tryUnlock(openPlayer);
			}else{
				ChatMessageHandler.sendChatToPlayer(openPlayer, Translation.localize("msg.has.noability"));
			}
			

		}
		if(id==GuiChest.DEFUSE){
			openPlayer.openContainer = openPlayer.inventoryContainer;
			if(HelperAbility.hasAbilityLiving(openPlayer, AbilityRegistry.defuse)>0){
				this.chest.tryDefuse(openPlayer);
			}else{
				ChatMessageHandler.sendChatToPlayer(openPlayer, Translation.localize("msg.has.noability"));
			}

		}
		if(id==GuiChest.DIVINATION){
			openPlayer.openContainer = openPlayer.inventoryContainer;
			if(HelperAbility.hasAbilityLiving(openPlayer, AbilityRegistry.divination)>0){
				this.chest.divination(openPlayer);
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

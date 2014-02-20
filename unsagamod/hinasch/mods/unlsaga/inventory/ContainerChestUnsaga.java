package hinasch.mods.unlsaga.inventory;

import hinasch.mods.unlsaga.client.gui.GuiChest;
import hinasch.mods.unlsaga.misc.ability.Ability;
import hinasch.mods.unlsaga.misc.ability.AbilityRegistry;
import hinasch.mods.unlsaga.misc.ability.HelperAbility;
import hinasch.mods.unlsaga.misc.translation.Translation;
import hinasch.mods.unlsaga.network.PacketHandler;
import hinasch.mods.unlsaga.tileentity.TileEntityChestUnsaga;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

import com.google.common.io.ByteArrayDataInput;

import cpw.mods.fml.common.network.PacketDispatcher;

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
		PacketDispatcher.sendPacketToServer(PacketHandler.getChestButtonPacket(id));

	}
	public void readPacketData(ByteArrayDataInput data) {
		this.id = (int)data.readInt();


	}
	public void onPacketData() {
		if(id==GuiChest.OPEN){
			openPlayer.closeScreen();
			boolean flag = this.chest.activateChest(openPlayer);
			if(flag){
				this.chest.setItemsToChest(this.chest.worldObj.rand);
				this.chest.chestFunc(this.openPlayer);
			}

		}
		if(id==GuiChest.UNLOCK){
			openPlayer.closeScreen();
			if(HelperAbility.hasAbilityPlayer(openPlayer, AbilityRegistry.unlock)>0){
				this.chest.tryUnlock(openPlayer);
			}else{
				openPlayer.addChatMessage(Translation.localize("msg.has.noability"));
			}
			

		}
		if(id==GuiChest.DEFUSE){
			openPlayer.closeScreen();
			if(HelperAbility.hasAbilityPlayer(openPlayer, AbilityRegistry.defuse)>0){
				this.chest.tryDefuse(openPlayer);
			}else{
				openPlayer.addChatMessage(Translation.localize("msg.has.noability"));
			}

		}
		if(id==GuiChest.DIVINATION){
			openPlayer.closeScreen();
			if(HelperAbility.hasAbilityPlayer(openPlayer, AbilityRegistry.divination)>0){
				this.chest.divination(openPlayer);
			}else{
				openPlayer.addChatMessage(Translation.localize("msg.has.noability"));
			}
			
		}
	}
	public boolean playerHasAbility(Ability ab) {
		// TODO 自動生成されたメソッド・スタブ
		return HelperAbility.hasAbilityPlayer(openPlayer, ab)>0;
	}
}

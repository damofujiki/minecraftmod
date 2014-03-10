package hinasch.mods.unlsaga.client;

import hinasch.mods.unlsaga.Unsaga;
import hinasch.mods.unlsaga.network.packet.PacketGuiOpen;
import hinasch.mods.unlsaga.network.packet.PacketSkill;
import net.minecraft.client.settings.KeyBinding;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent.KeyInputEvent;

public class KeyHandler {

	private KeyBinding key_openEquipmentGui  = new KeyBinding("commaKey", Keyboard.KEY_COMMA, "Unsaga");
	private KeyBinding key_gainAbilityOnCreative = new KeyBinding("M Key",Keyboard.KEY_M,"Unsaga");
	
	public KeyHandler(){
		ClientRegistry.registerKeyBinding(key_openEquipmentGui);
		ClientRegistry.registerKeyBinding(key_gainAbilityOnCreative);
	}
	
	@SubscribeEvent
	public void onKeyPressed(KeyInputEvent e){
		if(key_openEquipmentGui.isPressed()){
			PacketGuiOpen pgo = new PacketGuiOpen(Unsaga.guiNumber.equipment);
			Unsaga.packetPipeline.sendToServer(pgo);
		}
		if(this.key_gainAbilityOnCreative.isPressed()){
			PacketSkill ps = new PacketSkill(PacketSkill.PACKETID.DEBUG_ABILITY);
			Unsaga.packetPipeline.sendToServer(ps);
		}
	}
}

package hinasch.mods.unlsaga.misc.module;

import hinasch.mods.unlsaga.Unsaga;
import hinasch.mods.unlsagamagic.client.GuiBlender;
import hinasch.mods.unlsagamagic.misc.ContainerBlender;

import java.io.DataOutputStream;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.world.World;

import com.google.common.io.ByteArrayDataInput;

import cpw.mods.fml.common.network.Player;

public class HandlerCommonProxy {

	
	public static ContainerBlender getContainerBlender(EntityPlayer ep,World world){
		return new ContainerBlender(ep,world);
	}
	
	public static GuiBlender getGuiBlender(EntityPlayer ep,World world){
		return new GuiBlender(ep,world);
	}

	public static void handleBlendButtonEvent(Player player, ByteArrayDataInput data) {
		Container container = ((EntityPlayerMP)player).openContainer;
		if(container != null && container instanceof ContainerBlender)
		{
			Unsaga.debug("コンテナ開いてます");
			((ContainerBlender)container).readPacketData(data);
			((ContainerBlender)container).onPacketData();
		}
		
	}

	public static void handleWritePacketEvent(Container blender, int id, DataOutputStream dos) {
		ContainerBlender containerBlender = (ContainerBlender)blender;
		
		containerBlender.writePacketData(dos);
		
	}
}

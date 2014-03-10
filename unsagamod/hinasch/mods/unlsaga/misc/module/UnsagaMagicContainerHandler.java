package hinasch.mods.unlsaga.misc.module;

import hinasch.mods.unlsaga.Unsaga;
import hinasch.mods.unlsagamagic.client.GuiBlender;
import hinasch.mods.unlsagamagic.misc.ContainerBlender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.world.World;

public class UnsagaMagicContainerHandler {

	
	public static ContainerBlender getContainerBlender(EntityPlayer ep,World world){
		return new ContainerBlender(ep,world);
	}
	
	public static GuiBlender getGuiBlender(EntityPlayer ep,World world){
		return new GuiBlender(ep,world);
	}

	public static void handleBlendButtonEvent(EntityPlayerMP player, Object[] args) {
		Container container = ((EntityPlayerMP)player).openContainer;
		if(container != null && container instanceof ContainerBlender)
		{
			Unsaga.debug("コンテナ開いてます");
			((ContainerBlender)container).readPacketData(args);
			((ContainerBlender)container).onPacketData();
		}
		
	}

}

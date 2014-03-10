package hinasch.mods.unlsaga.misc.util;

import hinasch.mods.unlsaga.misc.translation.Translation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

public class ChatUtil {

	public static void addMessage(EntityLivingBase ep,String mes){
		if(ep instanceof EntityPlayer){
			ChatMessageHandler.sendChatToPlayer((EntityPlayer) ep, Translation.localize(mes));
		}
		                            
		
	}
	
	public static void addMessageNoLocalized(EntityLivingBase ep,String mes){
		if(ep instanceof EntityPlayer){
			ChatMessageHandler.sendChatToPlayer((EntityPlayer) ep, mes);
		}
		
	}
}

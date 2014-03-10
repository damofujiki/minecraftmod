package hinasch.mods.unlsagamagic.event;

import hinasch.mods.unlsaga.core.event.ExtendedPlayerData;
import hinasch.mods.unlsaga.core.init.UnsagaConfigs;
import hinasch.mods.unlsagamagic.item.ItemTablet;
import hinasch.mods.unlsagamagic.misc.spell.Deciphering;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class EventDecipherAtSleep {


	
	@SubscribeEvent
	public void onPlayerTick(LivingUpdateEvent e) {
		if(e.entityLiving instanceof EntityPlayer){
			EntityPlayer entityPlayer = (EntityPlayer)e.entityLiving;
			int sleepTimer = entityPlayer.getSleepTimer();
			if(sleepTimer == 99){
				onPlayerSleep(entityPlayer.worldObj,entityPlayer);
			}
		}

	}

	public void onPlayerSleep(World world,EntityPlayer ep){

		if(!UnsagaConfigs.decipherAtSleep)return;
		if(ep.getExtendedProperties(ExtendedPlayerData.key)!=null){
			ExtendedPlayerData data = (ExtendedPlayerData)ep.getExtendedProperties(ExtendedPlayerData.key);
			if(data.getTablet()!=null && data.getTablet().getItem() instanceof ItemTablet){
				ItemStack tablet = data.getTablet();
				if(!ItemTablet.isDeciphered(tablet)){
					Deciphering.progressDeciphering(world, ep, tablet);
				}
			}
		}

	}

}

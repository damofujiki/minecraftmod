package hinasch.mods.unlsagamagic.event;

import hinasch.mods.unlsaga.misc.debuff.DebuffRegistry;
import hinasch.mods.unlsaga.misc.debuff.LivingBuff;
import hinasch.mods.unlsaga.misc.debuff.LivingDebuff;
import hinasch.mods.unlsaga.network.PacketHandler;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;

public class EventSpellBuff {

	@ForgeSubscribe
	public void onPlayerHurtDebuff(LivingHurtEvent e){
		if(e.source.getSourceOfDamage() instanceof EntityArrow){
			EntityLivingBase el = (EntityLivingBase)e.entityLiving;
			if(LivingDebuff.hasDebuff(el, DebuffRegistry.missuileGuard)){
				e.ammount = 0;
				if(e.source.getEntity() instanceof EntityPlayer){
					EntityPlayer ep = (EntityPlayer)e.source.getEntity();
					PacketDispatcher.sendPacketToPlayer(PacketHandler.getSoundPacket((int)1022,(int)el.entityId),(Player)ep);
				}else{
					EntityPlayer ep = el.worldObj.getClosestPlayerToEntity(el, 20.0D);
					if(ep!=null){
						PacketDispatcher.sendPacketToPlayer(PacketHandler.getSoundPacket((int)1022,(int)el.entityId),(Player)ep);
					}
				}
				
				
			}
			

		}
		if(e.entityLiving instanceof EntityLivingBase){
			EntityLivingBase damagedLiving = (EntityLivingBase)e.entityLiving;
			if(LivingDebuff.hasDebuff(damagedLiving, DebuffRegistry.leavesShield)){
				if(LivingDebuff.getLivingDebuff(damagedLiving, DebuffRegistry.leavesShield).isPresent()){
					LivingBuff buff = (LivingBuff)LivingDebuff.getLivingDebuff(damagedLiving, DebuffRegistry.leavesShield).get();
					if(damagedLiving.getRNG().nextInt(100)<buff.amp){
						e.ammount = 0;
						if(e.source.getEntity() instanceof EntityPlayer){
							EntityPlayer ep = (EntityPlayer)e.source.getEntity();
							PacketDispatcher.sendPacketToPlayer(PacketHandler.getSoundPacket((int)1022,(int)damagedLiving.entityId),(Player)ep);
						}else{
							EntityPlayer ep = damagedLiving.worldObj.getClosestPlayerToEntity(damagedLiving, 20.0D);
							if(ep!=null){
								PacketDispatcher.sendPacketToPlayer(PacketHandler.getSoundPacket((int)1022,(int)damagedLiving.entityId),(Player)ep);
							}
						}
					}

				}

				
				
			}
			
			
		}
		if(e.source.getEntity() instanceof EntityLivingBase){
			EntityLivingBase attackLiving = (EntityLivingBase)e.source.getEntity();
			if(LivingDebuff.hasDebuff(attackLiving, DebuffRegistry.powerup)){
				e.ammount += 1;
			}
		}
	}
}

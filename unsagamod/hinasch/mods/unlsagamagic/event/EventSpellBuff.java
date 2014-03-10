package hinasch.mods.unlsagamagic.event;

import hinasch.mods.unlsaga.Unsaga;
import hinasch.mods.unlsaga.misc.debuff.Debuffs;
import hinasch.mods.unlsaga.misc.debuff.livingdebuff.LivingBuff;
import hinasch.mods.unlsaga.misc.debuff.livingdebuff.LivingDebuff;
import hinasch.mods.unlsaga.network.packet.PacketSound;
import hinasch.mods.unlsaga.network.packet.PacketUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;

public class EventSpellBuff {

	protected PacketSound ps;

	@SubscribeEvent
	public void onPlayerHurtDebuff(LivingHurtEvent e){
		if(e.source.getSourceOfDamage() instanceof EntityArrow){
			EntityLivingBase el = (EntityLivingBase)e.entityLiving;
			if(LivingDebuff.hasDebuff(el, Debuffs.missuileGuard)){
				e.ammount = 0;
				ps = new PacketSound(1022,el.getEntityId(),PacketSound.MODE.AUX);

				EntityPlayer ep = (EntityPlayer)e.source.getEntity();
				TargetPoint tp = PacketUtil.getTargetPointNear(el);
				Unsaga.packetPipeline.sendToAllAround(ps, tp);
				//PacketDispatcher.sendPacketToPlayer(ps.getPacket(),(Player)ep);



			}


		}
		if(e.entityLiving instanceof EntityLivingBase){
			EntityLivingBase damagedLiving = (EntityLivingBase)e.entityLiving;
			if(LivingDebuff.hasDebuff(damagedLiving, Debuffs.leavesShield)){
				if(LivingDebuff.getLivingDebuff(damagedLiving, Debuffs.leavesShield).isPresent()){
					LivingBuff buff = (LivingBuff)LivingDebuff.getLivingDebuff(damagedLiving, Debuffs.leavesShield).get();
					if(damagedLiving.getRNG().nextInt(100)<buff.amp){
						e.ammount = 0;
						ps = new PacketSound(1022,damagedLiving.getEntityId(),PacketSound.MODE.AUX);

						EntityPlayer ep = (EntityPlayer)e.source.getEntity();
						TargetPoint tp = PacketUtil.getTargetPointNear(damagedLiving);
						Unsaga.packetPipeline.sendToAllAround(ps, tp);
						//PacketDispatcher.sendPacketToPlayer(ps.getPacket(),(Player)ep);

					}

				}



			}


		}

	}
}

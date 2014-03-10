package hinasch.mods.unlsaga.core.event;

import hinasch.lib.HSLibs;
import hinasch.lib.StaticWords;
import hinasch.lib.XYZPos;
import hinasch.mods.unlsaga.Unsaga;
import hinasch.mods.unlsaga.item.weapon.ItemBowUnsaga;
import hinasch.mods.unlsaga.misc.ability.Ability;
import hinasch.mods.unlsaga.misc.ability.AbilityRegistry;
import hinasch.mods.unlsaga.misc.ability.skill.effect.SkillBow;
import hinasch.mods.unlsaga.network.packet.PacketParticle;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class EventLivingUpdate {

	protected AxisAlignedBB bb;
	protected AbilityRegistry ar = Unsaga.abilityRegistry;
	@SubscribeEvent
	public void onLivingUpdata(LivingUpdateEvent e){
		SkillBow.checkShadowStitchOnLivingUpdate(e);

		Ability.abilityEventOnLivingUpdate(e.entityLiving,ar);

		
		//矢の追加パーティクル用
		if(e.entityLiving instanceof EntityPlayer){

			EntityPlayer ep = (EntityPlayer)e.entityLiving;
			if(!HSLibs.isServer(ep.worldObj)){
				return;
			}
			//Unsaga.debug("クライアントです");
			this.bb = ep.boundingBox.expand(100.0D, 100.0D, 100.0D);
			List<EntityArrow> nearArrowList = ep.worldObj.getEntitiesWithinAABB(EntityArrow.class, bb);
			for(EntityArrow arrow:nearArrowList){
				if(arrow.ticksExisted %2 == 0){
					if(!arrow.isCollided && ExtendedEntityTag.entityHasTag(arrow, ItemBowUnsaga.ARROW_EXORCIST)){
						PacketParticle pp = new PacketParticle(XYZPos.entityPosToXYZ(arrow),StaticWords.getParticleNumber(StaticWords.particleHappy),2);
						Unsaga.packetPipeline.sendTo(pp, (EntityPlayerMP) ep);
						//arrow.worldObj.spawnParticle(StaticWords.particleHappy, arrow.posX, arrow.posY + ep.worldObj.rand.nextDouble() * 2.0D, arrow.posZ, ep.worldObj.rand.nextGaussian()*2, 0.0D, ep.worldObj.rand.nextGaussian()*2);
					}
					
				}
			}

		}

	}
}

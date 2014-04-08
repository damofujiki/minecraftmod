package hinasch.mods.unlsagamagic.event;

import hinasch.mods.unlsaga.core.FiveElements.EnumElement;
import hinasch.mods.unlsaga.misc.debuff.Buff;
import hinasch.mods.unlsaga.misc.debuff.Debuffs;
import hinasch.mods.unlsaga.misc.util.DamageHelper;
import hinasch.mods.unlsaga.misc.util.DamageSourceUnsaga;
import hinasch.mods.unlsaga.network.packet.PacketSound;

import java.util.HashSet;
import java.util.Set;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class EventSpellBuff {

	public static Set<BuffShield> shieldSet = new HashSet();
	protected PacketSound ps;
	protected BuffShield shieldMissileGuard = new BuffShieldMissileGuard(Debuffs.missuileGuard,true,EnumElement.WOOD);
	protected BuffShield shieldLeaves = new BuffShieldLeavesShield(Debuffs.leavesShield,false,EnumElement.WOOD);
	protected BuffShield shieldWater = new BuffShieldWaterShield(Debuffs.waterShield,false,EnumElement.WATER);
	protected BuffShield shieldAegis = new BuffShieldAegis(Debuffs.aegisShield,false,EnumElement.EARTH);
	public class BuffShieldMissileGuard extends BuffShield{

		public BuffShieldMissileGuard(Buff parent, boolean isGuardAll,
				EnumElement element) {
			super(parent, isGuardAll, element);
			// TODO 自動生成されたコンストラクター・スタブ
		}

		@Override
		public boolean isEffective(LivingHurtEvent e) {
			if(e.source.getSourceOfDamage() instanceof EntityArrow){
				return true;
			}
			if(e.source instanceof DamageSourceUnsaga){
				DamageSourceUnsaga ds = (DamageSourceUnsaga)e.source;
				if(ds.getUnsagaDamageType()==DamageHelper.Type.SPEAR){
					return true;
				}
			}
			return false;
		}
		

		
	}
	public class BuffShieldAegis extends BuffShield{

		public BuffShieldAegis(Buff parent, boolean isGuardAll,
				EnumElement element) {
			super(parent, isGuardAll, element);
			// TODO 自動生成されたコンストラクター・スタブ
		}

		@Override
		public boolean isEffective(LivingHurtEvent e) {
			if(!e.source.isUnblockable()){
				return true;
			}
			return false;
		}
		
	}
	
	public class BuffShieldLeavesShield extends BuffShield{

		public BuffShieldLeavesShield(Buff parent, boolean isGuardAll,
				EnumElement element) {
			super(parent, isGuardAll, element);
			// TODO 自動生成されたコンストラクター・スタブ
		}

		@Override
		public boolean isEffective(LivingHurtEvent e) {
			if(e.source.getEntity() instanceof EntityLivingBase && !e.source.isMagicDamage() && !e.source.isUnblockable()){
				return true;
			}
			return false;
		}
		
	}
	
	public class BuffShieldWaterShield extends BuffShield{

		public BuffShieldWaterShield(Buff parent, boolean isGuardAll,
				EnumElement element) {
			super(parent, isGuardAll, element);
			// TODO 自動生成されたコンストラクター・スタブ
		}

		@Override
		public boolean isEffective(LivingHurtEvent e) {
			if(e.source.getEntity() instanceof EntityBlaze || e.source.getEntity() instanceof EntityMagmaCube){
				return true;
			}
			if(e.source.getEntity() instanceof EntityFireball){
				return true;
			}
			if(e.source.isFireDamage()){
				return true;
			}

			if(e.source instanceof DamageSourceUnsaga){
				DamageSourceUnsaga ds = (DamageSourceUnsaga)e.source;
				if(ds.getSubDamageType().contains(DamageHelper.SubType.FIRE)){
					return true;
				}
			}

			return false;
		}
		
	}

	@SubscribeEvent
	public void onPlayerHurtDebuff(LivingHurtEvent e){
		
		for(BuffShield shield:shieldSet){
			shield.doGuard(e);
		}

		
//		if(e.source.getSourceOfDamage() instanceof EntityArrow){
//			EntityLivingBase el = (EntityLivingBase)e.entityLiving;
//			if(LivingDebuff.hasDebuff(el, Debuffs.missuileGuard)){
//				e.ammount = 0;
//				ps = new PacketSound(1022,el.getEntityId(),PacketSound.MODE.AUX);
//
//				EntityPlayer ep = (EntityPlayer)e.source.getEntity();
//				TargetPoint tp = PacketUtil.getTargetPointNear(el);
//				Unsaga.packetPipeline.sendToAllAround(ps, tp);
//				//PacketDispatcher.sendPacketToPlayer(ps.getPacket(),(Player)ep);
//
//
//
//			}
//
//
//		}
//		if(e.entityLiving instanceof EntityLivingBase){
//			EntityLivingBase damagedLiving = (EntityLivingBase)e.entityLiving;
//			if(LivingDebuff.hasDebuff(damagedLiving, Debuffs.leavesShield)){
//				if(LivingDebuff.getLivingDebuff(damagedLiving, Debuffs.leavesShield).isPresent()){
//					LivingBuff buff = (LivingBuff)LivingDebuff.getLivingDebuff(damagedLiving, Debuffs.leavesShield).get();
//					if(damagedLiving.getRNG().nextInt(100)<buff.amp){
//						e.ammount = 0;
//						ps = new PacketSound(1022,damagedLiving.getEntityId(),PacketSound.MODE.AUX);
//
//						EntityPlayer ep = (EntityPlayer)e.source.getEntity();
//						TargetPoint tp = PacketUtil.getTargetPointNear(damagedLiving);
//						Unsaga.packetPipeline.sendToAllAround(ps, tp);
//						//PacketDispatcher.sendPacketToPlayer(ps.getPacket(),(Player)ep);
//
//					}
//
//				}
//
//
//
//			}
//
//
//		}

	}
}

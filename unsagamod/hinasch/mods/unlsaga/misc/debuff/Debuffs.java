package hinasch.mods.unlsaga.misc.debuff;

import hinasch.lib.StaticWords;
import hinasch.mods.unlsaga.core.init.UnsagaConfigs;
import hinasch.mods.unlsaga.misc.debuff.livingdebuff.LivingBuff;
import hinasch.mods.unlsaga.misc.debuff.livingdebuff.LivingDebuff;
import hinasch.mods.unlsaga.misc.debuff.state.State;
import hinasch.mods.unlsaga.misc.debuff.state.StateBow;
import hinasch.mods.unlsaga.misc.debuff.state.StateCrimsonFlare;
import hinasch.mods.unlsaga.misc.debuff.state.StateFlyingAxe;
import hinasch.mods.unlsaga.misc.debuff.state.StateRandomThrow;
import hinasch.mods.unlsaga.misc.debuff.state.StateTarget;

import java.util.HashMap;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class Debuffs {

	
	protected static HashMap<Integer,Debuff> debuffMap = new HashMap();
	
	public static final Debuff downSkill = new Debuff(1,"Down Skill");
	public static final Debuff downPhysical = new Debuff(2,"Down Physical");

	public static final Debuff downMagic = new Debuff(4,"Down Magic");
	public static final Debuff sleep = new Debuff(5,"sleep");
	public static final Debuff lockSlime = new Buff(6,"Slime Lock");
	public static final Debuff detected = new Debuff(7,"Detected");
	 
	
	public static final Buff fireVeil = (Buff) new Buff(10,"Fire Veil").setParticleNumber(StaticWords.getParticleNumber(StaticWords.particleFlame));
	public static final Buff waterVeil = (Buff)new Buff(11,"Water Veil").setParticleNumber(202);
	public static final Buff earthVeil = (Buff)new Buff(12,"Earth Veil").setParticleNumber(200);
	public static final Buff woodVeil = (Buff)new Buff(13,"Wood Veil").setParticleNumber(201);
	public static final Buff metalVeil = (Buff)new Buff(14,"Metal Veil").setParticleNumber(StaticWords.getParticleNumber(StaticWords.particleReddust));
	
	public static final Buff missuileGuard = new Buff(15,"Missuile Guard");
	public static final Buff leavesShield = (Buff)new Buff(16,"Leaves Shield").setParticleNumber(201);
	public static final Buff powerup = new Buff(17,"Power up");
	public static final Buff lifeBoost = new Buff(18,"Life Boost");
	public static final Buff magicUp = new Buff(19,"Magic Up");
	public static final Buff perseveranceUp = new Buff(20,"perseverance Up");


	//public static final State _antiFallDamage = new State(18,"anti Fall","落下無敵");
	 
	public static final State cooling = new State(99,"Cooling");
	public static final State spellTarget = new StateTarget(100,"Spell Target");
	public static final State flyingAxe = new StateFlyingAxe(101,"SkyDrive");
	public static final State kalaidoscope = new State(102,"kaleidoScope");
	public static final State antiFallDamage = new State(103,"anti Fall");
	public static final State weaponTarget = new StateTarget(104,"Weapon Target");
	public static final State rushBlade = new State(105,"Rush Blade");
	public static final State roundabout = new State(106,"Roundabout");
	public static final State setAiming = new State(107,"SetAiming");
	public static final State bowDouble = new StateBow(108,"Double Shot");
	//public static final State grandSlam = new StateGrandSlam(109,"grandslam","グランドスラム");
	public static final State stoneShower = new StateRandomThrow(110,"stoneShower").setSoundString(StaticWords.soundFireBall).setInterval(2);
	public static final State thunderCrap = new StateRandomThrow(111,"thunderCrap").setSoundString(StaticWords.soundShoot).setInterval(4);
	
	public static final State gust = new State(112,"HeadWind");
	public static final State crimsonFlare = new StateCrimsonFlare(113,"CrimsonFlare");
	
	public static Debuff getDebuff(int par1){
		return debuffMap.get(par1);
		
	}
	
	public static void debuffEventOnLivingHurt(LivingHurtEvent e){
		EntityLivingBase hurtEntity = e.entityLiving;
		Entity attacker = e.source.getEntity();
		
		if((LivingDebuff.hasDebuff(hurtEntity,detected))){
			e.ammount += 1;
		}
		
		if(attacker instanceof EntityLivingBase){
			if(LivingDebuff.hasDebuff((EntityLivingBase) attacker,powerup)){
				LivingBuff buff = (LivingBuff) LivingDebuff.getLivingDebuff((EntityLivingBase) attacker, powerup).get();
				e.ammount += buff.amp;
			}
			if(LivingDebuff.hasDebuff((EntityLivingBase) attacker,downSkill)){
				if(!UnsagaConfigs.module.isLPEnabled()){
					LivingBuff buff = (LivingBuff) LivingDebuff.getLivingDebuff((EntityLivingBase) attacker, downSkill).get();
					if(((EntityLivingBase) attacker).getRNG().nextInt(100)<20)
					e.ammount = 1;
				}

			}

			
			if(LivingDebuff.hasDebuff(hurtEntity, downPhysical)){
				float yaw = MathHelper.wrapAngleTo180_float(hurtEntity.rotationYaw + 180.0F);
				float i = 1.0F;
				hurtEntity.addVelocity((double)(-MathHelper.sin((yaw) * (float)Math.PI / 180.0F) * (float)i * 0.5F), 0.1D, (double)(MathHelper.cos(yaw * (float)Math.PI / 180.0F) * (float)i * 0.5F));
			}
		}

		
		if(LivingDebuff.hasDebuff(hurtEntity, antiFallDamage) && e.source == DamageSource.fall){
			e.ammount = 0;
			hurtEntity.fallDistance = 0;
			e.setCanceled(true);
			LivingDebuff.removeDebuff(hurtEntity, antiFallDamage);
			return;
		}
		
		if(e.source.isMagicDamage() && attacker instanceof EntityLivingBase){
			if(LivingDebuff.hasDebuff((EntityLivingBase) attacker, Debuffs.downMagic)){
				e.ammount *= 0.5F;
				MathHelper.clamp_float(e.ammount, 1.0F, 1000.0F);
			}
		}
		
		
	}
}

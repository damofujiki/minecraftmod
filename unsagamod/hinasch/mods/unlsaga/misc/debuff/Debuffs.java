package hinasch.mods.unlsaga.misc.debuff;

import hinasch.lib.StaticWords;
import hinasch.mods.unlsaga.Unsaga;
import hinasch.mods.unlsaga.core.init.UnsagaConfigs;
import hinasch.mods.unlsaga.misc.ability.AbilityRegistry;
import hinasch.mods.unlsaga.misc.debuff.livingdebuff.LivingDebuff;
import hinasch.mods.unlsaga.misc.debuff.state.State;
import hinasch.mods.unlsaga.misc.debuff.state.StateBow;
import hinasch.mods.unlsaga.misc.debuff.state.StateCrimsonFlare;
import hinasch.mods.unlsaga.misc.debuff.state.StateFlyingAxe;
import hinasch.mods.unlsaga.misc.debuff.state.StateRandomThrow;
import hinasch.mods.unlsaga.misc.debuff.state.StateTarget;

import java.util.HashMap;
import java.util.UUID;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import com.google.common.collect.Lists;

public class Debuffs {


	protected static HashMap<Integer,Debuff> debuffMap = new HashMap();
	
	public static final Debuff downSkill = new Debuff(1,"Down Skill").addAbilityAgainst(AbilityRegistry.skillGuard);
	public static final Debuff downPhysical = new Debuff(2,"Down Physical").addAbilityAgainst(AbilityRegistry.bodyGuard);

	public static final Debuff downMagic = new Debuff(4,"Down Magic").addAbilityAgainst(AbilityRegistry.magicGuard);
	public static final Debuff sleep = new Debuff(5,"sleep").setAbilitiesAgainst(Lists.newArrayList(AbilityRegistry.antiDebuff,AbilityRegistry.antiSleep));
	public static final Debuff lockSlime = new Buff(6,"Slime Lock");
	public static final Debuff detected = new Debuff(7,"Detected");
	 
	
	public static final Buff fireVeil = (Buff) new Buff(10,"Fire Veil").setParticleNumber(StaticWords.getParticleNumber(StaticWords.particleFlame));
	public static final Buff waterVeil = (Buff)new Buff(11,"Water Veil").setParticleNumber(202);
	public static final Buff earthVeil = (Buff)new Buff(12,"Earth Veil").setParticleNumber(200);
	public static final Buff woodVeil = (Buff)new Buff(13,"Wood Veil").setParticleNumber(201);
	public static final Buff metalVeil = (Buff)new Buff(14,"Metal Veil").setParticleNumber(StaticWords.getParticleNumber(StaticWords.particleReddust));
	
	public static final Buff missuileGuard = new Buff(15,"Missuile Guard");
	public static final Buff leavesShield = (Buff)new Buff(16,"Leaves Shield").setParticleNumber(201);
	public static final Buff powerup = (Buff) new Buff(17,"Power up").setAttributeModifier(SharedMonsterAttributes.attackDamage,new AttributeModifier(UUID.fromString("2a0fed7c-cd21-dca7-8498-f8a85db66619"),"Buff Power Up",0.25D,1));
	public static final Buff lifeBoost = new Buff(18,"Life Boost");
	public static final Buff magicUp = new Buff(19,"Magic Up");
	public static final Buff perseveranceUp = new Buff(20,"perseverance Up");
	public static final Buff waterShield = (Buff) new Buff(21,"ice shield").setParticleNumber(202);
	public static final Buff physicalUp = (Buff) new Buff(22,"Physical Up").setAttributeModifier(SharedMonsterAttributes.knockbackResistance,new AttributeModifier(UUID.fromString("f48ae4e8-2d96-ce9b-1fd3-2796557439d3"),"Build Up SuperArmor",1.0D,0));
	public static final Buff aegisShield = (Buff) new Buff(23,"Aegis Shield");
	public static final Buff perseveranceDown = new Buff(24,"perseverance Up");

	//public static final State _antiFallDamage = new State(18,"anti Fall","落下無敵");
	 
	public static final State cooling = new State(99,"Cooling");
	public static final State spellTarget = new StateTarget(100,"Spell Target");
	public static final State flyingAxe = new StateFlyingAxe(101,"SkyDrive");
	public static final State kalaidoscope = new State(102,"kaleidoScope");
	public static final State antiFallDamage = new State(103,"anti Fall");
	public static final State weaponTarget = new StateTarget(104,"Weapon Target");
	public static final State rushBlade = new State(105,"Rush Blade");
	public static final State roundabout = (State) new State(106,"Roundabout").setAttributeModifier(SharedMonsterAttributes.attackDamage,new AttributeModifier(UUID.fromString("9dba3eaf-7cc8-4c36-a380-1bf1bf6221df"),"Critical On Roundabout",0.25D,1));
	public static final State setAiming = new State(107,"SetAiming");
	public static final State bowDouble = new StateBow(108,"Double Shot");
	//public static final State grandSlam = new StateGrandSlam(109,"grandslam","グランドスラム");
	public static final State stoneShower = new StateRandomThrow(110,"stoneShower").setSoundString(StaticWords.soundFireBall).setInterval(2);
	public static final State thunderCrap = new StateRandomThrow(111,"thunderCrap").setSoundString("random.bow").setInterval(4);
	
	public static final State gust = new State(112,"HeadWind");
	public static final State crimsonFlare = new StateCrimsonFlare(113,"CrimsonFlare");
	
	public static Debuff getDebuff(int par1){
		return debuffMap.get(par1);
		
	}
	
	public static void debuffEventOnLivingHurt(LivingHurtEvent e){
		EntityLivingBase hurtEntity = e.entityLiving;
		Entity attacker = e.source.getEntity();
		
		if((LivingDebuff.hasDebuff(hurtEntity,detected))){
			e.ammount *= 1.25F;
		}
		
		
	
		//攻撃手に関するデバフ
		if(attacker instanceof EntityLivingBase){
			EntityLivingBase livingAttacker = (EntityLivingBase)attacker;

			if(LivingDebuff.hasDebuff(livingAttacker,downSkill)){
				if(!UnsagaConfigs.module.isLPEnabled()){
					LivingDebuff buff = (LivingDebuff) LivingDebuff.getLivingDebuff((EntityLivingBase) attacker, downSkill).get();
					if(((EntityLivingBase) attacker).getRNG().nextInt(100)<20){
						e.ammount = 1;
					}
					
				}

			}
			if(LivingDebuff.hasDebuff(livingAttacker,downMagic)){
				if(e.source.isMagicDamage()){
					e.ammount *= 0.5F;
					Unsaga.debug("魔力減がきいている");
				}


			}


			
			if(LivingDebuff.hasDebuff(hurtEntity, downPhysical)){
				float yaw = MathHelper.wrapAngleTo180_float(hurtEntity.rotationYaw + 180.0F);
				float i = 1.0F;
				hurtEntity.addVelocity((double)(-MathHelper.sin((yaw) * (float)Math.PI / 180.0F) * (float)i * 0.5F), 0.1D, (double)(MathHelper.cos(yaw * (float)Math.PI / 180.0F) * (float)i * 0.5F));
			}
		}

		if(e.source.isUnblockable()){
			if(LivingDebuff.hasDebuff(hurtEntity,perseveranceDown)){
					e.ammount *= 1.25F;
					Unsaga.debug("心DOWNがきいている");
				


			}
		}
		doBuffs(e);


		
		
	}
	

	
	protected static void doBuffs(LivingHurtEvent e){
		EntityLivingBase hurtEntity = e.entityLiving;
		Entity attacker = e.source.getEntity();
		//攻撃手に関係するバフ
		if(attacker instanceof EntityLivingBase){
			EntityLivingBase livingAttacker = (EntityLivingBase)attacker;
			if(LivingDebuff.hasDebuff(livingAttacker,magicUp)){
				if(e.source.isMagicDamage()){
					e.ammount *= 1.5F;
					Unsaga.debug("魔力upがきいている");
				}


			}
		}
		if(e.source.isUnblockable()){
			if(LivingDebuff.hasDebuff(hurtEntity,perseveranceUp)){
					e.ammount *= 0.6F;
					Unsaga.debug("心UPがきいている");
				


			}
		}
		if(LivingDebuff.hasDebuff(hurtEntity, antiFallDamage) && e.source == DamageSource.fall){
			e.ammount = 0;
			hurtEntity.fallDistance = 0;
			e.setCanceled(true);
			LivingDebuff.removeDebuff(hurtEntity, antiFallDamage);
			return;
		}
	}
}

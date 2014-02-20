package hinasch.mods.unlsaga.misc.ability.skill.effect;

import hinasch.lib.HSLibs;
import hinasch.mods.unlsaga.Unsaga;
import hinasch.mods.unlsaga.misc.ability.AbilityRegistry;
import hinasch.mods.unlsaga.misc.debuff.DebuffRegistry;
import hinasch.mods.unlsaga.misc.debuff.LivingDebuff;
import hinasch.mods.unlsaga.misc.debuff.LivingState;

import java.util.Random;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.Vec3;

public class SkillSword extends SkillEffect{

	@Override
	public void selector(SkillEffectHelper helper){
		if(helper.skill==AbilityRegistry.gust)this.doGust(helper);
		if(helper.skill==AbilityRegistry.chargeBlade)this.doRearBlade(helper);
		if(helper.skill==AbilityRegistry.kaleidoscope)this.doKaleidoscope(helper);
		if(helper.skill==AbilityRegistry.vandalize)this.doVandelize(helper);
	}
	
	public void doGust(SkillEffectHelper helper){
		helper.ownerSkill.playSound("mob.wither.shoot", 0.5F, 1.8F / (helper.ownerSkill.getRNG().nextFloat() * 0.4F + 1.2F));
		Vec3 lookvec = helper.ownerSkill.getLookVec();
		helper.ownerSkill.setVelocity(lookvec.xCoord*2.5, 0, lookvec.zCoord*2.5);
		LivingDebuff.addLivingDebuff(helper.ownerSkill, new LivingState(DebuffRegistry.gust,50,false));
		return;
	}

	public void doVandelize(SkillEffectHelper parent){

		
		EntityLivingBase entity = parent.target;
		if(!parent.world.isRemote){
			if(HSLibs.isEntityLittleMaidAvatar(parent.ownerSkill)){
				parent.world.createExplosion(HSLibs.getLMMFromAvatar(parent.ownerSkill), entity.posX, entity.posY, entity.posZ, 1.5F,false);
				return;
			}
			parent.world.createExplosion(parent.ownerSkill, entity.posX, entity.posY, entity.posZ, 1.5F,false);

		}


	}

	public void doKaleidoscope(SkillEffectHelper parent){
		Unsaga.lpHandler.tryHurtLP(parent.target, parent.getAttackDamageLP());
		Random random = parent.ownerSkill.getRNG();
		LivingDebuff.addLivingDebuff(parent.ownerSkill,new LivingState(DebuffRegistry.antiFallDamage,10,true));
		LivingDebuff.addLivingDebuff(parent.ownerSkill, new LivingState(DebuffRegistry.kalaidoscope,10,true));
		parent.ownerSkill.playSound("mob.wither.shoot", 0.5F, 1.8F / (parent.ownerSkill.getRNG().nextFloat() * 0.4F + 1.2F));
		double newposY = parent.target.posY + random.nextInt(4)+12;
		//ep.posY = newposY;
		parent.ownerSkill.motionY = 0.8;


		double disX = parent.target.posX-parent.ownerSkill.posX;
		double disZ =parent.target.posZ-parent.ownerSkill.posZ;
		double distance = Math.sqrt(Math.pow(disX, 2)+Math.pow(disZ,2));

		parent.ownerSkill.motionX = (disX*0.08);
		parent.ownerSkill.motionZ = (disZ*0.08);


	}

	public void doRearBlade(SkillEffectHelper parent){
		parent.ownerSkill.hurtResistantTime = 5;
		parent.ownerSkill.playSound("mob.wither.shoot", 0.5F, 1.8F / (parent.ownerSkill.getRNG().nextFloat() * 0.4F + 1.2F));
		LivingDebuff.addLivingDebuff(parent.ownerSkill, new LivingState(DebuffRegistry.rushBlade,1, false));

		//parent.ownerSkill.moveEntityWithHeading(parent.ownerSkill.moveStrafing,-parent.ownerSkill.moveForward);

		//ep.moveEntityWithHeading(1.0F	, 2.0F);
	}


}

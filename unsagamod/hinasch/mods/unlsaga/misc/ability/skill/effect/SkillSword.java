package hinasch.mods.unlsaga.misc.ability.skill.effect;

import hinasch.lib.HSLibs;
import hinasch.mods.unlsaga.Unsaga;
import hinasch.mods.unlsaga.misc.ability.AbilityRegistry;
import hinasch.mods.unlsaga.misc.debuff.Debuffs;
import hinasch.mods.unlsaga.misc.debuff.livingdebuff.LivingDebuff;
import hinasch.mods.unlsaga.misc.debuff.livingdebuff.LivingState;
import hinasch.mods.unlsaga.misc.util.rangedamage.CauseKnockBack;

import java.util.Random;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;

public class SkillSword extends SkillEffect{

	private static SkillSword INSTANCE;


	@Override
	public void selector(InvokeSkill helper){
		if(helper.skill==AbilityRegistry.gust)this.doGust(helper);
		if(helper.skill==AbilityRegistry.chargeBlade)this.doRearBlade(helper);
		if(helper.skill==AbilityRegistry.kaleidoscope)this.doKaleidoscope(helper);
		if(helper.skill==AbilityRegistry.vandalize)this.doVandelize(helper);
	}
	
	public final SkillBase gust = new SkillGust();
	public final SkillBase kaleidoScope = new SkillKaleidoScope();
	public final SkillBase chargeBlade = new SkillChargeBlade();
	public final SkillBase vandelize = new SkillVandelize();
	
	public class SkillGust extends SkillBase{

		@Override
		public void invokeSkill(InvokeSkill helper) {
			helper.owner.playSound("mob.wither.shoot", 0.5F, 1.8F / (helper.owner.getRNG().nextFloat() * 0.4F + 1.2F));
			Vec3 lookvec = helper.owner.getLookVec();
			helper.owner.setVelocity(lookvec.xCoord*2.5, 0, lookvec.zCoord*2.5);
			LivingDebuff.addLivingDebuff(helper.owner, new LivingState(Debuffs.gust,100,false));
			return;
		}
		
	}
	public void doGust(InvokeSkill helper){
		helper.owner.playSound("mob.wither.shoot", 0.5F, 1.8F / (helper.owner.getRNG().nextFloat() * 0.4F + 1.2F));
		Vec3 lookvec = helper.owner.getLookVec();
		helper.owner.setVelocity(lookvec.xCoord*2.5, 0, lookvec.zCoord*2.5);
		LivingDebuff.addLivingDebuff(helper.owner, new LivingState(Debuffs.gust,100,false));
		return;
	}

	public class SkillVandelize extends SkillBase{

		@Override
		public void invokeSkill(InvokeSkill parent) {
			EntityLivingBase entity = parent.target;
			parent.attack(parent.target, null);
			if(!parent.world.isRemote){
				if(HSLibs.isEntityLittleMaidAvatar(parent.owner)){
					parent.world.createExplosion(HSLibs.getLMMFromAvatar(parent.owner), entity.posX, entity.posY, entity.posZ, 1.5F,false);
					return;
				}
				parent.world.createExplosion(parent.owner, entity.posX, entity.posY, entity.posZ, 1.5F,false);

			}
		}
		
	}
	public void doVandelize(InvokeSkill parent){

		
		EntityLivingBase entity = parent.target;
		if(!parent.world.isRemote){
			if(HSLibs.isEntityLittleMaidAvatar(parent.owner)){
				parent.world.createExplosion(HSLibs.getLMMFromAvatar(parent.owner), entity.posX, entity.posY, entity.posZ, 1.5F,false);
				return;
			}
			parent.world.createExplosion(parent.owner, entity.posX, entity.posY, entity.posZ, 1.5F,false);

		}


	}

	public class SkillKaleidoScope extends SkillBase{

		@Override
		public void invokeSkill(InvokeSkill parent) {
			Random random = parent.owner.getRNG();
			parent.attack(parent.target, null);
			LivingDebuff.addLivingDebuff(parent.owner,new LivingState(Debuffs.antiFallDamage,10,true));
			LivingDebuff.addLivingDebuff(parent.owner, new LivingState(Debuffs.kalaidoscope,10,true));
			parent.owner.playSound("mob.wither.shoot", 0.5F, 1.8F / (parent.owner.getRNG().nextFloat() * 0.4F + 1.2F));
			double newposY = parent.target.posY + random.nextInt(4)+12;
			//ep.posY = newposY;
			parent.owner.motionY = 0.8;


			double disX = parent.target.posX-parent.owner.posX;
			double disZ =parent.target.posZ-parent.owner.posZ;
			double distance = Math.sqrt(Math.pow(disX, 2)+Math.pow(disZ,2));

			parent.owner.motionX = (disX*0.08);
			parent.owner.motionZ = (disZ*0.08);
		}
		
	}
	public void doKaleidoscope(InvokeSkill parent){
		Unsaga.lpHandler.tryHurtLP(parent.target, parent.getAttackDamageLP());
		Random random = parent.owner.getRNG();
		LivingDebuff.addLivingDebuff(parent.owner,new LivingState(Debuffs.antiFallDamage,10,true));
		LivingDebuff.addLivingDebuff(parent.owner, new LivingState(Debuffs.kalaidoscope,10,true));
		parent.owner.playSound("mob.wither.shoot", 0.5F, 1.8F / (parent.owner.getRNG().nextFloat() * 0.4F + 1.2F));
		double newposY = parent.target.posY + random.nextInt(4)+12;
		//ep.posY = newposY;
		parent.owner.motionY = 0.8;


		double disX = parent.target.posX-parent.owner.posX;
		double disZ =parent.target.posZ-parent.owner.posZ;
		double distance = Math.sqrt(Math.pow(disX, 2)+Math.pow(disZ,2));

		parent.owner.motionX = (disX*0.08);
		parent.owner.motionZ = (disZ*0.08);


	}

	public class SkillChargeBlade extends SkillBase{

		@Override
		public void invokeSkill(InvokeSkill parent) {
			parent.owner.playSound("mob.wither.shoot", 0.5F, 1.8F / (parent.owner.getRNG().nextFloat() * 0.4F + 1.2F));
			CauseKnockBack knock = new CauseKnockBack(parent.world, 1.5F);
			knock.setSkillEffectHelper(parent);
			AxisAlignedBB bb = parent.owner.boundingBox.expand(2.0D, 1.0D, 2.0D);
			knock.causeRangeDamage(bb, parent.getDamageSource(), parent.getModifiedAttackDamage(false, 0));
		}
		
	}
	public void doRearBlade(InvokeSkill parent){
		//parent.owner.hurtResistantTime = 5;
		parent.owner.playSound("mob.wither.shoot", 0.5F, 1.8F / (parent.owner.getRNG().nextFloat() * 0.4F + 1.2F));
		CauseKnockBack knock = new CauseKnockBack(parent.world, 1.5F);
		knock.setSkillEffectHelper(parent);
		AxisAlignedBB bb = parent.owner.boundingBox.expand(2.0D, 1.0D, 2.0D);
		knock.causeRangeDamage(bb, parent.getDamageSource(), parent.getModifiedAttackDamage(false, 0));
		//parent.causeRangeDamage(knock, parent.world,bb , parent.getAttackDamage(), DamageSource.causePlayerDamage(parent.owner), false);
		//LivingDebuff.addLivingDebuff(parent.owner, new LivingState(DebuffRegistry.rushBlade,1, false));

		//parent.ownerSkill.moveEntityWithHeading(parent.ownerSkill.moveStrafing,-parent.ownerSkill.moveForward);

		//ep.moveEntityWithHeading(1.0F	, 2.0F);
	}


	public static SkillSword getInstance(){
		if(INSTANCE==null){
			INSTANCE = new SkillSword();
		}
		return INSTANCE;
		
	}
}

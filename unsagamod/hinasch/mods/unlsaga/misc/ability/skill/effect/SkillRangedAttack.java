package hinasch.mods.unlsaga.misc.ability.skill.effect;

import hinasch.lib.RangeDamageHelper;
import net.minecraft.util.AxisAlignedBB;

public class SkillRangedAttack extends SkillMelee{

	protected double rangeVertical;
	protected double rangeHorizontal;
	protected boolean onGroundOnly = false;
	
	public SkillRangedAttack(SkillMelee.Type type,double horizontal,double vertical){
		super(type);
		this.rangeHorizontal = horizontal;
		this.rangeVertical = vertical;
	}
	
	@Override
	public void invokeSkill(InvokeSkill parent) {
		this.hookStart(parent);
		AxisAlignedBB bb = null;
		if(this.getCustomizedBoundingBox(parent)==null){
			bb = parent.owner.boundingBox.expand(rangeHorizontal, rangeVertical, rangeHorizontal);
		}else{
			bb = this.getCustomizedBoundingBox(parent);
		}
		if(this.getCustomizedRangeDamageHelper(parent)!=null){
			this.getCustomizedRangeDamageHelper(parent).causeRangeDamage(bb, parent.getDamageSource(), parent.getModifiedAttackDamage(),onGroundOnly);
		}else{
			RangeDamageHelper.causeDamage(parent.owner.worldObj, null, bb, parent.getDamageSource(), parent.getModifiedAttackDamage(),onGroundOnly);
		}
		
		this.hookEnd(parent);
	}
	
	public RangeDamageHelper getCustomizedRangeDamageHelper(InvokeSkill parent){
		return null;
	}
	
	public AxisAlignedBB getCustomizedBoundingBox(InvokeSkill parent){
		return null;
	}
	
	public void hookStart(InvokeSkill parent){
		
	}
	
	public void hookEnd(InvokeSkill parent){
		
	}

}

package hinasch.mods.unlsaga.misc.util.rangedamage;

import hinasch.lib.RangeDamageHelper;
import hinasch.mods.unlsaga.misc.ability.skill.effect.InvokeSkill;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;

public class CauseExplode extends RangeDamageHelper{

	public InvokeSkill parent;
	
	public CauseExplode(InvokeSkill parent,int amp) {
		super(parent.world);
		this.parent = parent;
		// TODO 自動生成されたコンストラクター・スタブ
	}


	@Override
	public void takeEntityLiving(EntityLivingBase living,DamageSource source){
		if(parent.owner.onGround){
			this.world.createExplosion(source.getEntity(), living.posX, living.posY, living.posZ, 0.6F * (float)parent.getModifiedAttackDamage(false,0)/2, false);
		}else{
			this.world.createExplosion(source.getEntity(), living.posX, living.posY, living.posZ, 0.6F * (float)parent.getModifiedAttackDamage(false,0)/2, true);
		}
		
	}
}

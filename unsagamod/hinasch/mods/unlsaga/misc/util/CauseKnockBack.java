package hinasch.mods.unlsaga.misc.util;

import hinasch.lib.CauseDamageBoundingbox;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class CauseKnockBack extends CauseDamageBoundingbox{

	public double knockbackStr;
	
	public CauseKnockBack(World world,double knock) {
		super(world);
		this.knockbackStr = knock;
		// TODO 自動生成されたコンストラクター・スタブ
	}

	@Override
	public void takeEntityLiving(EntityLivingBase living,DamageSource source){
		living.knockBack(living, 0, 2.0D*knockbackStr, 1.0D*knockbackStr);
	}
}

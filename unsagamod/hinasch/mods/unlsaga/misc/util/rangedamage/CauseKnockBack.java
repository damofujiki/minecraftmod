package hinasch.mods.unlsaga.misc.util.rangedamage;

import hinasch.lib.RangeDamageHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class CauseKnockBack extends RangeDamageHelper{

	public double knockbackStr;
	public float lpdamage;
	
	public CauseKnockBack(World world,double knock) {
		super(world);
		this.knockbackStr = knock;
		// TODO 自動生成されたコンストラクター・スタブ
	}
	
	public void setLPDamage(float f){
		this.lpdamage = f;
	}

	@Override
	public void takeEntityLiving(EntityLivingBase living,DamageSource source){
		living.knockBack(living, 0, 2.0D*knockbackStr, 1.0D*knockbackStr);
	}
}

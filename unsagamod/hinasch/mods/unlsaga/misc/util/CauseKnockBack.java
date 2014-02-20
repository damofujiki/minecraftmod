package hinasch.mods.unlsaga.misc.util;

import hinasch.lib.CauseDamageBoundingbox;
import hinasch.mods.unlsaga.Unsaga;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class CauseKnockBack extends CauseDamageBoundingbox{

	public double knockbackStr;
	public int lpdamage;
	
	public CauseKnockBack(World world,double knock) {
		super(world);
		this.knockbackStr = knock;
		// TODO 自動生成されたコンストラクター・スタブ
	}
	
	public void setLPDamage(int par1){
		this.lpdamage = par1;
	}

	@Override
	public void takeEntityLiving(EntityLivingBase living,DamageSource source){
		living.knockBack(living, 0, 2.0D*knockbackStr, 1.0D*knockbackStr);
		Unsaga.lpHandler.tryHurtLP(living,lpdamage);
	}
}

package hinasch.mods.unlsaga.misc.debuff.state;

import hinasch.mods.unlsaga.entity.projectile.EntityBoulderNew;
import hinasch.mods.unlsaga.entity.projectile.EntitySolutionLiquid;
import hinasch.mods.unlsaga.misc.debuff.Debuff;
import hinasch.mods.unlsaga.misc.debuff.Debuffs;
import hinasch.mods.unlsaga.misc.debuff.livingdebuff.LivingDebuff;
import hinasch.mods.unlsaga.misc.debuff.livingdebuff.LivingState;
import hinasch.mods.unlsaga.misc.debuff.livingdebuff.LivingStateRandomThrow;
import hinasch.mods.unlsagamagic.misc.spell.Spells;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

public class StateRandomThrow extends State{

	protected String soundStr;
	protected int interval;
	
	public StateRandomThrow(int num, String nameEn) {
		super(num, nameEn);
		this.soundStr = "";
		this.interval = 1;
		// TODO 自動生成されたコンストラクター・スタブ
	}

	@Override
	public LivingDebuff init(String[] strs){
		Debuff debuff = Debuffs.getDebuff(Integer.valueOf(strs[0]));
		int remain = Integer.valueOf(strs[1]);
		int shoottick = 0;
		int amp = 0;
		if(strs.length>2){
			shoottick = Integer.valueOf(strs[2]);
			amp = Integer.valueOf(strs[3]);
		}
		return new LivingStateRandomThrow(debuff,remain,shoottick,amp);
	}
	
	public Entity getThrowingEntity(LivingState parent,EntityLivingBase thrower,double dx,double dy,double dz){
		if(this==Debuffs.stoneShower){
			EntityBoulderNew var8 = new EntityBoulderNew(thrower.worldObj, thrower, 1.0F * 2.0F);
			var8.setLocationAndAngles(var8.posX+dx, var8.posY+dy, var8.posZ+dz, var8.rotationYaw, var8.rotationPitch);
			var8.setDamage(Spells.stoneShower.hurtHP+parent.getModifierAttackBuff(thrower));
			return var8;
		}
		if(this==Debuffs.thunderCrap){
			EntitySolutionLiquid liquid = new EntitySolutionLiquid(thrower.worldObj,thrower);
			liquid.setThunderCrap();
			return liquid;
		}
		return null;

	}
	
	public String getSoundString(){
		return this.soundStr;
	}
	
	public StateRandomThrow setSoundString(String par1){
		this.soundStr = par1;
		return this;
	}
	
	public int getInterval(){
		return this.interval;
	}
	
	public StateRandomThrow setInterval(int par1){
		this.interval = par1;
		return this;
	}
}

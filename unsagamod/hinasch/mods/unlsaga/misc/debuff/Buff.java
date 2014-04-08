package hinasch.mods.unlsaga.misc.debuff;

import com.hinasch.lib.StaticWords;

import hinasch.mods.unlsaga.misc.debuff.livingdebuff.LivingBuff;
import hinasch.mods.unlsaga.misc.debuff.livingdebuff.LivingDebuff;

public class Buff extends Debuff{

	
	
	protected Buff(int num, String nameEn) {
		super(num, nameEn);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	
	@Override
	public LivingDebuff init(String[] strs){
		Debuff debuff = Debuffs.getDebuff(Integer.valueOf(strs[0]));
		int remain = Integer.valueOf(strs[1]); 
		int amp = 0;
		if(strs.length>2){
			amp = Integer.valueOf(amp);
		}
		return new LivingBuff(debuff,remain,amp);
	}
	
	@Override
	public int getParticleNumber(){
		if(particle!=-1){
			return particle;
		}
		return StaticWords.getParticleNumber(StaticWords.particleMobSpell);
	}
	
	
	@Override
	public Debuff setParticleNumber(int par1){
		this.particle = par1;
		return (Buff)this;
	}
	
	
}

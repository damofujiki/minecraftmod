package hinasch.mods.unlsaga.misc.debuff;

import hinasch.lib.StaticWords;
import hinasch.mods.unlsaga.misc.debuff.livingdebuff.LivingDebuff;

public class Debuff {

	public final String name;
	protected int particle;

	public final int number;
	
	protected Debuff(int num,String nameEn){
		this.name = nameEn;
		this.number = num;
		this.particle = -1;
		Debuffs.debuffMap.put(num, this);
	}
	public String toString(){
		return this.name;
	}
	
	
	public LivingDebuff init(String[] strs){
		Debuff debuff = Debuffs.getDebuff(Integer.valueOf(strs[0]));
		int remain = Integer.valueOf(strs[1]); 
		return new LivingDebuff(debuff,remain);
	}
	
	public int getParticleNumber(){
		if(particle!=-1){
			return particle;
		}
		return StaticWords.getParticleNumber(StaticWords.particleSpell);
	}
	
	public Debuff setParticleNumber(int par1){
		this.particle = par1;
		return this;
	}
}

package hinasch.mods.unlsaga.misc.debuff.state;

import hinasch.mods.unlsaga.misc.debuff.Debuff;
import hinasch.mods.unlsaga.misc.debuff.Debuffs;
import hinasch.mods.unlsaga.misc.debuff.livingdebuff.LivingDebuff;
import hinasch.mods.unlsaga.misc.debuff.livingdebuff.LivingState;

public class State extends Debuff{


	
	public State(int num, String nameEn) {
		super(num, nameEn);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	
	@Override
	public LivingDebuff init(String[] strs){
		Debuff debuff = Debuffs.getDebuff(Integer.valueOf(strs[0]));
		int remain = Integer.valueOf(strs[1]);
		boolean isOnlyAir = false;
		if(strs.length>2){
			isOnlyAir = (Integer.valueOf(strs[2])==0 ? true :false);
		}
		return new LivingState(debuff,remain,isOnlyAir);
	}
	
	@Override
	public int getParticleNumber(){
		if(particle!=-1){
			return particle;
		}
		return -1;
	}
}

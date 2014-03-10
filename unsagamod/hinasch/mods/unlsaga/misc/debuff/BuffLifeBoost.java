package hinasch.mods.unlsaga.misc.debuff;

import hinasch.mods.unlsaga.misc.debuff.livingdebuff.LivingBuffLifeBoost;
import hinasch.mods.unlsaga.misc.debuff.livingdebuff.LivingDebuff;

public class BuffLifeBoost extends Buff{

	protected BuffLifeBoost(int num, String nameEn) {
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
		return new LivingBuffLifeBoost(debuff,remain,amp);
	}
}

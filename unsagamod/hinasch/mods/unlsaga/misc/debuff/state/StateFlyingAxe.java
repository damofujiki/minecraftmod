package hinasch.mods.unlsaga.misc.debuff.state;

import hinasch.mods.unlsaga.misc.debuff.livingdebuff.LivingDebuff;
import hinasch.mods.unlsaga.misc.debuff.livingdebuff.LivingStateFlyingAxe;

public class StateFlyingAxe extends State{

	public StateFlyingAxe(int num, String nameEn) {
		super(num, nameEn);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	@Override
	public LivingDebuff init(String[] strs){
		int remain = Integer.valueOf(strs[1]);
		int damage = 1;
		if(strs.length<3){
			damage = Integer.valueOf(strs[2]);
		}
		
		LivingStateFlyingAxe output = new LivingStateFlyingAxe(this,remain, damage);
		return output;
	}
}

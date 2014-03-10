package hinasch.mods.unlsaga.misc.debuff.state;

import hinasch.mods.unlsaga.misc.debuff.livingdebuff.LivingDebuff;
import hinasch.mods.unlsaga.misc.debuff.livingdebuff.LivingStateBow;

public class StateBow extends State{

	public StateBow(int num, String nameEn) {
		super(num, nameEn);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	@Override
	public LivingDebuff init(String[] strs){
		int remain = Integer.valueOf(strs[1]);
		int shoottick = 1;
		String tag = "none";
		if(strs.length > 2){
			shoottick = Integer.valueOf(strs[2]);
		}
		if(strs.length > 3){
			tag = strs[3];
		}
		float charge = 1.0F;
		if(strs.length > 4){
			charge = Float.valueOf(strs[4]);
		}

		
		LivingStateBow output = new LivingStateBow(this,remain,false,shoottick,tag,charge);
		return output;
	}
}

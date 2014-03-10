package hinasch.mods.unlsaga.misc.debuff.state;

import hinasch.mods.unlsaga.misc.debuff.livingdebuff.LivingDebuff;
import hinasch.mods.unlsaga.misc.debuff.livingdebuff.LivingStateTarget;

public class StateTarget extends State{

	
	public StateTarget(int num, String nameEn) {
		super(num, nameEn);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	@Override
	public LivingDebuff init(String[] strs){
		int remain = Integer.valueOf(strs[1]);
		int entityid = -1;
		if(strs.length<3){
			entityid = Integer.valueOf(strs[2]);
		}
		
		LivingStateTarget output = new LivingStateTarget(this,remain, entityid);
		return output;
	}
	

}

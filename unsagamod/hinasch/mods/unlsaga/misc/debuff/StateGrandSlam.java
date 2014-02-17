package hinasch.mods.unlsaga.misc.debuff;

import hinasch.lib.XYZPos;

public class StateGrandSlam extends State{

	protected StateGrandSlam(int num, String nameEn, String nameJp) {
		super(num, nameEn, nameJp);
		// TODO 自動生成されたコンストラクター・スタブ
	}
	
	
	@Override
	public LivingDebuff init(String[] strs){
		Debuff debuff = DebuffRegistry.getDebuff(Integer.valueOf(strs[0]));
		int remain = Integer.valueOf(strs[1]);
		XYZPos brokenpos = new XYZPos(Integer.valueOf(strs[2]),Integer.valueOf(strs[3]),Integer.valueOf(strs[4]));
		String psv = strs[5];
		return new LivingStateGrandSlam(debuff,remain,false,brokenpos,psv);
	}
}

package hinasch.mods.unlsaga.misc.debuff;

public class StateBow extends State{

	protected StateBow(int num, String nameEn, String nameJp) {
		super(num, nameEn, nameJp);
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

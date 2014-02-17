package hinasch.mods.unlsaga.misc.debuff;

public class State extends Debuff{


	
	protected State(int num, String nameEn, String nameJp) {
		super(num, nameEn, nameJp);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	
	@Override
	public LivingDebuff init(String[] strs){
		Debuff debuff = DebuffRegistry.getDebuff(Integer.valueOf(strs[0]));
		int remain = Integer.valueOf(strs[1]);
		boolean isOnlyAir = false;
		if(strs.length>2){
			isOnlyAir = (Integer.valueOf(strs[2])==0 ? true :false);
		}
		return new LivingState(debuff,remain,isOnlyAir);
	}
}

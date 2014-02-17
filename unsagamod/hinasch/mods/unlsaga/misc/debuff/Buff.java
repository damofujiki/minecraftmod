package hinasch.mods.unlsaga.misc.debuff;

public class Buff extends Debuff{

	
	protected Buff(int num, String nameEn, String nameJp) {
		super(num, nameEn, nameJp);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	
	@Override
	public LivingDebuff init(String[] strs){
		Debuff debuff = DebuffRegistry.getDebuff(Integer.valueOf(strs[0]));
		int remain = Integer.valueOf(strs[1]); 
		int amp = 0;
		if(strs.length>2){
			amp = Integer.valueOf(amp);
		}
		return new LivingBuff(debuff,remain,amp);
	}
}

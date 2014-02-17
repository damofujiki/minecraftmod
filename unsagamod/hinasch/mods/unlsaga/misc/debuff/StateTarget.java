package hinasch.mods.unlsaga.misc.debuff;

public class StateTarget extends State{

	
	protected StateTarget(int num, String nameEn, String nameJp) {
		super(num, nameEn, nameJp);
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

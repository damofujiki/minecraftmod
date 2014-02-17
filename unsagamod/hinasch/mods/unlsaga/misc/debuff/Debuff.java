package hinasch.mods.unlsaga.misc.debuff;

public class Debuff {

	public final String nameEn;
	public final String nameJp;
	public final int number;
	
	protected Debuff(int num,String nameEn,String nameJp){
		this.nameEn = nameEn;
		this.nameJp = nameJp;
		this.number = num;
		DebuffRegistry.debuffMap.put(num, this);
	}
	public String toString(){
		return this.nameEn;
	}
	
	
	public LivingDebuff init(String[] strs){
		Debuff debuff = DebuffRegistry.getDebuff(Integer.valueOf(strs[0]));
		int remain = Integer.valueOf(strs[1]); 
		return new LivingDebuff(debuff,remain);
	}
}

package hinasch.mods.unlsaga.misc.ability;

public class Ability {

	
	public final int number;
	protected final String name;
	protected final String nameJP;
	
	protected int healPoint;
	
	public Ability(int num,String par1,String par2){
		name = par1;
		nameJP = par2;
		this.number = num;
		
		AbilityRegistry.abilityMap.put(num, this);
		
	}
	
	public Ability(int num,String par1,String par2,int par3){
		this(num,par1,par2);
		
		this.healPoint = par3;
	}
	
	public String getName(int en_or_jp){
		if(en_or_jp==0)return name;
		return nameJP;
	}
	
	public String getName(String lang){
		if(lang.equals("ja_JP")){
			return this.getName(1);
		}
		return this.getName(0);
	}
	
	//use Heal Ability
	public int getHealPoint(){
		return this.healPoint;
	}
	
	@Override
	public String toString(){
		return String.valueOf(this.number)+":"+name;
	}
}

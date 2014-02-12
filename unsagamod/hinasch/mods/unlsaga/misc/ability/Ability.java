package hinasch.mods.unlsaga.misc.ability;

public class Ability {

	
	protected final String name;
	protected final String nameJP;
	
	protected int healPoint;
	
	public Ability(int num,String par1,String par2){
		name = par1;
		nameJP = par2;
		
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
	
	//use Heal Ability
	public int getHealPoint(){
		return this.healPoint;
	}
}

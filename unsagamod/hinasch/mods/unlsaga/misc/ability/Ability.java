package hinasch.mods.unlsaga.misc.ability;

public class Ability {

	protected final String name;
	protected final String nameJP;
	
	public Ability(int num,String par1,String par2){
		name = par1;
		nameJP = par2;
		
		AbilityRegistry.abilityMap.put(num, this);
		
	}
	
	public String getName(int en_or_jp){
		if(en_or_jp==0)return name;
		return nameJP;
	}
	
}

package hinasch.mods.unlsagamagic.misc.spell.effect;


public abstract class SpellEffect {

	protected InvokeSpell parentInvoke;
	
	public SpellEffect(){
		
	}
	
	public void doEffect(InvokeSpell spellinvoke){
		this.parentInvoke = spellinvoke;
	}
}

package hinasch.mods.unlsagamagic.misc.spell.effect;

import hinasch.mods.unlsaga.misc.ability.skill.effect.AbstractSkillEffect;

public abstract class SpellBase extends AbstractSkillEffect{


	public SpellBase(){
		
	}

	
	@Override
	public void invoke(Object parent){
		this.invokeSpell((InvokeSpell)parent);
	}
	
	abstract public void invokeSpell(InvokeSpell parent);

}

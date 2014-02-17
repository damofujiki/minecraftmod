package hinasch.mods.unlsagamagic.misc.spell;

import hinasch.lib.HSLibs;
import hinasch.mods.unlsaga.Unsaga;
import hinasch.mods.unlsagamagic.misc.element.UnsagaElement.EnumElement;
import hinasch.mods.unlsagamagic.misc.spell.effect.SpellEffect;

public class Spell {


	public final int damegeItem;
	public final int difficultyDecipher;
	public final int baseProbability;
	public int hurtLP;
	public int hurtHP;
	public final EnumElement element;

	public final int number;
	public final SpellEffect spellEffect;
	
	public final String nameEn;
	public final String nameJp;
	
	public SpellMixTable elementsTable;
	public SpellMixTable elementsAmp;
	public SpellMixTable elementsCost;

	protected Spell(EnumElement element,int num,String nameEn,String nameJp,int difficulty,int damageItem,int baseProb){
		this.number = num;
		this.nameEn = nameEn;
		this.nameJp = nameJp;
		this.damegeItem = damageItem;
		this.baseProbability = baseProb;
		this.difficultyDecipher = difficulty;
		this.element = element;
		this.spellEffect = SpellRegistry.spellEffectNormal;
		
		Unsaga.debug("register Spell "+this.nameJp);
		SpellRegistry.spellMap.put(num, this);
	}
	
	public void setSpellMixElements(SpellMixTable table){
		this.elementsTable = table;
	}
	
	public void setSpellAmplifier(SpellMixTable amp,SpellMixTable cost){
		this.elementsAmp = amp;
		this.elementsCost = cost;
	}

	public SpellMixTable getSpellMixElements(){
		return this.elementsTable;
	}
	
	public SpellMixTable getAmp(){
		return this.elementsTable;
	}
	
	public SpellMixTable getCost(){
		return this.elementsTable;
	}
	
	public String getName(String currentLang) {
		if(currentLang.equals(HSLibs.JPKEY)){
			return this.nameJp;
		}
		return this.nameEn;
	}
}

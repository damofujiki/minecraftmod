package hinasch.mods.unlsagamagic.misc.spell;

import com.hinasch.lib.HSLibs;

import hinasch.mods.unlsaga.Unsaga;
import hinasch.mods.unlsaga.core.FiveElements;
import hinasch.mods.unlsagamagic.misc.spell.effect.SpellBase;

public class Spell {



	public final int damegeItem;
	public final int difficultyDecipher;
	public final int baseProbability;
	public float hurtLP;
	public float hurtHP;
	public final FiveElements.EnumElement element;

	public boolean usePointer;
	public final int number;
	public SpellBase spellEffect;
	
	public final String nameEn;
	public final String nameJp;
	
	public SpellMixTable elementsTable;
	public SpellMixTable elementsAmp;
	public SpellMixTable elementsCost;
	
	protected Class<? extends SpellBase> spellClass;

	protected Spell(FiveElements.EnumElement element,int num,String nameEn,String nameJp,int difficulty,int damageItem,int baseProb){
		this.number = num;
		this.nameEn = nameEn;
		this.nameJp = nameJp;
		this.damegeItem = damageItem;
		this.baseProbability = baseProb;
		this.difficultyDecipher = difficulty;
		this.spellEffect = null;
		this.element = element;
		this.usePointer = false;
		
		Unsaga.debug("register Spell "+this.nameJp);
		Spells.spellMap.put(num, this);
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
	
	public Spell setStrength(float hp,float lp){
		this.hurtHP = hp;
		this.hurtLP = lp;
		return this;
	}
	
	public boolean isUsePointer(){
		return this.usePointer;
	}
	
	public void setUsePointer(boolean par1){
		this.usePointer = par1;
	}
	
	public Spell setSpellEffect(SpellBase par1){
		this.spellEffect = par1;
		return this;
	}
	
	public Spell setSpellClass(Class<? extends SpellBase> spellClass){
		this.spellClass = spellClass;
		return this;
	}
	
	public Class<? extends SpellBase> getSpellClass(){
		return this.spellClass;
	}
	public SpellBase getSpellEffect(){
		return this.spellEffect;
	}
}

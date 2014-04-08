package hinasch.mods.unlsagamagic.misc.spell;

import hinasch.mods.unlsaga.core.FiveElements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SpellBlend extends Spell{


	protected ArrayList<Spell> baseRequire;
	protected HashMap<Spell,SpellMixTable> baseAndRequireMap;
	protected SpellBlend(FiveElements.EnumElement element, int num, String nameEn,
			String nameJp, int damageItem, int baseProb) {
		super(element, num, nameEn, nameJp, 10, damageItem, baseProb);
		this.baseAndRequireMap = new HashMap();
		Spells.blendSet.add(this);
	}

	
	public SpellBlend addRequireBlend(Spell spell,SpellMixTable table){
		this.baseAndRequireMap.put(spell, table);
		return this;
	}
	
	public Map<Spell,SpellMixTable> getRequireMap(){
		return this.baseAndRequireMap;
	}
	
	
	public SpellMixTable getSpellMixElements(){
		return null;
	}
	
	public SpellMixTable getAmp(){
		return null;
	}
	
	public SpellMixTable getCost(){
		return null;
	}
	
	public SpellBlend setStrength(int hp,int lp){
		this.hurtHP = hp;
		this.hurtLP = lp;
		return this;
	}
}

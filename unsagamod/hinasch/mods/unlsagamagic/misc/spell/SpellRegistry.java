package hinasch.mods.unlsagamagic.misc.spell;

import hinasch.mods.unlsagamagic.misc.element.UnsagaElement.EnumElement;
import hinasch.mods.unlsagamagic.misc.spell.effect.SpellEffect;
import hinasch.mods.unlsagamagic.misc.spell.effect.SpellEffectBlend;
import hinasch.mods.unlsagamagic.misc.spell.effect.SpellEffectNormal;

import java.util.HashMap;
import java.util.HashSet;

import com.google.common.collect.Sets;

public class SpellRegistry {

	public static HashMap<Integer,Spell> spellMap = new HashMap();
	
	public static final SpellEffect spellEffectNormal = new SpellEffectNormal();
	public static final SpellEffect spellEffectBlend = new SpellEffectBlend();
	
	public static final Spell fireArrow = new Spell(EnumElement.FIRE,1,"Fire Arrow","炎の矢",10,10,75);
	public static final Spell fireVeil = new Spell(EnumElement.FIRE,2,"Fire Veil","火遁",8,8,75);
	public static final Spell heroism = new Spell(EnumElement.FIRE,3,"Heroism","ヒロイズム",8,8,70);
	public static final Spell cloudCall = new Spell(EnumElement.WATER,10,"Cloud Call","クラウドコール",6,10,70);
	public static final Spell purify = new Spell(EnumElement.WATER,11,"Purify","ピュリファイ",11,10,70);
	public static final Spell waterVeil = new Spell(EnumElement.WATER,12,"Water Veil","水遁",8,8,75);

	public static final Spell stoneVeil = new Spell(EnumElement.EARTH,21,"Earth Veil","土遁",8,8,75);
	public static final Spell boulder = new Spell(EnumElement.EARTH,22,"Stone Barrett","ストーンバレット",8,8,75);
	public static final Spell animalCharm = new Spell(EnumElement.EARTH,23,"Animal Charm","アニマルチャーム",8,8,75);
	public static final Spell missuileGuard = new Spell(EnumElement.WOOD,30,"Missuile Guard","ミサイルガード",12,15,65);
	public static final Spell recycle = new Spell(EnumElement.WOOD,31,"Recycle","リサイクル",13,14,70);
	public static final Spell woodVeil = new Spell(EnumElement.WOOD,32,"Wood Veil","木遁",13,14,70);
	public static final Spell lifeBoost = new Spell(EnumElement.WOOD,33,"Life Boost","ライフブースト",13,14,70);

	public static final Spell overGrowth = new Spell(EnumElement.WOOD,34,"Over Growth","オーバーグロウス",13,14,70);
	public static final Spell metalVeil = new Spell(EnumElement.METAL,40,"Metal Veil","金遁",13,14,70);
	public static final Spell detectGold = new Spell(EnumElement.METAL,41,"Detect Gold","デテクトゴールド",13,14,70);
	public static final Spell magicLock = new Spell(EnumElement.METAL,42,"Magic Lock","マジックロック",13,14,70);
	public static final Spell weakness = new Spell(EnumElement.FORBIDDEN,50,"Weakness","ウィークネス",13,14,70);
	public static final Spell abyss = new Spell(EnumElement.FORBIDDEN,51,"Seal of the Abyss","魔印",13,14,70);
	
	public static final SpellBlend crimsonFlare = new SpellBlend(EnumElement.FIRE,60,"Crimson Flare","クリムゾンフレア",14,70);
	public static final SpellBlend stoneShower = new SpellBlend(EnumElement.EARTH,61,"Stone Shower","ストーンシャワー",14,70);
	public static final SpellBlend detectTreasure = new SpellBlend(EnumElement.METAL,62,"Detect Treasire","デテクトトレジャー",14,70);
	public static final SpellBlend leavesShield = new SpellBlend(EnumElement.WOOD,63,"Leaves Shield","木の葉の盾",14,70);
	public static final SpellBlend iceNine = new SpellBlend(EnumElement.WATER,64,"Ice Nine","アイスナイン",14,70);
	
	public static final HashSet<SpellBlend> blendSet = Sets.newHashSet(crimsonFlare,stoneShower,detectTreasure,leavesShield,iceNine);
	public static int getSize(){
		return spellMap.size();
	}

	public static Spell getSpell(int par1){
		return spellMap.get(par1);
	}
	
	public static void initBlenderData(){
		fireVeil.setSpellMixElements(new SpellMixTable(3,0,0,0,0,0));
		fireVeil.setSpellAmplifier(new SpellMixTable(15,15,-15,0,0,0),new SpellMixTable(15,15,-15,0,0,0));
		fireArrow.setSpellMixElements(new SpellMixTable(1,2,0,0,0,1));
		fireArrow.setSpellAmplifier(new SpellMixTable(10,20,10,-5,5,5),new SpellMixTable(15,25,15,0,10,10));
		heroism.setSpellMixElements(new SpellMixTable(2,2,0,0,1,0));
		heroism.setSpellAmplifier(new SpellMixTable(15,15,0,-10,5,0),new SpellMixTable(15,15,0,-10,5,0));
		crimsonFlare.addRequireBlend(fireArrow, new SpellMixTable(4,1,1,0,1,5));
		waterVeil.setSpellMixElements(new SpellMixTable(0,0,0,3,0,0));
		waterVeil.setSpellAmplifier(new SpellMixTable(-15,0,0,15,15,0),new SpellMixTable(-15,0,0,15,15,0));
		cloudCall.setSpellMixElements(new SpellMixTable(0,1,0,2,1,0));
		cloudCall.setSpellAmplifier(new SpellMixTable(-5,0,5,5,15,0),new SpellMixTable(-5,0,5,5,15,0));	
		purify.setSpellMixElements(new SpellMixTable(0,0,0,2,1,0));
		purify.setSpellAmplifier(new SpellMixTable(-5,5,0,10,15,0),new SpellMixTable(-5,5,0,10,15,0));
		iceNine.addRequireBlend(cloudCall, new SpellMixTable(0,4,0,4,0,4));
		stoneVeil.setSpellMixElements(new SpellMixTable(0,3,0,0,0,0));
		stoneVeil.setSpellAmplifier(new SpellMixTable(0,15,15,-15,0,0),new SpellMixTable(0,15,15,-15,0,0));
		boulder.setSpellMixElements(new SpellMixTable(0,2,1,0,0,1));
		boulder.setSpellAmplifier(new SpellMixTable(5,15,20,0,0,5),new SpellMixTable(10,20,25,5,5,10));
		animalCharm.setSpellMixElements(new SpellMixTable(1,3,0,0,2,0));
		animalCharm.setSpellAmplifier(new SpellMixTable(15,10,10,-15,10,0),new SpellMixTable(15,10,10,-15,10,0));
		stoneShower.addRequireBlend(boulder, new SpellMixTable(0,4,0,0,0,6));
		metalVeil.setSpellMixElements(new SpellMixTable(0,0,3,0,0,0));
		metalVeil.setSpellAmplifier(new SpellMixTable(0,0,15,15,-15,0),new SpellMixTable(0,0,15,15,-15,0));
		detectGold.setSpellMixElements(new SpellMixTable(1,0,2,0,0,0));
		detectGold.setSpellAmplifier(new SpellMixTable(5,5,5,10,-10,0),new SpellMixTable(5,5,5,10,-10,0));
		magicLock.setSpellMixElements(new SpellMixTable(0,0,2,1,0,1));
		magicLock.setSpellAmplifier(new SpellMixTable(0,5,15,20,0,5),new SpellMixTable(5,10,20,25,5,10));
		detectTreasure.addRequireBlend(detectGold, new SpellMixTable(0,0,5,2,0,2));
		woodVeil.setSpellMixElements(new SpellMixTable(0,0,0,0,3,0));
		woodVeil.setSpellAmplifier(new SpellMixTable(15,15,0,0,15,0),new SpellMixTable(15,15,0,0,15,0));
		overGrowth.setSpellMixElements(new SpellMixTable(0,1,0,1,2,0));
		overGrowth.setSpellAmplifier(new SpellMixTable(5,-5,5,0,15,0),new SpellMixTable(5,-5,5,0,15,0));
		lifeBoost.setSpellMixElements(new SpellMixTable(2,0,0,0,2,0));
		lifeBoost.setSpellAmplifier(new SpellMixTable(20,0,-10,0,10,0),new SpellMixTable(20,0,-10,0,10,0));		
		recycle.setSpellMixElements(new SpellMixTable(1,1,1,0,2,0));
		recycle.setSpellAmplifier(new SpellMixTable(15,0,5,0,5,0),new SpellMixTable(15,0,5,0,5,0));	
		missuileGuard.setSpellMixElements(new SpellMixTable(0,2,1,0,2,0));
		missuileGuard.setSpellAmplifier(new SpellMixTable(10,0,15,-5,5,0),new SpellMixTable(10,0,15,-5,5,0));
		weakness.setSpellMixElements(new SpellMixTable(0,0,2,0,0,2));
		weakness.setSpellAmplifier(new SpellMixTable(10,10,20,20,0,10),new SpellMixTable(20,20,30,30,10,20));
		abyss.setSpellMixElements(new SpellMixTable(0,2,0,0,0,2));
		abyss.setSpellAmplifier(new SpellMixTable(10,10,20,20,10,10),new SpellMixTable(20,30,30,10,20,20));	
		leavesShield.addRequireBlend(missuileGuard, new SpellMixTable(0,2,0,2,3,0));
	}

}

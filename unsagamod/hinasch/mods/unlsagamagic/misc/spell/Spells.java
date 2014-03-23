package hinasch.mods.unlsagamagic.misc.spell;

import hinasch.mods.unlsaga.Unsaga;
import hinasch.mods.unlsaga.core.FiveElements;
import hinasch.mods.unlsagamagic.misc.spell.effect.SpellEffectBlend;
import hinasch.mods.unlsagamagic.misc.spell.effect.SpellEffectNormal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import com.google.common.collect.Sets;

public class Spells {

	public static HashMap<Integer,Spell> spellMap = new HashMap();
	
	public static final SpellEffectNormal effectNormal = SpellEffectNormal.getInstance();
	public static final SpellEffectBlend effectBlend = SpellEffectBlend.getInstance();
	
	public static final Spell fireArrow = new Spell(FiveElements.EnumElement.FIRE,1,"Fire Arrow","炎の矢",10,10,75).setSpellEffect(effectNormal.fireArrow).setStrength(5, 1);
	public static final Spell fireVeil = new Spell(FiveElements.EnumElement.FIRE,2,"Fire Veil","火遁",8,8,75).setSpellEffect(effectNormal.elemntVeil);
	public static final Spell heroism = new Spell(FiveElements.EnumElement.FIRE,3,"Heroism","ヒロイズム",8,8,70).setSpellEffect(effectNormal.heroism);
	public static final Spell fireWall = new Spell(FiveElements.EnumElement.FIRE,4,"Fire Wall","ファイアウォール",14,14,70).setSpellEffect(effectNormal.fireWall);
	public static final Spell fireStorm = new Spell(FiveElements.EnumElement.FIRE,5,"Fire Storm","太陽風",18,20,70).setStrength(3, 0.4F).setSpellEffect(effectNormal.fireStorm);
	
	public static final Spell cloudCall = new Spell(FiveElements.EnumElement.WATER,10,"Cloud Call","クラウドコール",6,10,70).setSpellEffect(effectNormal.cloudCall);
	public static final Spell purify = new Spell(FiveElements.EnumElement.WATER,11,"Purify","ピュリファイ",11,10,70).setSpellEffect(effectNormal.purify).setStrength(3, 0);
	public static final Spell waterVeil = new Spell(FiveElements.EnumElement.WATER,12,"Water Veil","水遁",8,8,75).setSpellEffect(effectNormal.elemntVeil);

	public static final Spell stoneVeil = new Spell(FiveElements.EnumElement.EARTH,21,"Earth Veil","土遁",8,8,75).setSpellEffect(effectNormal.elemntVeil);
	public static final Spell boulder = new Spell(FiveElements.EnumElement.EARTH,22,"Stone Barrett","ストーンバレット",10,10,75).setSpellEffect(effectNormal.boulder).setStrength(5, 0);
	
	
	public static final Spell animalCharm = new Spell(FiveElements.EnumElement.EARTH,23,"Animal Charm","アニマルチャーム",8,9,75).setSpellEffect(effectNormal.animalCharm);
	public static final Spell detectAnimal = new Spell(FiveElements.EnumElement.EARTH,24,"Detect Animal","デテクトアニマル",8,8,75).setSpellEffect(effectNormal.detectAnimal);
	public static final Spell buildUp = new Spell(FiveElements.EnumElement.EARTH,25,"Build Up","ビルドアップ",8,8,75).setSpellEffect(effectNormal.buildUp);
	
	public static final Spell missuileGuard = new Spell(FiveElements.EnumElement.WOOD,30,"Missuile Guard","ミサイルガード",14,15,65).setSpellEffect(effectNormal.missuileGuard);
	public static final Spell recycle = new Spell(FiveElements.EnumElement.WOOD,31,"Recycle","リサイクル",15,14,70).setSpellEffect(effectNormal.recycle);
	public static final Spell woodVeil = new Spell(FiveElements.EnumElement.WOOD,32,"Wood Veil","木遁",8,8,75).setSpellEffect(effectNormal.elemntVeil);
	public static final Spell lifeBoost = new Spell(FiveElements.EnumElement.WOOD,33,"Life Boost","ライフブースト",13,14,70).setSpellEffect(effectNormal.lifeBoost);
	public static final Spell callThunder = new Spell(FiveElements.EnumElement.WOOD,35,"Call Thunder","召雷",13,14,70).setSpellEffect(effectNormal.callThunder);
	public static final Spell meditation = new Spell(FiveElements.EnumElement.WOOD,36,"Meditation","メディテーション",13,14,70).setSpellEffect(effectNormal.meditation).setStrength(3, 0);
	
	public static final Spell overGrowth = new Spell(FiveElements.EnumElement.WOOD,34,"Over Growth","オーバーグロウス",13,14,70).setSpellEffect(effectNormal.overGrowth);
	public static final Spell metalVeil = new Spell(FiveElements.EnumElement.METAL,40,"Metal Veil","金遁",8,8,75).setSpellEffect(effectNormal.elemntVeil);
	public static final Spell detectGold = new Spell(FiveElements.EnumElement.METAL,41,"Detect Gold","デテクトゴールド",12,12,70).setSpellEffect(effectNormal.detectGold);
	//public static final Spell armorBless = new Spell(EnumElement.METAL,42,"Armor Blessing","アーマーブレス",13,14,70);
	//public static final Spell weaponBless = new Spell(EnumElement.METAL,43,"Weapon Blessing","ウエポンブレス",13,14,70);
	
	public static final Spell magicLock = new Spell(FiveElements.EnumElement.METAL,42,"Magic Lock","マジックロック",10,10,72).setSpellEffect(effectNormal.magicLock);
	
	public static final Spell weakness = new Spell(FiveElements.EnumElement.FORBIDDEN,50,"Weakness","ウィークネス",10,10,70).setSpellEffect(effectNormal.weakness);
	public static final Spell abyss = new Spell(FiveElements.EnumElement.FORBIDDEN,51,"Seal of the Abyss","魔印",12,12,70).setSpellEffect(effectNormal.abyss);
	public static final Spell detectBlood = new Spell(FiveElements.EnumElement.FORBIDDEN,52,"Detect Blood","デテクトブラッド",10,8,75).setSpellEffect(effectNormal.detectBlood);
	
	
	
	public static final SpellBlend crimsonFlare = (SpellBlend) new SpellBlend(FiveElements.EnumElement.FIRE,60,"Crimson Flare","クリムゾンフレア",25,70).setStrength(5, 1).setSpellEffect(effectBlend.crimsonFlare);
	public static final SpellBlend stoneShower = (SpellBlend)new SpellBlend(FiveElements.EnumElement.EARTH,61,"Stone Shower","ストーンシャワー",25,70).setSpellEffect(effectBlend.stoneShower).setStrength(5, 1);
	public static final SpellBlend detectTreasure = (SpellBlend)new SpellBlend(FiveElements.EnumElement.METAL,62,"Detect Treasire","デテクトトレジャー",15,70).setSpellEffect(effectBlend.detectTreasure);
	public static final SpellBlend leavesShield = (SpellBlend)new SpellBlend(FiveElements.EnumElement.WOOD,63,"Leaves Shield","木の葉の盾",15,70).setSpellEffect(effectBlend.leavesShield);
	public static final SpellBlend iceNine = (SpellBlend)new SpellBlend(FiveElements.EnumElement.WATER,64,"Ice Nine","アイスナイン",15,70).setSpellEffect(effectBlend.iceNine);
	public static final SpellBlend touchGold = (SpellBlend)new SpellBlend(FiveElements.EnumElement.METAL,65,"Gold Finger","ゴールドフィンガー",20,70).setSpellEffect(effectBlend.goldFinger);
	public static final SpellBlend thunderCrap = (SpellBlend)new SpellBlend(FiveElements.EnumElement.WATER,66,"Thunder crap","サンダークラップ",20,70).setSpellEffect(effectBlend.thudnerCrap).setStrength(5, 1);
	public static final SpellBlend reflesh = (SpellBlend)new SpellBlend(FiveElements.EnumElement.WATER,67,"Reflesh","リフレッシュ",20,70).setSpellEffect(effectBlend.reflesh).setStrength(4, 0);
	
	public static final HashSet<SpellBlend> blendSet = Sets.newHashSet(crimsonFlare,stoneShower,detectTreasure,leavesShield,iceNine,touchGold,thunderCrap);

	
	public static List<Spell> getValidSpells(){
		List<Spell> list = new ArrayList();
		for(Spell spell:spellMap.values()){
			if(!(spell instanceof SpellBlend)){
				list.add(spell);
			}
			
		}
		
		return list;
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
//		armorBless.setSpellMixElements(new SpellMixTable(0,2,2,1,0,0));
//		armorBless.setSpellAmplifier(new SpellMixTable(-5,10,20,5,-5,0),new SpellMixTable(-5,10,20,5,-5,0));
//		weaponBless.setSpellMixElements(new SpellMixTable(0,2,2,1,0,0));
//		weaponBless.setSpellAmplifier(new SpellMixTable(-5,10,20,5,-5,0),new SpellMixTable(-5,10,20,5,-5,0));
		touchGold.addRequireBlend(detectGold, new SpellMixTable(0,0,5,0,0,4));
		touchGold.addRequireBlend(magicLock, new SpellMixTable(2,0,4,0,0,3));
		fireWall.setSpellMixElements(new SpellMixTable(3,0,1,1,1,0));
		fireWall.setSpellAmplifier(new SpellMixTable(15,10,-10,10,5,0),new SpellMixTable(15,10,-10,10,5,0));
	}

	public static void init() {
		initBlenderData();
		fireWall.setUsePointer(true);
		if(Unsaga.debug.get()){
			for(Spell spell:spellMap.values()){
				if(spell.getSpellMixElements()==null){
					Unsaga.debug(spell.nameJp+"の合成五行値がありません");
				}
			}
		}

	}

}

package hinasch.mods.unlsaga.misc.ability;

import hinasch.mods.unlsaga.core.init.UnsagaMaterial;
import hinasch.mods.unlsaga.core.init.UnsagaMaterials;
import hinasch.mods.unlsaga.misc.ability.skill.Skill;
import hinasch.mods.unlsaga.misc.ability.skill.effect.SkillAxe;
import hinasch.mods.unlsaga.misc.ability.skill.effect.SkillBow;
import hinasch.mods.unlsaga.misc.ability.skill.effect.SkillSpear;
import hinasch.mods.unlsaga.misc.ability.skill.effect.SkillStaff;
import hinasch.mods.unlsaga.misc.ability.skill.effect.SkillSword;
import hinasch.mods.unlsaga.misc.util.DamageHelper;
import hinasch.mods.unlsaga.misc.util.EnumUnsagaTools;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.minecraft.potion.Potion;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

public class AbilityRegistry {

	public static AbilityRegistry instance;
	public static HashMap<Integer,Ability> abilityMap = new HashMap();
	

	
	protected HashMap<String,List<Ability>> normalAbilityMap;
	protected HashMap<String,List<Ability>> inheritAbilityMap;
	
	public final static SkillAxe skillAxe = SkillAxe.getInstance();
	public final static SkillStaff skillStaff = SkillStaff.getInstance();
	public final static SkillSpear skillSpear = SkillSpear.getInstance();
	public final static SkillSword skillSword = SkillSword.getInstance();
	public final static SkillBow skillBow = SkillBow.getInstance();

	
	public static final Ability healDown5 = new Ability(0,"Heal -5","回復力 -5",-1);
	public static final Ability healUp5 = new Ability(1,"Heal +5","回復力 +5",1);
	public static final Ability supportFire = new Ability(2,"Support Fire","火行サポート");
	public static final Ability supportWater = new Ability(3,"Support Water","水行サポート");
	public static final Ability supportEarth = new Ability(4,"Support Earth","土行サポート");
	public static final Ability supportMetal = new Ability(5,"Support Metal","金行サポート");
	public static final Ability supportWood = new Ability(6,"Support Wood","木行サポート");
	public static final Ability fire = new Ability(7,"Spell Fire","火行術");
	public static final Ability water = new Ability(8,"Spell Water","水行術");
	public static final Ability earth = new Ability(9,"Spell Earth","土行術");
	public static final Ability metal = new Ability(10,"Spell Metal","金行術");
	public static final Ability wood = new Ability(11,"Spell Wood","木行術");
	public static final Ability healUp10 = new Ability(12,"Heal +10","回復力 +10",2);
	public static final Ability healDown10 = new Ability(13,"Heal -10","回復力 -10",-2);
	public static final Ability lifeGuard = new Ability(14,"Life Guard","ライフ防御");
	public static final Ability fireProtection = new Ability(15,"Fire Protection","熱防御");
	public static final Ability antiPoison = new Ability(16,"Anti Poison","毒防御");
	public static final Ability skillGuard = new Ability(17,"Anti Debuff:Skill","保護 技");
	public static final Ability magicGuard = new Ability(18,"Anti Debuff:Magic","保護 魔");
	public static final Ability antiDebuff = new Ability(19,"Anti Debuff","状態防御");
	public static final Ability projectileProtection = new Ability(20,"Projectile Protection","射突防御");
	public static final Ability blastProtection = new Ability(21,"Blast Protection","爆風防御");
	public static final Ability unlock = new Ability(21,"Unlock","鍵開け");
	public static final Ability defuse = new Ability(22,"Defuse","罠外し");
	public static final Ability divination = new Ability(23,"Divination","占い");
	public static final Ability healDown20 = new Ability(24,"Heal -20","回復力 -20",-4);
	public static final Ability healDown25 = new Ability(25,"Heal -25","回復力 -25",-5);
	public static final Ability healDown15 = new Ability(26,"Heal -15","回復力 -15",-3);
	public static final Ability dummy = new Ability(27,"","");
	public static final Ability forbidden = new Ability(28,"Spell Forbidden","禁行術");
	public static final Ability supportForbidden = new Ability(29,"Support Forbidden","禁行サポート");
	public static final Ability antiSleep = new Ability(30,"Anti Paralyze","マヒ防御");
	public static final Ability antiBlind = new Ability(31,"Anti Blindness","暗闇防御");
	public static final Ability protectionSlash = new Ability(32,"Slash Protection","斬撃防御");
	//public static final Ability protectionProjectile = new Ability(33,"Projectile Protection","射突防御");
	public static final Ability bodyGuard = new Ability(34,"Anti Debuff:Body","保護 体");
	public static final Ability antiWither = new Ability(35,"Anti Wither","ウィザー耐性");
	public static final Ability superHealing = new Ability(36,"Super Healing","超回復");
	public static final Ability powerGuard = new Ability(37,"Power Guard","保護 力");

	
	public static final Skill kaleidoscope = new Skill(100,"Bopeep","変幻自在",20,6,DamageHelper.Type.SWORD,5).setSkillEffect(skillSword.kaleidoScope);
	public static final Skill slash = new Skill(101,"Slash","払い抜け",5,3,DamageHelper.Type.SWORD,3).setSkillEffect(null);
	public static final Skill smash = new Skill(102,"Smash","スマッシュ",15,3,DamageHelper.Type.SWORD,8).setSkillEffect(skillSword.smash);
	public static final Skill roundabout = new Skill(103,"Roundabout","転",0,0,DamageHelper.Type.SWORD,6).setSkillEffect(skillSword.roundabout);
	public static final Skill chargeBlade = new Skill(104,"Charge Blade","追突剣",0,0,DamageHelper.Type.SWORDPUNCH,8).setSkillEffect(skillSword.chargeBlade);
	public static final Skill gust = new Skill(105,"Gust","逆風の太刀",9,0,DamageHelper.Type.SWORD,5).setSkillEffect(skillSword.gust);
	public static final Skill vandalize = new Skill(106,"Vandalize","ヴァンダライズ",10,0,DamageHelper.Type.SWORD,15).setSkillEffect(skillSword.vandelize);
	public static final Skill tomahawk = new Skill(107,"Tomahawk","トマホーク",5,0,DamageHelper.Type.SWORDPUNCH,15).setSkillEffect(null);
	public static final Skill fujiView = new Skill(108,"Fuji View","富嶽百景",12,0,DamageHelper.Type.SWORDPUNCH,15).setSkillEffect(skillAxe.fujiView);
	public static final Skill skyDrive = new Skill(109,"Skydrive","スカイドライブ",10,0,DamageHelper.Type.SWORDPUNCH,15).setSkillEffect(skillAxe.skyDrive);
	public static final Skill woodBreakerPhoenix = new Skill(111,"Wood Breaker Phoenix","マキ割りフェニックス",10,0,DamageHelper.Type.SWORDPUNCH,15).setSkillEffect(skillAxe.woodBreaker);
	public static final Skill woodChopper = new Skill(110,"Wood Chopper","大木断",5,0,DamageHelper.Type.SWORDPUNCH,15).setSkillEffect(skillAxe.woodChopper);
	public static final Skill aiming = new Skill(120,"Aiming","エイミング",10,2,DamageHelper.Type.SPEAR,15).setSkillEffect(skillSpear.aiming);
	public static final Skill acupuncture = new Skill(121,"Acupuncture","独妙点穴",20,2,DamageHelper.Type.SPEAR,25).setSkillEffect(skillSpear.acupuncture);
	public static final Skill swing = new Skill(122,"Swing","スウィング",-10,2,DamageHelper.Type.SWORDPUNCH,15).setSkillEffect(skillSpear.swing);;
	public static final Skill grassHopper = new Skill(123,"Grass Hopper","草伏せ",-50,0,DamageHelper.Type.SPEAR,5).setSkillEffect(skillSpear.grassHopper);;
	public static final Skill earthDragon = new Skill(133,"Earth Dragon","土竜撃",-13,0,DamageHelper.Type.PUNCH,5).setSkillEffect(skillStaff.earthDragon);
	public static final Skill skullCrash = new Skill(134,"Skull Crash","スカルクラッシュ",1,0,DamageHelper.Type.PUNCH,5).setSkillEffect(skillStaff.skullCrusher);
	public static final Skill pulvorizer = new Skill(135,"Pulvorizer","粉砕撃",5,0,DamageHelper.Type.PUNCH,5).setSkillEffect(skillStaff.pulverizer);
	public static final Skill grandSlam = new Skill(136,"Grand Slam","グランドスラム",2,0,DamageHelper.Type.PUNCH,5).setSkillEffect(skillStaff.grandSlam);
	public static final Skill gonger = new Skill(137,"Gonger","どら鳴らし",1,0,DamageHelper.Type.PUNCH,5).setSkillEffect(skillStaff.bellRinger);
	public static final Skill rockCrusher = new Skill(138,"Rockcrusher","削岩撃",1,0,DamageHelper.Type.PUNCH,5).setSkillEffect(skillStaff.rockCrusher);
	
	public static final Skill doubleShot = new Skill(150,"Double Shot","ニ連射",2,2,DamageHelper.Type.SPEAR,5).setSkillEffect(skillBow.multipleShoot);
	public static final Skill tripleShot = new Skill(151,"Triple Shot","三連射",2,2,DamageHelper.Type.SPEAR,9).setSkillEffect(skillBow.multipleShoot);
	public static final Skill zapper = new Skill(152,"Zapper","ザップショット",5,0,DamageHelper.Type.SPEAR,15).setSkillEffect(skillBow.zapper);
	public static final Skill exorcist = new Skill(153,"Exorcist","破魔の矢",5,0,DamageHelper.Type.SPEAR,5).setSkillEffect(skillBow.exorcist);
	public static final Skill shadowStitching = new Skill(154,"Shadow Stitching","影縫い",2,0,DamageHelper.Type.SPEAR,3).setSkillEffect(skillBow.shodowStitch);
	public static final Skill phoenix = new Skill(155,"Phoenix Arrow","フェニックスアロー",2,0,DamageHelper.Type.SPEAR,3).setSkillEffect(skillBow.phoenixArrow);
	public static final Skill arrowRain = new Skill(156,"Arrow Rain","アローレイン",2,0,DamageHelper.Type.SPEAR,3).setSkillEffect(skillBow.shodowStitch);
	
	//TODO 一部の技、攻撃力が増えるものだけでなく下るものもあるようにする
	public static final HashSet<Ability> healDowns = Sets.newHashSet(healDown5,healDown10,healDown15,healDown20,healDown25);
	public static final HashSet<Ability> healUps = Sets.newHashSet(healUp5,healUp10);
	
	public static final HashSet<Skill> requireCoolingSet = Sets.newHashSet(fujiView,vandalize,skyDrive,grandSlam,zapper);
	
	public static Map<Potion,Ability> againstPotionMap = new HashMap();
	
	public static AbilityRegistry getInstance(){
		if(instance==null){
			instance = new AbilityRegistry();
		}
		return instance;
	}
	protected AbilityRegistry(){
		this.inheritAbilityMap = new HashMap();
		this.normalAbilityMap = new HashMap();
		
		againstPotionMap.put(Potion.poison, antiPoison);
		againstPotionMap.put(Potion.wither, antiWither);
		againstPotionMap.put(Potion.blindness, antiBlind);
		
		addInheritAbility(EnumUnsagaTools.SWORD,UnsagaMaterials.dragonHeart,Lists.newArrayList(superHealing));
		addInheritAbility(EnumUnsagaTools.STAFF,UnsagaMaterials.dragonHeart,Lists.newArrayList(superHealing));
		addInheritAbility(EnumUnsagaTools.AXE,UnsagaMaterials.dragonHeart,Lists.newArrayList(superHealing));
		addInheritAbility(EnumUnsagaTools.SPEAR,UnsagaMaterials.dragonHeart,Lists.newArrayList(superHealing));
		addInheritAbility(EnumUnsagaTools.BOW,UnsagaMaterials.dragonHeart,Lists.newArrayList(superHealing));
		
		addInheritAbility(EnumUnsagaTools.ARMOR,UnsagaMaterials.cloth,Lists.newArrayList(healDown5));
		addInheritAbility(EnumUnsagaTools.ARMOR,UnsagaMaterials.categorywood,Lists.newArrayList(healDown5));
		addInheritAbility(EnumUnsagaTools.ARMOR,UnsagaMaterials.stone,Lists.newArrayList(healDown20));
		addInheritAbility(EnumUnsagaTools.ARMOR,UnsagaMaterials.leathers,Lists.newArrayList(healDown5));
		addInheritAbility(EnumUnsagaTools.ARMOR,UnsagaMaterials.bestial,Lists.newArrayList(healDown10));
		addInheritAbility(EnumUnsagaTools.ARMOR,UnsagaMaterials.silver,Lists.newArrayList(healDown15));
		addInheritAbility(EnumUnsagaTools.ARMOR,UnsagaMaterials.metals,Lists.newArrayList(healDown25));
		addInheritAbility(EnumUnsagaTools.ARMOR,UnsagaMaterials.hydra,Lists.newArrayList(healDown10));
		addInheritAbility(EnumUnsagaTools.ARMOR,UnsagaMaterials.fairieSilver,Lists.newArrayList(healDown15));
		addInheritAbility(EnumUnsagaTools.ARMOR,UnsagaMaterials.obsidian,Lists.newArrayList(healDown25));
		addInheritAbility(EnumUnsagaTools.ARMOR,UnsagaMaterials.steels,Lists.newArrayList(healDown25));
		addInheritAbility(EnumUnsagaTools.ARMOR,UnsagaMaterials.liveSilk,Lists.newArrayList(healDown5));
		addInheritAbility(EnumUnsagaTools.ARMOR,UnsagaMaterials.damascus,Lists.newArrayList(healDown25));
		
		addAbility(EnumUnsagaTools.ARMOR,UnsagaMaterials.cloth,Lists.newArrayList(lifeGuard));
		addAbility(EnumUnsagaTools.ARMOR,UnsagaMaterials.liveSilk,Lists.newArrayList(lifeGuard,antiWither));
		addAbility(EnumUnsagaTools.ARMOR,UnsagaMaterials.categorywood,Lists.newArrayList(supportFire,lifeGuard));
		addAbility(EnumUnsagaTools.ARMOR,UnsagaMaterials.meteorite,Lists.newArrayList(blastProtection));
		addAbility(EnumUnsagaTools.ARMOR,UnsagaMaterials.stone,Lists.newArrayList(lifeGuard));
		addAbility(EnumUnsagaTools.ARMOR,UnsagaMaterials.leathers,Lists.newArrayList(lifeGuard));
		addAbility(EnumUnsagaTools.ARMOR,UnsagaMaterials.fur,Lists.newArrayList(lifeGuard));
		addAbility(EnumUnsagaTools.ARMOR,UnsagaMaterials.crocodileLeather,Lists.newArrayList(lifeGuard));
		addAbility(EnumUnsagaTools.ARMOR,UnsagaMaterials.carnelian,Lists.newArrayList(supportFire));
		addAbility(EnumUnsagaTools.ARMOR,UnsagaMaterials.opal,Lists.newArrayList(supportMetal));
		addAbility(EnumUnsagaTools.ARMOR,UnsagaMaterials.topaz,Lists.newArrayList(supportEarth));
		addAbility(EnumUnsagaTools.ARMOR,UnsagaMaterials.ravenite,Lists.newArrayList(supportWater));
		addAbility(EnumUnsagaTools.ARMOR,UnsagaMaterials.lazuli,Lists.newArrayList(supportWood));
		addAbility(EnumUnsagaTools.ARMOR,UnsagaMaterials.silver,Lists.newArrayList(supportWater));
		addAbility(EnumUnsagaTools.ARMOR,UnsagaMaterials.meteoricIron,Lists.newArrayList(blastProtection));
		addAbility(EnumUnsagaTools.ARMOR,UnsagaMaterials.metals,Lists.newArrayList(bodyGuard));
		addAbility(EnumUnsagaTools.ARMOR,UnsagaMaterials.steel1,Lists.newArrayList(bodyGuard));
		addAbility(EnumUnsagaTools.ARMOR,UnsagaMaterials.hydra,Lists.newArrayList(fireProtection,antiPoison));
		addAbility(EnumUnsagaTools.ARMOR,UnsagaMaterials.fairieSilver,Lists.newArrayList(antiDebuff,blastProtection));
		addAbility(EnumUnsagaTools.ARMOR,UnsagaMaterials.damascus,Lists.newArrayList(bodyGuard));
		
		
		addInheritAbility(EnumUnsagaTools.HELMET,UnsagaMaterials.stone,Lists.newArrayList(healDown5));
		addInheritAbility(EnumUnsagaTools.HELMET,UnsagaMaterials.metals,Lists.newArrayList(healDown5));
		addInheritAbility(EnumUnsagaTools.HELMET,UnsagaMaterials.hydra,Lists.newArrayList(healDown5));
		addInheritAbility(EnumUnsagaTools.HELMET,UnsagaMaterials.steels,Lists.newArrayList(healDown5));
		addInheritAbility(EnumUnsagaTools.HELMET,UnsagaMaterials.corundums,Lists.newArrayList(healDown5));
		addInheritAbility(EnumUnsagaTools.HELMET,UnsagaMaterials.obsidian,Lists.newArrayList(healDown5));
		addInheritAbility(EnumUnsagaTools.HELMET,UnsagaMaterials.diamond,Lists.newArrayList(healDown5));
		addInheritAbility(EnumUnsagaTools.HELMET,UnsagaMaterials.damascus,Lists.newArrayList(healDown5));
		
		addAbility(EnumUnsagaTools.HELMET,UnsagaMaterials.cloth,Lists.newArrayList(supportFire));
		addAbility(EnumUnsagaTools.HELMET,UnsagaMaterials.liveSilk,Lists.newArrayList(supportFire));
		addAbility(EnumUnsagaTools.HELMET,UnsagaMaterials.fur,Lists.newArrayList(supportMetal));
		addAbility(EnumUnsagaTools.HELMET,UnsagaMaterials.leathers,Lists.newArrayList(supportMetal));
		addAbility(EnumUnsagaTools.HELMET,UnsagaMaterials.stone,Lists.newArrayList(antiBlind,magicGuard));
		addAbility(EnumUnsagaTools.HELMET,UnsagaMaterials.metals,Lists.newArrayList(antiBlind,magicGuard));
		addAbility(EnumUnsagaTools.HELMET,UnsagaMaterials.silver,Lists.newArrayList(supportWater));
		addAbility(EnumUnsagaTools.HELMET,UnsagaMaterials.hydra,Lists.newArrayList(antiPoison,fireProtection));
		addAbility(EnumUnsagaTools.HELMET,UnsagaMaterials.meteoricIron,Lists.newArrayList(antiBlind));
		addAbility(EnumUnsagaTools.HELMET,UnsagaMaterials.corundum1,Lists.newArrayList(fireProtection));
		addAbility(EnumUnsagaTools.HELMET,UnsagaMaterials.corundum2,Lists.newArrayList(defuse,unlock));
		addAbility(EnumUnsagaTools.HELMET,UnsagaMaterials.steels,Lists.newArrayList(antiBlind,magicGuard));
		addAbility(EnumUnsagaTools.HELMET,UnsagaMaterials.fairieSilver,Lists.newArrayList(supportWater,defuse,unlock));
		addAbility(EnumUnsagaTools.HELMET,UnsagaMaterials.obsidian,Lists.newArrayList(antiBlind,magicGuard));
		addAbility(EnumUnsagaTools.HELMET,UnsagaMaterials.diamond,Lists.newArrayList(antiDebuff));
		addAbility(EnumUnsagaTools.HELMET,UnsagaMaterials.damascus,Lists.newArrayList(antiBlind,magicGuard));
		
		addInheritAbility(EnumUnsagaTools.LEGGINS,UnsagaMaterials.copperOre,Lists.newArrayList(dummy));
		addInheritAbility(EnumUnsagaTools.LEGGINS,UnsagaMaterials.ironOre,Lists.newArrayList(dummy));
		addInheritAbility(EnumUnsagaTools.LEGGINS,UnsagaMaterials.stone,Lists.newArrayList(healDown5));
		addInheritAbility(EnumUnsagaTools.LEGGINS,UnsagaMaterials.quartz,Lists.newArrayList(healDown10));
		addInheritAbility(EnumUnsagaTools.LEGGINS,UnsagaMaterials.copper,Lists.newArrayList(healDown10));
		addInheritAbility(EnumUnsagaTools.LEGGINS,UnsagaMaterials.lead,Lists.newArrayList(healDown10));
		addInheritAbility(EnumUnsagaTools.LEGGINS,UnsagaMaterials.meteoricIron,Lists.newArrayList(healDown10));
		addInheritAbility(EnumUnsagaTools.LEGGINS,UnsagaMaterials.hydra,Lists.newArrayList(healDown5));
		addInheritAbility(EnumUnsagaTools.LEGGINS,UnsagaMaterials.obsidian,Lists.newArrayList(healDown10));
		
		addAbility(EnumUnsagaTools.LEGGINS,UnsagaMaterials.metals,Lists.newArrayList(bodyGuard));
		addAbility(EnumUnsagaTools.LEGGINS,UnsagaMaterials.iron,Lists.newArrayList(supportWater,blastProtection));
		addAbility(EnumUnsagaTools.BOOTS,UnsagaMaterials.meteoricIron,Lists.newArrayList(bodyGuard));
		addAbility(EnumUnsagaTools.BOOTS,UnsagaMaterials.hydra,Lists.newArrayList(fireProtection,antiPoison));
		addAbility(EnumUnsagaTools.BOOTS,UnsagaMaterials.obsidian,Lists.newArrayList(skillGuard));
		
		addAbility(EnumUnsagaTools.ACCESSORY,UnsagaMaterials.categorywood,Lists.newArrayList(healUp5,supportFire));
		addAbility(EnumUnsagaTools.ACCESSORY,UnsagaMaterials.bone,Lists.newArrayList(healDown5,supportWood,lifeGuard));
		addAbility(EnumUnsagaTools.ACCESSORY,UnsagaMaterials.stone,Lists.newArrayList(healUp5,supportMetal));
		addAbility(EnumUnsagaTools.ACCESSORY,UnsagaMaterials.bestial,Lists.newArrayList(healUp5));
		addAbility(EnumUnsagaTools.ACCESSORY,UnsagaMaterials.carnelian,Lists.newArrayList(healUp5,fire));
		addAbility(EnumUnsagaTools.ACCESSORY,UnsagaMaterials.opal,Lists.newArrayList(healUp5,metal));
		addAbility(EnumUnsagaTools.ACCESSORY,UnsagaMaterials.topaz,Lists.newArrayList(healUp5,earth));
		addAbility(EnumUnsagaTools.ACCESSORY,UnsagaMaterials.ravenite,Lists.newArrayList(healUp5,water));
		addAbility(EnumUnsagaTools.ACCESSORY,UnsagaMaterials.lazuli,Lists.newArrayList(healUp5,wood));
		addAbility(EnumUnsagaTools.ACCESSORY,UnsagaMaterials.meteorite,Lists.newArrayList(healUp5,supportFire,supportEarth,supportMetal));
		addAbility(EnumUnsagaTools.ACCESSORY,UnsagaMaterials.angelite,Lists.newArrayList(healUp10,supportWood,lifeGuard,divination));
		addAbility(EnumUnsagaTools.ACCESSORY,UnsagaMaterials.demonite,Lists.newArrayList(healDown20,supportForbidden,forbidden));
		addAbility(EnumUnsagaTools.ACCESSORY,UnsagaMaterials.silver,Lists.newArrayList(healUp5,supportWater,defuse,unlock));
		addAbility(EnumUnsagaTools.ACCESSORY,UnsagaMaterials.corundum2,Lists.newArrayList(healUp5,supportWater,water));
		addAbility(EnumUnsagaTools.ACCESSORY,UnsagaMaterials.corundum1,Lists.newArrayList(healUp5,supportFire,fire));
		addAbility(EnumUnsagaTools.ACCESSORY,UnsagaMaterials.obsidian,Lists.newArrayList(healUp5,supportMetal,defuse,unlock));
		addAbility(EnumUnsagaTools.ACCESSORY,UnsagaMaterials.diamond,Lists.newArrayList(healUp5,supportFire,defuse,antiSleep,unlock));
		addAbility(EnumUnsagaTools.ACCESSORY,UnsagaMaterials.metals,Lists.newArrayList(healUp5,supportWater));		
		addAbility(EnumUnsagaTools.ACCESSORY,UnsagaMaterials.steels,Lists.newArrayList(healUp5,supportWater));	
		addAbility(EnumUnsagaTools.ACCESSORY,UnsagaMaterials.meteoricIron,Lists.newArrayList(healUp5,supportMetal,supportWater,supportWood));		
		addAbility(EnumUnsagaTools.ACCESSORY,UnsagaMaterials.fairieSilver,Lists.newArrayList(healUp10,antiWither,defuse,unlock,supportWater));	
		addAbility(EnumUnsagaTools.ACCESSORY,UnsagaMaterials.damascus,Lists.newArrayList(healUp5,antiWither,supportWater,lifeGuard));
		this.registerSkill();
	}
	
	public void registerSkill(){
		//vandalize.setMethod(skillSword.getClass().getMethod("doVandelize", parameterTypes));
		addSkill(EnumUnsagaTools.SWORD,true,newSkillList(vandalize,smash,gust));
		addSkill(EnumUnsagaTools.SWORD,false,newSkillList(kaleidoscope,roundabout,chargeBlade));
		addSkill(EnumUnsagaTools.AXE,false,newSkillList(tomahawk,skyDrive,woodChopper));
		addSkill(EnumUnsagaTools.AXE,true,newSkillList(fujiView,woodBreakerPhoenix,woodChopper));
		addSkill(EnumUnsagaTools.SPEAR,false,newSkillList(swing,grassHopper));
		addSkill(EnumUnsagaTools.SPEAR,true,newSkillList(aiming,acupuncture));
		addSkill(EnumUnsagaTools.BOW,false,newSkillList(doubleShot,tripleShot,shadowStitching));
		addSkill(EnumUnsagaTools.BOW,true,newSkillList(zapper,exorcist,phoenix));
		addSkill(EnumUnsagaTools.STAFF,false,newSkillList(skullCrash,pulvorizer,gonger,rockCrusher));
		addSkill(EnumUnsagaTools.STAFF,true,newSkillList(gonger,grandSlam,earthDragon));
	}
	

	
	public static List<Ability> newSkillList(Skill... skills){
		List<Ability> newlist = new ArrayList();
		for(Skill skill:skills){
			newlist.add((Ability)skill);
		}
		return newlist;
	}
	
	public void addAbility(EnumUnsagaTools category,UnsagaMaterial material,List<Ability> abilityList){
		this.normalAbilityMap.put(category.toString()+"."+material.name,abilityList);
	}
	
	public void addSkill(EnumUnsagaTools category,boolean heavy,List<Ability> abilityList){
		String keyweight = (heavy ? "HEAVY" : "LIGHT");
		this.normalAbilityMap.put(category.toString()+"."+keyweight,abilityList);
	}
	
	public void addInheritAbility(EnumUnsagaTools category,UnsagaMaterial material,List<Ability> abilityList){
		this.inheritAbilityMap.put(category.toString()+"."+material.name,abilityList);
	}
	
	private Optional<List<Ability>> getAbilityList(EnumUnsagaTools category,UnsagaMaterial material){
		if(!this.normalAbilityMap.isEmpty()){
			if(this.normalAbilityMap.get(category.toString()+"."+material.name)!=null){
				return Optional.of(this.normalAbilityMap.get(category.toString()+"."+material.name));
			}
		}
		return Optional.absent();
	}
	
	public Optional<List<Ability>> getSkillList(EnumUnsagaTools category,boolean heavy){
		String keyweight = (heavy ? "HEAVY" : "LIGHT");
		if(!this.normalAbilityMap.isEmpty()){
			if(this.normalAbilityMap.get(category.toString()+"."+keyweight)!=null){
				return Optional.of(this.normalAbilityMap.get(category.toString()+"."+keyweight));
			}
		}
		return Optional.absent();
	}
	
	private Optional<List<Ability>> getInheritAbilityList(EnumUnsagaTools category,UnsagaMaterial material){
		if(!this.inheritAbilityMap.isEmpty()){
			String key = category.toString()+"."+material.name;
			if(this.inheritAbilityMap.get(key)!=null){
				return Optional.of(this.inheritAbilityMap.get(key));
			}
			
		}
		return Optional.absent();
	}
	
	public Optional<List<Ability>> getInheritAbilities(EnumUnsagaTools category,UnsagaMaterial material){
		if(getInheritAbilityList(category,material).isPresent()){
			return getInheritAbilityList(category,material);
		}
		if(material.isChild){
			UnsagaMaterial parent = material.getParentMaterial();
			if(getInheritAbilityList(category,parent).isPresent()){
				return getInheritAbilityList(category,parent);
			}
		}
		return Optional.absent();
	}
	
	public Optional<List<Ability>> getAbilities(EnumUnsagaTools category,UnsagaMaterial material){
		if(getAbilityList(category,material).isPresent()){
			return getAbilityList(category,material);
		}
		if(material.isChild){
			UnsagaMaterial parent = material.getParentMaterial();
			if(getAbilityList(category,parent).isPresent()){
				return getAbilityList(category,parent);
			}
		}
		return Optional.absent();
	}
	
	public boolean hasInherit(EnumUnsagaTools category,UnsagaMaterial material,Ability ability){
		if(getInheritAbilities(category,material).isPresent()){
			return getInheritAbilities(category,material).get().contains(ability);
		}
		return false;
	}
	
	public int getInheritHealAmount(EnumUnsagaTools category,UnsagaMaterial material,Collection<Ability> ability){
		if(getInheritAbilities(category,material).isPresent()){
			//Unsaga.debug(getInheritAbilities(category,material).get().toString());
			int healpoint = 0;
			for(Iterator<Ability> ite=ability.iterator();ite.hasNext();){
				Ability inputAbility = ite.next();
				if(getInheritAbilities(category,material).get().contains(inputAbility)){
					healpoint += inputAbility.healPoint;
					//Unsaga.debug(healpoint);
				}
			}
			return healpoint;
		}
		return 0;
	}
	

	
	public Ability getAbilityFromInt(int par1){
		return this.abilityMap.get(par1);
	}
	
	public static List<Integer> exchangeToInt(List<Ability> input){
		List<Integer> output = new ArrayList();
		for(Ability ab:input){
			output.add(ab.number);
		}
		return output;
	}
	
	public List<Ability> exchangeToAbilities(List<Integer> input){
		List<Ability> output = new ArrayList();
		for(Integer i:input){
			output.add(this.getAbilityFromInt(i));
		}
		return output;
	}
	

}

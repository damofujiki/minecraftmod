package hinasch.mods.unlsaga.misc.debuff;

import java.util.HashMap;

public class DebuffRegistry {

	
	protected static HashMap<Integer,Debuff> debuffMap = new HashMap();
	
	public static final Debuff downSkill = new Debuff(1,"Down Skill","技ダウン");
	public static final Debuff downPhysical = new Debuff(2,"Down Physical","体ダウン");
	public static final State cooling = new State(3,"Cooling","クールダウン中");
	public static final Debuff downMagic = new Debuff(4,"Down Magic","魔ダウン");
	
	public static final Buff fireVeil = new Buff(10,"Fire Veil","火遁");
	public static final Buff waterVeil = new Buff(11,"Water Veil","水遁");
	public static final Buff earthVeil = new Buff(12,"Earth Veil","土遁");
	public static final Buff woodVeil = new Buff(13,"Wood Veil","木遁");
	public static final Buff metalVeil = new Buff(14,"Metal Veil","金遁");
	
	public static final Buff missuileGuard = new Buff(15,"Missuile Guard","ミサイルガード");
	public static final Buff leavesShield = new Buff(16,"Leaves Shield","木の葉盾");
	public static final Buff powerup = new Buff(17,"Power up","力アップ");
	public static final Buff lifeBoost = new Buff(18,"Life Boost","ライフブースト");
	
	//public static final State _antiFallDamage = new State(18,"anti Fall","落下無敵");
	
	
	public static final State spellTarget = new State(100,"Spell Target","術対象");
	public static final StateFlyingAxe flyingAxe = new StateFlyingAxe(101,"SkyDrive","スカイドライブ");
	public static final State kalaidoscope = new State(102,"kaleidoScope","変幻自在");
	public static final State antiFallDamage = new State(103,"anti Fall","落下無敵");
	public static final StateTarget weaponTarget = new StateTarget(104,"Weapon Target","武器対象");
	public static final State rushBlade = new State(105,"Rush Blade","追突剣");
	public static final State roundabout = new State(106,"Roundabout","まろび");
	public static final State setAiming = new State(107,"SetAiming","エイミング中");
	public static final StateBow bowDouble = new StateBow(108,"Double Shot","に連写");
	public static final StateGrandSlam grandSlam = new StateGrandSlam(109,"grandslam","グランドスラム");
	public static final State sleep = new State(110,"sleep","マヒ");
	public static final State gust = new State(111,"HeadWind","逆風の太刀");
	
	public static Debuff getDebuff(int par1){
		return debuffMap.get(par1);
	}
}

package hinasch.mods.unlsaga.misc.debuff;

import java.util.HashMap;

public class DebuffRegistry {

	
	protected static HashMap<Integer,Debuff> debuffMap = new HashMap();
	
	public static final Debuff downSkill = new Debuff(1,"Down Skill","技ダウン");
	public static final Debuff downPhysical = new Debuff(2,"Down Physical","体ダウン");
	public static final Debuff cooling = new Debuff(3,"Cooling","クールダウン中");
	
	public static final Debuff fireVeil = new Debuff(10,"Fire Veil","火遁");
	public static final Debuff waterVeil = new Debuff(11,"Water Veil","水遁");
	public static final Debuff earthVeil = new Debuff(12,"Earth Veil","土遁");
	public static final Debuff woodVeil = new Debuff(13,"Wood Veil","木遁");
	public static final Debuff metalVeil = new Debuff(14,"Metal Veil","金遁");
	
	public static final Buff missuileGuard = new Buff(15,"Missuile Guard","ミサイルガード");
	public static final Buff leavesShield = new Buff(16,"Leaves Shield","木の葉盾");
	public static final Buff powerup = new Buff(17,"Power up","力アップ");
	public static final Buff lifeBoost = new Buff(18,"Life Boost","ライフブースト");
	
	public static final State _antiFallDamage = new State(18,"anti Fall","落下無敵");
	
	
	public static final State spellTarget = new State(100,"Spell Target","術対象");
	public static final State flyingAxe = new State(101,"SkyDrive","スカイドライブ");
	public static final State kalaidoscope = new State(102,"kaleidoScope","変幻自在");
	public static final State antiFallDamage = new State(103,"anti Fall","落下無敵");
	public static final State weaponTarget = new State(104,"Weapon Target","武器対象");
	public static final State rushBlade = new State(105,"Rush Blade","追突剣");
	public static final State roundabout = new State(106,"Roundabout","まろび");
	public static final State setAiming = new State(107,"SetAiming","エイミング中");
	public static final State bowDouble = new State(108,"Double Shot","に連写");
	public static final State grandSlam = new State(109,"grandslam","グランドスラム");
	
	public static Debuff getDebuff(int par1){
		return debuffMap.get(par1);
	}
}

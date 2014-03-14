package hinasch.mods.unlsaga.misc.ability.skill;

import hinasch.mods.unlsaga.misc.ability.Ability;
import hinasch.mods.unlsaga.misc.ability.skill.effect.SkillBase;
import hinasch.mods.unlsaga.misc.util.DamageHelper;

public class Skill extends Ability{

	//public static enum EnumDamageUnsaga {SWORD,PUNCH,SPEAR,SWORDPUNCH};
	public float hurtHp;
	public float hurtLp;
	public int damageWeapon;
	public DamageHelper.Type damageType;
	public SkillBase effect;
	
	public Skill(int num, String par1, String par2,int hp,int lp,DamageHelper.Type type,int damage) {
		super(num, par1, par2);
		this.hurtHp = hp;
		this.hurtLp = lp;
		this.damageType = type;
		this.damageWeapon = damage;
		// TODO 自動生成されたコンストラクター・スタブ
	}
	
	public Skill setSkillEffect(SkillBase par1){
		this.effect = par1;
		return this;
	}
	
	public SkillBase getSkillEffect(){
		return this.effect;
	}

}

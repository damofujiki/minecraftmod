package hinasch.mods.unlsaga.misc.ability.skill;

import hinasch.mods.unlsaga.misc.ability.Ability;

import java.lang.reflect.Method;

public class Skill extends Ability{

	public static enum EnumDamageUnsaga {SWORD,PUNCH,SPEAR,SWORDPUNCH};
	public int hurtHp;
	public int hurtLp;
	public int damageWeapon;
	public EnumDamageUnsaga damageType;
	public Method method;
	
	public Skill(int num, String par1, String par2,int hp,int lp,EnumDamageUnsaga type,int damage) {
		super(num, par1, par2);
		this.hurtHp = hp;
		this.hurtLp = lp;
		this.damageType = type;
		this.damageWeapon = damage;
		// TODO 自動生成されたコンストラクター・スタブ
	}
	
	public void setMethod(Method method){
		this.method = method;
	}

}

package hinasch.mods.unlsaga.misc.ability;

import hinasch.mods.unlsaga.core.init.UnsagaMaterial;
import hinasch.mods.unlsaga.misc.util.EnumUnsagaWeapon;

import java.util.HashMap;
import java.util.List;

import com.google.common.base.Optional;

public class AbilityRegistry {

	public static HashMap<Integer,Ability> abilityMap = new HashMap();
	
	protected HashMap<String,List<Ability>> normalAbilityMap;
	protected HashMap<String,List<Ability>> inheritAbilityMap;
	
	public static final Ability healDown5 = new Ability(0,"Heal -5","回復力 -5");
	public static final Ability healUp5 = new Ability(1,"Heal +5","回復力 +5");
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
	public static final Ability healUp10 = new Ability(12,"Heal +10","回復力 +10");
	public static final Ability healDown10 = new Ability(13,"Heal -10","回復力 -10");
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
	
	public AbilityRegistry(){
		this.inheritAbilityMap = new HashMap();
		this.normalAbilityMap = new HashMap();
		
	}
	
	public void addAbility(EnumUnsagaWeapon category,UnsagaMaterial material,List<Ability> abilityList){
		this.normalAbilityMap.put(category.toString()+"."+material.name,abilityList);
	}
	
	public void addInheritAbility(EnumUnsagaWeapon category,UnsagaMaterial material,List<Ability> abilityList){
		this.inheritAbilityMap.put(category.toString()+"."+material.name,abilityList);
	}
	
	public Optional<List<Ability>> getAbilityList(EnumUnsagaWeapon category,UnsagaMaterial material){
		if(!this.normalAbilityMap.isEmpty()){
			if(this.normalAbilityMap.get(category.toString()+"."+material.name)!=null){
				return Optional.of(this.normalAbilityMap.get(category.toString()+"."+material.name));
			}
		}
		return Optional.absent();
	}
	
	public Optional<List<Ability>> getInheritAbilityList(EnumUnsagaWeapon category,UnsagaMaterial material){
		if(!this.inheritAbilityMap.isEmpty()){
			if(this.inheritAbilityMap.get(category.toString()+"."+material.name)!=null){
				return Optional.of(this.inheritAbilityMap.get(category.toString()+"."+material.name));
			}
		}
		return Optional.absent();
	}
}

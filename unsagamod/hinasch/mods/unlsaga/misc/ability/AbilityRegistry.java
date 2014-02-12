package hinasch.mods.unlsaga.misc.ability;

import hinasch.mods.unlsaga.core.init.MaterialList;
import hinasch.mods.unlsaga.core.init.UnsagaMaterial;
import hinasch.mods.unlsaga.misc.util.EnumUnsagaWeapon;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

public class AbilityRegistry {

	public static HashMap<Integer,Ability> abilityMap = new HashMap();
	
	protected HashMap<String,List<Ability>> normalAbilityMap;
	protected HashMap<String,List<Ability>> inheritAbilityMap;
	
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
	
	public static final HashSet<Ability> healDowns = Sets.newHashSet(healDown5,healDown10,healDown15,healDown20,healDown25);
	public static final HashSet<Ability> healUps = Sets.newHashSet(healUp5,healUp10);
	
	public AbilityRegistry(){
		this.inheritAbilityMap = new HashMap();
		this.normalAbilityMap = new HashMap();
		
		addInheritAbility(EnumUnsagaWeapon.ARMOR,MaterialList.cloth,Lists.newArrayList(healDown5));
		addInheritAbility(EnumUnsagaWeapon.ARMOR,MaterialList.categorywood,Lists.newArrayList(healDown5));
		addInheritAbility(EnumUnsagaWeapon.ARMOR,MaterialList.stone,Lists.newArrayList(healDown20));
		addInheritAbility(EnumUnsagaWeapon.ARMOR,MaterialList.leathers,Lists.newArrayList(healDown5));
		addInheritAbility(EnumUnsagaWeapon.ARMOR,MaterialList.bestial,Lists.newArrayList(healDown10));
		addInheritAbility(EnumUnsagaWeapon.ARMOR,MaterialList.silver,Lists.newArrayList(healDown15));
		addInheritAbility(EnumUnsagaWeapon.ARMOR,MaterialList.metal,Lists.newArrayList(healDown25));
		addInheritAbility(EnumUnsagaWeapon.ARMOR,MaterialList.hydra,Lists.newArrayList(healDown10));
		addInheritAbility(EnumUnsagaWeapon.ARMOR,MaterialList.fairieSilver,Lists.newArrayList(healDown15));
		addInheritAbility(EnumUnsagaWeapon.ARMOR,MaterialList.obsidian,Lists.newArrayList(healDown25));
		addInheritAbility(EnumUnsagaWeapon.ARMOR,MaterialList.steels,Lists.newArrayList(healDown25));
		addInheritAbility(EnumUnsagaWeapon.ARMOR,MaterialList.liveSilk,Lists.newArrayList(healDown5));
		addInheritAbility(EnumUnsagaWeapon.ARMOR,MaterialList.damascus,Lists.newArrayList(healDown25));
		
		addInheritAbility(EnumUnsagaWeapon.HELMET,MaterialList.stone,Lists.newArrayList(healDown5));
		addInheritAbility(EnumUnsagaWeapon.HELMET,MaterialList.metal,Lists.newArrayList(healDown5));
		addInheritAbility(EnumUnsagaWeapon.HELMET,MaterialList.hydra,Lists.newArrayList(healDown5));
		addInheritAbility(EnumUnsagaWeapon.HELMET,MaterialList.steels,Lists.newArrayList(healDown5));
		addInheritAbility(EnumUnsagaWeapon.HELMET,MaterialList.corundums,Lists.newArrayList(healDown5));
		addInheritAbility(EnumUnsagaWeapon.HELMET,MaterialList.obsidian,Lists.newArrayList(healDown5));
		addInheritAbility(EnumUnsagaWeapon.HELMET,MaterialList.diamond,Lists.newArrayList(healDown5));
		addInheritAbility(EnumUnsagaWeapon.HELMET,MaterialList.damascus,Lists.newArrayList(healDown5));
		
		addInheritAbility(EnumUnsagaWeapon.LEGGINS,MaterialList.copperOre,Lists.newArrayList(dummy));
		addInheritAbility(EnumUnsagaWeapon.LEGGINS,MaterialList.ironOre,Lists.newArrayList(dummy));
		addInheritAbility(EnumUnsagaWeapon.LEGGINS,MaterialList.stone,Lists.newArrayList(healDown5));
		addInheritAbility(EnumUnsagaWeapon.LEGGINS,MaterialList.quartz,Lists.newArrayList(healDown10));
		addInheritAbility(EnumUnsagaWeapon.LEGGINS,MaterialList.copper,Lists.newArrayList(healDown10));
		addInheritAbility(EnumUnsagaWeapon.LEGGINS,MaterialList.lead,Lists.newArrayList(healDown10));
		addInheritAbility(EnumUnsagaWeapon.LEGGINS,MaterialList.meteoricIron,Lists.newArrayList(healDown10));
		addInheritAbility(EnumUnsagaWeapon.LEGGINS,MaterialList.hydra,Lists.newArrayList(healDown5));
		addInheritAbility(EnumUnsagaWeapon.LEGGINS,MaterialList.obsidian,Lists.newArrayList(healDown10));
		
		addAbility(EnumUnsagaWeapon.ACCESSORY,MaterialList.categorywood,Lists.newArrayList(healUp5,supportFire));
		addAbility(EnumUnsagaWeapon.ACCESSORY,MaterialList.bone,Lists.newArrayList(healDown5,supportWood,lifeGuard));
		addAbility(EnumUnsagaWeapon.ACCESSORY,MaterialList.stone,Lists.newArrayList(healUp5,supportMetal));
		addAbility(EnumUnsagaWeapon.ACCESSORY,MaterialList.bestial,Lists.newArrayList(healUp5));
		addAbility(EnumUnsagaWeapon.ACCESSORY,MaterialList.carnelian,Lists.newArrayList(healUp5,fire));
		addAbility(EnumUnsagaWeapon.ACCESSORY,MaterialList.opal,Lists.newArrayList(healUp5,metal));
		addAbility(EnumUnsagaWeapon.ACCESSORY,MaterialList.topaz,Lists.newArrayList(healUp5,earth));
		addAbility(EnumUnsagaWeapon.ACCESSORY,MaterialList.ravenite,Lists.newArrayList(healUp5,water));
		addAbility(EnumUnsagaWeapon.ACCESSORY,MaterialList.lazuli,Lists.newArrayList(healUp5,wood));
		addAbility(EnumUnsagaWeapon.ACCESSORY,MaterialList.meteorite,Lists.newArrayList(healUp5,supportFire,supportEarth,supportMetal));
		addAbility(EnumUnsagaWeapon.ACCESSORY,MaterialList.angelite,Lists.newArrayList(healUp10,supportWood,forbidden));
	}
	
	public void addAbility(EnumUnsagaWeapon category,UnsagaMaterial material,List<Ability> abilityList){
		this.normalAbilityMap.put(category.toString()+"."+material.name,abilityList);
	}
	
	public void addInheritAbility(EnumUnsagaWeapon category,UnsagaMaterial material,List<Ability> abilityList){
		this.inheritAbilityMap.put(category.toString()+"."+material.name,abilityList);
	}
	
	protected Optional<List<Ability>> getAbilityList(EnumUnsagaWeapon category,UnsagaMaterial material){
		if(!this.normalAbilityMap.isEmpty()){
			if(this.normalAbilityMap.get(category.toString()+"."+material.name)!=null){
				return Optional.of(this.normalAbilityMap.get(category.toString()+"."+material.name));
			}
		}
		return Optional.absent();
	}
	
	protected Optional<List<Ability>> getInheritAbilityList(EnumUnsagaWeapon category,UnsagaMaterial material){
		if(!this.inheritAbilityMap.isEmpty()){
			if(this.inheritAbilityMap.get(category.toString()+"."+material.name)!=null){
				return Optional.of(this.inheritAbilityMap.get(category.toString()+"."+material.name));
			}
			
		}
		return Optional.absent();
	}
	
	public Optional<List<Ability>> getInheritAbilities(EnumUnsagaWeapon category,UnsagaMaterial material){
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
	
	public Optional<List<Ability>> getAbilities(EnumUnsagaWeapon category,UnsagaMaterial material){
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
	
	public boolean hasInherit(EnumUnsagaWeapon category,UnsagaMaterial material,Ability ability){
		if(getInheritAbilities(category,material).isPresent()){
			return getInheritAbilities(category,material).get().contains(ability);
		}
		return false;
	}
	
	public int getInheritHealAmount(EnumUnsagaWeapon category,UnsagaMaterial material,Collection<Ability> ability){
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
}

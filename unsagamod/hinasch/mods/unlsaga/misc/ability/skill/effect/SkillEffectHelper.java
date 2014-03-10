package hinasch.mods.unlsaga.misc.ability.skill.effect;

import hinasch.lib.RangeDamageHelper;
import hinasch.lib.XYZPos;
import hinasch.mods.unlsaga.Unsaga;
import hinasch.mods.unlsaga.core.init.UnsagaMaterial;
import hinasch.mods.unlsaga.entity.EntityTreasureSlime;
import hinasch.mods.unlsaga.item.IUnsagaMaterial;
import hinasch.mods.unlsaga.misc.ability.AbilityRegistry;
import hinasch.mods.unlsaga.misc.ability.skill.Skill;
import hinasch.mods.unlsaga.misc.debuff.Debuffs;
import hinasch.mods.unlsaga.misc.debuff.livingdebuff.LivingDebuff;
import hinasch.mods.unlsaga.misc.util.DamageHelper;
import hinasch.mods.unlsaga.misc.util.DamageSourceUnsaga;
import hinasch.mods.unlsaga.misc.util.EnumUnsagaTools;
import hinasch.mods.unlsaga.misc.util.HelperUnsagaWeapon;

import java.util.HashMap;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import com.google.common.collect.Maps;

public class SkillEffectHelper {
	
	//未実装

	public static HashMap<Class,Integer> effectiveSpear = Maps.newHashMap();
	public static HashMap<Class,Integer> effectivePunch = Maps.newHashMap();
	public static HashMap<Class,Integer> effectiveSword = Maps.newHashMap();
	public static HashMap<Class,Integer> effectiveAxe = Maps.newHashMap();
	
	
	protected SkillEffect skillEffecter;
	protected int coolingTime;
	protected Object parent;
	public final World world;
	protected final Skill skill;
	public final EntityPlayer owner;
	public final ItemStack weapon;
	public final EnumUnsagaTools category;
	public EntityLivingBase target;
	public XYZPos usepoint;
	public float charge;
	
	public SkillEffectHelper(World world,EntityPlayer skillOwner,Skill skill,ItemStack gainedWeapon){
		this.owner = skillOwner;
		this.skill = skill;
		this.world = world;
		this.weapon = gainedWeapon;
		this.coolingTime = 10;
		
		
		
		if(gainedWeapon.getItem() instanceof IUnsagaMaterial){
			IUnsagaMaterial iu = (IUnsagaMaterial)gainedWeapon.getItem();
			this.category = iu.getCategory();
		}else{
			this.category = null;
		}
		
		switch(this.category){

		case SWORD:
			this.skillEffecter = new SkillSword();
			break;
		case STAFF:
			this.skillEffecter = new SkillStaff();
			break;
		case SPEAR:
			this.skillEffecter = new SkillSpear();
			break;
		case AXE:
			this.skillEffecter = new SkillAxe();
			break;
		case BOW:
			this.skillEffecter = new SkillBow();
			break;
		default:
			this.skillEffecter = new SkillSword();
			break;
			
		}
		Unsaga.debug(this.category);
	}
	
	public void setCharge(float par1){
		this.charge = par1;
	}
	public void setParent(Object par1){
		this.parent = par1;
	}
	
	public void setTarget(EntityLivingBase target){
		this.target = target;
	}
	
	public void setUsePoint(XYZPos xyz){
		this.usepoint = xyz;
	}
	
	public void doSkill(){
	
		if(LivingDebuff.isCooling(owner) && this.requireCooling())return;
		
		if(this.skillEffecter!=null){
			Unsaga.debug("skilleffectet!=の那珂"+this.skillEffecter);
			this.skillEffecter.selector(this);
		}
		if(!this.world.isRemote){
			this.damageWeapon();
		}
		if(this.requireCooling()){
			LivingDebuff.addDebuff(owner, Debuffs.cooling, this.coolingTime);
		}
	}
	
	public void setCoolingTime(int par1){
		this.coolingTime = par1;
	}
	public boolean requireCooling(){
		return AbilityRegistry.requireCoolingSet.contains(this.skill);
	}
	
	public void damageWeapon(){
		this.weapon.damageItem(getDamageItem(), owner);
	}
	
	public int getDamageItem(){
		return this.skill.damageWeapon;
	}
	
	public float getAttackDamage(){
		float modifier = 0;
		if(this.category == EnumUnsagaTools.BOW){
			UnsagaMaterial material = HelperUnsagaWeapon.getMaterial(this.weapon);
			modifier += material.getBowModifier();
		}
		modifier += LivingDebuff.getModifierAttackBuff(owner);
		float base = 0;

		return modifier + base + this.skill.hurtHp;
	}
	
	public float getAttackDamageLP(){
		return this.skill.hurtLp;
	}

	public void playSound(String par1){
		this.owner.playSound(par1, 0.5F, 1.8F / (this.world.rand.nextFloat() * 0.4F + 1.2F));

	}
	
	public String getWitherSound(){
		return "mob.wither.shoot";

	}
	
	public String getExplodeSonud(){
		return "random.explode";
	}
	
	public void playSoundAt(Entity target,String str){
		this.world.playSoundAtEntity(target, getFireBallSound(), 1.0F, 1.0F / (this.world.rand.nextFloat() * 0.4F + 1.2F) + 1.0F * 0.5F);

	}
	
	public void spawnParticle(String par1,Entity target){
		this.world.spawnParticle(par1, (double)target.posX+0.5D, (double)target.posY+1, (double)target.posZ+0.5D, 1.0D, 0.0D, 0.0D);

	}
	
	public void playBowSound(){
		this.world.playSoundAtEntity(owner, "random.bow", 1.0F, 1.0F / (this.world.rand.nextFloat() * 0.4F + 1.2F) + this.charge * 0.5F);

	}
	
	public DamageHelper.Type getDamageType(){
		return this.skill.damageType;
	}
	public String getLargeExplode(){
		return "largeexplode";
	}
	public String getFireBallSound(){
		return "mob.ghast.fireball";
	}
	
	public void addPotion(EntityLivingBase target,int potionID,int time,int amp){
		target.addPotionEffect(new PotionEffect(potionID,time,amp));
	}
	
	public void addPotionChance(int prob,EntityLivingBase target,int potionID,int time,int amp){
		if(this.world.rand.nextInt(100)<prob){
			target.addPotionEffect(new PotionEffect(potionID,time,amp));
		}
		
	}
	
	public Skill getSkill(){
		return this.skill;
	}
	public void causeRangeDamage(RangeDamageHelper parent,World world,AxisAlignedBB bb,float damage,DamageSource ds,boolean isEnemyOnly){
		RangeDamageHelper.causeDamage(parent, world, bb, damage, ds, isEnemyOnly);
	}
	
	public DamageSourceUnsaga getDamageSource(){
		String str = (this.owner instanceof EntityPlayer)? "player" : "mob";
		DamageSourceUnsaga ds = new DamageSourceUnsaga(str,this.owner,this.getAttackDamageLP(),this.getDamageType());
		return ds;
		
	}
	
	public float getModifiedAttackDamage(boolean ischarge,float charge){
		float f = 0;
		if(ischarge){
			charge = MathHelper.clamp_float(charge, 0, 15);
			f = charge/15.0F;
			
		}
		float baseStr = (float)this.owner.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue();
		float apliedStr;
		if(ischarge){
			apliedStr = baseStr  + (baseStr * ((this.getAttackDamage()*f)/30.0F));
		}else{
			apliedStr = baseStr  + (baseStr * (this.getAttackDamage()/30.0F));
		}

		return apliedStr;
	}
	public void attack(Entity living,Entity projectile){
	
		if(living instanceof EntityLivingBase){
			living.attackEntityFrom(this.getDamageSource(), this.getModifiedAttackDamage(false,0));

		}
	}
	
	public void attack(Entity living,Entity projectile,float charge){

		if(living instanceof EntityLivingBase){
			living.attackEntityFrom(this.getDamageSource(), this.getModifiedAttackDamage(true,charge));

		}

		//Unsaga.debug("modifier STR:"+this.getModifiedAttackDamage());
//		float at = attack(this.skill.damageType,this.getAttackDamage(),living);
//		Unsaga.debug("Owner:"+owner.getCommandSenderName()+" Target:"+living.getCommandSenderName()+" Damage:"+at);
//		doAttack(owner, living, projectile, at, charge);
	}
	
	public static float attack(DamageHelper.Type type,float attackdamage,Entity living){
		float at = attackdamage;
		if(type==DamageHelper.Type.PUNCH){
			if(living instanceof EntitySkeleton) at += 3;
			if(living instanceof EntitySlime) at = 0;
			if(living instanceof EntityTreasureSlime) at = 0;
		}
		if(type==DamageHelper.Type.SPEAR){
			if(living instanceof EntitySkeleton) at -= 3;
		}
		if(type==DamageHelper.Type.SWORDPUNCH){
			if(living instanceof EntitySkeleton) at += 1;
			if(living instanceof EntitySlime) at -= 3;
			if(living instanceof EntityTreasureSlime) at -= 3;
		}



		at = MathHelper.clamp_float(at, 0, 100);
		return at;
	}
	
	public static void doAttack(EntityLivingBase attacker,Entity living,Entity projectile,float at,float attackLP){
		if(projectile!=null){
			living.attackEntityFrom(DamageSource.causeThrownDamage(attacker, projectile), at);
			if(living instanceof EntityLivingBase){
				Unsaga.lpHandler.tryHurtLP((EntityLivingBase) living, (int)attackLP);
			}
			
			return;
		}

		if(attacker instanceof EntityPlayer){
			living.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer) attacker), at);
		}else{
			living.attackEntityFrom(DamageSource.causeMobDamage(attacker), at);
		}
		
		if(living instanceof EntityLivingBase){
			Unsaga.lpHandler.tryHurtLP((EntityLivingBase) living, (int) attackLP);
		}
		return;
	}
}

package hinasch.mods.unlsaga.misc.ability.skill.effect;

import hinasch.lib.CauseDamageBoundingbox;
import hinasch.lib.XYZPos;
import hinasch.mods.unlsaga.Unsaga;
import hinasch.mods.unlsaga.core.init.UnsagaMaterial;
import hinasch.mods.unlsaga.entity.EntityTreasureSlime;
import hinasch.mods.unlsaga.misc.ability.AbilityRegistry;
import hinasch.mods.unlsaga.misc.ability.skill.Skill;
import hinasch.mods.unlsaga.misc.ability.skill.Skill.EnumDamageUnsaga;
import hinasch.mods.unlsaga.misc.debuff.DebuffRegistry;
import hinasch.mods.unlsaga.misc.debuff.LivingDebuff;
import hinasch.mods.unlsaga.misc.util.EnumUnsagaWeapon;
import hinasch.mods.unlsaga.misc.util.HelperUnsagaWeapon;
import hinasch.mods.unlsaga.misc.util.IUnsagaMaterial;

import java.util.HashMap;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
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
	public final EntityPlayer ownerSkill;
	public final ItemStack weaponGained;
	public final EnumUnsagaWeapon category;
	public EntityLivingBase target;
	public XYZPos usepoint;
	public float charge;
	
	public SkillEffectHelper(World world,EntityPlayer skillOwner,Skill skill,ItemStack gainedWeapon){
		this.ownerSkill = skillOwner;
		this.skill = skill;
		this.world = world;
		this.weaponGained = gainedWeapon;
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
	
		if(LivingDebuff.isCooling(ownerSkill) && this.requireCooling())return;
		
		if(this.skillEffecter!=null){
			Unsaga.debug("skilleffectet!=の那珂"+this.skillEffecter);
			this.skillEffecter.selector(this);
		}
		if(!this.world.isRemote){
			this.damageWeapon();
		}
		if(this.requireCooling()){
			LivingDebuff.addDebuff(ownerSkill, DebuffRegistry.cooling, this.coolingTime);
		}
	}
	
	public void setCoolingTime(int par1){
		this.coolingTime = par1;
	}
	public boolean requireCooling(){
		return AbilityRegistry.requireCoolingSet.contains(this.skill);
	}
	
	public void damageWeapon(){
		this.weaponGained.damageItem(getDamageItem(), ownerSkill);
	}
	
	public int getDamageItem(){
		return this.skill.damageWeapon;
	}
	
	public int getAttackDamage(){
		int modifier = 0;
		if(this.category == EnumUnsagaWeapon.BOW){
			UnsagaMaterial material = HelperUnsagaWeapon.getMaterial(this.weaponGained);
			modifier += material.getBowModifier();
		}
		modifier += LivingDebuff.getModifierAttackBuff(ownerSkill);
		return modifier + this.skill.hurtHp;
	}
	
	public int getAttackDamageLP(){
		return this.skill.hurtLp;
	}

	public void playSoundServer(String par1){
		this.ownerSkill.playSound(par1, 0.5F, 1.8F / (this.world.rand.nextFloat() * 0.4F + 1.2F));

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
		this.world.playSoundAtEntity(ownerSkill, "random.bow", 1.0F, 1.0F / (this.world.rand.nextFloat() * 0.4F + 1.2F) + this.charge * 0.5F);

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
	public void causeRangeDamage(CauseDamageBoundingbox parent,World world,AxisAlignedBB bb,int damage,DamageSource ds,boolean isEnemyOnly){
		CauseDamageBoundingbox.causeDamage(parent, world, bb, damage, ds, isEnemyOnly);
	}
	
	public void attack(Entity living,Entity projectile){
		int at = this.getAttackDamage();
		if(this.skill.damageType==EnumDamageUnsaga.PUNCH){
			if(living instanceof EntitySkeleton) at += 3;
			if(living instanceof EntitySlime) at = 0;
			if(living instanceof EntityTreasureSlime) at = 0;
		}
		if(this.skill.damageType==EnumDamageUnsaga.SPEAR){
			if(living instanceof EntitySkeleton) at -= 3;
		}
		if(this.skill.damageType==EnumDamageUnsaga.SWORDPUNCH){
			if(living instanceof EntitySkeleton) at += 1;
			if(living instanceof EntitySlime) at -= 3;
			if(living instanceof EntityTreasureSlime) at -= 3;
		}


		if(this.category==EnumUnsagaWeapon.BOW){
			living.attackEntityFrom(DamageSource.causeThrownDamage(ownerSkill, projectile), at);
			if(living instanceof EntityLivingBase){
				Unsaga.lpHandler.tryHurtLP((EntityLivingBase) living, getAttackDamageLP());
			}
			
			return;
		}
		at = MathHelper.clamp_int(at, 0, 100);
		living.attackEntityFrom(DamageSource.causePlayerDamage(ownerSkill), at);
		if(living instanceof EntityLivingBase){
			Unsaga.lpHandler.tryHurtLP((EntityLivingBase) living, getAttackDamageLP());
		}
		return;
	}
}

package hinasch.mods.unlsaga.misc.ability.skill.effect;

import hinasch.lib.VecUtil;
import hinasch.mods.unlsaga.Unsaga;
import hinasch.mods.unlsaga.core.event.ExtendedEntityTag;
import hinasch.mods.unlsaga.item.weapon.ItemBowUnsaga;
import hinasch.mods.unlsaga.misc.ability.AbilityRegistry;
import hinasch.mods.unlsaga.misc.debuff.Debuffs;
import hinasch.mods.unlsaga.misc.debuff.livingdebuff.LivingBuff;
import hinasch.mods.unlsaga.misc.debuff.livingdebuff.LivingDebuff;
import hinasch.mods.unlsaga.misc.debuff.livingdebuff.LivingStateBow;

import java.util.List;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;


public class SkillBow extends SkillEffect{

	@Override
	public void selector(SkillEffectHelper helper){
		Unsaga.debug("ここまできてます");
		if(helper.skill==AbilityRegistry.doubleShot)this.doDoubleShots(helper);
		if(helper.skill==AbilityRegistry.tripleShot)this.doDoubleShots(helper);
		if(helper.skill==AbilityRegistry.zapper)this.doZapperOrExorcist(helper);
		if(helper.skill==AbilityRegistry.exorcist)this.doZapperOrExorcist(helper);
	}


	public void doZapperOrExorcist(SkillEffectHelper helper){
		LivingHurtEvent e = (LivingHurtEvent)helper.parent;

		EntityArrow arrow = (EntityArrow)e.source.getSourceOfDamage();

		if(ExtendedEntityTag.hasTag(arrow, ItemBowUnsaga.ARROW_ZAPPER)){
			if(!arrow.worldObj.isRemote){
				arrow.setKnockbackStrength(2);
				arrow.worldObj.createExplosion(helper.owner, arrow.posX, arrow.posY, arrow.posZ, 1.0F+((float)arrow.getDamage())	, false);
			}
		}
		if(ExtendedEntityTag.hasTag(arrow, ItemBowUnsaga.ARROW_EXORCIST)){
			if(e.entity instanceof EntityLivingBase){
				EntityLivingBase el = (EntityLivingBase)e.entity;
				if(el.getCreatureAttribute()==EnumCreatureAttribute.UNDEAD){
					//UtilSkill.tryLPHurt(45, 2, event.entity, ep);
					//DamageSourceUnsaga ds = new DamageSourceUnsaga(null,helper.owner,helper.skill.hurtLp,DamageHelper.Type.MAGIC);
					helper.attack(el,arrow);
					el.setFire(3);


				}
			}
		}
	}

	public boolean doDoubleShots(SkillEffectHelper parent){
		boolean flag = false;
		if(parent.owner.inventory.hasItem(Items.arrow))flag = true;
		if(parent.owner.capabilities.isCreativeMode)flag = true;
		if(!flag)return false;

		Unsaga.debug("doubleショットmadekiter");
		Unsaga.debug("remote:"+parent.owner.worldObj.isRemote);
		parent.playBowSound();

		LivingStateBow state = (LivingStateBow)parent.parent;

		EntityArrow clone = new EntityArrow(parent.world, parent.owner,parent.charge * 1.0F+parent.world.rand.nextFloat());
		clone.setDamage(clone.getDamage()+parent.getAttackDamage());
		//UtilItem.setArrowProp(clone, "LPHurt");
		if(EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, parent.weapon)>0){
			clone.canBePickedUp  = 2;
		}

		if(state.tag.equals("random")){
			Vec3 shaked = VecUtil.getShake(VecUtil.getVecFromEntityMotion(parent.world, clone), parent.world.rand, 20, 41, 5, 11);
			clone.motionX = shaked.xCoord;
			clone.motionY = shaked.yCoord;
			clone.motionZ = shaked.zCoord;

		}else{
			Vec3 shaked = VecUtil.getShake(VecUtil.getVecFromEntityMotion(parent.world, clone), parent.world.rand, 2, 4, 2, 4);
			clone.motionX = shaked.xCoord;
			clone.motionY = shaked.yCoord;
			clone.motionZ = shaked.zCoord;
		}


		if(!parent.world.isRemote){
//			state.shootTick -= 1;
//			if(state.shootTick>0){
//
//
//			}
			if(EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, parent.weapon)==0 && !parent.owner.capabilities.isCreativeMode){
				parent.owner.inventory.consumeInventoryItem(Items.arrow);
			}
			parent.world.spawnEntityInWorld(clone);
			return true;
		}
		return false;
	}
	
	public static void checkShadowStitchOnLivingUpdate(LivingUpdateEvent eventObj){
		//LivingUpdateEvent eventObj = (LivingUpdateEvent)parent.parent;
		AxisAlignedBB bb = eventObj.entityLiving.boundingBox.expand(5D, 5D, 5D);
		List<EntityArrow> arrows = eventObj.entityLiving.worldObj.getEntitiesWithinAABB(EntityArrow.class, bb);
		
		for(EntityArrow arrow:arrows){
			Unsaga.debug("矢を検地");
			Unsaga.debug(arrow.motionX + arrow.motionY + arrow.motionZ);
			if(arrow.shootingEntity!=eventObj.entityLiving && !LivingDebuff.hasDebuff(eventObj.entityLiving, Debuffs.sleep)){
				if(arrow.motionX + arrow.motionY + arrow.motionZ <= 0.00001D && ExtendedEntityTag.hasTag(arrow, ItemBowUnsaga.ARROW_STITCH)){
					Unsaga.debug("カゲヌイがあたりました");
					
					LivingBuff.addDebuff(eventObj.entityLiving, Debuffs.sleep, 15);
					arrow.setDead();
				}
			}

		}
	}
	

}

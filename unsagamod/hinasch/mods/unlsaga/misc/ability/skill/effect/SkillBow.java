package hinasch.mods.unlsaga.misc.ability.skill.effect;

import hinasch.lib.VecUtil;
import hinasch.mods.unlsaga.entity.EntityArrowUnsaga;
import hinasch.mods.unlsaga.misc.ability.AbilityRegistry;
import hinasch.mods.unlsaga.misc.debuff.LivingStateBow;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.Item;
import net.minecraft.util.Vec3;
import net.minecraftforge.event.entity.living.LivingHurtEvent;


public class SkillBow extends SkillEffect{

	@Override
	public void selector(SkillEffectHelper helper){
		
		if(helper.skill==AbilityRegistry.doubleShot)this.doDoubleShots(helper);
		if(helper.skill==AbilityRegistry.tripleShot)this.doDoubleShots(helper);
		if(helper.skill==AbilityRegistry.zapper)this.doZapperOrExorcist(helper);
		if(helper.skill==AbilityRegistry.exorcist)this.doZapperOrExorcist(helper);
	}

	public void doZapperOrExorcist(SkillEffectHelper helper){
		LivingHurtEvent e = (LivingHurtEvent)helper.parent;

		EntityArrowUnsaga arrow = (EntityArrowUnsaga)e.source.getSourceOfDamage();

		if(arrow.isZapper()){
			if(!arrow.worldObj.isRemote){
				arrow.setKnockbackStrength(2);
				arrow.worldObj.createExplosion(helper.ownerSkill, arrow.posX, arrow.posY, arrow.posZ, 1.0F+((float)arrow.getDamage())	, false);
			}
		}
		if(arrow.isExorcist()){
			if(e.entity instanceof EntityLivingBase){
				EntityLivingBase el = (EntityLivingBase)e.entity;
				if(el.getCreatureAttribute()==EnumCreatureAttribute.UNDEAD){
					//UtilSkill.tryLPHurt(45, 2, event.entity, ep);
					el.heal(-helper.getAttackDamage());
					el.setFire(3);


				}
			}
		}
	}

	public static void doDoubleShots(SkillEffectHelper parent){

		parent.playBowSound();

		LivingStateBow state = (LivingStateBow)parent.parent;

		EntityArrow clone = new EntityArrow(parent.world, parent.ownerSkill,parent.charge * 1.0F+parent.world.rand.nextFloat());
		clone.setDamage(clone.getDamage()+parent.getAttackDamage());
		//UtilItem.setArrowProp(clone, "LPHurt");
		if(EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, parent.weaponGained)>0){
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
			state.shootTick -= 1;
			if(state.shootTick>0){

				if(EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, parent.weaponGained)==0){
					parent.ownerSkill.inventory.consumeInventoryItem(Item.arrow.itemID);
				}
			}
			parent.world.spawnEntityInWorld(clone);
		}
	}
}

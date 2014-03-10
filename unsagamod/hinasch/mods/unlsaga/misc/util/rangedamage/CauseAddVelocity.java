package hinasch.mods.unlsaga.misc.util.rangedamage;

import hinasch.lib.RangeDamageHelper;
import hinasch.mods.unlsaga.misc.ability.AbilityRegistry;
import hinasch.mods.unlsaga.misc.ability.skill.effect.SkillEffectHelper;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class CauseAddVelocity extends RangeDamageHelper{

	protected SkillEffectHelper helper;

	public CauseAddVelocity(World world,SkillEffectHelper helper) {
		super(world);
		this.helper = helper;
		// TODO 自動生成されたコンストラクター・スタブ
	}

	@Override
	public void takeEntityLiving(EntityLivingBase living,DamageSource source){
		if(helper.getSkill()==AbilityRegistry.grassHopper){
			if(living.onGround){
				EntityLivingBase attacket = (EntityLivingBase) source.getEntity();
				int j = 1;
				j += EnchantmentHelper.getKnockbackModifier(attacket, (EntityLiving)living);
				living.addVelocity((double)(-MathHelper.sin(attacket.rotationYaw * (float)Math.PI / 180.0F) * (float)j * 0.5F), 0.9D, (double)(MathHelper.cos(attacket.rotationYaw * (float)Math.PI / 180.0F) * (float)j * 0.5F));


				living.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id,120,1));
			}
		}

		if(helper.getSkill()==AbilityRegistry.earthDragon){
			if(living.onGround){
				helper.attack(living, null);
				helper.addPotionChance(45, living, Potion.moveSlowdown.id, 90, 1);

				living.setVelocity(0.0D, 0.0D,0.0D);
			}
			double d0 = helper.owner.posX - living.posX;
			double d1;

			for (d1 = helper.owner.posZ - living.posZ; d0 * d0 + d1 * d1 < 1.0E-4D; d1 = (Math.random() - Math.random()) * 0.01D)
			{
				d0 = (Math.random() - Math.random()) ;
			}
			living.knockBack(living, 0, d0, d1);
			living.motionX *= 2;
			living.motionY *= 2;
			living.motionZ *= 2;
		}
	}
}


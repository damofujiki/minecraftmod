package hinasch.mods.unlsaga.core.event;

import hinasch.mods.unlsaga.entity.EntityArrowUnsaga;
import hinasch.mods.unlsaga.item.weapon.ItemBowUnsaga;
import hinasch.mods.unlsaga.item.weapon.ItemStaffUnsaga;
import hinasch.mods.unlsaga.misc.ability.AbilityRegistry;
import hinasch.mods.unlsaga.misc.ability.HelperAbility;
import hinasch.mods.unlsaga.misc.ability.skill.effect.SkillEffectHelper;
import hinasch.mods.unlsaga.misc.debuff.DebuffRegistry;
import hinasch.mods.unlsaga.misc.debuff.LivingDebuff;
import hinasch.mods.unlsaga.misc.util.UtilItem;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class EventFallDamage {

	
	@ForgeSubscribe
	public void onLivingHurt(LivingHurtEvent e){
		if(e.source.getSourceOfDamage() instanceof EntityArrow){
			this.onArrowHitEvent(e);
		}
		EntityLivingBase living = (EntityLivingBase)e.entityLiving;
		if(e.source.getEntity() instanceof EntityPlayer){
			EntityPlayer player = (EntityPlayer)e.source.getEntity();
			if(UtilItem.hasItemInstance(player, ItemBowUnsaga.class) && e.source.getSourceOfDamage() instanceof EntityArrowUnsaga){
				ItemStack is = player.getHeldItem();
				SkillEffectHelper helper = new SkillEffectHelper(player.worldObj, player, AbilityRegistry.zapper, is);
				helper.setParent(e);
				helper.doSkill();
				
			}
			if(UtilItem.hasItemInstance(player, ItemStaffUnsaga.class) && player.isSneaking() && 
					HelperAbility.hasAbilityFromItemStack(AbilityRegistry.skillGuard, player.getHeldItem())){
				ItemStack is = player.getHeldItem();
				SkillEffectHelper helper = new SkillEffectHelper(player.worldObj, player, AbilityRegistry.skullCrash, is);
				helper.setParent(e);
				helper.doSkill();
			}
		}

		if(LivingDebuff.hasDebuff(living, DebuffRegistry.antiFallDamage) && e.source.damageType.equals("fall")){
			e.ammount = 0;
			living.fallDistance = 0;
			e.setCanceled(true);
			LivingDebuff.removeDebuff(living, DebuffRegistry.antiFallDamage);
			return;
		}
	}
	
	public void onArrowHitEvent(LivingHurtEvent e){
		e.entityLiving.hurtResistantTime = 0;
	}
}

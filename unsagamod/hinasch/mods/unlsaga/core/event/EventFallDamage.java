package hinasch.mods.unlsaga.core.event;

import hinasch.mods.unlsaga.Unsaga;
import hinasch.mods.unlsaga.entity.EntityArrowUnsaga;
import hinasch.mods.unlsaga.item.weapon.ItemBowUnsaga;
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
import net.minecraft.util.MathHelper;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class EventFallDamage {

	
	@ForgeSubscribe
	public void onLivingHurt(LivingHurtEvent e){
		if(e.source.getSourceOfDamage() instanceof EntityArrow){
			this.onArrowHitEvent(e);
		}
		if(e.source.isExplosion()){
			this.onExplodeEvent(e);
		}

		EntityLivingBase living = (EntityLivingBase)e.entityLiving;
		if(e.source.getEntity() instanceof EntityPlayer){
			EntityPlayer player = (EntityPlayer)e.source.getEntity();
			
			if(UtilItem.hasItemInstance(player, ItemBowUnsaga.class) && e.source.getSourceOfDamage() instanceof EntityArrowUnsaga){
				ItemStack is = player.getHeldItem();
				if(HelperAbility.hasAbilityFromItemStack(AbilityRegistry.exorcist,is) ||HelperAbility.hasAbilityFromItemStack(AbilityRegistry.zapper,is) ){
					SkillEffectHelper helper = new SkillEffectHelper(player.worldObj, player, AbilityRegistry.zapper, is);
					helper.setParent(e);
					helper.doSkill();
				}

				
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
	
	private void onExplodeEvent(LivingHurtEvent e) {
		if(e.source.getEntity() instanceof EntityLivingBase){
			EntityLivingBase el = (EntityLivingBase)e.source.getEntity();
			if(el.getHeldItem()!=null){
				if(HelperAbility.hasAbilityFromItemStack(AbilityRegistry.vandalize, el.getHeldItem())){
					e.ammount += AbilityRegistry.vandalize.hurtHp;
					Unsaga.lpHandler.tryHurtLP(e.entityLiving, AbilityRegistry.vandalize.hurtLp);
				}
			}

		}
		if(HelperAbility.hasAbilityPlayer(e.entityLiving, AbilityRegistry.blastProtection)>0){
			e.ammount = e.ammount / (2+HelperAbility.hasAbilityPlayer(e.entityLiving, AbilityRegistry.blastProtection));
			e.ammount = MathHelper.clamp_float(e.ammount, 1.0F, 10.0F);
			
		}
		
	}

	public void onArrowHitEvent(LivingHurtEvent e){
		e.entityLiving.hurtResistantTime = 0;
	}
}

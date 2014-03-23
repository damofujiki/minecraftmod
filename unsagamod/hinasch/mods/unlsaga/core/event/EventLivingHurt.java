package hinasch.mods.unlsaga.core.event;

import hinasch.mods.unlsaga.Unsaga;
import hinasch.mods.unlsaga.core.init.UnsagaConfigs;
import hinasch.mods.unlsaga.entity.projectile.EntityArrowUnsaga;
import hinasch.mods.unlsaga.item.IUnsagaMaterial;
import hinasch.mods.unlsaga.item.weapon.ItemBowUnsaga;
import hinasch.mods.unlsaga.item.weapon.ItemSwordUnsaga;
import hinasch.mods.unlsaga.misc.ability.Ability;
import hinasch.mods.unlsaga.misc.ability.AbilityRegistry;
import hinasch.mods.unlsaga.misc.ability.HelperAbility;
import hinasch.mods.unlsaga.misc.ability.skill.effect.InvokeSkill;
import hinasch.mods.unlsaga.misc.ability.skill.effect.SkillBow;
import hinasch.mods.unlsaga.misc.debuff.Debuffs;
import hinasch.mods.unlsaga.misc.util.ChatMessageHandler;
import hinasch.mods.unlsaga.misc.util.DamageHelper;
import hinasch.mods.unlsaga.misc.util.DamageSourceUnsaga;
import hinasch.mods.unlsaga.misc.util.EnumUnsagaTools;
import hinasch.mods.unlsaga.misc.util.UtilItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class EventLivingHurt {

	
	boolean flagBowAttack;
	protected DamageSourceUnsaga unsagaDamageSource;
	
	@SubscribeEvent
	public void onLivingHurt(LivingHurtEvent e){
		Unsaga.debug(e.source);
		this.unsagaDamageSource = null;
		if(e.source.getEntity() instanceof EntityLivingBase && !(e.source instanceof DamageSourceUnsaga)){
			this.setUnsagaDamage(e, DamageHelper.getUnsagaDamageSource(e.source));
		}
		//this.flagBowAttack = false;
		Debuffs.debuffEventOnLivingHurt(e);
		Ability.abilityEventOnLivingHurt(e);
		ItemSwordUnsaga.hitExplodeByVandalize(e);
		SkillBow.checkArrowOnLivingHurtEvent(e);

		if(e.source.getSourceOfDamage() instanceof EntityArrow || e.source.getSourceOfDamage() instanceof EntityArrowUnsaga){
			this.onArrowHitEvent(e);
		}
		this.onNormalAttackEvent(e);
		
		EntityLivingBase living = (EntityLivingBase)e.entityLiving;

		if(e.source instanceof DamageSourceUnsaga){
			this.unsagaDamageSource = (DamageSourceUnsaga) e.source;
		}
		if(this.unsagaDamageSource!=null){
			this.resultUnsagaDamage(e);
		}


	}


	public void onArrowHitEvent(LivingHurtEvent e){
		if(e.source.getSourceOfDamage() instanceof EntityArrow || e.source.getSourceOfDamage() instanceof EntityArrow){
			if(HelperAbility.hasAbilityLiving(e.entityLiving, AbilityRegistry.projectileProtection)>0){
				e.ammount = e.ammount / (2+HelperAbility.hasAbilityLiving(e.entityLiving, AbilityRegistry.projectileProtection));
				e.ammount = MathHelper.clamp_float(e.ammount, 1.0F, 10.0F);
			}
			
			
			EntityLivingBase living = (EntityLivingBase)e.entityLiving;
			if(e.source.getEntity() instanceof EntityPlayer){
				EntityPlayer player = (EntityPlayer)e.source.getEntity();
				
				if(UtilItem.hasItemInstance(player, ItemBowUnsaga.class) && e.source.getSourceOfDamage() instanceof EntityArrow){
					ItemStack is = player.getHeldItem();
					if(HelperAbility.hasAbilityFromItemStack(AbilityRegistry.exorcist,is) ||HelperAbility.hasAbilityFromItemStack(AbilityRegistry.zapper,is) ){
						InvokeSkill helper = new InvokeSkill(player.worldObj, player, AbilityRegistry.zapper, is);
						helper.setParent(e);
						helper.doSkill();
					}

					
				}

			}
		}
		
		this.setUnsagaDamage(e, DamageHelper.getUnsagaDamageSource(e.source, EnumUnsagaTools.BOW));
		

		e.entityLiving.hurtResistantTime = 0;
	}
	
	public void onNormalAttackEvent(LivingHurtEvent e){
		if(e.source.getEntity() instanceof EntityLivingBase){

			EntityLivingBase attacker = (EntityLivingBase)e.source.getEntity();
			EntityLivingBase damagedEntity = e.entityLiving;
			
//			if(attacker.getHeldItem()!=null && attacker instanceof EntityPlayer){
//				if(HelperAbility.hasAbilityFromItemStack(AbilityRegistry.skullCrash, attacker.getHeldItem())){
//					SkillEffectHelper helper = new SkillEffectHelper(attacker.worldObj,(EntityPlayer) attacker,AbilityRegistry.skullCrash,attacker.getHeldItem());
//					helper.setParent(e);
//					helper.doSkill();
//				}
//			}
			
			Entity sourceent = e.source.getSourceOfDamage();

			ItemStack weapon = attacker.getHeldItem();

			if(weapon!=null && weapon.getItem() instanceof IUnsagaMaterial){
				float baseStr = e.ammount;
				IUnsagaMaterial iu = (IUnsagaMaterial)weapon.getItem();
				//弓関係も実装する
				this.setUnsagaDamage(e, DamageHelper.getUnsagaDamageSource(e.source,iu.getCategory()));

				

			}
		}
	}
	
	public void resultUnsagaDamage(LivingHurtEvent e){
		float lphurt = this.unsagaDamageSource.getStrengthLPHurt();
		float modifier = DamageHelper.getDamageModifierFromType(this.unsagaDamageSource.getUnsagaDamageType(), e.entityLiving, e.ammount);
		float emodifier = DamageHelper.getDamageModifierFromSubType(this.unsagaDamageSource.getSubDamageType(), e.entityLiving, e.ammount);
		e.ammount += modifier;
		e.ammount += emodifier;
		e.ammount = MathHelper.clamp_float(e.ammount, 0.0F, 1000.0F);
		if(UnsagaConfigs.module.isLPEnabled()){
			Unsaga.lpHandler.tryHurtLP(e.entityLiving, lphurt);
		}
		if(Unsaga.debug.get()){
			if(e.source.getEntity() instanceof EntityPlayer){
				EntityPlayer ep = (EntityPlayer) e.source.getEntity();
				ChatMessageHandler.sendChatToPlayer((EntityPlayer) e.source.getEntity(), e.ammount+" Damage!:Attribute>"+this.unsagaDamageSource.getUnsagaDamageType());

				Unsaga.debug(ep.getEntityAttribute(SharedMonsterAttributes.attackDamage).func_111122_c());
			}
		}
	}

	public void setUnsagaDamage(LivingHurtEvent e,DamageSourceUnsaga damagesource){
		if(e.source instanceof DamageSourceUnsaga){
			return;
		}else{
			this.unsagaDamageSource = damagesource;
		}
	}
}

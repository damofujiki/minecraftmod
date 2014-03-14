package hinasch.mods.unlsaga.misc.ability;

import hinasch.mods.unlsaga.Unsaga;
import hinasch.mods.unlsaga.core.event.ExtendedPlayerData;
import hinasch.mods.unlsaga.core.init.UnsagaMaterial;
import hinasch.mods.unlsaga.item.IUnsagaMaterial;
import hinasch.mods.unlsaga.misc.util.HelperUnsagaWeapon;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.MathHelper;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class Ability {


	public final int number;
	protected final String name;
	protected final String nameJP;



	protected int healPoint;

	public Ability(int num,String par1,String par2){
		name = par1;
		nameJP = par2;
		this.number = num;


		AbilityRegistry.abilityMap.put(num, this);

	}

	public Ability(int num,String par1,String par2,int par3){
		this(num,par1,par2);

		this.healPoint = par3;
	}

	public String getName(int en_or_jp){
		if(en_or_jp==0)return name;
		return nameJP;
	}

	public String getName(String lang){
		if(lang==null){
			return this.getName(0);
		}
		if(lang.equals("ja_JP")){
			return this.getName(1);
		}
		return this.getName(0);
	}

	//use Heal Ability
	public int getHealPoint(){
		return this.healPoint;
	}

	@Override
	public String toString(){
		return String.valueOf(this.number)+":"+name;
	}


	//From LivingUpdate
	public static void abilityEventOnLivingUpdate(EntityLivingBase living,AbilityRegistry ar){
		int amountHeal = 0;
		if(!living.isPotionActive(Potion.regeneration)){
			if(HelperAbility.hasAbilityLiving(living, AbilityRegistry.superHealing)>0){
				living.addPotionEffect(new PotionEffect(Potion.regeneration.id,150,0));
			}
		}

		for(Potion potion:AbilityRegistry.againstPotionMap.keySet()){
			if(living.isPotionActive(potion)){
				if(HelperAbility.hasAbilityLiving(living, AbilityRegistry.againstPotionMap.get(potion))>0){
					living.removePotionEffect(potion.id);
				}
			}
		}
		for(Potion potion:Potion.potionTypes){
			if(potion!=null){
				if(potion.isBadEffect() && living.isPotionActive(potion) && HelperAbility.hasAbilityLiving(living, AbilityRegistry.antiDebuff)>0){
					living.removePotionEffect(potion.id);
				}
			}
		}
		
		if(!living.isPotionActive(Potion.fireResistance)){
			if(HelperAbility.hasAbilityLiving(living, AbilityRegistry.fireProtection)>0){
				int amount = HelperAbility.hasAbilityLiving(living, AbilityRegistry.fireProtection);
				living.addPotionEffect(new PotionEffect(Potion.fireResistance.id,150,0));
			}
		}

		if(living instanceof EntityPlayer){
			
			if(ExtendedPlayerData.getData((EntityPlayer) living).isPresent()){
				ExtendedPlayerData pdata = ExtendedPlayerData.getData((EntityPlayer) living).get();

				for(ItemStack is:pdata.getAccessories()){
					if(is!=null){
						if(is.getItem() instanceof IUnsagaMaterial){
							IUnsagaMaterial im = (IUnsagaMaterial)is.getItem();
							UnsagaMaterial material = HelperUnsagaWeapon.getMaterial(is);
							amountHeal += ar.getInheritHealAmount(im.getCategory(), material, AbilityRegistry.healUps);
							amountHeal += ar.getInheritHealAmount(im.getCategory(), material, AbilityRegistry.healDowns);
						}
						if(is.getItem() instanceof IGainAbility){
							HelperAbility helper = new HelperAbility(is,living);
							amountHeal += helper.getHealAmount();
						}
					}
				}

			}

		}
		for(int i=1;i<5;i++){
			ItemStack armor = living.getEquipmentInSlot(i);
			if(armor!=null){
				if(armor.getItem() instanceof IUnsagaMaterial){
					IUnsagaMaterial im = (IUnsagaMaterial)armor.getItem();
					UnsagaMaterial material = HelperUnsagaWeapon.getMaterial(armor);
					amountHeal += ar.getInheritHealAmount(im.getCategory(), material, AbilityRegistry.healUps);
					amountHeal += ar.getInheritHealAmount(im.getCategory(), material, AbilityRegistry.healDowns);
				}
				if(armor.getItem() instanceof IGainAbility){
					HelperAbility helper = new HelperAbility(armor,living);
					amountHeal += helper.getHealAmount();
				}
			}
		}


		if(amountHeal<0){
			if(living.ticksExisted % 20 * 12 == 0){
				if(living instanceof EntityPlayer){
					((EntityPlayer) living).addExhaustion(0.025F * (float)(Math.abs(amountHeal) + 1));
				}
				
			}
		}
		if(amountHeal>0){
			if(living.ticksExisted % 20 * 12 == 0){
				if(living instanceof EntityPlayer){
					if(!((EntityPlayer) living).getFoodStats().needFood()){
						living.heal(amountHeal);
					}
				}else{
					living.heal(amountHeal);
				}


			}
		}
		if(living.ticksExisted % 20 * 12 == 0){
			//Unsaga.debug(amountHeal);
		}
	}

	public static void abilityEventOnLivingHurt(LivingHurtEvent e){
		EntityLivingBase hurtEntity = e.entityLiving;
		Entity attacker = e.source.getEntity();
		
		if(e.source.isExplosion()){
			if(HelperAbility.hasAbilityLiving(e.entityLiving, AbilityRegistry.blastProtection)>0){
				e.ammount = e.ammount / (2+HelperAbility.hasAbilityLiving(e.entityLiving, AbilityRegistry.blastProtection));
				e.ammount = MathHelper.clamp_float(e.ammount, 1.0F, 10.0F);
				Unsaga.debug("爆発防御！");
			}
		}
		
		if(HelperAbility.hasAbilityLiving(hurtEntity, AbilityRegistry.lifeGuard)>0){
			int amount = HelperAbility.hasAbilityLiving(hurtEntity, AbilityRegistry.lifeGuard);
			if(e.entityLiving.getRNG().nextInt(3)==0){
				e.ammount -= (float)amount;
				e.ammount = MathHelper.clamp_float(e.ammount, 1.0F, 100.0F);
				Unsaga.debug("ライフ防御！");
			}
		}
		
		if(e.source.isProjectile()){
			if(HelperAbility.hasAbilityLiving(hurtEntity, AbilityRegistry.projectileProtection)>0){
				e.ammount = e.ammount / (2+HelperAbility.hasAbilityLiving(hurtEntity, AbilityRegistry.projectileProtection));
				e.ammount = MathHelper.clamp_float(e.ammount, 1.0F, 10.0F);
				Unsaga.debug("飛び道具防御！");
			}
		}

	}
	//On LivingDeathEvent
	public static void gainAbilityEventOnLivingDeath(LivingDeathEvent e,EntityLivingBase enemy){
		Unsaga.debug("呼ばれてる");
		EntityPlayer ep = (EntityPlayer)e.source.getEntity();

		if(ExtendedPlayerData.getData(ep).isPresent()){
			ExtendedPlayerData pdata = ExtendedPlayerData.getData(ep).get();
			for(int i=0;i<2;i++){
				ItemStack is = pdata.getAccessory(i);
				if(HelperAbility.canGainAbility(is)){
					Unsaga.debug("アビリティを覚えられる");
					HelperAbility helper = new HelperAbility(is,ep);
					if(!ep.worldObj.isRemote){
						helper.drawChanceToGainAbility(ep.getRNG(), enemy);
					}

				}
			}

		}
		for(ItemStack armor:ep.inventory.armorInventory){
			if(armor!=null){
				if(HelperAbility.canGainAbility(armor)){
					HelperAbility helper = new HelperAbility(armor,ep);
					if(!ep.worldObj.isRemote){
						helper.drawChanceToGainAbility(ep.getRNG(), enemy);
					}
				}
			}
		}
	}
}

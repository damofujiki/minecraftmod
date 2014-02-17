package hinasch.mods.unlsaga.core.event;

import hinasch.mods.unlsaga.Unsaga;
import hinasch.mods.unlsaga.core.init.UnsagaMaterial;
import hinasch.mods.unlsaga.item.weapon.ItemSwordUnsaga;
import hinasch.mods.unlsaga.misc.ability.AbilityRegistry;
import hinasch.mods.unlsaga.misc.ability.HelperAbility;
import hinasch.mods.unlsaga.misc.ability.IGainAbility;
import hinasch.mods.unlsaga.misc.debuff.DebuffRegistry;
import hinasch.mods.unlsaga.misc.debuff.LivingDebuff;
import hinasch.mods.unlsaga.misc.util.CauseKnockBack;
import hinasch.mods.unlsaga.misc.util.HelperUnsagaWeapon;
import hinasch.mods.unlsaga.misc.util.IUnsagaMaterial;
import hinasch.mods.unlsaga.misc.util.UtilItem;

import java.util.EnumSet;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

public class TickHandlerUnsaga implements ITickHandler{

	protected AbilityRegistry ar = Unsaga.abilityRegistry;
	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData) {
		if (type.equals(EnumSet.of(TickType.PLAYER))) {
			onPlayerTick((EntityPlayer) tickData[0]);
		}

	}

	protected void onPlayerTick(EntityPlayer entityPlayer) {
		int amountHeal = 0;

		if (LivingDebuff.hasDebuff(entityPlayer, DebuffRegistry.rushBlade) && UtilItem.hasItemInstance(entityPlayer, ItemSwordUnsaga.class)) {
			entityPlayer.setSneaking(true);
			ItemStack is = entityPlayer.getHeldItem();
			float damage = ((ItemSwordUnsaga)is.getItem()).weapondamage + (float)AbilityRegistry.rearBlade.damageWeapon;
			CauseKnockBack causeknock = new CauseKnockBack(entityPlayer.worldObj,1.0D);
			AxisAlignedBB bb = entityPlayer.boundingBox
					.expand(1.0D, 1.0D, 1.0D);
			causeknock.doCauseDamage(bb, damage, DamageSource.causePlayerDamage(entityPlayer), false);

//			List entlist = entityPlayer.worldObj.getEntitiesWithinAABB(
//					EntityLiving.class, bb);
//			if (!entlist.isEmpty()) {
//				for (Iterator<EntityLiving> ite = entlist.iterator(); ite
//						.hasNext();) {
//					EntityLiving entityliving = ite.next();
//					if (entityliving != null && entityliving != entityPlayer) {
//						int str = 0;
//						entityliving
//						.attackEntityFrom(DamageSource
//								.causePlayerDamage(entityPlayer), 2);
//						entityliving.knockBack(entityPlayer, 0, 2.0D, 1.0D);
//					}
//				}
//			}


		}
		
		if(ExtendedPlayerData.getData(entityPlayer).isPresent()){
			ExtendedPlayerData pdata = ExtendedPlayerData.getData(entityPlayer).get();
			
			for(ItemStack is:pdata.getItemStacks()){
				if(is!=null){
					if(is.getItem() instanceof IUnsagaMaterial){
						IUnsagaMaterial im = (IUnsagaMaterial)is.getItem();
						UnsagaMaterial material = HelperUnsagaWeapon.getMaterial(is);
						amountHeal += ar.getInheritHealAmount(im.getCategory(), material, AbilityRegistry.healUps);
						amountHeal += ar.getInheritHealAmount(im.getCategory(), material, AbilityRegistry.healDowns);
					}
					if(is.getItem() instanceof IGainAbility){
						HelperAbility helper = new HelperAbility(is,entityPlayer);
						amountHeal += helper.getHealAmount();
					}
				}
			}

		}
		for(ItemStack armor:entityPlayer.inventory.armorInventory){
			if(armor!=null){
				if(armor.getItem() instanceof IUnsagaMaterial){
					IUnsagaMaterial im = (IUnsagaMaterial)armor.getItem();
					UnsagaMaterial material = HelperUnsagaWeapon.getMaterial(armor);
					amountHeal += ar.getInheritHealAmount(im.getCategory(), material, AbilityRegistry.healUps);
					amountHeal += ar.getInheritHealAmount(im.getCategory(), material, AbilityRegistry.healDowns);
				}
				if(armor.getItem() instanceof IGainAbility){
					HelperAbility helper = new HelperAbility(armor,entityPlayer);
					amountHeal += helper.getHealAmount();
				}
			}
		}

		
		if(amountHeal<0){
			if(entityPlayer.ticksExisted % 20 * 12 == 0){
				entityPlayer.addExhaustion(0.025F * (float)(Math.abs(amountHeal) + 1));
			}
		}
		if(amountHeal>0){
			if(entityPlayer.ticksExisted % 20 * 12 == 0){
				if(!entityPlayer.getFoodStats().needFood()){
					entityPlayer.heal(amountHeal);
				}
				
			}
		}
		if(entityPlayer.ticksExisted % 20 * 12 == 0){
			Unsaga.debug(amountHeal);
		}
		
	}

	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	public EnumSet<TickType> ticks() {
		// TODO 自動生成されたメソッド・スタブ
		return EnumSet.of(TickType.PLAYER,TickType.SERVER);
	}

	@Override
	public String getLabel() {
		// TODO 自動生成されたメソッド・スタブ
		return "armorTick";
	}

}

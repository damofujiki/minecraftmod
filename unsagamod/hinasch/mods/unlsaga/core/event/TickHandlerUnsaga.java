package hinasch.mods.unlsaga.core.event;

import hinasch.mods.unlsaga.Unsaga;
import hinasch.mods.unlsaga.core.init.UnsagaMaterial;
import hinasch.mods.unlsaga.misc.ability.AbilityRegistry;
import hinasch.mods.unlsaga.misc.util.HelperUnsagaWeapon;
import hinasch.mods.unlsaga.misc.util.IUnsagaMaterial;

import java.util.EnumSet;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
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
		if(ExtendedPlayerData.getData(entityPlayer).isPresent()){
			ExtendedPlayerData pdata = ExtendedPlayerData.getData(entityPlayer).get();
			for(int i=0;i<2;i++){
				if(pdata.getItemStack(i)!=null){
					if(pdata.getItemStack(i).getItem() instanceof IUnsagaMaterial){
						IUnsagaMaterial im = (IUnsagaMaterial)pdata.getItemStack(i).getItem();
						UnsagaMaterial material = HelperUnsagaWeapon.getMaterial(pdata.getItemStack(i));
						amountHeal += ar.getInheritHealAmount(im.getCategory(), material, AbilityRegistry.healUps);
						amountHeal += ar.getInheritHealAmount(im.getCategory(), material, AbilityRegistry.healDowns);
					}
				}
			}
		}
		for(int i=0;i<4;i++){
			if(entityPlayer.inventory.armorInventory[i]!=null){
				ItemStack armorstack = entityPlayer.inventory.armorInventory[i];
				if(armorstack.getItem() instanceof IUnsagaMaterial){
					IUnsagaMaterial im = (IUnsagaMaterial)armorstack.getItem();
					UnsagaMaterial material = HelperUnsagaWeapon.getMaterial(armorstack);
					//Unsaga.debug(material.headerEn+":"+im.getCategory());
					amountHeal += ar.getInheritHealAmount(im.getCategory(), material, AbilityRegistry.healUps);
					amountHeal += ar.getInheritHealAmount(im.getCategory(), material, AbilityRegistry.healDowns);
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
		return EnumSet.of(TickType.PLAYER);
	}

	@Override
	public String getLabel() {
		// TODO 自動生成されたメソッド・スタブ
		return "armorTick";
	}

}

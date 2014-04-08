package hinasch.mods.unlsagamagic.misc.instantenchant;

import java.util.Set;

import com.hinasch.lib.UtilNBT;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class InstantEnchantHelper {

	protected final static String KEY = "unsaga.instantEnchant";
	protected final static AttributeModifier atSharpness = new AttributeModifier("InstantEnchant Sharpness", 1.5F, 1); 
	
	public static void addInstantEnchant(ItemStack is){
		int enchantid = 0;
		int remain = 100;
		Set toolClasses = is.getItem().getToolClasses(is);
		if(toolClasses.contains("pickaxe") || toolClasses.contains("shavel")){
			enchantid = Enchantment.efficiency.effectId;
			
		}else{
			enchantid = Enchantment.sharpness.effectId;
			if(!is.getAttributeModifiers().containsValue(atSharpness)){
				is.getAttributeModifiers().put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), atSharpness);
				
			}
			
		}
		
		UtilNBT.setFreeTag(is, KEY+".id", enchantid);
		UtilNBT.setFreeTag(is, KEY+".remain",remain );
	}
	
	@SubscribeEvent
	public void hookBreakSpeed(BreakSpeed e){
		ItemStack is = e.entityPlayer.getHeldItem();
		if(is!=null){
			if(UtilNBT.hasKey(is, KEY+".id") && UtilNBT.readFreeTag(is, KEY+".id")==Enchantment.efficiency.effectId){
				e.newSpeed = e.originalSpeed + 1.0F;
			}
		}
	}
	
	public static void updateEvent(LivingUpdateEvent e){
		ItemStack is = e.entityLiving.getHeldItem();
		if(is!=null && e.entityLiving.ticksExisted % 2 == 0){
			if(UtilNBT.hasKey(is, KEY+".remain") && UtilNBT.readFreeTag(is, KEY+".remain")>0){
				int nextremain = UtilNBT.readFreeTag(is, KEY+".remain")-1;
				UtilNBT.setFreeTag(is, KEY+".remain", nextremain);
				if(nextremain<=0){
					removeTags(is);
				}
			}
		}
	}
	
	public static void removeTags(ItemStack is){
		if(UtilNBT.hasKey(is, KEY+".id")){
			if(UtilNBT.readFreeTag(is, KEY+".id")==Enchantment.sharpness.effectId){
				is.getAttributeModifiers().remove(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), atSharpness);
			}
			UtilNBT.removeTag(is, KEY+".id");
		}
		if(UtilNBT.hasKey(is, KEY+".remain")){
			UtilNBT.removeTag(is, KEY+".remain");
		}
	}
}

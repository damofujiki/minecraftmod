package hinasch.mods.unlsaga.core.event;

import hinasch.lib.HSLibs;
import hinasch.mods.unlsaga.misc.ability.IGainAbility;
import hinasch.mods.unlsaga.misc.ability.skill.HelperSkill;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

public class EventGainSkillOnAttack {

	@ForgeSubscribe
	public void inspireEvent(LivingAttackEvent e){
		if(e.source==null)return;
		if(e.source.getEntity() instanceof EntityPlayer){
			if(HSLibs.isEnemy(e.entityLiving)){
				EntityPlayer ep = (EntityPlayer)e.source.getEntity();
				ItemStack weapon = ep.getHeldItem();
				if(weapon!=null){
					if(weapon.getItem() instanceof IGainAbility){
						HelperSkill helper = new HelperSkill(weapon,ep);
						helper.drawChanceToGainAbility(ep.getRNG(),e.entityLiving );
					}

				}
			}

		}
	}
}

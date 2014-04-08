package hinasch.mods.unlsaga.core.event;

import hinasch.mods.unlsaga.Unsaga;
import hinasch.mods.unlsaga.misc.ability.Ability;
import hinasch.mods.unlsaga.misc.ability.AbilityRegistry;
import hinasch.mods.unlsaga.misc.ability.skill.effect.SkillBow;
import hinasch.mods.unlsaga.misc.debuff.Debuffs;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class EventLivingUpdate {

	protected AxisAlignedBB bb;
	protected AbilityRegistry ar = Unsaga.abilityRegistry;
	@SubscribeEvent
	public void onLivingUpdata(LivingUpdateEvent e){
		SkillBow.checkArrowOnStoppedEvent(e);
		SkillBow.checkArrowParticleEvent(e);

		Ability.abilityEventOnLivingUpdate(e.entityLiving,ar);

		Debuffs.debuffEventOnLivingUpdate(e);
		

		

	}
	
	

}

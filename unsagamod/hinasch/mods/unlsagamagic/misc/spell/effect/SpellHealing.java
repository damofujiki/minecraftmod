package hinasch.mods.unlsagamagic.misc.spell.effect;

import hinasch.mods.unlsaga.Unsaga;
import hinasch.mods.unlsaga.misc.translation.Translation;
import hinasch.mods.unlsaga.misc.util.ChatUtil;
import hinasch.mods.unlsaga.network.packet.PacketParticle;
import hinasch.mods.unlsaga.network.packet.PacketUtil;
import net.minecraft.entity.EntityLivingBase;

public abstract class SpellHealing extends SpellBase{

	public SpellHealing(){
		
	}


	@Override
	public void invokeSpell(InvokeSpell parent) {
		float heal = Math.round(parent.spell.hurtHP*parent.getAmp());
		if(parent.getTarget().isPresent()){

			EntityLivingBase target = parent.getTarget().get();
			target.heal(heal);
			this.hookHealing(parent,target);
			String mesbase = Translation.localize("msg.heal");
			String formatted = String.format(mesbase, target.getCommandSenderName(),heal);
			ChatUtil.addMessageNoLocalized(parent.invoker, formatted);
			PacketParticle pp = new PacketParticle(3,target.getEntityId(),10);

			Unsaga.packetPipeline.sendToAllAround(pp, PacketUtil.getTargetPointNear(target));
			
			
		}else{
			parent.invoker.heal(heal);
			this.hookHealing(parent,parent.invoker);
			String mesbase = Translation.localize("msg.heal");
			String formatted = String.format(mesbase, parent.invoker.getCommandSenderName(),heal);
			ChatUtil.addMessageNoLocalized(parent.invoker, formatted);
			PacketParticle pp = new PacketParticle(3,parent.invoker.getEntityId(),3);
			Unsaga.packetPipeline.sendToAllAround(pp, PacketUtil.getTargetPointNear(parent.invoker));
			
		}
	}

	abstract public void hookHealing(InvokeSpell parent, EntityLivingBase target);
	


}

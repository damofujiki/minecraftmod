package hinasch.mods.unlsagamagic.misc.spell.effect;

import hinasch.mods.unlsaga.Unsaga;
import hinasch.mods.unlsaga.misc.translation.Translation;
import hinasch.mods.unlsaga.misc.util.ChatUtil;
import hinasch.mods.unlsaga.network.packet.PacketParticle;
import hinasch.mods.unlsaga.network.packet.PacketUtil;
import net.minecraft.entity.EntityLivingBase;

public abstract class SpellHealing extends SpellBase{

	protected boolean isSelf = false;
	
	public SpellHealing(){
		
	}


	@Override
	public void invokeSpell(InvokeSpell parent) {
		float heal = parent.spell.hurtHP*parent.getAmp();
		if(parent.getTarget().isPresent() && !this.isSelf){

			EntityLivingBase target = parent.getTarget().get();
			target.heal(heal);			
			this.hookHealing(parent,target);
			String mesbase = Translation.localize("msg.heal");
			String formatted = String.format(mesbase, target.getCommandSenderName(),Math.round(heal));
			ChatUtil.addMessageNoLocalized(parent.getInvoker(), formatted);
			PacketParticle pp = new PacketParticle(3,target.getEntityId(),10);

			Unsaga.packetPipeline.sendToAllAround(pp, PacketUtil.getTargetPointNear(target));
			
			
		}else{
			parent.getInvoker().heal(heal);
			this.hookHealing(parent,parent.getInvoker());
			String mesbase = Translation.localize("msg.heal");
			String formatted = String.format(mesbase, parent.getInvoker().getCommandSenderName(),Math.round(heal));
			ChatUtil.addMessageNoLocalized(parent.getInvoker(), formatted);
			PacketParticle pp = new PacketParticle(3,parent.getInvoker().getEntityId(),3);
			Unsaga.packetPipeline.sendToAllAround(pp, PacketUtil.getTargetPointNear(parent.getInvoker()));
			
		}
	}

	abstract public void hookHealing(InvokeSpell parent, EntityLivingBase target);
	


}

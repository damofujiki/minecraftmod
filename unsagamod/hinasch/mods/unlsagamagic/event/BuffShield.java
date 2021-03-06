package hinasch.mods.unlsagamagic.event;

import hinasch.mods.unlsaga.Unsaga;
import hinasch.mods.unlsaga.core.FiveElements.EnumElement;
import hinasch.mods.unlsaga.misc.debuff.Buff;
import hinasch.mods.unlsaga.misc.debuff.livingdebuff.LivingBuff;
import hinasch.mods.unlsaga.misc.debuff.livingdebuff.LivingDebuff;
import hinasch.mods.unlsaga.network.packet.PacketSound;
import hinasch.mods.unlsaga.network.packet.PacketUtil;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;

public abstract class BuffShield {

	public boolean isGuardAll;
	protected Buff parentBuff;
	public EnumElement element;
	protected PacketSound ps;
	
	public BuffShield(Buff parent,boolean isGuardAll,EnumElement element){
		this.parentBuff = parent;
		this.isGuardAll = isGuardAll;
		this.element = element;
		EventSpellBuff.shieldSet.add(this);
	}
	
	public void doGuard(LivingHurtEvent e){
		if(this.isEffective(e)){
			if(LivingDebuff.hasDebuff(e.entityLiving, parentBuff)){
				if(LivingDebuff.getLivingDebuff(e.entityLiving, parentBuff).isPresent()){
					LivingBuff shield = (LivingBuff)LivingDebuff.getLivingDebuff(e.entityLiving, parentBuff).get();
					if((!this.isGuardAll && e.entityLiving.getRNG().nextInt(100)<shield.amp) || this.isGuardAll){
						e.ammount = 0;
						ps = new PacketSound(1022,e.entityLiving.getEntityId(),PacketSound.MODE.AUX);

						TargetPoint tp = PacketUtil.getTargetPointNear(e.entityLiving);
						Unsaga.packetPipeline.sendToAllAround(ps, tp);
					}
				}
			}
		}


		
	}
	
	abstract public boolean isEffective(LivingHurtEvent e);
}

package hinasch.mods.unlsagamagic.misc.spell.effect;

import hinasch.mods.unlsaga.Unsaga;
import hinasch.mods.unlsaga.network.packet.PacketSound;
import hinasch.mods.unlsaga.network.packet.PacketUtil;
import net.minecraft.entity.Entity;

public class SpellProjectile extends SpellBase{

	public void hookStart(InvokeSpell parent){
		PacketSound ps = new PacketSound(1008,parent.invoker.getEntityId(),PacketSound.MODE.AUX);
		Unsaga.packetPipeline.sendToAllAround(ps, PacketUtil.getTargetPointNear(parent.invoker));
	}
	
	public void hookEnd(InvokeSpell parent){
		
	}
	
	@Override
	public void invokeSpell(InvokeSpell parent) {
		this.hookStart(parent);
		Entity projectile = this.getProjectileEntity(parent);
		if (!parent.world.isRemote)
		{
			parent.world.spawnEntityInWorld(projectile);
		}
		this.hookEnd(parent);
		return;
		
	}

	public Entity getProjectileEntity(InvokeSpell parent){
		return null;
	}
}

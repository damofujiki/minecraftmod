package hinasch.mods.unlsagamagic.event;

import hinasch.mods.unlsaga.misc.debuff.DebuffRegistry;
import hinasch.mods.unlsaga.misc.debuff.LivingDebuff;
import hinasch.mods.unlsaga.misc.debuff.LivingStateTarget;
import hinasch.mods.unlsagamagic.item.ItemSpellBook;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.EntityInteractEvent;

public class EventSpellTarget {


	@ForgeSubscribe
	public void onLivingInteractForTarget(EntityInteractEvent e){
		if(e.target!=null){
			if(e.entityPlayer.getHeldItem()==null)return;
			if(e.entityPlayer.getHeldItem().getItem() instanceof ItemSpellBook && e.entityPlayer.isSneaking()){
				if(e.target instanceof EntityLivingBase && !e.entityPlayer.worldObj.isRemote){
					EntityLivingBase el = (EntityLivingBase)e.target;
					LivingStateTarget state = new LivingStateTarget(DebuffRegistry.spellTarget,30,el.entityId);
					LivingDebuff.addLivingDebuff(e.entityPlayer, state);
					e.entityPlayer.addChatMessage("Set Target To "+e.target.getEntityName());
				}
			}
		}
	}
}

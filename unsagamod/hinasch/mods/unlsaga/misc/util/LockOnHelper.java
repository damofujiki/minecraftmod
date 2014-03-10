package hinasch.mods.unlsaga.misc.util;

import hinasch.mods.unlsaga.misc.debuff.Debuffs;
import hinasch.mods.unlsaga.misc.debuff.livingdebuff.LivingDebuff;
import hinasch.mods.unlsaga.misc.debuff.livingdebuff.LivingStateTarget;
import hinasch.mods.unlsaga.misc.debuff.state.State;

import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;

public class LockOnHelper {

	
	public static void setAttackTarget(EntityLivingBase attacker,EntityLivingBase target){
		setTarget(attacker,target,Debuffs.weaponTarget);
	}
	
	public static void setSpellTarget(EntityLivingBase attacker,EntityLivingBase target){
		setTarget(attacker,target,Debuffs.spellTarget);
	}
	
	public static void setTarget(EntityLivingBase attacker,EntityLivingBase target,State debuff){
		LivingStateTarget state = new LivingStateTarget(debuff,30,target.getEntityId());
		LivingDebuff.addLivingDebuff(attacker, state);
		if(attacker instanceof EntityPlayer){
			ChatMessageHandler.sendChatToPlayer((EntityPlayer) attacker, "Set Target To "+target.getCommandSenderName());
		}
		if(target instanceof EntityPlayer){
			ChatMessageHandler.sendChatToPlayer((EntityPlayer) target, attacker.getCommandSenderName()+" Sets Target To You");
		}
		
	}
	
	public static EntityLivingBase searchEntityNear(EntityLivingBase attacker,State state){
		AxisAlignedBB bb = attacker.boundingBox.expand(10.0D, 10.0D, 10.0D);
		List<EntityLivingBase> entitynearlist = attacker.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, bb);
		if(!entitynearlist.isEmpty()){
			for(EntityLivingBase entity:entitynearlist){
				if(entity!=attacker && entity instanceof IMob){
					setTarget(attacker,entity,state);
					return entity;
				}
			}
		}

		return null;
	}

}

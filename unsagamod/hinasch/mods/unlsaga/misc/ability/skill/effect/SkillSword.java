package hinasch.mods.unlsaga.misc.ability.skill.effect;

import hinasch.lib.HSLibs;
import hinasch.mods.unlsaga.misc.debuff.DebuffRegistry;
import hinasch.mods.unlsaga.misc.debuff.LivingDebuff;
import hinasch.mods.unlsaga.misc.debuff.LivingState;

import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class SkillSword extends SkillEffect{


	public void doVandelize(EntityPlayer ep,Entity entity,World world,ItemStack is){

		if(!world.isRemote){
			if(HSLibs.isEntityLittleMaidAvatar(ep)){
				world.createExplosion(HSLibs.getLMMFromAvatar(ep), entity.posX, entity.posY, entity.posZ, 1.5F,false);
				return;
			}
			world.createExplosion(ep, entity.posX, entity.posY, entity.posZ, 1.5F,false);

		}


	}
	
	public void doKaleidoscope(EntityPlayer ep,Entity entity,World world,ItemStack is){
		Random random = ep.getRNG();
		LivingDebuff.addLivingDebuff(ep,new LivingState(DebuffRegistry.antiFallDamage,10,true));
		LivingDebuff.addLivingDebuff(ep, new LivingState(DebuffRegistry.kalaidoscope,10,true));
		//iaitem.setStateNBT(is, 1);
		//UnsagaCore.debuglib.addChatMessage("PlayerAttack");
		ep.playSound("mob.wither.shoot", 0.5F, 1.8F / (ep.getRNG().nextFloat() * 0.4F + 1.2F));
		double newposY = entity.posY + random.nextInt(4)+12;
		//ep.posY = newposY;
		ep.motionY = 0.8;


		double disX = entity.posX-ep.posX;
		double disZ =entity.posZ-ep.posZ;
		double distance = Math.sqrt(Math.pow(disX, 2)+Math.pow(disZ,2));

		ep.motionX = (disX*0.08);
		ep.motionZ = (disZ*0.08);


	}
	
	public void doRearBlade(EntityPlayer ep,World world,ItemStack is){
		ep.hurtResistantTime = 5;
		ep.playSound("mob.wither.shoot", 0.5F, 1.8F / (ep.getRNG().nextFloat() * 0.4F + 1.2F));
		LivingDebuff.addLivingDebuff(ep, new LivingState(DebuffRegistry.rushBlade,1, false));


		//ep.moveEntityWithHeading(1.0F	, 2.0F);
	}
}

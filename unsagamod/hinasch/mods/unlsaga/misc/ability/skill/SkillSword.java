package hinasch.mods.unlsaga.misc.ability.skill;

import hinasch.lib.HSLibs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class SkillSword {


	public void doVandelize(EntityPlayer ep,Entity entity,World world,ItemStack is){

		if(!world.isRemote){
			if(HSLibs.isEntityLittleMaidAvatar(ep)){
				world.createExplosion(HSLibs.getLMMFromAvatar(ep), entity.posX, entity.posY, entity.posZ, 1.5F,false);
				return;
			}
			world.createExplosion(ep, entity.posX, entity.posY, entity.posZ, 1.5F,false);

		}


	}
}

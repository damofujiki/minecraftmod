package hinasch.mods.unlsaga.core.event;

import hinasch.lib.HSLibs;
import hinasch.lib.XYZPos;
import hinasch.mods.unlsaga.Unsaga;
import hinasch.mods.unlsaga.core.init.UnsagaBlocks;
import hinasch.mods.unlsaga.core.init.UnsagaConfigs;
import hinasch.mods.unlsaga.entity.EntityTreasureSlime;
import hinasch.mods.unlsaga.misc.ability.Ability;

import java.util.Random;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class EventLivingDeath {

	@SubscribeEvent
	public void onLivingDeath(LivingDeathEvent e){
		if(UnsagaConfigs.module.isMagicEnabled() && !UnsagaConfigs.decipherAtSleep){
			Unsaga.getModuleMagicHandler().progressDecipherOnLivingDeath(e);
		}
		if(e.entityLiving instanceof EntityPlayer){
			ExtendedPlayerData.dropAccessoriesOnDeath(e);
		}
		if(e.entityLiving instanceof EntityMob || e.entityLiving instanceof IMob){
			enemyDropEvent(e);
		}
		if(e.source.getEntity()!=null){
			EntityLivingBase enemy = e.entityLiving;
			if(e.source.getEntity() instanceof EntityPlayer){
				Ability.gainAbilityEventOnLivingDeath(e, enemy);
				
			}
		}
	}
	


	private void enemyDropEvent(LivingDeathEvent e){
		boolean isMiniSlime = false;
		if(e.entityLiving instanceof EntitySlime){
			if(((EntitySlime)e.entityLiving).getSlimeSize()<=1){
				isMiniSlime = true;
			}
		}
		if(e.entityLiving instanceof EntityTreasureSlime){
			if(((EntityTreasureSlime)e.entityLiving).getSlimeSize()<=1){
				isMiniSlime = true;
			}
		}
		Random rand = e.entityLiving.getRNG();
		World world = e.entityLiving.worldObj;
		XYZPos po = XYZPos.entityPosToXYZ(e.entityLiving);
		if(e.entityLiving instanceof EntityTreasureSlime && !isMiniSlime){
			if(rand.nextInt(100)<20){
				if(UnsagaConfigs.module.isMagicEnabled()){
					ItemStack tablet = Unsaga.getModuleMagicHandler().getRandomMagicTablet(rand);
					
					HSLibs.dropItem(e.entityLiving.worldObj, tablet, po.x, po.y, po.z);
					return;
				}
			}
			
		}
		if(rand.nextInt(100)<15 && !isMiniSlime){
			if(!world.isRemote){
				if(world.isAirBlock(po.x, po.y, po.z)){
					world.setBlock(po.x, po.y, po.z, UnsagaBlocks.blockChestUnsaga);
				}
			}
		}
	}

	public void isGainAbility(){
		
	}
}

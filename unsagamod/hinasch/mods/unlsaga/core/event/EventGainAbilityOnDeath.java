package hinasch.mods.unlsaga.core.event;

import hinasch.lib.HSLibs;
import hinasch.lib.XYZPos;
import hinasch.mods.unlsaga.Unsaga;
import hinasch.mods.unlsaga.core.init.UnsagaBlocks;
import hinasch.mods.unlsaga.entity.EntityTreasureSlime;
import hinasch.mods.unlsaga.misc.ability.HelperAbility;

import java.util.Random;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

public class EventGainAbilityOnDeath {

	@ForgeSubscribe
	public void onLivingDeath(LivingDeathEvent e){
		if(e.entityLiving instanceof EntityMob || e.entityLiving instanceof IMob){
			enemyDropEvent(e);
		}
		if(e.source.getEntity()!=null){
			EntityLivingBase enemy = e.entityLiving;
			if(e.source.getEntity() instanceof EntityPlayer){
				Unsaga.debug("呼ばれてる");
				EntityPlayer ep = (EntityPlayer)e.source.getEntity();
//				for(int i=0;i<4;i++){
//					ItemStack is = ep.inventory.armorInventory[i];
//					if(HelperAbility.canGainAbility(is)){
//						
//					}
//				}
				
				if(ExtendedPlayerData.getData(ep).isPresent()){
					ExtendedPlayerData pdata = ExtendedPlayerData.getData(ep).get();
					for(int i=0;i<2;i++){
						ItemStack is = pdata.getItemStack(i);
						if(HelperAbility.canGainAbility(is)){
							Unsaga.debug("アビリティを覚えられる");
							HelperAbility helper = new HelperAbility(is,ep);
							if(!ep.worldObj.isRemote){
								helper.drawChanceToGainAbility(ep.getRNG(), enemy);
							}
							
						}
					}
					
				}
				for(ItemStack armor:ep.inventory.armorInventory){
					if(armor!=null){
						if(HelperAbility.canGainAbility(armor)){
							HelperAbility helper = new HelperAbility(armor,ep);
							if(!ep.worldObj.isRemote){
								helper.drawChanceToGainAbility(ep.getRNG(), enemy);
							}
						}
					}
				}
				
			}
		}
	}
	
	private void enemyDropEvent(LivingDeathEvent e){
		Random rand = e.entityLiving.getRNG();
		World world = e.entityLiving.worldObj;
		XYZPos po = XYZPos.entityPosToXYZ(e.entityLiving);
		if(e.entityLiving instanceof EntityTreasureSlime){
			if(rand.nextInt(100)<20){
				if(Unsaga.module.isPresent()){
					ItemStack tablet = Unsaga.module.get().getRandomMagicTablet(rand);
					
					HSLibs.dropItem(e.entityLiving.worldObj, tablet, po.x, po.y, po.z);
				}
			}
		}
		if(rand.nextInt(100)<15){
			if(!world.isRemote){
				if(world.isAirBlock(po.x, po.y, po.z)){
					world.setBlock(po.x, po.y, po.z, UnsagaBlocks.blockChestUnsaga.blockID);
				}
			}
		}
	}

	public void isGainAbility(){
		
	}
}

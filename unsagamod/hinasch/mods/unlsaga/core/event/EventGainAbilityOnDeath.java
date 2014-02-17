package hinasch.mods.unlsaga.core.event;

import hinasch.mods.unlsaga.Unsaga;
import hinasch.mods.unlsaga.misc.ability.HelperAbility;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

public class EventGainAbilityOnDeath {

	@ForgeSubscribe
	public void onLivingDeath(LivingDeathEvent e){
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
								helper.drawChanceToGainAbility(ep.getRNG(), 100);
							}
							
						}
					}
					
				}
				
			}
		}
	}
	
	public void isGainAbility(){
		
	}
}

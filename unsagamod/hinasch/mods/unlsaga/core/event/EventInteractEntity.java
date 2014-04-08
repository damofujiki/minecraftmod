package hinasch.mods.unlsaga.core.event;

import com.hinasch.lib.HSLibs;
import com.hinasch.lib.XYZPos;

import hinasch.mods.unlsaga.Unsaga;
import hinasch.mods.unlsaga.item.weapon.ItemAxeUnsaga;
import hinasch.mods.unlsaga.misc.ability.AbilityRegistry;
import hinasch.mods.unlsaga.misc.ability.HelperAbility;
import hinasch.mods.unlsaga.misc.util.LockOnHelper;
import hinasch.mods.unlsaga.network.packet.PacketGuiOpen;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class EventInteractEntity {

	@SubscribeEvent 
	public void onPlayerInteractsVillager(EntityInteractEvent e){
		onPlayerTargetting(e);
		
		if(e.target instanceof EntityVillager){
			Unsaga.debug("ターゲットは村人");
			EntityPlayer ep =(EntityPlayer)e.entityPlayer;
			if(ep.isSneaking()){
				EntityVillager villager = (EntityVillager)e.target;
				XYZPos xyz = XYZPos.entityPosToXYZ(ep);
				int profession = villager.getProfession();
				Unsaga.debug("村人:"+profession);
				if(villager.getCustomer()!=null)return;
				if(profession==3){ //鍛冶屋
					Unsaga.debug("鍛冶屋です");
					villager.setCustomer(ep);
					if(!ep.worldObj.isRemote && HSLibs.getExtendedData(ExtendedPlayerData.key, ep).isPresent()){
						//e.setCanceled(true);
						Unsaga.debug("データがとれた");
						ExtendedPlayerData data = (ExtendedPlayerData) HSLibs.getExtendedData(ExtendedPlayerData.key, e.entityPlayer).get();
						data.setMerchant(villager);
						
						PacketGuiOpen pgo = new PacketGuiOpen(Unsaga.guiNumber.smith,villager);
						Unsaga.packetPipeline.sendToServer(pgo);
					}

				}
				if(profession==0 || profession==4){ //農家か肉屋
					villager.setCustomer(e.entityPlayer);
					if(HSLibs.getExtendedData(ExtendedPlayerData.key, ep).isPresent()){
						ExtendedPlayerData data = (ExtendedPlayerData) HSLibs.getExtendedData(ExtendedPlayerData.key, e.entityPlayer).get();
						data.setMerchant(villager);
						//e.entityPlayer.openGui(Unsaga.instance, Unsaga.GuiBartering, e.entityPlayer.worldObj, xyz.x, xyz.y, xyz.z);
						XYZPos pos = XYZPos.entityPosToXYZ(ep);
						PacketGuiOpen pgo = new PacketGuiOpen(Unsaga.guiNumber.bartering,villager);
						Unsaga.packetPipeline.sendToServer(pgo);
					}
				}
			}
		}
	}

	public void onPlayerTargetting(EntityInteractEvent e){
		if(e.target!=null){
			EntityPlayer ep = e.entityPlayer;
			//スカイドライブにしても、ロックオン指定しない場合、投げる「前」にひろうようにするとよさそう
			if(ep.getHeldItem()==null)return;
			ItemStack is = ep.getHeldItem();
			World world = ep.worldObj;
			if(HelperAbility.hasAbilityFromItemStack(AbilityRegistry.skyDrive, is) && ep.isSneaking() && is.getItem() instanceof ItemAxeUnsaga){
				if(!world.isRemote){
					if(e.target instanceof EntityLivingBase){
						LockOnHelper.setAttackTarget(ep, (EntityLivingBase) e.target);
					}
				}

			}
		}
	}
	
	

}

package hinasch.mods.unlsaga.core.event;

import hinasch.lib.HSLibs;
import hinasch.lib.XYZPos;
import hinasch.mods.unlsaga.Unsaga;
import hinasch.mods.unlsaga.network.PacketHandler;

import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import cpw.mods.fml.common.network.PacketDispatcher;

public class EventInteractVillager {

	@ForgeSubscribe
	public void onPlayerInteractsVillager(EntityInteractEvent e){
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
						//e.entityPlayer.openGui(Unsaga.instance, Unsaga.GuiSmith, ep.worldObj, xyz.x, xyz.y, xyz.z);
						PacketDispatcher.sendPacketToServer(PacketHandler.getPacket(this,villager));
					}

				}
				if(profession==0 || profession==4){ //農家か肉屋
					villager.setCustomer(e.entityPlayer);
					if(HSLibs.getExtendedData(ExtendedPlayerData.key, e.entityPlayer).isPresent()){
						ExtendedPlayerData data = (ExtendedPlayerData) HSLibs.getExtendedData(ExtendedPlayerData.key, e.entityPlayer).get();
						data.setMerchant(villager);
						//e.entityPlayer.openGui(Unsaga.instance, Unsaga.GuiBartering, e.entityPlayer.worldObj, xyz.x, xyz.y, xyz.z);
					}
				}
			}
		}
	}

	public static void writePacketData(DataOutputStream dos,
			EntityVillager villager) {
		try {
			dos.writeByte((byte)Unsaga.GuiSmith);
			dos.writeInt((int)villager.entityId);
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		
	}
}

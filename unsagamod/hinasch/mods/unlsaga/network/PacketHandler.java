package hinasch.mods.unlsaga.network;

import hinasch.lib.HSLibs;
import hinasch.lib.XYZPos;
import hinasch.mods.unlsaga.Unsaga;
import hinasch.mods.unlsaga.client.KeyHandlerTest;
import hinasch.mods.unlsaga.core.event.EventInteractVillager;
import hinasch.mods.unlsaga.core.event.ExtendedPlayerData;
import hinasch.mods.unlsaga.inventory.ContainerSmithUnsaga;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;

import cpw.mods.fml.common.network.FMLNetworkHandler;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

public class PacketHandler implements IPacketHandler{

	public static final int PLAY_SOUND = 0x01;
	public static final int OPEN_EQUIPMENT = 0x10;
	public static final int OPEN_SMITH = 0x11;
	public static final int GUI_FORGE = 0x12;
	@Override
	public void onPacketData(INetworkManager manager,
			Packet250CustomPayload packet, Player player) {
		// TODO 自動生成されたメソッド・スタブ
		Unsaga.debug("パケット受け取りました");
		if (packet.channel.equals("unsagamod")){
			ByteArrayDataInput data = ByteStreams.newDataInput(packet.data);

			int id = data.readInt();
			Unsaga.debug(id+":パケット番号");
			if(id==PLAY_SOUND){
				if(player instanceof EntityPlayerMP){
					int entityId = data.readInt();
					EntityClientPlayerMP ep = (EntityClientPlayerMP)player;
					Entity ent = ep.worldObj.getEntityByID(entityId);
					if(ent!=null){
						//ep.worldObj.playSoundAtEntity(ent, "mob.blaze.hit", 1.5F, 1.0F / (ep.getRNG().nextFloat() * 0.4F + 1.2F));
						ent.playSound("mob.blaze.hit", 1.5F, 1.0F / (ep.getRNG().nextFloat() * 0.4F + 1.2F));
					}
				}
			}
			if(id==GUI_FORGE){
				//ByteArrayDataInput data2 = ByteStreams.newDataInput(packet.data);

				Container container = ((EntityPlayerMP)player).openContainer;

				if(container != null && container instanceof ContainerSmithUnsaga)
				{
					//UnsagaCore.debuglib.addChatMessage("okok");
					Unsaga.debug("コンテナ開いてます");
					((ContainerSmithUnsaga)container).readPacketData(data);
					((ContainerSmithUnsaga)container).onPacketData();
				}
			}

		}
		if (packet.channel.equals("unsagamod_gui")){
			ByteArrayDataInput data = ByteStreams.newDataInput(packet.data);

			Byte id = data.readByte();
			Unsaga.debug(id+":パケット番号");


			if(id==Unsaga.GuiEquipment){
				Unsaga.debug("GuiEquipment");
				EntityPlayerMP ep = (EntityPlayerMP)player;
				//Unsaga.debug(ep.openContainer);
				
				//ep.openGui(Unsaga.instance, Unsaga.GuiEquipment, ep.worldObj, (int)ep.posX, (int)ep.posY, (int)ep.posZ);
				FMLNetworkHandler.openGui((EntityPlayer) player, Unsaga.instance, Unsaga.GuiEquipment, ep.worldObj, (int)ep.posX, (int)ep.posY, (int)ep.posZ);
//			Container container = ((EntityPlayerMP)player).openContainer;
//			if(container != null && container instanceof ContainerEquipment)
//			{
//				Unsaga.debug("GUIを開いている");
////				((ContainerEquipment)container).readPacketData(data);
////				((ContainerEquipment)container).onPacketData();
//			}

			}
			if(id==Unsaga.GuiSmith){
				int villagerid = data.readInt();
				EntityPlayerMP ep = (EntityPlayerMP)player;
				EntityVillager villager = (EntityVillager) ep.worldObj.getEntityByID(villagerid);
				if(HSLibs.getExtendedData(ExtendedPlayerData.key,ep).isPresent()){
					((ExtendedPlayerData)HSLibs.getExtendedData(ExtendedPlayerData.key, ep).get()).setMerchant(villager);
					XYZPos pos = XYZPos.entityPosToXYZ(ep);
					FMLNetworkHandler.openGui((EntityPlayer) player, Unsaga.instance, Unsaga.GuiSmith, ep.worldObj,pos.x,pos.y,pos.z);
				}
				

			}
			
		}
	}
	
	
	public static Packet getPacket(KeyHandlerTest keyHandlerTest,EntityClientPlayerMP client)
	{
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);

		keyHandlerTest.writePacketData(dos,client);

		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = "unsagamod_gui"; // ここでチャンネルを設定する
		packet.data    = bos.toByteArray();
		packet.length  = bos.size();
		packet.isChunkDataPacket = false;

		return packet;
	}


	public static Packet getPacket(EventInteractVillager eventInteractVillager,
			EntityVillager villager) {
		// TODO 自動生成されたメソッド・スタブ
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);

		EventInteractVillager.writePacketData(dos,villager);

		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = "unsagamod_gui"; // ここでチャンネルを設定する
		packet.data    = bos.toByteArray();
		packet.length  = bos.size();
		packet.isChunkDataPacket = false;

		return packet;
	}


	public static Packet getPacket(ContainerSmithUnsaga containerSmithUnsaga,
			int id,byte category) {
		// TODO 自動生成されたメソッド・スタブ
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);

		ContainerSmithUnsaga.writePacketData(dos,id,category);

		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = "unsagamod"; // ここでチャンネルを設定する
		packet.data    = bos.toByteArray();
		packet.length  = bos.size();
		packet.isChunkDataPacket = false;

		return packet;
	}

}

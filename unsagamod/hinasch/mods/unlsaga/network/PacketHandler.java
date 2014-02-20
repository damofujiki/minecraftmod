package hinasch.mods.unlsaga.network;

import hinasch.lib.HSLibs;
import hinasch.lib.XYZPos;
import hinasch.mods.unlsaga.Unsaga;
import hinasch.mods.unlsaga.core.event.EventInteractVillager;
import hinasch.mods.unlsaga.core.event.ExtendedPlayerData;
import hinasch.mods.unlsaga.inventory.ContainerBartering;
import hinasch.mods.unlsaga.inventory.ContainerChestUnsaga;
import hinasch.mods.unlsaga.inventory.ContainerSmithUnsaga;
import hinasch.mods.unlsaga.item.etc.ItemAccessory;
import hinasch.mods.unlsaga.item.etc.ItemArmorUnsaga;
import hinasch.mods.unlsaga.misc.ability.AbilityRegistry;
import hinasch.mods.unlsaga.misc.ability.HelperAbility;
import hinasch.mods.unlsaga.misc.ability.IGainAbility;
import hinasch.mods.unlsaga.misc.ability.skill.HelperSkill;
import hinasch.mods.unlsaga.misc.module.UnsagaMagicContainerHandler;
import hinasch.mods.unlsaga.misc.translation.Translation;
import hinasch.mods.unlsaga.tileentity.TileEntityChestUnsaga;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;

import cpw.mods.fml.common.network.FMLNetworkHandler;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

public class PacketHandler implements IPacketHandler{

	public static final int PLAY_SOUND = 0x01;
	public static final int MESSAGE = 0x02;
	public static final int PLAY_SOUND_AT = 0x03;
	public static final int PARTICLE = 0x04;
	public static final int PLAY_SOUND2 = 0x05;
	public static final int OPEN_EQUIPMENT = 0x10;
	public static final int OPEN_SMITH = 0x11;
	public static final int GUI_FORGE = 0x12;
	public static final int GUI_BARTERING_BUTTON = 0x13;


	public static final int GUI_BLEND_BUTTON = 0x14;
	public static final int CHEST_SYNC_TOSERVER = 0x15;
	public static final int PARTICLE_DENS = 0x16;
	public static final int ABILITY = 0x17;
	public static final int GUI_CHEST_BUTTON = 0x18;
	public static final int CHEST_SYNC_TOCLIENT = 0x19;
	public static final int PARTICLE_TO_POS = 0x20;
	public static final int GUI_CHEST_EP_SYNC = 0x21;

	//public static final int THUNDER = 0x04;
	public static HashMap<Integer,String> mesMap;
	public static HashMap<Integer,String> particleMap;
	public static HashMap<Integer,String> soundMap;



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
				if(player instanceof EntityClientPlayerMP){
					int soundnumber = data.readInt();
					EntityClientPlayerMP ep = (EntityClientPlayerMP)player;
					XYZPos po = XYZPos.entityPosToXYZ(ep);
					ep.worldObj.playAuxSFX(soundnumber, po.x, po.y, po.z, 0);
				}
			}
			if(id==PLAY_SOUND2){
				if(player instanceof EntityClientPlayerMP){
					int soundnumber = data.readInt();
					int entityid = data.readInt();
					EntityClientPlayerMP ep = (EntityClientPlayerMP)player;
					Entity entity = ep.worldObj.getEntityByID(entityid);
					ep.worldObj.playSoundAtEntity(entity, soundMap.get(soundnumber), 1.0F, 1.0F / (ep.worldObj.rand.nextFloat() * 0.4F + 1.2F) + 1.0F * 0.5F);

				}

			}
			if(id==PLAY_SOUND_AT){
				if(player instanceof EntityClientPlayerMP){
					int soundnumber = data.readInt();
					int entityid = data.readInt();

					EntityClientPlayerMP ep = (EntityClientPlayerMP)player;
					Entity entity = ep.worldObj.getEntityByID(entityid);
					XYZPos po = XYZPos.entityPosToXYZ(entity);
					ep.worldObj.playAuxSFX(soundnumber, po.x, po.y, po.z, 0);
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
			if(id==GUI_BARTERING_BUTTON){
				Container container = ((EntityPlayerMP)player).openContainer;
				if(container != null && container instanceof ContainerBartering)
				{
					//UnsagaCore.debuglib.addChatMessage("okok");
					Unsaga.debug("コンテナ開いてます");
					((ContainerBartering)container).readPacketData(data);
					((ContainerBartering)container).onPacketData();
				}
			}
			if(id==GUI_BLEND_BUTTON){
				UnsagaMagicContainerHandler.handleBlendButtonEvent(player,data);
			}
			if(id==GUI_CHEST_BUTTON){
				Container container = ((EntityPlayerMP)player).openContainer;
				if(container != null && container instanceof ContainerChestUnsaga)
				{
					((ContainerChestUnsaga)container).readPacketData(data);
					((ContainerChestUnsaga)container).onPacketData();
				}
			}
			if(id==MESSAGE){
				int mesnum = data.readInt();
				String message = mesMap.get(mesnum);
				int number = (int)data.readInt();
				String lang = Minecraft.getMinecraft().gameSettings.language;
				EntityClientPlayerMP ep = (EntityClientPlayerMP)player;
				String str = Translation.localize(message);
				String formatted = String.format(str, Unsaga.abilityRegistry.getAbilityFromInt(number).getName(lang));
				ep.addChatMessage(formatted);


			}
			//deprecated
			if(id==PARTICLE){
				int particlenum = data.readInt();
				int entityid = data.readInt();
				String particlestr = particleMap.get(particlenum);
				EntityClientPlayerMP ep = (EntityClientPlayerMP)player;
				Entity en = ep.worldObj.getEntityByID(entityid);
				XYZPos epo = XYZPos.entityPosToXYZ(en);
				for (int i = 0; i < 32; ++i)
				{
					ep.worldObj.spawnParticle(particlestr, epo.dx, epo.dy + ep.worldObj.rand.nextDouble() * 2.0D, epo.dz, ep.worldObj.rand.nextGaussian()*2, 0.0D, ep.worldObj.rand.nextGaussian()*2);
				}

			}
			if(id==PARTICLE_TO_POS){
				int x = data.readInt();
				int y = data.readInt();
				int z = data.readInt();
				int particlenum = data.readInt();
				short dens = (short)data.readShort();
				String particlestr = particleMap.get(particlenum);
				EntityClientPlayerMP ep = (EntityClientPlayerMP)player;
				for (int i = 0; i < dens; ++i)
				{
					ep.worldObj.spawnParticle(particlestr, (double)x, (double)y + ep.worldObj.rand.nextDouble() * 2.0D, (double)z, ep.worldObj.rand.nextGaussian()*2, 0.0D, ep.worldObj.rand.nextGaussian()*2);
				}


			}
			if(id==CHEST_SYNC_TOSERVER){
				int chestlevel = data.readInt();
				int cx = data.readInt();
				int cy = data.readInt();
				int cz = data.readInt();
				EntityPlayerMP ep = (EntityPlayerMP)player;
				TileEntity te = ep.worldObj.getBlockTileEntity(cx, cy, cz);
				if(te instanceof TileEntityChestUnsaga){
					((TileEntityChestUnsaga)te).setChestLevel(chestlevel);
				}
			}
			if(id==GUI_CHEST_EP_SYNC){
				EntityPlayerMP ep = (EntityPlayerMP)player;
				int ab = data.readInt();
				boolean defuse = HelperAbility.hasAbilityPlayer(ep, AbilityRegistry.defuse)>0;
				boolean unlock = HelperAbility.hasAbilityPlayer(ep, AbilityRegistry.unlock)>0;
				boolean divination = HelperAbility.hasAbilityPlayer(ep, AbilityRegistry.divination)>0;
				
			}
			if(id==CHEST_SYNC_TOCLIENT){
				int chestlevel = data.readInt();
				int cx = data.readInt();
				int cy = data.readInt();
				int cz = data.readInt();
				boolean b1 = data.readBoolean();
				boolean b2 = data.readBoolean();
				boolean b3 = data.readBoolean();
				boolean b4 = data.readBoolean();
				boolean b5 = data.readBoolean();

				EntityClientPlayerMP ep = (EntityClientPlayerMP)player;
				TileEntity te = ep.worldObj.getBlockTileEntity(cx, cy, cz);
				if(te instanceof TileEntityChestUnsaga){
					((TileEntityChestUnsaga)te).setChestLevel(chestlevel);
					((TileEntityChestUnsaga)te).sync(b1, b2, b3, b4, b5);
				}
			}

			if(id==PARTICLE_DENS){
				int particlenum = data.readInt();
				int entityid = data.readInt();
				short dens = (short)data.readShort();
				String particlestr = particleMap.get(particlenum);
				EntityClientPlayerMP ep = (EntityClientPlayerMP)player;
				Entity en = ep.worldObj.getEntityByID(entityid);
				if(en!=null){
					XYZPos epo = XYZPos.entityPosToXYZ(en);
					for (int i = 0; i < dens; ++i)
					{
						ep.worldObj.spawnParticle(particlestr, epo.dx, epo.dy + ep.worldObj.rand.nextDouble() * 2.0D, epo.dz, ep.worldObj.rand.nextGaussian()*2, 0.0D, ep.worldObj.rand.nextGaussian()*2);
					}
				}

			}
			if(id==ABILITY){
				EntityPlayerMP ep = (EntityPlayerMP)player;
				if(ep.getHeldItem()!=null){
					if(ep.getHeldItem().getItem() instanceof IGainAbility){
						ItemStack is = ep.getHeldItem();
						HelperAbility helper = new HelperAbility(is,(EntityPlayer)ep);
						Unsaga.debug("きてる");



						if(is.getItem() instanceof ItemAccessory || is.getItem() instanceof ItemArmorUnsaga){
							if(helper.canGainAbility(is)){
								helper.gainSomeAbility(ep.getRNG());
							}else{
								helper.forgetSomeAbility(ep.getRNG());
								helper.gainSomeAbility(ep.getRNG());
							}

						}else{
							HelperSkill shelper = new HelperSkill(is,ep);
							shelper.forgetSomeAbility(ep.getRNG());
							shelper.gainSomeAbility(ep.getRNG());
						}

					}
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
			if(id==Unsaga.GuiBartering){
				int villagerid = data.readInt();
				EntityPlayerMP ep = (EntityPlayerMP)player;
				EntityVillager villager = (EntityVillager) ep.worldObj.getEntityByID(villagerid);
				if(HSLibs.getExtendedData(ExtendedPlayerData.key,ep).isPresent()){
					((ExtendedPlayerData)HSLibs.getExtendedData(ExtendedPlayerData.key, ep).get()).setMerchant(villager);
					XYZPos pos = XYZPos.entityPosToXYZ(ep);
					FMLNetworkHandler.openGui((EntityPlayer) player, Unsaga.instance, Unsaga.GuiBartering, ep.worldObj,pos.x,pos.y,pos.z);
				}


			}
			if(id==Unsaga.GuiBlender){
				EntityPlayerMP ep = (EntityPlayerMP)player;
				FMLNetworkHandler.openGui((EntityPlayer) player, Unsaga.instance, Unsaga.GuiBlender, ep.worldObj, (int)ep.posX, (int)ep.posY, (int)ep.posZ);

			}
			if(id==Unsaga.GuiChest){
				//int chestlevel = data.readInt();
				int x = data.readInt();
				int y = data.readInt();
				int z = data.readInt();
				EntityPlayerMP ep = (EntityPlayerMP)player;
				FMLNetworkHandler.openGui((EntityPlayer) player, Unsaga.instance, Unsaga.GuiChest, ep.worldObj, x,y,z);

			}
		}
	}


	public static Packet getEquipGuiPacket(EntityClientPlayerMP client)
	{
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);

		try {
			dos.writeByte((byte)Unsaga.GuiEquipment);
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

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


	public static Packet getPacket(EventInteractVillager eventInteractVillager,
			byte guiBartering, EntityVillager villager) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);

		EventInteractVillager.writePacketData(dos,guiBartering,villager);

		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = "unsagamod_gui"; // ここでチャンネルを設定する
		packet.data    = bos.toByteArray();
		packet.length  = bos.size();
		packet.isChunkDataPacket = false;

		return packet;
	}


	public static Packet getPacket(ContainerBartering containerBartering, int id) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);

		ContainerBartering.writePacketData(dos,(byte)id);

		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = "unsagamod"; // ここでチャンネルを設定する
		packet.data    = bos.toByteArray();
		packet.length  = bos.size();
		packet.isChunkDataPacket = false;

		return packet;
	}


	public static Packet getMessagePacket(int mesnumber,int number) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);

		try {
			dos.writeInt(MESSAGE);
			dos.writeInt((int)mesnumber);
			dos.writeInt((int)number);
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = "unsagamod"; // ここでチャンネルを設定する
		packet.data    = bos.toByteArray();
		packet.length  = bos.size();
		packet.isChunkDataPacket = false;

		return packet;
	}


	protected static void writeMessagePacket(DataOutputStream dos, String string,int number) {
		try {
			dos.writeInt(MESSAGE);
			dos.writeUTF((String)string);
			dos.writeInt((int)number);
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

	}


	public static Packet getSoundPacket(int i) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);

		try {
			dos.writeInt(PLAY_SOUND);
			dos.writeInt(i);
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}


		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = "unsagamod"; // ここでチャンネルを設定する
		packet.data    = bos.toByteArray();
		packet.length  = bos.size();
		packet.isChunkDataPacket = false;

		return packet;
	}

	public static Packet getSoundPacket(int i,int entityid) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);

		try {
			dos.writeInt(PLAY_SOUND_AT);
			dos.writeInt(i);
			dos.writeInt(entityid);
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}


		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = "unsagamod"; // ここでチャンネルを設定する
		packet.data    = bos.toByteArray();
		packet.length  = bos.size();
		packet.isChunkDataPacket = false;

		return packet;
	}

	public static Packet getSoundPacket2(int i,int entityid) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);

		try {
			dos.writeInt(PLAY_SOUND2);
			dos.writeInt(i);
			dos.writeInt(entityid);
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}


		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = "unsagamod"; // ここでチャンネルを設定する
		packet.data    = bos.toByteArray();
		packet.length  = bos.size();
		packet.isChunkDataPacket = false;

		return packet;
	}

	public static Packet getParticlePacket(int particlenum,int entityid,int dens) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);

		try {
			dos.writeInt(PARTICLE_DENS);
			dos.writeInt(particlenum);
			dos.writeInt(entityid);
			dos.writeShort((short)dens);
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}


		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = "unsagamod"; // ここでチャンネルを設定する
		packet.data    = bos.toByteArray();
		packet.length  = bos.size();
		packet.isChunkDataPacket = false;

		return packet;
	}

	public static Packet getParticleToPosPacket(XYZPos xyz,int particlenum,int dens) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);

		try {
			dos.writeInt(PARTICLE_TO_POS);
			dos.writeInt(xyz.x);
			dos.writeInt(xyz.y);
			dos.writeInt(xyz.z);
			dos.writeInt(particlenum);
			dos.writeShort((short)dens);
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}


		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = "unsagamod"; // ここでチャンネルを設定する
		packet.data    = bos.toByteArray();
		packet.length  = bos.size();
		packet.isChunkDataPacket = false;

		return packet;
	}





	public static Packet getBlenderGuiPacket() {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);

		try {
			dos.writeByte((byte)Unsaga.GuiBlender);
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = "unsagamod_gui"; // ここでチャンネルを設定する
		packet.data    = bos.toByteArray();
		packet.length  = bos.size();
		packet.isChunkDataPacket = false;

		return packet;
	}


	public static Packet getBlendButtonPacket(Container blender,int id) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);

		UnsagaMagicContainerHandler.handleWritePacketEvent(blender,id,dos);

		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = "unsagamod"; // ここでチャンネルを設定する
		packet.data    = bos.toByteArray();
		packet.length  = bos.size();
		packet.isChunkDataPacket = false;

		return packet;
	}

	static{
		mesMap = new HashMap();
		mesMap.put(1, "msg.gained.ability");
		mesMap.put(2, "msg.gained.skill");
		particleMap = new HashMap();
		particleMap.put(1, "portal");
		particleMap.put(2, "heart");
		particleMap.put(3, "happyVillager");
		particleMap.put(4, "mobSpell");
		particleMap.put(5, "spell");
		soundMap = new HashMap();
		soundMap.put(1, "mob.endermen.portal");
		soundMap.put(2, "mob.ghast.fireball");
	}

	public static Packet getChestSyncPacket(int chestlevel,int x,int y,int z,boolean isServer, boolean trapOccured, boolean unlocked, boolean defused, boolean magicLock, boolean hasItemSet) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);

		int sw;
		if(isServer){
			sw = CHEST_SYNC_TOSERVER;
		}else{
			sw = CHEST_SYNC_TOCLIENT;
		}
		try {
			dos.writeInt(sw);
			dos.writeInt(chestlevel);
			dos.writeInt(x);
			dos.writeInt(y);
			dos.writeInt(z);
			dos.writeBoolean(trapOccured);
			dos.writeBoolean(unlocked);
			dos.writeBoolean(defused);
			dos.writeBoolean(magicLock);
			dos.writeBoolean(hasItemSet);

		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = "unsagamod"; // ここでチャンネルを設定する
		packet.data    = bos.toByteArray();
		packet.length  = bos.size();
		packet.isChunkDataPacket = false;

		return packet;
	}


	public static Packet getAbilityPacket() {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);

		try {
			dos.writeInt(ABILITY);

		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = "unsagamod"; // ここでチャンネルを設定する
		packet.data    = bos.toByteArray();
		packet.length  = bos.size();
		packet.isChunkDataPacket = false;

		return packet;
	}


	public static Packet getChestButtonPacket(int id) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);
		try {
			dos.writeInt(GUI_CHEST_BUTTON);
			dos.writeInt(id);
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = "unsagamod"; // ここでチャンネルを設定する
		packet.data    = bos.toByteArray();
		packet.length  = bos.size();
		packet.isChunkDataPacket = false;

		return packet;
	}

	public static Packet getChestGuiPacket(int x, int y, int z)
	{
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);

		try {
			dos.writeByte((byte)Unsaga.GuiChest);
			dos.writeInt((int)x);
			dos.writeInt((int)y);
			dos.writeInt((int)z);
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = "unsagamod_gui"; // ここでチャンネルを設定する
		packet.data    = bos.toByteArray();
		packet.length  = bos.size();
		packet.isChunkDataPacket = false;

		return packet;
	}
}

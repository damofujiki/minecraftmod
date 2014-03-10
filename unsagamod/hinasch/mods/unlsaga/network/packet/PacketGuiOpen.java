package hinasch.mods.unlsaga.network.packet;

import hinasch.lib.AbstractPacket;
import hinasch.lib.HSLibs;
import hinasch.lib.XYZPos;
import hinasch.mods.unlsaga.Unsaga;
import hinasch.mods.unlsaga.core.event.ExtendedPlayerData;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import cpw.mods.fml.common.network.internal.FMLNetworkHandler;

public class PacketGuiOpen extends AbstractPacket{

	protected byte guinum;
	protected XYZPos pos;
	protected int entityid;
	
	public PacketGuiOpen(){
		
	}
	
	public PacketGuiOpen(int numberGui){
	
		this.guinum = (byte)numberGui;
	}
	
	public PacketGuiOpen(int numberGui,XYZPos pos){
		this(numberGui);
		this.pos = pos;
	}
	
	public PacketGuiOpen(int numberGui,EntityVillager entity){
		this(numberGui);
		this.entityid = entity.getEntityId();
	}
	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
		buffer.writeByte(this.guinum);
		if(this.guinum==Unsaga.guiNumber.chest){
			buffer.writeInt(pos.x);
			buffer.writeInt(pos.y);
			buffer.writeInt(pos.z);
		}
		if(this.guinum==Unsaga.guiNumber.smith || this.guinum==Unsaga.guiNumber.bartering){
			buffer.writeInt(this.entityid);
		}
		
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
		this.guinum = buffer.readByte();
		if(this.guinum==Unsaga.guiNumber.chest){
			this.pos = new XYZPos(0,0,0);
			this.pos.x = buffer.readInt();
			this.pos.y = buffer.readInt();
			this.pos.z = buffer.readInt();
		}
		if(this.guinum==Unsaga.guiNumber.smith || this.guinum==Unsaga.guiNumber.bartering){
			this.entityid = buffer.readInt();
		}
	}

	@Override
	public void handleClientSide(EntityPlayer player) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	public void handleServerSide(EntityPlayer player) {
		if(this.guinum==Unsaga.guiNumber.equipment){
			Unsaga.debug("GuiEquipment");
			EntityPlayerMP ep = (EntityPlayerMP)player;
			FMLNetworkHandler.openGui((EntityPlayer) player, Unsaga.instance, Unsaga.guiNumber.equipment, ep.worldObj, (int)ep.posX, (int)ep.posY, (int)ep.posZ);
		}
		if(this.guinum==Unsaga.guiNumber.smith){
			EntityPlayerMP ep = (EntityPlayerMP)player;
			EntityVillager villager = (EntityVillager) ep.worldObj.getEntityByID(this.entityid);
			if(villager!=null && HSLibs.getExtendedData(ExtendedPlayerData.key,ep).isPresent()){
				((ExtendedPlayerData)HSLibs.getExtendedData(ExtendedPlayerData.key, ep).get()).setMerchant(villager);
				XYZPos pos = XYZPos.entityPosToXYZ(ep);
				FMLNetworkHandler.openGui((EntityPlayer) player, Unsaga.instance, Unsaga.guiNumber.smith, ep.worldObj,pos.x,pos.y,pos.z);
			}


		}
		if(this.guinum==Unsaga.guiNumber.bartering){
			EntityPlayerMP ep = (EntityPlayerMP)player;
			EntityVillager villager = (EntityVillager) ep.worldObj.getEntityByID(this.entityid);
			if(villager!=null && HSLibs.getExtendedData(ExtendedPlayerData.key,ep).isPresent()){
				((ExtendedPlayerData)HSLibs.getExtendedData(ExtendedPlayerData.key, ep).get()).setMerchant(villager);
				XYZPos pos = XYZPos.entityPosToXYZ(ep);
				FMLNetworkHandler.openGui((EntityPlayer) player, Unsaga.instance, Unsaga.guiNumber.bartering, ep.worldObj,pos.x,pos.y,pos.z);
			}


		}
		if(this.guinum==Unsaga.guiNumber.blender){
			EntityPlayerMP ep = (EntityPlayerMP)player;
			FMLNetworkHandler.openGui((EntityPlayer) player, Unsaga.instance, Unsaga.guiNumber.blender, ep.worldObj, (int)ep.posX, (int)ep.posY, (int)ep.posZ);

		}
		if(this.guinum==Unsaga.guiNumber.chest){
			Unsaga.debug("パケトはきてます");
			EntityPlayerMP ep = (EntityPlayerMP)player;
			FMLNetworkHandler.openGui((EntityPlayer) player, Unsaga.instance, Unsaga.guiNumber.chest, ep.worldObj, pos.x,pos.y,pos.z);

		}
	}

}

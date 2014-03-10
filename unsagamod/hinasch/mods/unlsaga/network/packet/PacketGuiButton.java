package hinasch.mods.unlsaga.network.packet;

import hinasch.lib.AbstractPacket;
import hinasch.mods.unlsaga.Unsaga;
import hinasch.mods.unlsaga.client.gui.GuiSmithUnsaga;
import hinasch.mods.unlsaga.inventory.container.ContainerBartering;
import hinasch.mods.unlsaga.inventory.container.ContainerChestUnsaga;
import hinasch.mods.unlsaga.inventory.container.ContainerSmithUnsaga;
import hinasch.mods.unlsaga.misc.module.UnsagaMagicContainerHandler;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;

public class PacketGuiButton extends AbstractPacket{

	protected int id;
	protected Object[] args;
	public static final int GUI_FORGE = 0x01;
	public static final int GUI_BARTERING = 0x02;
	public static final int GUI_CHEST = 0x03;
	public static final int GUI_BLEND = 0x04;
	
	public PacketGuiButton(){
		
	}
	
	public PacketGuiButton(int guinumber,Object... args){
		this.id = guinumber;
		this.args = args;
	}
	
	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
		buffer.writeInt(this.id);
		if(this.id==GUI_BARTERING){
			ContainerBartering.writePacketData(buffer,(Byte)args[0] );
		}
		if(this.id==GUI_CHEST){
			buffer.writeInt((Integer)args[0]);
		}
		if(this.id==GUI_FORGE){
			ContainerSmithUnsaga.writePacketData(buffer,(Integer)args[0],(Byte)args[1]);
		}
		if(this.id==GUI_BLEND){
			
			buffer.writeInt((Integer)args[0]);
		}
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
		this.id = buffer.readInt();
		
		if(this.id==GUI_BARTERING){
			this.args = new Object[1];
			args[0] = (Byte)buffer.readByte();
		}
		if(this.id==GUI_CHEST){
			this.args = new Object[1];
			args[0] = (Integer)buffer.readInt();
		}
		if(this.id==GUI_FORGE){
			this.args = new Object[2];
			args[0] = (Integer)buffer.readInt();
			if((Integer)args[0]==GuiSmithUnsaga.CATEGORY){
				args[1] = (Byte)buffer.readByte();
			}
			
		}
		if(this.id==GUI_BLEND){
			this.args = new Object[1];
			args[0] = (Integer)buffer.readInt();
		}
	}

	@Override
	public void handleClientSide(EntityPlayer player) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	public void handleServerSide(EntityPlayer player) {
		if(id==GUI_FORGE){
			Container container = ((EntityPlayerMP)player).openContainer;
			if(container != null && container instanceof ContainerSmithUnsaga)
			{
				Unsaga.debug("コンテナ開いてます");
				((ContainerSmithUnsaga)container).readPacketData(args);
				((ContainerSmithUnsaga)container).onPacketData();
			}
		}
		if(id==GUI_BARTERING){
			Container container = ((EntityPlayerMP)player).openContainer;
			if(container != null && container instanceof ContainerBartering)
			{
				//UnsagaCore.debuglib.addChatMessage("okok");
				Unsaga.debug("コンテナ開いてます");
				((ContainerBartering)container).readPacketData(args);
				((ContainerBartering)container).onPacketData();
			}
		}
		if(id==GUI_BLEND){
			UnsagaMagicContainerHandler.handleBlendButtonEvent((EntityPlayerMP)player,args);
		}
		if(id==GUI_CHEST){
			Container container = ((EntityPlayerMP)player).openContainer;
			if(container != null && container instanceof ContainerChestUnsaga)
			{
				((ContainerChestUnsaga)container).readPacketData(args);
				((ContainerChestUnsaga)container).onPacketData();
			}
		}
		
	}

}

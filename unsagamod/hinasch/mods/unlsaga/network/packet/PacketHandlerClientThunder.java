package hinasch.mods.unlsaga.network.packet;

import hinasch.lib.AbstractPacket;
import hinasch.lib.XYZPos;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;

public class PacketHandlerClientThunder extends AbstractPacket{

	protected XYZPos position;
	public PacketHandlerClientThunder(){
		
	}
	
	public PacketHandlerClientThunder(XYZPos pos){
		this.position = pos;
	}
	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
		// TODO 自動生成されたメソッド・スタブ
		PacketUtil.XYZPosToPacket(buffer, position);
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
		// TODO 自動生成されたメソッド・スタブ
		this.position = PacketUtil.bufferToXYZPos(buffer);
	}

	@Override
	public void handleClientSide(EntityPlayer player) {
		EntityLightningBolt bolt = new EntityLightningBolt(player.worldObj,position.dx,position.dy,position.dz);
		player.worldObj.spawnEntityInWorld(bolt);
	}

	@Override
	public void handleServerSide(EntityPlayer player) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

}

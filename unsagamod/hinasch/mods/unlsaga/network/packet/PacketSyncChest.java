package hinasch.mods.unlsaga.network.packet;

import hinasch.lib.AbstractPacket;
import hinasch.lib.XYZPos;
import hinasch.mods.unlsaga.tileentity.TileEntityChestUnsaga;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class PacketSyncChest extends AbstractPacket{

	protected XYZPos pos;
	protected int lv;
	
	public PacketSyncChest(){
		
	}
	
	public PacketSyncChest(XYZPos pos,int lv){
		this.pos = pos;
		this.lv = lv;
	}
	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
		PacketUtil.XYZPosToPacket(buffer, pos);
		buffer.writeInt(lv);
		
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
		this.pos = PacketUtil.bufferToXYZPos(buffer);
		this.lv = buffer.readInt();
	}

	@Override
	public void handleClientSide(EntityPlayer player) {
		World world = player.worldObj;
		TileEntityChestUnsaga chest = (TileEntityChestUnsaga) world.getTileEntity(pos.x, pos.y, pos.z);
		if(chest!=null){
			chest.setChestLevel(lv);
		}
		
	}

	@Override
	public void handleServerSide(EntityPlayer player) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

}

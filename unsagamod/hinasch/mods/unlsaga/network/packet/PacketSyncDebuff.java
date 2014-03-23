package hinasch.mods.unlsaga.network.packet;

import hinasch.lib.AbstractPacket;
import hinasch.mods.unlsaga.misc.debuff.Debuff;
import hinasch.mods.unlsaga.misc.debuff.Debuffs;
import hinasch.mods.unlsaga.misc.debuff.livingdebuff.LivingDebuff;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

public class PacketSyncDebuff extends AbstractPacket{

	protected int number;
	protected int entityid;
	public PacketSyncDebuff(){
		
	}
	
	public PacketSyncDebuff(int entityid,int number){
		this.number = number;
		this.entityid = entityid;
	}
	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
		buffer.writeInt(entityid);
		buffer.writeInt(number);
		
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
		this.entityid = buffer.readInt();
		this.number = buffer.readInt();
		
	}

	@Override
	public void handleClientSide(EntityPlayer player) {
		if(Debuffs.getDebuff(number)!=null && player.worldObj.getEntityByID(entityid)!=null){
			Debuff debuff = Debuffs.getDebuff(number);
			EntityLivingBase entity = (EntityLivingBase) player.worldObj.getEntityByID(entityid);
			if(LivingDebuff.hasDebuff(entity, debuff)){
				LivingDebuff.removeDebuff(entity, debuff);
			}
		}
		
		
	}

	@Override
	public void handleServerSide(EntityPlayer player) {
		if(Debuffs.getDebuff(number)!=null && player.worldObj.getEntityByID(entityid)!=null){
			Debuff debuff = Debuffs.getDebuff(number);
			EntityLivingBase entity = (EntityLivingBase) player.worldObj.getEntityByID(entityid);
			if(LivingDebuff.hasDebuff(entity, debuff)){
				LivingDebuff.removeDebuff(entity, debuff);
			}
		}
	}

}

package hinasch.mods.unlsaga.network.packet;

import hinasch.lib.AbstractPacket;
import hinasch.lib.StaticWords;
import hinasch.lib.XYZPos;
import hinasch.mods.unlsaga.Unsaga;
import hinasch.mods.unlsaga.client.UnsagaParticles;
import hinasch.mods.unlsaga.client.particle.EntityUnsagaFX;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.util.Random;

import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.client.FMLClientHandler;

public class PacketParticle extends AbstractPacket{
	
	protected final int PARTICLE_DENS = 0x00;
	protected final int PARTICLE_TO_POS = 0x01;
	
	
	protected int packetId;
	protected int particleNumber;
	protected int entityId;
	protected short dens;
	
	protected XYZPos xyz;
	
	public PacketParticle(){
		
	}
	
	public PacketParticle(int particlenum,int entityid,int dens){
		this.packetId = PARTICLE_DENS;
		this.particleNumber = particlenum;
		this.entityId = entityid;
		this.dens = (short)dens;
	}
	
	public PacketParticle(XYZPos pos,int particlenum,int dens){
		this.packetId = PARTICLE_TO_POS;
		
		this.particleNumber = particlenum;
		this.xyz = pos;
		this.dens = (short)dens;
		
	}
	
	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
		buffer.writeInt(packetId);
		buffer.writeInt(particleNumber);
		buffer.writeShort(dens);
		if(this.packetId==PARTICLE_DENS){
			buffer.writeInt(entityId);
		}
		if(this.packetId==PARTICLE_TO_POS){
			PacketUtil.XYZPosToPacket(buffer, this.xyz);
		}
		
		
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
		this.packetId = buffer.readInt();
		this.particleNumber = buffer.readInt();
		this.dens = buffer.readShort();
		if(this.packetId==PARTICLE_DENS){
			this.entityId = buffer.readInt();
		}
		if(this.packetId==PARTICLE_TO_POS){
			this.xyz = PacketUtil.bufferToXYZPos(buffer);
		}
		
	}

	@Override
	public void handleClientSide(EntityPlayer player) {
		if(packetId==PARTICLE_DENS){

			String particlestr = "";
			if(this.particleNumber>=200){
				particlestr = this.particleNumber==200 ? "stone" : this.particleNumber==201 ? "leave" : this.particleNumber==202 ? "bubble" : "none";
			}else{
				particlestr = StaticWords.particleMap.get(this.particleNumber);
			}
			
			EntityClientPlayerMP ep = (EntityClientPlayerMP)player;
			Entity en = ep.worldObj.getEntityByID(this.entityId);
			if(en!=null){
				XYZPos epo = XYZPos.entityPosToXYZ(en);
				for (int i = 0; i < dens; ++i)
				{
					if(this.particleNumber>=200){
						Unsaga.debug("出てますパーティ");
						Random par5Random = ep.getRNG();
						double r = 0.8D + par5Random.nextDouble();
						double t = par5Random.nextDouble() * 2 * Math.PI;
			 
						double d0 = en.posX + 0.5D + r * Math.sin(t);
						double d1 = en.posY + 0.0D + par5Random.nextDouble();
						double d2 = en.posZ + 0.5D + r * Math.cos(t);
			 
						// パーティクルの移動速度。+0.03Dで上昇する
						double d3 = Math.sin(t) / 64.0D;
						double d4 = 0.03D;
						double d5 = Math.cos(t) / 64.0D;
						
						EntityUnsagaFX entityFX = new EntityUnsagaFX(ep.worldObj,d0,d1,d2,d3,d4,d5);
						entityFX.setParticleIcon(UnsagaParticles.getInstance().getIcon(particlestr));
						Unsaga.debug(particlestr);
						FMLClientHandler.instance().getClient().effectRenderer.addEffect(entityFX);
					}else{
						boolean flag = false;
						if(particlestr.equals(StaticWords.particleBubble) && !flag){
							float f4 = 0.25F;
							flag = true;
							ep.worldObj.spawnParticle(StaticWords.particleBubble, en.posX - en.motionX * (double)f4, en.posY - en.motionY * (double)f4, en.posZ - en.motionZ * (double)f4, en.motionX, en.motionY, en.motionZ);
						}
						if(particlestr.equals(StaticWords.particleReddust) && !flag){
							flag = true;
							ep.worldObj.spawnParticle(particlestr, en.posX, en.posY + ep.worldObj.rand.nextDouble() * 2.0D, en.posZ, 0.0D,0.0D,0.0D);
						}
						if(!flag){
							flag = true;
							ep.worldObj.spawnParticle(particlestr, en.posX, en.posY + ep.worldObj.rand.nextDouble() * 2.0D, en.posZ, ep.worldObj.rand.nextGaussian()*2, 0.0D, ep.worldObj.rand.nextGaussian()*2);
						}
							
						
						
					}
						
					
				}
			}

		}
		if(packetId==PARTICLE_TO_POS){
			String particlestr = StaticWords.particleMap.get(this.particleNumber);
			EntityClientPlayerMP ep = (EntityClientPlayerMP)player;
			for (int i = 0; i < dens; ++i)
			{
				ep.worldObj.spawnParticle(particlestr, (double)xyz.x, (double)xyz.y + ep.worldObj.rand.nextDouble() * 2.0D, (double)xyz.z, ep.worldObj.rand.nextGaussian()*2, 0.0D, ep.worldObj.rand.nextGaussian()*2);
			}


		}
		
	}

	@Override
	public void handleServerSide(EntityPlayer player) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

}

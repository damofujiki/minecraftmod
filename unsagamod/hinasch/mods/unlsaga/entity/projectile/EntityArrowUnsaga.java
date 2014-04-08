package hinasch.mods.unlsaga.entity.projectile;

import hinasch.mods.unlsaga.Unsaga;
import hinasch.mods.unlsaga.misc.debuff.Debuffs;
import hinasch.mods.unlsaga.misc.debuff.livingdebuff.LivingDebuff;
import hinasch.mods.unlsaga.misc.debuff.livingdebuff.LivingState;
import hinasch.mods.unlsaga.network.packet.PacketParticle;
import hinasch.mods.unlsaga.network.packet.PacketUtil;

import java.util.List;

import com.hinasch.lib.HSLibs;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class EntityArrowUnsaga extends EntityArrowThrowable implements IProjectile{

	private boolean isZapper = false;
	private boolean isExorcist = false;
	private boolean isShadowStitch = false;
	private int type;
	public float charge = 0;
	public int particleTick;

	public EntityArrowUnsaga(World par1World) {
		super(par1World);
		this.particleTick = 0;
		// TODO Auto-generated constructor stub
	}

	public EntityArrowUnsaga(World par2World, EntityPlayer par3EntityPlayer, float f) {

		super(par2World,par3EntityPlayer,f);
	}

	public void setZapper(boolean par1){
		this.isZapper = par1;
	}

	public void setExorcist(boolean par1){
		this.isExorcist = par1;
	}

	public void setShadowStitch(boolean par1){
		this.isShadowStitch = par1;
	}

	public boolean isZapper(){
		return this.isZapper;
	}

	public boolean isExorcist(){
		return this.isExorcist;
	}

	public boolean isShadowStitching(){
		return this.isShadowStitch;
	}
	private void setFromType(){
		if(this.type==1){
			this.isZapper = true;
		}
		if(this.type==2){
			this.isExorcist = true;
		}
		if(this.type==3){
			this.isShadowStitch = true;
		}
		return;
	}

	@Override
	public void onUpdate(){
		super.onUpdate();
		if(this.particleTick % 6 ==0){
			if(this.isExorcist && !this.inGround){
				PacketParticle pp = new PacketParticle(3,this.getEntityId(),1);
				Unsaga.packetPipeline.sendToAllAround(pp, PacketUtil.getTargetPointNear(this));
				//PacketParticle pp = new PacketParticle(3,this.entityId,1);
	        	//PacketDispatcher.sendPacketToAllPlayers(pp.getPacket());
	        }
		}
//		Random rand = this.rand;
//		if(this.isExorcist && this.shootingEntity instanceof EntityPlayer){
//			boolean flag = this.rand.nextInt(9)==0;
//			if(flag && !this.inGround){
//				//PacketDispatcher.sendPacketToPlayer(PacketHandler.getParticlePacket(2, this.entityId), (Player) this.shootingEntity);
//				PacketDispatcher.sendPacketToAllPlayers(PacketHandler.getParticlePacket(3, this.entityId,4));
//			}
//		}
	}

	public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.writeEntityToNBT(par1NBTTagCompound);
		if(this.isZapper){
			par1NBTTagCompound.setByte("type", (byte)1);
		}
		if(this.isExorcist){
			par1NBTTagCompound.setByte("type", (byte)2);
		}
		if(this.isShadowStitch){
			par1NBTTagCompound.setByte("type", (byte)3);
		}
	}

	public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.readEntityFromNBT(par1NBTTagCompound);

		if(par1NBTTagCompound.hasKey("type")){
			this.type = par1NBTTagCompound.getByte("type");
		}else{
			this.type = 0;
		}
		this.setFromType();
	}

	@Override
	public void onArrowImpactOnTile(){
		if(this.isShadowStitch){
			AxisAlignedBB bb = HSLibs.getBounding(this.posX, this.posY, this.posZ, 2.0D, 2.0D);
			List<EntityLivingBase> livings = this.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, bb);
			for(EntityLivingBase living:livings){
				if(living!=this.shootingEntity){
					living.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id,160,3));
					LivingDebuff.addLivingDebuff(living, new LivingState(Debuffs.sleep,10,false));
				}

			}
		}

	}
}

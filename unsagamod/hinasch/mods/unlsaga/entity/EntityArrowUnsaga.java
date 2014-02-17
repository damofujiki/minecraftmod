package hinasch.mods.unlsaga.entity;

import hinasch.lib.HSLibs;

import java.util.List;

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
	
	public EntityArrowUnsaga(World par1World) {
		super(par1World);
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
    			living.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id,160,3));
    		}
    	}

	}
}

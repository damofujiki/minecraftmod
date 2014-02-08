package hinasch.mods.unlsaga.entity;

import net.minecraft.entity.IProjectile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityArrowUnsaga extends EntityArrowThrowable implements IProjectile{

	private boolean isZapper = false;
	private boolean isExorcist = false;
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

	public void setSticker(boolean par1){
		this.isExorcist = par1;
	}

	public boolean isZapper(){
		return this.isZapper;
	}
	
	public boolean isExorcist(){
		return this.isExorcist;
	}
	private void setFromType(){
		if(this.type==1){
			this.isZapper = true;
		}
		if(this.type==2){
			this.isExorcist = true;
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
    }
    
    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
    {
    	super.readEntityFromNBT(par1NBTTagCompound);

    	if(par1NBTTagCompound.hasKey("type")){
    		this.type = par1NBTTagCompound.getByte("name");
    	}else{
    		this.type = 0;
    	}
    	this.setFromType();
    }

}

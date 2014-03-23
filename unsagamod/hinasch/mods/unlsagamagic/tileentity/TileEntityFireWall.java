package hinasch.mods.unlsagamagic.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileEntityFireWall extends TileEntity{

	protected int remaining = 100;
	
	public TileEntityFireWall(){
		
	}
	
	public void init(int par1){
		this.remaining = par1;
	}
	
	@Override
    public void readFromNBT(NBTTagCompound compound)
    {

    	super.readFromNBT(compound);
    	this.remaining = compound.getInteger("remaining");
    	
    }
	
    @Override
    public void writeToNBT(NBTTagCompound compound)
    {

    	super.writeToNBT(compound);
    	compound.setInteger("remaining", this.remaining);
    }
    
    @Override
    public void updateEntity() {
    	this.remaining -=1;
    	
    	if(this.remaining<0){
    		this.setDeath();
    	}
    }
    
    protected void setDeath(){
    	this.worldObj.setBlockToAir(xCoord, yCoord, zCoord);
    }
}

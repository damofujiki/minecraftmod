package hinasch.lib;

import net.minecraft.world.World;

public class PairID {

	public int id;
	public int metadata;
	
	public PairID(){
		this.id = 0;
		this.metadata = 0;
	}
	
	public PairID(int par1,int par2){
		this.id = par1;
		this.metadata = par2;
	}
	
	@Override
	public String toString(){
		return String.valueOf(this.id) + ","+String.valueOf(this.metadata);
	}
	
	public void setData(int par1,int par2){
		this.id = par1;
		this.metadata = par2;
	}
	
	public void getFromWorld(World world,int x,int y,int z){
		this.id = world.getBlockId(x, y, z);
		this.metadata = world.getBlockMetadata(x, y, z);
		
	}
}

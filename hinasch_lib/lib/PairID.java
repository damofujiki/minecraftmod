package hinasch.lib;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class PairID {

	//public int id;
	public int metadata;
	//option
	public int stack;
	
	public List<PairID> sameBlocks;
	
	public boolean checkMetadata;
	
	public Block blockObj;
	public Item itemObj;
	
	public PairID(){
		//this.id = 0;
		this.metadata = 0;
		this.stack = 0;
		this.sameBlocks = new ArrayList();
		this.checkMetadata = false;
	}
	
	public PairID(int par2){
		//this.id = par1;
		this.metadata = par2;
		
	}
	
	public PairID(Item item, int itemDamage) {
		this(itemDamage);
		this.itemObj = item;
		this.sameBlocks = new ArrayList();
		this.checkMetadata = true;
	}
	
	public PairID(Block block,int blockDamage){
		this(blockDamage);
		this.blockObj = block;
		this.sameBlocks = new ArrayList();
		this.checkMetadata = true;
	}

	@Override
	public String toString(){
		return blockObj!=null ? blockObj.getLocalizedName() : itemObj.getUnlocalizedName() + ","+String.valueOf(this.metadata);
	}
	
	public void setData(Object par1,int par2){
		//this.id = par1;
		if(par1 instanceof Block){
			this.blockObj = (Block)par1;
		}
		if(par1 instanceof Item){
			this.itemObj = (Item)par1;
		}
		this.metadata = par2;
	}
	
	public void getFromWorld(World world,int x,int y,int z){
		this.blockObj = world.getBlock(x, y, z);;
		this.metadata = world.getBlockMetadata(x, y, z);
		
	}
	
	public boolean equals(PairID pairid){
		if(this.blockObj!=null){
			if(this.blockObj==pairid.blockObj){
				if(this.metadata==pairid.metadata && this.checkMetadata || !this.checkMetadata){
					return true;
				}
			}
		}
		if(this.itemObj!=null){
			if(this.itemObj==pairid.itemObj){
				if(this.metadata==pairid.metadata && this.checkMetadata || !this.checkMetadata){
					return true;
				}
			}
		}

		
		return false;
	}
	

	public PairID setCheckMetadata(boolean par1){
		this.checkMetadata = par1;
		return this;
	}
	
	public boolean equalsOrSameBlock(PairID pairid){
		if(this.equals(pairid)){
			return true;
		}
		for(PairID blockdata:this.sameBlocks){
			if(blockdata.equals(pairid)){
				return true;
			}
		}
		return false;
	}
	
	public static PairID getBlockFromWorld(World world,XYZPos pos){
		
		Block block = world.getBlock(pos.x,pos.y,pos.z);
		int meta = world.getBlockMetadata(pos.x, pos.y, pos.z);
		PairID pairid = new PairID(block,meta);
		return pairid;
	}
}

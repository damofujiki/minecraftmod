package com.hinasch.lib;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class WorldHelper {

	public static XYZPos UP = new XYZPos(0,+1,0);
	public static XYZPos DOWN = new XYZPos(0,-1,0);
	public World world;;
	public WorldHelper(World world){
		this.world = world;
	}
	
	public void setBlock(XYZPos pos,PairID pairid){
		if(pairid.getBlockObject()!=null){
			world.setBlock(pos.x, pos.y, pos.z, pairid.getBlockObject(), pairid.getMeta(), 3);
		}
		
	}
	
	public void setBlock(XYZPos pos,Block block){
		world.setBlock(pos.x, pos.y, pos.z, block);
		
		
	}
	
	public void setBlockToAir(XYZPos pos){
		world.setBlockToAir(pos.x, pos.y, pos.z);
	}
	
	public Block getBlock(XYZPos pos){
		return world.getBlock(pos.x, pos.y, pos.z);
	}
	
	public Material getMaterial(XYZPos pos){
		return world.getBlock(pos.x, pos.y,pos.z).getMaterial();
	}
	
	public int getBlockMetadata(XYZPos pos){
		return world.getBlockMetadata(pos.x, pos.y, pos.z);
	}
	
	public PairID getBlockDatas(XYZPos pos){
		PairID pairID = new PairID(world.getBlock(pos.x, pos.y, pos.z),this.getBlockMetadata(pos));
		return pairID;
	}
	
	public boolean isAirBlock(XYZPos pos){
		return world.isAirBlock(pos.x, pos.y, pos.z);
	}
	
	public float getBlockHardness(XYZPos pos){
		return this.getBlock(pos).getBlockHardness(world, pos.x, pos.y, pos.z);
	}
	
	public int getBlockHarvestLevel(XYZPos pos){
		return this.getBlock(pos).getHarvestLevel(this.getBlockMetadata(pos));
	}
	
	public void setBlockMetadata(XYZPos pos,int meta,int flag){
		this.world.setBlockMetadataWithNotify(pos.x, pos.y, pos.z, meta, flag);
	}
	
	public void addBlockMetadata(XYZPos pos,int flag){
		int meta = this.getBlockMetadata(pos);
		if(meta<=0 && meta>=15){
			this.setBlockMetadata(pos, meta+1, flag);
		}
	}
	public XYZPos findNearMaterial(World world,Material material,XYZPos pos,int range){
		ScanHelper scan = new ScanHelper(pos.x,pos.y,pos.z,range,range);
		scan.setWorld(world);
		for(;scan.hasNext();scan.next()){
			if(scan.getBlock().getMaterial()==material){
				return scan.getAsXYZPos();
			}
		}
		return null;
	}
	
	public XYZPos findNearBlock(World world,Block block,XYZPos pos,int range){
		ScanHelper scan = new ScanHelper(pos.x,pos.y,pos.z,range,range);
		scan.setWorld(world);
		for(;scan.hasNext();scan.next()){
			if(scan.getBlock()==block){
				return scan.getAsXYZPos();
			}
		}
		return null;
	}
	
	public TileEntity getTileEntity(XYZPos pos){
		return this.world.getTileEntity(pos.x, pos.y, pos.z);
	}
	
	public boolean isReplaceable(XYZPos pos){
		return this.getBlock(pos).isReplaceable(world, pos.x, pos.y, pos.z);
	}
}

package hinasch.lib;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRedstoneOre;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public abstract class AbstractScanner {
	
	protected ArrayList<XYZPos> scheduledBreak;
	protected PairID compareBlock;
	protected XYZPos startPoint;
	protected WorldHelper helper;


	public AbstractScanner(PairID compareBlock,XYZPos startpoint){
		this.compareBlock = compareBlock;
		this.scheduledBreak = new ArrayList();
		this.scheduledBreak.add(startpoint);
		this.startPoint = startpoint;
	}
	public void doScan(World world,int length){
		this.helper = new WorldHelper(world);
		for(int i=0;i<length;i++){
			List<XYZPos> removeList = new ArrayList();
			List<XYZPos> addList = new ArrayList();
			for(XYZPos pos:scheduledBreak){
				Block block = helper.getBlock(pos);
				//Block block = world.getBlock(pos.x, pos.y, pos.z);
				if(block!=Blocks.air){

					this.hook(world, block, pos);
				}

				removeList.add(pos);
				for(XYZPos round:XYZPos.around){
					XYZPos addedPos = pos.add(round);
					PairID roundBlockData = helper.getBlockDatas(addedPos);
					if(compareBlock.equalsOrSameBlock(roundBlockData)){
						addList.add(addedPos);
					}
					if((compareBlock.getBlockObject() instanceof BlockRedstoneOre) && (roundBlockData.getBlockObject() instanceof BlockRedstoneOre)){
						addList.add(addedPos);
					}

				}
			}
			//消去を同時にやると怒られるので、分けてやる
			for(XYZPos rm:removeList){
				scheduledBreak.remove(rm);
			}
			
			for(XYZPos ad:addList){
				scheduledBreak.add(ad);
			}

		}
	}
	
	abstract public void hook(World world,Block currentBlock,XYZPos currentPos);
}

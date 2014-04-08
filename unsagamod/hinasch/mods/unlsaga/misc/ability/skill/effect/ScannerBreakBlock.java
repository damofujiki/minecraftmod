package hinasch.mods.unlsaga.misc.ability.skill.effect;

import com.hinasch.lib.AbstractScanner;
import com.hinasch.lib.HSLibs;
import com.hinasch.lib.PairID;
import com.hinasch.lib.XYZPos;

import net.minecraft.block.Block;
import net.minecraft.world.World;

public class ScannerBreakBlock extends AbstractScanner{

	protected int breaked;
	
	public ScannerBreakBlock(PairID compareBlock, XYZPos startpoint) {
		super(compareBlock, startpoint);
		this.breaked = 0;
		// TODO 自動生成されたコンストラクター・スタブ
	}

	@Override
	public void hook(World world, Block currentBlock, XYZPos currentPos) {

		currentBlock.dropXpOnBlockBreak(world, startPoint.x, startPoint.y, startPoint.z, currentBlock.getExpDrop(world, compareBlock.getMeta(), 0));
		HSLibs.playBlockBreakSFX(world, currentPos, compareBlock);
		breaked += 1;


	}



}

package hinasch.mods.unlsaga.misc.ability.skill.effect;

import hinasch.lib.AbstractScanner;
import hinasch.lib.HSLibs;
import hinasch.lib.PairID;
import hinasch.lib.XYZPos;
import net.minecraft.block.Block;
import net.minecraft.world.World;

public class ScannerBreakBlock extends AbstractScanner{

	public ScannerBreakBlock(PairID compareBlock, XYZPos startpoint) {
		super(compareBlock, startpoint);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	@Override
	public void hook(World world, Block currentBlock, XYZPos currentPos) {

		currentBlock.dropXpOnBlockBreak(world, startPoint.x, startPoint.y, startPoint.z, currentBlock.getExpDrop(world, compareBlock.metadata, 0));
		HSLibs.playBlockBreakSFX(world, currentPos, compareBlock);


	}



}

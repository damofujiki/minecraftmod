package hinasch.mods.unlsaga.block;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGeneratorUnsaga extends WorldGenerator{

	@Override
	public boolean generate(World var1, Random var2, int var3, int var4,
			int var5) {
		switch(var1.provider.dimensionId){
		case 0:
			generateOverworld(var1,var2,var3*16,var4*16);
			break;
		case 1:
			generateEnd(var1,var2,var3*16,var4*16);
			break;
		case -1:
			generateNether(var1,var2,var3*16,var4*16);
			break;
		
		}
		return false;
	}

	private void generateEnd(World var1, Random var2, int var3, int var4) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	private void generateNether(World var1, Random var2, int var3, int var4) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	private void generateOverworld(World var1, Random var2, int var3, int var4) {
		
		
	}

}

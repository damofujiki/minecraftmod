package hinasch.mods.unlsaga.core.init;

import hinasch.mods.tsukiyotake.lib.PropertyCustom;
import hinasch.mods.unlsaga.Unsaga;
import hinasch.mods.unlsaga.block.BlockChestUnsaga;
import hinasch.mods.unlsaga.lib.HSLibs;
import hinasch.mods.unlsaga.tileentity.TileEntityChestUnsaga;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.Configuration;
import cpw.mods.fml.common.registry.GameRegistry;

public class UnsagaBlocks {

	public static int blockFallStoneID;
	public static int blockChestUnsagaID;
	public static int blockBlenderID;
	public static int blockUnsagaOresID;
	
	public static Block blockFallStone;
	public static Block blockChestUnsaga;
	public static Block blockBlender;
	public static Block blockUnsagaOres;
	
	public static Class<? extends TileEntity> tileEntityChestUnsaga;

	//public static Class<? extends ItemBlockOreUnsaga> classItemBlockOre;
	
	public static void loadConfig(Configuration config) {
		PropertyCustom prop = new PropertyCustom(new String[]{"BlockID.FallingStone","BlockID.Chest.Unsaga","BlockID.Ores"
				});
		prop.setValues(new Integer[]{1000,1001,1002});
		prop.setCategoriesAll(config.CATEGORY_BLOCK);
		prop.buildProps(config);
		
		blockFallStoneID = prop.getProp(0).getInt();
		blockChestUnsagaID = prop.getProp(1).getInt();
		blockUnsagaOresID = prop.getProp(2).getInt();
				
	}

	public static void registerValues() {

		blockChestUnsaga = new BlockChestUnsaga(blockChestUnsagaID,0).setUnlocalizedName("unsaga.blockchest").setCreativeTab(Unsaga.tabUnsaga);
		
		tileEntityChestUnsaga = TileEntityChestUnsaga.class;
		
		GameRegistry.registerTileEntity(tileEntityChestUnsaga, "unsaga.tileEntityChestUnsaga");
		GameRegistry.registerBlock(blockChestUnsaga,"unsaga.blockChestUnsaga");
	}


	public static void setLocalize(){
		HSLibs.langSet("Bonus Chest", "宝箱", blockChestUnsaga);
	}
}

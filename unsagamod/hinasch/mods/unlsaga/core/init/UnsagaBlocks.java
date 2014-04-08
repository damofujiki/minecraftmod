package hinasch.mods.unlsaga.core.init;

import hinasch.mods.unlsaga.Unsaga;
import hinasch.mods.unlsaga.block.BlockChestUnsagaNew;
import hinasch.mods.unlsaga.block.BlockDataUnsaga;
import hinasch.mods.unlsaga.block.BlockFallStone;
import hinasch.mods.unlsaga.block.BlockOreUnsagaNew;
import hinasch.mods.unlsaga.item.etc.ItemBlockOreUnsaga;
import hinasch.mods.unlsaga.tileentity.TileEntityChestUnsagaNew;
import hinasch.mods.unlsaga.tileentity.TileEntityFallStone;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.common.registry.GameRegistry;

public class UnsagaBlocks {


	public static Block blockFallStone;
	public static Block blockChestUnsaga;
	public static Block blockBlender;
	public static Block blockAir;
	public static Block[] blocksOreUnsaga;
	
	public static Class<? extends TileEntity> tileEntityChestUnsaga;
	public static Class<? extends ItemBlock> classItemBlockOre;
	//public static Class<? extends ItemBlockOreUnsaga> classItemBlockOre;
	
	public static void loadConfig(Configuration config) {
//		PropertyCustom prop = new PropertyCustom(new String[]{"BlockID.FallingStone","BlockID.Chest.Unsaga","BlockID.Ores"
//				,"BlockID.AirBlock"});
//		prop.setValues(new Integer[]{1000,1001,1002,1003});
//		prop.setCategoriesAll(config.CATEGORY_BLOCK);
//		prop.buildProps(config);
//		
//		blockFallStoneID = prop.getProp(0).getInt();
//		blockChestUnsagaID = prop.getProp(1).getInt();
//		blockUnsagaOresID = prop.getProp(2).getInt();
//		blockAirID = prop.getProp(3).getInt();
	}

	public static void registerValues() {

		blockChestUnsaga = new BlockChestUnsagaNew().setBlockName("unsaga.chest").setHardness(2.5F).setCreativeTab(Unsaga.tabUnsaga);
		blockFallStone = new BlockFallStone(Material.rock).setHardness(2.0F).setResistance(10.0F).setStepSound(Block.soundTypeStone)
				.setBlockName("unsaga.stonefalling").setCreativeTab(Unsaga.tabUnsaga);
		//blockAir = new BlockNothing(blockAirID,Material.air).setHardness(100.0F).setUnlocalizedName("unsaga.nothing");
		blocksOreUnsaga = new Block[BlockDataUnsaga.unlocalizedNames.size()];
		for(int i=0;i<BlockDataUnsaga.unlocalizedNames.size();i++){
			blocksOreUnsaga[i] = new BlockOreUnsagaNew(i).setHardness(3.0F).setResistance(5.0F).setStepSound(Block.soundTypeStone)
					.setBlockName("unsaga."+BlockDataUnsaga.unlocalizedNames.get(i)).setCreativeTab(Unsaga.tabUnsaga);
			GameRegistry.registerBlock(blocksOreUnsaga[i], ItemBlockOreUnsaga.class,BlockDataUnsaga.unlocalizedNames.get(i),Unsaga.modid);
		}
		//blockOreUnsaga = new BlockOreUnsaga().setHardness(3.0F).setResistance(5.0F).setStepSound(Block.soundTypeStone)
		//		.setBlockName("unsaga.ore").setCreativeTab(Unsaga.tabUnsaga);
		//tileEntityChestUnsaga = TileEntityChestUnsaga.class;
		

		GameRegistry.registerTileEntity(TileEntityChestUnsagaNew.class, "unsaga.tileEntityChestUnsaga");
		GameRegistry.registerTileEntity(TileEntityFallStone.class, "unsaga.fallingStone");
		//GameRegistry.registerTileEntity(TileEntityShapeMemory.class, "unsaga.tileEntityShapeMemory");
		GameRegistry.registerBlock(blockChestUnsaga,ItemBlock.class,"bonuschest",Unsaga.modid);
		
		GameRegistry.registerBlock(blockFallStone,ItemBlock.class,"fallstone",Unsaga.modid);
	}

}

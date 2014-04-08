package hinasch.mods.unlsaga.block;

import hinasch.mods.unlsaga.core.init.NoFunctionItems;
import hinasch.mods.unlsaga.core.init.UnsagaBlocks;
import hinasch.mods.unlsaga.core.init.UnsagaMaterials;

import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

import com.google.common.collect.Lists;

public class BlockDataUnsaga {
	public static List<String> oreDictionaryList = Lists.newArrayList("oreLead","oreRuby","oreSapphire","oreSilver","oreCopper","oreAngelite","oreDemonite");
	public static List<String> unlocalizedNames = Lists.newArrayList("oreLead","oreRuby","oreSapphire","oreSilver","oreCopper","oreAngelite","oreDemonite");
	public static List<Float> exps = Lists.newArrayList(0.7F,1.0F,1.0F,0.7F,0.7F,1.0F,1.0F);
	public static List<Integer> smelted = Lists.newArrayList(5,6,7,4,3,16,17);
	//public static List<String> localizedNameJP = Lists.newArrayList("鉛鉱石","鋼玉鉱石","鋼玉鉱石","銀鉱石","銅鉱石","聖石鉱石","魔石鉱石");
	public static List<Integer> harvestLevel = Lists.newArrayList(1,2,2,1,1,1,1);
	//public static List<String> localizedNameEN = Lists.newArrayList("Lead Ore","Corundum Ore","Corundum Ore","Silver Ore","Copper Ore","Angelite Ore","Demonite Ore");
	public static List<Integer> containerItem = Lists.newArrayList(-1,6,7,-1,-1,16,17);


	public static void registerSmeltingAndAssociation(){



		for(int i=0;i<UnsagaBlocks.blocksOreUnsaga.length;i++){
			//ItemStack blockitem = new ItemStack(UnsagaBlocks.blocksOreUnsaga[i],1,i);
			ItemStack smeltedItemStack = NoFunctionItems.getItemStack(1, smelted.get(i));			
			FurnaceRecipes.smelting().func_151393_a(UnsagaBlocks.blocksOreUnsaga[i],smeltedItemStack, exps.get(i));
		}

		UnsagaMaterials.copperOre.associate(new ItemStack(UnsagaBlocks.blocksOreUnsaga[oreDictionaryList.indexOf("oreCopper")],1));

	}
}

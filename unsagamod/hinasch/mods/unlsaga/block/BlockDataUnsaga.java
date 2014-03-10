package hinasch.mods.unlsaga.block;

import hinasch.mods.unlsaga.core.init.NoFuncItemList;
import hinasch.mods.unlsaga.core.init.UnsagaBlocks;
import hinasch.mods.unlsaga.core.init.UnsagaMaterials;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

import com.google.common.collect.Lists;

public class BlockDataUnsaga {
	public static ArrayList<String> oreDictionaryList = Lists.newArrayList("oreLead","oreRuby","oreSapphire","oreSilver","oreCopper","oreAngelite","oreDemonite");
	public static ArrayList<String> unlocalizedNames = Lists.newArrayList("oreLead","oreRuby","oreSapphire","oreSilver","oreCopper","oreAngelite","oreDemonite");
	public static ArrayList<Float> exps = Lists.newArrayList(0.7F,1.0F,1.0F,0.7F,0.7F,1.0F,1.0F);
	public static ArrayList<Integer> smelted = Lists.newArrayList(5,6,7,4,3,16,17);
	public static ArrayList<String> localizedNameJP = Lists.newArrayList("鉛鉱石","鋼玉鉱石","鋼玉鉱石","銀鉱石","銅鉱石","聖石鉱石","魔石鉱石");
	public static ArrayList<Integer> harvestLevel = Lists.newArrayList(1,2,2,1,1,1,1);
	public static ArrayList<String> localizedNameEN = Lists.newArrayList("Lead Ore","Corundum Ore","Corundum Ore","Silver Ore","Copper Ore","Angelite Ore","Demonite Ore");
	public static ArrayList<Integer> containerItem = Lists.newArrayList(-1,6,7,-1,-1,16,17);


	public static void registerSmeltingAndAssociation(){



		for(int i=0;i<UnsagaBlocks.blocksOreUnsaga.length;i++){
			//ItemStack blockitem = new ItemStack(UnsagaBlocks.blocksOreUnsaga[i],1,i);
			ItemStack smeltedItemStack = NoFuncItemList.getItemStack(1, smelted.get(i));			
			FurnaceRecipes.smelting().func_151393_a(UnsagaBlocks.blocksOreUnsaga[i],smeltedItemStack, exps.get(i));
		}

		UnsagaMaterials.copperOre.associate(new ItemStack(UnsagaBlocks.blocksOreUnsaga[oreDictionaryList.indexOf("oreCopper")],1));

	}
}

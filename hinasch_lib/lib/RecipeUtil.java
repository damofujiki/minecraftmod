package hinasch.lib;

import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;

import com.google.common.collect.Lists;

public class RecipeUtil {

	public static List<ItemStack> getRequireItemStacksFromRecipe(IRecipe recipe){
		List<ItemStack> itemsList = null;
		if(recipe instanceof ShapelessRecipes){
			itemsList = ((ShapelessRecipes)recipe).recipeItems;
		}
		if(recipe instanceof ShapedRecipes){
			ItemStack[] isarray = ((ShapedRecipes)recipe).recipeItems;
			itemsList = Lists.newArrayList(isarray);
		}
		return itemsList;
	}
}

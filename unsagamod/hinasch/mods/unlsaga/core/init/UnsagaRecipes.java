package hinasch.mods.unlsaga.core.init;

import com.hinasch.lib.RecipeUtil;

import hinasch.mods.unlsaga.misc.util.EnumUnsagaTools;

public class UnsagaRecipes {

	public static void register(){
		UnsagaItems.pickaxeInitializer.regsiterRecipes(RecipeUtil.Recipes.getPickaxe(), EnumUnsagaTools.PICKAXE);
	}
}

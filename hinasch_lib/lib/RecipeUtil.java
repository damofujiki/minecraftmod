package hinasch.lib;

import hinasch.lib.RecipeUtil.Recipe.Shaped;
import hinasch.lib.RecipeUtil.Recipe.Shapelss;

import java.util.List;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import com.google.common.collect.Lists;

import cpw.mods.fml.common.registry.GameRegistry;

public class RecipeUtil {
	public static enum changeable {CHANGEABLE};
	
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
	
	public static void addShapelessRecipe(ItemStack output,Shapelss materials){
		GameRegistry.addShapelessRecipe(output, materials.getObj());
	}
	
	public static void addShapedRecipe(ItemStack output,Shaped recipe){
		GameRegistry.addShapedRecipe(output, recipe.getObj());
	}
	
	public static ShapedOreRecipe getShapedOreRecipe(ItemStack output,Shaped recipe){
		
		return new ShapedOreRecipe(output,recipe.getObj());
	}
	
	public static ShapelessOreRecipe getShapelssOreRecipe(ItemStack output,Shapelss recipe){
		return new ShapelessOreRecipe(output,recipe.getObj());
	}
	
	public static class Recipe{
		protected Object[] recipe;

		public static class Shapelss extends Recipe{

			public Shapelss(Object... inputs) {
				super(inputs);
			
			}
			@Override
			public Shapelss getChangedRecipe(Object obj){
				Object[] reci = this.recipe.clone();
				for(int i=0;i<reci.length;i++){
					if(reci[i]==RecipeUtil.changeable.CHANGEABLE){
						reci[i] = obj;
					}
				}
				return new Shapelss(reci);
			}
		}
		
		public static class Shaped extends Recipe{
			public Shaped(Object... inputs) {
				super(inputs);
			
			}
			@Override
			public Shaped getChangedRecipe(Object obj){
				Object[] reci = this.recipe.clone();
				for(int i=0;i<reci.length;i++){
					if(reci[i]==RecipeUtil.changeable.CHANGEABLE){
						reci[i] = obj;
					}
				}
				return new Shaped(reci);
			}
		}
		
		
		public static Shapelss getShapelss(Object... inputs){
			return new Shapelss(inputs);
		}

		public Recipe(Object... recipes){
			this.recipe = recipes;
		}
		
		public Object[] getObj(){
			return this.recipe;
		}
		
		@Override
		public String toString(){
			StringBuilder builder = new StringBuilder();
			for(Object obj:this.recipe){
				boolean flag = false;
				if(obj instanceof String){
					builder.append("[String]"+((String)obj).toString());
					flag = true;
				}
				if(obj instanceof Character){
					builder.append("[Character]"+((Character)obj).toString());
					flag = true;
				}
				
				if(!flag){
					builder.append(obj.toString());
				}
				
				builder.append("/");
			}
			
			return new String(builder);
		}
		public Recipe getChangedRecipe(Object obj){
			Object[] reci = this.recipe.clone();
			for(int i=0;i<reci.length;i++){
				if(reci[i]==RecipeUtil.changeable.CHANGEABLE){
					reci[i] = obj;
				}
			}
			return new Recipe(reci);
		}
	}
	public static class Recipes{
		
		public static Shaped getPickaxe(){
			Shaped obj = new Shaped("III"," S "," S ",
					Character.valueOf('S'),Items.stick,
					Character.valueOf('I'),RecipeUtil.changeable.CHANGEABLE);
			return obj;
		}
	}
}

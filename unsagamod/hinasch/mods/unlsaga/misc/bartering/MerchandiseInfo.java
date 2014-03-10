package hinasch.mods.unlsaga.misc.bartering;

import hinasch.lib.HSLibs;
import hinasch.lib.RecipeUtil;
import hinasch.lib.UtilNBT;
import hinasch.mods.unlsaga.Unsaga;
import hinasch.mods.unlsaga.core.init.UnsagaMaterials;
import hinasch.mods.unlsaga.core.init.UnsagaItems;
import hinasch.mods.unlsaga.core.init.UnsagaItems.EnumSelecterItem;
import hinasch.mods.unlsaga.core.init.UnsagaMaterial;
import hinasch.mods.unlsaga.misc.smith.MaterialInfo;
import hinasch.mods.unlsaga.misc.util.UtilItem;
import hinasch.mods.unlsaga.misc.util.WeightedRandomNumber;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.MathHelper;
import net.minecraft.util.WeightedRandom;

import com.google.common.base.Optional;

public class MerchandiseInfo {

	
	protected ItemStack is;
	protected MaterialInfo info;
	protected static WeightedRandomNumber[] wr;
	
	protected static final String PRICE_TAG = "Bartering.Price";
	public MerchandiseInfo(ItemStack is){
		this.is = is;
		this.info = new MaterialInfo(is);
	}
	
	public int getPrice(){
		return getPrice(this.is);
	}
	
	public static ItemStack getRandomMaterialItemStack(Random rand){
		ArrayList<ItemStack> itemstackList = new ArrayList();
		for(Iterator<UnsagaMaterial> ite=UnsagaMaterials.allMaterialMap.values().iterator();ite.hasNext();){
			UnsagaMaterial mate = ite.next();
			if(mate.getAssociatedItem().isPresent()){
				itemstackList.add(mate.getAssociatedItem().get());
			}
		}
		return itemstackList.get(rand.nextInt(itemstackList.size()));
	}
	
	public static ItemStack getRandomMerchandise(Random rand,int popularity){
		int merchantrank = getRankFromPopularity(popularity);
		if(rand.nextInt(3)<=1){
			return getRandomMaterialItemStack(rand);
		}
		ItemStack ms = UnsagaItems.getRandomWeapon(rand, merchantrank, EnumSelecterItem.MERCHANDISE);
		if(rand.nextInt(6)<=1){
			WeightedRandomNumber wrs = (WeightedRandomNumber) WeightedRandom.getRandomItem(rand, wr);

			EnchantmentHelper.addRandomEnchantment(rand, ms, wrs.number);
			//EnchantmentData enchant = new EnchantmentData(Enchantment.fortune,1);
		}
		return ms;
	}
	public static int getPrice(ItemStack is){
		int price = 0;
		price = Unsaga.merchandiseFactory.getPriceFromUnsagaMaterialItem(is);
		
		
		
		float per = 1.0F;
		HashMap<Class,Float> classMap = new HashMap();
		classMap.put(ItemBow.class, 2.0F);
		classMap.put(ItemSword.class, 1.8F);
		classMap.put(ItemTool.class, 1.9F);
		classMap.put(ItemArmor.class, 2.0F);
		classMap.put(ItemHoe.class, 2.0F);
		
		for(Class cl:classMap.keySet()){
			if(UtilItem.isItemInstance(is, cl)){
				price = (int)((float)price * classMap.get(cl));
				per = (is.getMaxDamage()-is.getItemDamage())/HSLibs.exceptZero(is.getMaxDamage());
				price = (int)((float)price*per);
				if(price<10){
					price = 10;
				}
			}
		}
		
//		if(classMap.containsKey(is.getItem().getClass())){
//			price = (int)((float)price * classMap.get(is.getItem().getClass()));
//			per = (is.getMaxDamage()-is.getItemDamage())/HSLibs.exceptZero(is.getMaxDamage());
//			price = (int)((float)price*per);
//			if(price<10){
//				price = 10;
//			}
//		}
		if(is.isItemEnchanted()){
			Map enchantmap = EnchantmentHelper.getEnchantments(is);
			int maxlv = 0;
			int lv = 0;
			for(Iterator<Integer> ite=enchantmap.values().iterator();ite.hasNext();){
				lv = ite.next();
				if(maxlv<=lv){
					maxlv = lv;
				}
			}
			maxlv += 1;
			int enchantmentvalue = (int)((float)price*0.5F*(float)maxlv);
			enchantmentvalue *= enchantmap.size();
			price += enchantmentvalue;
			
		}

		boolean flag  = false;
		if(Unsaga.merchandiseFactory.find(is).isPresent() && !flag){
			MerchandiseLibraryBook book = (MerchandiseLibraryBook) Unsaga.merchandiseFactory.find(is).get();
			price = book.price;
			flag = true;
		}
//		if(HSLibs.getToolMaterialNameFromTool(is.getItem())!=null && !flag){
//			String str = HSLibs.getToolMaterialNameFromTool(is.getItem());
//			if(Unsaga.merchandiseFactory.findPrice(str).isPresent()){
//				price = Unsaga.merchandiseFactory.findPrice(str).get();
//				flag = true;
//			}
//		}
		
		Unsaga.debug("きてます");
		if(!flag && supposeFromRecipe(is).isPresent()){
			price = supposeFromRecipe(is).get();
		}
		
//		if(is.getItem() instanceof ItemTool){
//			String str = ((ItemTool)is.getItem()).getToolMaterialName();
//			if(Unsaga.merchandiseFactory.findPrice(str).isPresent()){
//				price = Unsaga.merchandiseFactory.findPrice(str).get();
//			}
//		}
		if(is.stackSize>1){
			price *= is.stackSize;
		}
		
		return (int)price;
	}
	
	public static void setBuyPriceTag(ItemStack is,int price){
		UtilNBT.setFreeTag(is, PRICE_TAG, price);
	}
	
	public int getBuyPriceTag(){
		return UtilNBT.readFreeTag(this.is, PRICE_TAG);
	}
	
	public static boolean hasBuyPriceTag(ItemStack is){
		return UtilNBT.hasKey(is, PRICE_TAG);
	}
	
	public static boolean isPossibleToSell(ItemStack is){
		MaterialInfo info = new MaterialInfo(is);
		if(info.getMaterial().isPresent())return true;
		if(Unsaga.merchandiseFactory.find(is).isPresent())return true;
		return false;
	}
	
	public static void removePriceTag(ItemStack is){
		UtilNBT.removeTag(is, PRICE_TAG);
	}
	

	//レシピからの推測
	public static Optional<Integer> supposeFromRecipe(ItemStack is){
		List list = CraftingManager.getInstance().getRecipeList();
		int price = 0;
		boolean flag = false;
		for(Object obj:list){
			IRecipe recipe = (IRecipe)obj;
			if(recipe.getRecipeOutput()!=null && is.isItemEqual(recipe.getRecipeOutput()) && !flag){
				Unsaga.debug("レシピが一致するのがありました");
				//クラフト前レシピに必要なアイテム一覧を取得
				List<ItemStack> itemsList = RecipeUtil.getRequireItemStacksFromRecipe(recipe);
				if(itemsList!=null && !itemsList.isEmpty()){
					getPriceFromRecipe(itemsList);
				}
				flag = true;
			}
		}
		if(price==0){
			return Optional.absent();
		}
		return Optional.of(price);
	}
	
	protected static int getPriceFromRecipe(List<ItemStack> itemList){
		int price = 0;
		for(ItemStack is:itemList){
			if(Unsaga.materialFactory.find(is).isPresent()){
				MerchandiseLibraryBook book = (MerchandiseLibraryBook) Unsaga.materialFactory.find(is).get();
				price += book.price;
			}
		}
		return price;
		
	}
	public static int getRankFromPopularity(int popularity){
		int rank = 1;
		popularity = MathHelper.clamp_int(popularity, 1, 300);
		for(int i=0;i<10;i++){
			if((i+1)*(i+1)<popularity){
				rank = i;
			}
			
		}
		return rank;
	}
	static{
		wr = new WeightedRandomNumber[20];
		for(int i=0;i<20;i++){
			int itemWeight = (wr.length -i)*(wr.length -i);
			wr[i] = new WeightedRandomNumber(itemWeight,i+1);
		}
	}
}

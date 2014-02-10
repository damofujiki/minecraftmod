package hinasch.mods.unlsaga.misc.bartering;

import hinasch.lib.HSLibs;
import hinasch.lib.UtilNBT;
import hinasch.mods.unlsaga.core.init.MaterialList;
import hinasch.mods.unlsaga.core.init.UnsagaItems;
import hinasch.mods.unlsaga.core.init.UnsagaItems.EnumSelecterItem;
import hinasch.mods.unlsaga.core.init.UnsagaMaterial;
import hinasch.mods.unlsaga.misc.smith.MaterialInfo;
import hinasch.mods.unlsaga.misc.util.WeightedRandomNumber;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.util.WeightedRandom;

public class MerchandiseInfo {

	
	protected ItemStack is;
	protected MaterialInfo info;
	
	protected static final String PRICE_TAG = "Bartering.Price";
	public MerchandiseInfo(ItemStack is){
		this.is = is;
		this.info = new MaterialInfo(is);
	}
	
	public int getPrice(){
		return getPrice(this.is);
	}
	
	public static ItemStack getRandomMerchandise(Random rand){
		ItemStack ms = UnsagaItems.getRandomWeapon(rand, 5, EnumSelecterItem.MERCHANDISE);
		if(rand.nextInt(6)<=1){
			WeightedRandomNumber[] wr = new WeightedRandomNumber[4];
			for(int i=0;i<4;i++){
				int itemWeight = (wr.length -i)*(wr.length -i);
				wr[i] = new WeightedRandomNumber(itemWeight,i+1);
			}
			WeightedRandomNumber wrs = (WeightedRandomNumber) WeightedRandom.getRandomItem(rand, wr);
			
			EnchantmentHelper.addRandomEnchantment(rand, ms, wrs.number);
		}
		return ms;
	}
	public static int getPrice(ItemStack is){
		int price = 0;
		UnsagaMaterial material = null;
		MaterialInfo info = new MaterialInfo(is);
		if(info.getMaterial().isPresent()){
			
			material = info.getMaterial().get();
			price = (material.rank+3);
			price *= price;
			price = price * 8;
			if(material==MaterialList.diamond){
				price *= 3;
			}
		}else{
			price = 100;
		}
		
		
		
		float per = 1.0F;
		HashMap<Class,Float> classMap = new HashMap();
		classMap.put(ItemBow.class, 2.0F);
		classMap.put(ItemSword.class, 1.8F);
		classMap.put(ItemTool.class, 1.9F);
		classMap.put(ItemArmor.class, 2.0F);
		
		if(classMap.containsKey(is.getItem().getClass())){
			price = (int)((float)price * classMap.get(is.getItem().getClass()));
			per = (is.getMaxDamage()-is.getItemDamage())/HSLibs.exceptZero(is.getMaxDamage());
			price = (int)((float)price*per);
			if(price<10){
				price = 10;
			}
		}
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
			price += enchantmentvalue;
		}

		if(MerchandiseLibrary.findPrice(is).isPresent()){
			price = MerchandiseLibrary.findPrice(is).get();
		}
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
		if(MerchandiseLibrary.findPrice(is).isPresent())return true;
		return false;
	}
	
	public static void removePriceTag(ItemStack is){
		UtilNBT.removeTag(is, PRICE_TAG);
	}
}

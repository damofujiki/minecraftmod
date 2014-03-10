package hinasch.mods.unlsaga.misc.smith;

import java.util.HashSet;
import java.util.Iterator;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class ValidPayment {

	public static enum EnumPayValues {HIGH,MID,LOW};
	
	public static HashSet<ValidPayment> validPayList = new HashSet();

	public static ValidPayment payEmerald = new ValidPayment("item",Items.emerald,EnumPayValues.HIGH);
	public static ValidPayment payGold = new ValidPayment("item",Items.gold_ingot,EnumPayValues.MID);
	public static ValidPayment payGoldFromKey = new ValidPayment("dict","ingotGold",EnumPayValues.MID);
	public static ValidPayment payGoldNugget = new ValidPayment("item",Items.gold_nugget,EnumPayValues.LOW);
	
	public EnumPayValues value;
	public String key;
	public Item itemObj;
	public String oreDictKey;
	
	public ValidPayment(String par1,Object par2,EnumPayValues par3){
		if(par1.equals("item")){
			Item item = (Item)par2;
			this.itemObj = item;
		}
		if(par1.equals("dict")){
			this.oreDictKey = (String)par2;
			
		}
		this.key = par1;
		this.value = par3;
		validPayList.add(this);
	}
	
	public static EnumPayValues getValueFromItemStack(ItemStack is){
		EnumPayValues enumpay = null;
		for(Iterator<ValidPayment> ite = validPayList.iterator();ite.hasNext();){
			ValidPayment pay = ite.next();
			if(pay.compare(is)){
				enumpay = pay.value;
			}
		}
		return enumpay;
	}
	
	public boolean compare(ItemStack is){
		if(this.key.equals("item")){
			if(is.getItem() == this.itemObj){
				return true;
			}
		}
		if(this.key.equals("dict")){
			int oreid = OreDictionary.getOreID(is);
			String orekey = OreDictionary.getOreName(oreid);
			if(this.oreDictKey.equals(orekey)){
				return true;
			}
		}
		return false;
	}
	
}

package hinasch.lib;

import net.minecraft.block.Block;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.google.common.base.Optional;

public class Library {

	public static enum EnumSelecter {ITEM,ITEMSTACK,STRING,TOOLMATERIAL,ARMORMATERIAL,_CLASS};
	protected EnumSelecter key;
	protected Optional<String> orekey = Optional.absent();
	protected Optional<EnumToolMaterial> enumtool = Optional.absent();
	protected Optional<EnumArmorMaterial> enumarmor = Optional.absent();
	protected Optional<PairID> idmeta = Optional.absent();
	protected Optional<Class> classstore = Optional.absent();
	
	public Library(Object par1){
		if(par1 instanceof Block){
			this.key = EnumSelecter.ITEMSTACK;
			ItemStack is = new ItemStack((Block)par1,1);
			idmeta = Optional.of(new PairID(is.getItem().itemID,is.getItemDamage()));
			this.init(EnumSelecter.ITEMSTACK,is);
		}
		if(par1 instanceof String){
			this.key = EnumSelecter.STRING;
			this.orekey = Optional.of((String)par1);
			this.init(EnumSelecter.STRING, par1);
		}
		if(par1 instanceof Item){
			this.key = EnumSelecter.ITEMSTACK;
			ItemStack is = new ItemStack((Item)par1,1);
			idmeta = Optional.of(new PairID(is.getItem().itemID,is.getItemDamage()));
			this.init(EnumSelecter.ITEMSTACK,is);
		}
		if(par1 instanceof ItemStack){
			this.key = EnumSelecter.ITEMSTACK;
			ItemStack is = (ItemStack)par1;
			idmeta = Optional.of(new PairID(is.getItem().itemID,is.getItemDamage()));
			this.init(EnumSelecter.ITEMSTACK,is);
		}
		if(par1 instanceof EnumToolMaterial){
			this.key = EnumSelecter.TOOLMATERIAL;
			this.enumtool = Optional.of((EnumToolMaterial)par1);
			this.init(EnumSelecter.TOOLMATERIAL, par1);
		}
		if(par1 instanceof EnumArmorMaterial){
			this.key = EnumSelecter.ARMORMATERIAL;
			this.enumarmor = Optional.of((EnumArmorMaterial)par1);
			this.init(EnumSelecter.ARMORMATERIAL, par1);
		}
		if(par1 instanceof Class){
			this.key = EnumSelecter._CLASS;
			this.classstore = Optional.of((Class)par1);
			this.init(EnumSelecter._CLASS, par1);
		}
	}
	
	public void init(EnumSelecter selecter,Object par1){
		
	}
	
	
}

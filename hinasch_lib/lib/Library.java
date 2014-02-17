package hinasch.lib;

import net.minecraft.block.Block;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.google.common.base.Optional;

public class Library {

	public static enum EnumSelector {ITEM,ITEMSTACK,STRING,TOOLMATERIAL,ARMORMATERIAL,_CLASS,BLOCK};
	protected EnumSelector key;
	protected boolean isAllMedadata = false;
	protected Optional<String> orekey = Optional.absent();
	protected Optional<EnumToolMaterial> enumtool = Optional.absent();
	protected Optional<EnumArmorMaterial> enumarmor = Optional.absent();
	protected Optional<PairID> idmeta = Optional.absent();
	protected Optional<Class> classstore = Optional.absent();
	
	public Library(Object par1){
		if(par1 instanceof Block){

			this.key = EnumSelector.ITEMSTACK;
			ItemStack is = new ItemStack((Block)par1,1);
			idmeta = Optional.of(new PairID(is.getItem().itemID,is.getItemDamage()));
			this.isAllMedadata = true;

		}
		if(par1 instanceof String){
			this.key = EnumSelector.STRING;
			this.orekey = Optional.of((String)par1);

		}
		if(par1 instanceof Item){
			this.key = EnumSelector.ITEMSTACK;
			ItemStack is = new ItemStack((Item)par1,1);
			idmeta = Optional.of(new PairID(is.getItem().itemID,is.getItemDamage()));

		}
		if(par1 instanceof ItemStack){
			this.key = EnumSelector.ITEMSTACK;
			ItemStack is = (ItemStack)par1;
			idmeta = Optional.of(new PairID(is.getItem().itemID,is.getItemDamage()));

		}
		if(par1 instanceof EnumToolMaterial){
			this.key = EnumSelector.TOOLMATERIAL;
			this.enumtool = Optional.of((EnumToolMaterial)par1);

		}
		if(par1 instanceof EnumArmorMaterial){
			this.key = EnumSelector.ARMORMATERIAL;
			this.enumarmor = Optional.of((EnumArmorMaterial)par1);

		}
		if(par1 instanceof Class){
			this.key = EnumSelector._CLASS;
			this.classstore = Optional.of((Class)par1);

		}
	}
	
	

	
	public EnumSelector getKey(){
		return this.key;
	}
}

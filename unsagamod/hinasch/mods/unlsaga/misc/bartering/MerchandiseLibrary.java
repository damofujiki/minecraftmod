package hinasch.mods.unlsaga.misc.bartering;

import hinasch.lib.LibraryFactory;
import net.minecraft.block.Block;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.google.common.base.Optional;

public class MerchandiseLibrary extends LibraryFactory{

	public MerchandiseLibrary(){
			libSet.add(new MerchandiseLibraryBook("logWood",36));
			libSet.add(new MerchandiseLibraryBook("gemAmber",200));
			libSet.add(new MerchandiseLibraryBook("ingotTin",100));
			libSet.add(new MerchandiseLibraryBook(Block.wood,36));
			libSet.add(new MerchandiseLibraryBook(Block.planks,9));
			libSet.add(new MerchandiseLibraryBook(Block.cobblestone,5));
			libSet.add(new MerchandiseLibraryBook(Block.sand,10));
			libSet.add(new MerchandiseLibraryBook(Block.glass,20));
			libSet.add(new MerchandiseLibraryBook("ingotGold",1000));
			libSet.add(new MerchandiseLibraryBook(Item.ingotGold,900));
			libSet.add(new MerchandiseLibraryBook(Item.redstone,100));
			libSet.add(new MerchandiseLibraryBook(Item.rottenFlesh,5));
			libSet.add(new MerchandiseLibraryBook(Item.fishRaw,100));
			libSet.add(new MerchandiseLibraryBook(Item.enderPearl,1500));
			libSet.add(new MerchandiseLibraryBook(Item.emerald,2500));
			libSet.add(new MerchandiseLibraryBook(Item.goldNugget,100));
			libSet.add(new MerchandiseLibraryBook(Item.silk,10));
			libSet.add(new MerchandiseLibraryBook(Item.feather,100));
			libSet.add(new MerchandiseLibraryBook(Item.diamond,5000));
			libSet.add(new MerchandiseLibraryBook(EnumToolMaterial.GOLD,1000));
			
	}
	
	public Optional<Integer> findPrice(ItemStack is){
		if(this.findInfo(is).isPresent()){
			MerchandiseLibraryBook info = (MerchandiseLibraryBook)this.findInfo(is).get();
			return Optional.of(info.price);
		}
		return Optional.absent();
	}
	
	public Optional<Integer> findPrice(String input){
		if(this.findEnumInfo(input).isPresent()){
			MerchandiseLibraryBook info = (MerchandiseLibraryBook)this.findEnumInfo(input).get();
			return Optional.of(info.price);
		}
		return Optional.absent();
	}
}

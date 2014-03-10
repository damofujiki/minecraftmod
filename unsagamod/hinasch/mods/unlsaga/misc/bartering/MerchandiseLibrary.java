package hinasch.mods.unlsaga.misc.bartering;

import hinasch.lib.LibraryBook;
import hinasch.lib.LibraryShelf;
import hinasch.mods.unlsaga.core.init.UnsagaMaterials;
import hinasch.mods.unlsaga.core.init.UnsagaMaterial;
import hinasch.mods.unlsaga.misc.smith.MaterialInfo;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemStack;

import com.google.common.base.Optional;

public class MerchandiseLibrary extends LibraryShelf{

	public MerchandiseLibrary(){
			libSet.add(new MerchandiseLibraryBook("logWood",36));
			libSet.add(new MerchandiseLibraryBook("gemAmber",200));
			libSet.add(new MerchandiseLibraryBook("ingotTin",100));
			libSet.add(new MerchandiseLibraryBook(Blocks.log,36));
			libSet.add(new MerchandiseLibraryBook(Blocks.planks,9));
			libSet.add(new MerchandiseLibraryBook(Blocks.cobblestone,5));
			libSet.add(new MerchandiseLibraryBook(Blocks.sand,10));
			libSet.add(new MerchandiseLibraryBook(Blocks.glass,20));
			libSet.add(new MerchandiseLibraryBook("ingotGold",1000));
			libSet.add(new MerchandiseLibraryBook(Items.gold_ingot,900));
			libSet.add(new MerchandiseLibraryBook(Items.redstone,100));
			libSet.add(new MerchandiseLibraryBook(Items.rotten_flesh,5));
			libSet.add(new MerchandiseLibraryBook(Items.fish,100));
			libSet.add(new MerchandiseLibraryBook(Items.ender_pearl,1500));
			libSet.add(new MerchandiseLibraryBook(Items.emerald,2500));
			libSet.add(new MerchandiseLibraryBook(Items.gold_nugget,100));
			libSet.add(new MerchandiseLibraryBook(Items.string,10));
			libSet.add(new MerchandiseLibraryBook(Items.feather,100));
			libSet.add(new MerchandiseLibraryBook(Items.diamond,5000));
			libSet.add(new MerchandiseLibraryBook(ToolMaterial.GOLD,1000));
			libSet.add(new MerchandiseLibraryBook(Items.bone,100));
			libSet.add(new MerchandiseLibraryBook(Blocks.gravel,10));
			libSet.add(new MerchandiseLibraryBook(Items.wheat,20));
			libSet.add(new MerchandiseLibraryBook(Items.leather,100));
	}
	
	@Override
	public Optional<LibraryBook> find(Object object){
		
		
		return super.find(object);
		
	}
	
	public int getPriceFromUnsagaMaterialItem(ItemStack is){
		int price = 0;
		UnsagaMaterial material = null;
		MaterialInfo info = new MaterialInfo(is);
		if(info.getMaterial().isPresent()){
			
			material = info.getMaterial().get();
			price = (material.rank+3);
			price *= price;
			price = price * 8;
			if(material.rank>=8){
				price = (int)((float)price *2.5F);
			}
			if(material==UnsagaMaterials.diamond){
				price *= 3;
			}
		}else{
			price = 100;
		}
		return price;
	}
//	public Optional<Integer> findPrice(ItemStack is){
//		if(this.findInfo(is).isPresent()){
//			MerchandiseLibraryBook info = (MerchandiseLibraryBook)this.findInfo(is).get();
//			return Optional.of(info.price);
//		}
//		return Optional.absent();
//	}
//	
//	public Optional<Integer> findPrice(String input){
//		if(this.findEnumInfo(input).isPresent()){
//			MerchandiseLibraryBook info = (MerchandiseLibraryBook)this.findEnumInfo(input).get();
//			return Optional.of(info.price);
//		}
//		return Optional.absent();
//	}
}

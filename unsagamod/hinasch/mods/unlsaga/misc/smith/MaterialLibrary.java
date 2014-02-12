package hinasch.mods.unlsaga.misc.smith;

import hinasch.lib.LibraryFactory;
import hinasch.mods.unlsaga.core.init.MaterialList;
import hinasch.mods.unlsaga.core.init.UnsagaItems;
import net.minecraft.block.Block;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;

public class MaterialLibrary extends LibraryFactory{
	public MaterialLibrary(){
		libSet.add(new MaterialLibraryBook(EnumToolMaterial.EMERALD,MaterialList.diamond,1));
		libSet.add(new MaterialLibraryBook(EnumToolMaterial.WOOD,MaterialList.wood,1));
		libSet.add(new MaterialLibraryBook(EnumToolMaterial.IRON,MaterialList.iron,1));
		libSet.add(new MaterialLibraryBook(EnumToolMaterial.STONE,MaterialList.stone,1));
		libSet.add(new MaterialLibraryBook(Item.ingotIron,MaterialList.iron,80));
		libSet.add(new MaterialLibraryBook("ingotIron",MaterialList.iron,80));
		libSet.add(new MaterialLibraryBook("ingotCopper",MaterialList.copper,50));
		libSet.add(new MaterialLibraryBook("ingotSteel",MaterialList.steel1,300));
		libSet.add(new MaterialLibraryBook("ingotSilver",MaterialList.silver,70));
		libSet.add(new MaterialLibraryBook("ingotDamascus",MaterialList.damascus,2000));
		libSet.add(new MaterialLibraryBook("ingotLead",MaterialList.lead,100));
		libSet.add(new MaterialLibraryBook("gemRuby",MaterialList.corundum1,1400));
		libSet.add(new MaterialLibraryBook("gemSapphire",MaterialList.corundum2,1400));
		libSet.add(new MaterialLibraryBook("ingotFaerieSilver",MaterialList.fairieSilver,1000));
		libSet.add(new MaterialLibraryBook("stoneMeteorite",MaterialList.meteorite,200));
		libSet.add(new MaterialLibraryBook("ingotMeteoriticIron",MaterialList.meteoricIron,400));
		libSet.add(new MaterialLibraryBook("gemCarnelian",MaterialList.carnelian,50));
		libSet.add(new MaterialLibraryBook("gemTopaz",MaterialList.topaz,50));
		libSet.add(new MaterialLibraryBook("gemRavenite",MaterialList.ravenite,50));
		libSet.add(new MaterialLibraryBook("gemLapis",MaterialList.lazuli,50));
		libSet.add(new MaterialLibraryBook("gemOpal",MaterialList.opal,50));
		libSet.add(new MaterialLibraryBook("gemAngelite",MaterialList.lazuli,100));
		libSet.add(new MaterialLibraryBook("gemDemonite",MaterialList.opal,100));
		libSet.add(new MaterialLibraryBook("stoneDebris",MaterialList.debris,1));
		libSet.add(new MaterialLibraryBook("logWood",MaterialList.wood,20));
		libSet.add(new MaterialLibraryBook(Block.wood,MaterialList.wood,10));
		libSet.add(new MaterialLibraryBook(Block.planks,MaterialList.wood,5));
		libSet.add(new MaterialLibraryBook(Item.stick,MaterialList.wood,2));
		libSet.add(new MaterialLibraryBook(Item.diamond,MaterialList.diamond,2000));
		libSet.add(new MaterialLibraryBook(Block.stone,MaterialList.stone,5));
		libSet.add(new MaterialLibraryBook(Item.feather,MaterialList.feather,3));
		libSet.add(new MaterialLibraryBook(Item.bone,MaterialList.bone,5));
		libSet.add(new MaterialLibraryBook(UnsagaItems.itemMusket,MaterialList.iron,200));
	}
}

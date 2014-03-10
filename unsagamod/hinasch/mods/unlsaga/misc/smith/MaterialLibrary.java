package hinasch.mods.unlsaga.misc.smith;

import hinasch.lib.LibraryShelf;
import hinasch.mods.unlsaga.core.init.UnsagaMaterials;
import hinasch.mods.unlsaga.core.init.UnsagaItems;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item.ToolMaterial;

public class MaterialLibrary extends LibraryShelf{
	public MaterialLibrary(){
		libSet.add(new MaterialLibraryBook(ToolMaterial.EMERALD,UnsagaMaterials.diamond,1));
		libSet.add(new MaterialLibraryBook(ToolMaterial.WOOD,UnsagaMaterials.wood,1));
		libSet.add(new MaterialLibraryBook(ToolMaterial.IRON,UnsagaMaterials.iron,1));
		libSet.add(new MaterialLibraryBook(ToolMaterial.STONE,UnsagaMaterials.stone,1));
		libSet.add(new MaterialLibraryBook(Items.iron_ingot,UnsagaMaterials.iron,100));
		libSet.add(new MaterialLibraryBook("ingotIron",UnsagaMaterials.iron,80));
		libSet.add(new MaterialLibraryBook("ingotCopper",UnsagaMaterials.copper,50));
		libSet.add(new MaterialLibraryBook("ingotSteel",UnsagaMaterials.steel1,300));
		libSet.add(new MaterialLibraryBook("ingotSilver",UnsagaMaterials.silver,70));
		libSet.add(new MaterialLibraryBook("ingotDamascus",UnsagaMaterials.damascus,2000));
		libSet.add(new MaterialLibraryBook("ingotLead",UnsagaMaterials.lead,100));
		libSet.add(new MaterialLibraryBook("gemRuby",UnsagaMaterials.corundum1,1400));
		libSet.add(new MaterialLibraryBook("gemSapphire",UnsagaMaterials.corundum2,1400));
		libSet.add(new MaterialLibraryBook("ingotFaerieSilver",UnsagaMaterials.fairieSilver,1000));
		libSet.add(new MaterialLibraryBook("stoneMeteorite",UnsagaMaterials.meteorite,200));
		libSet.add(new MaterialLibraryBook("ingotMeteoriticIron",UnsagaMaterials.meteoricIron,400));
		libSet.add(new MaterialLibraryBook("gemCarnelian",UnsagaMaterials.carnelian,50));
		libSet.add(new MaterialLibraryBook("gemTopaz",UnsagaMaterials.topaz,50));
		libSet.add(new MaterialLibraryBook("gemRavenite",UnsagaMaterials.ravenite,50));
		libSet.add(new MaterialLibraryBook("gemLapis",UnsagaMaterials.lazuli,50));
		libSet.add(new MaterialLibraryBook("gemOpal",UnsagaMaterials.opal,50));
		libSet.add(new MaterialLibraryBook("gemAngelite",UnsagaMaterials.lazuli,100));
		libSet.add(new MaterialLibraryBook("gemDemonite",UnsagaMaterials.opal,100));
		libSet.add(new MaterialLibraryBook("stoneDebris",UnsagaMaterials.debris,1));
		libSet.add(new MaterialLibraryBook("logWood",UnsagaMaterials.wood,20));
		libSet.add(new MaterialLibraryBook(Blocks.log,UnsagaMaterials.wood,10));
		libSet.add(new MaterialLibraryBook(Blocks.planks,UnsagaMaterials.wood,5));
		libSet.add(new MaterialLibraryBook(Items.stick,UnsagaMaterials.wood,2));
		libSet.add(new MaterialLibraryBook(Items.diamond,UnsagaMaterials.diamond,2000));
		libSet.add(new MaterialLibraryBook(Blocks.stone,UnsagaMaterials.stone,5));
		libSet.add(new MaterialLibraryBook(Items.feather,UnsagaMaterials.feather,3));
		libSet.add(new MaterialLibraryBook(Items.bone,UnsagaMaterials.bone,5));
		libSet.add(new MaterialLibraryBook(Blocks.dragon_egg,UnsagaMaterials.dragonHeart,1500));
		libSet.add(new MaterialLibraryBook(UnsagaItems.itemMusket,UnsagaMaterials.iron,200));
	}
}

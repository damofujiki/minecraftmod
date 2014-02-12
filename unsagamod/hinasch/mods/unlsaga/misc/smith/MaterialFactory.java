package hinasch.mods.unlsaga.misc.smith;

import hinasch.lib.LibraryFactory;
import hinasch.mods.unlsaga.core.init.MaterialList;
import hinasch.mods.unlsaga.core.init.UnsagaItems;
import net.minecraft.block.Block;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;

public class MaterialFactory extends LibraryFactory{
	public MaterialFactory(){
		libSet.add(new MaterialLibraryRefactor(EnumToolMaterial.EMERALD,MaterialList.diamond,1));
		libSet.add(new MaterialLibraryRefactor(EnumToolMaterial.WOOD,MaterialList.wood,1));
		libSet.add(new MaterialLibraryRefactor(EnumToolMaterial.IRON,MaterialList.iron,1));
		libSet.add(new MaterialLibraryRefactor(EnumToolMaterial.STONE,MaterialList.stone,1));
		libSet.add(new MaterialLibraryRefactor(Item.ingotIron,MaterialList.iron,80));
		libSet.add(new MaterialLibraryRefactor("ingotIron",MaterialList.iron,80));
		libSet.add(new MaterialLibraryRefactor("ingotCopper",MaterialList.copper,50));
		libSet.add(new MaterialLibraryRefactor("ingotSteel",MaterialList.steel1,300));
		libSet.add(new MaterialLibraryRefactor("ingotSilver",MaterialList.silver,70));
		libSet.add(new MaterialLibraryRefactor("ingotDamascus",MaterialList.damascus,2000));
		libSet.add(new MaterialLibraryRefactor("ingotLead",MaterialList.lead,100));
		libSet.add(new MaterialLibraryRefactor("gemRuby",MaterialList.corundum1,1400));
		libSet.add(new MaterialLibraryRefactor("gemSapphire",MaterialList.corundum2,1400));
		libSet.add(new MaterialLibraryRefactor("ingotFaerieSilver",MaterialList.fairieSilver,1000));
		libSet.add(new MaterialLibraryRefactor("stoneMeteorite",MaterialList.meteorite,200));
		libSet.add(new MaterialLibraryRefactor("ingotMeteoriticIron",MaterialList.meteoricIron,400));
		libSet.add(new MaterialLibraryRefactor("gemCarnelian",MaterialList.carnelian,50));
		libSet.add(new MaterialLibraryRefactor("gemTopaz",MaterialList.topaz,50));
		libSet.add(new MaterialLibraryRefactor("gemRavenite",MaterialList.ravenite,50));
		libSet.add(new MaterialLibraryRefactor("gemLapis",MaterialList.lazuli,50));
		libSet.add(new MaterialLibraryRefactor("gemOpal",MaterialList.opal,50));
		libSet.add(new MaterialLibraryRefactor("gemAngelite",MaterialList.lazuli,100));
		libSet.add(new MaterialLibraryRefactor("gemDemonite",MaterialList.opal,100));
		libSet.add(new MaterialLibraryRefactor("stoneDebris",MaterialList.debris,1));
		libSet.add(new MaterialLibraryRefactor("logWood",MaterialList.wood,20));
		libSet.add(new MaterialLibraryRefactor(Block.wood,MaterialList.wood,10));
		libSet.add(new MaterialLibraryRefactor(Block.planks,MaterialList.wood,5));
		libSet.add(new MaterialLibraryRefactor(Item.stick,MaterialList.wood,2));
		libSet.add(new MaterialLibraryRefactor(Item.diamond,MaterialList.diamond,2000));
		libSet.add(new MaterialLibraryRefactor(Block.stone,MaterialList.stone,5));
		libSet.add(new MaterialLibraryRefactor(Item.feather,MaterialList.feather,3));
		libSet.add(new MaterialLibraryRefactor(Item.bone,MaterialList.bone,5));
		libSet.add(new MaterialLibraryRefactor(UnsagaItems.itemMusket,MaterialList.iron,200));
	}
}

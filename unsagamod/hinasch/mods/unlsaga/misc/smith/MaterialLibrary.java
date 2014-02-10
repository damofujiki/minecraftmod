package hinasch.mods.unlsaga.misc.smith;

import hinasch.mods.unlsaga.core.init.MaterialList;
import hinasch.mods.unlsaga.core.init.UnsagaItems;
import hinasch.mods.unlsaga.core.init.UnsagaMaterial;

import java.util.HashSet;
import java.util.Iterator;

import net.minecraft.block.Block;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import com.google.common.base.Optional;

public class MaterialLibrary {

	
	protected static HashSet<MaterialLibrary> smithItemsSet = new HashSet();
	public static enum EnumLibraryKey {TOOLMATERIAL,ITEM,BLOCK,STRING,ITEMSTACK};
	public Optional<Integer> itemid = Optional.absent();
	public Optional<Integer> itemmeta = Optional.absent();
	public Optional<String> oreid = Optional.absent();
	public EnumLibraryKey key;
	public UnsagaMaterial material;
	public Optional<EnumToolMaterial> enumtool;
	public int damage;
	
	public static void init(){
		smithItemsSet.add(new MaterialLibrary(EnumToolMaterial.EMERALD,MaterialList.diamond,1));
		smithItemsSet.add(new MaterialLibrary(EnumToolMaterial.WOOD,MaterialList.wood,1));
		smithItemsSet.add(new MaterialLibrary(EnumToolMaterial.IRON,MaterialList.iron,1));
		smithItemsSet.add(new MaterialLibrary(EnumToolMaterial.STONE,MaterialList.stone,1));
		smithItemsSet.add(new MaterialLibrary(Item.ingotIron,MaterialList.iron,80));
		smithItemsSet.add(new MaterialLibrary("ingotIron",MaterialList.iron,80));
		smithItemsSet.add(new MaterialLibrary("ingotCopper",MaterialList.copper,50));
		smithItemsSet.add(new MaterialLibrary("ingotSteel",MaterialList.steel1,300));
		smithItemsSet.add(new MaterialLibrary("ingotSilver",MaterialList.silver,70));
		smithItemsSet.add(new MaterialLibrary("ingotDamascus",MaterialList.damascus,2000));
		smithItemsSet.add(new MaterialLibrary("ingotLead",MaterialList.lead,100));
		smithItemsSet.add(new MaterialLibrary("gemRuby",MaterialList.corundum1,1400));
		smithItemsSet.add(new MaterialLibrary("gemSapphire",MaterialList.corundum2,1400));
		smithItemsSet.add(new MaterialLibrary("ingotFaerieSilver",MaterialList.fairieSilver,1000));
		smithItemsSet.add(new MaterialLibrary("stoneMeteorite",MaterialList.meteorite,200));
		smithItemsSet.add(new MaterialLibrary("ingotMeteoriticIron",MaterialList.meteoricIron,400));
		smithItemsSet.add(new MaterialLibrary("gemCarnelian",MaterialList.carnelian,50));
		smithItemsSet.add(new MaterialLibrary("gemTopaz",MaterialList.topaz,50));
		smithItemsSet.add(new MaterialLibrary("gemRavenite",MaterialList.ravenite,50));
		smithItemsSet.add(new MaterialLibrary("gemLapis",MaterialList.lazuli,50));
		smithItemsSet.add(new MaterialLibrary("gemOpal",MaterialList.opal,50));
		smithItemsSet.add(new MaterialLibrary("gemAngelite",MaterialList.lazuli,100));
		smithItemsSet.add(new MaterialLibrary("gemDemonite",MaterialList.opal,100));
		smithItemsSet.add(new MaterialLibrary("stoneDebris",MaterialList.debris,1));
		smithItemsSet.add(new MaterialLibrary("logWood",MaterialList.wood,20));
		smithItemsSet.add(new MaterialLibrary(Block.wood,MaterialList.wood,10));
		smithItemsSet.add(new MaterialLibrary(Block.planks,MaterialList.wood,5));
		smithItemsSet.add(new MaterialLibrary(Item.stick,MaterialList.wood,2));
		smithItemsSet.add(new MaterialLibrary(Item.diamond,MaterialList.diamond,2000));
		smithItemsSet.add(new MaterialLibrary(Block.stone,MaterialList.stone,5));
		smithItemsSet.add(new MaterialLibrary(Item.feather,MaterialList.feather,3));
		smithItemsSet.add(new MaterialLibrary(Item.bone,MaterialList.bone,5));
		smithItemsSet.add(new MaterialLibrary(UnsagaItems.itemMusket,MaterialList.iron,200));
	}
	
	protected MaterialLibrary(Object par1,UnsagaMaterial material,int damage){
		if(par1 instanceof EnumToolMaterial){
			this.enumtool = Optional.of((EnumToolMaterial)par1);
			this.key = EnumLibraryKey.TOOLMATERIAL;
		}
		if(par1 instanceof Item){
			this.itemid = Optional.of(((Item)par1).itemID);
			this.key = EnumLibraryKey.ITEM;
		}
		if(par1 instanceof Block){
			ItemStack is = new ItemStack((Block)par1,1);
			this.itemid = Optional.of(is.getItem().itemID);
			this.key = EnumLibraryKey.ITEM;
		}
		if(par1 instanceof String){
			this.oreid = Optional.of((String)par1);
			this.key = EnumLibraryKey.STRING;
		}
		if(par1 instanceof ItemStack){
			ItemStack is = (ItemStack)par1;
			this.itemmeta = Optional.of((is.getItemDamage()));
			this.itemid = Optional.of(is.getItem().itemID);
			this.key = EnumLibraryKey.ITEMSTACK;
		}
		this.material = material;
		this.damage = damage;
	}
	
	public static Optional<MaterialLibrary> findEnumInfo(String input){
		Optional<MaterialLibrary> info = Optional.absent();
		for(Iterator<MaterialLibrary> ite=smithItemsSet.iterator();ite.hasNext();){
			MaterialLibrary currentpage = ite.next();
			if(currentpage.key==EnumLibraryKey.TOOLMATERIAL){
				if(currentpage.enumtool.get().toString().equals(input)){
					info = Optional.of(currentpage);
				}
			}
		}
		return info;
	}
	
	public static Optional<MaterialLibrary> findInfo(ItemStack input){
		Optional<MaterialLibrary> info = Optional.absent();
		Item item = (Item)input.getItem();
		for(Iterator<MaterialLibrary> ite=smithItemsSet.iterator();ite.hasNext();){
			MaterialLibrary currentpage = ite.next();
			if(currentpage.key==EnumLibraryKey.ITEMSTACK){
				if(currentpage.itemid.get()==item.itemID && currentpage.itemmeta.get()==input.getItemDamage()){
					info = Optional.of(currentpage);
				}
			}
			if(currentpage.key==EnumLibraryKey.ITEM && !info.isPresent()){
				//Unsaga.debug(currentpage.itemid.get()+":"+item.itemID);
				if(currentpage.itemid.get()==item.itemID){
					info = Optional.of(currentpage);
				}
			}
			if(currentpage.key==EnumLibraryKey.STRING && !info.isPresent()){
				String baseoreid = OreDictionary.getOreName(OreDictionary.getOreID(input));
				//Unsaga.debug(baseoreid);
				if(baseoreid.equals(currentpage.oreid.get())){
					info = Optional.of(currentpage);
				}
				
			}
		}
		return info;
	}
	

	


}

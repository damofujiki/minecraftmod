package hinasch.mods.unlsaga.misc.old;
//package hinasch.mods.unlsaga.misc.bartering;
//
//import hinasch.lib.Library.EnumSelecter;
//import hinasch.mods.unlsaga.core.init.UnsagaMaterial;
//
//import java.util.HashSet;
//import java.util.Iterator;
//
//import net.minecraft.block.Block;
//import net.minecraft.item.EnumToolMaterial;
//import net.minecraft.item.Item;
//import net.minecraft.item.ItemStack;
//import net.minecraftforge.oredict.OreDictionary;
//
//import com.google.common.base.Optional;
//
//public class MerchandiseLibrary {
//
//	
//	protected static HashSet<MerchandiseLibrary> sellSet = new HashSet();
//	public Optional<Integer> itemid = Optional.absent();
//	public Optional<Integer> itemmeta = Optional.absent();
//	public Optional<String> oreid = Optional.absent();
//	public EnumSelecter key;
//	public UnsagaMaterial material;
//	public Optional<EnumToolMaterial> enumtool;
//	public int price;
//	
//	protected MerchandiseLibrary(Object par1,int baseprice){
//		if(par1 instanceof EnumToolMaterial){
//			this.enumtool = Optional.of((EnumToolMaterial)par1);
//			this.key = EnumSelecter.TOOLMATERIAL;
//		}
//		if(par1 instanceof Item){
//			this.itemid = Optional.of(((Item)par1).itemID);
//			this.key = EnumSelecter.ITEM;
//		}
//		if(par1 instanceof Block){
//			ItemStack is = new ItemStack((Block)par1,1);
//			this.itemid = Optional.of(is.getItem().itemID);
//			this.key = EnumSelecter.ITEM;
//		}
//		if(par1 instanceof String){
//			this.oreid = Optional.of((String)par1);
//			this.key = EnumSelecter.STRING;
//		}
//		if(par1 instanceof ItemStack){
//			ItemStack is = (ItemStack)par1;
//			this.itemmeta = Optional.of((is.getItemDamage()));
//			this.itemid = Optional.of(is.getItem().itemID);
//			this.key = EnumSelecter.ITEMSTACK;
//		}
//		this.price = baseprice;
//	}
//	
//	public static Optional<Integer> findPrice(ItemStack input){
//		Optional<MerchandiseLibrary> info = Optional.absent();
//		Item item = (Item)input.getItem();
//		for(Iterator<MerchandiseLibrary> ite=sellSet.iterator();ite.hasNext();){
//			MerchandiseLibrary currentpage = ite.next();
//			switch(currentpage.key){
//			case ITEM:
//				if(currentpage.itemid.get()==item.itemID){
//					info = Optional.of(currentpage);
//				}
//				break;
//			case STRING:
//				String baseoreid = OreDictionary.getOreName(OreDictionary.getOreID(input));
//				//Unsaga.debug(baseoreid);
//				if(baseoreid.equals(currentpage.oreid.get())){
//					info = Optional.of(currentpage);
//				}
//				break;
//			default:
//				break;
//			}
//		}
//		if(info.isPresent()){
//			return Optional.of(info.get().price);
//		}
//		return Optional.absent();
//	}
//	
//	static{
//		sellSet.add(new MerchandiseLibrary("logWood",36));
//		sellSet.add(new MerchandiseLibrary("gemAmber",200));
//		sellSet.add(new MerchandiseLibrary("ingotTin",100));
//		sellSet.add(new MerchandiseLibrary(Block.wood,36));
//		sellSet.add(new MerchandiseLibrary(Block.planks,9));
//		sellSet.add(new MerchandiseLibrary(Block.cobblestone,5));
//		sellSet.add(new MerchandiseLibrary(Block.sand,10));
//		sellSet.add(new MerchandiseLibrary(Block.glass,20));
//		sellSet.add(new MerchandiseLibrary("ingotGold",1000));
//		sellSet.add(new MerchandiseLibrary(Item.ingotGold,900));
//		sellSet.add(new MerchandiseLibrary(Item.redstone,100));
//		sellSet.add(new MerchandiseLibrary(Item.rottenFlesh,5));
//		sellSet.add(new MerchandiseLibrary(Item.fishRaw,100));
//		sellSet.add(new MerchandiseLibrary(Item.enderPearl,1500));
//		sellSet.add(new MerchandiseLibrary(Item.emerald,2500));
//		sellSet.add(new MerchandiseLibrary(Item.goldNugget,100));
//		sellSet.add(new MerchandiseLibrary(Item.silk,10));
//		sellSet.add(new MerchandiseLibrary(Item.feather,100));
//		sellSet.add(new MerchandiseLibrary(Item.diamond,5000));
//	}
//}

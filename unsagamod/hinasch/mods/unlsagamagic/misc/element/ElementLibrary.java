package hinasch.mods.unlsagamagic.misc.element;

import hinasch.lib.LibraryBook;
import hinasch.lib.LibraryShelf;
import hinasch.mods.unlsaga.core.FiveElements;
import hinasch.mods.unlsaga.core.FiveElements.EnumElement;
import hinasch.mods.unlsagamagic.misc.spell.SpellMixTable;

import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.block.BlockOre;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;

import com.google.common.base.Optional;

public class ElementLibrary extends LibraryShelf{


	public HashMap<Material,SpellMixTable> element_material;
	public HashMap<Block,SpellMixTable> element_block;
	public HashMap<Class,SpellMixTable> element_class;
	
	public ElementLibrary(){
		//this.element_material = new HashMap();
		//this.element_class = new HashMap();
		//this.element_block = new HashMap();
		
		put(Material.water, new SpellMixTable(-0.05F,0,0,0.5F,0,0));
		put(Material.snow, new SpellMixTable(-0.04F,0,0,0.5F,0,0));
		put(Material.ice, new SpellMixTable(-0.04F,0,0,0.5F,0,0));
		put(Material.craftedSnow, new SpellMixTable(-0.04F,0,0,0.8F,0,0));
		put(Material.grass, new SpellMixTable(FiveElements.EnumElement.WOOD,0.1F));
		put(Material.wood, new SpellMixTable(FiveElements.EnumElement.WOOD,0.05F));
		put(Material.plants, new SpellMixTable(FiveElements.EnumElement.WOOD,0.02F));
		put(Material.lava, new SpellMixTable(FiveElements.EnumElement.FIRE,0.08F));
		put(Material.leaves, new SpellMixTable(FiveElements.EnumElement.WOOD,0.01F));
		put(Material.iron, new SpellMixTable(FiveElements.EnumElement.METAL,1.0F));
		
		put(Blocks.lava, new SpellMixTable(FiveElements.EnumElement.FIRE,1.0F));
		put(Blocks.flowing_lava, new SpellMixTable(FiveElements.EnumElement.FIRE,0.5F));
		put(Blocks.fire, new SpellMixTable(FiveElements.EnumElement.FIRE,1.0F));
		put(Blocks.flowing_water, new SpellMixTable(-0.04F,0,0,0.5F,0,0));
		put(Blocks.water, new SpellMixTable(-0.04F,0,0,0.8F,0,0));
		put(Blocks.log, new SpellMixTable(FiveElements.EnumElement.WOOD,0.1F));
		put(Blocks.log2, new SpellMixTable(FiveElements.EnumElement.WOOD,0.1F));
		put(Blocks.planks, new SpellMixTable(FiveElements.EnumElement.WOOD,0.1F));
		put(Blocks.netherrack, new SpellMixTable(FiveElements.EnumElement.FORBIDDEN,0.01F));
		put(Blocks.nether_brick, new SpellMixTable(FiveElements.EnumElement.FORBIDDEN,0.05F));
		put(Blocks.end_stone, new SpellMixTable(FiveElements.EnumElement.FORBIDDEN,0.1F));
		
		put(BlockOre.class, new SpellMixTable(FiveElements.EnumElement.METAL,0.2F));
		

	}
	
	public void put(Object obj,SpellMixTable table){
		this.addShelf(new ElementLibraryBook(obj,table));
	}
	@Override
	public Optional<LibraryBook> find(Object object){
		LibraryBook returnbook = null;
		if(object instanceof Block){
			Block block = (Block)object;
			Material material = block.getMaterial();
			for(LibraryBook book:libSet){
				ElementLibraryBook bookelement = (ElementLibraryBook)book;
				if(bookelement.childkey==bookelement.MATERIAL && material==bookelement.material){
					returnbook = bookelement;
				}
				if(bookelement.childkey==bookelement.BLOCK && bookelement.block==block){
					returnbook = bookelement;
				}
				if(bookelement.childkey==bookelement._CLASS && sameOrInstanceOf(bookelement._class,block.getClass())){
					returnbook = bookelement;
				}
			}
			
		}
		if(returnbook!=null){
			return Optional.of(returnbook);
		}
		return Optional.absent();
		//return super.find(object);
	}

	public boolean sameOrInstanceOf(Class class1,Class class2){
		if(class1.isInstance(class2)){
			return true;
		}
		if(class2.isInstance(class1)){
			return true;
		}
		if(class1==class2){
			return true;
		}
		return false;
	}
//	public Optional<SpellMixTable> findBlock(Block block){
//		if(element_block.containsKey(block)){
//			return Optional.of(element_block.get(block));
//		}
//		return Optional.absent();
//	}
//	
//	public Optional<SpellMixTable> findClass(Class classblock){
//		if(element_class.containsKey(classblock)){
//			return Optional.of(element_class.get(classblock));
//		}
//		return Optional.absent();
//	}
//	
//	public Optional<SpellMixTable> findMaterial(Material material){
//		if(element_material.containsKey(material)){
//			return Optional.of(element_material.get(material));
//		}
//		return Optional.absent();
//	}
	//	public Optional<Library> findBlock(Object blockdata){
	//
	//		for(Library book:libSet){
	//			if(book.getKey() == EnumSelector.BLOCK || book.getKey() == EnumSelector.ITEMSTACK){
	//				ElementLibraryBook elementlib = (ElementLibraryBook)book;
	//				switch(elementlib.key2){
	//				case 0:
	//					if(blockdata instanceof PairID){
	//
	//						if(elementlib.blockdata.equals((PairID)blockdata)){
	//							return Optional.of(book);
	//						}
	//					}
	//
	//				case 1:
	//					if(blockdata instanceof PairID){
	//						if(elementlib.blockdata.id==((PairID)blockdata).id){
	//							return Optional.of(book);
	//						}
	//					}
	//				case 2:
	//					if(blockdata instanceof Material){
	//						if(elementlib.material==(Material)blockdata){
	//							return Optional.of(book);
	//						}
	//					}
	//				}
	//
	//			}
	//		}
	//
	//		return Optional.absent();
	//	}
//
//	public Optional<Library> findMaterial(Material material){
//		for(Library book:libSet){
//			if(book.getKey() == EnumSelector.BLOCK){
//				ElementLibraryBook elementlib = (ElementLibraryBook)book;
//				if(elementlib.childKey==elementlib.MATERIAL){
//					if(material instanceof Material){
//						if(elementlib.material==(Material)material){
//							return Optional.of(book);
//						}
//					}
//				}
//			}
//		}
//		return Optional.absent();
//	}
//
//	public Optional<Library> findBlock(PairID blockdata){
//		for(Library book:libSet){
//			if(book.getKey() == EnumSelector.BLOCK){
//				ElementLibraryBook elementlib = (ElementLibraryBook)book;
//				if(elementlib.childKey==elementlib.PAIRID){
//					if(elementlib.hasMeta){
//						if(elementlib.blockdata.id==blockdata.id && elementlib.blockdata.metadata==blockdata.metadata){
//							return Optional.of(book);
//						}
//					}else{
//						if(elementlib.blockdata.id==blockdata.id){
//							return Optional.of(book);
//						}
//					}
//				}
//
//			}
//		}
//		return Optional.absent();
//	}

}

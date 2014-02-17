package hinasch.mods.unlsagamagic.misc.element;

import hinasch.lib.LibraryFactory;
import hinasch.mods.unlsagamagic.misc.element.UnsagaElement.EnumElement;
import hinasch.mods.unlsagamagic.misc.spell.SpellMixTable;

import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.block.BlockOre;
import net.minecraft.block.material.Material;

import com.google.common.base.Optional;

public class ElementLibrary extends LibraryFactory{


	public HashMap<Material,SpellMixTable> element_material;
	public HashMap<Block,SpellMixTable> element_block;
	public HashMap<Class,SpellMixTable> element_class;
	
	public ElementLibrary(){
		this.element_material = new HashMap();
		this.element_class = new HashMap();
		this.element_block = new HashMap();
		
		element_material.put(Material.water, new SpellMixTable(-0.05F,0,0,0.5F,0,0));
		element_material.put(Material.snow, new SpellMixTable(-0.04F,0,0,0.5F,0,0));
		element_material.put(Material.ice, new SpellMixTable(-0.04F,0,0,0.5F,0,0));
		element_material.put(Material.craftedSnow, new SpellMixTable(-0.04F,0,0,0.8F,0,0));
		element_material.put(Material.grass, new SpellMixTable(EnumElement.WOOD,0.1F));
		element_material.put(Material.wood, new SpellMixTable(EnumElement.WOOD,0.05F));
		element_material.put(Material.plants, new SpellMixTable(EnumElement.WOOD,0.02F));
		element_material.put(Material.lava, new SpellMixTable(EnumElement.FIRE,0.08F));
		element_material.put(Material.leaves, new SpellMixTable(EnumElement.WOOD,0.01F));
		element_material.put(Material.iron, new SpellMixTable(EnumElement.METAL,1.0F));
		
		element_block.put(Block.lavaStill, new SpellMixTable(EnumElement.FIRE,1.0F));
		element_block.put(Block.lavaMoving, new SpellMixTable(EnumElement.FIRE,0.5F));
		element_block.put(Block.fire, new SpellMixTable(EnumElement.FIRE,1.0F));
		element_block.put(Block.waterMoving, new SpellMixTable(-0.04F,0,0,0.5F,0,0));
		element_block.put(Block.waterStill, new SpellMixTable(-0.04F,0,0,0.8F,0,0));
		element_block.put(Block.wood, new SpellMixTable(EnumElement.WOOD,0.1F));
		element_block.put(Block.planks, new SpellMixTable(EnumElement.WOOD,0.1F));
		element_block.put(Block.netherrack, new SpellMixTable(EnumElement.FORBIDDEN,0.01F));
		element_block.put(Block.netherBrick, new SpellMixTable(EnumElement.FORBIDDEN,0.05F));
		element_block.put(Block.whiteStone, new SpellMixTable(EnumElement.FORBIDDEN,0.1F));
		
		element_class.put(BlockOre.class, new SpellMixTable(EnumElement.METAL,0.2F));
		

	}

	public Optional<SpellMixTable> findBlock(Block block){
		if(element_block.containsKey(block)){
			return Optional.of(element_block.get(block));
		}
		return Optional.absent();
	}
	
	public Optional<SpellMixTable> findClass(Class classblock){
		if(element_class.containsKey(classblock)){
			return Optional.of(element_class.get(classblock));
		}
		return Optional.absent();
	}
	
	public Optional<SpellMixTable> findMaterial(Material material){
		if(element_material.containsKey(material)){
			return Optional.of(element_material.get(material));
		}
		return Optional.absent();
	}
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

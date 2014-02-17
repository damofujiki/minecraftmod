package hinasch.mods.unlsagamagic.misc.element;



import hinasch.lib.Library;
import hinasch.lib.PairID;
import hinasch.mods.unlsagamagic.misc.element.UnsagaElement.EnumElement;
import hinasch.mods.unlsagamagic.misc.spell.SpellMixTable;
import net.minecraft.block.material.Material;

import com.google.common.base.Optional;

public class ElementLibraryBook extends Library{

	public PairID blockdata;
	public boolean hasMeta = false;
	public EnumElement element;
	public float elementPoint;
	public Optional<SpellMixTable> elementTable = Optional.absent();
	//public EnumSelector keyselector;
	public int childKey;
	public static final int PAIRID = 0;
	public static final int BLOCK = 1;
	public static final int MATERIAL = 2;
	
	public Material material;
	
	protected ElementLibraryBook(Object par1,EnumElement elm,float elementpoint) {
		super(par1);
//		if(par1 instanceof PairID){
//			this.key = EnumSelector.BLOCK;
//			this.key2 = PAIRID;
//			this.blockdata = (PairID)par1;
//		}
//		if(par1 instanceof Block){
//			//this.key = EnumSelector.BLOCK;
//			this.key2 = BLOCK;
//			this.blockdata = new PairID(((Block)par1).blockID,0);
//		}
		if(par1 instanceof PairID){
			this.key = EnumSelector.BLOCK;
			this.childKey = PAIRID;
			this.hasMeta = true;
			this.blockdata = (PairID)par1;
		}
		if(par1 instanceof Material){
			this.key = EnumSelector.BLOCK;
			this.childKey = MATERIAL;
			this.material = (Material)par1;
		}
		this.element = elm;
		this.elementPoint = elementpoint;
	}

	public ElementLibraryBook(Object par1,SpellMixTable elementTable) {
		this(par1,null,0);
		this.elementTable = Optional.of(elementTable);
	}
	

	

}

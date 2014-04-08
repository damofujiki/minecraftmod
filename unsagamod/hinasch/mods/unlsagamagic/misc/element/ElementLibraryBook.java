package hinasch.mods.unlsagamagic.misc.element;



import com.hinasch.lib.LibraryBook;

import hinasch.mods.unlsagamagic.misc.spell.SpellMixTable;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class ElementLibraryBook extends LibraryBook{


	public SpellMixTable table;
	public Block block;
	public static final int _CLASS = 0;
	public static final int BLOCK = 1;
	public static final int MATERIAL = 2;
	public int childkey;
	public Material material;
	public Class _class;

	public ElementLibraryBook(Object par1,SpellMixTable elementTable) {
		super(par1);
		if(par1 instanceof Material){
			this.childkey = MATERIAL;
			this.material = (Material)par1;
		}
		if(par1 instanceof Block){
			this.childkey = BLOCK;
			this.block = (Block)par1;
		}
		if(par1 instanceof Class){
			this.childkey = _CLASS;
			this._class = (Class)par1;
		}
		
		this.table = elementTable;
	}
	

	

}

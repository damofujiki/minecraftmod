package hinasch.mods.unlsaga.misc.util;

import hinasch.mods.unlsaga.core.init.UnsagaItems;
import hinasch.mods.unlsaga.core.init.UnsagaMaterial;
import net.minecraft.item.ItemStack;

public class NoFunctionItem {
	
	public final String name;
	//public final String nameJP;
	public final String dictID;
	public final String iconname;
	public final int rendercolor;
	public final UnsagaMaterial associated;
	public final int number;
	public int repair;
	
	
	public NoFunctionItem(int number,String par1,String par3,String par4,int par5,UnsagaMaterial material){
		this.number = number;
		name = par1;
		//nameJP = par2;
		dictID = par3;
		iconname = par4;
		rendercolor = par5;
		this.associated = material;
	}
	
	public NoFunctionItem(int number,String par1,String par2,String par4,int par5,UnsagaMaterial material,int meta,int damage){
		this(number,par1,par2,par4,par5,material);
		this.repair = damage;
		material.associate(new ItemStack(UnsagaItems.itemMaterials,1,meta));
	}
	

}

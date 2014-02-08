package hinasch.mods.unlsaga.misc.util;

import hinasch.mods.unlsaga.core.init.UnsagaMaterial;

public class NoFuncItem {
	
	public final String name;
	public final String nameJP;
	public final String dictID;
	public final String iconname;
	public final int rendercolor;
	public final UnsagaMaterial associated;
	public int forgedamage;
	
	
	public NoFuncItem(String par1,String par2,String par3,String par4,int par5,UnsagaMaterial material){
		name = par1;
		nameJP = par2;
		dictID = par3;
		iconname = par4;
		rendercolor = par5;
		this.associated = material;
	}
	
	public NoFuncItem(String par1,String par2,String par3,String par4,int par5,UnsagaMaterial material,int meta,int damage){
		this(par1,par2,par3,par4,par5,material);
		this.forgedamage = damage;
		material.linkToItem(meta);
	}
	

}

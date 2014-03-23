package hinasch.mods.unlsaga.misc.util;

import hinasch.lib.PairObject;
import hinasch.mods.unlsaga.core.init.UnsagaMaterial;

public class MaterialPair extends PairObject<UnsagaMaterial,Integer>{


	
	public MaterialPair(UnsagaMaterial material,int id){
		super(material,id);
	}
	
	public UnsagaMaterial getMaterial(){
		return this.left;
	}
	
	public int getId(){
		return this.right;
	}
}

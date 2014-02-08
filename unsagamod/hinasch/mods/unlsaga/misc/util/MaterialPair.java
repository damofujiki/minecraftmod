package hinasch.mods.unlsaga.misc.util;

import hinasch.mods.unlsaga.core.init.UnsagaMaterial;

public class MaterialPair {

	public final UnsagaMaterial material;
	public final int itemid;
	
	public MaterialPair(UnsagaMaterial material,int id){
		this.material = material;
		this.itemid = id;
	}
}

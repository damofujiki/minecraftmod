package hinasch.mods.unlsaga.item;

import hinasch.mods.unlsaga.core.init.UnsagaMaterial;
import hinasch.mods.unlsaga.misc.util.EnumUnsagaTools;


public interface IUnsagaMaterial {

	
	//public UnsagaMaterial unsMaterial = MaterialList.dummy; //Dummy
	//public HashMap<String,Icon> iconMap = new HashMap();
	
	public EnumUnsagaTools getCategory();
	
	public UnsagaMaterial getMaterial();
}

package hinasch.mods.unlsaga.misc.smith;

import hinasch.mods.unlsaga.core.init.UnsagaMaterial;
import hinasch.mods.unlsaga.misc.util.EnumUnsagaTools;
import net.minecraft.item.Item;

public class SmithValidWeapon {

	
	public UnsagaMaterial associated;
	public EnumUnsagaTools category;
	public Item item;
	
	public SmithValidWeapon(Item item,EnumUnsagaTools cate,UnsagaMaterial material){
		this.item = item;
		this.category = cate;
		this.associated = material;
	}
	

}

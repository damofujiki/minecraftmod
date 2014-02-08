package hinasch.mods.unlsaga.misc.smith;

import hinasch.mods.unlsaga.core.init.UnsagaMaterial;
import hinasch.mods.unlsaga.misc.util.EnumUnsagaWeapon;
import net.minecraft.item.Item;

public class SmithValidWeapon {

	
	public UnsagaMaterial associated;
	public EnumUnsagaWeapon category;
	public int itemID;
	
	public SmithValidWeapon(Item item,EnumUnsagaWeapon cate,UnsagaMaterial material){
		this.itemID = item.itemID;
		this.category = cate;
		this.associated = material;
	}
	

}

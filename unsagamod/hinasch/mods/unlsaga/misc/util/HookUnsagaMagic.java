package hinasch.mods.unlsaga.misc.util;

import hinasch.mods.unlsagamagic.UnsagaMagic;
import net.minecraft.item.ItemStack;

public class HookUnsagaMagic {

	public HookUnsagaMagic(){
		
	}
	
	public ItemStack getUnsagaMagicItem(){
		return new ItemStack(UnsagaMagic.itemBlender,1);
	}
}

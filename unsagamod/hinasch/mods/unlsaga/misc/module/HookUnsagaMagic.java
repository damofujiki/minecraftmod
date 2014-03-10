package hinasch.mods.unlsaga.misc.module;

import hinasch.mods.unlsagamagic.UnsagaMagic;
import net.minecraft.item.ItemStack;

public class HookUnsagaMagic {

	public HookUnsagaMagic(){
		
	}
	
	public ItemStack getUnsagaMagicItem(){
		return new ItemStack(UnsagaMagic.itemBlender,1);
	}
}

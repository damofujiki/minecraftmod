package hinasch.mods.unlsaga.misc.ability;

import net.minecraft.item.ItemStack;

public interface IGainAbility {

	public void gainAbility(ItemStack is);
	public void gainAbilityWithChance(ItemStack is);
	public int getMaxAbility();
}

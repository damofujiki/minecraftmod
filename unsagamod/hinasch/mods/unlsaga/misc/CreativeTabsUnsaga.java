package hinasch.mods.unlsaga.misc;


import hinasch.mods.unlsaga.core.init.MaterialList;
import hinasch.mods.unlsaga.core.init.UnsagaItems;
import hinasch.mods.unlsaga.misc.util.EnumUnsagaWeapon;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class CreativeTabsUnsaga extends CreativeTabs{

	public CreativeTabsUnsaga(String label) {
		super(label);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	
	@Override
    public ItemStack getIconItemStack()
    {
        return UnsagaItems.getItem(EnumUnsagaWeapon.AXE, MaterialList.damascus,1,0);
    }
 
	@Override
	public String getTranslatedTabLabel()
	{
		return "Unsaga Mod";
	}
}

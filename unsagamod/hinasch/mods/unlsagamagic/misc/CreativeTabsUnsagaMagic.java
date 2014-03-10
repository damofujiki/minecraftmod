package hinasch.mods.unlsagamagic.misc;


import hinasch.mods.unlsagamagic.item.ItemTablet;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CreativeTabsUnsagaMagic extends CreativeTabs{

	public CreativeTabsUnsagaMagic(String label) {
		super(label);
	
	}

	
//	@Override
//    public ItemStack getIconItemStack()
//    {
//        return UnsagaItems.getItem(EnumUnsagaTools.AXE, MaterialList.damascus,1,0);
//    }
 
	@Override
	public String getTranslatedTabLabel()
	{
		return "Unsaga Mod";
	}


	@Override
	public Item getTabIconItem() {

		return ItemTablet.getDisplayMagicTablet(this).getItem();
	}


	@Override
    public ItemStack getIconItemStack()
    {
    	return ItemTablet.getDisplayMagicTablet(this);
    }
}

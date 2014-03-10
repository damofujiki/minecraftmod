package hinasch.mods.unlsaga.misc;


import hinasch.mods.unlsaga.Unsaga;
import hinasch.mods.unlsaga.core.init.UnsagaMaterials;
import hinasch.mods.unlsaga.core.init.UnsagaItems;
import hinasch.mods.unlsaga.misc.util.EnumUnsagaTools;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class CreativeTabsUnsaga extends CreativeTabs{

	public CreativeTabsUnsaga(String label) {
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
		// TODO 自動生成されたメソッド・スタブ
		Unsaga.debug("creativeitem:"+UnsagaItems.getItemStack(EnumUnsagaTools.AXE, UnsagaMaterials.damascus,1,0));
		return UnsagaItems.getItemStack(EnumUnsagaTools.AXE, UnsagaMaterials.damascus,1,0).getItem();
	}

    @SideOnly(Side.CLIENT)
    public int func_151243_f()
    {
        return UnsagaItems.getItemStack(EnumUnsagaTools.AXE, UnsagaMaterials.damascus,1,0).getItemDamage();
    }


}

package hinasch.mods.creativeitems;

import hinasch.lib.HSLibs;
import hinasch.lib.PropertyCustom;
import hinasch.mods.creativeitems.item.ItemCreativeSpade;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import net.minecraftforge.common.Configuration;

public class ItemRegistry {

	public static Item itemCreativeSpade;
	public static int itemIDCreativeSpade;
	
	public static void configure(Configuration config){
		PropertyCustom prop = new PropertyCustom(new String[]{"itemID.spade"});
		prop.setValues(new Integer[]{1230});
		prop.setCategoriesAll(Configuration.CATEGORY_ITEM);
		prop.buildProps(config);
		
		itemIDCreativeSpade = prop.getProp(0).getInt();
	}
	
	public static void register(){
		itemCreativeSpade = new ItemCreativeSpade(itemIDCreativeSpade, EnumToolMaterial.IRON).setUnlocalizedName("hinasch.creativespade").setCreativeTab(CreativeTabs.tabTools);
		HSLibs.langSet("Creative Spade", "クリエイティブなスコップ", itemCreativeSpade);
	}
}

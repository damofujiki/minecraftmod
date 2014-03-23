package hinasch.mods.unlsagamagic;

import hinasch.lib.HSLibs;
import hinasch.mods.unlsagamagic.block.BlockFireWall;
import hinasch.mods.unlsagamagic.event.EventDecipherAtSleep;
import hinasch.mods.unlsagamagic.event.EventSpellBuff;
import hinasch.mods.unlsagamagic.event.EventSpellTarget;
import hinasch.mods.unlsagamagic.item.ItemBlender;
import hinasch.mods.unlsagamagic.item.ItemSpellBook;
import hinasch.mods.unlsagamagic.item.ItemTablet;
import hinasch.mods.unlsagamagic.misc.CreativeTabsUnsagaMagic;
import hinasch.mods.unlsagamagic.misc.element.ElementLibrary;
import hinasch.mods.unlsagamagic.misc.element.UnsagaElement;
import hinasch.mods.unlsagamagic.misc.spell.Spells;
import hinasch.mods.unlsagamagic.tileentity.TileEntityFireWall;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.common.registry.GameRegistry;


public class UnsagaMagic {

	public static UnsagaMagic instance;
	public static UnsagaElement worldElement;
	public static ElementLibrary elementLibrary;
//	protected static int itemIDMagicTablet;
//	protected static int itemIDSpellBook;
//	protected static int itemIDBlender;
//	protected static int blockIDFireWall;
	public static Block blockFireWall;
	public static Item itemMagicTablet;
	public static Item itemSpellBook;
	public static Item itemBlender;
	
	public static CreativeTabs tabUnsagaMagic;
	

	
	protected UnsagaMagic(){
		
	}
	
	public static UnsagaMagic getInstance(){
		if(instance==null){
			instance = new UnsagaMagic();
		}
		return instance;
	}
	
	public void init(){
		this.elementLibrary = new ElementLibrary();
		this.worldElement = new UnsagaElement();
		Spells.init();
		this.tabUnsagaMagic = new CreativeTabsUnsagaMagic("tabUnsagaMagic");
	}
	
	public void registerEvents(){
		//TickRegistry.registerTickHandler(new EventDecipherAtSleep(), Side.SERVER);
		HSLibs.registerEvent(new EventSpellTarget());
		HSLibs.registerEvent(new EventSpellBuff());
		HSLibs.registerEvent(new EventDecipherAtSleep());
	}
	
	public void registerEntity(){
		//EntityRegistry.registerModEntity(EntityFireArrow.class, EntityDataUnsaga.fireArrow.name, EntityDataUnsaga.fireArrow.id, Unsaga.instance, 250, 5, true);
		//EntityRegistry.registerModEntity(EntityBoulder.class, EntityDataUnsaga.boulder.name, EntityDataUnsaga.boulder.id, Unsaga.instance, 250, 5, true);
	}

	public void initItem(Configuration config) {
		
//		PropertyCustom prop = new PropertyCustom(new String[]{"itemID.tablet","itemID.spellBook","itemID.Blender"});
//		PropertyCustom prop2 = new PropertyCustom(new String[]{"blockID.FireWall"});
//		
//		prop.setValues(new Integer[]{1603,1604,1605});
//		prop2.setValues(new Integer[]{1606});
//
//		prop.setCategoriesAll(config.CATEGORY_GENERAL);
//		prop2.setCategoriesAll(config.CATEGORY_GENERAL);
//
//		prop.buildProps(config);
//		prop2.buildProps(config);

//		itemIDMagicTablet = prop.getProp(0).getInt();
//		itemIDSpellBook = prop.getProp(1).getInt();
//		itemIDBlender = prop.getProp(2).getInt();
//		blockIDFireWall = prop2.getProp(0).getInt();
		

		
		itemMagicTablet = new ItemTablet().setCreativeTab(tabUnsagaMagic).setUnlocalizedName("unsaga.magictablet");
		itemSpellBook = new ItemSpellBook().setCreativeTab(tabUnsagaMagic).setUnlocalizedName("unsaga.spellbook");
		itemBlender = new ItemBlender().setCreativeTab(tabUnsagaMagic).setUnlocalizedName("unsaga.blender");
		blockFireWall = new BlockFireWall().setCreativeTab(tabUnsagaMagic).setBlockName("unsaga.firewall").setLightLevel(1.0F);
		
		

		GameRegistry.registerItem(itemMagicTablet, "itemMagicTablet");
		GameRegistry.registerItem(itemSpellBook, "itemSpellBook");
		GameRegistry.registerItem(itemBlender, "itemBlender");
		
		GameRegistry.registerTileEntity(TileEntityFireWall.class, "unsaga.firewall");
		GameRegistry.registerBlock(blockFireWall,"blockFireWall");
		
	}
}

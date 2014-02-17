package hinasch.mods.unlsagamagic;

import hinasch.lib.HSLibs;
import hinasch.mods.tsukiyotake.lib.PropertyCustom;
import hinasch.mods.unlsaga.Unsaga;
import hinasch.mods.unlsagamagic.entity.EntityBoulder;
import hinasch.mods.unlsagamagic.entity.EntityFireArrow;
import hinasch.mods.unlsagamagic.event.EventDecipherAtSleep;
import hinasch.mods.unlsagamagic.event.EventSpellBuff;
import hinasch.mods.unlsagamagic.event.EventSpellTarget;
import hinasch.mods.unlsagamagic.item.ItemBlender;
import hinasch.mods.unlsagamagic.item.ItemSpellBook;
import hinasch.mods.unlsagamagic.item.ItemTablet;
import hinasch.mods.unlsagamagic.misc.element.ElementLibrary;
import hinasch.mods.unlsagamagic.misc.element.UnsagaElement;
import hinasch.mods.unlsagamagic.misc.spell.SpellRegistry;
import net.minecraft.item.Item;
import net.minecraftforge.common.Configuration;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;


public class UnsagaMagic {

	public static UnsagaElement worldElement;
	public static ElementLibrary elementLibrary;
	protected static int itemIDMagicTablet;
	protected static int itemIDSpellBook;
	protected static int itemIDBlender;
	public static Item itemMagicTablet;
	public static Item itemSpellBook;
	public static Item itemBlender;
	
	public static int GuiBlander = 4;

	
	public UnsagaMagic(){
		
	}
	
	public void init(){
		this.elementLibrary = new ElementLibrary();
		this.worldElement = new UnsagaElement();
		SpellRegistry.initBlenderData();
	}
	
	public void registerEvents(){
		TickRegistry.registerTickHandler(new EventDecipherAtSleep(), Side.SERVER);
		HSLibs.registerEvent(new EventSpellTarget());
		HSLibs.registerEvent(new EventSpellBuff());
	}
	
	public void registerEntity(){
		EntityRegistry.registerModEntity(EntityFireArrow.class, "unsaga.firearrow", 3, Unsaga.instance, 250, 5, true);
		EntityRegistry.registerModEntity(EntityBoulder.class, "unsaga.boulder", 6, Unsaga.instance, 250, 5, true);
	}

	public void initItem(Configuration config) {
		
		PropertyCustom prop = new PropertyCustom(new String[]{"itemID.tablet","itemID.spellBook","itemID.Blender"});

		prop.setValues(new Integer[]{1603,1604,1605});

		prop.setCategoriesAll(config.CATEGORY_ITEM);

		prop.buildProps(config);

		itemIDMagicTablet = prop.getProp(0).getInt();
		itemIDSpellBook = prop.getProp(1).getInt();
		itemIDBlender = prop.getProp(2).getInt();

		
		itemMagicTablet = new ItemTablet(this.itemIDMagicTablet).setCreativeTab(Unsaga.tabUnsaga).setUnlocalizedName("unsaga.magictablet");
		itemSpellBook = new ItemSpellBook(this.itemIDSpellBook).setCreativeTab(Unsaga.tabUnsaga).setUnlocalizedName("unsaga.spellbook");
		itemBlender = new ItemBlender(this.itemIDBlender).setCreativeTab(Unsaga.tabUnsaga).setUnlocalizedName("unsaga.blender");
		
		HSLibs.langSet("Magic Tablet", "魔道板", itemMagicTablet);
		HSLibs.langSet("Spell Book", "術書", itemSpellBook);
		HSLibs.langSet("Blender", "術合成", itemBlender);
	}
}

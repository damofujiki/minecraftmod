package hinasch.mods.creativeitems;

import hinasch.mods.creativeitems.network.CommonProxy;
import net.minecraftforge.common.Configuration;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;


@Mod(modid = "CreativeItems", name = "CreativeItems Mod", dependencies = "required-after:Forge@[7.0,);required-after:FML@[5.0.5,)")
@NetworkMod(channels = { "CreativeItems" }, versionBounds = "[5.2,)", clientSideRequired = true, serverSideRequired = false)//, packetHandler = PacketHandler.class)
public class CreativeItems {
	@SidedProxy(clientSide = "hinasch.mods.creativeitems.client.ClientProxy", serverSide = "hinasch.mods.creativeitems.network.CommonProxy")
	public static CommonProxy proxy;
	@Instance("CreativeItems")
	public static CreativeItems instance;

	//基本情報のロード、イベントのレジスターなど
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		//config.buildConfiguration(event.getSuggestedConfigurationFile());

		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		ItemRegistry.configure(config);
		ItemRegistry.register();
		
		
		config.save();
		//DwarvenBlock.configure(config);
		//DwarvenItem.configure(config);
		//DwarvenEnchantment.configure(config);

		//(new ForgeEventRegistry()).registerEvent();

		//NetworkRegistry.instance().registerGuiHandler(instance, proxy);

		//GameRegistry.registerWorldGenerator(new DwarvenWorldGenerator());
		//GameRegistry.registerCraftingHandler(new DwarvenCraftingHandler());
	}

	//レシピやローカライズなど。
	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		//(new OreDictRegistry()).register();
		//(new ForgeChestHooks()).addLoot();
		//(new LocalizationRegistry()).addLocalization();
		//(new RecipeRegistry()).addRecipe();
	}

}


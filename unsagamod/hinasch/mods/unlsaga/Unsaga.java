package hinasch.mods.unlsaga;

import hinasch.lib.BWrapper;
import hinasch.mods.unlsaga.core.event.EventInitEnemyWeapon;
import hinasch.mods.unlsaga.core.event.EventInteractVillager;
import hinasch.mods.unlsaga.core.event.EventUnsagaToolTip;
import hinasch.mods.unlsaga.core.event.ExtendedMerchantData;
import hinasch.mods.unlsaga.core.event.ExtendedPlayerData;
import hinasch.mods.unlsaga.core.event.TickHandlerUnsaga;
import hinasch.mods.unlsaga.core.init.MaterialList;
import hinasch.mods.unlsaga.core.init.NoFuncItemList;
import hinasch.mods.unlsaga.core.init.UnsagaBlocks;
import hinasch.mods.unlsaga.core.init.UnsagaItems;
import hinasch.mods.unlsaga.entity.EntityArrowUnsaga;
import hinasch.mods.unlsaga.entity.EntityBarrett;
import hinasch.mods.unlsaga.misc.CreativeTabsUnsaga;
import hinasch.mods.unlsaga.misc.ability.AbilityRegistry;
import hinasch.mods.unlsaga.misc.smith.MaterialLibrary;
import hinasch.mods.unlsaga.misc.translation.Translation;
import hinasch.mods.unlsaga.network.CommonProxy;
import hinasch.mods.unlsaga.network.PacketHandler;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

@Mod(modid = "Unsaga", name = "Unsaga Mod", dependencies = "required-after:Forge@[7.0,);required-after:FML@[5.0.5,)")
@NetworkMod(channels = { "unsagamod","unsagamod_gui" }, versionBounds = "[5.2,)", clientSideRequired = true, serverSideRequired = false, packetHandler = PacketHandler.class)
public class Unsaga {
	@SidedProxy(clientSide = "hinasch.mods.unlsaga.client.ClientProxy", serverSide = "hinasch.mods.unlsaga.core.CommonProxy")
	public static CommonProxy proxy;
	@Instance("Unsaga")
	public static Unsaga instance;
	public static BWrapper debug = new BWrapper();
	public static String domain = "hinasch.unlsaga";
	public static int GuiEquipment = 1;
	public static int GuiSmith = 2;
	public static int GuiBartering = 3;
	
	public static AbilityRegistry abilityRegistry;
	public static CreativeTabs tabUnsaga;
	
	//基本情報のロード、イベントのレジスターなど
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		//config.buildConfiguration(event.getSuggestedConfigurationFile());
		
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());


		debug.setFalse();
		checkLoadedMods();
		MaterialList.init();

		tabUnsaga = new CreativeTabsUnsaga("tabUnsaga");
		abilityRegistry = new AbilityRegistry();
		
		UnsagaBlocks.loadConfig(config);
		UnsagaBlocks.registerValues();
		
		
		UnsagaItems.loadConfig(config);
		UnsagaItems.register();
		
		NoFuncItemList.load();
		
		config.save();
		
		
		this.registerEntity();
		proxy.registerRenderers();
		proxy.setDebugUnsaga();
		//DwarvenBlock.configure(config);
		//DwarvenItem.configure(config);
		//DwarvenEnchantment.configure(config);
		
		MinecraftForge.EVENT_BUS.register(new EventInitEnemyWeapon());
		MinecraftForge.EVENT_BUS.register(new EventInteractVillager());
		MinecraftForge.EVENT_BUS.register(new ExtendedPlayerData());
		MinecraftForge.EVENT_BUS.register(new ExtendedMerchantData());
		MinecraftForge.EVENT_BUS.register(new EventUnsagaToolTip());
		TickRegistry.registerTickHandler(new TickHandlerUnsaga(), Side.SERVER);
		//(new ForgeEventRegistry()).registerEvent();
		
		//NetworkRegistry.instance().registerGuiHandler(instance, proxy);
		NetworkRegistry.instance().registerGuiHandler(instance, proxy);
		if(this.debug.get()){
			
		}
		//GameRegistry.registerWorldGenerator(new DwarvenWorldGenerator());
		//GameRegistry.registerCraftingHandler(new DwarvenCraftingHandler());
	}
	
	//レシピやローカライズなど。
	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		MaterialLibrary.init();
		Translation.load();
		NoFuncItemList.setLocalizeAndOreDict();
		UnsagaBlocks.setLocalize();
		

		
		//(new OreDictRegistry()).register();
		//(new ForgeChestHooks()).addLoot();
		//(new LocalizationRegistry()).addLocalization();
		//(new RecipeRegistry()).addRecipe();
	}
	
	public void checkLoadedMods(){

		String className1[] = {
				"hinasch.mods.Debug"
		};
		String cn = null;
		for(int i=0;i<className1.length;i++){
			try{
				//cn = getClassName(className1[i]);
				cn = className1[i];
				System.out.println(cn);
				//cn="realterrainbiomes.mods.RTBiomesCore";
				cn = ""+Class.forName(cn);
				System.out.println(cn+"is ok.");
				if(i==0){
					debug.setTrue();
				}

			}catch(ClassNotFoundException e){

			}
		}

		System.out.println("check end");


	}
	
	public static void debug(Object par1){


		
		if(Unsaga.debug.get()){
			System.out.println("[UnsagaMod]"+par1);
		}
	}
	
	public static void logc(EntityPlayer par3,String par1,boolean debugmes){
		World world = par3.worldObj;
		if(!world.isRemote){
			if(debugmes){
				if(Unsaga.debug.get()){
					par3.addChatMessage(par1);
					return;
				}
			}
			par3.addChatMessage(par1);
			return;
		}
	}
	
	public void registerEntity(){
		EntityRegistry.registerModEntity(EntityArrowUnsaga.class, "unsaga.arrow", 1, this, 250, 5, true);
		EntityRegistry.registerModEntity(EntityBarrett.class, "unsaga.barrett", 2, this, 250, 5, true);
	}
}

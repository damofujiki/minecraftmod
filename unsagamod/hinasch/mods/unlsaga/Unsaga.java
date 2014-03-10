package hinasch.mods.unlsaga;

import hinasch.lib.BWrapper;
import hinasch.lib.HSLibs;
import hinasch.mods.unlsaga.block.BlockDataUnsaga;
import hinasch.mods.unlsaga.core.event.EventGainSkillOnAttack;
import hinasch.mods.unlsaga.core.event.EventInitEnemyWeapon;
import hinasch.mods.unlsaga.core.event.EventInteractEntity;
import hinasch.mods.unlsaga.core.event.EventLivingDeath;
import hinasch.mods.unlsaga.core.event.EventLivingHurt;
import hinasch.mods.unlsaga.core.event.EventLivingUpdate;
import hinasch.mods.unlsaga.core.event.EventUnsagaToolTip;
import hinasch.mods.unlsaga.core.event.ExtendedEntityLivingData;
import hinasch.mods.unlsaga.core.event.ExtendedEntityTag;
import hinasch.mods.unlsaga.core.event.ExtendedMerchantData;
import hinasch.mods.unlsaga.core.event.ExtendedPlayerData;
import hinasch.mods.unlsaga.core.init.NoFuncItemList;
import hinasch.mods.unlsaga.core.init.UnsagaBlocks;
import hinasch.mods.unlsaga.core.init.UnsagaConfigs;
import hinasch.mods.unlsaga.core.init.UnsagaItems;
import hinasch.mods.unlsaga.core.init.UnsagaMaterials;
import hinasch.mods.unlsaga.entity.EntityDataUnsaga;
import hinasch.mods.unlsaga.misc.CreativeTabsUnsaga;
import hinasch.mods.unlsaga.misc.ability.AbilityRegistry;
import hinasch.mods.unlsaga.misc.bartering.MerchandiseLibrary;
import hinasch.mods.unlsaga.misc.module.LPHandlerEmpty;
import hinasch.mods.unlsaga.misc.module.UnsagaMagicHandler;
import hinasch.mods.unlsaga.misc.smith.MaterialLibrary;
import hinasch.mods.unlsaga.misc.translation.Translation;
import hinasch.mods.unlsaga.network.CommonProxy;
import hinasch.mods.unlsaga.network.packet.PacketPipeline;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.config.Configuration;

import com.google.common.base.Optional;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;

@Mod(modid = Unsaga.modid, name = Unsaga.name, version=Unsaga.version)
//@NetworkMod(channels = { "unsagamod","unsagamod_gui" }, versionBounds = "[5.2,)", clientSideRequired = true, serverSideRequired = false, packetHandler = PacketHandler.class)
public class Unsaga {
	@SidedProxy(clientSide = "hinasch.mods.unlsaga.client.ClientProxy", serverSide = "hinasch.mods.unlsaga.core.CommonProxy")
	public static CommonProxy proxy;
	@Instance("Unsaga")
	public static Unsaga instance;
	public static final String modid = "Unsaga";
	public static final String name = "Unsaga Mod";
	public static final String version = "1.0 MC1.7.2";
	public static final String domain = "hinasch.unlsaga";
	public static final PacketPipeline packetPipeline = new PacketPipeline();
	public static BWrapper debug = new BWrapper();


	public static Translation translation;
	
	public static class guiNumber{
		public static final int equipment = 1;
		public static final int smith = 2;
		public static final int bartering = 3;
		public static final int blender = 4;
		public static final int chest = 5;
	}

	

	
	public static AbilityRegistry abilityRegistry;
	public static CreativeTabs tabUnsaga;
	
	public static MaterialLibrary materialFactory = new MaterialLibrary();
	public static MerchandiseLibrary merchandiseFactory = new MerchandiseLibrary();
	
	protected static Optional<UnsagaMagicHandler> module = Optional.absent();
	public static LPHandlerEmpty lpHandler = new LPHandlerEmpty();
	//基本情報のロード、イベントのレジスターなど
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		//config.buildConfiguration(event.getSuggestedConfigurationFile());
		
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());


		debug.setFalse();
		checkLoadedMods();
		UnsagaMaterials.init();

		tabUnsaga = new CreativeTabsUnsaga("tabUnsaga");
		this.translation = Translation.getInstance();
		abilityRegistry = AbilityRegistry.getInstance();
		
		UnsagaBlocks.loadConfig(config);
		UnsagaBlocks.registerValues();

		
		UnsagaItems.loadConfig(config);
		UnsagaItems.register();
		
		if(UnsagaConfigs.module.isMagicEnabled()){
			this.getModuleMagicHandler().preInit();
			this.getModuleMagicHandler().initItem(config);
		}
		
		NoFuncItemList.load();
		
		config.save();
		
		
		EntityDataUnsaga.registerEntities();
		proxy.registerRenderers();
		proxy.setDebugUnsaga();

		
		
		HSLibs.registerEvent(new EventInitEnemyWeapon()); //敵に武器を持たせる関連
		HSLibs.registerEvent(new EventInteractEntity()); //エンティティにライトクリックした時
		HSLibs.registerEvent(new ExtendedPlayerData()); //プレイヤーの拡張インベントリ
		HSLibs.registerEvent(new ExtendedMerchantData()); //物々交換関連
		HSLibs.registerEvent(new EventUnsagaToolTip()); //ツールチップ
		//TickRegistry.registerTickHandler(new TickHandlerUnsaga(), Side.SERVER);
		HSLibs.registerEvent(new EventLivingDeath()); //敵が死んだ時
		HSLibs.registerEvent(new EventGainSkillOnAttack()); //敵を攻撃した時
		HSLibs.registerEvent(new ExtendedEntityLivingData()); //デバフ関連
		HSLibs.registerEvent(new EventLivingUpdate()); //TickHandlerのかわり
		HSLibs.registerEvent(new ExtendedEntityTag()); //矢に属性つけたり
		HSLibs.registerEvent(new EventLivingHurt());
		//HSLibs.registerEvent(new SkillBow());
		
		
		if(UnsagaConfigs.module.isMagicEnabled()){
			module.get().registerEvents();
		}
		//(new ForgeEventRegistry()).registerEvent();
		
		//NetworkRegistry.instance().registerGuiHandler(instance, proxy);
		NetworkRegistry.INSTANCE.registerGuiHandler(instance, proxy);
		if(this.debug.get()){
			
		}
		//GameRegistry.registerWorldGenerator(new DwarvenWorldGenerator());
		//GameRegistry.registerCraftingHandler(new DwarvenCraftingHandler());
	}
	
	//レシピやローカライズなど。
	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		//MaterialLibrary.init();
		packetPipeline.initialise();
		proxy.registerKeyHandler();

		NoFuncItemList.setLocalizeAndOreDict();
		BlockDataUnsaga.registerSmeltingAndAssociation();
		

		
		//(new OreDictRegistry()).register();
		//(new ForgeChestHooks()).addLoot();
		//(new LocalizationRegistry()).addLocalization();
		//(new RecipeRegistry()).addRecipe();
	}
	
	public void checkLoadedMods(){

		String className1[] = {
				"hinasch.mods.Debug","hinasch.mods.unlsagamagic.UnsagaMagic"
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
				if(i==1){
					UnsagaConfigs.module.setMagicEnabled(true, this.instance);
					this.module = Optional.of(new UnsagaMagicHandler());
				}
				if(i==2){
					this.lpHandler = new LPHandlerEmpty();
				}

			}catch(ClassNotFoundException e){

			}
		}

		System.out.println("check end");


	}
	
	//must check configs.ismagicenabled before
	public static UnsagaMagicHandler getModuleMagicHandler(){
		if(Unsaga.module.isPresent()){
			return Unsaga.module.get();
		}
		return null;
	}
	
	
	public static void debug(Object par1){


		
		if(Unsaga.debug.get()){
			System.out.println("[UnsagaMod]"+par1);
		}
	}
	


	

}

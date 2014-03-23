package hinasch.mods.unlsaga.core.init;

import hinasch.mods.unlsaga.misc.util.EnumUnsagaTools;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.EnumHelper;

import com.google.common.base.Optional;

public class UnsagaMaterials {

	public static HashMap<String,UnsagaMaterial> allMaterialMap = new HashMap();
	
	public static final UnsagaMaterial categorywood = new UnsagaMaterial("categorywood",1,0);
	public static final UnsagaMaterial wood = categorywood.addSubMaterial(new UnsagaMaterial("wood",1,0));
	public static final UnsagaMaterial tonerico = categorywood.addSubMaterial(new UnsagaMaterial("toneriko",2,7));
	public static final UnsagaMaterial stone = new UnsagaMaterial("stone",5,1);
	public static final UnsagaMaterial serpentine = stone.addSubMaterial(new UnsagaMaterial("serpentine",5,3));
	public static final UnsagaMaterial copperOre = stone.addSubMaterial(new UnsagaMaterial("copperOre",6,3));	
	public static final UnsagaMaterial debris = stone.addSubMaterial(new UnsagaMaterial("debris",4,1));
	public static final UnsagaMaterial debris2 = stone.addSubMaterial(new UnsagaMaterial("debris2",4,1));
	public static final UnsagaMaterial quartz = stone.addSubMaterial(new UnsagaMaterial("quartz",4,5));	
	public static final UnsagaMaterial meteorite = stone.addSubMaterial(new UnsagaMaterial("meteorite",5,8));	
	public static final UnsagaMaterial ironOre = stone.addSubMaterial(new UnsagaMaterial("ironOre",6,5));	
	
	public static final UnsagaMaterial feather = new UnsagaMaterial("feather",0,4);

	public static final UnsagaMaterial metals = new UnsagaMaterial("metal",4,4);
	public static final UnsagaMaterial iron = metals.addSubMaterial(new UnsagaMaterial("iron",5,5));
	public static final UnsagaMaterial copper = metals.addSubMaterial(new UnsagaMaterial("copper",6,4));
	public static final UnsagaMaterial silver = metals.addSubMaterial(new UnsagaMaterial("silver",3,5));
	public static final UnsagaMaterial meteoricIron = metals.addSubMaterial(new UnsagaMaterial("meteoricIron",4,8));
	public static final UnsagaMaterial lead = metals.addSubMaterial(new UnsagaMaterial("lead",8,4));
	
	public static final UnsagaMaterial steels = new UnsagaMaterial("steels",5,7);
	public static final UnsagaMaterial steel1 = steels.addSubMaterial(new UnsagaMaterial("steel1",5,7));
	public static final UnsagaMaterial steel2 = steels.addSubMaterial(new UnsagaMaterial("steel2",5,8));
	
	public static final UnsagaMaterial obsidian = new UnsagaMaterial("obsidian",8,7);
	
	public static final UnsagaMaterial bestial = new UnsagaMaterial("bestial",3,4);
	public static final UnsagaMaterial topaz = bestial.addSubMaterial(new UnsagaMaterial("topaz",3,4));
	public static final UnsagaMaterial carnelian = bestial.addSubMaterial(new UnsagaMaterial("carnelian",3,4));
	public static final UnsagaMaterial opal = bestial.addSubMaterial(new UnsagaMaterial("opal",3,4));
	public static final UnsagaMaterial ravenite = bestial.addSubMaterial(new UnsagaMaterial("ravenite",3,4));
	public static final UnsagaMaterial lazuli = bestial.addSubMaterial(new UnsagaMaterial("lazuli",3,4));	

	public static final UnsagaMaterial corundums = new UnsagaMaterial("corundums",3,7);	
	public static final UnsagaMaterial corundum1 = corundums.addSubMaterial(new UnsagaMaterial("corundum1",3,7));	
	public static final UnsagaMaterial corundum2 = corundums.addSubMaterial(new UnsagaMaterial("corundum2",3,7));	
	public static final UnsagaMaterial diamond = new UnsagaMaterial("diamond",3,8);	
	public static final UnsagaMaterial fairieSilver = new UnsagaMaterial("fairieSilver",2,8);		
	public static final UnsagaMaterial damascus = new UnsagaMaterial("damascus",6,9);	
	
	public static final UnsagaMaterial cloth = new UnsagaMaterial("cloth",2,2);
	public static final UnsagaMaterial cotton = cloth.addSubMaterial(new UnsagaMaterial("cotton",2,1));
	public static final UnsagaMaterial silk = cloth.addSubMaterial(new UnsagaMaterial("silk",2,3));
	public static final UnsagaMaterial velvet = cloth.addSubMaterial(new UnsagaMaterial("velvet",2,6));
	public static final UnsagaMaterial liveSilk = new UnsagaMaterial("liveSilk",2,9);

	public static final UnsagaMaterial angelite = new UnsagaMaterial("angelite",0,5);
	public static final UnsagaMaterial demonite = new UnsagaMaterial("demonite",0,5);
	
	public static final UnsagaMaterial leathers = new UnsagaMaterial("leathers",0,5);
	public static final UnsagaMaterial bone = new UnsagaMaterial("bone",3,4);
	public static final UnsagaMaterial fur = leathers.addSubMaterial(new UnsagaMaterial("fur",3,2));
	public static final UnsagaMaterial hydra = new UnsagaMaterial("hydra",5,8);
	public static final UnsagaMaterial crocodileLeather = leathers.addSubMaterial(new UnsagaMaterial("crocodileLeather",3,5));

	public static final UnsagaMaterial dragonHeart = new UnsagaMaterial("dragonHeart",3,100);

	public static final UnsagaMaterial failed = new UnsagaMaterial("failed",3,100);
	
	public static final UnsagaMaterial dummy = new UnsagaMaterial("dummy",1,1);	
	
	public static final HashMap<String,ToolMaterial> toolMaterialList = new HashMap();
	public static final HashMap<String,ArmorMaterial> armorMaterialList = new HashMap();
	
	
	
	public static final ToolMaterial def_toolMaterial = EnumHelper.addToolMaterial("unsaga.default", 1, 131, 4.0F, 1.0F, 22);
	public static final ArmorMaterial def_armorMaterial = EnumHelper.addArmorMaterial("unsaga.default", 14, new int[]{2, 6, 5, 2}, 6);
	
	public static class ItemInitData{
		private static Map<EnumUnsagaTools,List<UnsagaMaterial>> unsuitedList = new HashMap();
		
		private static final Map<EnumUnsagaTools,Map<Integer,UnsagaMaterial>> availableMap = new HashMap();
		
		public static void init(){
			availableMap.put(EnumUnsagaTools.SWORD, makeSetInOrder(failed,categorywood,stone,bestial,copper,lead,silver,meteoricIron,iron,fairieSilver,dragonHeart,steels,bone));
			availableMap.put(EnumUnsagaTools.SPEAR, makeSetInOrder(failed,categorywood,stone,bestial,copper,silver,lead,meteoricIron,iron,corundums,fairieSilver,obsidian,steels,diamond,dragonHeart,bone,damascus));
			availableMap.put(EnumUnsagaTools.AXE, makeSetInOrder(failed,stone,bestial,copper,silver,lead,meteoricIron,iron,fairieSilver,obsidian,damascus,steels,bone,dragonHeart));
			availableMap.put(EnumUnsagaTools.STAFF, makeSetInOrder(failed,categorywood,stone,bestial,bone,copper,silver,meteoricIron,lead,iron,obsidian,fairieSilver,steels,diamond,corundums,damascus,dragonHeart));
			availableMap.put(EnumUnsagaTools.BOW, makeSetInOrder(failed,categorywood,bone,copper,lead,meteoricIron,iron,steels,fairieSilver,damascus,dragonHeart));
			availableMap.put(EnumUnsagaTools.ACCESSORY, makeSetInOrder(failed,categorywood,stone,bestial,bone,metals,fairieSilver,steels,corundums,obsidian,damascus,diamond,angelite,demonite));
			availableMap.put(EnumUnsagaTools.BOOTS, makeSetInOrder(failed,cloth,liveSilk,fur,silver,meteorite,hydra,fairieSilver,obsidian));
			availableMap.put(EnumUnsagaTools.LEGGINS, makeSetInOrder(failed,stone,metals,steels,damascus,bestial));
			availableMap.put(EnumUnsagaTools.ARMOR, makeSetInOrder(failed,cloth,liveSilk,fur,crocodileLeather,stone,metals,hydra,steels,obsidian,bestial,damascus));
			availableMap.put(EnumUnsagaTools.HELMET, makeSetInOrder(failed,stone,metals,steels,damascus,fairieSilver,feather,obsidian,corundums,hydra,diamond,bestial));
		}
		
		public static Map<Integer,UnsagaMaterial> getAvailableSet(EnumUnsagaTools category){
			return availableMap.get(category);
		}

		public static List<UnsagaMaterial> getUnsuited(EnumUnsagaTools category){
			return unsuitedList.get(category);
		}

		private static Map<Integer,UnsagaMaterial> makeSetInOrder(UnsagaMaterial... materials){
			Map<Integer,UnsagaMaterial> map = new HashMap();
			for(int i=0;i<materials.length;i++){
				map.put(i,materials[i]);
			}
			return map;
			
			
		}
	}

	
	public static void init(){
		cloth.setArmorMaterial(Items.leather_chestplate.getArmorMaterial());
		liveSilk.setArmorMaterial(addNewArmorMaterial("liveSilk",26,new int[]{3, 8, 6, 3},30));
		fur.setArmorMaterial(ArmorMaterial.CLOTH);
		crocodileLeather.setArmorMaterial(ArmorMaterial.CLOTH);
		crocodileLeather.setSpecialArmorTexture(EnumUnsagaTools.HELMET, "headband", "armor2");
		categorywood.setToolMaterial(ToolMaterial.WOOD).setArmorMaterial(def_armorMaterial);
		categorywood.setIconKey("wood");
		tonerico.setToolMaterial(addNewToolMaterial(tonerico.name,1,120,2.0F,1.0F,30)).setBowModifier(1);
		stone.setToolMaterial(ToolMaterial.STONE);
		stone.setArmorMaterial(addNewArmorMaterial("armor_stone",5,new int[]{2,6,5,2},1));
		stone.setIconKey("stone").setSpecialArmorTexture(EnumUnsagaTools.HELMET, "mask", "armor2");
		metals.setToolMaterial(ToolMaterial.IRON).setArmorMaterial(addNewArmorMaterial("unsaga.metal",14, new int[]{2, 6, 5, 2}, 6));;
		metals.setBowModifier(1);
		copper.setToolMaterial(addNewToolMaterial(copper.name,2, 225, 6.0F, 2.0F, 10));
		copper.setArmorMaterial(addNewArmorMaterial("unsaga.Copper",14, new int[]{2, 6, 5, 2}, 6));
		silver.setToolMaterial(addNewToolMaterial(silver.name,1, 131, 4.0F, 1.0F, 22));
		silver.setArmorMaterial(addNewArmorMaterial("unsaga.Silver",8, new int[]{2, 5, 4, 1}, 22));
		meteoricIron.setToolMaterial(addNewToolMaterial(meteoricIron.name,2, 450, 7.0F, 3.0F, 17));
		meteoricIron.setAttackModifier(3.0F);
		quartz.setAttackModifier(2.0F);
		tonerico.setAttackModifier(1.0F);
		fairieSilver.setToolMaterial(addNewToolMaterial(fairieSilver.name,2, 500, 4.0F, 2.0F, 30));
		fairieSilver.setBowModifier(2).setArmorMaterial(addNewArmorMaterial("fairieSilver",15, new int[]{2, 6, 5, 2}, 32));
		corundums.setToolMaterial(addNewToolMaterial(corundums.name,2, 1000, 7.0F, 4.0F, 20));
		corundums.setArmorMaterial(addNewArmorMaterial("corundum",20, new int[]{3, 7, 6, 3}, 18));
		bestial.setToolMaterial(addNewToolMaterial(bestial.name,1, 131, 4.0F, 1.0F, 15));
		bestial.setArmorMaterial(ArmorMaterial.IRON);

		steels.setToolMaterial(addNewToolMaterial(steels.name,2, 500, 7.0F, 3.0F, 16));
		steels.setBowModifier(2);
		damascus.setToolMaterial(addNewToolMaterial(damascus.name,3, 1400, 8.0F, 4.0F, 14));
		damascus.setBowModifier(3).setArmorMaterial(addNewArmorMaterial("unsaga.Damascus",32,new int[]{3, 8, 6, 3},9));
		steels.setIconKey("steel").setArmorMaterial(addNewArmorMaterial("unsaga.Steel",20, new int[]{3, 7, 6, 3}, 12));
		diamond.setToolMaterial(ToolMaterial.EMERALD).setArmorMaterial(ArmorMaterial.DIAMOND);
		obsidian.setToolMaterial(addNewToolMaterial(obsidian.name,1, 1000, 4.0F, 1.0F, 2));
		obsidian.setSpecialArmorTexture(EnumUnsagaTools.HELMET, "mask", "armor2");
		lead.setToolMaterial(addNewToolMaterial(lead.name,2, 350, 6.0F, 2.0F, 5));
		dragonHeart.setToolMaterial(addNewToolMaterial(dragonHeart.name,2, 1400, 7.0F, 3.0F, 20));
		failed.setToolMaterial(addNewToolMaterial(failed.name,1, 50, 1.0F, 1.0F, 1));
		failed.setArmorMaterial(addNewArmorMaterial("failed",5, new int[]{1, 1, 1, 1}, 1));
		setIcons();
		setRenderColor();
		setItemAssociated();
		setSpecialName();
	}
	


	
	protected static void setSpecialName(){
//		carnelian.setSpecialName(EnumUnsagaTools.ARMOR, "*,朱雀石の鎧");
//		topaz.setSpecialName(EnumUnsagaTools.ARMOR, "*,黄龍石の鎧");
//		opal.setSpecialName(EnumUnsagaTools.ARMOR, "*,白虎石の鎧");
//		lazuli.setSpecialName(EnumUnsagaTools.ARMOR, "*,蒼龍石の鎧");
//		ravenite.setSpecialName(EnumUnsagaTools.ARMOR, "*,玄武石の鎧");
//		meteorite.setSpecialName(EnumUnsagaTools.BOOTS, "Star Boots,星辰の石靴");
//		meteoricIron.setSpecialName(EnumUnsagaTools.BOOTS, "Cosmic Leggins,コスモレガース");
//		meteoricIron.setSpecialName(EnumUnsagaTools.HELMET, "Cosmic Helmet,コスモヘルム");
//		silver.setSpecialName(EnumUnsagaTools.HELMET, "Silver Ring,シルバーリング");
//		silver.setSpecialName(EnumUnsagaTools.BOOTS, "Silver Boots,シルバーブーツ");
	}
	
	protected static void setItemAssociated(){
		lazuli.associate(new ItemStack(Items.dye,1,4));
		quartz.associate(new ItemStack(Items.quartz,1));
		fur.associate(new ItemStack(Items.leather,1));
		bone.associate(new ItemStack(Items.bone,1));
		obsidian.associate(new ItemStack(Blocks.obsidian,1));
		silk.associate(new ItemStack(Items.string,1));
		ironOre.associate(new ItemStack(Blocks.iron_ore,1));

	}
	
	protected static void setRenderColor(){
		damascus.setRenderColor(0x726250);
		crocodileLeather.setRenderColor(0x8f2e14);
		metals.setRenderColor(0x949495);
		stone.setRenderColor(0xadadad);
		tonerico.setRenderColor(0xa59564);
		wood.setRenderColor(0xbfa46f);
		categorywood.setRenderColor(0xbfa46f);
		copper.setRenderColor(0xc37854);
		fairieSilver.setRenderColor(0xa6a5c4);
		meteoricIron.setRenderColor(0xc0c6c9);
		bestial.setRenderColor(0xadadad);
		carnelian.setRenderColor(0xc9171e);
		topaz.setRenderColor(0xdccb18);
		opal.setRenderColor(0xeaf4fc);
		lazuli.setRenderColor(0x192f60);
		ravenite.setRenderColor(0xdcdddd);
		corundum1.setRenderColor(0xba2636);
		corundum2.setRenderColor(0x19448e);
		obsidian.setRenderColor(0x0d0015);
		fur.setRenderColor(0x583822);
		lead.setRenderColor(0x7b7c7d);
		iron.setRenderColor(0xafafb0);
		diamond.setRenderColor(0x89c3eb);
		steels.setRenderColor(0x595857);
		dragonHeart.setRenderColor(0x16160e);
	}
	
	protected static void setIcons(){
		crocodileLeather.setSpecialIcon(EnumUnsagaTools.HELMET,"band");
		cloth.setSpecialIcon(EnumUnsagaTools.HELMET,"bandana");
		cloth.setSpecialIcon(EnumUnsagaTools.BOOTS,"socksicon");
		liveSilk.setSpecialIcon(EnumUnsagaTools.HELMET,"bandana");
		liveSilk.setSpecialIcon(EnumUnsagaTools.BOOTS,"socksicon");
		bone.setSpecialIcon(EnumUnsagaTools.ACCESSORY,"crossbone" );
		damascus.setSpecialIcon(EnumUnsagaTools.ACCESSORY, "breslet_damascus");
		obsidian.setSpecialIcon(EnumUnsagaTools.ACCESSORY, "breslet_damascus");
		diamond.setSpecialIcon(EnumUnsagaTools.ACCESSORY, "ring_dia");
		corundum1.setSpecialIcon(EnumUnsagaTools.ACCESSORY, "ring_ruby");
		corundum2.setSpecialIcon(EnumUnsagaTools.ACCESSORY, "ring_sapphire");
		meteorite.setSpecialIcon(EnumUnsagaTools.ACCESSORY, "amulet_meteorite");
		meteoricIron.setSpecialIcon(EnumUnsagaTools.ACCESSORY, "amulet_meteorite");
		fairieSilver.setSpecialIcon(EnumUnsagaTools.HELMET, "ringhelmet");
		silver.setSpecialIcon(EnumUnsagaTools.HELMET, "ringhelmet");
	}
	
	public static UnsagaMaterial getMaterial(String name){
		Optional<UnsagaMaterial> mat = Optional.of(allMaterialMap.get(name));
		return mat.get();
	}
	
	public static ToolMaterial addNewToolMaterial(String name,int harvest,int maxuse,float efficiency,float damage,int enchant){
		toolMaterialList.put(name, EnumHelper.addToolMaterial(name, harvest, maxuse, efficiency, damage,enchant));
		return toolMaterialList.get(name);
	}
	
	public static ArmorMaterial addNewArmorMaterial(String name,int armor,int[] reduces,int enchant){
		armorMaterialList.put(name, EnumHelper.addArmorMaterial(name, armor, reduces, enchant));
		return armorMaterialList.get(name);
	}
}

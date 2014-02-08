package hinasch.mods.unlsaga.core.init;

import hinasch.mods.unlsaga.misc.util.EnumUnsagaWeapon;

import java.util.HashMap;

import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import net.minecraftforge.common.EnumHelper;

import com.google.common.base.Optional;

public class MaterialList {

	public static HashMap<String,UnsagaMaterial> allMaterialMap = new HashMap();
	
	public static final UnsagaMaterial categorywood = new UnsagaMaterial("categorywood",1,0,"Wood","木");
	public static final UnsagaMaterial wood = categorywood.addSubMaterial(new UnsagaMaterial("wood",1,0,"Wood","木"));
	public static final UnsagaMaterial tonerico = categorywood.addSubMaterial(new UnsagaMaterial("toneriko",2,7,"Toneriko","とねりこ"));
	public static final UnsagaMaterial stone = new UnsagaMaterial("stone",5,1,"Stone","石");
	public static final UnsagaMaterial serpentine = stone.addSubMaterial(new UnsagaMaterial("serpentine",5,3,"Stone","石"));
	public static final UnsagaMaterial copperOre = stone.addSubMaterial(new UnsagaMaterial("copperOre",6,3,"Copper Ore","銅鉱石"));	
	public static final UnsagaMaterial debris = stone.addSubMaterial(new UnsagaMaterial("debris",4,1,"Debris","廃石"));
	public static final UnsagaMaterial debris2 = stone.addSubMaterial(new UnsagaMaterial("debris2",4,1,"Debris","廃石"));
	public static final UnsagaMaterial quartz = stone.addSubMaterial(new UnsagaMaterial("quartz",4,5,"Quartz","石英"));	
	public static final UnsagaMaterial meteorite = stone.addSubMaterial(new UnsagaMaterial("meteorite",5,8,"Meteorite","隕石"));	
	public static final UnsagaMaterial ironOre = stone.addSubMaterial(new UnsagaMaterial("ironOre",6,5,"Iron Ore","鉄鉱石"));	
	
	public static final UnsagaMaterial feather = new UnsagaMaterial("feather",0,4,"Feather","フェザー");

	public static final UnsagaMaterial metal = new UnsagaMaterial("metal",4,4,"Metal","金属");
	public static final UnsagaMaterial iron = metal.addSubMaterial(new UnsagaMaterial("iron",5,5,"Iron","鉄"));
	public static final UnsagaMaterial copper = metal.addSubMaterial(new UnsagaMaterial("copper",6,4,"Copper","銅"));
	public static final UnsagaMaterial silver = metal.addSubMaterial(new UnsagaMaterial("silver",3,5,"Silver","銀"));
	public static final UnsagaMaterial meteoricIron = metal.addSubMaterial(new UnsagaMaterial("meteoricIron",4,8,"Meteoric Iron","隕鉄"));
	public static final UnsagaMaterial lead = metal.addSubMaterial(new UnsagaMaterial("lead",8,4,"Lead","鉛"));
	
	public static final UnsagaMaterial steels = new UnsagaMaterial("steels",5,7,"Steel","鋼");
	public static final UnsagaMaterial steel1 = steels.addSubMaterial(new UnsagaMaterial("steel1",5,7,"Steel","鋼"));
	public static final UnsagaMaterial steel2 = steels.addSubMaterial(new UnsagaMaterial("steel2",5,8,"Steel","鋼"));
	
	public static final UnsagaMaterial obsidian = new UnsagaMaterial("obsidian",8,3,"Obsidian","黒曜石");
	
	public static final UnsagaMaterial bestial = new UnsagaMaterial("bestial",3,4,"Bestial","獣石");
	public static final UnsagaMaterial topaz = bestial.addSubMaterial(new UnsagaMaterial("topaz",3,4,"Topaz","黄龍石"));
	public static final UnsagaMaterial carnelian = bestial.addSubMaterial(new UnsagaMaterial("carnelian",3,4,"Carnelian","朱雀石"));
	public static final UnsagaMaterial opal = bestial.addSubMaterial(new UnsagaMaterial("opal",3,4,"Opal","白虎石"));
	public static final UnsagaMaterial ravenite = bestial.addSubMaterial(new UnsagaMaterial("ravenite",3,4,"Ravenite","玄武石"));
	public static final UnsagaMaterial lazuli = bestial.addSubMaterial(new UnsagaMaterial("lazuli",3,4,"Lazuli","蒼龍石"));	

	public static final UnsagaMaterial corundums = new UnsagaMaterial("corundums",3,7,"Corundums","鋼玉");	
	public static final UnsagaMaterial corundum1 = corundums.addSubMaterial(new UnsagaMaterial("corundum1",3,7,"Corundum","鋼玉"));	
	public static final UnsagaMaterial corundum2 = corundums.addSubMaterial(new UnsagaMaterial("corundum2",3,7,"Corundum","鋼玉"));	
	public static final UnsagaMaterial diamond = new UnsagaMaterial("diamond",3,8,"Dia","金剛石");	
	public static final UnsagaMaterial fairieSilver = new UnsagaMaterial("fairieSilver",2,8,"Fairie Silver","白銀","精霊銀");		
	public static final UnsagaMaterial damascus = new UnsagaMaterial("damascus",6,9,"Damuscus","黒鋼","ダマスカス");	
	
	public static final UnsagaMaterial cloth = new UnsagaMaterial("cloth",2,2,"Cloth","クロース");
	public static final UnsagaMaterial cotton = cloth.addSubMaterial(new UnsagaMaterial("cotton",2,1,"Cotton","コットン"));
	public static final UnsagaMaterial silk = cloth.addSubMaterial(new UnsagaMaterial("silk",2,3,"Silk","シルク"));
	public static final UnsagaMaterial velvet = cloth.addSubMaterial(new UnsagaMaterial("velvet",2,6,"Velvet","ベルベット"));
	public static final UnsagaMaterial liveSilk = new UnsagaMaterial("liveSilk",2,9,"Live Silk","ライブシルク");

	public static final UnsagaMaterial angelite = new UnsagaMaterial("angelite",0,5,"Angelite","聖石");
	public static final UnsagaMaterial demonite = new UnsagaMaterial("demonite",0,5,"Demonite","魔石");
	
	public static final UnsagaMaterial leathers = new UnsagaMaterial("leathers",0,5,"Leather","革");
	public static final UnsagaMaterial bone = new UnsagaMaterial("bone",3,4,"Bone","骨","骨材");
	public static final UnsagaMaterial fur = leathers.addSubMaterial(new UnsagaMaterial("fur",3,2,"Fur","毛皮"));
	public static final UnsagaMaterial hydra = new UnsagaMaterial("hydra",5,8,"Hydra Leather","ヒドラ革");
	public static final UnsagaMaterial crocodileLeather = leathers.addSubMaterial(new UnsagaMaterial("crocodileLeather",3,5,"Leather","ワニ革"));

	public static final UnsagaMaterial dummy = new UnsagaMaterial("dummy",1,1,"Dummy","ダミー");	
	
	public static final HashMap<String,EnumToolMaterial> toolMaterialList = new HashMap();
	public static final HashMap<String,EnumArmorMaterial> armorMaterialList = new HashMap();
	
	
	public static void init(){
		cloth.setArmorMaterial(EnumArmorMaterial.CLOTH);
		liveSilk.setArmorMaterial(addNewArmorMaterial("liveSilk",26,new int[]{3, 8, 6, 3},30));
		fur.setArmorMaterial(EnumArmorMaterial.CLOTH);
		crocodileLeather.setArmorMaterial(EnumArmorMaterial.CLOTH);
		crocodileLeather.setSpecialArmorTexture(EnumUnsagaWeapon.HELMET, "headband", "armor2");
		categorywood.setToolMaterial(EnumToolMaterial.WOOD).setArmorMaterial(EnumArmorMaterial.CLOTH);
		categorywood.setIconKey("wood");
		tonerico.setToolMaterial(addNewToolMaterial(tonerico.name,1,120,2.0F,1.0F,30)).setBowModifier(1);
		stone.setToolMaterial(EnumToolMaterial.STONE);
		stone.setArmorMaterial(addNewArmorMaterial("armor_stone",5,new int[]{2,6,5,2},1));
		stone.setIconKey("stone").setSpecialArmorTexture(EnumUnsagaWeapon.HELMET, "mask", "armor2");
		metal.setToolMaterial(EnumToolMaterial.IRON).setArmorMaterial(addNewArmorMaterial("unsaga.metal",14, new int[]{2, 6, 5, 2}, 6));;
		metal.setBowModifier(1);
		copper.setToolMaterial(addNewToolMaterial(copper.name,2, 225, 6.0F, 2.0F, 10));
		copper.setArmorMaterial(addNewArmorMaterial("unsaga.Copper",14, new int[]{2, 6, 5, 2}, 6));
		silver.setToolMaterial(addNewToolMaterial(silver.name,1, 131, 4.0F, 1.0F, 22));
		silver.setArmorMaterial(addNewArmorMaterial("unsaga.Silver",8, new int[]{2, 5, 4, 1}, 22));
		meteoricIron.setToolMaterial(addNewToolMaterial(meteoricIron.name,2, 450, 7.0F, 3.0F, 17));
		fairieSilver.setToolMaterial(addNewToolMaterial(fairieSilver.name,2, 500, 4.0F, 2.0F, 30));
		fairieSilver.setBowModifier(2).setArmorMaterial(addNewArmorMaterial("fairieSilver",15, new int[]{2, 6, 5, 2}, 32));
		corundums.setToolMaterial(addNewToolMaterial(corundums.name,2, 1000, 7.0F, 4.0F, 20));
		corundums.setArmorMaterial(addNewArmorMaterial("corundum",20, new int[]{3, 7, 6, 3}, 18));
		bestial.setToolMaterial(addNewToolMaterial(bestial.name,1, 131, 4.0F, 1.0F, 15));
		bestial.setArmorMaterial(EnumArmorMaterial.IRON);
		carnelian.setSpecialName(EnumUnsagaWeapon.ARMOR, "*,朱雀石の鎧");
		topaz.setSpecialName(EnumUnsagaWeapon.ARMOR, "*,黄龍石の鎧");
		opal.setSpecialName(EnumUnsagaWeapon.ARMOR, "*,白虎石の鎧");
		lazuli.setSpecialName(EnumUnsagaWeapon.ARMOR, "*,蒼龍石の鎧");
		ravenite.setSpecialName(EnumUnsagaWeapon.ARMOR, "*,玄武石の鎧");
		steels.setToolMaterial(addNewToolMaterial(steels.name,2, 500, 7.0F, 3.0F, 16));
		steels.setBowModifier(2);
		damascus.setToolMaterial(addNewToolMaterial(damascus.name,3, 1400, 8.0F, 4.0F, 14));
		damascus.setBowModifier(3).setArmorMaterial(addNewArmorMaterial("unsaga.Damascus",32,new int[]{3, 8, 6, 3},9));
		steels.setIconKey("steel").setArmorMaterial(addNewArmorMaterial("unsaga.Steel",20, new int[]{3, 7, 6, 3}, 12));
		diamond.setToolMaterial(EnumToolMaterial.EMERALD).setArmorMaterial(EnumArmorMaterial.DIAMOND);
		obsidian.setToolMaterial(addNewToolMaterial(obsidian.name,1, 1000, 4.0F, 1.0F, 2));
		obsidian.setSpecialArmorTexture(EnumUnsagaWeapon.HELMET, "mask", "armor2");
		lead.setToolMaterial(addNewToolMaterial(lead.name,2, 350, 6.0F, 2.0F, 5));
		lazuli.relateVanillaItem(Item.dyePowder.itemID, 4);
		quartz.relateVanillaItem(Item.netherQuartz.itemID, 0);

		setIcons();
		setRenderColor();
	}
	
	protected static void setRenderColor(){
		damascus.setRenderColor(0x726250);
		crocodileLeather.setRenderColor(0x8f2e14);
		metal.setRenderColor(0x949495);
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
	}
	
	protected static void setIcons(){
		crocodileLeather.setSpecialIcon(EnumUnsagaWeapon.HELMET,"band");
		cloth.setSpecialIcon(EnumUnsagaWeapon.HELMET,"bandana");
		liveSilk.setSpecialIcon(EnumUnsagaWeapon.HELMET,"bandana");
		bone.setSpecialIcon(EnumUnsagaWeapon.ACCESSORY,"crossbone" );
		damascus.setSpecialIcon(EnumUnsagaWeapon.ACCESSORY, "breslet_damascus");
		obsidian.setSpecialIcon(EnumUnsagaWeapon.ACCESSORY, "breslet_damascus");
		diamond.setSpecialIcon(EnumUnsagaWeapon.ACCESSORY, "ring_dia");
		corundum1.setSpecialIcon(EnumUnsagaWeapon.ACCESSORY, "ring_ruby");
		corundum2.setSpecialIcon(EnumUnsagaWeapon.ACCESSORY, "ring_sapphire");
		fairieSilver.setSpecialIcon(EnumUnsagaWeapon.HELMET, "ringhelmet");
	}
	
	public static UnsagaMaterial getMaterial(String name){
		Optional<UnsagaMaterial> mat = Optional.of(allMaterialMap.get(name));
		return mat.get();
	}
	
	public static EnumToolMaterial addNewToolMaterial(String name,int harvest,int maxuse,float efficiency,float damage,int enchant){
		toolMaterialList.put(name, EnumHelper.addToolMaterial(name, harvest, maxuse, efficiency, damage,enchant));
		return toolMaterialList.get(name);
	}
	
	public static EnumArmorMaterial addNewArmorMaterial(String name,int armor,int[] reduces,int enchant){
		armorMaterialList.put(name, EnumHelper.addArmorMaterial(name, armor, reduces, enchant));
		return armorMaterialList.get(name);
	}
}

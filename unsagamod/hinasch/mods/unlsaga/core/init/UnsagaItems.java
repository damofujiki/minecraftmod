package hinasch.mods.unlsaga.core.init;

import hinasch.lib.HSLibs;
import hinasch.mods.tsukiyotake.lib.PropertyCustom;
import hinasch.mods.unlsaga.Unsaga;
import hinasch.mods.unlsaga.item.etc.ItemAccessory;
import hinasch.mods.unlsaga.item.etc.ItemArmorUnsaga;
import hinasch.mods.unlsaga.item.etc.ItemBarrett;
import hinasch.mods.unlsaga.item.etc.ItemIngotsUnsaga;
import hinasch.mods.unlsaga.item.weapon.ItemAxeUnsaga;
import hinasch.mods.unlsaga.item.weapon.ItemBowUnsaga;
import hinasch.mods.unlsaga.item.weapon.ItemGunUnsaga;
import hinasch.mods.unlsaga.item.weapon.ItemSpearUnsaga;
import hinasch.mods.unlsaga.item.weapon.ItemStaffUnsaga;
import hinasch.mods.unlsaga.item.weapon.ItemSwordUnsaga;
import hinasch.mods.unlsaga.misc.util.EnumUnsagaWeapon;
import hinasch.mods.unlsaga.misc.util.HelperUnsagaWeapon;
import hinasch.mods.unlsaga.misc.util.MaterialPair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;

import com.google.common.base.Optional;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;

public class UnsagaItems {


	public static int itemIDsSwords;
	public static int itemIDsAxes;
	public static int itemIDsStaffs;
	public static int itemIDsSpears;
	public static int itemIDsBows;
	public static int itemIDsAccessories;
	public static int itemIDsArmors;

	public static int itemIDBarrett;
	public static int itemIDGun;
	public static int itemIDMagicTablet;
	public static int itemSpellBookID;
	public static int itemToolsHSID;
	public static int itemIDIngotsUnsaga;

	public static Item itemMagicTablet;
	public static Item itemSpellBook;
	public static Item itemToolsHS;
	public static Item itemMusket;
	public static Item itemBlenderSpell;
	public static Item itemKey;
	public static Item itemBarrett;
	public static Item itemNoFunc;


	public static HashMap<String,Integer> itemMap = new HashMap();
	public static Item[] itemSwords = new Item[30];
	//public static HashMap<String,Integer> swordMap = new HashMap();
	public static Item[] itemAxes = new Item[30];
	//public static HashMap<String,Integer> axeMap = new HashMap();
	public static Item[] itemStaffs = new Item[30];
	//public static HashMap<String,Integer> staffMap = new HashMap();
	public static Item[] itemSpears = new Item[30];
	//public static HashMap<String,Integer> spearMap = new HashMap();
	public static Item[] itemKnives = new Item[30];
	//public static HashMap<String,Integer> knifeMap = new HashMap();
	public static Item[] itemAccessories = new Item[50];
	//public static HashMap<String,Integer> acsMap = new HashMap();
	public static Item[] itemArmors = new Item[80];
	//public static HashMap<String,Integer> armorMap = new HashMap();
	public static Item[] itemBows = new Item[30];
	//public static HashMap<String,Integer> bowMap = new HashMap();

	public static ArrayList<String> keyExcept;
	
	public static Multimap<EnumUnsagaWeapon,MaterialPair> validMaterialMap = ArrayListMultimap.create();

	public static enum EnumSelecterItem {BOWONLY,WEAPONONLY,MERCHANDISE};
	public static void loadConfig(Configuration config){
		PropertyCustom prop = new PropertyCustom(new String[]{"itemIDs.Swords","itemIDs.Axes","itemIDs.Staffs"
				,"itemIDs.Spears","itemIDs.Bows","itemIDs.Accessories","itemIDs.Armors","itemID.Ingots","itemID.Barrett"
				,"itemID.Gun"});

		prop.setValues(new Integer[]{1200,1230,1260,1290,1320,1370,1450,1600,1601,1602});

		prop.setCategoriesAll(config.CATEGORY_ITEM);

		prop.buildProps(config);

		itemIDsSwords = prop.getProp(0).getInt();
		itemIDsAxes = prop.getProp(1).getInt();
		itemIDsStaffs = prop.getProp(2).getInt();
		itemIDsSpears = prop.getProp(3).getInt();
		itemIDsBows = prop.getProp(4).getInt();
		itemIDsAccessories = prop.getProp(5).getInt();
		itemIDsArmors = prop.getProp(6).getInt();
		itemIDIngotsUnsaga = prop.getProp(7).getInt();
		itemIDBarrett = prop.getProp(8).getInt();
		itemIDGun = prop.getProp(9).getInt();

	}

	public static void register(){
		String particle = "";
		String header = "";
		String header2 = "";
		String footer = "";
		String footer2 = "";
		List<String> sets = Lists.newArrayList();
		List<String> noParticles = Lists.newArrayList();
		//sets = Lists.newArrayList("wood","debris","debris2","copperOre","ironOre","quartz","meteorite","carnelian","topaz","opal","ravenite","lazuli");
		//		,"copper","silver","lead","meteoricIron","iron","fairieSilver","obsidian","damascus","steel1","steel2"};
		sets = Lists.newArrayList("categorywood","stone","bestial","copper","silver","lead","meteoricIron","iron","fairieSilver","obsidian","damascus","steels");		
		noParticles = Lists.newArrayList("stone","wood","copper");
		for(int i=0;i<sets.size();i++){
			UnsagaMaterial uns = MaterialList.getMaterial(sets.get(i));
			itemSwords[i] = new ItemSwordUnsaga(itemIDsSwords+i,uns.getToolMaterial(),uns).setUnlocalizedName("unsaga.sword."+uns.name).setCreativeTab(Unsaga.tabUnsaga);
			//GameData.newItemAdded(itemSwords[i]);	
			particle = getParticle(noParticles,uns);
			header = uns.headerEn;
			header2 = uns.headerJp;
			footer = " Sword";
			footer2 = "剣";
			if(uns.isChild){
				if(!uns.getParentMaterial().name.equals("metal")){
					header = uns.getParentMaterial().headerEn;
					header2 = uns.getParentMaterial().headerJp;
				}
			}else{
				if(uns.name.equals("silver")){
					header2 = "シルバー";
					particle = "";
					footer2 = "ソード";
				}
			}
			HSLibs.langSet(header+footer, header2+particle+footer2, itemSwords[i]);
		}

		sets = Lists.newArrayList("categorywood","stone","bestial","copper","silver","lead","meteoricIron","iron","fairieSilver","obsidian","damascus","steels");		
		noParticles = Lists.newArrayList("stone");
		for(int i=0;i<sets.size();i++){
			UnsagaMaterial uns = MaterialList.getMaterial(sets.get(i));
			itemAxes[i] = new ItemAxeUnsaga(itemIDsAxes+i,uns.getToolMaterial(),uns).setUnlocalizedName("unsaga.axe."+uns.name).setCreativeTab(Unsaga.tabUnsaga);
			//GameData.newItemAdded(itemAxes[i]);	
			particle = getParticle(noParticles,uns);
			header = uns.headerEn;
			header2 = uns.headerJp;
			footer = " Axe";
			footer2 = "斧";
			if(uns.isChild){
				if(!uns.getParentMaterial().name.equals("metal")){
					header = uns.getParentMaterial().headerEn;
					header2 = uns.getParentMaterial().headerJp;
				}
			}
			HSLibs.langSet(header+footer, header2+particle+footer2, itemAxes[i]);
		}

		sets = Lists.newArrayList("categorywood","stone","bestial","copper","silver","lead","meteoricIron","iron",
				"corundum1","corundum2","fairieSilver","obsidian","damascus","steels","diamond");		
		noParticles = Lists.newArrayList("categorywood","stone","iron");
		for(int i=0;i<sets.size();i++){
			UnsagaMaterial uns = MaterialList.getMaterial(sets.get(i));
			itemSpears[i] = new ItemSpearUnsaga(itemIDsSpears+i,uns.getToolMaterial(),uns).setUnlocalizedName("unsaga.spear."+uns.name).setCreativeTab(Unsaga.tabUnsaga);
			//GameData.newItemAdded(itemSpears[i]);	
			particle = getParticle(noParticles,uns);
			header = uns.headerEn;
			header2 = uns.headerJp;
			footer = " Spear";
			footer2 = "槍";
			if(uns.isChild){
				if(!uns.getParentMaterial().name.equals("metal")){
					header = uns.getParentMaterial().headerEn;
					header2 = uns.getParentMaterial().headerJp;
				}
			}
			HSLibs.langSet(header+footer, header2+particle+footer2, itemSpears[i]);
		}


		sets = Lists.newArrayList("categorywood","stone","bestial","copper","silver","lead","meteoricIron","iron","fairieSilver","obsidian","damascus","steels"
				,"corundum1","corundum2","diamond");		
		noParticles = Lists.newArrayList("stone");
		for(int i=0;i<sets.size();i++){
			UnsagaMaterial uns = MaterialList.getMaterial(sets.get(i));
			itemStaffs[i] = new ItemStaffUnsaga(itemIDsStaffs+i,uns.getToolMaterial(),uns).setUnlocalizedName("unsaga.staff."+uns.name).setCreativeTab(Unsaga.tabUnsaga);
			//GameData.newItemAdded(itemStaffs[i]);	
			particle = getParticle(noParticles,uns);
			header = uns.headerEn;
			header2 = uns.headerJp;
			footer = " Staff";
			footer2 = "杖";
			if(uns.name.equals("categorywood")){
				header2 = "";
				particle = "";
			}
			//			if(uns.isChild){
			//				if(!uns.getParentMaterial().name.equals("metal")){
			//					header = uns.getParentMaterial().headerEn;
			//					header2 = uns.getParentMaterial().headerJp;
			//				}
			//			}
			HSLibs.langSet(header+footer, header2+particle+footer2, itemStaffs[i]);
		}
		
		sets = Lists.newArrayList("wood","toneriko","metal","fairieSilver","steels","damascus");		
		noParticles = Lists.newArrayList("metal");
		for(int i=0;i<sets.size();i++){
			UnsagaMaterial uns = MaterialList.getMaterial(sets.get(i));
			itemBows[i] = new ItemBowUnsaga(itemIDsBows+i,uns).setUnlocalizedName("unsaga.bows."+uns.name).setCreativeTab(Unsaga.tabUnsaga)
					.setMaxDamage(uns.getToolMaterial().getMaxUses()+320);
			//GameData.newItemAdded(itemBows[i]);	
			particle = getParticle(noParticles,uns);
			header = uns.headerEn;
			header2 = uns.headerJp;
			footer = " Bow";
			footer2 = "弓";
			if(uns.name.equals("wood")){
				header2 = "";
				particle = "";
				footer2 = "長弓";
			}
			if(uns.name.equals("toneriko")){
				header2 = "";
				particle = "";
				footer2 = "霊木の弓";
			}
			HSLibs.langSet(header+footer, header2+particle+footer2, itemBows[i]);
		}
		
		ArmorName armorname = new ArmorName();
		
		sets = Lists.newArrayList("cloth","liveSilk","fur","meteorite","silver","fairieSilver","hydra","obsidian");
		int counter = 0;
		for(int i=0;i<sets.size();i++){
			UnsagaMaterial uns = MaterialList.getMaterial(sets.get(i));
			Unsaga.debug(uns.name);
			itemArmors[counter] = new ItemArmorUnsaga(itemIDsArmors+counter, uns.getArmorMaterial(), EnumUnsagaWeapon.BOOTS, 3, uns).setUnlocalizedName("unsaga.boots."+uns.name).setCreativeTab(Unsaga.tabUnsaga);
			//GameData.newItemAdded(itemArmors[counter]);	
			if(armorname.getLegsName(uns)!=null){
				HSLibs.langSet(armorname.getLegsName(uns).get(0), armorname.getLegsName(uns).get(1), itemArmors[counter]);
			}
			counter++;
		}
		
		sets = Lists.newArrayList("stone","metal","steels","damascus");
		for(int i=0;i<sets.size();i++){
			UnsagaMaterial uns = MaterialList.getMaterial(sets.get(i));
			Unsaga.debug(uns.name);
			itemArmors[counter] = new ItemArmorUnsaga(itemIDsArmors+counter, uns.getArmorMaterial(), EnumUnsagaWeapon.LEGGINS, 2, uns).setUnlocalizedName("unsaga.boots."+uns.name).setCreativeTab(Unsaga.tabUnsaga);
			//GameData.newItemAdded(itemArmors[counter]);	
			if(armorname.getLegsName(uns)!=null){
				HSLibs.langSet(armorname.getLegsName(uns).get(0), armorname.getLegsName(uns).get(1), itemArmors[counter]);
			}
			counter++;
		}

		
		sets = Lists.newArrayList("cloth","liveSilk","fur","crocodileLeather","stone","metal","hydra","corundum1","corundum2","steels"
				,"fairieSilver","obsidian","diamond","damascus");
		for(int i=0;i<sets.size();i++){
			UnsagaMaterial uns = MaterialList.getMaterial(sets.get(i));
			Unsaga.debug(uns.name);
			itemArmors[counter] = new ItemArmorUnsaga(itemIDsArmors+counter, uns.getArmorMaterial(), EnumUnsagaWeapon.HELMET, 0, uns).setUnlocalizedName("unsaga.helmet."+uns.name).setCreativeTab(Unsaga.tabUnsaga);
			//GameData.newItemAdded(itemArmors[counter]);			
			if(armorname.getHelmetName(uns)!=null){
				HSLibs.langSet(armorname.getHelmetName(uns).get(0), armorname.getHelmetName(uns).get(1), itemArmors[counter]);
			}
			counter++;
		}
		
		sets = Lists.newArrayList("cloth","categorywood","stone","leathers","metal","bestial","hydra","liveSilk","steels"
				,"fairieSilver","obsidian","damascus");
		for(int i=0;i<sets.size();i++){
			UnsagaMaterial uns = MaterialList.getMaterial(sets.get(i));
			Unsaga.debug(uns.name);
			itemArmors[counter] = new ItemArmorUnsaga(itemIDsArmors+counter, uns.getArmorMaterial(), EnumUnsagaWeapon.ARMOR, 1, uns).setUnlocalizedName("unsaga.armor."+uns.name).setCreativeTab(Unsaga.tabUnsaga);
			//GameData.newItemAdded(itemArmors[counter]);
			if(armorname.getArmorName(uns)!=null){
				HSLibs.langSet(armorname.getArmorName(uns).get(0), armorname.getArmorName(uns).get(1), itemArmors[counter]);
			}

			counter++;
		}
		
		sets = Lists.newArrayList("categorywood","stone","bestial","bone","metal","fairieSilver","steels","corundum1","corundum2","obsidian","damascus","diamond","demonite","angelite");		
		for(int i=0;i<sets.size();i++){
			UnsagaMaterial uns = MaterialList.getMaterial(sets.get(i));
			itemAccessories[i] = new ItemAccessory(itemIDsAccessories+i,uns).setUnlocalizedName("unsaga.accessory."+uns.name).setCreativeTab(Unsaga.tabUnsaga);
			//GameData.newItemAdded(itemAccessories[counter]);
			if(armorname.getAcsName(uns)!=null){
				HSLibs.langSet(armorname.getAcsName(uns).get(0), armorname.getAcsName(uns).get(1), itemAccessories[i]);
			}
		}
		itemNoFunc = new ItemIngotsUnsaga(itemIDIngotsUnsaga).setUnlocalizedName("unsaga.nofuncitem").setCreativeTab(Unsaga.tabUnsaga);
		//GameData.newItemAdded(itemNoFunc);
		itemBarrett = new ItemBarrett(itemIDBarrett,"gunpowder").setUnlocalizedName("unsaga.barrett").setCreativeTab(Unsaga.tabUnsaga);
		//GameData.newItemAdded(itemBarrett);
		itemMusket = new ItemGunUnsaga(itemIDGun,"musket").setUnlocalizedName("unsaga.musket").setCreativeTab(Unsaga.tabUnsaga);
		//GameData.newItemAdded(itemMusket);
		HSLibs.langSet("Musket", "銃", itemMusket);
		HSLibs.langSet("Barrett", "弾", itemBarrett);
	}

	protected static boolean checkNoParticle(List<String> nopart,UnsagaMaterial uns){
		boolean flag = false;
		for(int i=0;i<nopart.size();i++){
			if(nopart.get(i).equals(uns.name)){
				flag = true;
			}
			if(uns.isChild){
				if(nopart.get(i).equals(uns.getParentMaterial().name)){
					flag = true;
				}
			}
		}
		return flag;
	}

	protected static String getParticle(List<String> nopart,UnsagaMaterial uns){
		String ret = "";
		if(!checkNoParticle(nopart,uns)){
			ret = "の";
		}
		return ret;

	}

	public static void putItemMap(int itemid,String key){
		itemMap.put(key, new Integer(itemid));
	}

	public static ItemStack getItem(EnumUnsagaWeapon category,UnsagaMaterial material,int stack,int damage){
		String key = category.toString() + "." + material.name;
		Optional<Integer> itemid = Optional.fromNullable(itemMap.get(key));
		if(itemid.isPresent()){
			return new ItemStack(itemid.get(),stack,damage);
		}
		if(material.isChild){
			key = category.toString() + "." + material.parentMaterial.name;
			itemid = Optional.fromNullable(itemMap.get(key));
			if(itemid.isPresent()){
				return new ItemStack(itemid.get(),stack,damage);
			}
			
		}
		Unsaga.debug("Caution:getItem>null");
		return new ItemStack(Item.swordStone.itemID,stack,damage);

	}
	
	public static boolean isValidItemForMaterial(EnumUnsagaWeapon category,UnsagaMaterial material){
		String key = category.toString() + "." + material.name;
		if(itemMap.containsKey(key)){
			return true;
		}
		if(material.isChild){
			UnsagaMaterial parent = material.getParentMaterial();
			key = category.toString() + "." + parent.name;
			if(itemMap.containsKey(key)){
				return true;
			}
		}
		return false;
	}

	public static ItemStack getRandomWeapon(Random rand, int rank,EnumSelecterItem selecter){
		//if(keyExcept==null){
		//hashSet<String> keyExcept = new HashSet();
		boolean flag = true;
		keyExcept = new ArrayList();
		for(Iterator<String> ite=itemMap.keySet().iterator();ite.hasNext();){
			String key = ite.next();
			String keys[] = key.split("\\.");
			flag = true;

			if(selecter==EnumSelecterItem.WEAPONONLY){
				if(EnumUnsagaWeapon.weaponList.contains(keys[0])){
					flag = true;
				}else{
					flag = false;
				}


//				if(keys[0].equals(EnumUnsagaWeapon.BOW.toString())){
//					flag = false;
//				}
//				if(EnumUnsagaWeapon.armorList.contains(keys[0])){
//					flag = false;
//				}
//				if(keys[0].equals(EnumUnsagaWeapon.ACCESSORY.toString())){
//					flag = false;
//				}
			}

			if(selecter==EnumSelecterItem.BOWONLY){
				if(!keys[0].equals(EnumUnsagaWeapon.BOW.toString())){
					flag = false;
				}
			}
			if(selecter==EnumSelecterItem.MERCHANDISE){
				if(EnumUnsagaWeapon.merchandiseList.contains(keys[0])){
					flag = true;
				}else{
					flag = false;
				}
			}
			if(MaterialList.getMaterial(keys[1]).rank>rank){
				flag = false;
			}
			if(flag){
				keyExcept.add(key);
			}

		}
		//}
		if(keyExcept.isEmpty()){
			return new ItemStack(Item.swordStone.itemID,1,0);
		}

		int var1 = rand.nextInt(keyExcept.size());
		String randomKey = keyExcept.get(var1);
		Optional<Integer> itemid = Optional.fromNullable(itemMap.get(randomKey));
		if(itemid.isPresent()){
			ItemStack is = new ItemStack(itemid.get(),1,0);
			String key2[] = randomKey.split("\\.");
			UnsagaMaterial mate = MaterialList.getMaterial(key2[1]);
			if(mate.hasSubMaterials()){
				UnsagaMaterial subm = mate.getRandomSubMaterial(rand);
				HelperUnsagaWeapon.initWeapon(is, subm.name, subm.weight);
				return is;
			}
			HelperUnsagaWeapon.initWeapon(is, mate.name, mate.weight);
			return is;
		}
		return new ItemStack(Item.swordStone.itemID,1,0);
	}
	
	@Deprecated
	public static void registerValidTool(EnumUnsagaWeapon category,UnsagaMaterial material,int itemid){
		if(material.hasSubMaterials()){
			for(Iterator<UnsagaMaterial> ite=material.getSubMaterials().values().iterator();ite.hasNext();){
				UnsagaMaterial childMaterial = ite.next();
				validMaterialMap.put(category, new MaterialPair(childMaterial,itemid));
			}
		}else{
			validMaterialMap.put(category, new MaterialPair(material,itemid));
		}
	}

	static{



	}
}

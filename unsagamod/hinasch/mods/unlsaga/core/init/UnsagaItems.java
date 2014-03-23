package hinasch.mods.unlsaga.core.init;

import hinasch.lib.FileObject;
import hinasch.mods.unlsaga.Unsaga;
import hinasch.mods.unlsaga.item.IUnsagaMaterial;
import hinasch.mods.unlsaga.item.armor.ItemAccessory;
import hinasch.mods.unlsaga.item.armor.ItemArmorUnsaga;
import hinasch.mods.unlsaga.item.etc.ItemBarrett;
import hinasch.mods.unlsaga.item.etc.ItemIngotsUnsaga;
import hinasch.mods.unlsaga.item.tool.ItemPickaxeUnsaga;
import hinasch.mods.unlsaga.item.weapon.ItemAxeUnsaga;
import hinasch.mods.unlsaga.item.weapon.ItemBowUnsaga;
import hinasch.mods.unlsaga.item.weapon.ItemGunUnsaga;
import hinasch.mods.unlsaga.item.weapon.ItemSpearUnsaga;
import hinasch.mods.unlsaga.item.weapon.ItemStaffUnsaga;
import hinasch.mods.unlsaga.item.weapon.ItemSwordUnsaga;
import hinasch.mods.unlsaga.misc.util.EnumUnsagaTools;
import hinasch.mods.unlsaga.misc.util.HelperUnsagaWeapon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import cpw.mods.fml.common.registry.GameRegistry;

public class UnsagaItems {

//
//	public static int itemIDsSwords;
//	public static int itemIDsAxes;
//	public static int itemIDsStaffs;
//	public static int itemIDsSpears;
//	public static int itemIDsBows;
//	public static int itemIDsAccessories;
//	public static int itemIDsArmors;
//
//	public static int itemIDBarrett;
//	public static int itemIDGun;
//	public static int itemIDMagicTablet;
//	public static int itemSpellBookID;
//	public static int itemToolsHSID;
//	public static int itemIDIngotsUnsaga;

	public static Item itemMagicTablet;
	public static Item itemSpellBook;
	public static Item itemToolsHS;
	public static Item itemMusket;
	public static Item itemBlenderSpell;
	public static Item itemKey;
	public static Item itemBarrett;
	public static Item itemMaterials;

	protected static UnsagaToolInitializer swordInitializer;
	protected static UnsagaToolInitializer axeInitializer;
	protected static UnsagaToolInitializer staffInitializer;
	protected static UnsagaToolInitializer bowInitializer;
	protected static UnsagaToolInitializer spearInitializer;
	protected static UnsagaToolInitializer armorInitializer;
	protected static UnsagaToolInitializer bootsInitializer;
	protected static UnsagaToolInitializer legginsInitializer;
	protected static UnsagaToolInitializer helmetInitializer;
	protected static UnsagaToolInitializer acsInitializer;
	protected static UnsagaToolInitializer pickaxeInitializer;
	

	public static HashMap<String,Item> itemMap = new HashMap();
	protected static Item[] itemSwords = new Item[30];
	protected static Item[] itemAxes = new Item[30];
	protected static Item[] itemStaffs = new Item[30];
	protected static Item[] itemSpears = new Item[30];
	protected static Item[] itemKnives = new Item[30];
	protected static Item[] itemAccessories = new Item[50];
	protected static Item[] itemArmors = new Item[80];
	protected static Item[] itemBows = new Item[30];
	protected static Item[] itemPickaxes = new Item[30];

	protected static ArrayList<String> keyExcept;


	public static enum EnumSelecterItem {BOWONLY,WEAPONONLY,MERCHANDISE};
	public static void loadConfig(Configuration config){
//		PropertyCustom prop = new PropertyCustom(new String[]{"itemIDs.Swords","itemIDs.Axes","itemIDs.Staffs"
//				,"itemIDs.Spears","itemIDs.Bows","itemIDs.Accessories","itemIDs.Armors","itemID.Ingots","itemID.Barrett"
//				,"itemID.Gun"});
//
//		prop.setValues(new Integer[]{1200,1230,1260,1290,1320,1370,1450,1600,1601,1602});
//
//		prop.setCategoriesAll(config.CATEGORY_GENERAL);
//
//		
//		prop.buildProps(config);

//		itemIDsSwords = prop.getProp(0).getInt();
//		itemIDsAxes = prop.getProp(1).getInt();
//		itemIDsStaffs = prop.getProp(2).getInt();
//		itemIDsSpears = prop.getProp(3).getInt();
//		itemIDsBows = prop.getProp(4).getInt();
//		itemIDsAccessories = prop.getProp(5).getInt();
//		itemIDsArmors = prop.getProp(6).getInt();
//		itemIDIngotsUnsaga = prop.getProp(7).getInt();
//		itemIDBarrett = prop.getProp(8).getInt();
//		itemIDGun = prop.getProp(9).getInt();

	}

	public static void register(){
		UnsagaMaterials.ItemInitData.init();
		//FileObject file = new FileObject("i:\\test.txt");
		//file.openForOutput();
		swordInitializer = new UnsagaToolInitializer();
//		swordInitializer.setNoParticles(Sets.newHashSet(UnsagaMaterials.stone,UnsagaMaterials.wood));
//		swordInitializer.setNoUseParentNames(Sets.newHashSet(UnsagaMaterials.metals));
//		swordInitializer.setHooter("en_US", "Sword").setHooter("ja_JP", "剣");
		swordInitializer.setAvailableMaterial(UnsagaMaterials.ItemInitData.getAvailableSet(EnumUnsagaTools.SWORD));
		swordInitializer.register(itemSwords, ItemSwordUnsaga.class, "sword");

		
		axeInitializer = new UnsagaToolInitializer();
//		axeInitializer.setHooter("en_US", "Axe").setHooter("ja_JP", "斧");
//		axeInitializer.setNoParticles(Sets.newHashSet(UnsagaMaterials.bone,UnsagaMaterials.stone));
//		axeInitializer.setNoUseParentNames(Sets.newHashSet(UnsagaMaterials.metals));
		axeInitializer.setAvailableMaterial(UnsagaMaterials.ItemInitData.getAvailableSet(EnumUnsagaTools.AXE));
		axeInitializer.register(itemAxes, ItemAxeUnsaga.class, "axe");
		
		spearInitializer = new UnsagaToolInitializer();
		spearInitializer.setNoParticles(Sets.newHashSet(UnsagaMaterials.stone,UnsagaMaterials.categorywood,UnsagaMaterials.bone,UnsagaMaterials.iron));
		spearInitializer.setNoUseParentNames(Sets.newHashSet(UnsagaMaterials.metals));
		spearInitializer.setHooter("en_US", "Spear").setHooter("ja_JP", "槍");
		spearInitializer.setAvailableMaterial(UnsagaMaterials.ItemInitData.getAvailableSet(EnumUnsagaTools.SPEAR));
		spearInitializer.register(itemSpears, ItemSpearUnsaga.class, "spear");

		staffInitializer = new UnsagaToolInitializer();
		staffInitializer.setHooter("en_US", "Staff").setHooter("ja_JP", "杖");
		staffInitializer.setNoParticles(Sets.newHashSet(UnsagaMaterials.stone));
		staffInitializer.setNoUseParentNames(Sets.newHashSet(UnsagaMaterials.metals));
		staffInitializer.setAvailableMaterial(UnsagaMaterials.ItemInitData.getAvailableSet(EnumUnsagaTools.STAFF));
		staffInitializer.register(itemStaffs, ItemStaffUnsaga.class, "staff");
		
		bowInitializer = new UnsagaToolInitializer();
		bowInitializer.setHooter("en_US", "Bow").setHooter("ja_JP", "弓");
		bowInitializer.setNoParticles(Sets.newHashSet(UnsagaMaterials.metals));
		bowInitializer.setAvailableMaterial(UnsagaMaterials.ItemInitData.getAvailableSet(EnumUnsagaTools.BOW));
		bowInitializer.register(itemBows, ItemBowUnsaga.class, "bow");

		bootsInitializer = new UnsagaToolInitializer();
		bootsInitializer.setHooter("en_US", "Boots").setHooter("ja_JP", "靴");
		bootsInitializer.setAvailableMaterial(UnsagaMaterials.ItemInitData.getAvailableSet(EnumUnsagaTools.BOOTS));
		bootsInitializer.setArmorData(EnumUnsagaTools.BOOTS, 3);
		bootsInitializer.register(itemArmors, ItemArmorUnsaga.class, "boots");
		
		legginsInitializer = new UnsagaToolInitializer();
		legginsInitializer.setHooter("en_US", "Leggins").setHooter("ja_JP", "レギンス");
		legginsInitializer.setUseParentLocalized(true);
		legginsInitializer.setAvailableMaterial(UnsagaMaterials.ItemInitData.getAvailableSet(EnumUnsagaTools.LEGGINS));
		legginsInitializer.setArmorData(EnumUnsagaTools.LEGGINS, 2);
		legginsInitializer.register(itemArmors, ItemArmorUnsaga.class, "leggins");

		helmetInitializer = new UnsagaToolInitializer();
		helmetInitializer.setHooter("en_US", "Helmet").setHooter("ja_JP", "兜");
		helmetInitializer.setUseParentLocalized(true);
		helmetInitializer.setAvailableMaterial(UnsagaMaterials.ItemInitData.getAvailableSet(EnumUnsagaTools.HELMET));
		helmetInitializer.setArmorData(EnumUnsagaTools.HELMET, 0);
		helmetInitializer.register(itemArmors, ItemArmorUnsaga.class, "helmet");

		
		armorInitializer = new UnsagaToolInitializer();
		armorInitializer.setHooter("en_US", "Armor").setHooter("ja_JP", "鎧");
		armorInitializer.setUseParentLocalized(true);
		armorInitializer.setAvailableMaterial(UnsagaMaterials.ItemInitData.getAvailableSet(EnumUnsagaTools.ARMOR));
		armorInitializer.setArmorData(EnumUnsagaTools.ARMOR, 1);
		armorInitializer.register(itemArmors, ItemArmorUnsaga.class, "armor");

		
		acsInitializer = new UnsagaToolInitializer();
		acsInitializer.setHooter("en_US", "Ring").setHooter("ja_JP", "腕輪");
		acsInitializer.setAvailableMaterial(UnsagaMaterials.ItemInitData.getAvailableSet(EnumUnsagaTools.ACCESSORY));
		acsInitializer.register(itemAccessories, ItemAccessory.class, "acs");
		
		FileObject fo = new FileObject("i:\\test.txt");
		fo.openForOutput();
		pickaxeInitializer = new UnsagaToolInitializer(fo);
		pickaxeInitializer.setAvailableMaterial(UnsagaMaterials.ItemInitData.getAvailableSet(EnumUnsagaTools.STAFF));
		pickaxeInitializer.register(itemPickaxes, ItemPickaxeUnsaga.class, "pickaxe");
		fo.close();
			
		itemMaterials = new ItemIngotsUnsaga().setUnlocalizedName("unsaga.ingots").setCreativeTab(Unsaga.tabUnsaga);
		GameRegistry.registerItem(itemMaterials, "nofuncitem",Unsaga.modid);
		itemBarrett = new ItemBarrett("gunpowder").setUnlocalizedName("unsaga.barrett").setCreativeTab(Unsaga.tabUnsaga);
		GameRegistry.registerItem(itemBarrett, "barrett",Unsaga.modid);
		itemMusket = new ItemGunUnsaga("musket").setUnlocalizedName("unsaga.musket").setCreativeTab(Unsaga.tabUnsaga);
		GameRegistry.registerItem(itemMusket, "musket",Unsaga.modid);
	}
	



	public static void putItemMap(Item item,String key){
		Unsaga.debug(key+":"+Item.getIdFromItem(item));
		itemMap.put(key, item);
	}

	public static Set<UnsagaMaterial> getUnsuitedMaterial(EnumUnsagaTools category){
		Set<UnsagaMaterial> availableSet = Sets.newHashSet(UnsagaMaterials.ItemInitData.getAvailableSet(category).values());
		Set<UnsagaMaterial> unsuitedSet = new HashSet();
		for(UnsagaMaterial uns:UnsagaMaterials.allMaterialMap.values()){
			if(!uns.setContainsThis(availableSet) && uns.isChild){
				unsuitedSet.add(uns);
			}
		}
		Unsaga.debug(unsuitedSet);
		return unsuitedSet;
		
	}
	
	public static UnsagaMaterial getRandomUnsuitedMaterial(Set<UnsagaMaterial> set,Random rand){
		List<UnsagaMaterial> list = Lists.newArrayList(set);
		int num = rand.nextInt(list.size());
		return list.get(num);
	}
	public static ItemStack getItemStack(EnumUnsagaTools category,UnsagaMaterial material,int stack,int damage){
		String key = category.toString() + "." + material.name;
		Optional<Item> itemid = Optional.fromNullable(itemMap.get(key));
		if(itemid.isPresent()){
			return new ItemStack(itemid.get(),stack,damage);
		}
		if(material.isChild){
			key = category.toString() + "." + material.getParentMaterial().name;
			itemid = Optional.fromNullable(itemMap.get(key));
			if(itemid.isPresent()){
				return new ItemStack(itemid.get(),stack,damage);
			}
			
		}
		Unsaga.debug("Caution:getItem>null");
		return new ItemStack(Items.stone_sword,stack,damage);

	}
	
	public static boolean isValidItemForMaterial(EnumUnsagaTools category,UnsagaMaterial material){
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
	
	public static ItemStack getFailedWeapon(Random rand,EnumUnsagaTools category){
		ItemStack failed = getItemStack(category,UnsagaMaterials.failed,1,0);
		UnsagaMaterial uns = getRandomUnsuitedMaterial(getUnsuitedMaterial(category), rand);

		HelperUnsagaWeapon.initWeapon(failed, uns.name, uns.weight);
		return failed;
		
	}

	public static ItemStack getRandomWeapon(Random rand, int rank,EnumSelecterItem selecter){
		boolean flag = true;
		keyExcept = new ArrayList();
		for(Iterator<String> ite=itemMap.keySet().iterator();ite.hasNext();){
			String key = ite.next();
			String keys[] = key.split("\\.");
			flag = true;

			if(selecter==EnumSelecterItem.WEAPONONLY){
				if(EnumUnsagaTools.weaponList.contains(keys[0])){
					flag = true;
				}else{
					flag = false;
				}
			}

			if(selecter==EnumSelecterItem.BOWONLY){
				if(!keys[0].equals(EnumUnsagaTools.BOW.toString())){
					flag = false;
				}
			}
			if(selecter==EnumSelecterItem.MERCHANDISE){
				if(EnumUnsagaTools.merchandiseList.contains(keys[0])){
					flag = true;
				}else{
					flag = false;
				}
			}
			if(UnsagaMaterials.getMaterial(keys[1]).rank>rank){
				flag = false;
			}
			if(flag){
				keyExcept.add(key);
			}

		}
		//}
		if(keyExcept.isEmpty()){
			return new ItemStack(Items.stone_sword,1,0);
		}

		int var1 = rand.nextInt(keyExcept.size());
		String randomKey = keyExcept.get(var1);
		Optional<Item> itemid = Optional.fromNullable(itemMap.get(randomKey));
		if(itemid.isPresent()){
			ItemStack is = new ItemStack(itemid.get(),1,0);
			String key2[] = randomKey.split("\\.");
			UnsagaMaterial mate = UnsagaMaterials.getMaterial(key2[1]);
			if(mate==UnsagaMaterials.failed){
				IUnsagaMaterial iu = (IUnsagaMaterial)is.getItem();
				EnumUnsagaTools cate = iu.getCategory();
				return getFailedWeapon(rand,cate);
			}
			HelperUnsagaWeapon.initWeapon(is, mate.name, mate.weight);
			return is;
		}
		return new ItemStack(Items.stone_sword,1,0);
	}
	


}

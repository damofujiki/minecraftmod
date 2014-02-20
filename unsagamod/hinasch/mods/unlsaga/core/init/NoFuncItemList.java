package hinasch.mods.unlsaga.core.init;

import hinasch.lib.HSLibs;
import hinasch.mods.unlsaga.Unsaga;
import hinasch.mods.unlsaga.misc.util.NoFuncItem;

import java.util.HashMap;
import java.util.Iterator;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class NoFuncItemList {

	protected static HashMap<Integer,NoFuncItem> itemMap;
	
	public static void load(){
		if(itemMap!=null)return;
		
		itemMap = new HashMap();
		addMap(0,"Steel Ingot","鋼インゴット","ingotSteel","ingot",0xa3a3a2,MaterialList.steel1,250);
		addMap(1,"Steel Ingot*","鋼インゴット*","ingotSteel","ingot",0xa3a3a2,MaterialList.steel2,250);
		addMap(2,"Damascus Ingot","ダマスカス鋼インゴット","ingotDamascus","ingot",0x928178,MaterialList.damascus,2000);
		addMap(3,"Copper Ingot","銅インゴット","ingotCopper","ingot",0xbf794e,MaterialList.copper,90);
		addMap(4,"Silver Ingot","銀インゴット","ingotSilver","ingot",0,MaterialList.silver,80);
		addMap(5,"Lead Ingot","鉛インゴット","ingotLead","ingot",0x0f2350,MaterialList.lead,100);
		addMap(6,"Corundum","鋼玉","gemRuby","ruby",0,MaterialList.corundum1,1200);
		addMap(7,"Corundum*","鋼玉","gemSapphire","sapphire",0,MaterialList.corundum2,1200);
		addMap(8,"Faerie Silver Ingot","精霊銀インゴット","ingotFaerieSilver","ingot",0xaaaaFF,MaterialList.fairieSilver,1000);
		addMap(9,"Meteorite","隕石","stoneMeteorite","stone",0x2e2930,MaterialList.meteorite,200);
		addMap(10,"Meteoritic Iron","隕鉄","ingotMeteoriticIron","ingot",0xafafb0,MaterialList.meteoricIron,300);
		addMap(11,"Carnelian","朱雀石","gemCarnelian","carnelian",0,MaterialList.carnelian,50);
		addMap(12,"Topaz","黄龍石","gemTopaz","topaz",0,MaterialList.topaz,50);
		addMap(13,"Ravenite","玄武石","gemRavenite","ravenite",0,MaterialList.ravenite,50);
		addMap(14,"Lapis Lazuli","蒼龍石","gemLapis","lapis",0,MaterialList.lazuli,50);
		addMap(15,"Opal","白虎石","gemOpal","opal",0,MaterialList.opal,50);
		addMap(16,"Angelite","聖石","gemAngelite","angelite",0,MaterialList.angelite,100);
		addMap(17,"Demonite","魔石","gemDemonite","demonite",0,MaterialList.demonite,100);
		addMap(18,"Debris","廃石","stoneDebris","stone",0,MaterialList.debris,2);
		addMap(19,"Debris*","廃石*","stoneDebris","stone",0,MaterialList.debris2,2);
	}
	
	public static void addMap(int num,String name,String jp,String dict,String icon,int color,UnsagaMaterial material,int damage){
		//int num = itemMap.size();
		Unsaga.debug("put into itemMap:"+num+":"+name);
		if(itemMap.containsKey(num)){
			Unsaga.debug("key"+num+" is already registered.");
		}
		itemMap.put(num, new NoFuncItem(num,name,jp,dict,icon,color,material,num,damage));
	}
	
	public static int length(){
		return itemMap.size();
	}
	
	public static HashMap<Integer,NoFuncItem> getList(){
		return itemMap;
	}
	
	public static NoFuncItem getDataFromMeta(int meta){
		return itemMap.get(meta);
	}
	
	public static ItemStack getItemStack(int stack,int meta){
		return new ItemStack(UnsagaItems.itemNoFunc.itemID,stack,meta);
	}
	
	public static void setLocalizeAndOreDict(){
		Unsaga.debug(itemMap);
		
		for(Iterator<Integer> ite=itemMap.keySet().iterator();ite.hasNext();){
			int key = ite.next();
			NoFuncItem var1 = itemMap.get(key);
			HSLibs.langSet(var1.name, var1.nameJP, getItemStack(1,key));
			if(!var1.dictID.equals("")){
				OreDictionary.registerOre(var1.dictID, getItemStack(1,key));
			}
			
			Unsaga.debug("put localize:"+key+"/"+var1.name+"+"+var1.nameJP+":"+var1.rendercolor);
		}
	}
}

package hinasch.mods.unlsaga.core.init;

import hinasch.mods.unlsaga.Unsaga;
import hinasch.mods.unlsaga.misc.util.NoFunctionItem;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class NoFunctionItems {

	protected static Map<Integer,NoFunctionItem> itemMap;
	
	public static void load(){
		if(itemMap!=null)return;
		
		itemMap = new HashMap();
		addMap(0,"steelIngot","鋼インゴット","ingotSteel","ingot",0xa3a3a2,UnsagaMaterials.steel1,250);
		addMap(1,"steelIngot*","鋼インゴット*","ingotSteel","ingot",0xa3a3a2,UnsagaMaterials.steel2,250);
		addMap(2,"damascusIngot","ダマスカス鋼インゴット","ingotDamascus","ingot",0x928178,UnsagaMaterials.damascus,2000);
		addMap(3,"copperIngot","銅インゴット","ingotCopper","ingot",0xbf794e,UnsagaMaterials.copper,90);
		addMap(4,"silverIngot","銀インゴット","ingotSilver","ingot",0,UnsagaMaterials.silver,80);
		addMap(5,"leadIngot","鉛インゴット","ingotLead","ingot",0x0f2350,UnsagaMaterials.lead,100);
		addMap(6,"corundum","鋼玉","gemRuby","ruby",0,UnsagaMaterials.corundum1,1200);
		addMap(7,"corundum*","鋼玉","gemSapphire","sapphire",0,UnsagaMaterials.corundum2,1200);
		addMap(8,"faerieSilver","精霊銀インゴット","ingotFaerieSilver","ingot",0xaaaaFF,UnsagaMaterials.fairieSilver,1000);
		addMap(9,"meteorite","隕石","stoneMeteorite","stone",0x2e2930,UnsagaMaterials.meteorite,200);
		addMap(10,"meteoricIron","隕鉄","ingotMeteoriticIron","ingot",0xafafb0,UnsagaMaterials.meteoricIron,300);
		addMap(11,"carnelian","朱雀石","gemCarnelian","carnelian",0,UnsagaMaterials.carnelian,50);
		addMap(12,"topaz","黄龍石","gemTopaz","topaz",0,UnsagaMaterials.topaz,50);
		addMap(13,"ravenite","玄武石","gemRavenite","ravenite",0,UnsagaMaterials.ravenite,50);
		addMap(14,"lapisLazuli","蒼龍石","gemLapis","lapis",0,UnsagaMaterials.lazuli,50);
		addMap(15,"opal","白虎石","gemOpal","opal",0,UnsagaMaterials.opal,50);
		addMap(16,"angelite","聖石","gemAngelite","angelite",0,UnsagaMaterials.angelite,100);
		addMap(17,"demonite","魔石","gemDemonite","demonite",0,UnsagaMaterials.demonite,100);
		addMap(18,"debris","廃石","stoneDebris","stone",0,UnsagaMaterials.debris,2);
		addMap(19,"debris*","廃石*","stoneDebris","stone",0,UnsagaMaterials.debris2,2);
	}
	
	public static void addMap(int num,String name,String jp,String dict,String icon,int color,UnsagaMaterial material,int damage){
		//int num = itemMap.size();
		Unsaga.debug("put into itemMap:"+num+":"+name);
		if(itemMap.containsKey(num)){
			Unsaga.debug("key"+num+" is already registered.");
		}
		itemMap.put(num, new NoFunctionItem(num,name,dict,icon,color,material,num,damage));
	}
	
	public static int length(){
		return itemMap.size();
	}
	
	public static Map<Integer,NoFunctionItem> getList(){
		return itemMap;
	}
	
	public static String getName(int par1){
		return getList().get(par1).name;
	}
	
	public static NoFunctionItem getDataFromMeta(int meta){
		return itemMap.get(meta);
	}
	
	public static ItemStack getItemStack(int stack,int meta){
		return new ItemStack(UnsagaItems.itemMaterials,stack,meta);
	}
	
	public static void setLocalizeAndOreDict(){
		Unsaga.debug(itemMap);
		
		for(Iterator<Integer> ite=itemMap.keySet().iterator();ite.hasNext();){
			int key = ite.next();
			NoFunctionItem var1 = itemMap.get(key);
			//HSLibs.langSet(var1.name, var1.nameJP, getItemStack(1,key));
			if(!var1.dictID.equals("")){
				OreDictionary.registerOre(var1.dictID, getItemStack(1,key));
			}
			
			Unsaga.debug("put localize:"+key+"/"+var1.name+"+"+":"+var1.rendercolor);
		}
	}
}

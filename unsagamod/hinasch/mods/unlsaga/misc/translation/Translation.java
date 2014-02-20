package hinasch.mods.unlsaga.misc.translation;

import hinasch.mods.unlsaga.Unsaga;

import java.util.HashMap;

import net.minecraft.client.Minecraft;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class Translation {

	protected static HashMap<String,String> langMap;
	protected static Translation instance;
	protected HashMap<String,String> mapJa;
	protected HashMap<String,String> mapEn;
//	public String strEN;
//	public String strJP;
//	
//	public Translation(String en,String jp){
//		this.strEN = en;
//		this.strJP = jp;
//	}
	public static Translation getInstance(){
		if(instance==null){
			instance = new Translation();
		}
		return instance;
	}
	
	protected Translation(){
		langMap = new HashMap();
		this.mapEn = new HashMap();
		this.mapJa = new HashMap();
		add("msg.gained.ability","gained ability %s.", "%s のアビリティを解放した！");
		add("msg.gained.skill","gained skill %s.", "%s を閃いた！");
		add("element.fire","fire", "火");
		add("element.water","water", "水");
		add("element.wood","wood", "木");
		add("element.metal","metal", "金");
		add("element.earth","earth", "土");
		add("element.forbidden","forbidden", "禁");
		add("word.spell","spell", "術");
		add("word.chest","Chest", "宝箱");
		add("msg.has.noability","you don't have the ability.","アビリティを持ってない。");
		add("msg.chest.needle","Cactus Damage!","ダメージ！");
		add("msg.chest.poison","Poison Trap!","毒のトラップ！");
		add("msg.chest.burst","Chest Exploded!","宝箱が爆発した！");
		add("msg.failed","Failed.","失敗した。");
		add("msg.finished.deciphred","finished deciphring the magic tablet.", "魔道板の解読が完了した。");
		add("msg.write.spell","wrote spell to book.", "術を本に書き込んだ。");
		add("msg.spell.succeeded","Invoking Spell Succeeded.", "詠唱成功");
		add("msg.spell.failed","Invoking Spell Failed.", "詠唱失敗");
		add("msg.heal","%s's life was healed %d.", "%sの体力が%d回復した。");
		add("tablet.notdeciphred","not deciphred", "未解読");
		add("tablet.deciphred","deciphred", "解読済み");
		add("msg.touch.chest","try to open the chest.", "宝箱を開けようとした。");
		add("msg.chest.locked","chest has locked.", "宝箱には鍵がかかっている。");
		add("msg.chest.magiclocked","chest has locked with magical lock.", "宝箱には魔法鍵がかかっている。");
		add("msg.chest.unlocked","unlock succeeded.", "鍵を開けた。");
		add("msg.chest.magiclock.unlocked","unlock magiclock,","魔法鍵を解除した。");
		add("msg.chest.defused","defuse succeeded.", "ワナを外した。");
		add("gui.blender.elements","Fi:%d Me:%d Wo:%d Wa:%d Ea:%d Fo:%d","火:%d 金:%d 木:%d 水:%d 土:%d 禁:%d");
		add("gui.blender.percentage","Fi:%d Me:%d Wo:%d Wa:%d Ea:%d Fo:%d","火:%d 金:%d 木:%d 水:%d 土:%d 禁:%d");
		add("gui.blender.button.blend","Blend","合成");
		add("gui.blender.button.undo","Undo","解除");
		add("gui.chest.button.open","Open","開ける");
		add("gui.chest.button.unlock","Unlock","鍵開け");
		add("gui.chest.button.defuse","Defuse","罠外し");
		add("gui.chest.button.divination","Divination","占い");
		add("gui.bartering.amount","Amount:","合計:");
		add("msg.chest.divination.succeeded","Divination Suceeded.","占い成功");
		add("msg.chest.divination.failed","Divination Failed.","占い失敗");
		add("msg.chest.divination.levelis","this chest is Level %?","LV %d の宝箱？");
		add("msg.chest.divination.catastrophe","Divination Awfully Failed!","占い大失敗..");
	}
	

	
	
	@SideOnly(Side.CLIENT)
	public static boolean isJapanese(){
		String language = Minecraft.getMinecraft().gameSettings.language;
		return language.equals("ja_JP") ? true : false;
	}
	
	public static String getLang(){
		return Minecraft.getMinecraft().gameSettings.language;
	}
	
	public void add(String key,String en,String ja){
		this.mapEn.put(key, en);
		this.mapJa.put(key, ja);
	}
	
	public String getLocalized(String key){
		if(Translation.isJapanese()){
			if(mapJa.containsKey(key)){
				return this.mapJa.get(key);
			}
		}
		if(mapEn.containsKey(mapEn)){
			return this.mapEn.get(key);
		}
		return "";
	}
	

	public static String localize(String key){
		return Unsaga.translation.getLocalized(key);
	}
}

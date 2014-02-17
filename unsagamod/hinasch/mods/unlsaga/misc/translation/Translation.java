package hinasch.mods.unlsaga.misc.translation;

import java.util.HashMap;
import java.util.Iterator;

import net.minecraft.client.Minecraft;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class Translation {

	protected static HashMap<String,String> langMap;
	
//	public String strEN;
//	public String strJP;
//	
//	public Translation(String en,String jp){
//		this.strEN = en;
//		this.strJP = jp;
//	}
	@SideOnly(Side.CLIENT)
	public static String getWord(String strkey){
		if(strkey==null || strkey.equals(""))return "";
		String language;
		language = Minecraft.getMinecraft().gameSettings.language;
		if(language.equals("ja_JP")){
			if(langMap.get(strkey.toLowerCase())!=null){
				return langMap.get(strkey.toLowerCase());
			}else{
				return strkey;
			}
				
		}
		return strkey;
	}
	
	public static String translate(String str){
		String newstr = str;
		for(Iterator<String> ite=langMap.keySet().iterator();ite.hasNext();){
			String eng = ite.next();
			//Unsaga.debug(eng);
			if(newstr.toLowerCase().contains(eng.toLowerCase())){
				newstr = newstr.toLowerCase().replace(eng.toLowerCase(), getWord(eng));
			}
			

		}
		return newstr;
	}
	
	@SideOnly(Side.CLIENT)
	public static boolean isJapanese(){
		String language = Minecraft.getMinecraft().gameSettings.language;
		return language.equals("ja_JP") ? true : false;
	}
	
	public static String getLang(){
		return Minecraft.getMinecraft().gameSettings.language;
	}
	
	public static void load(){
		langMap = new HashMap();
		langMap.put("gained ability:", "のアビリティを解放した！");
		langMap.put("gained skill:", "を閃いた！");
		langMap.put("fire", "火");
		langMap.put("water", "水");
		langMap.put("wood", "木");
		langMap.put("metal", "金");
		langMap.put("earth", "土");
		langMap.put("forbidden", "禁");
		langMap.put("spell", "術");
		langMap.put("finished deciphring the magic tablet.", "魔道板の解読が完了した。");
		langMap.put("wrote to spell book.", "術を本に書き込んだ。");
		langMap.put("succeeded invoke spell.", "詠唱成功");
		langMap.put("healed", "回復");
		langMap.put("deciphred", "解読済み");
		langMap.put("not deciphred", "未解読");
	}
}

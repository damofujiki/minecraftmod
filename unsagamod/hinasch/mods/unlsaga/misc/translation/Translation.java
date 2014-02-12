package hinasch.mods.unlsaga.misc.translation;

import hinasch.mods.unlsaga.Unsaga;

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
	public static String trJP(String strkey){
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
	
	public static String translation(String str){
		String newstr = str;
		for(Iterator<String> ite=langMap.keySet().iterator();ite.hasNext();){
			String eng = ite.next();
			Unsaga.debug(eng);
			if(newstr.toLowerCase().contains(eng)){
				newstr = newstr.replace(eng, trJP(eng));
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
	}
}

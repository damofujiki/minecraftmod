package hinasch.mods.unlsaga.misc.translation;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import net.minecraft.client.Minecraft;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class Translation {

	protected static HashMap<String,String> langMap;
	
	@SideOnly(Side.CLIENT)
	public static String trJP(String strkey){
		if(strkey==null || strkey.equals(""))return "";
		String language;
		language = Minecraft.getMinecraft().gameSettings.language;
		if(language.equals("ja_JP")){
			if(langMap.get(strkey)!=null){
				return langMap.get(strkey.toLowerCase());
			}else{
				return strkey;
			}
				
		}
		return strkey;
	}
	
	@SideOnly(Side.CLIENT)
	public static boolean isJapanese(){
		String language = Minecraft.getMinecraft().gameSettings.language;
		return language.equals("ja_JP") ? true : false;
	}
	
	public static void load(){
		langMap = new HashMap();
		List<String> slist = Arrays.asList("debris","廃石","iron","鉄");
		
		for(Iterator<String> ite=slist.iterator();ite.hasNext();){
			String etext = ite.next();
			String jtext = ite.next();
			langMap.put(etext, jtext);
		}
	}
}

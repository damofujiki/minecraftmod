package hinasch.lib;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class UtilList {

	public static List<Integer> getListExceptList(List<Integer> baselist,List<Integer> list){
		List<Integer> output = baselist;
		for(Integer i:list){
			if(output.contains(i))output.remove(i);
		}
		return output;
	}
	
	public static String mapToCSV(Map map){
		StringBuilder output = new StringBuilder("");
		for(Iterator<String> ite=map.keySet().iterator();ite.hasNext();){
			String key = ite.next().toString();
			String value = map.get(key).toString();
			String mapstr = key+":"+value;
			output.append(mapstr);
			if(ite.hasNext()){
				output.append(",");
			}
			
		}
		return new String(output);
	}
	
	public static Map CSVToMap(String csv){
		HashMap<String,String> output = new HashMap();
		List<String> lists = CSVText.csvToStrList(csv);
		for(String str:lists){
			if(str.contains(":")){
				String[] keymap = str.split(":");
				output.put(keymap[0], keymap[1]);
			}else{
				output.put(str, "");
			}
		}
		return output;
	}
}

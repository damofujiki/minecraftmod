package hinasch.lib;

import java.util.List;

public class UtilList {

	public static List<Integer> getListExceptList(List<Integer> baselist,List<Integer> list){
		List<Integer> output = baselist;
		for(Integer i:list){
			if(output.contains(i))output.remove(i);
		}
		return output;
	}
	
}

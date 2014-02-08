package hinasch.lib;

import java.util.ArrayList;

public class StringCut {
	public static ArrayList strapOffInt(String par1){
		String[] splited = par1.split(",");
		ArrayList<Integer> arraylist = new ArrayList();
		for(int i=0 ;i<splited.length;i++){
			arraylist.add(Integer.parseInt(splited[i]));
		}
		return arraylist;
	}

	public static ArrayList strapOffHexInt(String par1){
		String[] splited = par1.split(",");
		ArrayList<Integer> arraylist = new ArrayList();
		for(int i=0 ;i<splited.length;i++){
			arraylist.add(Integer.decode(splited[i]));
		}
		return arraylist;
	}

	public static ArrayList strapOffStr(String par1){
		String[] splited = par1.split(",");
		ArrayList<String> arraylist = new ArrayList();
		for(int i=0 ;i<splited.length;i++){
			arraylist.add(splited[i]);
		}
		return arraylist;
	}



	public static ArrayList strapOffShort(String par1){
		String[] splited = par1.split(",");
		ArrayList<Short> arraylist = new ArrayList();
		for(int i=0 ;i<splited.length;i++){
			arraylist.add(Short.parseShort(splited[i]));
		}
		return arraylist;
	}

	public static ArrayList strapOffBoolean(String par1){
		String[] splited = par1.split(",");
		ArrayList<Boolean> arraylist = new ArrayList();
		for(int i=0 ;i<splited.length;i++){
			arraylist.add(Boolean.parseBoolean(splited[i]));
		}
		return arraylist;
	}

}

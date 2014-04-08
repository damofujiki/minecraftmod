package com.hinasch.lib;

import java.util.ArrayList;

public class PairIDList {

	public ArrayList<PairID> list;
	
	public PairIDList(){
		this.list = new ArrayList();
	}
	
	public int getNumber(PairID pair){
		for(int i=0;i<list.size();i++){
			if(list.get(i).equals(pair)){
				return i;
			}
		}
		return -1;
	}
	
	public boolean contains(PairID pair){
		return this.getNumber(pair)!=-1 ? true : false;
	}
	
	public PairID getElement(PairID pair){
		int num = this.getNumber(pair);
		return this.list.get(num);
	}
	
	public void addStack(PairID pair,int amount){
		if(this.contains(pair)){
			this.getElement(pair).stack += amount ;
		}else{
			this.list.add(pair.setStack(amount));
		}
		
	}
	//public 
	
}

package hinasch.lib;

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
		this.getElement(pair).stack += amount ;
//		int stack = this.getElement(pair).stack;
//		stack += amount;
//		this.list.set(this.getNumber(pair), )
	}
	//public 
	
}

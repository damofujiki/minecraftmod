package com.hinasch.lib;

public class BWrapper {

	private boolean bool;
	
	
	public BWrapper(){
		this.bool = false;
	}
	
	public void setTrue(){
		this.bool = true;
	}
	
	public void setFalse(){
		this.bool = false;
	}
	
	public boolean get(){
		return this.bool;
	}
}

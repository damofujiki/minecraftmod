package hinasch.mods.unlsaga.core;

import java.util.EnumSet;

import hinasch.mods.unlsaga.misc.translation.Translation;

public class FiveElements {

	public static enum EnumElement {
		FIRE,EARTH,WOOD,METAL,WATER,FORBIDDEN;
	
		
	
		public String getUnlocalized(){
		
			switch(this){
			case FIRE:return "element.fire";
			case EARTH:return "element.earth";
			case WOOD:return "element.wood";
			case METAL:return "element.metal";
			case WATER:return "element.water";
			case FORBIDDEN:return "element.forbidden";
			}
	
			return "";
		}
		
		public String getLocalized(){
			return Translation.localize(this.getUnlocalized());
		}
		
		public int getElementColor(){
			switch(this){
			case FIRE:return 0xff0000;
			case EARTH:return 0x8b4513;
			case WOOD:return 0x6b8e23;
			case METAL:return 0xffff00;
			case WATER:return 0x4169e1;
			case FORBIDDEN:return 0x800080;
			}
			return 0xffffff;
		}
	
	}

	public static EnumSet<EnumElement> enumElements = EnumSet.of(EnumElement.FIRE,EnumElement.EARTH,EnumElement.WOOD,EnumElement.METAL,EnumElement.WATER,EnumElement.FORBIDDEN);

}

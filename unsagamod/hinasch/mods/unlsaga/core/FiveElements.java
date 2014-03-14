package hinasch.mods.unlsaga.core;

import hinasch.lib.StaticWords;
import hinasch.mods.unlsaga.misc.translation.Translation;

import java.util.EnumSet;

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
		
		public int getElementParticle(){
			switch(this){
			case FIRE:return StaticWords.getParticleNumber(StaticWords.particleFlame);
			case EARTH:return 200;
			case WOOD:return 201;
			case METAL:return StaticWords.getParticleNumber(StaticWords.particleReddust);
			case WATER:return 202;
			case FORBIDDEN:return StaticWords.getParticleNumber(StaticWords.particlePortal);
			}
			return -1;
		}
	
	}

	public static EnumSet<EnumElement> enumElements = EnumSet.of(EnumElement.FIRE,EnumElement.EARTH,EnumElement.WOOD,EnumElement.METAL,EnumElement.WATER,EnumElement.FORBIDDEN);

}

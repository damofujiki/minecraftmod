package hinasch.mods.unlsagamagic.misc.spell;

import hinasch.mods.unlsaga.core.FiveElements;
import hinasch.mods.unlsaga.core.FiveElements.EnumElement;
import hinasch.mods.unlsaga.misc.translation.Translation;

import java.util.HashMap;

public class SpellMixTable {

	protected HashMap<FiveElements.EnumElement,Float> elementMap;
	
	public SpellMixTable(){
		this.elementMap = new HashMap();
		for(FiveElements.EnumElement element:FiveElements.enumElements){
			this.elementMap.put(element, 0F);
		}
	}
	
	public SpellMixTable(float fire,float earth,float metal,float water,float wood,float forbidden){
		this.elementMap = new HashMap();
		this.elementMap.put(FiveElements.EnumElement.FIRE, fire);
		this.elementMap.put(FiveElements.EnumElement.EARTH, earth);
		this.elementMap.put(FiveElements.EnumElement.METAL, metal);
		this.elementMap.put(FiveElements.EnumElement.WATER, water);
		this.elementMap.put(FiveElements.EnumElement.WOOD, wood);
		this.elementMap.put(FiveElements.EnumElement.FORBIDDEN, forbidden);
	}
	
	public SpellMixTable(FiveElements.EnumElement element,float par1){
		this.elementMap = new HashMap();
		for(FiveElements.EnumElement ele:FiveElements.enumElements){
			this.elementMap.put(ele, 0F);
		}
		this.elementMap.put(element, par1);
	}
	
	
	public void add(SpellMixTable table){
		this.add(FiveElements.EnumElement.FIRE, table.get(FiveElements.EnumElement.FIRE));
		this.add(FiveElements.EnumElement.WOOD, table.get(FiveElements.EnumElement.WOOD));
		this.add(FiveElements.EnumElement.EARTH, table.get(FiveElements.EnumElement.EARTH));
		this.add(FiveElements.EnumElement.METAL, table.get(FiveElements.EnumElement.METAL));
		this.add(FiveElements.EnumElement.WATER, table.get(FiveElements.EnumElement.WATER));
		this.add(FiveElements.EnumElement.FORBIDDEN, table.get(FiveElements.EnumElement.FORBIDDEN));

	}
	

	public boolean isBiggerThan(SpellMixTable table){
		int flag = 0;
		if(this.get(FiveElements.EnumElement.FIRE)>=table.get(FiveElements.EnumElement.FIRE))flag+=1;
		if(this.get(FiveElements.EnumElement.WOOD)>=table.get(FiveElements.EnumElement.WOOD))flag+=1;
		if(this.get(FiveElements.EnumElement.EARTH)>=table.get(FiveElements.EnumElement.EARTH))flag+=1;
		if(this.get(FiveElements.EnumElement.METAL)>=table.get(FiveElements.EnumElement.METAL))flag+=1;
		if(this.get(FiveElements.EnumElement.WATER)>=table.get(FiveElements.EnumElement.WATER))flag+=1;
		if(this.get(FiveElements.EnumElement.FORBIDDEN)>=table.get(FiveElements.EnumElement.FORBIDDEN))flag+=1;
		//Unsaga.debug(flag);
		if(flag>=6){
			return true;
		}
		return false;
	}
	

	
	public float get(FiveElements.EnumElement element){
		return this.elementMap.get(element);
	}
	
	public int getInt(FiveElements.EnumElement element){
		return Math.round(this.elementMap.get(element));
	}

	public void add(FiveElements.EnumElement element,float par1){
		float var1 = this.get(element);
		this.elementMap.put(element, var1+par1);
	}
	
	public void put(FiveElements.EnumElement element,float par1){
		this.elementMap.put(element, par1);
	}
	
	public void cut(int min,int max){
		for(FiveElements.EnumElement element:FiveElements.enumElements){
			if((int)this.get(element)<min){
				this.put(element,(float) min);
			}
			if((int)this.get(element)>max){
				this.put(element,(float) max);
			}
		}
	}
	
	public void reset(){
		for(FiveElements.EnumElement element:FiveElements.enumElements){
			this.put(element, 0F);
		}
	}
	
	
	@Override
	public String toString(){
		String str = Translation.localize("gui.blender.elements");
		String formatted = String.format(str, this.getInt(FiveElements.EnumElement.FIRE),this.getInt(FiveElements.EnumElement.METAL),this.getInt(FiveElements.EnumElement.WOOD)
				,this.getInt(FiveElements.EnumElement.WATER),this.getInt(FiveElements.EnumElement.EARTH),this.getInt(FiveElements.EnumElement.FORBIDDEN));
//		StringBuilder builder = new StringBuilder();
//		for(EnumElement element:UnsagaElement.enumElements){
//			builder.append(element.toString()+":"+this.get(element)+" ");
//		}
		return formatted;
	}
	

	public String getPercentage(){
		String str = Translation.localize("gui.blender.elements");
		String formatted = String.format(str, this.getInt(FiveElements.EnumElement.FIRE),this.getInt(FiveElements.EnumElement.METAL),this.getInt(FiveElements.EnumElement.WOOD)
				,this.getInt(FiveElements.EnumElement.WATER),this.getInt(FiveElements.EnumElement.EARTH),this.getInt(FiveElements.EnumElement.FORBIDDEN));
//		StringBuilder builder = new StringBuilder();
//		for(EnumElement element:UnsagaElement.enumElements){
//			builder.append(element.toString()+":"+this.get(element)+" ");
//		}
		return formatted;
	}
}

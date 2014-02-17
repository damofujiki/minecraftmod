package hinasch.mods.unlsagamagic.misc.spell;

import hinasch.mods.unlsagamagic.misc.element.UnsagaElement;
import hinasch.mods.unlsagamagic.misc.element.UnsagaElement.EnumElement;

import java.util.HashMap;

public class SpellMixTable {

	protected HashMap<EnumElement,Float> elementMap;
	
	public SpellMixTable(){
		this.elementMap = new HashMap();
		for(EnumElement element:UnsagaElement.enumElements){
			this.elementMap.put(element, 0F);
		}
	}
	
	public SpellMixTable(float fire,float earth,float metal,float water,float wood,float forbidden){
		this.elementMap = new HashMap();
		this.elementMap.put(EnumElement.FIRE, fire);
		this.elementMap.put(EnumElement.EARTH, earth);
		this.elementMap.put(EnumElement.METAL, metal);
		this.elementMap.put(EnumElement.WATER, water);
		this.elementMap.put(EnumElement.WOOD, wood);
		this.elementMap.put(EnumElement.FORBIDDEN, forbidden);
	}
	
	public SpellMixTable(EnumElement element,float par1){
		this.elementMap = new HashMap();
		for(EnumElement ele:UnsagaElement.enumElements){
			this.elementMap.put(ele, 0F);
		}
		this.elementMap.put(element, par1);
	}
	
	
	public void add(SpellMixTable table){
		this.add(EnumElement.FIRE, table.get(EnumElement.FIRE));
		this.add(EnumElement.WOOD, table.get(EnumElement.WOOD));
		this.add(EnumElement.EARTH, table.get(EnumElement.EARTH));
		this.add(EnumElement.METAL, table.get(EnumElement.METAL));
		this.add(EnumElement.WATER, table.get(EnumElement.WATER));
		this.add(EnumElement.FORBIDDEN, table.get(EnumElement.FORBIDDEN));

	}
	

	public boolean isBiggerThan(SpellMixTable table){
		int flag = 0;
		if(this.get(EnumElement.FIRE)>=table.get(EnumElement.FIRE))flag+=1;
		if(this.get(EnumElement.WOOD)>=table.get(EnumElement.WOOD))flag+=1;
		if(this.get(EnumElement.EARTH)>=table.get(EnumElement.EARTH))flag+=1;
		if(this.get(EnumElement.METAL)>=table.get(EnumElement.METAL))flag+=1;
		if(this.get(EnumElement.WATER)>=table.get(EnumElement.WATER))flag+=1;
		if(this.get(EnumElement.FORBIDDEN)>=table.get(EnumElement.FORBIDDEN))flag+=1;
		if(flag>=6){
			return true;
		}
		return false;
	}
	

	
	public float get(EnumElement element){
		return this.elementMap.get(element);
	}
	
	public int getInt(EnumElement element){
		return Math.round(this.elementMap.get(element));
	}

	public void add(EnumElement element,float par1){
		float var1 = this.get(element);
		this.elementMap.put(element, var1+par1);
	}
	
	public void put(EnumElement element,float par1){
		this.elementMap.put(element, par1);
	}
	
	public void cut(int min,int max){
		for(EnumElement element:UnsagaElement.enumElements){
			if((int)this.get(element)<min){
				this.put(element,(float) min);
			}
			if((int)this.get(element)>max){
				this.put(element,(float) max);
			}
		}
	}
	
	public void reset(){
		for(EnumElement element:UnsagaElement.enumElements){
			this.put(element, 0F);
		}
	}
	
	
	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		for(EnumElement element:UnsagaElement.enumElements){
			builder.append(element.toString()+":"+this.get(element)+" ");
		}
		return new String(builder);
	}
	

	public String getPercentage(){
		StringBuilder builder = new StringBuilder();
		for(EnumElement element:UnsagaElement.enumElements){
			builder.append(element.toString()+":"+this.get(element)+"% ");
		}
		return new String(builder);
	}
}

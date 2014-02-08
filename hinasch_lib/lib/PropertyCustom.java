package hinasch.lib;

import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;

import com.google.common.base.Preconditions;

//2013.11.13
public class PropertyCustom {

	public Property[] props;
	public String[] propNames;
	public String[] propCategories;
	public Object[] propValues;
	
	public PropertyCustom(String[] par1){
		this.propNames = par1;
		this.props = new Property[this.propNames.length];
	}
	
	public void setCategories(String[] par1){
		this.propCategories = par1;
		
	}
	
	public void setValues(Object[] par1){
		this.propValues = par1;
	}
	public void buildProps(Configuration config){
		for(int i=0;i<props.length;i++){
			Preconditions.checkNotNull(this.propValues[i], "propValue is null.ぬるぽ");
			if(this.propValues[i] instanceof Boolean){
				this.props[i] = config.get(this.propCategories[i], this.propNames[i], (Boolean)this.propValues[i]); 
			}
			if(this.propValues[i] instanceof Integer){
				this.props[i] = config.get(this.propCategories[i], this.propNames[i], (Integer)this.propValues[i]); 
			}
			if(this.propValues[i] instanceof String){
				this.props[i] = config.get(this.propCategories[i], this.propNames[i], (String)this.propValues[i]); 
			}
		}
	}
	
	public Property getProp(int i){
		Preconditions.checkNotNull(this.props[i],"build Props before getProp.");
		return this.props[i];
	}
	
	@Deprecated
	public static void makeProps(Configuration config,Property[] props,String[] names,String[] categories,Object[] values){
		for(int i=0;i<props.length;i++){
			if(values[i] instanceof Boolean){
				props[i] = config.get(categories[i], names[i],(Boolean)values[i] );
			}
			if(values[i] instanceof Integer){
				props[i] = config.get(categories[i], names[i],(Integer)values[i] );
			}
			if(values[i] instanceof String){
				props[i] = config.get(categories[i], names[i],(String)values[i] );
			}
		}
		
	}
}

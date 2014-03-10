package hinasch.mods.unlsaga.entity.ai;

import net.minecraft.entity.EntityLivingBase;

public abstract class AbstractAI {

	public String name;
	
	public String getName(){
		return this.name;
	}
	
	abstract int getWeight();
	abstract void task(EntityLivingBase target);
	abstract double getMaxDistance();
	abstract double getMinDistance();
}

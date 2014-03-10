package hinasch.mods.unlsaga.entity.ai;

import hinasch.mods.unlsaga.Unsaga;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

public class AIThrowProjectile extends AbstractAI{

	//protected String name = "rangedAttackAI";
	protected World world;
	protected IThrowableAttack owner;
	protected int weight;
	protected double MaxDistance;
	protected double MinDistance;
	
	public AIThrowProjectile(String name,World world,IThrowableAttack owner,int weight){
		this.name = name;
		this.world = world;
		this.owner = owner;
		this.weight = weight;
		this.MaxDistance = 18.0D;
		this.MaxDistance = 0.2D;
	}
	
	public AIThrowProjectile setMinMaxDistance(double min,double max){
		this.MaxDistance = max;
		this.MinDistance = min;
		return this;
	}
	@Override
	int getWeight() {
		// TODO 自動生成されたメソッド・スタブ
		return this.weight;
	}

	@Override
	void task(EntityLivingBase target) {
		if(target!=null){
			Entity throwable = owner.getNewThrowableInstance(this,target,0.2F);
	        if(!this.world.isRemote && throwable!=null){
	        	this.world.spawnEntityInWorld(throwable);
	        }
		}
		Unsaga.debug("doTask:"+this.name);
		


		
	}

	@Override
	double getMaxDistance() {
		// TODO 自動生成されたメソッド・スタブ
		return 18.0D;
	}

	@Override
	double getMinDistance() {
		// TODO 自動生成されたメソッド・スタブ
		return 0.2D;
	}


}

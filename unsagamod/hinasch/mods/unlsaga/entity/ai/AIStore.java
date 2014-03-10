package hinasch.mods.unlsaga.entity.ai;

import hinasch.mods.unlsaga.Unsaga;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.WeightedRandom;

//簡単なオレオレAI
public class AIStore {

	public int tick = 0;
	public int doAITick = 100;
	//最大索敵範囲（直接の範囲はAIのほうで決める）
	public double reconnoiteringDistance = 300.0D;
	public List<AbstractAI> availableAIList;
	public Random rand;
	public Entity owner;
	protected ArrayList<WeightedRandomTask> preparedAI;
	
	public AIStore(Entity parent,Random rand)
	{
		this.owner = parent;
		this.rand = rand;
		this.availableAIList = new ArrayList();
		
	}
	
	public void addAI(AbstractAI ai){
		this.availableAIList.add(ai);
	}
	protected EntityLivingBase prepareTasks(){
		EntityPlayer target = this.owner.worldObj.getClosestPlayerToEntity(this.owner, reconnoiteringDistance);
		Unsaga.debug("ターゲット発見:"+target.getCommandSenderName());
		if(target!=null){
			
			double distanceToTarget = this.owner.getDistanceToEntity(target);
			Unsaga.debug("距離は"+distanceToTarget);
			preparedAI = new ArrayList();
			for(int i=0;i<availableAIList.size();i++){
				AbstractAI var1 = this.availableAIList.get(i);
				Unsaga.debug("ストアしてあるAIは"+var1.getName());
				if(var1.getMinDistance()<=distanceToTarget && var1.getMaxDistance()>=distanceToTarget){
					Unsaga.debug(var1.getName()+"が実行できます");
					preparedAI.add(new WeightedRandomTask(var1.getWeight(),var1));
				}
				
			}
			
		}

		
		
		return target;
	}
	
	protected void doRandomTask(){
		
		EntityPlayer target = (EntityPlayer) this.prepareTasks();
		if(!this.preparedAI.isEmpty()){
			WeightedRandomTask randomTask = (WeightedRandomTask) WeightedRandom.getRandomItem(rand, preparedAI);
			Unsaga.debug(randomTask.ai.getName());
			randomTask.ai.task(target);
		}
		
		
	}
	
	public void updateAITick(){
		this.tick += 1;
		if(this.tick>this.doAITick){
			Unsaga.debug("タスク実行間隔を超えた");
			this.tick = 0;
			this.doRandomTask();
		}
	}
	
	protected class WeightedRandomTask extends WeightedRandom.Item{

		protected AbstractAI ai;
		
		public WeightedRandomTask(int weight,AbstractAI ai) {
			super(weight);
			this.ai = ai;
			// TODO 自動生成されたコンストラクター・スタブ
		}
		
		public AbstractAI getTask(){
			return this.ai;
		}
	}
}

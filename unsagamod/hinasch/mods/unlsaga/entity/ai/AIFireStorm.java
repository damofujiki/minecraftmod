package hinasch.mods.unlsaga.entity.ai;

import hinasch.lib.XYZPos;
import hinasch.mods.unlsaga.misc.debuff.Debuffs;
import hinasch.mods.unlsaga.misc.debuff.livingdebuff.LivingDebuff;
import hinasch.mods.unlsaga.misc.debuff.livingdebuff.LivingStateCrimsonFlare;
import net.minecraft.entity.EntityLivingBase;

public class AIFireStorm extends AbstractAI{

	protected EntityLivingBase owner;
	
	public AIFireStorm(String name,EntityLivingBase owner){
		this.name = name;
		this.owner = owner;
	}
	@Override
	int getWeight() {
		// TODO 自動生成されたメソッド・スタブ
		return 2;
	}

	@Override
	void task(EntityLivingBase target) {
		// TODO 自動生成されたメソッド・スタブ
		if(target!=null){
			XYZPos xyz = XYZPos.entityPosToXYZ(target);
			LivingDebuff.addLivingDebuff(owner, new LivingStateCrimsonFlare(Debuffs.crimsonFlare,100,xyz.x,xyz.y,xyz.z,1,-1));
		}

	}

	@Override
	double getMaxDistance() {
		// TODO 自動生成されたメソッド・スタブ
		return 8.0D;
	}

	@Override
	double getMinDistance() {
		// TODO 自動生成されたメソッド・スタブ
		return 2.0D;
	}

}

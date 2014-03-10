package hinasch.mods.unlsaga.misc.util;

import net.minecraft.util.WeightedRandom;


public class WeightedRandomNumber extends WeightedRandom.Item{

	public final int number;
	
	public WeightedRandomNumber(int weight,int num) {
		super(weight);
		this.number = num;
		
	}

}

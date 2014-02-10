package hinasch.mods.unlsaga.misc.util;

import net.minecraft.util.WeightedRandomItem;

public class WeightedRandomNumber extends WeightedRandomItem{

	public int number;
	
	public WeightedRandomNumber(int weight,int num) {
		super(weight);
		this.number = num;
		// TODO 自動生成されたコンストラクター・スタブ
	}

}

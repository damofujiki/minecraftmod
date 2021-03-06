package hinasch.mods.unlsaga.misc.debuff.livingdebuff;

import hinasch.mods.unlsaga.Unsaga;
import hinasch.mods.unlsaga.misc.debuff.Debuff;

public class LivingBuff extends LivingDebuff{

	public int amp;
	
	public LivingBuff(Debuff par1, int par2,int amp) {
		super(par1, par2);
		this.amp = amp;
		// TODO 自動生成されたコンストラクター・スタブ
	}

	@Override
	public String toString(){
		return this.debuff.number+":"+this.remain + ":" + this.amp;
	}
	
	@Override
	public boolean isExpired(){
		
		if(this.remain<=0){
			Unsaga.debug(this.debuff.name+" is expired.");
			return true;
		}
		
		return false;
	}

}

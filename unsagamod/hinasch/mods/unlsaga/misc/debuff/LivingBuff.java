package hinasch.mods.unlsaga.misc.debuff;

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
}

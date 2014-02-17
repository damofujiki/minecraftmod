package hinasch.mods.unlsaga.misc.debuff;

import net.minecraft.entity.EntityLivingBase;

public class LivingState extends LivingDebuff{

	public boolean isOnlyAir = false;
	
	public LivingState(Debuff par1, int par2,boolean par3) {
		super(par1, par2);
		this.isOnlyAir = par3;
		// TODO 自動生成されたコンストラクター・スタブ
	}

	public String toString(){
		return this.debuff.number+":"+this.remain+":"+(this.isOnlyAir ? "0" : "-1");
	}
	
	@Override
	public void updateRemain(EntityLivingBase living){
		this.remain -= 1;
		if(this.remain<=0){
			this.remain = 0;
		}
		if(this.isOnlyAir && living.onGround){
			this.remain = 0;
		}
	}
}

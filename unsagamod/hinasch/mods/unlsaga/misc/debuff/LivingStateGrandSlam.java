package hinasch.mods.unlsaga.misc.debuff;

import hinasch.lib.XYZPos;
import net.minecraft.entity.EntityLivingBase;

public class LivingStateGrandSlam extends LivingState{

	
	public XYZPos brokenPos;
	public String blocksPSV;
	
	public LivingStateGrandSlam(Debuff par1, int par2, boolean par3,XYZPos par4,String par5) {
		super(par1, par2, par3);
		// TODO 自動生成されたコンストラクター・スタブ
		this.brokenPos = par4;
		this.blocksPSV = par5;
	}

	public String toString(){
		return this.debuff.number+":"+this.remain+":"+ this.brokenPos.x +":" +this.brokenPos.y +":" +this.brokenPos.z +":" +this.blocksPSV;
	}
	
	@Override
	public void updateRemain(EntityLivingBase living){
		super.updateRemain(living);
		if(this.remain<=2){
			if(!living.worldObj.isRemote){
				
			}
		}
	}
}

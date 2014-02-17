package hinasch.mods.unlsaga.misc.debuff;

import hinasch.mods.unlsaga.Unsaga;
import hinasch.mods.unlsaga.item.weapon.ItemBowUnsaga;
import hinasch.mods.unlsaga.misc.ability.AbilityRegistry;
import hinasch.mods.unlsaga.misc.ability.skill.effect.SkillEffectHelper;
import hinasch.mods.unlsaga.misc.util.UtilItem;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

public class LivingStateBow extends LivingState{

	
	public int shootTick;
	public String tag;
	public float charge;
	
	public LivingStateBow(Debuff par1, int par2, boolean par3,int par4,String tag,float par5) {
		super(par1, par2, false);
		this.shootTick = par4;
		this.tag = tag;
		this.charge = par5;
		// TODO 自動生成されたコンストラクター・スタブ
	}

	@Override
	public String toString(){
		return this.debuff.number+":"+this.remain+":" + this.shootTick + ":" + this.tag + ":" + this.charge;
	}
	
	public void updateTick(EntityLivingBase living) {
		Unsaga.debug("呼ばれてますtick");
		if(this.shootTick<0){
			this.remain = 0;
		}
		if(living instanceof EntityPlayer){
			EntityPlayer ep = (EntityPlayer)living;
			if(UtilItem.hasItemInstance(ep, ItemBowUnsaga.class)){
				Unsaga.debug("到達してます");
				SkillEffectHelper helper = new SkillEffectHelper(ep.worldObj,ep,AbilityRegistry.doubleShot,ep.getHeldItem());
				helper.setCharge(charge);
				helper.setParent(this);
				helper.doSkill();
			}
		}
		
		
	}
	
	
}

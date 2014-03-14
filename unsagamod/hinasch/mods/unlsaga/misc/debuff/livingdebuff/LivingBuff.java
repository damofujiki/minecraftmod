package hinasch.mods.unlsaga.misc.debuff.livingdebuff;

import hinasch.mods.unlsaga.Unsaga;
import hinasch.mods.unlsaga.misc.debuff.Debuff;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;

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
	
	@Override
	public void onInitEvent(EntityLivingBase living){
		super.onInitEvent(living);
		if(this.debuff.getAttributeModifier()!=null && living.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).getModifier(this.debuff.getAttributeModifier().getID())==null){
			living.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).applyModifier(this.debuff.getAttributeModifier());
			Unsaga.debug(this.debuff.getAttributeModifier().getName()+"アプライしました："+living.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).getAttributeValue());
		}
	}
	
	@Override
	public void onExpiredEvent(EntityLivingBase living){
		super.onExpiredEvent(living);
		if(this.debuff.getAttributeModifier()!=null){
			living.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).removeModifier(this.debuff.getAttributeModifier());
			Unsaga.debug("おわりしました："+living.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).getAttributeValue());

		}
	}
}

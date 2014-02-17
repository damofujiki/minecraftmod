package hinasch.mods.unlsaga.misc.debuff;

import hinasch.mods.unlsaga.Unsaga;
import hinasch.mods.unlsaga.core.event.ExtendedEntityLivingData;
import hinasch.mods.unlsaga.misc.ability.AbilityRegistry;
import hinasch.mods.unlsaga.misc.ability.HelperAbility;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

import com.google.common.base.Optional;

public class LivingDebuff {

	protected Debuff debuff;
	protected int remain;
	
	//これを継承してステートにもできる。initとtostringを上書きする。
	
	public LivingDebuff(Debuff par1,int par2){
		this.debuff = par1;
		this.remain = par2;
	}
	
	public Debuff getDebuff(){
		return this.debuff;
	}
	
	public int getRemaining(){
		return this.remain;
	}
	
	//Tickごとに
	public void updateTick(EntityLivingBase living) {
		
		
	}
	
	//１秒ごとに
	public void updateRemain(EntityLivingBase living){
		this.remain -= 1;
		if(this.remain<=0){
			this.remain = 0;
		}
		
		if(living instanceof EntityPlayer){
			EntityPlayer ep = (EntityPlayer)living;
			if(this.debuff==DebuffRegistry.downSkill && HelperAbility.hasAbilityPlayer(ep, AbilityRegistry.skillGuard)>0){
				this.remain = 0;
			}
		}

		
		
		
		Unsaga.debug(this.toString());
	}
	
	public boolean isExpired(){
		
		if(this.remain<=0){
			Unsaga.debug(this.debuff.nameJp+" is expired.");
			return true;
		}
		
		return false;
	}
	
	public String toString(){
		return this.debuff.number+":"+this.remain;
	}
	
	
	public static LivingDebuff buildFromString(String data){
		String[] strs = data.split(":");
		Debuff debuff = DebuffRegistry.getDebuff(Integer.valueOf(strs[0]));
		LivingDebuff output = debuff.init(strs);
		return output;
		
	}
	
	
	public static void addDebuff(EntityLivingBase living,Debuff debuff,int remain){
		ExtendedEntityLivingData.addDebuff(living, debuff, remain);
	}
	public static void addLivingDebuff(EntityLivingBase living,LivingDebuff livdebuff){
		ExtendedEntityLivingData.addLivingDebuff(living, livdebuff);
	}
	
	public static void removeDebuff(EntityLivingBase living,Debuff debuff){
		ExtendedEntityLivingData.removeDebuff(living, debuff);
	}
	
	public static boolean hasDebuff(EntityLivingBase living,Debuff debuff){
		return ExtendedEntityLivingData.hasDebuff(living, debuff);
	}
	
	public static Optional<LivingDebuff> getLivingDebuff(EntityLivingBase living,Debuff debuff){
		return ExtendedEntityLivingData.getDebuff(living, debuff);
	}
	
	public static int getModifierAttackBuff(EntityLivingBase living){
		int amount = 0;
		if(LivingDebuff.getLivingDebuff(living, DebuffRegistry.powerup).isPresent()){
			LivingBuff buff = (LivingBuff)LivingDebuff.getLivingDebuff(living, DebuffRegistry.powerup).get();
			amount += buff.amp;
		}
		return amount;
	}
	
	public static boolean isCooling(EntityLivingBase living){
		return hasDebuff(living,DebuffRegistry.cooling);
	}


}

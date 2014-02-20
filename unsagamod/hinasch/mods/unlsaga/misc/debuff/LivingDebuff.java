package hinasch.mods.unlsaga.misc.debuff;

import hinasch.mods.unlsaga.Unsaga;
import hinasch.mods.unlsaga.core.event.ExtendedEntityLivingData;
import hinasch.mods.unlsaga.misc.ability.AbilityRegistry;
import hinasch.mods.unlsaga.misc.ability.HelperAbility;
import hinasch.mods.unlsaga.network.PacketHandler;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

import com.google.common.base.Optional;

import cpw.mods.fml.common.network.PacketDispatcher;

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
			if(this.debuff==DebuffRegistry.sleep && HelperAbility.hasAbilityPlayer(ep, AbilityRegistry.antiSleep)>0){
				this.remain = 0;
			}
			if(this.debuff.getClass() == Debuff.class && HelperAbility.hasAbilityPlayer(ep, AbilityRegistry.antiDebuff)>0){
				this.remain = 0;
			}
			if(this.debuff==DebuffRegistry.downPhysical && HelperAbility.hasAbilityPlayer(ep, AbilityRegistry.bodyGuard)>0){
				this.remain = 0;
			}
			if(this.debuff==DebuffRegistry.downMagic && HelperAbility.hasAbilityPlayer(ep, AbilityRegistry.magicGuard)>0){
				this.remain = 0;
			}
		}
		
		if(this.debuff.getClass()== Debuff.class || this.debuff.getClass()==Buff.class){
			if(living.getRNG().nextInt(4)<=1){
				PacketDispatcher.sendPacketToAllPlayers(PacketHandler.getParticlePacket(4, living.entityId,2));
//                double d0 = (double)(7 >> 16 & 255) / 255.0D;
//                double d1 = (double)(7 >> 8 & 255) / 255.0D;
//                double d2 = (double)(7 >> 0 & 255) / 255.0D;
//				 living.worldObj.spawnParticle("mobSpell", living.posX + (living.getRNG().nextDouble() - 0.5D) * (double)living.width, living.posY + living.getRNG().nextDouble() * (double)living.height - (double)living.yOffset, living.posZ + (living.getRNG().nextDouble() - 0.5D) * (double)living.width, 0, 0, 0);

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

package hinasch.mods.unlsaga.misc.debuff.livingdebuff;

import hinasch.mods.unlsaga.Unsaga;
import hinasch.mods.unlsaga.core.event.ExtendedEntityLivingData;
import hinasch.mods.unlsaga.misc.ability.Ability;
import hinasch.mods.unlsaga.misc.ability.AbilityRegistry;
import hinasch.mods.unlsaga.misc.ability.HelperAbility;
import hinasch.mods.unlsaga.misc.debuff.Debuff;
import hinasch.mods.unlsaga.misc.debuff.Debuffs;
import hinasch.mods.unlsaga.network.packet.PacketParticle;
import hinasch.mods.unlsaga.network.packet.PacketSyncDebuff;
import hinasch.mods.unlsaga.network.packet.PacketUtil;

import java.util.Iterator;
import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;



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
	
	//打ち消すアビリティを持ってれば消える
	public void checkAbilitiesAgainst(EntityLivingBase living){
		for(Ability ab:this.debuff.getAbilityAgainst()){
			if(HelperAbility.hasAbilityLiving(living, ab)>0){
				this.remain = 0;
			}
		}
	}
	
	//Tickごとに
	public void updateTick(EntityLivingBase living) {
		if(!this.debuff.getAbilityAgainst().isEmpty()){
			this.checkAbilitiesAgainst(living);
		}
		
		
	}
	
	//１秒ごとに
	public void updateRemain(EntityLivingBase living){
		this.remain -= 1;
		if(this.remain<=0){
			this.remain = 0;
		}
		
		if(living instanceof EntityPlayer){
			EntityPlayer ep = (EntityPlayer)living;
			if(this.debuff==Debuffs.downSkill && HelperAbility.hasAbilityLiving(ep, AbilityRegistry.skillGuard)>0){
				this.remain = 0;
			}
			if(this.debuff==Debuffs.sleep && HelperAbility.hasAbilityLiving(ep, AbilityRegistry.antiSleep)>0){
				this.remain = 0;
			}
			if(this.debuff.getClass() == Debuff.class && HelperAbility.hasAbilityLiving(ep, AbilityRegistry.antiDebuff)>0){
				this.remain = 0;
			}
			if(this.debuff==Debuffs.downPhysical && HelperAbility.hasAbilityLiving(ep, AbilityRegistry.bodyGuard)>0){
				this.remain = 0;
			}
			if(this.debuff==Debuffs.downMagic && HelperAbility.hasAbilityLiving(ep, AbilityRegistry.magicGuard)>0){
				this.remain = 0;
			}
			
		}

		if(this.debuff.getParticleNumber()!=-1){
			if(living.getRNG().nextInt(4)<=1){
				PacketParticle pp = new PacketParticle(debuff.getParticleNumber(),living.getEntityId(),3);
				Unsaga.packetPipeline.sendToAllAround(pp, PacketUtil.getTargetPointNear(living));
			}
		}
//		if(this.debuff==DebuffRegistry.earthVeil){
//			if(living.getRNG().nextInt(4)<=1){
//				PacketParticle pp = new PacketParticle(200,living.getEntityId(),3);
//				Unsaga.packetPipeline.sendToAllAround(pp, PacketUtil.getTargetPointNear(living));
//			}
//		}
//		if(this.debuff==DebuffRegistry.woodVeil){
//			if(living.getRNG().nextInt(4)<=1){
//				PacketParticle pp = new PacketParticle(201,living.getEntityId(),3);
//				Unsaga.packetPipeline.sendToAllAround(pp, PacketUtil.getTargetPointNear(living));
//			}
//		}
//		if(this.debuff==DebuffRegistry.fireVeil){
//			if(living.getRNG().nextInt(4)<=1){
//				PacketParticle pp = new PacketParticle(StaticWords.getParticleNumber(StaticWords.particleFlame),living.getEntityId(),3);
//				Unsaga.packetPipeline.sendToAllAround(pp, PacketUtil.getTargetPointNear(living));
//			}
//		}
//		if(this.debuff.getClass()== Debuff.class || this.debuff.getClass()==Buff.class){
//			if(living.getRNG().nextInt(4)<=1){
//				PacketParticle pp = new PacketParticle(4,living.getEntityId(),3);
//				Unsaga.packetPipeline.sendToAllAround(pp, PacketUtil.getTargetPointNear(living));
//				//PacketDispatcher.sendPacketToAllPlayers(pp.getPacket());
////                double d0 = (double)(7 >> 16 & 255) / 255.0D;
////                double d1 = (double)(7 >> 8 & 255) / 255.0D;
////                double d2 = (double)(7 >> 0 & 255) / 255.0D;
////				 living.worldObj.spawnParticle("mobSpell", living.posX + (living.getRNG().nextDouble() - 0.5D) * (double)living.width, living.posY + living.getRNG().nextDouble() * (double)living.height - (double)living.yOffset, living.posZ + (living.getRNG().nextDouble() - 0.5D) * (double)living.width, 0, 0, 0);
//
//			}
//        }
		


		
		
		
		Unsaga.debug(this.toString());
	}
	
	public boolean isExpired(){
		
		if(this.remain<=0){
			Unsaga.debug(this.debuff.name+" is expired.");

			return true;
		}
		
		return false;
	}
	
	public String toString(){
		return this.debuff.number+":"+this.remain;
	}
	
	
	public static LivingDebuff buildFromString(String data){
		String[] strs = data.split(":");
		Debuff debuff = Debuffs.getDebuff(Integer.valueOf(strs[0]));
		LivingDebuff output = debuff.init(strs);
		Unsaga.debug(output.debuff.name+"復元");
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
		if(LivingDebuff.getLivingDebuff(living, Debuffs.powerup).isPresent()){
			LivingBuff buff = (LivingBuff)LivingDebuff.getLivingDebuff(living, Debuffs.powerup).get();
			amount += buff.amp;
		}
		return amount;
	}
	
	public static boolean isCooling(EntityLivingBase living){
		return hasDebuff(living,Debuffs.cooling);
	}

	protected String buildSaveString(Object... strs){
		List<Object> list = Lists.newArrayList(strs);
		StringBuilder saveString = new StringBuilder();
		for(Iterator<Object> ite=list.iterator();ite.hasNext();){
			String str = ite.next().toString();
			saveString.append(str);
			if(ite.hasNext()){
				saveString.append(":");
			}
		}
		
		return new String(saveString);
		
	}

	//TODO : このへん要改変
	public void onExpiredEvent(EntityLivingBase living) {
		if(this.debuff.getAttributeModifier()!=null){
			living.getEntityAttribute(this.debuff.getAttributeType()).removeModifier(this.debuff.getAttributeModifier());
			Unsaga.debug("おわりしました："+living.getEntityAttribute(this.debuff.getAttributeType()).getAttributeValue());

		}
		if(!living.worldObj.isRemote){
			PacketSyncDebuff psd = new PacketSyncDebuff(living.getEntityId(),this.debuff.number);
			Unsaga.packetPipeline.sendToAll(psd);
		}
//		if(living.worldObj.isRemote){
//			PacketSyncDebuff psd = new PacketSyncDebuff(living.getEntityId(),this.debuff.number);
//			Unsaga.packetPipeline.sendToServer(psd);
//		}

		
	}

	public void onInitEvent(EntityLivingBase living) {
		if(this.debuff.getAttributeModifier()!=null && living.getEntityAttribute(this.debuff.getAttributeType()).getModifier(this.debuff.getAttributeModifier().getID())==null){
			living.getEntityAttribute(this.debuff.getAttributeType()).applyModifier(this.debuff.getAttributeModifier());
			Unsaga.debug(this.debuff.getAttributeModifier().getName()+"アプライしました："+living.getEntityAttribute(this.debuff.getAttributeType()).getAttributeValue());
		}
	}

	
}

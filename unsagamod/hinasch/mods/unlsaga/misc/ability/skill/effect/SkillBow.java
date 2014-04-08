package hinasch.mods.unlsaga.misc.ability.skill.effect;

import hinasch.mods.unlsaga.Unsaga;
import hinasch.mods.unlsaga.core.event.ExtendedEntityTag;
import hinasch.mods.unlsaga.item.weapon.ItemBowUnsaga;
import hinasch.mods.unlsaga.misc.ability.AbilityRegistry;
import hinasch.mods.unlsaga.misc.ability.skill.Skill;
import hinasch.mods.unlsaga.misc.debuff.Debuffs;
import hinasch.mods.unlsaga.misc.debuff.livingdebuff.LivingBuff;
import hinasch.mods.unlsaga.misc.debuff.livingdebuff.LivingDebuff;
import hinasch.mods.unlsaga.misc.debuff.livingdebuff.LivingStateBow;
import hinasch.mods.unlsaga.misc.util.DamageHelper;
import hinasch.mods.unlsaga.network.packet.PacketParticle;
import hinasch.mods.unlsaga.network.packet.PacketSound;
import hinasch.mods.unlsaga.network.packet.PacketUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import com.hinasch.lib.HSLibs;
import com.hinasch.lib.StaticWords;
import com.hinasch.lib.VecUtil;
import com.hinasch.lib.XYZPos;


public class SkillBow extends SkillEffect{

	
	public final SkillBase exorcist = new SkillExorcist(ItemBowUnsaga.ARROW_EXORCIST);
	public final SkillBase zapper = new SkillZapper(ItemBowUnsaga.ARROW_ZAPPER);
	public final SkillBase shodowStitch = new SkillShadowStitch(ItemBowUnsaga.ARROW_STITCH);
	public final SkillBase multipleShoot = new SkillMultipleShoot();
	public final SkillBase phoenixArrow = new SkillPhoenix(ItemBowUnsaga.ARROW_PHOENIX);
	
	public static SkillBow INSTANCE;
	
	public static SkillBow getInstance(){
		if(INSTANCE==null){
			INSTANCE = new SkillBow();
		}
		return INSTANCE;
		
	}
	
	protected static Map<String,SkillBowBase> tagAssociatedMap = new HashMap();
	

	public static Map<String,SkillBowBase> getAssociatedMap(){
		return tagAssociatedMap;
	}
	
	public static SkillBowBase getSkillAssociatedWithTag(String tag){
		if(tagAssociatedMap.containsKey(tag)){
			return tagAssociatedMap.get(tag);
		}
		return null;
	}
	
	
	public abstract class SkillBowBase extends SkillBase{

		protected String particleString;
		public SkillBowBase(String key){
			super();
			tagAssociatedMap.put(key,this);
		}
		
		@Override
		public void invokeSkill(InvokeSkill helper) {

		}
		
		public void prepareHitEvent(InvokeSkill helper){
			LivingHurtEvent e = (LivingHurtEvent)helper.parent;
			EntityArrow arrow = (EntityArrow)e.source.getSourceOfDamage();
			this.postHit(helper,e,arrow);
		}
		
		public String getParticle(){
			return particleString;
		}
		
		abstract public void postHit(InvokeSkill helper, LivingHurtEvent e, EntityArrow arrow);
		
		abstract public void postShoot(EntityArrow arrow);
		
		abstract public Skill getSkill();
		
		
		public void onArrowStopped(EntityArrow arrow){
			Unsaga.debug("stop event");
		}
	}

	public class SkillPhoenix extends SkillBowBase{

		public SkillPhoenix(String key) {
			super(key);
			this.particleString = StaticWords.particleFlame;
		}

		@Override
		public void postHit(InvokeSkill helper, LivingHurtEvent e,
				EntityArrow arrow) {
			if(ExtendedEntityTag.hasTag(arrow, ItemBowUnsaga.ARROW_PHOENIX)){
				if(e.entityLiving.getCreatureAttribute()!=EnumCreatureAttribute.UNDEAD){
					if(arrow.shootingEntity instanceof EntityLivingBase){
						Unsaga.debug("きてます");
						EntityLivingBase living = (EntityLivingBase)arrow.shootingEntity;
						Unsaga.debug(living.getCommandSenderName());
						float healing = e.ammount + DamageHelper.getDamageModifierFromType(DamageHelper.Type.SPEAR,e.entityLiving, e.ammount);
						healing *= 0.5F;
						Unsaga.debug(healing+"amount:"+e.ammount);
						if(healing>0){
							PacketParticle pp = new PacketParticle(StaticWords.getParticleNumber(StaticWords.particleHappy),living.getEntityId(), 5);
							Unsaga.packetPipeline.sendToAllAround(pp, PacketUtil.getTargetPointNear(living));
							living.heal(healing);
						}
					}
				}
			}
			
		}

		@Override
		public void postShoot(EntityArrow arrow) {
			PacketSound ps = new PacketSound(1008,arrow.shootingEntity.getEntityId(),PacketSound.MODE.AUX);
			Unsaga.packetPipeline.sendToAllAround(ps, PacketUtil.getTargetPointNear(arrow.shootingEntity));
			ExtendedEntityTag.addTagToEntity(arrow, ItemBowUnsaga.ARROW_PHOENIX);
			
		}

		@Override
		public Skill getSkill() {
			// TODO 自動生成されたメソッド・スタブ
			return AbilityRegistry.phoenix;
		}
		
	}
	
	public class SkillZapper extends SkillBowBase{
		
		public SkillZapper(String key) {
			super(key);
			this.particleString = "";
		}

		@Override
		public void postHit(InvokeSkill helper, LivingHurtEvent e, EntityArrow arrow){
			Unsaga.debug("ヒット！");
			if(ExtendedEntityTag.hasTag(arrow, ItemBowUnsaga.ARROW_ZAPPER)){
				if(!arrow.worldObj.isRemote){
					arrow.setKnockbackStrength(2);
					e.ammount += helper.getModifiedAttackDamage();
					arrow.worldObj.createExplosion(helper.owner, arrow.posX, arrow.posY, arrow.posZ, 1.5F, false);
				}
			}
		}
		
		@Override
		public void postShoot(EntityArrow arrow){
			Unsaga.debug("タグつけました");
			ExtendedEntityTag.addTagToEntity(arrow, ItemBowUnsaga.ARROW_ZAPPER);
		}

		@Override
		public Skill getSkill() {
			// TODO 自動生成されたメソッド・スタブ
			return AbilityRegistry.zapper;
		}
	}
	
	public class SkillExorcist extends SkillBowBase{
		public SkillExorcist(String key) {
			super(key);
			this.particleString = StaticWords.particleHappy;
		}

		@Override
		public void postHit(InvokeSkill helper, LivingHurtEvent e, EntityArrow arrow){
			if(ExtendedEntityTag.hasTag(arrow, ItemBowUnsaga.ARROW_EXORCIST)){
				if(e.entity instanceof EntityLivingBase){
					EntityLivingBase el = (EntityLivingBase)e.entity;
					if(el.getCreatureAttribute()==EnumCreatureAttribute.UNDEAD){
						e.ammount += helper.getModifiedAttackDamage();
						PacketParticle pp = new PacketParticle(StaticWords.getParticleNumber(StaticWords.particleHappy),e.entityLiving.getEntityId(), 5);
						Unsaga.packetPipeline.sendToAllAround(pp, PacketUtil.getTargetPointNear(e.entityLiving));
						el.setFire(5);


					}
				}
			}
		}
		
		@Override
		public void postShoot(EntityArrow arrow){
			Unsaga.debug("タグつけました");
			ExtendedEntityTag.addTagToEntity(arrow, ItemBowUnsaga.ARROW_EXORCIST);
		}

		@Override
		public Skill getSkill() {
			// TODO 自動生成されたメソッド・スタブ
			return AbilityRegistry.exorcist;
		}
	}
	
	public class SkillShadowStitch extends SkillBowBase{

		public SkillShadowStitch(String key) {
			super(key);
			this.particleString = StaticWords.particlePortal;
		}

		@Override
		public void postHit(InvokeSkill helper, LivingHurtEvent e,
				EntityArrow arrow) {
			// TODO 自動生成されたメソッド・スタブ
			
		}

		@Override
		public void postShoot(EntityArrow arrow) {
			Unsaga.debug("タグつけました");
			ExtendedEntityTag.addTagToEntity(arrow, ItemBowUnsaga.ARROW_STITCH);
			
		}
		
		@Override
		public void onArrowStopped(EntityArrow arrow){
			AxisAlignedBB bb = arrow.boundingBox.expand(5D, 5D, 5D);
			List<EntityLivingBase> livings = arrow.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, bb);
			for(EntityLivingBase living:livings){
				if(arrow.shootingEntity!=living && !LivingDebuff.hasDebuff(living, Debuffs.sleep)){
					LivingBuff.addDebuff(living, Debuffs.sleep, 15);
				}
			}
			
		}

		@Override
		public Skill getSkill() {
			// TODO 自動生成されたメソッド・スタブ
			return AbilityRegistry.shadowStitching;
		}
		
	}


	public class SkillMultipleShoot extends SkillBase{

		@Override
		public void invokeSkill(InvokeSkill parent) {
			boolean flag = false;
			if(parent.getOwnerEP().inventory.hasItem(Items.arrow))flag = true;
			if(parent.getOwnerEP().capabilities.isCreativeMode)flag = true;
			if(!flag)return ;

			Unsaga.debug("doubleショットmadekiter");
			Unsaga.debug("remote:"+parent.owner.worldObj.isRemote);
			parent.playBowSound(parent.charge);

			LivingStateBow state = (LivingStateBow)parent.parent;

			EntityArrow clone = new EntityArrow(parent.world, parent.owner,parent.charge * 1.0F+parent.world.rand.nextFloat());
			clone.setDamage(clone.getDamage()+parent.getAttackDamage());
			//UtilItem.setArrowProp(clone, "LPHurt");
			if(EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, parent.weapon)>0){
				clone.canBePickedUp  = 2;
			}

			if(state.tag.equals("random")){
				Vec3 shaked = VecUtil.getShake(VecUtil.getVecFromEntityMotion(parent.world, clone), parent.world.rand, 20, 41, 5, 11);
				clone.motionX = shaked.xCoord;
				clone.motionY = shaked.yCoord;
				clone.motionZ = shaked.zCoord;

			}else{
				Vec3 shaked = VecUtil.getShake(VecUtil.getVecFromEntityMotion(parent.world, clone), parent.world.rand, 2, 4, 2, 4);
				clone.motionX = shaked.xCoord;
				clone.motionY = shaked.yCoord;
				clone.motionZ = shaked.zCoord;
			}


			if(!parent.world.isRemote){
//				state.shootTick -= 1;
//				if(state.shootTick>0){
	//
	//
//				}
				if(EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, parent.weapon)==0 && !parent.getOwnerEP().capabilities.isCreativeMode){
					parent.getOwnerEP().inventory.consumeInventoryItem(Items.arrow);
				}
				parent.world.spawnEntityInWorld(clone);
				return ;
			}
			return ;
			
		}
		
	}

	public static void checkArrowOnLivingHurtEvent(LivingHurtEvent e){
		if(e.source.getSourceOfDamage() instanceof EntityArrow && e.source.getEntity() instanceof EntityPlayer){
			EntityArrow arrow = (EntityArrow)e.source.getSourceOfDamage();
			EntityLivingBase attacker = (EntityLivingBase)e.source.getEntity();
			for(String key:tagAssociatedMap.keySet()){
				if(ExtendedEntityTag.hasTag(arrow, key)){
					SkillBowBase skillBow = getSkillAssociatedWithTag(key);
					if(skillBow!=null){
						InvokeSkill helper = new InvokeSkill(attacker.worldObj, (EntityPlayer) attacker, skillBow.getSkill(), attacker.getHeldItem());
						skillBow.postHit(helper, e, arrow);
					}
				}
			}
		}
	}
	
	public static void checkArrowOnStoppedEvent(LivingUpdateEvent eventObj){
		AxisAlignedBB bb = eventObj.entityLiving.boundingBox.expand(100D, 100D, 100D);
		List<EntityArrow> arrows = eventObj.entityLiving.worldObj.getEntitiesWithinAABB(EntityArrow.class, bb);
		for(EntityArrow arrow:arrows){
			for(String key:tagAssociatedMap.keySet()){
				if(HSLibs.getArrowTickInGround(arrow,Unsaga.debug.get())<10 && ExtendedEntityTag.hasTag(arrow, key)){
					SkillBowBase skillBow = getSkillAssociatedWithTag(key);
					if(skillBow!=null){
						skillBow.onArrowStopped(arrow);
					}
				}
			}
			
		}
	}
	
	public static void checkArrowParticleEvent(LivingUpdateEvent e){
		if(e.entityLiving instanceof EntityPlayer){
			EntityPlayer ep = (EntityPlayer)e.entityLiving;
			if(!HSLibs.isServer(ep.worldObj)){
				return;
			}
			//Unsaga.debug("クライアントです");
			AxisAlignedBB bb = ep.boundingBox.expand(100.0D, 100.0D, 100.0D);
			List<EntityArrow> nearArrowList = ep.worldObj.getEntitiesWithinAABB(EntityArrow.class, bb);
			for(EntityArrow arrow:nearArrowList){
				if(arrow.ticksExisted %2 == 0){
					//Unsaga.debug("ぱーちくる前");
					for(String key:tagAssociatedMap.keySet()){
						if(!HSLibs.isArrowInGround(arrow,Unsaga.debug.get()) && ExtendedEntityTag.entityHasTag(arrow, key)){
							//Unsaga.debug("ぱーちくるきてます");
							SkillBowBase skillBow = SkillBow.getInstance().getSkillAssociatedWithTag(key);
							if(skillBow!=null){
								PacketParticle pp = new PacketParticle(XYZPos.entityPosToXYZ(arrow),StaticWords.getParticleNumber(skillBow.getParticle()),2);
								Unsaga.packetPipeline.sendTo(pp, (EntityPlayerMP) ep);
							}

							//arrow.worldObj.spawnParticle(StaticWords.particleHappy, arrow.posX, arrow.posY + ep.worldObj.rand.nextDouble() * 2.0D, arrow.posZ, ep.worldObj.rand.nextGaussian()*2, 0.0D, ep.worldObj.rand.nextGaussian()*2);
						}
					}

					
				}
			}

		}
	}
	
//	public static void checkShadowStitchOnLivingUpdate(LivingUpdateEvent eventObj){
//		//LivingUpdateEvent eventObj = (LivingUpdateEvent)parent.parent;
//		AxisAlignedBB bb = eventObj.entityLiving.boundingBox.expand(5D, 5D, 5D);
//		List<EntityArrow> arrows = eventObj.entityLiving.worldObj.getEntitiesWithinAABB(EntityArrow.class, bb);
//		
//		for(EntityArrow arrow:arrows){
//			Unsaga.debug("矢を検地");
//			Unsaga.debug(arrow.motionX + arrow.motionY + arrow.motionZ);
//			if(arrow.shootingEntity!=eventObj.entityLiving && !LivingDebuff.hasDebuff(eventObj.entityLiving, Debuffs.sleep)){
//				if(arrow.motionX + arrow.motionY + arrow.motionZ <= 0.00001D && ExtendedEntityTag.hasTag(arrow, ItemBowUnsaga.ARROW_STITCH)){
//					Unsaga.debug("カゲヌイがあたりました");
//					
//					LivingBuff.addDebuff(eventObj.entityLiving, Debuffs.sleep, 15);
//					arrow.setDead();
//				}
//			}
//
//		}
//	}
	

}

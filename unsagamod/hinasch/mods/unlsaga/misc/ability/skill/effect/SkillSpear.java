package hinasch.mods.unlsaga.misc.ability.skill.effect;


import hinasch.mods.unlsaga.Unsaga;
import hinasch.mods.unlsaga.client.ClientHelper;
import hinasch.mods.unlsaga.item.weapon.ItemSpearUnsaga;
import hinasch.mods.unlsaga.misc.ability.AbilityRegistry;
import hinasch.mods.unlsaga.misc.debuff.Debuffs;
import hinasch.mods.unlsaga.misc.debuff.livingdebuff.LivingDebuff;
import hinasch.mods.unlsaga.misc.util.DamageHelper;
import hinasch.mods.unlsaga.misc.util.DamageSourceUnsaga;
import hinasch.mods.unlsaga.misc.util.rangedamage.CauseAddVelocity;
import hinasch.mods.unlsaga.misc.util.rangedamage.CauseKnockBack;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import com.hinasch.lib.HSLibs;
import com.hinasch.lib.PairID;
import com.hinasch.lib.RangeDamageHelper;
import com.hinasch.lib.ScanHelper;
import com.hinasch.lib.XYZPos;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

public class SkillSpear extends SkillEffect{

	@Override
	public void selector(InvokeSkill parent){

		if(parent.skill==AbilityRegistry.grassHopper)this.doGrassHopper(parent);
		if(parent.skill==AbilityRegistry.acupuncture)this.doAiming(parent);
		if(parent.skill==AbilityRegistry.aiming)this.doAiming(parent);
		if(parent.skill==AbilityRegistry.swing)this.doSwing(parent);

	}
	public static SkillSpear INSTANCE;

	public final SkillBase aiming = new SkillAiming(SkillMelee.Type.STOPPED_USING).setRequireSneak(false);
	public final SkillBase acupuncture = new SkillAcupuncture(SkillMelee.Type.STOPPED_USING).setRequireSneak(false);
	public final SkillBase swing = new SkillSwing(SkillMelee.Type.RIGHTCLICK,5.0D,1.0D);
	public final SkillBase grassHopper = new SkillGrassHopper(SkillMelee.Type.USE,3.0D);


	public class SkillAiming extends SkillMelee{

		public SkillAiming(Type type) {
			super(type);
			
		}



		protected int chargeTime = 0;
		@Override
		public void invokeSkill(InvokeSkill parent) {
			
			ItemSpearUnsaga spear = (ItemSpearUnsaga)parent.weapon.getItem();
			ItemSpearUnsaga.setNeutral(parent.weapon);
			if(parent.charge<chargeTime){
				return;
			}
			parent.owner.swingItem();
			MovingObjectPosition mop = ClientHelper.getMouseOver();
			if(mop!=null && mop.entityHit!=null){
				AxisAlignedBB bb = mop.entityHit.boundingBox;
				List<Entity> entlist = parent.world.getEntitiesWithinAABB(Entity.class, bb);
				if(!entlist.isEmpty() && parent.owner.getDistanceToEntity(mop.entityHit)<spear.getReach()){
					Entity hitent = entlist.get(0);
					if(hitent!=parent.owner){		
						parent.attack(hitent, null,parent.charge);
						this.hookOnAimingHit(parent, hitent);
					}

				}
			}
		}



		public void hookOnAimingHit(InvokeSkill parent,Entity hitent){
		}

	}

	public class SkillAcupuncture extends SkillAiming{


		public SkillAcupuncture(Type type) {
			super(type);
			this.chargeTime = 30;
		}

		@Override
		public void hookOnAimingHit(InvokeSkill parent,Entity hitent){
			for (int i = 0; i < 32; ++i)
			{
				parent.world.spawnParticle("portal", hitent.posX, hitent.posY + parent.world.rand.nextDouble() * 2.0D, hitent.posZ, parent.world.rand.nextGaussian()*2, 0.0D, parent.world.rand.nextGaussian()*2);
			}
			hitent.playSound("mob.irongolem.hit", 1.5F,1.2F / (parent.world.rand.nextFloat() * 0.2F + 0.9F));
			parent.attack(hitent, null,parent.charge);

			if(hitent instanceof EntityLivingBase){
				if(parent.world.rand.nextInt(3)<1){
					LivingDebuff.addDebuff((EntityLivingBase) hitent, Debuffs.downSkill, 20);
				}

			}


		}
	}
	public void doAiming(InvokeSkill parent){
		int ac = 30;
		ItemSpearUnsaga spear = (ItemSpearUnsaga)parent.weapon.getItem();


		ItemSpearUnsaga.setNeutral(parent.weapon);
		if(parent.skill==AbilityRegistry.acupuncture && (int)parent.charge<ac){
			return;
		}


		parent.owner.swingItem();
		MovingObjectPosition mop = ClientHelper.getMouseOver();
		if(mop!=null){
			if(mop.entityHit!=null){
				AxisAlignedBB bb = mop.entityHit.boundingBox;
				List<Entity> entlist = parent.world.getEntitiesWithinAABB(Entity.class, bb);
				if(!entlist.isEmpty() && parent.owner.getDistanceToEntity(mop.entityHit)<spear.getReach()){
					Entity hitent = entlist.get(0);

					//System.out.println(reach);
					if(hitent!=parent.owner){
						if(parent.skill==AbilityRegistry.acupuncture && (int)parent.charge>ac){
							//float f3 = 0.25F;
							for (int i = 0; i < 32; ++i)
							{
								parent.world.spawnParticle("portal", hitent.posX, hitent.posY + parent.world.rand.nextDouble() * 2.0D, hitent.posZ, parent.world.rand.nextGaussian()*2, 0.0D, parent.world.rand.nextGaussian()*2);
							}
							hitent.playSound("mob.irongolem.hit", 1.5F,1.2F / (parent.world.rand.nextFloat() * 0.2F + 0.9F));
							parent.attack(hitent, null,parent.charge);

							if(hitent instanceof EntityLivingBase){
								if(parent.world.rand.nextInt(3)<1){
									LivingDebuff.addDebuff((EntityLivingBase) hitent, Debuffs.downSkill, 20);
								}

							}

						}
						if(parent.skill!=AbilityRegistry.aiming){

							parent.attack(hitent, null,parent.charge);

						}


					}

				}
			}
		}

	}

	//	public class SkillRangedAttack extends SkillBase{
	//
	//		public double rangeVertical = 1.0D;
	//		public double rangeHorizontal = 5.0D;
	//
	//		@Override
	//		public void invokeSkill(InvokeSkill parent) {
	//			EntityPlayer ep = parent.owner;
	//			ep.swingItem();
	//			this.playShootSound(ep);
	//
	//
	//			Vec3 vec = ep.getLookVec();
	//			vec.normalize();
	//			AxisAlignedBB ab = ep.boundingBox.expand(rangeHorizontal, rangeVertical, rangeHorizontal);
	//			RangeDamageHelper cause = new CauseKnockBack(parent.world, 2.0D);
	//			cause.causeRangeDamage(ab, parent.getDamageSource(), parent.getModifiedAttackDamage(false, 0));
	//			//ab.offset(vec.xCoord, 0, vec.zCoord);
	//			List<Entity> enlist = parent.world.getEntitiesWithinAABB(Entity.class, ab);
	////			if(!enlist.isEmpty()){
	////				for(Iterator<Entity> i=enlist.iterator();i.hasNext();){
	////					Entity hitent = i.next();
	////
	////					if(HSLibs.isEnemy(hitent, ep)){
	////						parent.attack(hitent, null);
	////						//hitent.attackEntityFrom(DamageSource.causePlayerDamage(ep), parent.getAttackDamage());
	////						//hitent.attackEntityFrom(DamageSource.causePlayerDamage(ep), AbilityRegistry.swing.damageWeapon);
	////						double d0 = ep.posX - hitent.posX;
	////						double d1;
	////
	////						for (d1 = ep.posZ - hitent.posZ; d0 * d0 + d1 * d1 < 1.0E-4D; d1 = (Math.random() - Math.random()) * 0.01D)
	////						{
	////							d0 = (Math.random() - Math.random()) * 0.01D;
	////						}
	////						((EntityLiving)hitent).knockBack(ep, 0	, d0, d1);
	////					}
	////
	////				}
	////			}
	//		}
	//
	//	}

	public class SkillSwing extends SkillRangedAttack{

		public SkillSwing(SkillMelee.Type type,double horizontal, double vertical) {
			super(type,horizontal, vertical);
			this.onGroundOnly = false;
		}

		public RangeDamageHelper getCustomizedRangeDamageHelper(InvokeSkill parent){
			return new CauseKnockBack(parent.world, 2.0D);
		}
		
		@Override
		public void hookStart(InvokeSkill helper){
			this.playShootSound(helper.owner);
			helper.owner.swingItem();
		}
	}
	
	public class SkillGrassHopper extends SkillRangedAttack{
		List<Block> idslist = Arrays.asList(Blocks.tallgrass,Blocks.double_plant,Blocks.deadbush,Blocks.fire,Blocks.web,Blocks.wheat);

		protected double max;
		public SkillGrassHopper(SkillMelee.Type type,double par1) {
			super(type,0, 0);
			this.onGroundOnly = true;
			this.max = par1;
		}
		
		@Override
		public void hookStart(InvokeSkill helper){
			this.playShootSound(helper.owner);
			helper.owner.swingItem();
			if(helper.owner.isSneaking()){
				ScanHelper scan = new ScanHelper(helper.usepoint.x,helper.usepoint.y,helper.usepoint.z,3,3);
				scan.setWorld(helper.world);
				for(;scan.hasNext();scan.next()){
					if(idslist.contains(scan.getBlock()) || scan.getBlock() instanceof BlockCrops){
						Block block = scan.getBlock();
						Unsaga.debug("きてるｓｑうぃｇ");
						HSLibs.playBlockBreakSFX(helper.world, new XYZPos(scan.sx,scan.sy,scan.sz), new PairID(block,scan.getMetadata()));
					}
				}
			}
		}
		
		@Override
		public AxisAlignedBB getCustomizedBoundingBox(InvokeSkill helper){

			double bxs = (double)helper.usepoint.x - (max/2);
			double bys = (double)helper.usepoint.y;
			double bzs = (double)helper.usepoint.z - (max/2);
			AxisAlignedBB aabb = AxisAlignedBB.getBoundingBox(bxs, bys-2, bzs, bxs+max, bys+2, bzs+max);
			return aabb;
		}
		
		@Override
		public RangeDamageHelper getCustomizedRangeDamageHelper(InvokeSkill helper){
			return new CauseAddVelocity(helper.world,helper);
		}
		
	}
	public void doSwing(InvokeSkill parent){
		EntityPlayer ep = parent.getOwnerEP();
		ep.swingItem();
		ep.playSound("mob.wither.shoot", 0.5F, 1.8F / (ep.getRNG().nextFloat() * 0.4F + 1.2F));


		Vec3 vec = ep.getLookVec();
		vec.normalize();
		AxisAlignedBB ab = ep.boundingBox.expand(5.0D, 1.0D, 5.0D);
		//ab.offset(vec.xCoord, 0, vec.zCoord);
		List<Entity> enlist = parent.world.getEntitiesWithinAABB(Entity.class, ab);
		if(!enlist.isEmpty()){
			for(Iterator<Entity> i=enlist.iterator();i.hasNext();){
				Entity hitent = i.next();

				if(HSLibs.isEnemy(hitent, ep)){
					parent.attack(hitent, null);
					//hitent.attackEntityFrom(DamageSource.causePlayerDamage(ep), parent.getAttackDamage());
					//hitent.attackEntityFrom(DamageSource.causePlayerDamage(ep), AbilityRegistry.swing.damageWeapon);
					double d0 = ep.posX - hitent.posX;
					double d1;

					for (d1 = ep.posZ - hitent.posZ; d0 * d0 + d1 * d1 < 1.0E-4D; d1 = (Math.random() - Math.random()) * 0.01D)
					{
						d0 = (Math.random() - Math.random()) * 0.01D;
					}
					((EntityLiving)hitent).knockBack(ep, 0	, d0, d1);
				}

			}
		}
	}

	public void doGrassHopper(InvokeSkill helper){
		List<Block> idslist = Arrays.asList(Blocks.tallgrass,Blocks.double_plant,Blocks.deadbush,Blocks.fire,Blocks.web,Blocks.wheat);

		helper.owner.swingItem();
		helper.playSound(helper.getWitherSound());

		double max = 3.0D;

		double bxs = (double)helper.usepoint.x - (max/2);
		double bys = (double)helper.usepoint.y;
		double bzs = (double)helper.usepoint.z - (max/2);
		AxisAlignedBB aabb = AxisAlignedBB.getBoundingBox(bxs, bys-2, bzs, bxs+max, bys+2, bzs+max);
		CauseAddVelocity cause = new CauseAddVelocity(helper.world,helper);
		DamageSourceUnsaga ds = new DamageSourceUnsaga(null,helper.owner,AbilityRegistry.grassHopper.hurtLp,DamageHelper.Type.SPEAR);
		cause.setSkillEffectHelper(helper);
		cause.causeRangeDamage(aabb, helper.getDamageSource(), helper.getModifiedAttackDamage(false, 0));
		//RangeDamageHelper.causeDamage(cause, helper.world, aabb, AbilityRegistry.grassHopper.hurtHp, ds, false);



		if(helper.owner.isSneaking()){
			ScanHelper scan = new ScanHelper(helper.usepoint.x,helper.usepoint.y,helper.usepoint.z,3,3);
			scan.setWorld(helper.world);
			for(;scan.hasNext();scan.next()){
				if(idslist.contains(scan.getBlock()) || scan.getBlock() instanceof BlockCrops){
					Block block = scan.getBlock();
					Unsaga.debug("きてるｓｑうぃｇ");

					HSLibs.playBlockBreakSFX(helper.world, new XYZPos(scan.sx,scan.sy,scan.sz), new PairID(block,scan.getMetadata()));
					//						}
					//					}
				}
			}
		}


		//		}
	}

	public static SkillSpear getInstance(){
		if(INSTANCE==null){
			INSTANCE = new SkillSpear();
		}
		return INSTANCE;
	}
}

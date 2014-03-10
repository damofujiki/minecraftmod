package hinasch.mods.unlsaga.misc.ability.skill.effect;


import hinasch.lib.RangeDamageHelper;
import hinasch.lib.HSLibs;
import hinasch.lib.PairID;
import hinasch.lib.ScanHelper;
import hinasch.lib.XYZPos;
import hinasch.mods.unlsaga.Unsaga;
import hinasch.mods.unlsaga.client.ClientHelper;
import hinasch.mods.unlsaga.item.weapon.ItemSpearUnsaga;
import hinasch.mods.unlsaga.misc.ability.AbilityRegistry;
import hinasch.mods.unlsaga.misc.debuff.Debuffs;
import hinasch.mods.unlsaga.misc.debuff.livingdebuff.LivingDebuff;
import hinasch.mods.unlsaga.misc.util.DamageHelper;
import hinasch.mods.unlsaga.misc.util.DamageSourceUnsaga;
import hinasch.mods.unlsaga.misc.util.rangedamage.CauseAddVelocity;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

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
	public void selector(SkillEffectHelper parent){
		
		if(parent.skill==AbilityRegistry.grassHopper)this.doGrassHopper(parent);
		if(parent.skill==AbilityRegistry.acupuncture)this.doAiming(parent);
		if(parent.skill==AbilityRegistry.aiming)this.doAiming(parent);
		if(parent.skill==AbilityRegistry.swing)this.doSwing(parent);
		
	}
	public void doAiming(SkillEffectHelper parent){
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
	
	public void doSwing(SkillEffectHelper parent){
		EntityPlayer ep = parent.owner;
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
	
	public void doGrassHopper(SkillEffectHelper helper){
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
		RangeDamageHelper.causeDamage(cause, helper.world, aabb, AbilityRegistry.grassHopper.hurtHp, ds, false);

		

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
}

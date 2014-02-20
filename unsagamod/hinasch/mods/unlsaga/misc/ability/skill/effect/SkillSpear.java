package hinasch.mods.unlsaga.misc.ability.skill.effect;


import hinasch.lib.CauseDamageBoundingbox;
import hinasch.lib.HSLibs;
import hinasch.lib.ScanHelper;
import hinasch.mods.unlsaga.Unsaga;
import hinasch.mods.unlsaga.item.weapon.ItemSpearUnsaga;
import hinasch.mods.unlsaga.misc.ability.AbilityRegistry;
import hinasch.mods.unlsaga.misc.debuff.DebuffRegistry;
import hinasch.mods.unlsaga.misc.debuff.LivingDebuff;
import hinasch.mods.unlsaga.misc.util.CauseAddVelocity;
import hinasch.mods.unlsaga.misc.util.UtilItem;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
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
		ItemSpearUnsaga spear = (ItemSpearUnsaga)parent.weaponGained.getItem();


		ItemSpearUnsaga.setNeutral(parent.weaponGained);
		if(parent.skill==AbilityRegistry.acupuncture && (int)parent.charge<ac){
			return;
		}
		
		
		parent.ownerSkill.swingItem();
		MovingObjectPosition mop = UtilItem.getMouseOver();
		if(mop!=null){
			if(mop.entityHit!=null){
				AxisAlignedBB bb = mop.entityHit.boundingBox;
				List<Entity> entlist = parent.world.getEntitiesWithinAABB(Entity.class, bb);
				if(!entlist.isEmpty() && parent.ownerSkill.getDistanceToEntity(mop.entityHit)<spear.getReach()){
					Entity hitent = entlist.get(0);

					//System.out.println(reach);
					if(hitent!=parent.ownerSkill){
						if(parent.skill==AbilityRegistry.acupuncture && (int)parent.charge>ac){
							float f3 = 0.25F;
							for (int i = 0; i < 32; ++i)
							{
								parent.world.spawnParticle("portal", hitent.posX, hitent.posY + parent.world.rand.nextDouble() * 2.0D, hitent.posZ, parent.world.rand.nextGaussian()*2, 0.0D, parent.world.rand.nextGaussian()*2);
							}
							hitent.playSound("mob.irongolem.hit", 1.5F,1.2F / (parent.world.rand.nextFloat() * 0.2F + 0.9F));
							parent.attack(hitent, null);
							//hitent.attackEntityFrom(DamageSource.causePlayerDamage(parent.ownerSkill), parent.getAttackDamage());
							UtilItem.playerAttackEntityWithItem(parent.ownerSkill, hitent, Math.round(parent.charge*0.5F), -1);
							//UtilSkill.tryLPHurt(50, 2, hitent, ep);
							if(hitent instanceof EntityLivingBase){
								if(parent.world.rand.nextInt(3)<1){
									LivingDebuff.addDebuff((EntityLivingBase) hitent, DebuffRegistry.downSkill, 20);
								}
								
							}
							
						}
						if(parent.skill!=AbilityRegistry.aiming){
							
							parent.attack(hitent, null);
							//hitent.attackEntityFrom(DamageSource.causePlayerDamage(parent.ownerSkill), parent.getAttackDamage());

						}


					}

				}
			}
		}

	}
	
	public void doSwing(SkillEffectHelper parent){
		EntityPlayer ep = parent.ownerSkill;
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
		List<Integer> idslist = Arrays.asList(Block.tallGrass.blockID,Block.crops.blockID);

		helper.ownerSkill.swingItem();
		helper.playSoundServer(helper.getWitherSound());

		double max = 3.0D;

		double bxs = (double)helper.usepoint.x - (max/2);
		double bys = (double)helper.usepoint.y;
		double bzs = (double)helper.usepoint.z - (max/2);
		AxisAlignedBB aabb = AxisAlignedBB.getBoundingBox(bxs, bys-2, bzs, bxs+max, bys+2, bzs+max);
		CauseAddVelocity cause = new CauseAddVelocity(helper.world,helper);
		cause.setSkillEffectHelper(helper);
		CauseDamageBoundingbox.causeDamage(cause, helper.world, aabb, AbilityRegistry.grassHopper.hurtHp
				, DamageSource.causePlayerDamage(helper.ownerSkill), false);

		

		if(helper.ownerSkill.isSneaking()){
			ScanHelper scan = new ScanHelper(helper.usepoint.x,helper.usepoint.y,helper.usepoint.z,3,3);
			scan.setWorld(helper.world);
			for(;scan.hasNext();scan.next()){
				if(idslist.contains(scan.getID()) || Block.blocksList[scan.getID()] instanceof BlockCrops){
					Block block = Block.blocksList[scan.getID()];
					Unsaga.debug("きてるｓｑうぃｇ");

					helper.world.playAuxSFX(2001, scan.sx, scan.sy, scan.sz, scan.getID() + (scan.getMetadata()  << 12));
					if(!helper.world.isRemote){
						boolean flag = helper.world.setBlockToAir(scan.sx, scan.sy, scan.sz);
						if (block != null && flag) {
							block.onBlockDestroyedByPlayer(helper.world, scan.sx, scan.sy, scan.sz, scan.getMetadata());
							block.dropBlockAsItem(helper.world, scan.sx, scan.sy, scan.sz, scan.getMetadata(),1);
						}
					}
				}
			}
		}


		//		}
	}
}

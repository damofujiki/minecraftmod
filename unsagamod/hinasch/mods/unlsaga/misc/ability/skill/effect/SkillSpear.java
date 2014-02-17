package hinasch.mods.unlsaga.misc.ability.skill.effect;


import hinasch.lib.CauseDamageBoundingbox;
import hinasch.lib.HSLibs;
import hinasch.lib.ScanHelper;
import hinasch.mods.unlsaga.item.weapon.ItemSpearUnsaga;
import hinasch.mods.unlsaga.misc.ability.AbilityRegistry;
import hinasch.mods.unlsaga.misc.ability.HelperAbility;
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
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class SkillSpear extends SkillEffect{
	
	@Override
	public void selector(SkillEffectHelper parent){
		
		if(parent.skill==AbilityRegistry.grassHopper)this.doGrassHopper(parent);
		
	}
	public void doAiming(World world,EntityPlayer ep,ItemStack is,int j,float reach){
		int ac = 30;
		if(!(HelperAbility.hasAbilityFromItemStack(AbilityRegistry.acupuncture, is) && j<ac)){
			ep.swingItem();
		}

		ItemSpearUnsaga.setNeutral(is);

		MovingObjectPosition mop = UtilItem.getMouseOver();
		if(mop!=null){
			if(mop.entityHit!=null){
				AxisAlignedBB bb = mop.entityHit.boundingBox;
				List<Entity> entlist = world.getEntitiesWithinAABB(Entity.class, bb);
				if(!entlist.isEmpty() && ep.getDistanceToEntity(mop.entityHit)<reach){
					Entity hitent = entlist.get(0);

					//System.out.println(reach);
					if(hitent!=ep){
						if(HelperAbility.hasAbilityFromItemStack(AbilityRegistry.acupuncture, is) && j>ac){
							float f3 = 0.25F;
							for (int i = 0; i < 32; ++i)
							{
								world.spawnParticle("portal", hitent.posX, hitent.posY + world.rand.nextDouble() * 2.0D, hitent.posZ, world.rand.nextGaussian()*2, 0.0D, world.rand.nextGaussian()*2);
							}
							hitent.playSound("mob.irongolem.hit", 1.5F,1.2F / (world.rand.nextFloat() * 0.2F + 0.9F));
							UtilItem.playerAttackEntityWithItem(ep, hitent, Math.round(j*0.5F), -1);
							//UtilSkill.tryLPHurt(50, 2, hitent, ep);
							if(hitent instanceof EntityLivingBase){
								if(world.rand.nextInt(3)<1){
									LivingDebuff.addDebuff((EntityLivingBase) hitent, DebuffRegistry.downSkill, 20);
								}
								
							}
							is.damageItem(AbilityRegistry.acupuncture.damageWeapon, ep);
						}
						if(HelperAbility.hasAbilityFromItemStack(AbilityRegistry.aiming, is)){

							UtilItem.playerAttackEntityWithItem(ep, hitent, Math.round(j*0.4F), -1);


							is.damageItem(AbilityRegistry.aiming.damageWeapon, ep);
						}


					}

				}
			}
		}

	}
	
	public void doSwing(World world,EntityPlayer ep,ItemStack is){
		ep.swingItem();
		ep.playSound("mob.wither.shoot", 0.5F, 1.8F / (ep.getRNG().nextFloat() * 0.4F + 1.2F));

		AxisAlignedBB ab = ep.boundingBox.expand(5.0D, 1.0D, 5.0D);
		List<Entity> enlist = world.getEntitiesWithinAABB(Entity.class, ab);
		if(!enlist.isEmpty()){
			for(Iterator<Entity> i=enlist.iterator();i.hasNext();){
				Entity hitent = i.next();

				if(HSLibs.isEnemy(hitent, ep)){
					UtilItem.playerAttackEntityWithItem(ep, hitent, AbilityRegistry.swing.damageWeapon,0.4F);
					//hitent.attackEntityFrom(DamageSource.causePlayerDamage(ep), AbilityRegistry.swing.damageWeapon);
					double d0 = ep.posX - hitent.posX;
					double d1;

					for (d1 = ep.posZ - hitent.posZ; d0 * d0 + d1 * d1 < 1.0E-4D; d1 = (Math.random() - Math.random()) * 0.01D)
					{
						d0 = (Math.random() - Math.random()) * 0.01D;
					}
					((EntityLiving)hitent).knockBack(ep, 0	, d0, d1);
				}
				is.damageItem(AbilityRegistry.swing.damageWeapon, ep);
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
		CauseDamageBoundingbox.causeDamage(cause, helper.world, aabb, AbilityRegistry.grassHopper.hurtHp
				, DamageSource.causePlayerDamage(helper.ownerSkill), false);

		

		if(helper.ownerSkill.isSneaking()){
			ScanHelper scan = new ScanHelper(helper.usepoint.x,helper.usepoint.y,helper.usepoint.z,3,3);
			scan.setWorld(helper.world);
			for(;scan.hasNext();scan.next()){
				if(idslist.contains(scan.getID()) || Block.blocksList[scan.getID()] instanceof BlockCrops){
					Block block = Block.blocksList[scan.getID()];
					block.onBlockDestroyedByPlayer(helper.world, scan.sx, scan.sy, scan.sz, scan.getMetadata());
					//block.dropBlockAsItem(world, scan.sx, scan.sy, scan.sz, scan.getMetadata(), en);
					//scan.setBlockHere(0);
					
				}
			}
		}


		//		}
	}
}

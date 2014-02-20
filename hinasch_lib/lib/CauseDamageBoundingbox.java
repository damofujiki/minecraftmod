package hinasch.lib;

import hinasch.mods.unlsaga.misc.ability.skill.effect.SkillEffectHelper;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class CauseDamageBoundingbox {


	public World world;
	public SkillEffectHelper helper;
	public CauseDamageBoundingbox(World world){
		this.world = world;
	}
	
	
	public void setSkillEffectHelper(SkillEffectHelper parent){
		this.helper = parent;
	}
	public void doCauseDamage(AxisAlignedBB bb,float damage,DamageSource damagesource,boolean isEnemyOnly){
		causeDamage(this,this.world,bb,damage,damagesource,isEnemyOnly);
	}
	
	public static void causeDamage(CauseDamageBoundingbox parent,World world,AxisAlignedBB bb,float damage,DamageSource damagesource,boolean isEnemyOnly){
		Entity damageEntity = damagesource.getEntity();
		if(isEnemyOnly){
			List<EntityMob> moblist = world.getEntitiesWithinAABB(EntityMob.class, bb);
			if(!moblist.isEmpty()){
				for(EntityMob mob:moblist){
					if(mob!=damageEntity){
						parent.attackMob(mob, damagesource, damage);
						if(parent!=null){
							parent.takeEntityLiving(mob,damagesource);
						}
					}


				}
			}
			List<EntityGhast> ghasts = world.getEntitiesWithinAABB(EntityGhast.class, bb);
			if(!ghasts.isEmpty()){
				for(EntityGhast ghast:ghasts){
					if(ghast!=damageEntity){
						parent.attackMob(ghast, damagesource, damage);
						if(parent!=null){
							parent.takeEntityLiving(ghast,damagesource);
						}
					}


					
				}
			}

		}else{
			List<EntityLivingBase> moblist = world.getEntitiesWithinAABB(EntityLivingBase.class, bb);
			if(!moblist.isEmpty()){
				for(EntityLivingBase mob:moblist){
					if(mob!=damageEntity){
						parent.attackMob(mob, damagesource, damage);
						if(parent!=null){
							parent.takeEntityLiving(mob,damagesource);
						}
					}



				}
			}
		}

	}
	
	protected void attackMob(Entity mob,DamageSource ds,float damage){
		if(this.helper!=null){
			this.helper.attack(mob, null);
		}else{
			mob.attackEntityFrom(ds,damage);
		}
		
	}
	public void takeEntityLiving(EntityLivingBase living,DamageSource source){
		
	}
}

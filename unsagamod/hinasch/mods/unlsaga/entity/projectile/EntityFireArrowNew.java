package hinasch.mods.unlsaga.entity.projectile;

import hinasch.mods.unlsaga.misc.util.DamageHelper;
import hinasch.mods.unlsaga.misc.util.DamageSourceUnsaga;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityFireArrowNew extends EntityThrowableBase{

	protected boolean isAmplified = false;
	
	public EntityFireArrowNew(World par1World) {
		super(par1World);
		
	}

	public EntityFireArrowNew(World par1World, EntityLivingBase par2EntityLiving, float par3)
	{
		super(par1World,par2EntityLiving,par3);


	}
	
    public void drawParticles(World par1World, int par2, int par3, int par4, Random par5Random)
    {
    	if(par5Random.nextInt(5)==0){
    		return;
    	}
        int var6=0;
        double var7 = (double)((float)par2 + 0.5F);
        double var9 = (double)((float)par3 + 0.7F);
        double var11 = (double)((float)par4 + 0.5F);
        double var13 = 0.2199999988079071D;
        double var15 = 0.27000001072883606D;

        if (var6 == 1)
        {
            par1World.spawnParticle("smoke", var7 - var15, var9 + var13, var11, 0.0D, 0.0D, 0.0D);
            par1World.spawnParticle("flame", var7 - var15, var9 + var13, var11, 0.0D, 0.0D, 0.0D);
        }
        else if (var6 == 2)
        {
            par1World.spawnParticle("smoke", var7 + var15, var9 + var13, var11, 0.0D, 0.0D, 0.0D);
            par1World.spawnParticle("flame", var7 + var15, var9 + var13, var11, 0.0D, 0.0D, 0.0D);
        }
        else if (var6 == 3)
        {
            par1World.spawnParticle("smoke", var7, var9 + var13, var11 - var15, 0.0D, 0.0D, 0.0D);
            par1World.spawnParticle("flame", var7, var9 + var13, var11 - var15, 0.0D, 0.0D, 0.0D);
        }
        else if (var6 == 4)
        {
            par1World.spawnParticle("smoke", var7, var9 + var13, var11 + var15, 0.0D, 0.0D, 0.0D);
            par1World.spawnParticle("flame", var7, var9 + var13, var11 + var15, 0.0D, 0.0D, 0.0D);
        }
        else
        {
            par1World.spawnParticle("smoke", var7, var9, var11, 0.0D, 0.0D, 0.0D);
            par1World.spawnParticle("flame", var7, var9, var11, 0.0D, 0.0D, 0.0D);
        }
    }
    
    @Override
    public void onUpdate(){
    	super.onUpdate();
    	 this.drawParticles(this.worldObj, (int)this.posX, (int)this.posY, (int)this.posZ, this.worldObj.rand);
    	 if(this.worldObj.isAABBInMaterial(this.boundingBox, Material.water)){
    		 this.setDead();
    	 }
    	 if(this.worldObj.isAABBInMaterial(this.boundingBox, Material.lava) && !this.isAmplified){
    		 this.isAmplified = true;
    		 this.setDamage(this.getDamage()*1.4F);

    	 }   	 
    	 
    }
    
	@Override
	protected void onImpact(MovingObjectPosition var1) {
		this.worldObj.spawnParticle("largeexplode", this.posX, this.posY, this.posZ, 1.0D, 0.0D, 0.0D);
		if(var1.typeOfHit==MovingObjectPosition.MovingObjectType.ENTITY){
			if(var1.entityHit!=null){
				Entity hitEntity = var1.entityHit;
				DamageSourceUnsaga ds = new DamageSourceUnsaga(null,this.getThrower(),1,DamageHelper.Type.MAGIC,this);
				ds.setSubDamageType(DamageHelper.SubType.FIRE);
				ds.setFireDamage().setMagicDamage();
				ds.setDamageBypassesArmor();
				hitEntity.attackEntityFrom(ds, this.getDamage());
				hitEntity.setFire(6);
			}
		}
		this.setDead();
	}
}

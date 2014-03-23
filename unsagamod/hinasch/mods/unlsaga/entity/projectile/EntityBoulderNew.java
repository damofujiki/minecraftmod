package hinasch.mods.unlsaga.entity.projectile;

import hinasch.mods.unlsaga.misc.util.DamageHelper;
import hinasch.mods.unlsaga.misc.util.DamageSourceUnsaga;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityBoulderNew extends EntityThrowableBase{

	public EntityBoulderNew(World par1World) {
		super(par1World);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	public EntityBoulderNew(World par1World, EntityLivingBase par2EntityLiving, float par3)
	{
		super(par1World,par2EntityLiving,par3);
		

	}
	
	@Override
	protected void onImpact(MovingObjectPosition var1) {
		this.worldObj.spawnParticle("largeexplode", this.posX, this.posY, this.posZ, 1.0D, 0.0D, 0.0D);
		if(var1.typeOfHit==MovingObjectPosition.MovingObjectType.ENTITY){
			if(var1.entityHit!=null){
				Entity hitEntity = var1.entityHit;
				DamageSourceUnsaga ds = new DamageSourceUnsaga(null,this.getThrower(),1,DamageHelper.Type.PUNCH,this);
				ds.setSubDamageType(DamageHelper.SubType.NONE);
				ds.setMagicDamage();
				hitEntity.attackEntityFrom(ds, this.getDamage());
				int j = this.getKnockBackModifier();
				Entity attacker = this.getThrower();
				hitEntity.addVelocity((double)(-MathHelper.sin(attacker.rotationYaw * (float)Math.PI / 180.0F) * (float)j * 0.3F), 0.1D, (double)(MathHelper.cos(attacker.rotationYaw * (float)Math.PI / 180.0F) * (float)j * 0.3F));

			}
		}
		this.setDead();
	}
}

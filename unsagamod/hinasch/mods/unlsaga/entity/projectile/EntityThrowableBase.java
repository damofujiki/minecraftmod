package hinasch.mods.unlsaga.entity.projectile;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityThrowableBase extends EntityThrowable implements IProjectile{

	protected final int DRIVE = 20;
	protected float damage = 2.0F;
	protected int knockbackModifier = 0;
	
	public EntityThrowableBase(World par1World) {
		super(par1World);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	public EntityThrowableBase(World par1World, EntityLivingBase par2EntityLiving, float par3)
	{
		super(par1World,par2EntityLiving);
		this.setDrive(par3);

	}
	
	@Override
	protected void onImpact(MovingObjectPosition var1) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
    protected float func_70182_d()
    {
        return this.getDrive() * 1.5F;
    }
	
	public void setDamage(float par1){
		this.damage = par1;
	}
	
	public float getDamage(){
		return this.damage;
	}
	
	public void setKnockBackModifier(int par1){
		this.knockbackModifier = par1;
	}
	
	public int getKnockBackModifier(){
		return this.knockbackModifier;
	}
	@Override
	protected void entityInit()
	{
		this.dataWatcher.addObject(16, Byte.valueOf((byte)0));
		this.getDataWatcher().addObjectByDataType(DRIVE, 3);
		this.getDataWatcher().updateObject(DRIVE, (Float)1.0F);
	}
	


	protected void setDrive(float par1){
		this.getDataWatcher().updateObject(DRIVE, (Float)par1);
		this.getDataWatcher().setObjectWatched(DRIVE);
	}
	
	protected float getDrive(){
		float f = this.getDataWatcher().getWatchableObjectFloat(DRIVE);
		return f;
	}
	
	public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.writeEntityToNBT(par1NBTTagCompound);
		par1NBTTagCompound.setFloat("damage.HP", this.getDamage());
		par1NBTTagCompound.setFloat("drive",this.getDrive());
		par1NBTTagCompound.setInteger("knockbackModifier", this.getKnockBackModifier());

	}
	
	public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
	{

		super.readEntityFromNBT(par1NBTTagCompound);
		this.setDamage(par1NBTTagCompound.getFloat("damage.HP"));
		this.setDrive(par1NBTTagCompound.getFloat("drive"));
		this.setKnockBackModifier(par1NBTTagCompound.getInteger("knockbackModifier"));

	}
}

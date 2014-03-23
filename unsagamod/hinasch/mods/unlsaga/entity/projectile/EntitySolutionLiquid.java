package hinasch.mods.unlsaga.entity.projectile;

import hinasch.lib.HSLibs;
import hinasch.lib.RangeDamageHelper;
import hinasch.lib.WorldHelper;
import hinasch.lib.XYZPos;
import hinasch.mods.unlsaga.Unsaga;
import hinasch.mods.unlsaga.misc.util.DamageHelper;
import hinasch.mods.unlsaga.misc.util.DamageSourceUnsaga;
import hinasch.mods.unlsaga.network.packet.PacketHandlerClientThunder;
import hinasch.mods.unlsaga.network.packet.PacketUtil;
import hinasch.mods.unlsagamagic.misc.spell.Spells;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntitySolutionLiquid extends EntityThrowable implements IProjectile{

	protected float damageHP;
	protected float damageLP;
	protected boolean isThunderCrap = false;
	
	public EntitySolutionLiquid(World par1World) {
		super(par1World);
		this.damageHP = 1.0F;
		this.damageLP = 0.1F;
		// TODO 自動生成されたコンストラクター・スタブ
	}

    public EntitySolutionLiquid(World par1World, EntityLivingBase par2EntityLivingBase)
    {
    	super(par1World,par2EntityLivingBase);
		this.damageHP = 1.0F;
		this.damageLP = 0.1F;
    }
	@Override
	protected void onImpact(MovingObjectPosition mop) {
		if(this.isThunderCrap){
			this.doThunderCrap(mop);
		}else{
			if(mop.entityHit!=null){
				Entity hitEntity = mop.entityHit;
				if(hitEntity!=this.getThrower()){
					if(hitEntity instanceof EntityLivingBase){
						EntityLivingBase living = (EntityLivingBase)hitEntity;
						living.attackEntityFrom(new DamageSourceUnsaga(null,this.getThrower(),damageLP,DamageHelper.Type.MAGIC), damageHP);
					}
				}
			}
		}

		
	}

	private void doThunderCrap(MovingObjectPosition mop) {
		if(mop.typeOfHit==MovingObjectPosition.MovingObjectType.BLOCK || mop.typeOfHit==MovingObjectPosition.MovingObjectType.ENTITY){
			DamageSourceUnsaga ds = new DamageSourceUnsaga(null,this.getThrower(),Spells.thunderCrap.hurtLP,DamageHelper.Type.MAGIC,this);
			ds.setSubDamageType(DamageHelper.SubType.ELECTRIC);
			WorldHelper helper = new WorldHelper(this.worldObj);
			AxisAlignedBB bb = HSLibs.getBounding(XYZPos.entityPosToXYZ(this), 2, 1);
			RangeDamageHelper.causeDamage(worldObj, null, bb, ds, Spells.thunderCrap.hurtHP);

			if(!this.worldObj.isRemote){
			

	            this.worldObj.playSoundEffect(this.posX, this.posY, this.posZ, "ambient.weather.thunder", 10000.0F, 0.8F + this.rand.nextFloat() * 0.2F);
				PacketHandlerClientThunder pt = new PacketHandlerClientThunder(XYZPos.entityPosToXYZ(this));
				Unsaga.packetPipeline.sendToAllAround(pt, PacketUtil.getTargetPointNear(this));
				//液体のキャッチが不安定なのでもうちょっと範囲広げる
				if(helper.findNearMaterial(this.worldObj,Material.water, XYZPos.entityPosToXYZ(this), 15)!=null){
					
					XYZPos pos = helper.findNearMaterial(worldObj,Material.water, XYZPos.entityPosToXYZ(this), 15);
					bb = HSLibs.getBounding(pos, 2, 1);
					RangeDamageHelper.causeDamage(worldObj, null, bb, ds, Spells.thunderCrap.hurtHP);
//					Unsaga.debug("液体発見");
//					PairID compare = new PairID(Blocks.water,0).setCheckMetadata(false);
//					compare.sameBlocks.add(new PairID(Blocks.flowing_water,0).setCheckMetadata(false));
//					
//					
//					ScannerElectricShock shocker = new ScannerElectricShock(compare,pos,this.getThrower());
//					shocker.doScan(this.worldObj, 10);
				}
				
				
			}
				this.setDead();
		}
		
	}
	


	public void setDamage(float hp,float lp){
		this.damageHP = hp;
		this.damageLP = lp;
	}
	
	public void setThunderCrap(){
		this.isThunderCrap = true;
	}
}

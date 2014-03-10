package hinasch.mods.unlsaga.entity.projectile;

import hinasch.lib.PairID;
import hinasch.lib.WorldHelper;
import hinasch.lib.XYZPos;
import hinasch.mods.unlsaga.Unsaga;
import hinasch.mods.unlsaga.misc.util.DamageHelper;
import hinasch.mods.unlsaga.misc.util.DamageSourceUnsaga;
import hinasch.mods.unlsaga.network.packet.PacketHandlerClientThunder;
import hinasch.mods.unlsaga.network.packet.PacketUtil;
import hinasch.mods.unlsagamagic.misc.spell.effect.ScannerElectricShock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
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
//			XYZPos pos = null;
//			switch(mop.typeOfHit){
//			case BLOCK:
//				pos = new XYZPos(mop.blockX,mop.blockY,mop.blockZ);
//				break;
//			case ENTITY:
//				pos = XYZPos.entityPosToXYZ(mop.entityHit);
//				break;
//			default:
//				return;
//			}
			WorldHelper helper = new WorldHelper(this.worldObj);
			EntityLightningBolt bolt = new EntityLightningBolt(this.worldObj,this.posX,this.posY,this.posZ);
			if(!this.worldObj.isRemote){
			
				this.worldObj.spawnEntityInWorld(bolt);
				PacketHandlerClientThunder pt = new PacketHandlerClientThunder(XYZPos.entityPosToXYZ(this));
				Unsaga.packetPipeline.sendToAllAround(pt, PacketUtil.getTargetPointNear(this));
				//液体のキャッチが不安定なのでもうちょっと範囲広げる
				if(helper.findNearMaterial(this.worldObj,Material.water, XYZPos.entityPosToXYZ(this), 3)!=null){
					XYZPos pos = helper.findNearMaterial(worldObj,Material.water, XYZPos.entityPosToXYZ(this), 3);
					Unsaga.debug("液体発見");
					PairID compare = new PairID(Blocks.water,0).setCheckMetadata(false);
					compare.sameBlocks.add(new PairID(Blocks.flowing_water,0).setCheckMetadata(false));
					
					
					ScannerElectricShock shocker = new ScannerElectricShock(compare,pos,this.getThrower());
					shocker.doScan(this.worldObj, 7);
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

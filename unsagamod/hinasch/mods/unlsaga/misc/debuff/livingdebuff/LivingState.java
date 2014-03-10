package hinasch.mods.unlsaga.misc.debuff.livingdebuff;

import hinasch.lib.HSLibs;
import hinasch.lib.XYZPos;
import hinasch.mods.unlsaga.Unsaga;
import hinasch.mods.unlsaga.item.weapon.ItemSwordUnsaga;
import hinasch.mods.unlsaga.misc.ability.AbilityRegistry;
import hinasch.mods.unlsaga.misc.ability.skill.effect.SkillEffectHelper;
import hinasch.mods.unlsaga.misc.debuff.Debuff;
import hinasch.mods.unlsaga.misc.debuff.Debuffs;
import hinasch.mods.unlsaga.misc.util.UtilItem;
import hinasch.mods.unlsaga.misc.util.rangedamage.CauseKnockBack;

import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;

public class LivingState extends LivingDebuff{

	public boolean isOnlyAir = false;
	
	public LivingState(Debuff par1, int par2,boolean par3) {
		super(par1, par2);
		this.isOnlyAir = par3;
		// TODO 自動生成されたコンストラクター・スタブ
	}

	public String toString(){
		return this.debuff.number+":"+this.remain+":"+(this.isOnlyAir ? "0" : "-1");
	}
	
	@Override
	public void updateRemain(EntityLivingBase living){
		Unsaga.debug(this.debuff+"updateremain");
		this.remain -= 1;
		if(this.remain<=0){
			this.remain = 0;
		}
		if(this.isOnlyAir && living.onGround){
			this.remain = 0;
			Unsaga.debug("地面にいますので消します");
		}
	}
	
	@Override
	public void updateTick(EntityLivingBase living) {
		super.updateTick(living);
		if(this.debuff==Debuffs.sleep){
			living.setVelocity(0, 0, 0);
		}
		if(this.debuff==Debuffs.gust){
			this.remain -= 1;
			living.moveForward = 0.5F;
			AxisAlignedBB bb = HSLibs.getBounding(XYZPos.entityPosToXYZ(living), 2.0D, 1.0D);
			List<EntityLivingBase> livings = living.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, bb);
			if(!livings.isEmpty()){
				for(EntityLivingBase lv:livings){
					if(lv!=living){
						lv.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer)living),AbilityRegistry.gust.hurtHp);

						this.remain = 0;
					}
				}
				
			}
		}
		if(this.debuff==Debuffs.rushBlade){
			
			if(living instanceof EntityPlayer){
				EntityPlayer ep = (EntityPlayer)living;

				if(ep.motionX + ep.motionZ <= 0.0001D){
					this.remain = 0;
				}
				if(UtilItem.hasItemInstance(ep, ItemSwordUnsaga.class)){
					
					ep.setSneaking(true);
					SkillEffectHelper helper = new SkillEffectHelper(ep.worldObj,ep,AbilityRegistry.chargeBlade,ep.getHeldItem());
					CauseKnockBack causeknock = new CauseKnockBack(ep.worldObj,1.0D);
					AxisAlignedBB bb = ep.boundingBox
							.expand(1.5D, 1.0D, 1.5D);
					causeknock.setLPDamage(helper.getAttackDamageLP());
					causeknock.doCauseDamage(bb, helper.getAttackDamage(), DamageSource.causePlayerDamage(ep), false);
					
				
				}else{
					this.remain = 0;
				}

			}

		}
	}
}

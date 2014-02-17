package hinasch.mods.unlsaga.misc.util;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentThorns;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityMultiPart;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.boss.EntityDragonPart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.stats.AchievementList;
import net.minecraft.stats.StatList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import cpw.mods.fml.client.FMLClientHandler;

public class UtilItem {

	private static Minecraft mc = FMLClientHandler.instance().getClient();
	
	public static boolean hasItemInstance(EntityPlayer player,Class _class){
		if(player.getHeldItem()!=null){
			if(player.getHeldItem().getItem().getClass().isInstance(_class)){
				return true;
			}
			if(player.getHeldItem().getItem().getClass()==_class){
				return true;
			}
		}
		return false;
		
	}

	public static MovingObjectPosition getMouseOver(){
		Entity pointedEntity = null;
		EntityLiving pointedEntityLiving = null;
		MovingObjectPosition objectMouseOver = null;

		if(mc.renderViewEntity!=null){
			if(mc.theWorld!=null){
				pointedEntityLiving=null;
				double d0 = (double)mc.playerController.getBlockReachDistance();
				objectMouseOver = mc.renderViewEntity.rayTrace(d0, 1.0F);
				double d1 = d0;
				Vec3 vec3 = mc.renderViewEntity.getPosition(1.0F);

				//				if (mc.playerController.extendedReach())
				//				{
				//					d0 = 6.0D;
				//					d1 = 6.0D;
				//				}
				//				else
				//				{
				//					if (d0 > 3.0D)
				//					{
				//						d1 = 3.0D;
				//					}
				//
				//					d0 = d1;
				//				}

				d0=20.0D;
				d1=20.0D;

				if (objectMouseOver != null)
				{
					d1 = objectMouseOver.hitVec.distanceTo(vec3);
				}

				Vec3 vec31 = mc.renderViewEntity.getLook(1.0F);
				Vec3 vec32 = vec3.addVector(vec31.xCoord * d0, vec31.yCoord * d0, vec31.zCoord * d0);
				pointedEntity = null;
				float f1 = 1.0F;
				List list = mc.theWorld.getEntitiesWithinAABBExcludingEntity(mc.renderViewEntity, mc.renderViewEntity.boundingBox.addCoord(vec31.xCoord * d0, vec31.yCoord * d0, vec31.zCoord * d0).expand((double)f1, (double)f1, (double)f1));
				double d2 = d1;

				for (int i = 0; i < list.size(); ++i)
				{
					Entity entity = (Entity)list.get(i);

					if (entity.canBeCollidedWith())
					{
						float f2 = entity.getCollisionBorderSize();
						AxisAlignedBB axisalignedbb = entity.boundingBox.expand((double)f2, (double)f2, (double)f2);
						MovingObjectPosition movingobjectposition = axisalignedbb.calculateIntercept(vec3, vec32);

						if (axisalignedbb.isVecInside(vec3))
						{
							if (0.0D < d2 || d2 == 0.0D)
							{
								pointedEntity = entity;
								d2 = 0.0D;
							}
						}
						else if (movingobjectposition != null)
						{
							double d3 = vec3.distanceTo(movingobjectposition.hitVec);

							if (d3 < d2 || d2 == 0.0D)
							{
								pointedEntity = entity;
								d2 = d3;
							}
						}
					}
				}

				if (pointedEntity != null && (d2 < d1 || objectMouseOver == null))
				{
					objectMouseOver = new MovingObjectPosition(pointedEntity);

					if (pointedEntity instanceof EntityLiving)
					{
						pointedEntityLiving = (EntityLiving)pointedEntity;
					}
				}
			}
		}


		return objectMouseOver;
	}

    public static void playerAttackEntityWithItem(EntityPlayer ep,Entity par1Entity,int additionalDamage,float damageChange)
    {
        if (MinecraftForge.EVENT_BUS.post(new AttackEntityEvent(ep, par1Entity)))
        {
            return;
        }
        ItemStack stack = ep.getCurrentEquippedItem();
//        if (stack != null && stack.getItem().onLeftClickEntity(stack, this, par1Entity))
//        {
//            return;
//        }
        if (par1Entity.canAttackWithItem())
        {
            if (!par1Entity.hitByEntity(ep))
            {
                float attackDamage = ((float)ep.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue() * damageChange) + additionalDamage;
                int i = 0;
                float f1 = 0.0F;

                if (par1Entity instanceof EntityLivingBase)
                {
                    f1 = EnchantmentHelper.getEnchantmentModifierLiving(ep, (EntityLivingBase)par1Entity);
                    i += EnchantmentHelper.getKnockbackModifier(ep, (EntityLivingBase)par1Entity);
                }

                if (ep.isSprinting())
                {
                    ++i;
                }

                if (attackDamage > 0.0F || f1 > 0.0F)
                {
                    boolean flag = ep.fallDistance > 0.0F && !ep.onGround && !ep.isOnLadder() && !ep.isInWater() && !ep.isPotionActive(Potion.blindness) && ep.ridingEntity == null && par1Entity instanceof EntityLivingBase;

                    if (flag && attackDamage > 0.0F)
                    {
                        attackDamage *= 1.5F;
                    }

                    attackDamage += f1;
                    boolean flag1 = false;
                    int j = EnchantmentHelper.getFireAspectModifier(ep);

                    if (par1Entity instanceof EntityLivingBase && j > 0 && !par1Entity.isBurning())
                    {
                        flag1 = true;
                        par1Entity.setFire(1);
                    }

                    
                    boolean flag2 = par1Entity.attackEntityFrom(DamageSource.causePlayerDamage(ep), attackDamage);

                    if (flag2)
                    {
                        if (i > 0)
                        {
                            par1Entity.addVelocity((double)(-MathHelper.sin(ep.rotationYaw * (float)Math.PI / 180.0F) * (float)i * 0.5F), 0.1D, (double)(MathHelper.cos(ep.rotationYaw * (float)Math.PI / 180.0F) * (float)i * 0.5F));
                            ep.motionX *= 0.6D;
                            ep.motionZ *= 0.6D;
                            ep.setSprinting(false);
                        }

                        if (flag)
                        {
                        	ep.onCriticalHit(par1Entity);
                        }

                        if (f1 > 0.0F)
                        {
                        	ep.onEnchantmentCritical(par1Entity);
                        }

                        if (attackDamage >= 18.0F)
                        {
                        	ep.triggerAchievement(AchievementList.overkill);
                        }

                        ep.setLastAttacker(par1Entity);

                        if (par1Entity instanceof EntityLivingBase)
                        {
                            EnchantmentThorns.func_92096_a(ep, (EntityLivingBase)par1Entity, ep.getRNG());
                        }
                    }

                    ItemStack itemstack = ep.getCurrentEquippedItem();
                    Object object = par1Entity;

                    if (par1Entity instanceof EntityDragonPart)
                    {
                        IEntityMultiPart ientitymultipart = ((EntityDragonPart)par1Entity).entityDragonObj;

                        if (ientitymultipart != null && ientitymultipart instanceof EntityLivingBase)
                        {
                            object = (EntityLivingBase)ientitymultipart;
                        }
                    }

                    if (itemstack != null && object instanceof EntityLivingBase)
                    {
                        itemstack.hitEntity((EntityLivingBase)object, ep);

                        if (itemstack.stackSize <= 0)
                        {
                        	ep.destroyCurrentEquippedItem();
                        }
                    }

                    if (par1Entity instanceof EntityLivingBase)
                    {
                    	ep.addStat(StatList.damageDealtStat, Math.round(attackDamage * 10.0F));

                        if (j > 0 && flag2)
                        {
                            par1Entity.setFire(j * 4);
                        }
                        else if (flag1)
                        {
                            par1Entity.extinguish();
                        }
                    }

                    ep.addExhaustion(0.3F);
                }
            }
        }
    }


}

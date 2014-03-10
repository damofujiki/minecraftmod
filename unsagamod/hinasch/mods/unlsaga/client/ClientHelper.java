package hinasch.mods.unlsaga.client;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import cpw.mods.fml.client.FMLClientHandler;

public class ClientHelper {
	
	public static Minecraft mc;
	
	
	public static MovingObjectPosition getMouseOver(){
		Entity pointedEntity = null;
		Entity newPointedEntity = null;
		EntityLivingBase pointedEntityLiving = null;
		MovingObjectPosition objectMouseOver = null;
		//Entity pointedEntity = null;
		if(mc==null){
			mc = FMLClientHandler.instance().getClient();
		}

		 if (mc.renderViewEntity != null)
	        {
	            if (mc.theWorld != null)
	            {
	            	newPointedEntity = null;
	                double d0 = 20.0D;//(double)mc.playerController.getBlockReachDistance();
	                objectMouseOver = mc.renderViewEntity.rayTrace(d0, 1.0F);
	                double d1 = d0;
	                Vec3 vec3 = mc.renderViewEntity.getPosition(1.0F);

//	                if (mc.playerController.extendedReach())
//	                {
//	                    d0 = 20.0D;
//	                    d1 = 20.0D;
//	                }
//	                else
//	                {
////	                    if (d0 > 3.0D)
////	                    {
////	                        d1 = 3.0D;
////	                    }
//
//	                    d0 = d1;
//	                }

	                if (objectMouseOver != null)
	                {
	                    d1 = objectMouseOver.hitVec.distanceTo(vec3);
	                }

	                Vec3 vec31 = mc.renderViewEntity.getLook(1.0F);
	                Vec3 vec32 = vec3.addVector(vec31.xCoord * d0, vec31.yCoord * d0, vec31.zCoord * d0);
	                pointedEntity = null;
	                Vec3 vec33 = null;
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
	                                vec33 = movingobjectposition == null ? vec3 : movingobjectposition.hitVec;
	                                d2 = 0.0D;
	                            }
	                        }
	                        else if (movingobjectposition != null)
	                        {
	                            double d3 = vec3.distanceTo(movingobjectposition.hitVec);

	                            if (d3 < d2 || d2 == 0.0D)
	                            {
	                                if (entity == mc.renderViewEntity.ridingEntity && !entity.canRiderInteract())
	                                {
	                                    if (d2 == 0.0D)
	                                    {
	                                        pointedEntity = entity;
	                                        vec33 = movingobjectposition.hitVec;
	                                    }
	                                }
	                                else
	                                {
	                                    pointedEntity = entity;
	                                    vec33 = movingobjectposition.hitVec;
	                                    d2 = d3;
	                                }
	                            }
	                        }
	                    }
	                }

	                if (pointedEntity != null && (d2 < d1 || objectMouseOver == null))
	                {
	                    objectMouseOver = new MovingObjectPosition(pointedEntity, vec33);

	                    if (pointedEntity instanceof EntityLivingBase || pointedEntity instanceof EntityItemFrame)
	                    {
	                    	newPointedEntity = pointedEntity;
	                    }
	                }
	            }
	        }

		return objectMouseOver;
	}
}

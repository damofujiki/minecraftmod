package hinasch.mods.creativeitems.lib;

import java.lang.reflect.Field;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.INpc;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class HSLibs {

	public static HSLibs instance;

	public static void langSet(String par1,String par3,Object par2){
		//System.out.println(par1+":"+par3+":"+par2);
		LanguageRegistry.addName(par2, par1);
		LanguageRegistry.instance().addNameForObject(par2, "ja_JP", par3);
		return;
	}

	public static boolean isEntityLittleMaidAvatar(EntityLiving entity){
		if(entity==null)return false;
		String clsname = entity.getClass().getSimpleName();
		if(clsname.equals("LMM_EntityLittleMaidAvatar")){
			HSLibs.log(0, "entity is LMMAVATAR");
			return true;
		}
		HSLibs.log(0, "entity is not LMMAVATAR");
		HSLibs.log(0, clsname);
		return false;
	}

	public static boolean isEntityLittleMaidAndFortune(EntityLiving entity){
		if(isEntityLittleMaidAvatar(entity)){
			if(entity.getRNG().nextInt(3)==0){
				return true;
			}
		}
		return false;
	}

	public static EntityLiving getLMMFromAvatar(EntityLiving entity){
		if(entity==null)return null;
		//avatarが渡される
		if(isEntityLittleMaidAvatar(entity)){
			Class avatarcls = entity.getClass();
			try {
				Field lmmfield = avatarcls.getDeclaredField("avatar");
				try {
					EntityLiving el = (EntityLiving)lmmfield.get(entity);
					return el;
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
	public static boolean isSide(int side){

		if(side!=0 && side!=1){
			return true;
		}
		return false;
	}
	//	public void registerTools(Block block){
	//		GameRegistry.addRecipe(new ItemStack(), new Object[]{" S "," S "," W ",
	//			Character.valueOf('S'),new ItemStack(Block.oreIron,1),Character.valueOf('W'),new ItemStack(Item.stick,1)});
	//	}

	public static int exceptZero(int par1){

		if(par1==0){
			return 1;
		}


		return par1;

	}

	public static void putSmokeParticle(World world,double x,double y,double z,int sx,int sy,int sz){
        double d0 = (double)((float)sx + world.rand.nextFloat());
        double d1 = (double)((float)sy + world.rand.nextFloat());
        double d2 = (double)((float)sz + world.rand.nextFloat());
        double d3 = d0 - x;
        double d4 = d1 - y;
        double d5 = d2 - z;
        double d6 = (double)MathHelper.sqrt_double(d3 * d3 + d4 * d4 + d5 * d5);
        d3 /= d6;
        d4 /= d6;
        d5 /= d6;
        double d7 = 0.5D / (d6 / (double)1.0D+ 0.1D);
        d7 *= (double)(world.rand.nextFloat() * world.rand.nextFloat() + 0.3F);
        d3 *= d7;
        d4 *= d7;
        d5 *= d7;
        world.spawnParticle("smoke", d0, d1, d2, d3, d4, d5);
	}
    public static MovingObjectPosition getMovingObjectPosition(World par1World, EntityPlayer par2EntityPlayer, boolean par3)
    {
        float f = 1.0F;
        float f1 = par2EntityPlayer.prevRotationPitch + (par2EntityPlayer.rotationPitch - par2EntityPlayer.prevRotationPitch) * f;
        float f2 = par2EntityPlayer.prevRotationYaw + (par2EntityPlayer.rotationYaw - par2EntityPlayer.prevRotationYaw) * f;
        double d0 = par2EntityPlayer.prevPosX + (par2EntityPlayer.posX - par2EntityPlayer.prevPosX) * (double)f;
        double d1 = par2EntityPlayer.prevPosY + (par2EntityPlayer.posY - par2EntityPlayer.prevPosY) * (double)f + 1.62D - (double)par2EntityPlayer.yOffset;
        double d2 = par2EntityPlayer.prevPosZ + (par2EntityPlayer.posZ - par2EntityPlayer.prevPosZ) * (double)f;
        Vec3 vec3 = par1World.getWorldVec3Pool().getVecFromPool(d0, d1, d2);
        float f3 = MathHelper.cos(-f2 * 0.017453292F - (float)Math.PI);
        float f4 = MathHelper.sin(-f2 * 0.017453292F - (float)Math.PI);
        float f5 = -MathHelper.cos(-f1 * 0.017453292F);
        float f6 = MathHelper.sin(-f1 * 0.017453292F);
        float f7 = f4 * f5;
        float f8 = f3 * f5;
        double d3 = 5.0D;
        if (par2EntityPlayer instanceof EntityPlayerMP)
        {
            d3 = ((EntityPlayerMP)par2EntityPlayer).theItemInWorldManager.getBlockReachDistance();
        }
        Vec3 vec31 = vec3.addVector((double)f7 * d3, (double)f6 * d3, (double)f8 * d3);
        return par1World.rayTraceBlocks_do_do(vec3, vec31, par3, !par3);
    }

    public static XYZPos getSideHitPos(XYZPos xyz,MovingObjectPosition mop){
    	XYZPos newxyz = xyz;

        if (mop.sideHit == 0)
        {
            newxyz.y--;
        }

        if (mop.sideHit == 1)
        {
            newxyz.y++;
        }

        if (mop.sideHit == 2)
        {
            newxyz.z--;
        }

        if (mop.sideHit == 3)
        {
            newxyz.z++;
        }

        if (mop.sideHit == 4)
        {
            newxyz.x--;
        }

        if (mop.sideHit == 5)
        {
            newxyz.x++;
        }
        return newxyz;
    }
	public static synchronized HSLibs getInstance(){
		if (instance == null) {
			instance = new HSLibs();
		}
		return instance;
	}
	public static void logc(EntityPlayer par3,String par1){
		World world = par3.worldObj;
		if(!world.isRemote){
			par3.addChatMessage(par1);
		}
	}

	public static void log(int par1,String par2){
		System.out.println("int:"+par1);
		System.out.println("string:"+par2);
	}

	public static void dropItem(World world,ItemStack itemstack,double x,double y,double z){
		EntityItem item = new EntityItem(world, x,y,z,itemstack);
		if(!world.isRemote){
			world.spawnEntityInWorld(item);
		}
		return;
	}


	public static int getDamageFromColorName(String var1){
		int result = -1;
		for(int i=0;i<ItemDye.dyeColorNames.length;i++){
			if(ItemDye.dyeColorNames[i].equals(var1)){
				result = i;
			}
		}
		System.out.println(result);
		return result;
	}

	public static void addPotionIfLiving(Entity entity,PotionEffect potionEffect){
		if(entity instanceof EntityLiving){
			EntityLiving el = (EntityLiving)entity;
			el.addPotionEffect(potionEffect);
		}
	}

	public static AxisAlignedBB getBounding(int x,int y,int z,double range,double rangeY){


		AxisAlignedBB aabb = AxisAlignedBB.getBoundingBox((double)x-range,(double)y-rangeY , (double)z-range,
				(double)x+range, (double)y+rangeY, (double)z+range);
		return aabb;
	}

	public static AxisAlignedBB getBounding(double x,double y,double z,double range,double rangeY){


		AxisAlignedBB aabb = AxisAlignedBB.getBoundingBox((double)x-range,(double)y-rangeY , (double)z-range,
				(double)x+range, (double)y+rangeY, (double)z+range);
		return aabb;
	}

	public static boolean isEnemy(Entity par1,Entity player){
		if(par1!=player && !(par1 instanceof EntityTameable) && !(par1 instanceof INpc)){
			if(par1 instanceof EntityLiving){
				return true;
			}

		}
		return false;
	}

	public static boolean isWood(int id){
		boolean var1 = false;
		if(id==Block.wood.blockID){
			var1 = true;
		}
		int oreid = OreDictionary.getOreID(new ItemStack(Block.blocksList[id]));
		if(OreDictionary.getOreName(oreid)!=null){
			if(OreDictionary.getOreName(oreid).equals("logWood")){
				var1 = true;
			}
		}
		return var1;
	}

    @SideOnly(Side.CLIENT)
    public static void spawnParticleHappy(World par0World, int par1, int par2, int par3, int par4,Random itemRand)
    {
        int i1 = par0World.getBlockId(par1, par2, par3);

        if (par4 == 0)
        {
            par4 = 15;
        }

        Block block = i1 > 0 && i1 < Block.blocksList.length ? Block.blocksList[i1] : null;

        if (block != null)
        {
            block.setBlockBoundsBasedOnState(par0World, par1, par2, par3);

            for (int j1 = 0; j1 < par4; ++j1)
            {
                double d0 = itemRand.nextGaussian() * 0.02D;
                double d1 = itemRand.nextGaussian() * 0.02D;
                double d2 = itemRand.nextGaussian() * 0.02D;
                par0World.spawnParticle("happyVillager", (double)((float)par1 + itemRand.nextFloat()), (double)par2 + (double)itemRand.nextFloat() * block.getBlockBoundsMaxY(), (double)((float)par3 + itemRand.nextFloat()), d0, d1, d2);
            }
        }
        else
        {
            for (int j1 = 0; j1 < par4; ++j1)
            {
                double d0 = itemRand.nextGaussian() * 0.02D;
                double d1 = itemRand.nextGaussian() * 0.02D;
                double d2 = itemRand.nextGaussian() * 0.02D;
                par0World.spawnParticle("happyVillager", (double)((float)par1 + itemRand.nextFloat()), (double)par2 + (double)itemRand.nextFloat() * 1.0f, (double)((float)par3 + itemRand.nextFloat()), d0, d1, d2);
            }
        }
    }
}

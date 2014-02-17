package hinasch.mods.unlsaga.misc.ability.skill.effect;

import hinasch.lib.HSLibs;
import hinasch.lib.PairID;
import hinasch.lib.XYZPos;
import hinasch.mods.unlsaga.core.init.UnsagaBlocks;
import hinasch.mods.unlsaga.entity.EntityFlyingAxe;
import hinasch.mods.unlsaga.item.weapon.ItemAxeUnsaga;
import hinasch.mods.unlsaga.misc.ability.AbilityRegistry;
import hinasch.mods.unlsaga.misc.debuff.LivingDebuff;
import hinasch.mods.unlsaga.misc.util.UtilItem;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class SkillAxe extends SkillEffect{

	public int doWoodChopper(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, XYZPos xyz){
		int amount = 0;
		int fortune = EnchantmentHelper.getFortuneModifier(par2EntityPlayer);
		par2EntityPlayer.playSound("mob.wither.shoot", 0.5F, 1.8F / (par2EntityPlayer.getRNG().nextFloat() * 0.4F + 1.2F));
		int bid = par3World.getBlockId(xyz.x, xyz.y, xyz.z);
		int meta = par3World.getBlockMetadata(xyz.x, xyz.y, xyz.z);
		PairID blockdata = new PairID(par3World.getBlockId(xyz.x, xyz.y, xyz.z),par3World.getBlockMetadata(xyz.x, xyz.y, xyz.z));
		if(par3World.getBlockMaterial(xyz.x, xyz.y, xyz.z)==Material.wood){
			this.breakWood(par1ItemStack,par3World,par2EntityPlayer, blockdata, xyz.x, xyz.y, xyz.z);
		}
		return amount;
	}
	
	private void breakWood(ItemStack axe,World world,EntityPlayer ep,PairID blockwooddata,int x,int y,int z){
		Block block = Block.blocksList[blockwooddata.id];
		block.dropBlockAsItem(world, x, y, z, blockwooddata.metadata, 0);
		world.setBlockToAir(x, y, z);
		PairID thisblock = new PairID(world.getBlockId(x, y+1, z),world.getBlockMetadata(x, y+1, z));
		if(blockwooddata.equals(thisblock)){
			this.breakWood(axe,world,ep,blockwooddata,x,y+1,z);
			axe.damageItem(1, ep);
		}
		return;

	}
	
	public void doFujiView(EntityPlayer ep,Entity entity,World world){

			world.createExplosion(ep, entity.posX, entity.posY, entity.posZ, 2.5F,false);

			for(int i=0;i<5;i++){
				for(int j=0;j<5;j++){
					XYZPos ta = XYZPos.entityPosToXYZ(entity);
					ta.x = ta.x -3 + i;
					ta.y = ta.y -1;;
					ta.z = ta.z -3 + j;


					PairID blockdata = new PairID(world.getBlockId(ta.x,ta.y,ta.z),world.getBlockMetadata(ta.x,ta.y,ta.z));
					if(world.getBlockTileEntity(ta.x,ta.y,ta.z)==null && blockdata.id!=Block.obsidian.blockID && blockdata.id!=Block.bedrock.blockID){
						if(world.isAirBlock(ta.x,ta.y+1,ta.z)||world.getBlockMaterial(ta.x,ta.y+2,ta.z)==Material.vine
								||world.getBlockId(ta.x,ta.y+1,ta.z)==Block.snow.blockID){
							if(!world.isRemote)world.setBlockToAir(ta.x,ta.y,ta.z);
							if(blockdata.id==Block.stone.blockID | blockdata.id==Block.cobblestone.blockID){
								blockdata.id = UnsagaBlocks.blockFallStone.blockID;
								blockdata.metadata = 0;
							}
							if(!world.isRemote)world.setBlock(ta.x,ta.y+1,ta.z, blockdata.id, blockdata.metadata, 2);
						}
					}
				}
			}

			for(int i=0;i<3;i++){
				for(int j=0;j<3;j++){
					XYZPos ta = XYZPos.entityPosToXYZ(entity);
					ta.x = ta.x -2 + i;
					ta.y = ta.y -2;
					ta.z = ta.z -2 + j;

					PairID blockdata = new PairID(world.getBlockId(ta.x,ta.y,ta.z),world.getBlockMetadata(ta.x,ta.y,ta.z));
					if(world.getBlockTileEntity(ta.x,ta.y,ta.z)==null && blockdata.id!=Block.obsidian.blockID && blockdata.id!=Block.bedrock.blockID){
						if(world.isAirBlock(ta.x,ta.y+3,ta.z)|world.getBlockMaterial(ta.x,ta.y+3,ta.z)==Material.vine
								||world.getBlockId(ta.x,ta.y+1,ta.z)==Block.snow.blockID){
							if(!world.isRemote){
								world.setBlock(ta.x,ta.y,ta.z, 0, 0, 2);
							}
							
							if(blockdata.id==Block.stone.blockID | blockdata.id==Block.cobblestone.blockID){
								blockdata.id = UnsagaBlocks.blockFallStone.blockID;
								blockdata.metadata = 0;
							}
							if(!world.isRemote){
								world.setBlock(ta.x,ta.y+3,ta.z, blockdata.id, blockdata.metadata, 2);
							}

							
						}
					}
				}
			}
		
	}
	
	public void doSkydrive(EntityPlayer entityPlayer,ItemStack is,int attackdamage, EntityLivingBase target){
		ItemStack isaxe = entityPlayer.getHeldItem();
		if(is.getItem() instanceof ItemAxeUnsaga){
			EntityFlyingAxe entityflyingaxe = new EntityFlyingAxe(entityPlayer.worldObj, entityPlayer, 0.0F,isaxe,true);
			int modifier = (entityPlayer.isPotionActive(Potion.damageBoost) ? 1 : 0) + LivingDebuff.getModifierAttackBuff(entityPlayer);
			entityflyingaxe.setDamage(attackdamage+modifier+AbilityRegistry.skyDrive.hurtHp);
			entityflyingaxe.setTarget(target);
			//entityPlayer.worldObj.playSoundAtEntity(par3EntityPlayer, "random.bow", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + f * 0.5F);



			if (!entityPlayer.worldObj.isRemote)
			{
				entityPlayer.worldObj.spawnEntityInWorld(entityflyingaxe);
				if(entityflyingaxe.getEntityItem()!=null){
					ItemStack aitemstack = null;
					entityPlayer.inventory.mainInventory[entityPlayer.inventory.currentItem] = aitemstack;
				}

			}
		}
	}
	
	
	public void doWoodBreaker(EntityPlayerMP playerMP, XYZPos xyz) {
		if(playerMP.getHeldItem()!=null){
			if(playerMP.getHeldItem().getItem() instanceof ItemAxeUnsaga){
				World world = playerMP.worldObj;
				Random rand = playerMP.getRNG();
				ItemStack is = playerMP.getHeldItem();
				world.playSoundEffect((double)xyz.x, (double)xyz.y, (double)xyz.z, "random.explode", 4.0F, (1.0F + (world.rand.nextFloat() -world.rand.nextFloat()) * 0.2F) * 0.7F);
				world.setBlockToAir(xyz.x, xyz.y, xyz.z);

				AxisAlignedBB aabb = HSLibs.getBounding(xyz.x, xyz.y, xyz.z, 2.0D, 1.0D);
				List<Entity> entlist = world.getEntitiesWithinAABB(EntityLivingBase.class, aabb);
				for(Iterator<Entity> i = entlist.iterator();i.hasNext();){
					Entity ent = i.next();
					if(HSLibs.isEnemy(ent, playerMP)){
						
//						if(Arrays.asList(HelperCreature.woodMonsters).contains(ent.getEntityName().toLowerCase())){
//							UtilItem.playerAttackEntityWithItem(playerMP, ent, 2, 1.0F);
//							UtilSkill.tryLPHurt(40, 1, ent, playerMP);
//						}else{
//							UtilItem.playerAttackEntityWithItem(playerMP, ent, 0, 1.0F);
//							UtilSkill.tryLPHurt(25, 1, ent, playerMP);
//						}
						UtilItem.playerAttackEntityWithItem(playerMP, ent, 0, 1.0F);
					}
				}

				for(int i=0;i<9;i++){
					HSLibs.dropItem(world, new ItemStack(Item.stick), (double)xyz.x + rand.nextGaussian(), (double)xyz.y + rand.nextGaussian()
							, (double)xyz.z + rand.nextGaussian());
				}
				is.damageItem(10, playerMP);
			}

		}
	}
}

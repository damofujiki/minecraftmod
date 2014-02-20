package hinasch.mods.unlsaga.misc.ability.skill.effect;

import hinasch.lib.HSLibs;
import hinasch.lib.PairID;
import hinasch.lib.XYZPos;
import hinasch.mods.unlsaga.core.init.UnsagaBlocks;
import hinasch.mods.unlsaga.entity.EntityFlyingAxe;
import hinasch.mods.unlsaga.item.weapon.ItemAxeUnsaga;
import hinasch.mods.unlsaga.misc.ability.AbilityRegistry;
import hinasch.mods.unlsaga.misc.debuff.DebuffRegistry;
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

	@Override
	public void selector(SkillEffectHelper helper){
		if(helper.skill==AbilityRegistry.woodChopper)this.doWoodChopper(helper);
		if(helper.skill==AbilityRegistry.skyDrive)this.doSkydrive(helper);

	}
	public int doWoodChopper(SkillEffectHelper parent){
		EntityPlayer ep = parent.ownerSkill;
		XYZPos po = parent.usepoint;
		int amount = 0;
		int fortune = EnchantmentHelper.getFortuneModifier(ep);
		ep.playSound("mob.wither.shoot", 0.5F, 1.8F / (ep.getRNG().nextFloat() * 0.4F + 1.2F));
		int bid = parent.world.getBlockId(po.x, po.y, po.z);
		int meta = parent.world.getBlockMetadata(po.x, po.y, po.z);
		PairID blockdata = new PairID(parent.world.getBlockId(po.x, po.y, po.z),
				parent.world.getBlockMetadata(po.x, po.y, po.z));
		if(parent.world.getBlockMaterial(po.x, po.y, po.z)==Material.wood){
			this.breakWood(parent, blockdata, po.x, po.y, po.z);
		}
		return amount;
	}

	private void breakWood(SkillEffectHelper parent,PairID blockwooddata,int x,int y,int z){
		Block block = Block.blocksList[blockwooddata.id];
		parent.world.playAuxSFX(2001, x,y,z, blockwooddata.id + (blockwooddata.metadata  << 12));
		if(!parent.world.isRemote){
			boolean flag = parent.world.setBlockToAir(x,y,z);
			if (block != null && flag) {
				block.onBlockDestroyedByPlayer(parent.world, x,y,z, blockwooddata.metadata);
				block.dropBlockAsItem(parent.world, x,y,z, blockwooddata.metadata,1);
			}
		}
		PairID thisblock = new PairID(parent.world.getBlockId(x, y+1, z),parent.world.getBlockMetadata(x, y+1, z));
		if(blockwooddata.equals(thisblock)){
			this.breakWood(parent,blockwooddata,x,y+1,z);
			parent.weaponGained.damageItem(1, parent.ownerSkill);
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

	public void doSkydrive(SkillEffectHelper parent){

		LivingDebuff.removeDebuff(parent.ownerSkill, DebuffRegistry.flyingAxe);
		EntityFlyingAxe entityflyingaxe = new EntityFlyingAxe(parent.world, parent.ownerSkill, 0.0F,parent.weaponGained,true);
		int modifier = (parent.ownerSkill.isPotionActive(Potion.damageBoost) ? 1 : 0) + LivingDebuff.getModifierAttackBuff(parent.ownerSkill);
		entityflyingaxe.setDamage(parent.getAttackDamage()+modifier);
		entityflyingaxe.setTarget(parent.target);
		//entityPlayer.worldObj.playSoundAtEntity(par3EntityPlayer, "random.bow", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + f * 0.5F);



		if (!parent.world.isRemote)
		{
			parent.world.spawnEntityInWorld(entityflyingaxe);
			if(entityflyingaxe.getEntityItem()!=null){
				ItemStack aitemstack = null;
				parent.ownerSkill.inventory.mainInventory[parent.ownerSkill.inventory.currentItem] = aitemstack;
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

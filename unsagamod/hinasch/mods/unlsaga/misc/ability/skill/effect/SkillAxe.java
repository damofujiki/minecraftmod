package hinasch.mods.unlsaga.misc.ability.skill.effect;

import hinasch.lib.HSLibs;
import hinasch.lib.PairID;
import hinasch.lib.WorldHelper;
import hinasch.lib.XYZPos;
import hinasch.mods.unlsaga.Unsaga;
import hinasch.mods.unlsaga.core.init.UnsagaBlocks;
import hinasch.mods.unlsaga.entity.projectile.EntityFlyingAxe;
import hinasch.mods.unlsaga.item.weapon.ItemAxeUnsaga;
import hinasch.mods.unlsaga.misc.ability.AbilityRegistry;
import hinasch.mods.unlsaga.misc.debuff.Debuffs;
import hinasch.mods.unlsaga.misc.debuff.livingdebuff.LivingDebuff;
import hinasch.mods.unlsaga.network.packet.PacketParticle;
import hinasch.mods.unlsaga.network.packet.PacketSkill;
import hinasch.mods.unlsaga.network.packet.PacketUtil;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

public class SkillAxe extends SkillEffect{

	public WorldHelper worldHelper;
	public World world;

	@Override
	public void selector(SkillEffectHelper helper){
		this.worldHelper = new WorldHelper(helper.world);
		this.world = helper.world;
		if(helper.skill==AbilityRegistry.woodChopper)this.doWoodChopper(helper);
		
		if(helper.skill==AbilityRegistry.skyDrive)this.doSkydrive(helper);
		if(helper.skill==AbilityRegistry.fujiView)this.doFujiView(helper);
		if(helper.skill==AbilityRegistry.woodBreakerPhoenix)this.doWoodBreaker(helper);

	}
	
	protected void playShootSound(EntityLivingBase ep){
		ep.playSound("mob.wither.shoot", 0.5F, 1.8F / (ep.getRNG().nextFloat() * 0.4F + 1.2F));
	}
	public int doWoodChopper(SkillEffectHelper parent){
		EntityPlayer ep = parent.owner;
		XYZPos po = parent.usepoint;
		int amount = 0;
		int fortune = EnchantmentHelper.getFortuneModifier(ep);
		this.playShootSound(ep);
		PairID blockdata = worldHelper.getBlockDatas(po);

		if(this.worldHelper.getMaterial(po)==Material.wood){
			this.breakWood(parent, blockdata, po);
		}
		return amount;
	}

	private void breakWood(SkillEffectHelper parent,PairID blockwooddata,XYZPos pos){
		Block block = blockwooddata.blockObj;
		HSLibs.playBlockBreakSFX(parent.world, pos, blockwooddata);
		XYZPos upPos = pos.add(worldHelper.UP);
		PairID thisblock = new PairID(worldHelper.getBlock(upPos),worldHelper.getBlockMetadata(upPos));
		if(blockwooddata.equals(thisblock)){
			this.breakWood(parent,blockwooddata,upPos);
			parent.weapon.damageItem(1, parent.owner);
		}
		return;

	}

	
	public void doFujiView(SkillEffectHelper parent){

		World world = parent.world;
		Entity entity = parent.target;
		EntityPlayer ep = parent.owner;
		world.createExplosion(ep, entity.posX, entity.posY, entity.posZ, 2.5F,false);

		Random rand = world.rand;
		for(int i=0;i<10;i++){
			XYZPos ta = XYZPos.entityPosToXYZ(entity);
			XYZPos ppos = new XYZPos(ta.x+rand.nextInt(3)-1,ta.y+(i*3),ta.z+rand.nextInt(3)-1);
			PacketParticle pp = new PacketParticle(ppos,3,6);
			Unsaga.packetPipeline.sendToAllAround(pp, PacketUtil.getTargetPointNear(entity));
			//PacketDispatcher.sendPacketToAllPlayers(PacketHandler.getParticleToPosPacket(pp, 3, 6));
		}
		
//		makeBulge(world,XYZPos.entityPosToXYZ(entity));
		for(int i=0;i<5;i++){
			for(int j=0;j<5;j++){
				XYZPos ta = XYZPos.entityPosToXYZ(entity);
				ta.x = ta.x -3 + i;
				ta.y = ta.y -1;;
				ta.z = ta.z -3 + j;


				PairID blockdata = new PairID(world.getBlock(ta.x,ta.y,ta.z),world.getBlockMetadata(ta.x,ta.y,ta.z));
				if(world.getTileEntity(ta.x,ta.y,ta.z)==null && !HSLibs.isHardBlock(blockdata.blockObj)){
					if(world.isAirBlock(ta.x,ta.y+1,ta.z)||world.getBlock(ta.x,ta.y+1,ta.z).isReplaceable(world, ta.x, ta.y+1, ta.z)){
						if(!world.isRemote){
							world.setBlockToAir(ta.x,ta.y,ta.z);
						}
						blockdata = getAssociatedFallBlock(blockdata);
						
						if(!world.isRemote){
							world.setBlock(ta.x,ta.y+1,ta.z, blockdata.blockObj, blockdata.metadata, 3);
						}
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

				PairID blockdata = new PairID(world.getBlock(ta.x,ta.y,ta.z),world.getBlockMetadata(ta.x,ta.y,ta.z));
				if(world.getTileEntity(ta.x,ta.y,ta.z)==null && !HSLibs.isHardBlock(blockdata.blockObj)){
					if(world.isAirBlock(ta.x,ta.y+3,ta.z)||world.getBlock(ta.x,ta.y+3,ta.z).isReplaceable(world, ta.x, ta.y+3, ta.z)){
						if(!world.isRemote){
							world.setBlockToAir(ta.x,ta.y,ta.z);
						}

						blockdata = getAssociatedFallBlock(blockdata);
						
						if(!world.isRemote){
							world.setBlock(ta.x,ta.y+3,ta.z, blockdata.blockObj, blockdata.metadata, HSLibs.FLAG_SETBLOCK.NORMAL);
						}


					}
				}
			}
		}

		

	}

	protected PairID getAssociatedFallBlock(PairID blockdata){
		PairID ret = new PairID();
		if(blockdata.blockObj==Blocks.stone | blockdata.blockObj==Blocks.cobblestone){
			ret.blockObj = UnsagaBlocks.blockFallStone;
			ret.metadata = 0;
		}
		if(blockdata.blockObj==Blocks.dirt | blockdata.blockObj==Blocks.grass){
			ret.blockObj = UnsagaBlocks.blockFallStone;
			ret.metadata = 3;
		}
		if(blockdata.blockObj==Blocks.netherrack){
			ret.blockObj = UnsagaBlocks.blockFallStone;
			ret.metadata = 5;
		}
		return ret;
	}
	public void doSkydrive(SkillEffectHelper parent){

		LivingDebuff.removeDebuff(parent.owner, Debuffs.flyingAxe);
		EntityFlyingAxe entityflyingaxe = new EntityFlyingAxe(parent.world, parent.owner, 0.0F,parent.weapon,true);
		int modifier = (parent.owner.isPotionActive(Potion.damageBoost) ? 1 : 0) + LivingDebuff.getModifierAttackBuff(parent.owner);
		entityflyingaxe.setDamage(parent.getAttackDamage()+modifier);
		entityflyingaxe.setTarget(parent.target);
		//entityPlayer.worldObj.playSoundAtEntity(par3EntityPlayer, "random.bow", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + f * 0.5F);



		if (!parent.world.isRemote)
		{
			
			if(entityflyingaxe.getEntityItem()!=null){
				parent.world.spawnEntityInWorld(entityflyingaxe);
				ItemStack aitemstack = null;
				//parent.ownerSkill.inventory.setInventorySlotContents(parent.ownerSkill.inventory.currentItem, null);
			}

		}


	}


	public void doWoodBreaker(SkillEffectHelper parent) {
		
		if(parent.owner.getHeldItem()!=null){
			if(parent.owner.getHeldItem().getItem() instanceof ItemAxeUnsaga){
				Random rand = parent.world.rand;
				ItemStack is = parent.weapon;
				XYZPos xyz = parent.usepoint;
				world.playSoundEffect((double)xyz.x, (double)xyz.y, (double)xyz.z, "random.explode", 4.0F, (1.0F + (world.rand.nextFloat() -world.rand.nextFloat()) * 0.2F) * 0.7F);

				PairID pairid = PairID.getBlockFromWorld(world, xyz);
				Block block = pairid.blockObj;
				boolean flag = false;
				if(block instanceof BlockLog)flag = true;
				int oreid = OreDictionary.getOreID(new ItemStack(pairid.blockObj,1,pairid.metadata));
				if(OreDictionary.getOreName(oreid).equals("logWood"))flag=true;
				if(!flag)return;
				HSLibs.playBlockBreakSFX(world, xyz, pairid);
				//parent.world.playAuxSFX(2001, xyz.x,xyz.y,xyz.z, Block.getIdFromBlock(pairid.blockObj) + (pairid.metadata  << 12));
				
//				if(!parent.world.isRemote){
//					boolean flag2 = parent.world.setBlockToAir(xyz.x,xyz.y,xyz.z);
//					if (block != null && flag2) {
//						block.onBlockDestroyedByPlayer(parent.world,xyz.x,xyz.y,xyz.z, pairid.metadata);
//						//block.dropBlockAsItem(parent.world, xyz.x,xyz.y,xyz.z, pairid.metadata,1);
//					}
//				}
				AxisAlignedBB aabb = HSLibs.getBounding(xyz.x, xyz.y, xyz.z, 2.0D, 1.0D);
				PacketSkill ps = new PacketSkill(PacketSkill.PACKETID.WOODBREAKER,xyz);
				Unsaga.packetPipeline.sendToServer(ps);
				//PacketDispatcher.sendPacketToPlayer(PacketHandler.getParticleToPosPacket(xyz, 3, 5), (Player) parent.owner);
				parent.causeRangeDamage(null, world, aabb, parent.getAttackDamage(), DamageSource.causePlayerDamage(parent.owner), false);


				for(int i=0;i<9;i++){
					HSLibs.dropItem(world, new ItemStack(Items.stick), (double)xyz.x + rand.nextGaussian(), (double)xyz.y + rand.nextGaussian()
							, (double)xyz.z + rand.nextGaussian());
				}

			}

		}
	}
}

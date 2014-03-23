package hinasch.mods.unlsaga.misc.ability.skill.effect;

import hinasch.lib.HSLibs;
import hinasch.lib.PairID;
import hinasch.lib.RangeDamageHelper;
import hinasch.lib.ScanHelper;
import hinasch.lib.WorldHelper;
import hinasch.lib.XYZPos;
import hinasch.mods.unlsaga.Unsaga;
import hinasch.mods.unlsaga.core.init.UnsagaBlocks;
import hinasch.mods.unlsaga.entity.projectile.EntityFlyingAxeNew;
import hinasch.mods.unlsaga.item.weapon.ItemAxeUnsaga;
import hinasch.mods.unlsaga.misc.debuff.Debuffs;
import hinasch.mods.unlsaga.misc.debuff.livingdebuff.LivingDebuff;
import hinasch.mods.unlsaga.misc.debuff.livingdebuff.LivingState;
import hinasch.mods.unlsaga.misc.debuff.livingdebuff.LivingStateFlyingAxe;
import hinasch.mods.unlsaga.misc.debuff.livingdebuff.LivingStateTarget;
import hinasch.mods.unlsaga.misc.util.LockOnHelper;
import hinasch.mods.unlsaga.network.packet.PacketParticle;
import hinasch.mods.unlsaga.network.packet.PacketSkill;
import hinasch.mods.unlsaga.network.packet.PacketUtil;

import java.util.Random;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

import com.google.common.collect.Sets;

public class SkillAxe extends SkillEffect{

	public WorldHelper worldHelper;
	public static SkillAxe INSTANCE;

	public static SkillAxe getInstance(){
		if(INSTANCE==null){
			INSTANCE = new SkillAxe();
		}
		return INSTANCE;
	}


	public final SkillMelee fujiView = new SkillFujiView(SkillMelee.Type.ENTITY_LEFTCLICK);
	public final SkillMelee skyDrive = new SkillSkyDrive(SkillMelee.Type.RIGHTCLICK).setRequirePrepare(true).setRequireSneak(false);
	public final SkillMelee woodBreaker = new SkillWoodBreaker(SkillMelee.Type.USE).setRequirePrepare(true);
	public final SkillMelee woodChopper = new SkillWoodChopper(SkillMelee.Type.USE);
	

	public class SkillWoodChopper extends SkillMelee{

		public SkillWoodChopper(Type type) {
			super(type);

		}
		


		@Override
		public void invokeSkill(InvokeSkill parent) {
			parent.owner.swingItem();
			EntityPlayer ep = parent.owner;
			XYZPos po = parent.usepoint;
			int amount = 0;
			int fortune = EnchantmentHelper.getFortuneModifier(ep);
			this.playShootSound(ep);
			PairID blockdata = worldHelper.getBlockDatas(po);

			if(this.worldHelper.getMaterial(po)==Material.wood){
				this.breakWood(parent, blockdata, po);
			}

			return;

		}

		private void breakWood(InvokeSkill parent,PairID blockwooddata,XYZPos pos){
			Block block = blockwooddata.getBlockObject();
			HSLibs.playBlockBreakSFX(parent.world, pos, blockwooddata);
			XYZPos upPos = pos.add(worldHelper.UP);
			PairID thisblock = new PairID(worldHelper.getBlock(upPos),worldHelper.getBlockMetadata(upPos));
			if(blockwooddata.equals(thisblock)){
				this.breakWood(parent,blockwooddata,upPos);
				parent.weapon.damageItem(1, parent.owner);
			}
			return;

		}

	}


	public class SkillFujiView extends SkillMelee{

		Set<Block> blockSet = Sets.newHashSet(Blocks.sandstone,Blocks.gravel,Blocks.grass,Blocks.dirt,
				Blocks.cobblestone,Blocks.stone,Blocks.netherrack,Blocks.sand,UnsagaBlocks.blockFallStone,Blocks.anvil);
		public SkillFujiView(Type type) {
			super(type);
			// TODO 自動生成されたコンストラクター・スタブ
		}

		@Override
		public void invokeSkill(InvokeSkill parent) {
			World world = parent.world;
			EntityLivingBase entity = parent.target;
			EntityPlayer ep = parent.owner;
			parent.attack(entity, null);
			world.createExplosion(ep, entity.posX, entity.posY, entity.posZ, 1.5F,false);
			

			Random rand = world.rand;
			for(int i=0;i<10;i++){
				XYZPos ta = XYZPos.entityPosToXYZ(entity);
				XYZPos ppos = new XYZPos(ta.x+rand.nextInt(3)-1,ta.y+(i*3),ta.z+rand.nextInt(3)-1);
				PacketParticle pp = new PacketParticle(ppos,3,6);
				Unsaga.packetPipeline.sendToAllAround(pp, PacketUtil.getTargetPointNear(entity));
				//PacketDispatcher.sendPacketToAllPlayers(PacketHandler.getParticleToPosPacket(pp, 3, 6));
			}
			ScanHelper scan = new ScanHelper(entity,3,3);
			for(;scan.hasNext();scan.next()){
				XYZPos scanpos = scan.getAsXYZPos();
				PairID blockdata = this.worldHelper.getBlockDatas(scanpos);
				PairID newblockdata = getAssociatedFallBlock(blockdata);
				Unsaga.debug(scan.getAsXYZPos()+":"+blockdata);
				if(blockSet.contains(blockdata.getBlockObject())){
					Unsaga.debug(scanpos.addPos(0, 6, 0));
					
					if(!world.isRemote){
						if(this.worldHelper.isAirBlock(scanpos.addPos(0, 3, 0))){
							this.worldHelper.setBlock(scanpos.addPos(0, 3, 0), newblockdata);
							this.worldHelper.setBlockToAir(scanpos);
						}

					}

				}

				
			}




		}

		protected PairID getAssociatedFallBlock(PairID blockdata){
			PairID ret = new PairID(blockdata.getBlockObject(),blockdata.getMeta());
			if(blockdata.getBlockObject()==Blocks.stone | blockdata.getBlockObject()==Blocks.cobblestone){
				ret.setData(UnsagaBlocks.blockFallStone, 0);
			}
			if(blockdata.getBlockObject()==Blocks.dirt | blockdata.getBlockObject()==Blocks.grass){
				ret.setData(UnsagaBlocks.blockFallStone, 3);
			}
			if(blockdata.getBlockObject()==Blocks.netherrack){
				ret.setData(UnsagaBlocks.blockFallStone, 5);
			}
			return ret;
		}
	}

//	public void doFujiView(InvokeSkill parent){
//
//		World world = parent.world;
//		Entity entity = parent.target;
//		EntityPlayer ep = parent.owner;
//		world.createExplosion(ep, entity.posX, entity.posY, entity.posZ, 2.5F,false);
//
//		Random rand = world.rand;
//		for(int i=0;i<10;i++){
//			XYZPos ta = XYZPos.entityPosToXYZ(entity);
//			XYZPos ppos = new XYZPos(ta.x+rand.nextInt(3)-1,ta.y+(i*3),ta.z+rand.nextInt(3)-1);
//			PacketParticle pp = new PacketParticle(ppos,3,6);
//			Unsaga.packetPipeline.sendToAllAround(pp, PacketUtil.getTargetPointNear(entity));
//			//PacketDispatcher.sendPacketToAllPlayers(PacketHandler.getParticleToPosPacket(pp, 3, 6));
//		}
//
//		//		makeBulge(world,XYZPos.entityPosToXYZ(entity));
//		for(int i=0;i<5;i++){
//			for(int j=0;j<5;j++){
//				XYZPos ta = XYZPos.entityPosToXYZ(entity);
//				ta.add(new XYZPos(-3+i,-1,-3+j));
//				//				ta.x = ta.x -3 + i;
//				//				ta.y = ta.y -1;;
//				//				ta.z = ta.z -3 + j;
//
//
//				PairID blockdata = this.worldHelper.getBlockDatas(ta);
//				if(this.worldHelper.getTileEntity(ta)==null && !HSLibs.isHardBlock(blockdata.getBlockObject())){
//					if(this.worldHelper.isAirBlock(ta.add(worldHelper.UP))||worldHelper.isReplaceable(ta.add(worldHelper.UP))){
//						if(!world.isRemote){
//							worldHelper.setBlockToAir(ta);
//							//world.setBlockToAir(ta.x,ta.y,ta.z);
//						}
//						blockdata = getAssociatedFallBlock(blockdata);
//
//						if(!world.isRemote){
//							worldHelper.setBlock(ta.add(worldHelper.UP), blockdata);
//							//world.setBlock(ta.x,ta.y+1,ta.z, blockdata.getBlockObject(), blockdata.getMeta(), 3);
//						}
//					}
//				}
//			}
//		}
//
//		for(int i=0;i<3;i++){
//			for(int j=0;j<3;j++){
//				XYZPos ta = XYZPos.entityPosToXYZ(entity);
//				ta.addPos(-2+i,-2,-2+j);
//				//				ta.x = ta.x -2 + i;
//				//				ta.y = ta.y -2;
//				//				ta.z = ta.z -2 + j;
//
//				PairID blockdata = this.worldHelper.getBlockDatas(ta);
//				if(this.worldHelper.getTileEntity(ta)==null && !HSLibs.isHardBlock(blockdata.getBlockObject())){
//					if(worldHelper.getTileEntity(ta.addPos(0, +3, 0))==null ||worldHelper.isReplaceable(ta.addPos(0, 3, 0))){
//						if(!world.isRemote){
//							worldHelper.setBlockToAir(ta);
//						}
//
//						blockdata = getAssociatedFallBlock(blockdata);
//
//						if(!world.isRemote){
//							worldHelper.setBlock(ta.addPos(0, 3, 0), blockdata);
//							//world.setBlock(ta.x,ta.y+3,ta.z, blockdata.getBlockObject(), blockdata.getMeta(), HSLibs.FLAG_SETBLOCK.NORMAL);
//						}
//
//
//					}
//				}
//			}
//		}
//
//
//
//	}

	protected PairID getAssociatedFallBlock(PairID blockdata){
		PairID ret = new PairID();
		if(blockdata.getBlockObject()==Blocks.stone | blockdata.getBlockObject()==Blocks.cobblestone){
			ret.setData(UnsagaBlocks.blockFallStone, 0);
		}
		if(blockdata.getBlockObject()==Blocks.dirt | blockdata.getBlockObject()==Blocks.grass){
			ret.setData(UnsagaBlocks.blockFallStone, 3);
		}
		if(blockdata.getBlockObject()==Blocks.netherrack){
			ret.setData(UnsagaBlocks.blockFallStone, 5);
		}
		return ret;
	}

	public class SkillSkyDrive extends SkillMelee{

		public SkillSkyDrive(Type type) {
			super(type);
			// TODO 自動生成されたコンストラクター・スタブ
		}

		@Override
		public boolean canInvoke(World world,EntityPlayer ep,ItemStack is,XYZPos pos){
			if(!ep.onGround){
				return super.canInvoke(world, ep, is, pos);
			}
			return false;
		}
		
		@Override
		public boolean hasFinishedPrepare(InvokeSkill parent){
			//if(!parent.world.isRemote){
				return LivingDebuff.hasDebuff(parent.owner, Debuffs.flyingAxe) && parent.owner.isSneaking();
			//}

			//return false;
		}
		
		@Override
		public void prepareSkill(InvokeSkill parent){

			if(!LivingDebuff.hasDebuff(parent.owner, Debuffs.flyingAxe)){
				this.setReadyToSkyDrive(parent.owner);
			}
		}
		
		@Override
		public void invokeSkill(InvokeSkill parent) {
			LivingDebuff.removeDebuff(parent.owner, Debuffs.flyingAxe);
			EntityLivingBase target = LockOnHelper.searchEntityNear(parent.owner, Debuffs.weaponTarget);
			parent.damageWeapon();
			if(parent.weapon==null){
				return;
			}
			ItemStack copyaxe = parent.weapon.copy();
			
			if(LivingDebuff.getLivingDebuff(parent.owner, Debuffs.weaponTarget).isPresent()){
				LivingStateTarget state = (LivingStateTarget)LivingDebuff.getLivingDebuff(parent.owner, Debuffs.weaponTarget).get();
				target = (EntityLivingBase) parent.world.getEntityByID(state.targetid);
			}
			
			EntityFlyingAxeNew entityflyingaxe = new EntityFlyingAxeNew(parent.world, parent.owner, 0.0F,copyaxe,true);
			entityflyingaxe.setDamage(parent.getModifiedAttackDamage());
			if(target!=null){
				entityflyingaxe.setTarget(target);
			}
			
			


			if (!parent.world.isRemote)
			{

				if(entityflyingaxe.getAxeItemStack()!=null){
					parent.weapon.stackSize --;
					parent.world.spawnEntityInWorld(entityflyingaxe);
					//ItemStack aitemstack = null;
					//parent.ownerSkill.inventory.setInventorySlotContents(parent.ownerSkill.inventory.currentItem, null);
				}

			}


		}
		
		protected void setReadyToSkyDrive(EntityPlayer ep){
			ep.motionY += 1.0;
			LivingDebuff.addLivingDebuff(ep, new LivingStateFlyingAxe(Debuffs.flyingAxe,30,(int)ep.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue()));
			LivingDebuff.addLivingDebuff(ep, new LivingState(Debuffs.antiFallDamage,30,true));
		}

	}


	public class SkillWoodBreaker extends SkillMelee{

		public SkillWoodBreaker(Type type) {
			super(type);
			// TODO 自動生成されたコンストラクター・スタブ
		}
		
		@Override
		public boolean canInvoke(World world,EntityPlayer ep,ItemStack is,XYZPos pos){
			if(!ep.onGround){
				return super.canInvoke(world, ep, is, pos);
			}
			return false;
		}
		
		@Override
		public void prepareSkill(InvokeSkill parent){
			if(parent.world.isRemote){
				parent.owner.swingItem();
				Unsaga.debug("wood breaker:きてます");
				PacketSkill ps = new PacketSkill(PacketSkill.PACKETID.WOODBREAKER,parent.usepoint);
				Unsaga.packetPipeline.sendToServer(ps);
			}

		}
		
		
		@Override
		public void invokeSkill(InvokeSkill parent) {
			if(parent.owner.getHeldItem()!=null){
				if(parent.owner.getHeldItem().getItem() instanceof ItemAxeUnsaga){
					Random rand = parent.world.rand;
					ItemStack is = parent.weapon;
					XYZPos xyz = parent.usepoint;
					Unsaga.debug(xyz);
					
					parent.world.playSoundEffect((double)xyz.x, (double)xyz.y, (double)xyz.z, "random.explode", 4.0F, (1.0F + (parent.world.rand.nextFloat() -parent.world.rand.nextFloat()) * 0.2F) * 0.7F);

					PairID pairid = PairID.getBlockFromWorld(parent.world, xyz);
					Block block = pairid.getBlockObject();
					boolean flag = false;
					if(block instanceof BlockLog)flag = true;
					int oreid = OreDictionary.getOreID(new ItemStack(pairid.getBlockObject(),1,pairid.getMeta()));
					if(OreDictionary.getOreName(oreid).equals("logWood"))flag=true;
					if(!flag)return;
					HSLibs.playBlockBreakSFX(parent.world, xyz, pairid,true);
					RangeDamageHelper.causeDamage(parent.world, null, HSLibs.getBounding(xyz.x, xyz.y, xyz.z, 2.0D, 1.0D)
							, parent.getDamageSource(), parent.getModifiedAttackDamage());

					for(int i=0;i<9;i++){
						HSLibs.dropItem(parent.world, new ItemStack(Items.stick), (double)xyz.x + rand.nextGaussian(), (double)xyz.y + rand.nextGaussian()
								, (double)xyz.z + rand.nextGaussian());
					}

				}

			}


		}

	}


}

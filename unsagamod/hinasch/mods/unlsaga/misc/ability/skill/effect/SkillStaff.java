package hinasch.mods.unlsaga.misc.ability.skill.effect;

import hinasch.mods.unlsaga.Unsaga;
import hinasch.mods.unlsaga.misc.ability.AbilityRegistry;
import hinasch.mods.unlsaga.misc.util.ChatUtil;
import hinasch.mods.unlsaga.misc.util.rangedamage.CauseAddVelocity;
import hinasch.mods.unlsaga.misc.util.rangedamage.CauseExplode;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.hinasch.lib.HSLibs;
import com.hinasch.lib.PairID;
import com.hinasch.lib.RangeDamageHelper;
import com.hinasch.lib.ScanHelper;
import com.hinasch.lib.XYZPos;

import net.minecraft.block.Block;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class SkillStaff extends SkillEffect{

	//private HashSet<Material> canBreakBlocks = Sets.newHashSet(Material.ground,Material.sand,Material.grass,Material.ice,Material.rock);

	//private HashSet<Block> breakBlocks = Sets.newHashSet(Blocks.cobblestone,Blocks.stone,Blocks.whiteStone,Blocks.netherrack,Blocks.sandstone);
	//hardness 2.0F 以下、Material rock ground
	
	public static SkillStaff INSTANCE;
	
	@Override
	public void selector(InvokeSkill parent){
		
		Unsaga.debug("kitemasu");
		if(parent.skill==AbilityRegistry.earthDragon)this.doEarthDragon(parent);
		if(parent.skill==AbilityRegistry.pulvorizer)this.doPulverizer(parent);
		if(parent.skill==AbilityRegistry.grandSlam)this.doGrandSlam(parent);
		if(parent.skill==AbilityRegistry.skullCrash)this.doSkullCrash(parent);
		if(parent.skill==AbilityRegistry.gonger)this.doBellRinger(parent);
		if(parent.skill==AbilityRegistry.rockCrusher)this.doRockcrusher(parent);
	}
	
	public final SkillBase bellRinger = new SkillBellRinger(SkillMelee.Type.USE);
	public final SkillBase earthDragon = new SkillEarthDragon(SkillMelee.Type.USE,8.0D,8.0D);
	public final SkillBase grandSlam = new SkillGrandSlam(SkillMelee.Type.USE,10.0D,3.0D);
	public final SkillBase skullCrusher = new SkillSkullCrusher(SkillMelee.Type.ENTITY_LEFTCLICK);
	public final SkillBase pulverizer = new SkillPulverizer(SkillMelee.Type.USE);
	public final SkillBase rockCrusher = new SkillRockCrusher(SkillMelee.Type.USE);
	
	public class SkillBellRinger extends SkillMelee{

		public SkillBellRinger(Type type) {
			super(type);
			// TODO 自動生成されたコンストラクター・スタブ
		}

		@Override
		public void invokeSkill(InvokeSkill parent) {
			parent.owner.swingItem();
			XYZPos up = parent.usepoint;
			parent.world.playSoundEffect(up.dx, up.dy, up.dz, "random.explode", 4.0F, (1.0F + (parent.owner.worldObj.rand.nextFloat() - parent.owner.worldObj.rand.nextFloat()) * 0.2F) * 0.7F);
			parent.world.spawnParticle("largeexplode", (double)up.dx+0.5D, (double)up.dy+1, (double)up.dz+0.5D, 1.0D, 0.0D, 0.0D);

			boolean sw = false;
			int depth = 40;

			for(int i=0;i<40;i++){
				if(parent.usepoint.y-i>0){
					if(parent.world.isAirBlock(parent.usepoint.x, parent.usepoint.y-i, parent.usepoint.z)){
						if(detectRoom(parent.world,parent.getOwnerEP(),parent.usepoint.x,parent.usepoint.y-i,parent.usepoint.z)){

							sw=true;
						}
					}
				}
			}

			if(sw){
				parent.world.playSound((double)parent.usepoint.x, (double)parent.usepoint.y, (double)parent.usepoint.z, "ambient.cave.cave", 0.7F, 0.8F + parent.world.rand.nextFloat() * 0.2F, false);

			}
			
		}
		
		private boolean detectRoom(World world,EntityPlayer ep,int x,int y,int z){
			int numair=0;
			for(int i=0;i<3;i++){
				for(int j=0;j<3;j++){
					for(int k=0;k<3;k++){
						int xx = x-1+i;
						int yy = y-1+j;
						int zz = z-1+k;
						if(yy>0){
							if(world.isAirBlock(xx, yy, zz)){
								numair+=1;
							}
						}
					}
				}
			}
			if(numair>=8){
				ChatUtil.addMessageNoLocalized(ep, "detect airblocks:"+String.valueOf(numair));
				//ep.addChatMessage("detect airblocks:"+String.valueOf(numair));
				return true;
			}
			return false;

		}
	}
	
	public void doBellRinger(InvokeSkill parent){
		parent.owner.swingItem();
		XYZPos up = parent.usepoint;
		parent.world.playSoundEffect(up.dx, up.dy, up.dz, "random.explode", 4.0F, (1.0F + (parent.owner.worldObj.rand.nextFloat() - parent.owner.worldObj.rand.nextFloat()) * 0.2F) * 0.7F);
		parent.world.spawnParticle("largeexplode", (double)up.dx+0.5D, (double)up.dy+1, (double)up.dz+0.5D, 1.0D, 0.0D, 0.0D);

		boolean sw = false;
		int depth = 40;

		for(int i=0;i<40;i++){
			if(parent.usepoint.y-i>0){
				if(parent.world.isAirBlock(parent.usepoint.x, parent.usepoint.y-i, parent.usepoint.z)){
					if(detectRoom(parent.world,parent.getOwnerEP(),parent.usepoint.x,parent.usepoint.y-i,parent.usepoint.z)){

						sw=true;
					}
				}
			}
		}

		if(sw){
			parent.world.playSound((double)parent.usepoint.x, (double)parent.usepoint.y, (double)parent.usepoint.z, "ambient.cave.cave", 0.7F, 0.8F + parent.world.rand.nextFloat() * 0.2F, false);

		}
	}
	
	private boolean detectRoom(World world,EntityPlayer ep,int x,int y,int z){
		int numair=0;
		for(int i=0;i<3;i++){
			for(int j=0;j<3;j++){
				for(int k=0;k<3;k++){
					int xx = x-1+i;
					int yy = y-1+j;
					int zz = z-1+k;
					if(yy>0){
						if(world.isAirBlock(xx, yy, zz)){
							numair+=1;
						}
					}
				}
			}
		}
		if(numair>=8){
			ChatUtil.addMessageNoLocalized(ep, "detect airblocks:"+String.valueOf(numair));
			//ep.addChatMessage("detect airblocks:"+String.valueOf(numair));
			return true;
		}
		return false;

	}
	
	public class SkillGrandSlam extends SkillRangedAttack{


		
		public SkillGrandSlam(SkillMelee.Type type,double horizontal, double vertical) {
			super(type,horizontal, vertical);
			this.onGroundOnly = true;
		}

		@Override
		public void hookStart(InvokeSkill parent){
			XYZPos up = parent.usepoint;
			parent.owner.swingItem();
			parent.world.playSoundEffect(up.dx, up.dy, up.dz, "random.explode", 4.0F, (1.0F + (parent.owner.worldObj.rand.nextFloat() - parent.owner.worldObj.rand.nextFloat()) * 0.2F) * 0.7F);
			parent.world.spawnParticle("largeexplode", (double)up.dx+0.5D, (double)up.dy+1, (double)up.dz+0.5D, 1.0D, 0.0D, 0.0D);
			if(parent.owner.onGround){
				parent.owner.motionY += 0.02;
			}
		}
		
		@Override
		public RangeDamageHelper getCustomizedRangeDamageHelper(InvokeSkill skill){
			return new CauseExplode(skill,1);
		}
		
		@Override
		public AxisAlignedBB getCustomizedBoundingBox(InvokeSkill parent){
			return HSLibs.getBounding(parent.usepoint, this.rangeHorizontal, this.rangeVertical);
		}
		
	}
	public void doGrandSlam(InvokeSkill parent){
		Vec3 lookvec = parent.owner.getLookVec();
		parent.owner.swingItem();
		Random rand = parent.world.rand;
		World world = parent.world;
		XYZPos up = parent.usepoint;
		//XYZPos up = new XYZPos(parent.usepoint.x+(int)lookvec.xCoord*5,parent.usepoint.y,parent.usepoint.z+(int)lookvec.zCoord*5);
		parent.world.playSoundEffect(up.dx, up.dy, up.dz, "random.explode", 4.0F, (1.0F + (parent.owner.worldObj.rand.nextFloat() - parent.owner.worldObj.rand.nextFloat()) * 0.2F) * 0.7F);
		parent.world.spawnParticle("largeexplode", (double)up.dx+0.5D, (double)up.dy+1, (double)up.dz+0.5D, 1.0D, 0.0D, 0.0D);


		if(parent.owner.onGround){
			parent.owner.motionY += 0.02;
		}
		
		
		AxisAlignedBB bb = HSLibs.getBounding(up, 10.0D, 3.0D);
		CauseExplode explode = new CauseExplode(parent,1);
		
		explode.causeRangeDamage(bb, parent.getDamageSource(), parent.getModifiedAttackDamage(false, 0));
		//parent.causeRangeDamage(explode, world, bb, parent.getAttackDamage(), DamageSource.causePlayerDamage(parent.owner), false);

	}

	public class SkillPulverizer extends SkillMelee{

		public SkillPulverizer(Type type) {
			super(type);
			// TODO 自動生成されたコンストラクター・スタブ
		}

		@Override
		public void invokeSkill(InvokeSkill helper) {
			helper.owner.swingItem();
			XYZPos up = helper.usepoint;
			helper.world.playSoundEffect(up.dx, up.dy, up.dz, "random.explode", 4.0F, (1.0F + (helper.owner.worldObj.rand.nextFloat() - helper.owner.worldObj.rand.nextFloat()) * 0.2F) * 0.7F);
			helper.world.spawnParticle("largeexplode", (double)up.dx+0.5D, (double)up.dy+1, (double)up.dz+0.5D, 1.0D, 0.0D, 0.0D);
			ScanHelper scan = new ScanHelper(helper.usepoint.x,helper.usepoint.y,helper.usepoint.z,3,3);
			scan.setWorld(helper.world);
			for(;scan.hasNext();scan.next()){
				if(!helper.world.isRemote){
					boolean flag = false;
					float hardness = scan.getBlock().getBlockHardness(helper.world, scan.sx, scan.sy, scan.sz);
					if(HSLibs.canBreakAndEffectiveBlock(helper.world,helper.getOwnerEP(),"pickaxe",scan.getAsXYZPos())){
						flag = true;
					}
					if(flag){
						Block id = scan.getBlock();
						id.dropXpOnBlockBreak(helper.world, scan.sx, scan.sy, scan.sz, id.getExpDrop(helper.world, scan.getMetadata(), 0));
						HSLibs.playBlockBreakSFX(helper.world, scan.getAsXYZPos(), new PairID(id,scan.getMetadata()));
						
					}
				}
			}

			return;
		}
		
	}
	
	public class SkillRockCrusher extends SkillMelee{

		public SkillRockCrusher(Type type) {
			super(type);
			// TODO 自動生成されたコンストラクター・スタブ
		}

		@Override
		public void invokeSkill(InvokeSkill parent) {
			List<XYZPos> scheduledBreak = new ArrayList();
			int harvestLevel = parent.weapon.getItem().getHarvestLevel(parent.weapon, "pickaxe");
			World world = parent.world;
			parent.owner.swingItem();
			XYZPos up = parent.usepoint;
			parent.world.playSoundEffect(up.dx, up.dy, up.dz, "random.explode", 4.0F, (1.0F + (parent.owner.worldObj.rand.nextFloat() - parent.owner.worldObj.rand.nextFloat()) * 0.2F) * 0.7F);
			parent.world.spawnParticle("largeexplode", (double)up.dx+0.5D, (double)up.dy+1, (double)up.dz+0.5D, 1.0D, 0.0D, 0.0D);
			PairID compareBlock = HSLibs.getBlockDatas(world, up);
			scheduledBreak.add(up);
			if(HSLibs.isOreBlock(compareBlock) && parent.getOwnerEP().canHarvestBlock(compareBlock.getBlockObject())){
				
				if(world.isRemote){
					return;
				}
				ScannerBreakBlock scannerBreak = new ScannerBreakBlock(compareBlock, up);
				scannerBreak.doScan(world, 5);
				if(!parent.world.isRemote){
					parent.weapon.damageItem(scannerBreak.breaked*2, parent.owner);
				}
			}else{
				if(compareBlock.getBlockObject().getHarvestLevel(compareBlock.getMeta())<=harvestLevel+1){
					
					compareBlock.getBlockObject().dropXpOnBlockBreak(parent.world, up.x, up.y, up.z, compareBlock.getBlockObject().getExpDrop(parent.world, compareBlock.getMeta(), 0));
					HSLibs.playBlockBreakSFX(world, up, compareBlock);
				}
			}
			
		}
		
	}
	public void doPulverizer(InvokeSkill helper){

	
		helper.owner.swingItem();
		XYZPos up = helper.usepoint;
		helper.world.playSoundEffect(up.dx, up.dy, up.dz, "random.explode", 4.0F, (1.0F + (helper.owner.worldObj.rand.nextFloat() - helper.owner.worldObj.rand.nextFloat()) * 0.2F) * 0.7F);
		helper.world.spawnParticle("largeexplode", (double)up.dx+0.5D, (double)up.dy+1, (double)up.dz+0.5D, 1.0D, 0.0D, 0.0D);
		ScanHelper scan = new ScanHelper(helper.usepoint.x,helper.usepoint.y,helper.usepoint.z,3,3);
		scan.setWorld(helper.world);
		for(;scan.hasNext();scan.next()){
			if(!helper.world.isRemote){
				boolean flag = false;
				float hardness = scan.getBlock().getBlockHardness(helper.world, scan.sx, scan.sy, scan.sz);
				if(HSLibs.canBreakAndEffectiveBlock(helper.world,helper.getOwnerEP(),"pickaxe",scan.getAsXYZPos())){
					flag = true;
				}
				if(flag){
					Block id = scan.getBlock();
					id.dropXpOnBlockBreak(helper.world, scan.sx, scan.sy, scan.sz, id.getExpDrop(helper.world, scan.getMetadata(), 0));
					HSLibs.playBlockBreakSFX(helper.world, scan.getAsXYZPos(), new PairID(id,scan.getMetadata()));
					
				}
			}
		}

		return;

	}
	//TODO 破壊したブロックの数に応じて破損
	public void doRockcrusher(InvokeSkill parent){
		List<XYZPos> scheduledBreak = new ArrayList();
		int harvestLevel = parent.weapon.getItem().getHarvestLevel(parent.weapon, "pickaxe");
		World world = parent.world;
		parent.owner.swingItem();
		XYZPos up = parent.usepoint;
		parent.world.playSoundEffect(up.dx, up.dy, up.dz, "random.explode", 4.0F, (1.0F + (parent.owner.worldObj.rand.nextFloat() - parent.owner.worldObj.rand.nextFloat()) * 0.2F) * 0.7F);
		parent.world.spawnParticle("largeexplode", (double)up.dx+0.5D, (double)up.dy+1, (double)up.dz+0.5D, 1.0D, 0.0D, 0.0D);
		PairID compareBlock = HSLibs.getBlockDatas(world, up);
		scheduledBreak.add(up);
		if(HSLibs.isOreBlock(compareBlock) && parent.getOwnerEP().canHarvestBlock(compareBlock.getBlockObject())){
			
			if(world.isRemote){
				return;
			}
			ScannerBreakBlock scannerBreak = new ScannerBreakBlock(compareBlock, up);
			scannerBreak.doScan(world, 5);
//			for(int i=0;i<5;i++){
//				List<XYZPos> removeList = new ArrayList();
//				List<XYZPos> addList = new ArrayList();
//				for(XYZPos pos:scheduledBreak){
//					Block block = world.getBlock(pos.x, pos.y, pos.z);
//					if(block!=Blocks.air){
//						block.dropXpOnBlockBreak(parent.world, up.x, up.y, up.z, block.getExpDrop(parent.world, compareBlock.metadata, 0));
//						HSLibs.playBlockBreakSFX(world, pos, compareBlock);
//					}
//
//					removeList.add(pos);
//					for(XYZPos round:XYZPos.around){
//						XYZPos addedPos = pos.add(round);
//						PairID roundBlockData = HSLibs.getBlockDatas(world, addedPos);
//						if(roundBlockData.equals(compareBlock)){
//							addList.add(addedPos);
//						}
//						if((compareBlock.blockObj instanceof BlockRedstoneOre) && (roundBlockData.blockObj instanceof BlockRedstoneOre)){
//							addList.add(addedPos);
//						}
//					}
//				}
//				
//				for(XYZPos rm:removeList){
//					scheduledBreak.remove(rm);
//				}
//				
//				for(XYZPos ad:addList){
//					scheduledBreak.add(ad);
//				}
//
//			}
			//this.BreakChainReaction(parent.world,parent.owner,up, new PairID(blockUsePoint,meta),0);
		}else{
			if(compareBlock.getBlockObject().getHarvestLevel(compareBlock.getMeta())<=harvestLevel+1){
				
				compareBlock.getBlockObject().dropXpOnBlockBreak(parent.world, up.x, up.y, up.z, compareBlock.getBlockObject().getExpDrop(parent.world, compareBlock.getMeta(), 0));
				HSLibs.playBlockBreakSFX(world, up, compareBlock);
			}
		}
		
	}
	

	public class SkillSkullCrusher extends SkillMelee{

		public SkillSkullCrusher(Type type) {
			super(type);
			// TODO 自動生成されたコンストラクター・スタブ
		}

		@Override
		public void invokeSkill(InvokeSkill helper) {
			helper.playSound("random.explode");
			//ep.worldObj.playSoundEffect(en.posX, en.posY, en.posZ, "random.explode", 4.0F, (1.0F + (ep.worldObj.rand.nextFloat() -ep.worldObj.rand.nextFloat()) * 0.2F) * 0.7F);

			helper.spawnParticle("largeexplode",helper.target);
			//ep.worldObj.spawnParticle("largeexplode", (double)en.posX+0.5D, (double)en.posY+1, (double)en.posZ+0.5D, 1.0D, 0.0D, 0.0D);

			helper.addPotionChance(40, helper.target, Potion.weakness.id, 160, 1);
			if(helper.target instanceof EntitySkeleton){
				helper.attack(helper.target, null,1.0F);
			}else{
				helper.attack(helper.target, null,0.7F);
			}
		}
		
	}
	
	public class SkillEarthDragon extends SkillRangedAttack{

		public SkillEarthDragon(SkillMelee.Type type,double horizontal, double vertical) {
			super(type,horizontal, vertical);
			this.onGroundOnly = true;
		}
		
		@Override
		public RangeDamageHelper getCustomizedRangeDamageHelper(InvokeSkill helper){
			return new CauseAddVelocity(helper.world,helper);
		}
		
		@Override
		public void hookStart(InvokeSkill helper){
			helper.owner.swingItem();
			XYZPos up = helper.usepoint;
			helper.playExplodeSound(up);
			helper.world.spawnParticle("largeexplode", (double)up.dx+0.5D, (double)up.dy+1, (double)up.dz+0.5D, 1.0D, 0.0D, 0.0D);

		}
		

	}
	
	public void doSkullCrash(InvokeSkill helper){
		helper.playSound("random.explode");
		//ep.worldObj.playSoundEffect(en.posX, en.posY, en.posZ, "random.explode", 4.0F, (1.0F + (ep.worldObj.rand.nextFloat() -ep.worldObj.rand.nextFloat()) * 0.2F) * 0.7F);

		helper.spawnParticle("largeexplode",helper.target);
		//ep.worldObj.spawnParticle("largeexplode", (double)en.posX+0.5D, (double)en.posY+1, (double)en.posZ+0.5D, 1.0D, 0.0D, 0.0D);

		helper.addPotionChance(40, helper.target, Potion.weakness.id, 160, 1);
		if(helper.target instanceof EntitySkeleton){
			helper.attack(helper.target, null,1.0F);
		}else{
			helper.attack(helper.target, null,0.7F);
		}
		
		

	}
	
	public void doEarthDragon(InvokeSkill helper){
		helper.owner.swingItem();
		XYZPos up = helper.usepoint;
		helper.world.playSoundEffect(up.dx, up.dy, up.dz, "random.explode", 4.0F, (1.0F + (helper.owner.worldObj.rand.nextFloat() - helper.owner.worldObj.rand.nextFloat()) * 0.2F) * 0.7F);
		helper.world.spawnParticle("largeexplode", (double)up.dx+0.5D, (double)up.dy+1, (double)up.dz+0.5D, 1.0D, 0.0D, 0.0D);

		AxisAlignedBB bb = helper.owner.boundingBox.expand(8.0D, 8.0D, 8.0D);
		CauseAddVelocity cause = new CauseAddVelocity(helper.world,helper);
		//DamageSource ds = new DamageSourceUnsaga(null,helper.owner,helper.getAttackDamageLP(),helper.getDamageType().MAGIC);
		cause.setSkillEffectHelper(helper);
		cause.causeRangeDamage(bb, helper.getDamageSource(), helper.getModifiedAttackDamage(false, 0));
		//helper.causeRangeDamage(cause, helper.world, bb, helper.getAttackDamage(),ds , false);

	}

	public static SkillStaff getInstance() {
		if(INSTANCE==null){
			INSTANCE = new SkillStaff();
		}
		return INSTANCE;
	}
}

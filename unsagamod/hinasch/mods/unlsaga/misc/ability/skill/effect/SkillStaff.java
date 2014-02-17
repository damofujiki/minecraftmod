package hinasch.mods.unlsaga.misc.ability.skill.effect;

import hinasch.lib.ScanHelper;
import hinasch.lib.XYZPos;
import hinasch.mods.unlsaga.misc.ability.AbilityRegistry;
import hinasch.mods.unlsaga.misc.debuff.DebuffRegistry;
import hinasch.mods.unlsaga.misc.debuff.LivingDebuff;
import hinasch.mods.unlsaga.misc.debuff.LivingStateGrandSlam;
import hinasch.mods.unlsaga.misc.util.CauseAddVelocity;

import java.util.HashSet;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import com.google.common.collect.Sets;

public class SkillStaff extends SkillEffect{

	private HashSet<Material> canBreakBlocks = Sets.newHashSet(Material.ground,Material.sand,Material.grass,Material.ice,Material.rock);
	//hardness 2.0F 以下、Material rock ground
	@Override
	public void selector(SkillEffectHelper parent){
		
		if(parent.skill==AbilityRegistry.earthDragon)this.doEarthDragon(parent);
		if(parent.skill==AbilityRegistry.pulvorizer)this.doPulverizer(parent);
		if(parent.skill==AbilityRegistry.grandSlam)this.doGrandSlam(parent);
		if(parent.skill==AbilityRegistry.skullCrash)this.doSkullCrash(parent);
		if(parent.skill==AbilityRegistry.gonger)this.doBellRinger(parent);
		
	}
	
	public void doBellRinger(SkillEffectHelper parent){

		boolean sw = false;
		int depth = 40;

		for(int i=0;i<40;i++){
			if(parent.usepoint.y-i>0){
				if(parent.world.isAirBlock(parent.usepoint.x, parent.usepoint.y-i, parent.usepoint.z)){
					if(detectRoom(parent.world,parent.ownerSkill,parent.usepoint.x,parent.usepoint.y-i,parent.usepoint.z)){

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
			ep.addChatMessage("detect airblocks:"+String.valueOf(numair));
			return true;
		}
		return false;

	}
	
	public void doGrandSlam(SkillEffectHelper parent){

		ScanHelper scan = new ScanHelper(parent.usepoint.x,parent.usepoint.y+4,parent.usepoint.z,4,4);
		StringBuilder csv = new StringBuilder();
		for(;scan.hasNext();scan.next()){
			if(scan.isAirBlock()){
				csv.append(0);
			}else{
				csv.append(scan.getID());
			}
			if(scan.hasNext())csv.append("\\.");
			if(canBlockBreak(parent,new XYZPos(scan.sx,scan.sy,scan.sz))){
				scan.setBlockHere(0);
				//Block.blocksList[scan.getID()].dropBlockAsItem(parent.world,scan.sx,scan.sy,scan.sz,scan.getID(),scan.getMetadata());
			}
		}
		
		LivingDebuff.addLivingDebuff(parent.ownerSkill, new LivingStateGrandSlam(DebuffRegistry.grandSlam,5,false,parent.usepoint,new String(csv)));
		
		
	}
	
	public static void restoreBrokenBlocks(XYZPos pos,String psv){
		if(psv!=null && !psv.equals("")){
			int index = 0;
			String[] strs = psv.split("\\.");
			ScanHelper scan = new ScanHelper(pos.x,pos.y+4,pos.z,4,4);
			for(;scan.hasNext();scan.next()){
				if(index>strs.length-1){
					scan.setBlockHere(Integer.valueOf(strs[index]), 0, 2);
				}
			}
		}
	}
	private boolean canBlockBreak(SkillEffectHelper parent,XYZPos xyz){
		if(this.canBreakBlocks.contains(parent.world.getBlockMaterial(xyz.x, xyz.y, xyz.z))){
			int blockid = parent.world.getBlockId(xyz.x, xyz.y, xyz.z);
			Block block = Block.blocksList[blockid];
			if(parent.world.getBlockMetadata(xyz.x, xyz.y, xyz.z)!=0){
				return false;
			}
			if(block.getBlockHardness(parent.world, xyz.x, xyz.y, xyz.z)<=2.0F){
				return true;
			}
		}
		return false;
	}
	public void doPulverizer(SkillEffectHelper helper){


		ScanHelper scan = new ScanHelper(helper.usepoint.x,helper.usepoint.y,helper.usepoint.z,3,3);
		scan.setWorld(helper.world);
		//System.out.print("kiteru1");
		for(;scan.hasNext();scan.next()){
			//System.out.print(world.getBlockId(scan.sx, scan.sy, scan.sz));

			// System.out.print("kiteru3");


			if(!helper.world.isRemote){
				if(scan.getID()==Block.stone.blockID || scan.getID()==Block.cobblestone.blockID){
					int id = scan.getID();
					Block.blocksList[id].harvestBlock(helper.world, helper.ownerSkill, scan.sx, scan.sy, scan.sz, 0);
					scan.setBlockHere(0);
				}
				
			}
		}

		return;

	}
	
	public void doSkullCrash(SkillEffectHelper helper){
		LivingHurtEvent e = (LivingHurtEvent)helper.parent;
		helper.playSoundServer("random.explode");
		//ep.worldObj.playSoundEffect(en.posX, en.posY, en.posZ, "random.explode", 4.0F, (1.0F + (ep.worldObj.rand.nextFloat() -ep.worldObj.rand.nextFloat()) * 0.2F) * 0.7F);

		helper.spawnParticle("largeexplode",helper.target);
		//ep.worldObj.spawnParticle("largeexplode", (double)en.posX+0.5D, (double)en.posY+1, (double)en.posZ+0.5D, 1.0D, 0.0D, 0.0D);

		helper.addPotionChance(40, helper.target, Potion.weakness.id, 160, 1);
		if(helper.target instanceof EntitySkeleton){

			e.ammount += helper.getAttackDamage()+3;
			//UtilSkill.tryLPHurt(40, 2, en, ep);


		}else{

			e.ammount += helper.getAttackDamage();

		}
	}
	
	public void doEarthDragon(SkillEffectHelper helper){
		AxisAlignedBB bb = helper.ownerSkill.boundingBox.expand(8.0D, 8.0D, 8.0D);
		CauseAddVelocity cause = new CauseAddVelocity(helper.world,helper);
		DamageSource ds = DamageSource.causePlayerDamage(helper.ownerSkill);
		helper.causeRangeDamage(cause, helper.world, bb, helper.getAttackDamage(),ds , false);

	}
}

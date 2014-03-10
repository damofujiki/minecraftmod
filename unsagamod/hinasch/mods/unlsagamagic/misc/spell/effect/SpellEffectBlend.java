package hinasch.mods.unlsagamagic.misc.spell.effect;


import hinasch.lib.HSLibs;
import hinasch.lib.PairID;
import hinasch.lib.ScanHelper;
import hinasch.lib.WorldHelper;
import hinasch.lib.XYZPos;
import hinasch.mods.unlsaga.Unsaga;
import hinasch.mods.unlsaga.block.BlockChestUnsaga;
import hinasch.mods.unlsaga.entity.projectile.EntityBoulder;
import hinasch.mods.unlsaga.misc.debuff.Debuffs;
import hinasch.mods.unlsaga.misc.debuff.livingdebuff.LivingBuff;
import hinasch.mods.unlsaga.misc.debuff.livingdebuff.LivingDebuff;
import hinasch.mods.unlsaga.misc.debuff.livingdebuff.LivingStateCrimsonFlare;
import hinasch.mods.unlsaga.misc.debuff.livingdebuff.LivingStateRandomThrow;
import hinasch.mods.unlsaga.misc.translation.Translation;
import hinasch.mods.unlsaga.misc.util.ChatUtil;
import hinasch.mods.unlsaga.misc.util.LockOnHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

import com.google.common.collect.Lists;

public class SpellEffectBlend {

	public static SpellEffectBlend INSTANCE;

	
	public class SpellThunderCrap extends AbstractSpell{

		public SpellThunderCrap() {
			super();
			// TODO 自動生成されたコンストラクター・スタブ
		}

		@Override
		public void doSpell(InvokeSpell invoke) {
			if(!LivingDebuff.hasDebuff(invoke.invoker, Debuffs.thunderCrap)){
				LivingDebuff.addLivingDebuff(invoke.invoker, new LivingStateRandomThrow(Debuffs.thunderCrap,100,10, 1));
				
			}
			
		}
		
	}
	public class SpellStoneShower extends AbstractSpell{

		public SpellStoneShower() {
			super();
			// TODO 自動生成されたコンストラクター・スタブ
		}

		@Override
		public void doSpell(InvokeSpell spell) {
			if(spell.getAmp()>1.5F){
				spell.world.playSoundAtEntity(spell.invoker, "mob.ghast.fireball", 1.0F, 1.0F / (spell.world.rand.nextFloat() * 0.4F + 1.2F) + 1.0F * 0.5F);
				
				EntityBoulder var8 = new EntityBoulder(spell.world, spell.invoker, 1.0F * 2.0F,true);
				var8.canBePickedUp = 0;
				var8.setDamage(var8.getDamage()+(1.0*spell.getAmp()));
				var8.setRangeStoneShower(Math.round(10*spell.getAmp()));
				if (!spell.world.isRemote)
				{
					spell.world.spawnEntityInWorld(var8);
				}
			}else{
				LivingDebuff.addLivingDebuff(spell.invoker, new LivingStateRandomThrow(Debuffs.stoneShower,100,18,(int)(1.0F*spell.getAmp())));
			}
			

			return;
			
		}
		
	}
	public class SpellLeavesShield extends AbstractSpell{

		public SpellLeavesShield() {
			super();
			// TODO 自動生成されたコンストラクター・スタブ
		}

		@Override
		public void doSpell(InvokeSpell parent) {
			int remain = (int)((float)15 * parent.getAmp());
			int amp = (int)((float)30 * parent.getAmp());
			if(parent.getTarget().isPresent()){
				LivingDebuff.addLivingDebuff(parent.getTarget().get(), new LivingBuff(Debuffs.leavesShield,remain,amp));
			}else{
				LivingDebuff.addLivingDebuff(parent.invoker, new LivingBuff(Debuffs.leavesShield,remain,amp));
			}
			
		}
		
	}
	public class SpellIceNine extends AbstractSpell{

		public SpellIceNine() {
			super();
			// TODO 自動生成されたコンストラクター・スタブ
		}

		@Override
		public void doSpell(InvokeSpell parent) {
			int amp = 1;

			if(parent.invoker.isBurning()){
				parent.invoker.setFire(0);
			}

			ScanHelper scan = new ScanHelper(parent.invoker,11,5);
			scan.setWorld(parent.world);

			for(;scan.hasNext();scan.next()){
				if((scan.sy>0)){
					if(scan.getBlock()==Blocks.water ||scan.getBlock()==Blocks.flowing_water ){
						scan.setBlockHere(Blocks.ice,0,2);
					}
					if(scan.getBlock()==Blocks.flowing_lava){
						scan.setBlockHere(Blocks.cobblestone);
					}
					if(scan.getBlock()==Blocks.lava){
						scan.setBlockHere(Blocks.obsidian);
					}
					if(scan.getBlock()==Blocks.fire){
						scan.setBlockHere(Blocks.air);
					}

					if(scan.isOpaqueBlock() && scan.isAirBlockUp() && !scan.isPlayerPos()){
						System.out.println("true");
						
						this.worldHelper.setBlock(scan.getAsXYZPos().add(WorldHelper.UP), new PairID(Blocks.snow,0));
						//parent.world.setBlock(scan.sx, scan.sy+1, scan.sz, Blocks.snow,0,2);
					}
					if(scan.getBlock()==Blocks.snow && !scan.isPlayerPos()){
						int meta = scan.getMetadata();
						if(meta<15){
							this.worldHelper.setBlockMetadata(scan.getAsXYZPos(), meta+1, 3);
							//parent.world.setBlockMetadataWithNotify(scan.sx, scan.sy, scan.sz, meta+1, 2);
						}
					}


				}
			}
			
		}
		
	}
	public class SpellReflesh extends SpellHealing{

		public SpellReflesh() {
			super();
			// TODO 自動生成されたコンストラクター・スタブ
		}

		@Override
		public void hookHealing(InvokeSpell parent, EntityLivingBase target) {
			for(Potion potion:Potion.potionTypes){
				if(potion!=null){
					if(potion.isBadEffect()){
						target.removePotionEffect(potion.id);
					}
				}
			}
		}
		
	}
	
	public class SpellCrimsonFlare extends AbstractSpell{

		public SpellCrimsonFlare() {
			super();
			// TODO 自動生成されたコンストラクター・スタブ
		}

		@Override
		public void doSpell(InvokeSpell parent) {
			int amp =(int) parent.getAmp();

			int targetid = -1;
			XYZPos xyz = null;
			if(parent.getTarget().isPresent()){
				xyz = XYZPos.entityPosToXYZ(parent.getTarget().get());
				targetid = parent.getTarget().get().getEntityId();

			}else{
				EntityLivingBase nearent = LockOnHelper.searchEntityNear(parent.invoker, Debuffs.spellTarget);
				if(nearent!=null){
					xyz = XYZPos.entityPosToXYZ(nearent);
					targetid = nearent.getEntityId();
				}
			}
			if(targetid!=-1 && !LivingDebuff.hasDebuff(parent.invoker, Debuffs.crimsonFlare)){
				LivingDebuff.addLivingDebuff(parent.invoker, new LivingStateCrimsonFlare(Debuffs.crimsonFlare,100,xyz.x,xyz.y,xyz.z,amp,targetid));

			}

			return;
			
		}
		
	}
	
	
	public class SpellPowerRise extends SpellAddBuff{
		public SpellPowerRise(){
			this.potions = Lists.newArrayList(Potion.digSpeed);
		}
	}
	
	public class SpellMegaPowerRise extends SpellAddBuff{
		public SpellMegaPowerRise(){
			this.potions = Lists.newArrayList(Potion.digSpeed,Potion.damageBoost,Potion.jump,Potion.moveSlowdown);
		}
	}
	
	public class SpellDetectTreasure extends AbstractSpell{

		public SpellDetectTreasure() {
			super();
			// TODO 自動生成されたコンストラクター・スタブ
		}

		@Override
		public void doSpell(InvokeSpell parent) {
			int range = Math.round((16*9)*parent.getAmp());
			if(range<1){
				range = 1;
			}
			if(range>16*16){
				range = 16*16;
			}
			Unsaga.debug(range);

			boolean found = false;
			ScanHelper scan = new ScanHelper(parent.invoker,range,range);
			scan.setWorld(parent.world);
			for(;scan.hasNext();scan.next()){
				if(scan.sy>0 && scan.sy<255){
					if(!scan.isAirBlock() && scan.getBlock()!=null){
						Block block = scan.getBlock();
						if(block instanceof BlockChest || block instanceof BlockChestUnsaga){
							Unsaga.debug("発見");
							Vec3 vec = Vec3.createVectorHelper((int)scan.sx, (int)scan.sy, (int)scan.sz);
							Vec3 vec2 = parent.invoker.getPosition(1.0F);
							int distance = (int) (vec2.distanceTo(vec));
							ChatUtil.addMessageNoLocalized(parent.invoker, "Detect Chest:"+distance+"m");
							found = true;
						}

					}
				}

			}


			if(!found){
				ChatUtil.addMessage(parent.invoker, "msg.spell.chest.notfound");
				
			}
			return;
		}
		
	}
	public static SpellEffectBlend getInstance(){
		if(INSTANCE==null){
			INSTANCE = new SpellEffectBlend();
		}
		return INSTANCE;

	}
	
	public class SpellGoldFinger extends AbstractSpell{

		public SpellGoldFinger() {
			super();
			// TODO 自動生成されたコンストラクター・スタブ
		}

		@Override
		public void doSpell(InvokeSpell parent) {
			if(parent.getTarget().isPresent()){
				EntityLivingBase target = parent.getTarget().get();
				if(target instanceof IBossDisplayData){
					return;
				}
				int prob = (int)70 - (int)(target.getHealth()/HSLibs.exceptZero(target.getMaxHealth(),1.0F) * 100);
				
				prob = MathHelper.clamp_int(prob, 1, 100);
				ItemStack goldnugget = new ItemStack(Items.gold_nugget,1);
				if(parent.world.rand.nextInt(100)<prob){
					HSLibs.dropItem(parent.world, goldnugget, target.posX, target.posY, target.posZ);
					target.setDead();
					String str = Translation.localize(Translation.localize("msg.spell.touchgold.succeeded"));
					String formatted = String.format(str, target.getCommandSenderName());
					ChatUtil.addMessageNoLocalized(parent.invoker, formatted);
				}else{


					ChatUtil.addMessage(parent.invoker, "msg.spell.touchgold.failed");
					//parent.invoker.addChatMessage(Translation.localize("msg.spell.touchgold.failed"));
				}
				
			}
			
		}
		
	}
	

}

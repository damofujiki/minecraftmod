package hinasch.mods.unlsagamagic.misc.spell.effect;

import hinasch.lib.HSLibs;
import hinasch.lib.PairID;
import hinasch.lib.PairIDList;
import hinasch.lib.ScanHelper;
import hinasch.lib.XYZPos;
import hinasch.mods.unlsaga.Unsaga;
import hinasch.mods.unlsaga.core.FiveElements;
import hinasch.mods.unlsaga.entity.EntityTreasureSlime;
import hinasch.mods.unlsaga.entity.projectile.EntityBoulder;
import hinasch.mods.unlsaga.entity.projectile.EntityFireArrow;
import hinasch.mods.unlsaga.misc.debuff.Buff;
import hinasch.mods.unlsaga.misc.debuff.Debuff;
import hinasch.mods.unlsaga.misc.debuff.Debuffs;
import hinasch.mods.unlsaga.misc.debuff.livingdebuff.LivingDebuff;
import hinasch.mods.unlsaga.misc.debuff.livingdebuff.LivingStateCrimsonFlare;
import hinasch.mods.unlsaga.misc.translation.Translation;
import hinasch.mods.unlsaga.misc.util.ChatUtil;
import hinasch.mods.unlsaga.misc.util.DamageHelper;
import hinasch.mods.unlsaga.misc.util.DamageSourceUnsaga;
import hinasch.mods.unlsaga.misc.util.LockOnHelper;
import hinasch.mods.unlsaga.network.packet.PacketHandlerClientThunder;
import hinasch.mods.unlsaga.network.packet.PacketParticle;
import hinasch.mods.unlsaga.network.packet.PacketSound;
import hinasch.mods.unlsaga.network.packet.PacketUtil;
import hinasch.mods.unlsagamagic.UnsagaMagic;
import hinasch.mods.unlsagamagic.item.ItemSpellBook;

import java.util.HashSet;

import net.minecraft.block.BlockOre;
import net.minecraft.block.BlockRedstoneOre;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityAmbientCreature;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraftforge.oredict.OreDictionary;

import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;

public class SpellEffectNormal{


	public static SpellEffectNormal INSTANCE;

	public final SpellBase abyss = new SpellAbyss();
	public final SpellBase animalCharm = new SpellAnimalCharm();
	public final SpellBase boulder = new SpellBoulder();
	public final SpellBase buildUp = new SpellBuildUp();
	public final SpellBase cloudCall = new SpellCloudCall();
	public final SpellBase callThunder = new SpellCallThunder();
	public final SpellBase detectAnimal = new SpellDetectAnimal();
	public final SpellBase detectBlood = new SpellDetectBlood();
	public final SpellBase detectGold = new SpellDetectGold();
	public final SpellBase elemntVeil = new SpellElementVeil();
	public final SpellBase fireArrow = new SpellFireArrow();
	public final SpellBase fireStorm = new SpellFireStorm();
	public final SpellBase fireWall = new SpellFireWall();
	public final SpellBase heroism = new SpellHeroism();
	public final SpellBase lifeBoost = new SpellLifeBoost();
	public final SpellBase magicLock = new SpellMagicLock();
	public final SpellBase meditation = new SpellMeditation();
	public final SpellBase missuileGuard = new SpellMissuileGuard();
	public final SpellBase overGrowth = new SpellOverGrowth();
	public final SpellBase purify = new SpellPurify();
	public final SpellBase recycle = new SpellRecycle();
	public final SpellBase weakness = new SpellWeakness();

	public static SpellEffectNormal getInstance(){
		if(INSTANCE==null){
			INSTANCE = new SpellEffectNormal();
		}
		return INSTANCE;

	}
	public class SpellCallThunder extends SpellBase{

		public SpellCallThunder() {
			//super();
			// TODO 自動生成されたコンストラクター・スタブ
		}

		@Override
		public void invokeSpell(InvokeSpell parentInvoke) {
			if(parentInvoke.getTarget().isPresent()){
				Entity target = parentInvoke.getTarget().get();
				EntityLightningBolt thunder = new EntityLightningBolt(parentInvoke.world,target.posX,target.posY,target.posZ);
				parentInvoke.world.spawnEntityInWorld(thunder);
				DamageSourceUnsaga ds = new DamageSourceUnsaga(null,parentInvoke.invoker,parentInvoke.spell.hurtLP,DamageHelper.Type.MAGIC);
				ds.setElement(FiveElements.EnumElement.WOOD);
				target.attackEntityFrom(ds, parentInvoke.spell.hurtHP*parentInvoke.getAmp());
				PacketHandlerClientThunder pl = new PacketHandlerClientThunder(XYZPos.entityPosToXYZ(target));
				Unsaga.packetPipeline.sendToAllAround(pl, PacketUtil.getTargetPointNear(target));
			}
			
		}
		
	}


	public class SpellBuildUp extends SpellAddBuff{
		
		public SpellBuildUp(){
			this.potions = Lists.newArrayList(Potion.digSpeed);
			this.buffs = Lists.newArrayList(Debuffs.physicalUp);
		}
	}
	public class SpellPurify extends SpellHealing{

		@Override
		public void hookHealing(InvokeSpell parent, EntityLivingBase target) {
			if(parent.getAmp()>1.5F){
				target.removePotionEffect(Potion.poison.id);
				target.removePotionEffect(Potion.wither.id);
				target.removePotionEffect(Potion.blindness.id);
			}
			
		}
		
	}
	public class SpellFireStorm extends SpellBase{

		public SpellFireStorm() {
			super();
			// TODO 自動生成されたコンストラクター・スタブ
		}

		@Override
		public void invokeSpell(InvokeSpell parent) {
			int amp =(int) parent.getAmp();

			XYZPos xyz = null;
			if(parent.getTarget().isPresent()){
				xyz = XYZPos.entityPosToXYZ(parent.getTarget().get());
			}else{
				EntityLivingBase nearent = LockOnHelper.searchEntityNear(parent.invoker, Debuffs.spellTarget);
				if(nearent!=null){
					xyz = XYZPos.entityPosToXYZ(nearent);
				}
			}
			if(xyz!=null && !LivingDebuff.hasDebuff(parent.invoker, Debuffs.crimsonFlare)){
				LivingDebuff.addLivingDebuff(parent.invoker, new LivingStateCrimsonFlare(Debuffs.crimsonFlare,100,xyz.x,xyz.y,xyz.z,amp,-1));

			}
			
		}
		
	}
	public class SpellDetectAnimal extends SpellDetectEntity{

		public SpellDetectAnimal() {
			super();
			// TODO 自動生成されたコンストラクター・スタブ
		}

		@Override
		public void addEntityList(InvokeSpell parent,Multimap<String,Integer> entityList,EntityLivingBase ent){
			if(ent instanceof IAnimals && !(ent instanceof EntityAmbientCreature || ent instanceof IMob)&& ent!=parent.invoker){
				
				int distance = (int) (ent.getDistanceSqToEntity(parent.invoker));

				entityList.put(ent.getCommandSenderName(), distance);

			}
		}
		
		
	}
	
	public class SpellDetectBlood extends SpellDetectEntity{

		public SpellDetectBlood() {
			super();
			// TODO 自動生成されたコンストラクター・スタブ
		}

		@Override
		public void addEntityList(InvokeSpell invoke,
				Multimap<String, Integer> entityList, EntityLivingBase ent) {
			if(ent instanceof IMob && ent!=invoke.invoker){
				if(!(ent.getCreatureAttribute()==EnumCreatureAttribute.UNDEAD)){
					
					int distance = (int) (ent.getDistanceSqToEntity(invoke.invoker));

					entityList.put(ent.getCommandSenderName(), distance);
					if(this.isAmplified){
						LivingDebuff.addDebuff(ent, Debuffs.detected, (int) (20*invoke.getAmp()));
					}
				}

			}
			
		}
		
	}
	public class SpellRecycle extends SpellBase{

		public SpellRecycle() {
			super();
			// TODO 自動生成されたコンストラクター・スタブ
		}

		@Override
		public void invokeSpell(InvokeSpell spell) {
			ItemStack is = null;
			if(spell.invoker instanceof EntityPlayer){
				is = ((EntityPlayer)spell.invoker).inventory.getStackInSlot(0);
			}else{
				is = spell.invoker.getHeldItem();
			}

			//HashSet<Class> validClasses = Sets.newHashSet(ItemSword.class,ItemAxe.class,ItemTool.class,ItemArmor.class,Item
			if(is!=null && is.getItem().isRepairable()){
				int repair = 20+spell.invoker.getRNG().nextInt(15);
				String str = Translation.localize("msg.spell.repair");
				String formatted = String.format(str, repair);
				ChatUtil.addMessageNoLocalized(spell.invoker, formatted);
				//spell.invoker.addChatMessage(formatted);
				if(!spell.world.isRemote){
					is.setItemDamage(-repair);
				}
			}
			
		}
		
	}
	public class SpellBoulder extends SpellBase{

		public SpellBoulder() {
			super();
			// TODO 自動生成されたコンストラクター・スタブ
		}

		@Override
		public void invokeSpell(InvokeSpell spell) {
			spell.world.playSoundAtEntity(spell.invoker, "mob.ghast.fireball", 1.0F, 1.0F / (spell.world.rand.nextFloat() * 0.4F + 1.2F) + 1.0F * 0.5F);
			EntityBoulder var8 = new EntityBoulder(spell.world, spell.invoker, 1.0F*1.5F);
			int knockback = Math.round(5.0F*spell.getAmp());
			if(knockback>12){
				knockback=12;
			}
			if(knockback<1){
				knockback=1;
			}
			var8.setKnockbackStrength(knockback);
			var8.canBePickedUp = 0;
			var8.setDamage(var8.getDamage()+(1.0*spell.getAmp()));
			if (!spell.world.isRemote)
			{
				spell.world.spawnEntityInWorld(var8);
			}
			return;
			
		}
		
	}
	public class SpellMagicLock extends SpellBase{

		public SpellMagicLock() {
			super();
			// TODO 自動生成されたコンストラクター・スタブ
		}

		@Override
		public void invokeSpell(InvokeSpell spell) {
			EntityLivingBase target = null;
			if(spell.getTarget().isPresent()){
				target = spell.getTarget().get();

			}else{
				target = LockOnHelper.searchEntityNear(spell.invoker, Debuffs.spellTarget);
			}
			if(target instanceof EntitySlime || target instanceof EntityTreasureSlime){
				//EntitySlime slime = (EntitySlime)spell.getTarget().get();
				target.addPotionEffect(new PotionEffect(Potion.weakness.id, 100*(int)spell.getAmp(),2*(int)spell.getAmp()));
				LivingDebuff.addDebuff(target, Debuffs.lockSlime, 30);
			}
			
		}
		
	}
	public class SpellFireArrow extends SpellBase{

		public SpellFireArrow() {
			super();
			// TODO 自動生成されたコンストラクター・スタブ
		}

		@Override
		public void invokeSpell(InvokeSpell parent) {
			PacketSound ps = new PacketSound(1008,parent.invoker.getEntityId(),PacketSound.MODE.AUX);
			Unsaga.packetPipeline.sendToAllAround(ps, PacketUtil.getTargetPointNear(parent.invoker));
			//PacketDispatcher.sendPacketToPlayer(ps.getPacket(),(Player)parent.invoker);
			EntityFireArrow var8 = new EntityFireArrow(parent.world, parent.invoker, 1.5F*1.5F);
			var8.setFire(100);
			var8.canBePickedUp = 0;
			var8.setDamage(var8.getDamage()+(int)(1.0F*parent.getAmp()));
			if (!parent.world.isRemote)
			{
				parent.world.spawnEntityInWorld(var8);
			}
			return;
			
		}
		
	}
	public class SpellAnimalCharm extends SpellBase{

		public SpellAnimalCharm() {
			super();
			// TODO 自動生成されたコンストラクター・スタブ
		}

		@Override
		public void invokeSpell(InvokeSpell spell) {
			if(spell.getTarget().isPresent()){
				if(spell.getTarget().get() instanceof EntityTameable){
					EntityTameable tameable = (EntityTameable)spell.getTarget().get();
					tameable.setTamed(true);
					tameable.setOwner(spell.invoker.getCommandSenderName());
					
					PacketParticle pp = new PacketParticle(2,tameable.getEntityId(),10);
					if(!spell.invoker.worldObj.isRemote){
						Unsaga.packetPipeline.sendTo(pp, (EntityPlayerMP) spell.invoker);
					}
					
					//PacketDispatcher.sendPacketToPlayer(pp.getPacket(), (Player)spell.invoker);
					return;
				}
				if(spell.invoker instanceof EntityPlayer){
					if(spell.getTarget().get() instanceof EntityHorse){
						EntityHorse horse = (EntityHorse)spell.getTarget().get();
						horse.setTamedBy((EntityPlayer) spell.invoker);
						PacketParticle pp = new PacketParticle(2,horse.getEntityId(),10);
						if(!spell.invoker.worldObj.isRemote){
							Unsaga.packetPipeline.sendTo(pp, (EntityPlayerMP) spell.invoker);
						}
						return;

					}
					if(spell.getTarget().get() instanceof EntityAnimal){
						EntityAnimal animal = (EntityAnimal)spell.getTarget().get();
						animal.func_146082_f((EntityPlayer) spell.invoker);
						return;

					}
				}

			}
			
		}
		
	}
	public class SpellElementVeil extends SpellBase{

		public SpellElementVeil() {
			super();
			// TODO 自動生成されたコンストラクター・スタブ
		}

		@Override
		public void invokeSpell(InvokeSpell parentInvoke) {
			int remain = (int)((float)20 * parentInvoke.getAmp());
			switch(parentInvoke.spell.element){
			case FIRE:
				LivingDebuff.addDebuff(parentInvoke.invoker, Debuffs.fireVeil, remain);
				break;
			case WATER:
				LivingDebuff.addDebuff(parentInvoke.invoker, Debuffs.waterVeil, remain);
				break;
			case EARTH:
				LivingDebuff.addDebuff(parentInvoke.invoker, Debuffs.earthVeil, remain);
				break;
			case WOOD:
				LivingDebuff.addDebuff(parentInvoke.invoker, Debuffs.woodVeil, remain);
				break;
			case METAL:
				LivingDebuff.addDebuff(parentInvoke.invoker, Debuffs.metalVeil, remain);
				break;
			case FORBIDDEN:
				break;
			default:
				break;
			}
			
		}
		
	}
	public class SpellHeroism extends SpellAddBuff{

		public SpellHeroism() {
			super();
			this.potions = Lists.newArrayList(Potion.damageBoost);
		}

		
	}
	public class SpellMissuileGuard extends SpellAddBuff{

		public SpellMissuileGuard() {
			super();
			this.buffs = Lists.newArrayList(Debuffs.missuileGuard);
		}
		
	}

	public class SpellLifeBoost extends SpellAddBuff{

		public SpellLifeBoost() {
			super();
			this.buffs = Lists.newArrayList(Debuffs.lifeBoost);
		}
		
	}

	public class SpellMeditation extends SpellHealing{

		public HashSet<Debuff> restoreDebuffs = Sets.newHashSet(Debuffs.downMagic,Debuffs.downPhysical,Debuffs.downSkill);
		public HashSet<Potion> restorePotionEffects = Sets.newHashSet(Potion.digSlowdown,Potion.digSpeed,Potion.moveSlowdown,Potion.weakness);
		public HashSet<Buff> addBuffs = Sets.newHashSet(Debuffs.magicUp,Debuffs.perseveranceUp);
		
		
		public SpellMeditation() {
			super();
			// TODO 自動生成されたコンストラクター・スタブ
		}

		
		@Override
		public void hookHealing(InvokeSpell parent, EntityLivingBase target) {
			for(Debuff debuff:restoreDebuffs){
				LivingDebuff.removeDebuff(target, debuff);
			}
			for(Potion potion:restorePotionEffects){
				target.removePotionEffect(potion.id);
			}
			for(Buff buff:addBuffs){
				LivingDebuff.addDebuff(target, buff, 15*(int)parent.getAmp());
			}
			//LivingDebuff.addDebuff(target, DebuffRegistry.);
		}


		
	}
	

	public class SpellCloudCall extends SpellBase{

		public SpellCloudCall() {
			super();
			// TODO 自動生成されたコンストラクター・スタブ
		}

		@Override
		public void invokeSpell(InvokeSpell parent) {
			parent.world.getWorldInfo().setRaining(true);
			
		}
		
	}
	public class SpellDetectGold extends SpellBase{

		public SpellDetectGold() {
			super();
			// TODO 自動生成されたコンストラクター・スタブ
		}

		@Override
		public void invokeSpell(InvokeSpell parent) {
			int prob = (int)(25.0F * parent.getAmp());
			boolean diacheck = parent.world.rand.nextInt(100)<prob;
			ScanHelper scan = new ScanHelper(parent.invoker,16,16);
			StringBuilder builder = new StringBuilder();
			PairIDList pairList = new PairIDList();
			scan.setWorld(parent.world);
			for(;scan.hasNext();scan.next()){
				if(!scan.isAirBlock() && scan.sy>0 && scan.sy<255){
					//Block block = scan.getBlock();
					PairID blocknumber = this.worldHelper.getBlockDatas(scan.getAsXYZPos());
					boolean flag = false;
					if(blocknumber.blockObj instanceof BlockOre || blocknumber.blockObj instanceof BlockRedstoneOre){
						Unsaga.debug(blocknumber);
						if(blocknumber.blockObj==Blocks.diamond_ore){
							if(diacheck){
								if(pairList.contains(blocknumber)){
									pairList.addStack(blocknumber, 1);
								}else{
									pairList.list.add(blocknumber.setStack(1));
								}
							}

						}else{
							if(pairList.contains(blocknumber)){
								pairList.addStack(blocknumber, 1);
							}else{
								pairList.list.add(blocknumber.setStack(1));
							}
						}

						flag = true;

					}
					String orekey = OreDictionary.getOreName(OreDictionary.getOreID(new ItemStack(blocknumber.blockObj,1,scan.getMetadata())));
					if(!orekey.equals("Unknown") && orekey.toLowerCase().contains("ore") && !flag){
						if(pairList.contains(blocknumber)){
							pairList.addStack(blocknumber, 1);
						}else{
							pairList.list.add(blocknumber.setStack(1));
						}
					}
				}
			}
			if(!pairList.list.isEmpty()){
				for(PairID pairid:pairList.list){
					String name = pairid.blockObj.getLocalizedName(); //"Unknown";
					//name = HSLibs.getItemNameFromPair(pairid);
					builder.append(name).append(":").append(pairid.stack).append("/");
				}
			}

			String message = new String(builder);
			if(message.equals("")){
				ChatUtil.addMessage(parent.invoker, "msg.spell.metal.notfound");
				//parent.invoker.addChatMessage(Translation.localize("msg.spell.metal.notfound"));
			}else{
				ChatUtil.addMessageNoLocalized(parent.invoker, message);
				//parent.invoker.addChatMessage(message);
			}

			return;
			
		}
		
	}

	public class SpellAbyss extends SpellBase{

		public SpellAbyss() {
			super();
			// TODO 自動生成されたコンストラクター・スタブ
		}

		@Override
		public void invokeSpell(InvokeSpell parent) {
			float amp = parent.getAmp();
			AxisAlignedBB bb = parent.invoker.boundingBox.expand(9.0D+amp, 4.0D+amp, 9.0D+amp);
			Entity nearent = parent.world.findNearestEntityWithinAABB(EntityLivingBase.class, bb, parent.invoker);

			if(nearent!=null){
				if(HSLibs.isEnemy(nearent,parent.invoker)){
					EntityLiving nearTarget = (EntityLiving)nearent;

					if(amp<1.0){
						amp=1.0F;
					}
					if(amp>4.0){
						amp=4.0F;
					}
					float f3 = 0.25F;
					
					PacketParticle pk = new PacketParticle(1,nearTarget.getEntityId(),25);
					Unsaga.packetPipeline.sendToAllAround(pk, PacketUtil.getTargetPointNear(nearTarget));
					//PacketDispatcher.sendPacketToAllPlayers(pk.getPacket());
					
					//PacketSound ps = new PacketSound(el.getEntityId(),1,1);
					//PacketDispatcher.sendPacketToPlayer(ps.getPacket(),(Player)parent.invoker);
					nearTarget.playSound("mob.endermen.portal", 1.0F, 1.0F);
					int rand1 = parent.world.rand.nextInt(30*(int)amp);
					if(rand1>100){
						rand1 = 100;
					}
					int at = 8*(int)amp;
					System.out.println(at);
					//DamageSource ds = new DamageSourceUnsaga(null, parent.invoker, parent.getSpell().hurtLP, DamageHelper.Type.MAGIC);
					
					nearTarget.attackEntityFrom(parent.getDamageSource(), at);

					int time = Math.round((float)240*amp);
					if(time>600){
						time = 600;
					}
					if(parent.world.rand.nextInt(100)<=rand1){
						nearTarget.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id,time , (int)amp));
					}
					//UtilSkill.tryLPHurt(15, 1, el, par3EntityPlayer);
				}

			}
			return;
			
		}
		
	}
	public class SpellWeakness extends SpellBase{

		public SpellWeakness() {
			super();
			// TODO 自動生成されたコンストラクター・スタブ
		}

		@Override
		public void invokeSpell(InvokeSpell parent) {
			AxisAlignedBB bb = parent.invoker.boundingBox.expand(9.0D, 4.0D, 9.0D);

			System.out.println(bb);

			float amp = parent.getAmp();
			Entity nearent = parent.world.findNearestEntityWithinAABB(EntityLiving.class, bb, parent.invoker);

			System.out.println(nearent);
			if(nearent!=null){
				//UnsagaCore.logc(par3EntityPlayer, Translation.trJP("Sucess")+" "+Translation.trJP("Weakness"));


				if(HSLibs.isEnemy(nearent, parent.invoker)){
					EntityLiving el = (EntityLiving)nearent;

					if(amp<1.0){
						amp=1.0F;
					}
					if(amp>4.0){
						amp=4.0F;
					}
					int time = Math.round((float)250*amp);
					if(time>1000){
						time = 1000;
					}
					el.addPotionEffect(new PotionEffect(Potion.weakness.id,time , (int)amp));
				}

			}
			return;
			
		}
		
	}

	public class SpellOverGrowth extends SpellBase{

		public SpellOverGrowth() {
			super();
			// TODO 自動生成されたコンストラクター・スタブ
		}

		@Override
		public void invokeSpell(InvokeSpell parent) {
			int range = 10;
			int prob = Math.round(30*parent.getAmp());




			ScanHelper scan = new ScanHelper(parent.invoker,8,6);

			for(;scan.hasNext();scan.next()){
				ItemStack dummy = new ItemStack(Items.dye,1,0);
				if(parent.world.rand.nextInt(100)<prob){
					PacketParticle pp = new PacketParticle(new XYZPos(scan.sx,scan.sy,scan.sz),3,5);
					if(!parent.world.isRemote){
						Unsaga.packetPipeline.sendTo(pp, (EntityPlayerMP) parent.invoker);
					}
					
					if(parent.invoker instanceof EntityPlayer){
						ItemDye.applyBonemeal(dummy, parent.world, scan.sx, scan.sy, scan.sz, (EntityPlayer) parent.invoker);
					}
					
				}

			}

			return;
			
		}
		
	}
	public class SpellFireWall extends SpellBase{

		public SpellFireWall() {
			super();
			// TODO 自動生成されたコンストラクター・スタブ
		}

		@Override
		public void invokeSpell(InvokeSpell parent) {
			if(ItemSpellBook.readPosition(parent.spellbook)!=null){
				if(!parent.world.isRemote){
					int ampli = (int)(3.0F + parent.getAmp());
					ampli = MathHelper.clamp_int(ampli, 0, 12);
					XYZPos pos = ItemSpellBook.readPosition(parent.spellbook);
					XYZPos start = new XYZPos(pos.x-1,pos.y+5,pos.z-1);
					XYZPos end = new XYZPos(pos.x+1,pos.y+1,pos.z+1);
					XYZPos[] swapped = XYZPos.swap(start, end);
					ScanHelper scan = new ScanHelper(swapped[0],swapped[1]);
					scan.setWorld(parent.world);
					for(;scan.hasNext();scan.next()){
						boolean flag = false;
						flag = scan.isAirBlock() ? true : false;
						if(!scan.isAirBlock() && scan.getBlock().isReplaceable(parent.world, scan.sx, scan.sy, scan.sz)){
							flag = true;
						}

						if(flag){
							parent.world.setBlock(scan.sx, scan.sy, scan.sz, UnsagaMagic.blockFireWall,ampli,3);
						}


					}
				}

			}else{
				ChatUtil.addMessage(parent.invoker, "msg.spell.notfound.position");
				//parent.invoker.addChatMessage(Translation.localize("msg.spell.notfound.position"));
			}
			
		}
		
	}


}

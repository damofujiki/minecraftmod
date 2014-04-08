package hinasch.mods.unlsagamagic.misc.spell.effect;

import hinasch.mods.unlsaga.Unsaga;
import hinasch.mods.unlsaga.entity.EntityTreasureSlime;
import hinasch.mods.unlsaga.entity.projectile.EntityBoulderNew;
import hinasch.mods.unlsaga.entity.projectile.EntityFireArrowNew;
import hinasch.mods.unlsaga.misc.debuff.Buff;
import hinasch.mods.unlsaga.misc.debuff.Debuff;
import hinasch.mods.unlsaga.misc.debuff.Debuffs;
import hinasch.mods.unlsaga.misc.debuff.livingdebuff.LivingDebuff;
import hinasch.mods.unlsaga.misc.debuff.livingdebuff.LivingStateFireStorm;
import hinasch.mods.unlsaga.misc.translation.Translation;
import hinasch.mods.unlsaga.misc.util.ChatUtil;
import hinasch.mods.unlsaga.misc.util.DamageHelper;
import hinasch.mods.unlsaga.misc.util.DamageSourceUnsaga;
import hinasch.mods.unlsaga.misc.util.LockOnHelper;
import hinasch.mods.unlsaga.network.packet.PacketHandlerClientThunder;
import hinasch.mods.unlsaga.network.packet.PacketParticle;
import hinasch.mods.unlsaga.network.packet.PacketUtil;
import hinasch.mods.unlsagamagic.UnsagaMagic;
import hinasch.mods.unlsagamagic.item.ItemSpellBook;
import hinasch.mods.unlsagamagic.tileentity.TileEntityFireWall;

import java.util.HashSet;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.block.BlockOre;
import net.minecraft.block.BlockRedstoneOre;
import net.minecraft.entity.Entity;
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
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraftforge.oredict.OreDictionary;

import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import com.hinasch.lib.HSLibs;
import com.hinasch.lib.PairID;
import com.hinasch.lib.PairIDList;
import com.hinasch.lib.RangeDamageHelper;
import com.hinasch.lib.ScanHelper;
import com.hinasch.lib.StaticWords;
import com.hinasch.lib.XYZPos;

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
	public final SpellBase superSonic = new SpellSuperSonic(5.0D,5.0D);
	public final SpellBase waterShield = new SpellWaterShield();

	public static SpellEffectNormal getInstance(){
		if(INSTANCE==null){
			INSTANCE = new SpellEffectNormal();
		}
		return INSTANCE;

	}
	public class SpellSuperSonic extends SpellRangedAttack{

		public SpellSuperSonic(double horizontal, double vertical) {
			super(horizontal, vertical);
			
		}
		
		@Override
		public void hookStart(InvokeSpell parent){
			parent.playExplodeSound(XYZPos.entityPosToXYZ(parent.getInvoker()));
			PacketParticle pp = new PacketParticle(StaticWords.getParticleNumber(StaticWords.particleExplode),parent.getInvoker().getEntityId(),1);
			Unsaga.packetPipeline.sendToAllAround(pp, PacketUtil.getTargetPointNear(parent.getInvoker()));
		}
		
		@Override
		public RangeDamageHelper getCustomizedRangeDamageHelper(InvokeSpell parent){
			return new RangeDamageSuperSonic(parent);
		}
		
		@Override
		public DamageSource getDamageSource(InvokeSpell parent){
			DamageSourceUnsaga ds = parent.getDamageSource();
			ds.setSubDamageType(DamageHelper.SubType.LIGHT);
			return ds;
		}
		
		public class RangeDamageSuperSonic extends RangeDamageHelper{

			protected InvokeSpell parent;
			public RangeDamageSuperSonic(InvokeSpell parent) {
				super(parent.world);
				this.parent = parent;
			}
			

			
			@Override
			public void takeEntityLiving(EntityLivingBase living,DamageSource source){
				if(living.getCreatureAttribute()==EnumCreatureAttribute.ARTHROPOD || living instanceof EntityAnimal){
					LivingDebuff.addLivingDebuff(living, new LivingDebuff(Debuffs.sleep,(int) (10*parent.getAmp())));
				}
			}
		}
	}
	
	public class SpellCallThunder extends SpellBase{

		public SpellCallThunder() {

		}

		@Override
		public void invokeSpell(InvokeSpell parentInvoke) {
			if(parentInvoke.getTarget().isPresent()){
				Entity target = parentInvoke.getTarget().get();
				EntityLightningBolt thunder = new EntityLightningBolt(parentInvoke.world,target.posX,target.posY,target.posZ);
				parentInvoke.world.spawnEntityInWorld(thunder);
				DamageSourceUnsaga ds = parentInvoke.getDamageSource();
				ds.setSubDamageType(DamageHelper.SubType.ELECTRIC);
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
				HSLibs.removePotionEffects(target, Potion.poison,Potion.wither,Potion.blindness);
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
				EntityLivingBase nearent = LockOnHelper.searchEntityNear(parent.getInvoker(), Debuffs.spellTarget);
				if(nearent!=null){
					xyz = XYZPos.entityPosToXYZ(nearent);
				}
			}
			if(xyz!=null && !LivingDebuff.hasDebuff(parent.getInvoker(), Debuffs.crimsonFlare)){
				LivingDebuff.addLivingDebuff(parent.getInvoker(), new LivingStateFireStorm(Debuffs.crimsonFlare,100,xyz.x,xyz.y,xyz.z,amp,-1));

			}
			
		}
		
	}
	public class SpellDetectAnimal extends SpellDetectEntity{

		public SpellDetectAnimal() {
			super();
			// TODO 自動生成されたコンストラクター・スタブ
		}

		@Override
		public void addEntityList(InvokeSpell parent,Multimap<String,Object> entityList,EntityLivingBase ent){
			if(ent instanceof IAnimals && !(ent instanceof EntityAmbientCreature || ent instanceof IMob)&& ent!=parent.getInvoker()){
				XYZPos distance_pos = XYZPos.entityPosToXYZ(ent).subtract(XYZPos.entityPosToXYZ(parent.getInvoker()));
				String hpstr = "[H:"+Math.round(ent.getHealth())+"]";
				distance_pos.setAsBlockPos(true);

				entityList.put(ent.getCommandSenderName(), distance_pos+hpstr);

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
				Multimap<String, Object> entityList, EntityLivingBase ent) {
			if(ent instanceof IMob && ent!=invoke.getInvoker()){
				if(!(ent.isEntityUndead())){
					
					XYZPos distance_pos = XYZPos.entityPosToXYZ(ent).subtract(XYZPos.entityPosToXYZ(invoke.getInvoker()));
					distance_pos.setAsBlockPos(true);
					String hpstr = "[H:"+Math.round(ent.getHealth())+"]";
					entityList.put(ent.getCommandSenderName(), distance_pos+hpstr);

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
			if(spell.getInvoker() instanceof EntityPlayer){
				is = ((EntityPlayer)spell.getInvoker()).inventory.getStackInSlot(0);
			}else{
				is = spell.getInvoker().getHeldItem();
			}

			//HashSet<Class> validClasses = Sets.newHashSet(ItemSword.class,ItemAxe.class,ItemTool.class,ItemArmor.class,Item
			if(is!=null && is.getItem().isRepairable()){
				int repair = 20+spell.getInvoker().getRNG().nextInt(15);
				String str = Translation.localize("msg.spell.repair");
				String formatted = String.format(str, repair);
				ChatUtil.addMessageNoLocalized(spell.getInvoker(), formatted);
				//spell.getInvoker().addChatMessage(formatted);
				if(!spell.world.isRemote){
					is.setItemDamage(-repair);
				}
			}
			
		}
		
	}
	public class SpellBoulder extends SpellProjectile{

		public SpellBoulder() {
			super();
			// TODO 自動生成されたコンストラクター・スタブ
		}

		@Override
		public Entity getProjectileEntity(InvokeSpell spell){
			EntityBoulderNew var8 = new EntityBoulderNew(spell.world, spell.getInvoker(), 1.0F*1.5F);
			int knockback = Math.round(5.0F*spell.getAmp());
			knockback = MathHelper.clamp_int(knockback, 1, 12);
			var8.setKnockBackModifier(knockback);
			var8.setDamage(spell.getSpell().hurtHP*spell.getAmp());
			return var8;
			
		}
		
	}
	public class SpellMagicLock extends SpellBase{

		protected Set<Class<? extends Entity>> classesEntity = new HashSet();
		
		public SpellMagicLock() {
			super();
			classesEntity.add(EntitySlime.class);
			classesEntity.add(EntityTreasureSlime.class);
		}

		@Override
		public void invokeSpell(InvokeSpell spell) {
			EntityLivingBase target = null;
			if(spell.getTarget().isPresent()){
				target = spell.getTarget().get();

			}else{
				target = LockOnHelper.searchEntityNear(spell.getInvoker(), Debuffs.spellTarget);
			}
			if(HSLibs.instanceOf(target, classesEntity)){
				target.addPotionEffect(new PotionEffect(Potion.weakness.id, 100*(int)spell.getAmp(),2*(int)spell.getAmp()));
				LivingDebuff.addDebuff(target, Debuffs.lockSlime, 30);
			}
			
		}
		
	}
	
	public class SpellFireArrow extends SpellProjectile{

		public SpellFireArrow() {
			super();
			// TODO 自動生成されたコンストラクター・スタブ
		}
		
		@Override
		public Entity getProjectileEntity(InvokeSpell parent){
			EntityFireArrowNew firearrow = new EntityFireArrowNew(parent.world, parent.getInvoker(), 1.5F*1.5F);
			firearrow.setFire(100);
			firearrow.setDamage(parent.spell.hurtHP*parent.getAmp());
			return firearrow;
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
					tameable.setOwner(spell.getInvoker().getCommandSenderName());
					
					PacketParticle pp = new PacketParticle(2,tameable.getEntityId(),10);
					if(!spell.getInvoker().worldObj.isRemote){
						Unsaga.packetPipeline.sendTo(pp, (EntityPlayerMP) spell.getInvoker());
					}
					
					//PacketDispatcher.sendPacketToPlayer(pp.getPacket(), (Player)spell.getInvoker());
					return;
				}
				if(spell.getInvoker() instanceof EntityPlayer){
					if(spell.getTarget().get() instanceof EntityHorse){
						EntityHorse horse = (EntityHorse)spell.getTarget().get();
						horse.setTamedBy((EntityPlayer) spell.getInvoker());
						PacketParticle pp = new PacketParticle(2,horse.getEntityId(),10);
						if(!spell.getInvoker().worldObj.isRemote){
							Unsaga.packetPipeline.sendTo(pp, (EntityPlayerMP) spell.getInvoker());
						}
						return;

					}
					if(spell.getTarget().get() instanceof EntityAnimal){
						EntityAnimal animal = (EntityAnimal)spell.getTarget().get();
						animal.func_146082_f((EntityPlayer) spell.getInvoker());
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
				LivingDebuff.addDebuff(parentInvoke.getInvoker(), Debuffs.fireVeil, remain);
				break;
			case WATER:
				LivingDebuff.addDebuff(parentInvoke.getInvoker(), Debuffs.waterVeil, remain);
				break;
			case EARTH:
				LivingDebuff.addDebuff(parentInvoke.getInvoker(), Debuffs.earthVeil, remain);
				break;
			case WOOD:
				LivingDebuff.addDebuff(parentInvoke.getInvoker(), Debuffs.woodVeil, remain);
				break;
			case METAL:
				LivingDebuff.addDebuff(parentInvoke.getInvoker(), Debuffs.metalVeil, remain);
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
			//this.buffs = Lists.newArrayList(Debuffs.powerup);
			this.potions = Lists.newArrayList(Potion.damageBoost);
		}

		
	}
	public class SpellMissuileGuard extends SpellAddBuff{

		public SpellMissuileGuard() {
			super();
			this.buffs = Lists.newArrayList(Debuffs.missuileGuard);
		}
		
	}

	public class SpellWaterShield extends SpellAddBuff{

		public SpellWaterShield() {
			super();
			this.buffs = Lists.newArrayList(Debuffs.waterShield);
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
			this.isSelf = true;
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

		public Set<Class<? extends Block>> blockClasses = Sets.newHashSet(BlockOre.class,BlockRedstoneOre.class);
		public SpellDetectGold() {
			super();
			// TODO 自動生成されたコンストラクター・スタブ
		}

		@Override
		public void invokeSpell(InvokeSpell parent) {
			int prob = (int)(25.0F * parent.getAmp());
			boolean diacheck = parent.world.rand.nextInt(100)<prob;
			ScanHelper scan = new ScanHelper(parent.getInvoker(),16,16);
			StringBuilder builder = new StringBuilder();
			PairIDList pairList = new PairIDList();
			scan.setWorld(parent.world);
			for(;scan.hasNext();scan.next()){
				if(!scan.isAirBlock() && scan.isValidHeight()){

					PairID blocknumber = this.worldHelper.getBlockDatas(scan.getAsXYZPos());
					boolean flag = false;
					Unsaga.debug(blocknumber);
					if(HSLibs.instanceOf(blocknumber.getBlockObject(),blockClasses)){

						if(blocknumber.getBlockObject()==Blocks.diamond_ore){
							if(diacheck){
								pairList.addStack(blocknumber, 1);
							}

						}else{
							pairList.addStack(blocknumber, 1);
						}

						flag = true;

					}
					String orekey = OreDictionary.getOreName(OreDictionary.getOreID(new ItemStack(blocknumber.getBlockObject(),1,scan.getMetadata())));
					if(!orekey.equals("Unknown") && orekey.toLowerCase().contains("ore") && !flag){
						pairList.addStack(blocknumber, 1);
					}
				}
			}
			if(!pairList.list.isEmpty()){
				for(PairID pairid:pairList.list){
					String name = pairid.getBlockObject().getLocalizedName(); //"Unknown";
					builder.append(name).append(":").append(pairid.stack).append("/");
				}
			}

			String message = new String(builder);
			if(message.equals("")){
				ChatUtil.addMessage(parent.getInvoker(), "msg.spell.metal.notfound");
			}else{
				ChatUtil.addMessageNoLocalized(parent.getInvoker(), message);
			}

			return;
			
		}
		
	}

	public class SpellAbyss extends SpellBase{

		public SpellAbyss() {
			super();
		}

		@Override
		public void invokeSpell(InvokeSpell parent) {
			float amp = parent.getAmp();
			
			EntityLivingBase target = parent.getTargetOrfindTarget();


			if(target!=null){
				PacketParticle pk = new PacketParticle(1,target.getEntityId(),25);
				Unsaga.packetPipeline.sendToAllAround(pk, PacketUtil.getTargetPointNear(target));
				target.playSound("mob.endermen.portal", 1.0F, 1.0F);
				DamageSourceUnsaga ds = parent.getDamageSource();
				target.attackEntityFrom(ds, parent.spell.hurtHP);
				int time = HSLibs.getPotionTime((int) (15*parent.getAmp()));
				if(parent.world.rand.nextDouble()<=0.5D*parent.getAmp()){
					target.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id,time , (int)amp));
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
			EntityLivingBase target = parent.getTargetOrfindTarget();
			int amp = (int) (1*parent.getAmp());
			
			if(target!=null){
				int time = HSLibs.getPotionTime((int) (25*parent.getAmp()));
				target.addPotionEffect(new PotionEffect(Potion.weakness.id,time , amp));
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




			ScanHelper scan = new ScanHelper(parent.getInvoker(),8,6);
			scan.setWorld(parent.world);

			for(;scan.hasNext();scan.next()){
				ItemStack dummy = new ItemStack(Items.dye,1,0);
				if(parent.world.rand.nextInt(100)<prob){
					PacketParticle pp = new PacketParticle(scan.getAsXYZPos(),3,5);
					Unsaga.packetPipeline.sendToAllAround(pp, PacketUtil.getTargetPointNear(scan.getAsXYZPos(), scan.world));
					
					
					if(parent.getInvoker() instanceof EntityPlayer){
						ItemDye.applyBonemeal(dummy, parent.world, scan.sx, scan.sy, scan.sz, (EntityPlayer) parent.getInvoker());
					}
					
				}

			}

			return;
			
		}
		
	}
	public class SpellFireWall extends SpellBase{

		public SpellFireWall() {
			super();
		}

		@Override
		public void invokeSpell(InvokeSpell parent) {
			if(ItemSpellBook.readPosition(parent.spellbook)!=null){
				if(!parent.world.isRemote){
					int ampli = (int)(800 * parent.getAmp());
					ampli = MathHelper.clamp_int(ampli, 10, 3000);
					XYZPos pos = ItemSpellBook.readPosition(parent.spellbook);
					XYZPos start = new XYZPos(pos.x-1,pos.y+5,pos.z-1);
					XYZPos end = new XYZPos(pos.x+1,pos.y+1,pos.z+1);
					XYZPos[] swapped = XYZPos.swap(start, end);
					ScanHelper scan = new ScanHelper(swapped[0],swapped[1]);
					scan.setWorld(parent.world);
					
					for(;scan.hasNext();scan.next()){
						boolean flag = false;
						flag = scan.isAirBlock() ? true : false;
						if(!scan.isAirBlock() && this.worldHelper.isReplaceable(scan.getAsXYZPos())){
							flag = true;
						}

						if(flag){
							this.worldHelper.setBlock(scan.getAsXYZPos(), new PairID(UnsagaMagic.blockFireWall,0));
							TileEntity te = this.worldHelper.getTileEntity(scan.getAsXYZPos());
							if(te instanceof TileEntityFireWall){
								((TileEntityFireWall) te).init(ampli);
								HSLibs.sendDescriptionPacketToAllPlayer(parent.world, te);
							}
						}


					}
				}

			}else{
				ChatUtil.addMessage(parent.getInvoker(), "msg.spell.notfound.position");
			}
			
		}
		
	}


}

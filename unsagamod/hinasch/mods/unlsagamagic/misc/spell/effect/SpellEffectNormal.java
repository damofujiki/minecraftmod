package hinasch.mods.unlsagamagic.misc.spell.effect;

import hinasch.lib.HSLibs;
import hinasch.lib.ScanHelper;
import hinasch.mods.unlsaga.misc.debuff.DebuffRegistry;
import hinasch.mods.unlsaga.misc.debuff.LivingBuff;
import hinasch.mods.unlsaga.misc.debuff.LivingDebuff;
import hinasch.mods.unlsaga.network.PacketHandler;
import hinasch.mods.unlsagamagic.entity.EntityBoulder;
import hinasch.mods.unlsagamagic.entity.EntityFireArrow;
import hinasch.mods.unlsagamagic.misc.spell.Spell;
import hinasch.mods.unlsagamagic.misc.spell.SpellRegistry;

import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.block.BlockOre;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;

public class SpellEffectNormal extends SpellEffect{

	
	@Override
	public void doEffect(InvokeSpell spellinvoke){
		this.parentInvoke = spellinvoke;
		EntityPlayer invoker = this.parentInvoke.invoker;
		float amp = this.parentInvoke.getAmp();
		Spell spell = this.parentInvoke.spell;
		if(spell==SpellRegistry.fireArrow)this.doFireArrow(parentInvoke);
		if(spell==SpellRegistry.purify)this.doPurify(this.parentInvoke);
		if(spell==SpellRegistry.fireVeil)this.doVeil(this.parentInvoke);
		if(spell==SpellRegistry.waterVeil)this.doVeil(this.parentInvoke);
		if(spell==SpellRegistry.woodVeil)this.doVeil(this.parentInvoke);
		if(spell==SpellRegistry.stoneVeil)this.doVeil(this.parentInvoke);
		if(spell==SpellRegistry.metalVeil)this.doVeil(this.parentInvoke);
		if(spell==SpellRegistry.detectGold)this.doDectectGold(parentInvoke);
		if(spell==SpellRegistry.missuileGuard)this.doMissuileGuard(parentInvoke);
		if(spell==SpellRegistry.heroism)this.doHeroism(parentInvoke);
		if(spell==SpellRegistry.abyss)this.doAbyss(parentInvoke);
		if(spell==SpellRegistry.weakness)this.doWeakness(parentInvoke);
		if(spell==SpellRegistry.overGrowth)this.doOvergrowth(parentInvoke);
		if(spell==SpellRegistry.cloudCall)this.doCloudCall(parentInvoke);
		if(spell==SpellRegistry.animalCharm)this.doAnimalCharm(parentInvoke);
		if(spell==SpellRegistry.lifeBoost)this.doLifeBoost(parentInvoke);
		if(spell==SpellRegistry.boulder)this.doBoulder(parentInvoke);

		
	}
	

	public void doBoulder(InvokeSpell spell){

		
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
	public void doMagicLock(InvokeSpell spell){
		if(spell.getTarget().isPresent()){
			if(spell.getTarget().get() instanceof EntitySlime){
				EntitySlime slime = (EntitySlime)spell.getTarget().get();
				slime.addPotionEffect(new PotionEffect(Potion.weakness.id, 100*(int)spell.getAmp(),2*(int)spell.getAmp()));
				LivingDebuff.addDebuff(slime, DebuffRegistry.sleep, 15);
			}
		}
	}
	
	public void doFireArrow(InvokeSpell parent){

		PacketDispatcher.sendPacketToPlayer(PacketHandler.getSoundPacket(1008, parent.invoker.entityId),(Player)parent.invoker);
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
	
	public void doAnimalCharm(InvokeSpell spell){
		
		if(spell.getTarget().isPresent()){
			if(spell.getTarget().get() instanceof EntityTameable){
				EntityTameable tameable = (EntityTameable)spell.getTarget().get();
				tameable.setTamed(true);
				tameable.setOwner(spell.invoker.getCommandSenderName());
				PacketDispatcher.sendPacketToPlayer(PacketHandler.getParticlePacket(2, tameable.entityId,10), (Player)spell.invoker);
				return;
			}
			if(spell.getTarget().get() instanceof EntityHorse){
				EntityHorse horse = (EntityHorse)spell.getTarget().get();
				horse.setTamedBy(spell.invoker);
				PacketDispatcher.sendPacketToPlayer(PacketHandler.getParticlePacket(2, horse.entityId,10), (Player)spell.invoker);
				return;

			}
			if(spell.getTarget().get() instanceof EntityAnimal){
				EntityAnimal animal = (EntityAnimal)spell.getTarget().get();
				animal.func_110196_bT();
				return;

			}
		}
	}

	
	private void doVeil(InvokeSpell parentInvoke) {
		int remain = (int)((float)20 * parentInvoke.getAmp());
		switch(parentInvoke.spell.element){
		case FIRE:
			LivingDebuff.addDebuff(parentInvoke.invoker, DebuffRegistry.fireVeil, remain);
			break;
		case WATER:
			LivingDebuff.addDebuff(parentInvoke.invoker, DebuffRegistry.waterVeil, remain);
			break;
		case EARTH:
			LivingDebuff.addDebuff(parentInvoke.invoker, DebuffRegistry.earthVeil, remain);
			break;
		case WOOD:
			LivingDebuff.addDebuff(parentInvoke.invoker, DebuffRegistry.woodVeil, remain);
			break;
		case METAL:
			LivingDebuff.addDebuff(parentInvoke.invoker, DebuffRegistry.metalVeil, remain);
			break;
		case FORBIDDEN:
			break;
		default:
			break;
		}
		
		
	}
	
	public void doHeroism(InvokeSpell parent){
		int remain = (int)((float)15 * parent.getAmp());
		if(parent.getTarget().isPresent()){
			LivingDebuff.addLivingDebuff(parent.getTarget().get(), new LivingBuff(DebuffRegistry.powerup,remain,1));
		}else{
			LivingDebuff.addLivingDebuff(parent.invoker, new LivingBuff(DebuffRegistry.powerup,remain,1));
		}
	}


	
	public void doMissuileGuard(InvokeSpell parent){
		int remain = (int)((float)15 * parent.getAmp());
		if(parent.getTarget().isPresent()){
			LivingDebuff.addLivingDebuff(parent.getTarget().get(), new LivingBuff(DebuffRegistry.missuileGuard,remain,1));
		}else{
			LivingDebuff.addLivingDebuff(parent.invoker, new LivingBuff(DebuffRegistry.missuileGuard,remain,1));
		}
	}
	
	public void doLifeBoost(InvokeSpell parent){
		int remain = (int)((float)15 * parent.getAmp());
		if(parent.getTarget().isPresent()){
			LivingDebuff.addLivingDebuff(parent.getTarget().get(), new LivingBuff(DebuffRegistry.lifeBoost,remain,1));
		}else{
			LivingDebuff.addLivingDebuff(parent.invoker, new LivingBuff(DebuffRegistry.lifeBoost,remain,1));
		}
	}
	

	public void doPurify(InvokeSpell parent){


		int heal = Math.round(3*parent.getAmp());
		if(parent.getTarget().isPresent()){
			
			parent.getTarget().get().heal(heal);
			parent.invoker.addChatMessage(parent.getTarget().get().getEntityName()+" healed "+heal+".");
		}else{
			parent.invoker.heal(heal);
			parent.invoker.addChatMessage(parent.invoker.getEntityName()+" healed "+heal+".");
		}

		return;

	}
	
	public void doCloudCall(InvokeSpell parent){
		parent.world.getWorldInfo().setRaining(true);
	}
	

	public void doDectectGold(InvokeSpell parent){
		ScanHelper scan = new ScanHelper(parent.invoker,10,10);
		StringBuilder builder = new StringBuilder();
		boolean diacheck = (parent.getAmp() >=2.0F);
		HashMap<String,Integer> oreMap = new HashMap();
		scan.setWorld(parent.world);
		for(;scan.hasNext();scan.next()){
			if(!scan.isAirBlock() && scan.sy>0){
				Block block = Block.blocksList[scan.getID()];
				if(block instanceof BlockOre){
					int amount = 0;
					if(oreMap.containsKey(block.getUnlocalizedName())){
						amount = oreMap.get(block.getUnlocalizedName());
					}
					amount +=1;
					oreMap.put(block.getUnlocalizedName(), amount);
					
				}
				String orekey = OreDictionary.getOreName(OreDictionary.getOreID(new ItemStack(block,1,scan.getMetadata())));
				if(!orekey.equals("Unknown")){
					int amount = 0;
					if(oreMap.containsKey(orekey)){
						amount = oreMap.get(orekey);
					}
					amount += 1;
					oreMap.put(orekey, amount);
				}
			}
		}
		if(!oreMap.isEmpty()){
			for(String key:oreMap.keySet()){
				builder.append(key).append(":").append(oreMap.get(key)).append("/");
			}
		}
		
		parent.invoker.addChatMessage(new String(builder));
		return;
	}
	
	public void doAbyss(InvokeSpell parent) {
		float amp = parent.getAmp();
		AxisAlignedBB bb = parent.invoker.boundingBox.expand(9.0D+amp, 4.0D+amp, 9.0D+amp);
		Entity nearent = parent.world.findNearestEntityWithinAABB(EntityLivingBase.class, bb, parent.invoker);

		if(nearent!=null){
			if(HSLibs.isEnemy(nearent,parent.invoker)){
				EntityLiving el = (EntityLiving)nearent;

				if(amp<1.0){
					amp=1.0F;
				}
				if(amp>4.0){
					amp=4.0F;
				}
				float f3 = 0.25F;
				PacketDispatcher.sendPacketToPlayer(PacketHandler.getParticlePacket(1, el.entityId,10), (Player) parent.invoker);
				//el.playSound("mob.endermen.portal", 1.0F, 1.0F);
				int rand1 = parent.world.rand.nextInt(30*(int)amp);
				if(rand1>100){
					rand1 = 100;
				}
				int at = 8*(int)amp;
				System.out.println(at);
				el.attackEntityFrom(DamageSource.causePlayerDamage(parent.invoker), at);

				int time = Math.round((float)240*amp);
				if(time>600){
					time = 600;
				}
				if(parent.world.rand.nextInt(100)<=rand1){
					el.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id,time , (int)amp));
				}
				//UtilSkill.tryLPHurt(15, 1, el, par3EntityPlayer);
			}

		}
		return;
	}
	
	public void doWeakness(InvokeSpell parent) {


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
	
	public void doOvergrowth(InvokeSpell parent){


		int range = 10;
		int prob = Math.round(30*parent.getAmp());




		ScanHelper scan = new ScanHelper(parent.invoker,8,6);
		
		for(;scan.hasNext();scan.next()){
			ItemStack dummy = new ItemStack(Item.dyePowder,1,0);
			if(parent.world.rand.nextInt(100)<prob){
				ItemDye.applyBonemeal(dummy, parent.world, scan.sx, scan.sy, scan.sz, parent.invoker);
			}
			
		}

		return;
	}
}

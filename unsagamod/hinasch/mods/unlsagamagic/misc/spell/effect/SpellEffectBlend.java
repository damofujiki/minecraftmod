package hinasch.mods.unlsagamagic.misc.spell.effect;


import hinasch.lib.ScanHelper;
import hinasch.mods.unlsaga.misc.debuff.DebuffRegistry;
import hinasch.mods.unlsaga.misc.debuff.LivingBuff;
import hinasch.mods.unlsaga.misc.debuff.LivingDebuff;
import hinasch.mods.unlsaga.network.PacketHandler;
import hinasch.mods.unlsagamagic.entity.EntityBoulder;
import hinasch.mods.unlsagamagic.entity.EntityFireArrow;
import hinasch.mods.unlsagamagic.misc.spell.Spell;
import hinasch.mods.unlsagamagic.misc.spell.SpellRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;

public class SpellEffectBlend extends SpellEffect {

	@Override
	public void doEffect(InvokeSpell spellinvoke){
		this.parentInvoke = spellinvoke;
		EntityPlayer invoker = this.parentInvoke.invoker;
		float amp = this.parentInvoke.getAmp();
		Spell spell = this.parentInvoke.spell;
		if(spell==SpellRegistry.iceNine)this.doIceNine(parentInvoke);
		if(spell==SpellRegistry.leavesShield)this.doLeavesShield(parentInvoke);
		if(spell==SpellRegistry.detectGold)this.doDetectTresure(parentInvoke);
		if(spell==SpellRegistry.crimsonFlare)this.doCrimsonFlare(parentInvoke);
		if(spell==SpellRegistry.stoneShower)this.doStoneShower(parentInvoke);
	}
	
	public void doStoneShower(InvokeSpell spell){

		spell.world.playSoundAtEntity(spell.invoker, "mob.ghast.fireball", 1.0F, 1.0F / (spell.world.rand.nextFloat() * 0.4F + 1.2F) + 1.0F * 0.5F);
		EntityBoulder var8 = new EntityBoulder(spell.world, spell.invoker, 1.0F * 2.0F,true);
		var8.canBePickedUp = 0;
		var8.setDamage(var8.getDamage()+(1.0*spell.getAmp()));
		var8.setRangeStoneShower(Math.round(10*spell.getAmp()));
		if (!spell.world.isRemote)
		{
			spell.world.spawnEntityInWorld(var8);
		}
		return;
	}

	public void doLeavesShield(InvokeSpell parent){
		int remain = (int)((float)15 * parent.getAmp());
		int amp = (int)((float)30 * parent.getAmp());
		if(parent.getTarget().isPresent()){
			LivingDebuff.addLivingDebuff(parent.getTarget().get(), new LivingBuff(DebuffRegistry.leavesShield,remain,amp));
		}else{
			LivingDebuff.addLivingDebuff(parent.invoker, new LivingBuff(DebuffRegistry.leavesShield,remain,amp));
		}
	}

	public void doIceNine(InvokeSpell parent){



		int amp = 1;

		if(parent.invoker.isBurning()){
			parent.invoker.setFire(0);
		}

		ScanHelper scan = new ScanHelper(parent.invoker,11,5);
		scan.setWorld(parent.world);

		for(;scan.hasNext();scan.next()){
			if((scan.sy>0)){
				if(scan.getID()==Block.waterMoving.blockID ||scan.getID()==Block.waterStill.blockID ){
					scan.setBlockHere(Block.ice.blockID,0,2);
				}
				if(scan.getID()==Block.lavaMoving.blockID){
					scan.setBlockHere(Block.cobblestone.blockID);
				}
				if(scan.getID()==Block.lavaStill.blockID){
					scan.setBlockHere(Block.obsidian.blockID);
				}

				//if(scan.isAirBlockUp() && !scan.isAirBlock()){
				if(scan.isOpaqueBlock() && scan.isAirBlockUp() && !scan.isPlayerPos()){
					System.out.println("true");
					parent.world.setBlock(scan.sx, scan.sy+1, scan.sz, Block.snow.blockID,0,2);
					//scan.setBlockHere(Block.snow.blockID);
				}
				if(scan.getID()==Block.snow.blockID && !scan.isPlayerPos()){
					int meta = scan.getMetadata();
					if(meta<15){
						parent.world.setBlockMetadataWithNotify(scan.sx, scan.sy, scan.sz, meta+1, 2);
					}
				}
				//					if(par2World.getBlockMaterial(scan.sx, scan.sy, scan.sz)==Material.leaves){
				//						par2World.setBlock(scan.sx, scan.sy+1, scan.sz, Block.snow.blockID,0,2);
				//					}

				//}



			}
		}
	}
	
	public void doCrimsonFlare(InvokeSpell parent){

		PacketDispatcher.sendPacketToPlayer(PacketHandler.getSoundPacket(1008, parent.invoker.entityId),(Player)parent.invoker);
		EntityFireArrow var8 = new EntityFireArrow(parent.world, parent.invoker, 1.5F * 2.0F);
		var8.setFire(100);
		var8.canBePickedUp = 0;
		var8.setDamage(var8.getDamage()+(1.0*parent.getAmp()));
		var8.setFlare(true);
		var8.setAmp(2+(int)parent.getAmp());
		if (!parent.world.isRemote)
		{
			parent.world.spawnEntityInWorld(var8);
		}
		return;
	}
	
	public void doDetectTresure(InvokeSpell parent){


		int sx;
		int sy;
		int sz;
		int goldore= 0;
		int ironore= 0;

		int range = Math.round(80*parent.getAmp());
		if(range<1){
			range = 1;
		}
		if(range>100){
			range = 100;
		}


		ScanHelper scan = new ScanHelper(parent.invoker,range,range);
		for(;scan.hasNext();scan.next()){
			if(scan.sy>0){
				int blockid = parent.world.getBlockId(scan.sx,scan.sy,scan.sz);
				if(Block.blocksList[blockid]!=null){
					if(Block.blocksList[blockid] instanceof BlockChest){
						parent.invoker.addChatMessage("Detect Chest:"+scan.sx+","+scan.sy+","+scan.sz);
					}

				}
			}

		}


		return;
	}
}

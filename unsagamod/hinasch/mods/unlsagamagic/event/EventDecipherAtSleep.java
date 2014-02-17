package hinasch.mods.unlsagamagic.event;

import hinasch.lib.HSLibs;
import hinasch.mods.unlsaga.Unsaga;
import hinasch.mods.unlsagamagic.UnsagaMagic;
import hinasch.mods.unlsagamagic.item.ItemTablet;
import hinasch.mods.unlsagamagic.misc.element.UnsagaElement.EnumElement;
import hinasch.mods.unlsagamagic.misc.spell.Spell;
import hinasch.mods.unlsagamagic.misc.spell.SpellMixTable;

import java.util.EnumSet;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

public class EventDecipherAtSleep implements ITickHandler{

	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData) {
		if (type.equals(EnumSet.of(TickType.PLAYER))) {
			onPlayerTick((EntityPlayer) tickData[0]);
		}

	}
	
	private void onPlayerTick(EntityPlayer entityPlayer) {
		int sleepTimer = entityPlayer.getSleepTimer();
		if(sleepTimer == 99){
			onPlayerSleep(entityPlayer.worldObj,entityPlayer);
		}
	}

	public void onPlayerSleep(World world,EntityPlayer ep){

		if(ep.getHeldItem()!=null){
			ItemStack is = ep.getHeldItem();
			if(is.getItem() instanceof ItemTablet && !world.isRemote){
				UnsagaMagic.worldElement.figureElements(world, ep);
				SpellMixTable table = UnsagaMagic.worldElement.getWorldElements();
				Spell spell = ItemTablet.getSpell(is);
				int difficultySpell = spell.difficultyDecipher;
				EnumElement elementMagic = spell.element;
				int elementpoint = table.getInt(elementMagic);
				int progressDecipher = (elementpoint*3) / HSLibs.exceptZero(difficultySpell/2);
				if(progressDecipher<2)progressDecipher=2;
				progressDecipher *= 2;
				Unsaga.debug("解読度:"+progressDecipher);
				ItemTablet.progressDeciphering(ep, is, progressDecipher);
			}
		}
	}

	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	public EnumSet<TickType> ticks() {
		// TODO 自動生成されたメソッド・スタブ
		return EnumSet.of(TickType.PLAYER);
	}

	@Override
	public String getLabel() {
		// TODO 自動生成されたメソッド・スタブ
		return "unsagaMagic";
	}
}

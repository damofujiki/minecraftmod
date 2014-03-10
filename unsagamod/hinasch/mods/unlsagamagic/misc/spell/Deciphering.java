package hinasch.mods.unlsagamagic.misc.spell;

import hinasch.lib.HSLibs;
import hinasch.mods.unlsaga.Unsaga;
import hinasch.mods.unlsaga.core.FiveElements;
import hinasch.mods.unlsaga.core.FiveElements.EnumElement;
import hinasch.mods.unlsaga.core.event.ExtendedPlayerData;
import hinasch.mods.unlsagamagic.UnsagaMagic;
import hinasch.mods.unlsagamagic.item.ItemTablet;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

public class Deciphering {

	public static void progressDeciphering(World world,EntityPlayer ep,ItemStack is){
			UnsagaMagic.worldElement.figureElements(world, ep);
			SpellMixTable table = UnsagaMagic.worldElement.getWorldElements();
			Spell spell = ItemTablet.getSpell(is);
			int difficultySpell = spell.difficultyDecipher;
			FiveElements.EnumElement elementMagic = spell.element;
			int elementpoint = table.getInt(elementMagic);
			int progressDecipher = (elementpoint*3) / HSLibs.exceptZero(difficultySpell/2);
			if(progressDecipher<2)progressDecipher=2;
			progressDecipher *= 2;
			Unsaga.debug("解読度:"+progressDecipher);
			ItemTablet.progressDeciphering(ep, is, progressDecipher);
		
	}
	
	public static void progressDecipheringOnFighted(LivingDeathEvent e){
		if(e.source.getEntity() instanceof EntityPlayer){
			EntityPlayer ep = (EntityPlayer)e.source.getEntity();
			if(ExtendedPlayerData.getData(ep).isPresent()){
				ExtendedPlayerData data = ExtendedPlayerData.getData(ep).get();
				if(data.getTablet()!=null){
					progressDeciphering(ep.worldObj,ep,data.getTablet());
				}
			}
		}
	}
}

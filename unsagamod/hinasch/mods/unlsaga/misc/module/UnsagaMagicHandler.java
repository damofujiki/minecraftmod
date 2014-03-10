package hinasch.mods.unlsaga.misc.module;

import hinasch.mods.unlsagamagic.UnsagaMagic;
import hinasch.mods.unlsagamagic.item.ItemTablet;
import hinasch.mods.unlsagamagic.misc.spell.Deciphering;

import java.util.Random;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

public class UnsagaMagicHandler {

	public UnsagaMagic unsagaMagic;
	
	public UnsagaMagicHandler(){
		this.unsagaMagic = UnsagaMagic.getInstance();
	}
	
	public void preInit(){
		unsagaMagic.init();
	}

	public void registerEvents(){
		unsagaMagic.registerEvents();
	}
	public void initItem(Configuration config) {
		unsagaMagic.initItem(config);
		
	}
	
	public void registerEntity(){
		unsagaMagic.registerEntity();
	}
	
	public Item getMagicTablet(){
		return unsagaMagic.itemMagicTablet;
	}
	
	public ItemStack getRandomMagicTablet(Random rand){
		return ItemTablet.getRandomMagicTablet(rand);
	}
	
	public void progressDecipherOnLivingDeath(LivingDeathEvent e){
		Deciphering.progressDecipheringOnFighted(e);
	}



}

package hinasch.mods.unlsaga.misc.module;

import hinasch.mods.unlsagamagic.UnsagaMagic;
import hinasch.mods.unlsagamagic.item.ItemTablet;

import java.util.Random;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;

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
}

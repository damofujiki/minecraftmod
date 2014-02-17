package hinasch.mods.unlsaga.misc.module;

import hinasch.mods.unlsagamagic.UnsagaMagic;
import net.minecraftforge.common.Configuration;

public class Module {

	public UnsagaMagic unsagaMagic;
	
	public Module(){
		this.unsagaMagic = new UnsagaMagic();
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
}

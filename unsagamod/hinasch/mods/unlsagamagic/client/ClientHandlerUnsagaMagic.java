package hinasch.mods.unlsagamagic.client;

import hinasch.mods.unlsagamagic.client.render.RenderBoulder;
import hinasch.mods.unlsagamagic.client.render.RenderFireArrow;
import hinasch.mods.unlsagamagic.entity.EntityBoulder;
import hinasch.mods.unlsagamagic.entity.EntityFireArrow;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientHandlerUnsagaMagic {

	public ClientHandlerUnsagaMagic(){
		
	}
	
	public void registerTextures(){
		RenderingRegistry.registerEntityRenderingHandler(EntityFireArrow.class, new RenderFireArrow(1.0F));
		RenderingRegistry.registerEntityRenderingHandler(EntityBoulder.class, new RenderBoulder());
	}
}

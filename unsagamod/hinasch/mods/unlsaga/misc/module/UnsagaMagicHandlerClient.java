package hinasch.mods.unlsaga.misc.module;

import hinasch.mods.unlsagamagic.client.ClientHandlerUnsagaMagic;

public class UnsagaMagicHandlerClient {

	public ClientHandlerUnsagaMagic registerTextures;
	
	public UnsagaMagicHandlerClient(){
		this.registerTextures = new ClientHandlerUnsagaMagic();
	}
	
	public void register(){
		this.registerTextures.registerTextures();
	}
}

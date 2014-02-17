package hinasch.mods.unlsaga.misc.module;

import hinasch.mods.unlsagamagic.client.ClientHandlerUnsagaMagic;

public class ProxyRegisterUnsagaSpell {

	public ClientHandlerUnsagaMagic registerTextures;
	
	public ProxyRegisterUnsagaSpell(){
		this.registerTextures = new ClientHandlerUnsagaMagic();
	}
	
	public void register(){
		this.registerTextures.registerTextures();
	}
}

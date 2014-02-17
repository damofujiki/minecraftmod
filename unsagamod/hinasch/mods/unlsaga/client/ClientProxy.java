package hinasch.mods.unlsaga.client;


import hinasch.mods.unlsaga.DebugUnsaga;
import hinasch.mods.unlsaga.Unsaga;
import hinasch.mods.unlsaga.client.render.RenderArrowUnsaga;
import hinasch.mods.unlsaga.client.render.RenderBarrett;
import hinasch.mods.unlsaga.client.render.RenderFlyingAxe;
import hinasch.mods.unlsaga.client.render.equipment.RenderItemArmor;
import hinasch.mods.unlsaga.client.render.equipment.RenderItemMusket;
import hinasch.mods.unlsaga.client.render.equipment.RenderItemSpear;
import hinasch.mods.unlsaga.client.render.equipment.RenderItemWeapon;
import hinasch.mods.unlsaga.entity.EntityArrowUnsaga;
import hinasch.mods.unlsaga.entity.EntityBarrett;
import hinasch.mods.unlsaga.entity.EntityFlyingAxe;
import hinasch.mods.unlsaga.misc.module.ProxyRegisterUnsagaSpell;
import hinasch.mods.unlsaga.network.CommonProxy;
import net.minecraftforge.client.MinecraftForgeClient;

import com.google.common.base.Optional;

import cpw.mods.fml.client.registry.KeyBindingRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy{

	public DebugUnsaga debugdata;
	public ProxyRegisterUnsagaSpell proxyUnsagaSpell;
	
	
	@Override
	public void registerSpearRenderer(int par1){
		MinecraftForgeClient.registerItemRenderer(par1, new RenderItemSpear());
	}
	
	@Override
	public void registerSpecialRenderer(int par1){
		MinecraftForgeClient.registerItemRenderer(par1, new RenderItemWeapon());
		
	}
	
	@Override
	public void registerMusketRenderer(int par1){
		MinecraftForgeClient.registerItemRenderer(par1, new RenderItemMusket());
	}
	
	@Override
	public void registerArmorRenderer(int par1){
		MinecraftForgeClient.registerItemRenderer(par1, new RenderItemArmor());
	}
	
	@Override
	public void registerRenderers(){
		RenderingRegistry.registerEntityRenderingHandler(EntityArrowUnsaga.class, new RenderArrowUnsaga());
		RenderingRegistry.registerEntityRenderingHandler(EntityBarrett.class, new RenderBarrett(1.0F));
		RenderingRegistry.registerEntityRenderingHandler(EntityFlyingAxe.class, new RenderFlyingAxe(1.0F));
		if(Unsaga.module.isPresent()){
			this.proxyUnsagaSpell = new ProxyRegisterUnsagaSpell();
			this.proxyUnsagaSpell.register();
		}
	}
	
	@Override
	public void setDebugUnsaga(){
		Unsaga.debug("[UnsagaMod]デバッグユーティリティの準備");
		
		if(Unsaga.debug.get()){
			if(debugdata==null){
				debugdata = new DebugUnsaga();
				//		GL11.glRotatef(90.0F,0.04F,-0.08F,-0.08F);
				//GL11.glTranslatef(-0.67F,0.17F,-0.3F);
				debugdata.registFloat(28.0F*4.0F, 0);
				debugdata.registFloat(53.0F,1);
			}
		}
		KeyBindingRegistry.registerKeyBinding(new KeyHandlerTest());
	}
	
	@Override
	public Optional<DebugUnsaga> getDebugUnsaga(){
		return Optional.of(this.debugdata);
	}
	
//	public sendGuiPacket(EntityClientPlayerMP clientPlayer){
//		PacketDispatcher.sendPacketToServer(PacketHandler.getPacket(this,clientPlayer));
//	}
//	@Override
//	public void registerGunRenderer(int itemID) {
//		MinecraftForgeClient.registerItemRenderer(itemID, new RenderItemMusket());
//		
//	}
}

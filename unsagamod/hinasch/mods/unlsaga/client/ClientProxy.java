package hinasch.mods.unlsaga.client;


import hinasch.lib.HSLibs;
import hinasch.mods.unlsaga.DebugUnsaga;
import hinasch.mods.unlsaga.Unsaga;
import hinasch.mods.unlsaga.client.render.RenderGolemUnsaga;
import hinasch.mods.unlsaga.client.render.RenderTreasureSlime;
import hinasch.mods.unlsaga.client.render.equipment.RenderItemArmor;
import hinasch.mods.unlsaga.client.render.equipment.RenderItemMusket;
import hinasch.mods.unlsaga.client.render.equipment.RenderItemSpear;
import hinasch.mods.unlsaga.client.render.equipment.RenderItemWeapon;
import hinasch.mods.unlsaga.client.render.projectile.RenderArrowUnsaga;
import hinasch.mods.unlsaga.client.render.projectile.RenderBarrett;
import hinasch.mods.unlsaga.client.render.projectile.RenderFlyingAxe;
import hinasch.mods.unlsaga.client.render.projectile.RenderThrowableItem;
import hinasch.mods.unlsaga.core.init.UnsagaConfigs;
import hinasch.mods.unlsaga.core.init.UnsagaItems;
import hinasch.mods.unlsaga.entity.EntityGolemUnsaga;
import hinasch.mods.unlsaga.entity.EntityTreasureSlime;
import hinasch.mods.unlsaga.entity.projectile.EntityArrowUnsaga;
import hinasch.mods.unlsaga.entity.projectile.EntityBarrett;
import hinasch.mods.unlsaga.entity.projectile.EntityBoulder;
import hinasch.mods.unlsaga.entity.projectile.EntityFireArrow;
import hinasch.mods.unlsaga.entity.projectile.EntityFlyingAxe;
import hinasch.mods.unlsaga.entity.projectile.EntitySolutionLiquid;
import hinasch.mods.unlsaga.misc.module.UnsagaMagicHandlerClient;
import hinasch.mods.unlsaga.network.CommonProxy;
import net.minecraft.client.model.ModelSlime;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;

import com.google.common.base.Optional;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;

public class ClientProxy extends CommonProxy{

	public DebugUnsaga debugdata;
	public UnsagaMagicHandlerClient proxyUnsagaSpell;
	public KeyHandler keyHandler;
	
	@Override
	public void registerKeyHandler(){
		this.keyHandler = new KeyHandler();
		FMLCommonHandler.instance().bus().register(keyHandler);
	}
	
	@Override
	public void registerSpearRenderer(Item par1){
		MinecraftForgeClient.registerItemRenderer(par1, new RenderItemSpear());
	}
	
	@Override
	public void registerSpecialRenderer(Item par1){
		MinecraftForgeClient.registerItemRenderer(par1, new RenderItemWeapon());
		
	}
	
	@Override
	public void registerMusketRenderer(Item par1){
		MinecraftForgeClient.registerItemRenderer(par1, new RenderItemMusket());
	}
	
	@Override
	public void registerArmorRenderer(Item par1){
		MinecraftForgeClient.registerItemRenderer(par1, new RenderItemArmor());
	}
	
	@Override
	public void registerRenderers(){
		RenderingRegistry.registerEntityRenderingHandler(EntityArrowUnsaga.class, new RenderArrowUnsaga());
		RenderingRegistry.registerEntityRenderingHandler(EntityBarrett.class, new RenderBarrett(1.0F));
		RenderingRegistry.registerEntityRenderingHandler(EntityFlyingAxe.class, new RenderFlyingAxe(1.0F));
		RenderingRegistry.registerEntityRenderingHandler(EntityTreasureSlime.class, new RenderTreasureSlime(new ModelSlime(16), new ModelSlime(0), 0.25F));
		RenderingRegistry.registerEntityRenderingHandler(EntityGolemUnsaga.class, new RenderGolemUnsaga());
		RenderingRegistry.registerEntityRenderingHandler(EntityFireArrow.class, new RenderThrowableItem(1.0F,Items.fire_charge,0));
		RenderingRegistry.registerEntityRenderingHandler(EntityBoulder.class, new RenderThrowableItem(1.0F,UnsagaItems.itemMaterials,18));
		RenderingRegistry.registerEntityRenderingHandler(EntitySolutionLiquid.class, new RenderThrowableItem(1.0F,Items.slime_ball,0));
		if(UnsagaConfigs.module.isMagicEnabled()){
			this.proxyUnsagaSpell = new UnsagaMagicHandlerClient();
			this.proxyUnsagaSpell.register();
		}
		HSLibs.registerEvent(new UnsagaParticles());
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
		//KeyBindingRegistry.registerKeyBinding(new KeyHandlerTest());
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

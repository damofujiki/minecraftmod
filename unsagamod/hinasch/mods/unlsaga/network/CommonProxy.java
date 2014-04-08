package hinasch.mods.unlsaga.network;

import hinasch.mods.unlsaga.DebugUnsaga;
import hinasch.mods.unlsaga.Unsaga;
import hinasch.mods.unlsaga.client.gui.GuiBartering;
import hinasch.mods.unlsaga.client.gui.GuiChest;
import hinasch.mods.unlsaga.client.gui.GuiEquipment;
import hinasch.mods.unlsaga.client.gui.GuiSmithUnsaga;
import hinasch.mods.unlsaga.core.event.ExtendedPlayerData;
import hinasch.mods.unlsaga.inventory.container.ContainerBartering;
import hinasch.mods.unlsaga.inventory.container.ContainerChestUnsaga;
import hinasch.mods.unlsaga.inventory.container.ContainerEquipment;
import hinasch.mods.unlsaga.inventory.container.ContainerSmithUnsaga;
import hinasch.mods.unlsaga.misc.module.UnsagaMagicContainerHandler;
import hinasch.mods.unlsaga.tileentity.TileEntityChestUnsagaNew;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.NpcMerchant;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.world.World;

import com.google.common.base.Optional;
import com.hinasch.lib.HSLibs;

import cpw.mods.fml.common.network.IGuiHandler;





public class CommonProxy implements IGuiHandler{

	public DebugUnsaga debugdata;
	public UnsagaMagicContainerHandler clientHandlerMagic;
	
	public CommonProxy(){
		this.clientHandlerMagic = new UnsagaMagicContainerHandler();
	}
	
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		if(ID==Unsaga.guiNumber.equipment){
			if(Minecraft.getMinecraft().currentScreen==null){
				Unsaga.debug("client側が呼ばれました");
				return new GuiEquipment(player);
			}

		}
		if(ID==Unsaga.guiNumber.smith){
			if(Minecraft.getMinecraft().currentScreen==null){

						return new GuiSmithUnsaga(new NpcMerchant(player), world, player);

			}
		}
		if(ID==Unsaga.guiNumber.bartering){
			if(Minecraft.getMinecraft().currentScreen==null){

						return new GuiBartering(new NpcMerchant(player), world, player);

			}
		}
		if(ID==Unsaga.guiNumber.blender){
			if(Minecraft.getMinecraft().currentScreen==null){

				Unsaga.debug("GUI");
						return (GuiContainer)UnsagaMagicContainerHandler.getGuiBlender(player,world);

			}
		}
		if(ID==Unsaga.guiNumber.chest){
			Unsaga.debug("きてます");
			TileEntityChestUnsagaNew chest = (TileEntityChestUnsagaNew)world.getTileEntity(x, y, z);
			if(Minecraft.getMinecraft().currentScreen==null && chest!=null){
				Unsaga.debug("GUI");
				return new GuiChest(chest,player);

			}


		}
		return null;
	}
	
	public Optional<DebugUnsaga> getDebugUnsaga(){
		return Optional.of(this.debugdata);
	}
	
	

	
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		// TODO 自動生成されたメソッド・スタブ
		if(ID==Unsaga.guiNumber.equipment){
			if(player.openContainer == player.inventoryContainer){
				
				return new ContainerEquipment(player.inventory,player);
			}
			
		}
		if(ID==Unsaga.guiNumber.smith){
			if(player.openContainer == player.inventoryContainer){
				if(HSLibs.getExtendedData(ExtendedPlayerData.key, player).isPresent()){
					ExtendedPlayerData data = (ExtendedPlayerData)HSLibs.getExtendedData(ExtendedPlayerData.key, player).get();
					if(data.getMerchant().isPresent()){
						Unsaga.debug("Container呼ばれてる");
						Unsaga.debug(data.getMerchant().get());
						return new ContainerSmithUnsaga(data.getMerchant().get(), world, player);
					}
					
				}
				
			}
		}
		if(ID==Unsaga.guiNumber.bartering){
			if(player.openContainer == player.inventoryContainer){
				if(HSLibs.getExtendedData(ExtendedPlayerData.key, player).isPresent()){
					ExtendedPlayerData data = (ExtendedPlayerData)HSLibs.getExtendedData(ExtendedPlayerData.key, player).get();
					if(data.getMerchant().isPresent()){
						Unsaga.debug("Container呼ばれてる");
						Unsaga.debug(data.getMerchant().get());
						return new ContainerBartering(world,player,data.getMerchant().get());
					}
					
				}
				
			}
		}
		if(ID==Unsaga.guiNumber.blender){
			if(player.openContainer == player.inventoryContainer){
				Unsaga.debug("Container");
				return (Container)UnsagaMagicContainerHandler.getContainerBlender(player, world);
			}
			
		}
		if(ID==Unsaga.guiNumber.chest){
			TileEntityChestUnsagaNew chest = (TileEntityChestUnsagaNew)world.getTileEntity(x, y, z);
			if(player.openContainer == player.inventoryContainer && chest!=null){
				Unsaga.debug("Container");
				return new ContainerChestUnsaga(chest,player);
			}

		}
		return null;
	}
	
	public void registerArmorRenderer(Item par1){
		
	}
	

	public void registerKeyHandler(){
	}
	

	public void registerMusketRenderer(Item par1){

	}
	
	public void registerRenderers(){
		
	}
	
	public void registerSpearRenderer(Item par1){
		
	}




	public void registerSpecialRenderer(Item par1){

	}




	public void setDebugUnsaga(){

	}
	
	
}

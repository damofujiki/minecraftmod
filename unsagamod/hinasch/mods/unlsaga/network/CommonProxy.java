package hinasch.mods.unlsaga.network;

import hinasch.lib.HSLibs;
import hinasch.mods.unlsaga.DebugUnsaga;
import hinasch.mods.unlsaga.Unsaga;
import hinasch.mods.unlsaga.client.gui.GuiEquipment;
import hinasch.mods.unlsaga.client.gui.GuiSmithUnsaga;
import hinasch.mods.unlsaga.core.event.ExtendedPlayerData;
import hinasch.mods.unlsaga.inventory.ContainerEquipment;
import hinasch.mods.unlsaga.inventory.ContainerSmithUnsaga;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.NpcMerchant;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import com.google.common.base.Optional;

import cpw.mods.fml.common.network.IGuiHandler;





public class CommonProxy implements IGuiHandler{

	public DebugUnsaga debugdata;
	
	public void registerSpearRenderer(int par1){
		
	}
	
	

	
	public void registerRenderers(){
		
	}
	
	public void registerArmorRenderer(int par1){
		
	}
	

	public void registerSpecialRenderer(int par1){

	}
	

	public void registerMusketRenderer(int par1){

	}
	
	public void setDebugUnsaga(){

	}
	
	public Optional<DebugUnsaga> getDebugUnsaga(){
		return Optional.of(this.debugdata);
	}




	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		// TODO 自動生成されたメソッド・スタブ
		if(ID==Unsaga.GuiEquipment){
			//if(player.openContainer==null){
			//if(player.openContainer instanceof ContainerEquipment)return null;
			if(player.openContainer == player.inventoryContainer){
				
				return new ContainerEquipment(player.inventory,player);
			//}
			}
			
		}
		if(ID==Unsaga.GuiSmith){
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
		return null;
	}




	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		if(ID==Unsaga.GuiEquipment){
			//if(player.openContainer==null){
			//if(player.openContainer instanceof ContainerEquipment)return null;
			if(Minecraft.getMinecraft().currentScreen==null){
				Unsaga.debug("client側が呼ばれました");
				return new GuiEquipment(player);
			}
			//}
		}
		if(ID==Unsaga.GuiSmith){
			if(Minecraft.getMinecraft().currentScreen==null){
//				if(HSLibs.getExtendedData(ExtendedPlayerData.key, player).isPresent()){
//					ExtendedPlayerData data = (ExtendedPlayerData)HSLibs.getExtendedData(ExtendedPlayerData.key, player).get();
//					Unsaga.debug("GUI呼ばれてる");

						//Unsaga.debug(data.getMerchant().get());
						return new GuiSmithUnsaga(new NpcMerchant(player), world, player);
					
					
//				}
				
			}
		}
		return null;
	}
	
	
}

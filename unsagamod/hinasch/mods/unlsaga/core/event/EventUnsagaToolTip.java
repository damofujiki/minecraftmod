package hinasch.mods.unlsaga.core.event;

import hinasch.mods.unlsaga.Unsaga;
import hinasch.mods.unlsaga.client.gui.GuiBartering;
import hinasch.mods.unlsaga.client.gui.GuiSmithUnsaga;
import hinasch.mods.unlsaga.core.init.UnsagaItems;
import hinasch.mods.unlsaga.core.init.UnsagaMaterial;
import hinasch.mods.unlsaga.misc.bartering.MerchandiseInfo;
import hinasch.mods.unlsaga.misc.smith.MaterialInfo;
import hinasch.mods.unlsaga.misc.translation.Translation;
import hinasch.mods.unlsaga.misc.util.EnumUnsagaTools;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class EventUnsagaToolTip {

	protected MaterialInfo info;
	
	protected boolean isClientInGuiBartering(){
		return Minecraft.getMinecraft().currentScreen instanceof GuiBartering;
	}
	
	protected boolean isClientInGuiSmithing(){
		return Minecraft.getMinecraft().currentScreen instanceof GuiSmithUnsaga;
	}
	
	@SubscribeEvent
	public void toolTipEvent(ItemTooltipEvent event){
		if(Unsaga.debug.get()){
			event.toolTip.add(event.itemStack.getUnlocalizedName());
		}
		if(isClientInGuiBartering()){
			this.addBarteringTips(event);
			return;
		}
		if(!isClientInGuiSmithing())return;
		GuiSmithUnsaga guismith = (GuiSmithUnsaga)Minecraft.getMinecraft().currentScreen;
		int currentcategory = guismith.getCurrentCategory();
		ItemStack is = event.itemStack;
		info = new MaterialInfo(event.itemStack);
		if(info.isValidMaterial()){
			
			//if(info.getMaterial().isPresent()){
				UnsagaMaterial material = info.getMaterial().get();
				event.toolTip.add(Translation.localize("tips.validmaterial")+material.getLocalized());
				
				if(UnsagaItems.isValidItemForMaterial(EnumUnsagaTools.toolArray.get(currentcategory), material)){
				event.toolTip.add(Translation.localize("tips.canuseforbase")+EnumUnsagaTools.toolArray.get(currentcategory).getLocalized());

				}
			//}
			//MaterialLibrary info = MaterialLibrary.findInfo(event.itemStack).get();

		}
		
	}

	protected void addBarteringTips(ItemTooltipEvent event) {
		// TODO 自動生成されたメソッド・スタブ
		if(MerchandiseInfo.hasBuyPriceTag(event.itemStack)){
			MerchandiseInfo merchandiseInfo = new MerchandiseInfo(event.itemStack);
			event.toolTip.add("Cost:"+merchandiseInfo.getBuyPriceTag());
		}else{
			if(MerchandiseInfo.isPossibleToSell(event.itemStack)){
				MerchandiseInfo merchandiseInfo = new MerchandiseInfo(event.itemStack);
				event.toolTip.add("Sell Price:"+merchandiseInfo.getPrice());
			}

		}
		
		
	}
}

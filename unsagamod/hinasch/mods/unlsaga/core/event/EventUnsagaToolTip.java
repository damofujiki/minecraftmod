package hinasch.mods.unlsaga.core.event;

import hinasch.mods.unlsaga.client.gui.GuiBartering;
import hinasch.mods.unlsaga.client.gui.GuiSmithUnsaga;
import hinasch.mods.unlsaga.core.init.UnsagaItems;
import hinasch.mods.unlsaga.core.init.UnsagaMaterial;
import hinasch.mods.unlsaga.misc.bartering.MerchandiseInfo;
import hinasch.mods.unlsaga.misc.smith.MaterialInfo;
import hinasch.mods.unlsaga.misc.util.EnumUnsagaWeapon;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;

public class EventUnsagaToolTip {

	protected MaterialInfo info;
	
	@ForgeSubscribe
	public void toolTipEvent(ItemTooltipEvent event){
		if(Minecraft.getMinecraft().currentScreen instanceof GuiBartering){
			this.bartering(event);
			return;
		}
		if(!(Minecraft.getMinecraft().currentScreen instanceof GuiSmithUnsaga))return;
		GuiSmithUnsaga guismith = (GuiSmithUnsaga)Minecraft.getMinecraft().currentScreen;
		int currentcategory = guismith.getCurrentCategory();
		ItemStack is = event.itemStack;
		info = new MaterialInfo(event.itemStack);
		if(info.isValidMaterial()){
			
			//if(info.getMaterial().isPresent()){
				UnsagaMaterial material = info.getMaterial().get();
				if(Minecraft.getMinecraft().gameSettings.language.equals("ja_JP")){
					event.toolTip.add("素材使用可："+material.headerJp);
				}else{
					event.toolTip.add("Valid Material:"+material.headerEn);
				}
				if(UnsagaItems.isValidItemForMaterial(EnumUnsagaWeapon.toolArray.get(currentcategory), material)){
					if(Minecraft.getMinecraft().gameSettings.language.equals("ja_JP")){
						event.toolTip.add("ベース素材可："+EnumUnsagaWeapon.toolArray.get(currentcategory));
					}else{
						event.toolTip.add("can use for "+EnumUnsagaWeapon.toolArray.get(currentcategory)+" base");
					}
					
				}
			//}
			//MaterialLibrary info = MaterialLibrary.findInfo(event.itemStack).get();

		}
		
	}

	protected void bartering(ItemTooltipEvent event) {
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

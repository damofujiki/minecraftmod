package hinasch.mods.unlsaga.core.event;

import hinasch.mods.unlsaga.client.gui.GuiSmithUnsaga;
import hinasch.mods.unlsaga.core.init.UnsagaItems;
import hinasch.mods.unlsaga.core.init.UnsagaMaterial;
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
		if(!(Minecraft.getMinecraft().currentScreen instanceof GuiSmithUnsaga))return;
		GuiSmithUnsaga guismith = (GuiSmithUnsaga)Minecraft.getMinecraft().currentScreen;
		int currentcategory = guismith.getCurrentCategory();
		ItemStack is = event.itemStack;
		info = new MaterialInfo(event.itemStack);
		if(info.getMaterial().isPresent()){
			
			if(info.getMaterial().isPresent()){
				UnsagaMaterial material = info.getMaterial().get();
				if(Minecraft.getMinecraft().gameSettings.language.equals("ja_JP")){
					event.toolTip.add("素材使用可："+material.headerJp);
				}else{
					event.toolTip.add("Valid Material:"+material.headerEn);
				}
				if(UnsagaItems.isValidItemMaterial(EnumUnsagaWeapon.toolArray.get(currentcategory), material)){
					if(Minecraft.getMinecraft().gameSettings.language.equals("ja_JP")){
						event.toolTip.add("ベース素材可："+EnumUnsagaWeapon.toolArray.get(currentcategory));
					}else{
						event.toolTip.add("can use for "+EnumUnsagaWeapon.toolArray.get(currentcategory)+" base");
					}
					
				}
			}
			//MaterialLibrary info = MaterialLibrary.findInfo(event.itemStack).get();

		}
		
	}
}

package hinasch.mods.unlsaga.misc.util;

import hinasch.lib.UtilNBT;
import hinasch.mods.unlsaga.Unsaga;
import hinasch.mods.unlsaga.core.init.MaterialList;
import hinasch.mods.unlsaga.core.init.UnsagaMaterial;
import hinasch.mods.unlsaga.item.etc.ItemAccessory;
import hinasch.mods.unlsaga.item.weapon.ItemBowUnsaga;
import hinasch.mods.unlsaga.item.weapon.ItemStaffUnsaga;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;

public class HelperUnsagaWeapon {
	
	protected UnsagaMaterial materialItem;
	protected Icon itemIcon;
	protected HashMap<String,Icon> iconMap;
	
	public HelperUnsagaWeapon(UnsagaMaterial materialItem,Icon itemIcon,HashMap<String,Icon> iconMap){
		this.materialItem = materialItem;
		this.iconMap = iconMap;
		this.itemIcon = itemIcon;
	}

    public static int getCurrentWeight(ItemStack is){
    	if(UtilNBT.hasKey(is, "weight")){
    		return UtilNBT.readFreeTag(is, "weight");
    	}
//    	if(is.getItem() instanceof IUnsagaWeapon){
//    		IUnsagaWeapon iu = (IUnsagaWeapon)is.getItem();
//    		return iu.unsMaterial.weight;
//    	}
    	return MaterialList.dummy.weight;
    }
    

    public int getColorFromItemStack(ItemStack par1ItemStack, int par2)
    {
    	boolean multipass = true;
    	if(par1ItemStack.getItem() instanceof ItemBowUnsaga){
    		multipass = false;
    	}
    	if(par1ItemStack.getItem() instanceof ItemStaffUnsaga){
    		UnsagaMaterial mate = getMaterial(par1ItemStack);
    		if(mate.isChild){
    			if(mate.getParentMaterial()==MaterialList.categorywood){
    				multipass = false;
    			}
    		}
    		
    	}
    	if(par1ItemStack.getItem() instanceof ItemAccessory){
    		multipass = false;
    	}
    	
        if((multipass && par2==0)||(!multipass)){
        	if(HelperUnsagaWeapon.getMaterial(par1ItemStack).getRenderColor().isPresent()){
        		return HelperUnsagaWeapon.getMaterial(par1ItemStack).getRenderColor().get();
        	}
        }
        return 0xFFFFFF;
    }
	
    public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List)
    {
    	if(materialItem.hasSubMaterials()){
    		
    		for(Iterator<UnsagaMaterial> ite=materialItem.getSubMaterials().values().iterator();ite.hasNext();){
    			ItemStack is = new ItemStack(par1,1,0);
    			UnsagaMaterial childMaterial = ite.next();
    			initWeapon(is,childMaterial.name,childMaterial.weight);
    			
    			par3List.add(is);
    		}
    	}else{
    		
    		ItemStack is = new ItemStack(par1,1,0);
			initWeapon(is,materialItem.name,materialItem.weight);
			
    		par3List.add(is);
    	}
        
    }
    

    public Icon getIconIndex(ItemStack par1ItemStack,Icon defa)
    {
        UnsagaMaterial mat = getMaterial(par1ItemStack);
        if(!iconMap.isEmpty()){
        	if(iconMap.containsKey(mat.name)){
        		return iconMap.get(mat.name);
        	}
        }
        return defa;
    }
    
    public static UnsagaMaterial getMaterial(ItemStack is){
    	if(UtilNBT.hasKey(is, "material")){
    		return MaterialList.getMaterial(UtilNBT.readFreeStrTag(is, "material"));
    	}
//    	if(is.getItem() instanceof IUnsagaWeapon){
//    		IUnsagaWeapon iu = (IUnsagaWeapon)is.getItem();
//    		return iu.unsMaterial;
//    	}
    	return MaterialList.dummy;
    }
    

    public static Icon registerIcons(IconRegister par1IconRegister,UnsagaMaterial material,String category)
    {

    	return par1IconRegister.registerIcon(Unsaga.domain+":"+category+"_"+material.iconname);

//    	if(this.unsMaterial.hasSubMaterials()){
//    		for(Iterator<UnsagaMaterial> ite=unsMaterial.getSubMaterials().values().iterator();ite.hasNext();){
//    			
//    			UnsagaMaterial childMat = ite.next();
//    			this.iconMap.put(childMat.name, par1IconRegister.registerIcon(Unsaga.domain+":"+"sword_"+childMat.iconname));
//    		}
//    	}else{
//    		this.itemIcon = par1IconRegister.registerIcon(Unsaga.domain+":"+"sword_"+this.unsMaterial.iconname);
//    	}
//    	

    	
    }
    
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
		if(par1ItemStack!=null){
			if(Minecraft.getMinecraft().gameSettings.language.equals("ja_JP")){
				par3List.add(this.getMaterial(par1ItemStack).jpName);
			}else{
				par3List.add(this.getMaterial(par1ItemStack).headerEn);
			}
			
			if(Unsaga.debug.get()){
				par3List.add("Weight:"+HelperUnsagaWeapon.getCurrentWeight(par1ItemStack));
			}
			if(!Unsaga.debug.get()){
				if(HelperUnsagaWeapon.getCurrentWeight(par1ItemStack)>5){
					par3List.add("W:Heavy");
				}else{
					par3List.add("W:Light");
				}
			}
		}
    }
		
    
    public static void initWeapon(ItemStack is,String mat,int weight){
		UtilNBT.setFreeTag(is, "material", mat);
		UtilNBT.setFreeTag(is, "weight", weight);
		return;
    }
    

}

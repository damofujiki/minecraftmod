package hinasch.mods.unlsaga.misc.util;

import hinasch.lib.UtilNBT;
import hinasch.lib.XYZPos;
import hinasch.mods.unlsaga.Unsaga;
import hinasch.mods.unlsaga.core.init.UnsagaMaterial;
import hinasch.mods.unlsaga.core.init.UnsagaMaterials;
import hinasch.mods.unlsaga.item.IUnsagaMaterial;
import hinasch.mods.unlsaga.item.armor.ItemAccessory;
import hinasch.mods.unlsaga.item.weapon.ItemBowUnsaga;
import hinasch.mods.unlsaga.item.weapon.ItemStaffUnsaga;
import hinasch.mods.unlsaga.misc.ability.Ability;
import hinasch.mods.unlsaga.misc.ability.AbilityRegistry;
import hinasch.mods.unlsaga.misc.ability.HelperAbility;
import hinasch.mods.unlsaga.misc.ability.skill.Skill;
import hinasch.mods.unlsaga.misc.ability.skill.effect.SkillMelee;

import java.util.Iterator;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class HelperUnsagaWeapon {
	
	protected UnsagaMaterial materialItem;
	protected IIcon itemIcon;
	//protected HashMap<String,Icon> iconMap;
	protected AbilityRegistry ar = Unsaga.abilityRegistry;
	protected EnumUnsagaTools category;
	
	public HelperUnsagaWeapon(UnsagaMaterial materialItem,IIcon itemIcon,EnumUnsagaTools category){
		this.materialItem = materialItem;
		this.category = category;
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
    	return getMaterial(is).weight;
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
    			if(mate.getParentMaterial()==UnsagaMaterials.categorywood){
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
	
    public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List)
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
    



    //getMaterial(From ItemStack) if material is MaterialList.failed ,returns Material from NBT.
    public static UnsagaMaterial getMaterial(ItemStack is){
    	if(is.getItem() instanceof IUnsagaMaterial){
    		IUnsagaMaterial iu = (IUnsagaMaterial)is.getItem();
    		if(iu.getMaterial()==UnsagaMaterials.failed){
    			if(UtilNBT.hasKey(is, "material")){
    				return UnsagaMaterials.getMaterial(UtilNBT.readFreeStrTag(is, "material"));
    			}else{
    				return UnsagaMaterials.feather;
    			}
    		}
    		return ((IUnsagaMaterial)is.getItem()).getMaterial();
    	}
    	return UnsagaMaterials.dummy;
//    	if(UtilNBT.hasKey(is, "material")){
//    		return MaterialList.getMaterial(UtilNBT.readFreeStrTag(is, "material"));
//    	}
////    	if(is.getItem() instanceof IUnsagaWeapon){
////    		IUnsagaWeapon iu = (IUnsagaWeapon)is.getItem();
////    		return iu.unsMaterial;
////    	}
//    	return MaterialList.dummy;
    }
    

    public static IIcon registerIcons(IIconRegister par1IconRegister,UnsagaMaterial material,String category)
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
    	String lang = Minecraft.getMinecraft().gameSettings.language;
		if(par1ItemStack!=null){
				par3List.add(this.getMaterial(par1ItemStack).getLocalized());

			
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

			displayAbilities(par1ItemStack, par2EntityPlayer, par3List, par4);
		}
    }
    
    public void displayAbilities(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4){
    	String lang = Minecraft.getMinecraft().gameSettings.language;
    	if(ar.getInheritAbilities(this.category, this.materialItem).isPresent()){
			String str = "";
			for(Iterator<Ability> ite=ar.getInheritAbilities(this.category, this.materialItem).get().iterator();ite.hasNext();){
				Ability abi = ite.next();
				str = str + abi.getName(lang);
			}
			par3List.add(str);
		}
		if(HelperAbility.canGainAbility(par1ItemStack)){
			HelperAbility helperab = new HelperAbility(par1ItemStack,par2EntityPlayer);
			if(helperab.getGainedAbilities().isPresent()){
				for(Ability abi:helperab.getGainedAbilities().get()){
					par3List.add(abi.getName(lang));
				}
			}
		}
    }
		
    
    
    //TODO マテリアルシステム見直し
    public static void initWeapon(ItemStack is,String mat,int weight){
    	if(is.getItem() instanceof IUnsagaMaterial){
    		IUnsagaMaterial iu = (IUnsagaMaterial)is.getItem();
    		if(iu.getMaterial()==UnsagaMaterials.failed){
    			UtilNBT.setFreeTag(is, "material", mat);
    		}
    	}
		
		UtilNBT.setFreeTag(is, "weight", weight);
		return;
    }
    
    
    public static SkillMelee getSkillMelee(SkillMelee.Type type,ItemStack par1ItemStack,EntityPlayer par2EntityPlayer,World par3World,XYZPos pos){
		HelperAbility abHelper  = new HelperAbility(par1ItemStack, par2EntityPlayer);
		SkillMelee pickedSkillEffect = null;
		if(abHelper.getGainedAbilities().isPresent()){
			for(Ability ability:abHelper.getGainedAbilities().get()){
				if(ability instanceof Skill){
					Skill skill = (Skill)ability;
					if(skill.getSkillEffect() instanceof SkillMelee){
						SkillMelee effect = (SkillMelee) skill.getSkillEffect();
						if(effect.getType()==type && effect.canInvoke(par3World, par2EntityPlayer, par1ItemStack, pos)){
							pickedSkillEffect = effect;
						}
					}
					
					
				}
			}
		}
		return pickedSkillEffect;
    }

}

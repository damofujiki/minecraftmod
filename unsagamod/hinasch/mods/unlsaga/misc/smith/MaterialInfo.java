package hinasch.mods.unlsaga.misc.smith;

import hinasch.lib.UtilNBT;
import hinasch.mods.unlsaga.Unsaga;
import hinasch.mods.unlsaga.core.init.NoFuncItemList;
import hinasch.mods.unlsaga.core.init.UnsagaMaterial;
import hinasch.mods.unlsaga.item.etc.ItemIngotsUnsaga;
import hinasch.mods.unlsaga.misc.util.HelperUnsagaWeapon;
import hinasch.mods.unlsaga.misc.util.IUnsagaMaterial;
import hinasch.mods.unlsaga.misc.util.NoFuncItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;

import com.google.common.base.Optional;

public class MaterialInfo {

	
	public ItemStack is;
	
	public MaterialInfo(ItemStack is){
		this.is = is;
	}
	
	public Optional<Integer> getPositiveDamage(){
		Item item = (Item)this.is.getItem();
		if(item instanceof ItemTool){
			return Optional.of(is.getMaxDamage() - is.getItemDamage());
		}
		if(item instanceof ItemSword){
			return Optional.of(is.getMaxDamage() - is.getItemDamage());
		}
		if(item instanceof ItemIngotsUnsaga){
			NoFuncItem nofunc = NoFuncItemList.getDataFromMeta(this.is.getItemDamage());
			return Optional.of(nofunc.forgedamage);
		}
		if(Unsaga.materialFactory.findInfo(this.is).isPresent()){
			MaterialLibraryBook info = (MaterialLibraryBook) Unsaga.materialFactory.findInfo(this.is).get();
			return Optional.of(info.damage);
		}
		return Optional.absent();
	}
	
	public int getWeight(){
		if(UtilNBT.hasKey(this.is, "weight")){
			return HelperUnsagaWeapon.getCurrentWeight(this.is);
			
		}
		if(this.getMaterial().isPresent()){
			return this.getMaterial().get().weight;
		}
		return 0;
	}
	
	public boolean isValidMaterial(){
		if(this.is.getItem() instanceof ItemArmor){
			return false;
		}
		if(this.getMaterial().isPresent()){
			return true;
		}
		return false;
	}
	
	public Optional<UnsagaMaterial> getMaterial(){
		Item item = (Item)this.is.getItem();
		UnsagaMaterial material = null;
		if(item instanceof IUnsagaMaterial){
			material = HelperUnsagaWeapon.getMaterial(this.is);
		}
		if(item instanceof ItemSword && material==null){
			
		}
		if(item instanceof ItemIngotsUnsaga && material==null){
			NoFuncItem nofunc = NoFuncItemList.getDataFromMeta(this.is.getItemDamage());
			material = nofunc.associated;
		}
		if(item instanceof ItemTool && material==null){
			if(Unsaga.materialFactory.findEnumInfo(((ItemTool)item).getToolMaterialName()).isPresent()){
				MaterialLibraryBook info = (MaterialLibraryBook) Unsaga.materialFactory.findEnumInfo(((ItemTool)item).getToolMaterialName()).get();
				material = info.material;
			}
		}
		if(item instanceof ItemSword && material==null){
			if(Unsaga.materialFactory.findEnumInfo(((ItemSword)item).getToolMaterialName()).isPresent()){
				MaterialLibraryBook info = (MaterialLibraryBook) Unsaga.materialFactory.findEnumInfo(((ItemSword)item).getToolMaterialName()).get();
				material = info.material;
			}
		}
		if(Unsaga.materialFactory.findInfo(this.is).isPresent() && material==null){
			MaterialLibraryBook info = (MaterialLibraryBook) Unsaga.materialFactory.findInfo(this.is).get();
			material = info.material;
		}
		if(material==null)return Optional.absent();
		return Optional.of(material);
		
	}
	
}

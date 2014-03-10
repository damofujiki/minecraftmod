package hinasch.mods.unlsaga.misc.smith;

import hinasch.lib.UtilNBT;
import hinasch.mods.unlsaga.Unsaga;
import hinasch.mods.unlsaga.core.init.UnsagaMaterials;
import hinasch.mods.unlsaga.core.init.NoFuncItemList;
import hinasch.mods.unlsaga.core.init.UnsagaMaterial;
import hinasch.mods.unlsaga.item.IUnsagaMaterial;
import hinasch.mods.unlsaga.item.etc.ItemIngotsUnsaga;
import hinasch.mods.unlsaga.misc.util.HelperUnsagaWeapon;
import hinasch.mods.unlsaga.misc.util.NoFuncItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

import com.google.common.base.Optional;

public class MaterialInfo {

	
	public ItemStack is;
	
	public MaterialInfo(ItemStack is){
		this.is = is;
	}
	
	//回復するダメージ量
	public Optional<Integer> getPositiveDamage(){
		Item item = (Item)this.is.getItem();
		if(item.isRepairable()){
			return Optional.of(is.getMaxDamage() - is.getItemDamage());
		}
		if(item instanceof ItemIngotsUnsaga){
			NoFuncItem nofunc = NoFuncItemList.getDataFromMeta(this.is.getItemDamage());
			return Optional.of(nofunc.forgedamage);
		}
		if(Unsaga.materialFactory.find(this.is).isPresent()){
			MaterialLibraryBook info = (MaterialLibraryBook) Unsaga.materialFactory.find(this.is).get();
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
		//鎧は作れない
		if(this.is.getItem() instanceof ItemArmor){
			return false;
		}
		//とりあえず素材の情報がとれたらOK
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
//		if(item instanceof ItemSword && material==null){
//			
//		}
		//素材と関連付けられたアイテムから走査
		for(UnsagaMaterial mate:UnsagaMaterials.allMaterialMap.values()){
			if(mate.getAssociatedItem().isPresent()){
				if(mate.getAssociatedItem().get().getItem()==is.getItem() && is.getItemDamage()==mate.getAssociatedItem().get().getItemDamage()){
					material = mate;
				}
			}
		}
		//無料力アイテムから
		if(item instanceof ItemIngotsUnsaga && material==null){
			NoFuncItem nofunc = NoFuncItemList.getDataFromMeta(this.is.getItemDamage());
			material = nofunc.associated;
		}
		
		if(Unsaga.materialFactory.find(is).isPresent()){
			MaterialLibraryBook book = (MaterialLibraryBook) Unsaga.materialFactory.find(is).get();
			material = book.material;
		}
		//
//		if(item instanceof ItemTool && material==null){
//			if(Unsaga.materialFactory.findEnumInfo(((ItemTool)item).getToolMaterialName()).isPresent()){
//				MaterialLibraryBook info = (MaterialLibraryBook) Unsaga.materialFactory.findEnumInfo(((ItemTool)item).getToolMaterialName()).get();
//				material = info.material;
//			}
//		}
//		if(item instanceof ItemSword && material==null){
//			if(Unsaga.materialFactory.findEnumInfo(((ItemSword)item).getToolMaterialName()).isPresent()){
//				MaterialLibraryBook info = (MaterialLibraryBook) Unsaga.materialFactory.findEnumInfo(((ItemSword)item).getToolMaterialName()).get();
//				material = info.material;
//			}
//		}
//		if(item instanceof ItemArmor && material==null){
//			if(Unsaga.materialFactory.findEnumInfo(((ItemArmor)item).getArmorMaterial().toString()).isPresent()){
//				MaterialLibraryBook info = (MaterialLibraryBook) Unsaga.materialFactory.findEnumInfo(((ItemSword)item).getToolMaterialName()).get();
//				material = info.material;
//			}
//		}
//		if(item instanceof ItemHoe && material==null){
//			if(Unsaga.materialFactory.findEnumInfo(((ItemHoe)item).getMaterialName().toString()).isPresent()){
//				MaterialLibraryBook info = (MaterialLibraryBook) Unsaga.materialFactory.findEnumInfo(((ItemSword)item).getToolMaterialName()).get();
//				material = info.material;
//			}
//		}
//		if(Unsaga.materialFactory.findInfo(this.is).isPresent() && material==null){
//			MaterialLibraryBook info = (MaterialLibraryBook) Unsaga.materialFactory.findInfo(this.is).get();
//			material = info.material;
//		}
		if(material==null)return Optional.absent();
		return Optional.of(material);
		
	}
	
	

}

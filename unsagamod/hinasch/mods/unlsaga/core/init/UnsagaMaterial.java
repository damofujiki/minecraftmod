package hinasch.mods.unlsaga.core.init;

import hinasch.lib.PairID;
import hinasch.mods.unlsaga.Unsaga;
import hinasch.mods.unlsaga.misc.util.EnumUnsagaWeapon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.google.common.base.Optional;

public class UnsagaMaterial {

	protected Optional<Integer> bowModifier = Optional.absent();
	protected Optional<EnumArmorMaterial> enumArmor = Optional.absent();
	protected Optional<EnumToolMaterial> enumTool = Optional.absent();
	public String headerEn;
	public String headerJp;
	public String iconname;
	public boolean isChild;
	public boolean isRelatedVanillaItem = false;
	public Optional<Integer> itemMeta = Optional.absent();
	public String jpName;
	public String name;
	protected UnsagaMaterial parentMaterial;
	public int rank;
	protected PairID associatedItem;
	protected Optional<Integer> renderColor = Optional.absent();
	
	
	protected HashMap<EnumUnsagaWeapon,List<String>> specialArmorTextureMap;

	protected HashMap<EnumUnsagaWeapon,String> specialIconMap;
	protected HashMap<EnumUnsagaWeapon,String> specialNameMap;
	protected HashMap<String,UnsagaMaterial> subMaterialMap = new HashMap();
	public int weight;
	
	
	public UnsagaMaterial(String name,int weight,int rank){
		this.name = name;
		this.weight = weight;
		this.rank = rank;
		this.isChild = false;
		this.iconname = name;
		MaterialList.allMaterialMap.put(this.name, this);
	}
	
	public UnsagaMaterial(String name,int weight,int rank,String en,String jp){

		this(name,weight,rank);
		this.headerEn = en;
		this.headerJp = jp;
		this.jpName = jp;
		
	}
	
	public UnsagaMaterial(String name,int weight,int rank,String en,String jp,String jpname){

		this(name,weight,rank,en,jp);
		this.jpName = jpname;

		
	}
	
	public UnsagaMaterial addSubMaterial(UnsagaMaterial mat){
		if(this.isChild){
			Unsaga.debug("this Material is child.can't add child Material."+mat);
			return mat;
		}
		mat.isChild = true;
		mat.parentMaterial = this;
		this.subMaterialMap.put(mat.name, mat);		
		return mat;
	}
	
	public EnumArmorMaterial getArmorMaterial(){
		if(this.enumArmor.isPresent()){
			return this.enumArmor.get();
		}
		if(this.isChild){
			return this.getParentMaterial().getArmorMaterial();
		}
		Unsaga.debug("this Material has no EnumArmorMAterial:"+this.name);
		return EnumArmorMaterial.CLOTH;
	}
	public int getBowModifier(){
		if(this.bowModifier.isPresent()){
			return this.bowModifier.get();
		}
		return 0;
	}
	
	public int getItemMeta(){
		return this.itemMeta.get();
	}

	public UnsagaMaterial getParentMaterial(){
		Optional<UnsagaMaterial> mat = Optional.absent();
		if(this.parentMaterial!=null){
			mat = Optional.of(this.parentMaterial);
		}
		return mat.get();
	}
	
	public UnsagaMaterial getRandomSubMaterial(Random rand){
		if(this.hasSubMaterials()){
			List<UnsagaMaterial> mlist = new ArrayList(this.subMaterialMap.values());
			return mlist.get(rand.nextInt(mlist.size()));
		}
		return this;
	}
	
	public Optional<ItemStack> getAssociatedItem(){
		if(this.associatedItem!=null){
			return Optional.of(new ItemStack(this.associatedItem.id,1,this.associatedItem.metadata));
		}
		return Optional.absent();
	}
	
	public Optional<Integer> getRenderColor() {
		if(this.renderColor.isPresent()){
			return this.renderColor;
		}
		if(this.isChild){
			if(this.parentMaterial.getRenderColor().isPresent()){
				return this.parentMaterial.getRenderColor();
			}
		}
		return Optional.absent();
	}
	
	public Optional<String> getSpecialArmorTexture(EnumUnsagaWeapon category,int par1){
		if(this.specialArmorTextureMap!=null){
			if(this.specialArmorTextureMap.get(category)!=null){
				return Optional.of(this.specialArmorTextureMap.get(category).get(par1));
			}
		}
		if(this.isChild && this.getParentMaterial().specialArmorTextureMap!=null){
			return this.getParentMaterial().getSpecialArmorTexture(category, par1);
		}
		return Optional.absent();
	}
	
	public Optional<String> getSpecialIcon(EnumUnsagaWeapon category){
		if(this.specialIconMap!=null){
			if(this.specialIconMap.get(category)!=null){
				return Optional.of(this.specialIconMap.get(category));
			}
		}
		if(this.isChild){
			return this.getParentMaterial().getSpecialIcon(category);
		}
		return Optional.absent();
	}
	
	public Optional<String> getSpecialName(EnumUnsagaWeapon category,int en_or_jp){
		if(specialNameMap!=null){
			if(specialNameMap.get(category)!=null){
				String[] names = specialNameMap.get(category).split(",");
				if(names.length!=2)return Optional.absent();
				return Optional.of(names[en_or_jp]);
			}
		}
		return Optional.absent();
	}
	
	public Optional<UnsagaMaterial> getSubMaterial(String key){
		Optional<UnsagaMaterial> material = Optional.absent();
		if(this.hasSubMaterials()){
			material = Optional.of(this.subMaterialMap.get(key));
			return material;
		}
		return material;
	}
	
	public Map<String,UnsagaMaterial> getSubMaterials(){
		return this.subMaterialMap;
	}
	
	public EnumToolMaterial getToolMaterial(){
		if(this.enumTool.isPresent()){
			return this.enumTool.get();
		}
		if(this.isChild){
			return this.getParentMaterial().getToolMaterial();
		}
		Unsaga.debug("this Material has no EnumToolMAterial:"+this.name);
		return EnumToolMaterial.STONE;
	}
	

	public boolean hasSubMaterials(){
		if(this.subMaterialMap.isEmpty()){
			return false;
		}
		return true;
	}
	
	public void linkToItem(int meta){
		this.itemMeta = Optional.of(meta);
	}
	
	public void associate(ItemStack is){
		Item item = (Item)is.getItem();
		this.associatedItem = new PairID();
		this.associatedItem.id = item.itemID;
		this.associatedItem.metadata = is.getItemDamage();
	}
	
	public UnsagaMaterial setArmorMaterial(EnumArmorMaterial par1){
		this.enumArmor = Optional.of(par1);
		return this;
	}
	
	public UnsagaMaterial setBowModifier(int par1){
		this.bowModifier = Optional.of(par1);
		return this;
	}
	
	public UnsagaMaterial setIconKey(String key){
		this.iconname = key;
		return this;
	}
	
	public UnsagaMaterial setRenderColor(int par1){
		this.renderColor = Optional.of(par1);
		return this;
	}

	public UnsagaMaterial setSpecialArmorTexture(EnumUnsagaWeapon category,String par1,String par2){
		if(this.specialArmorTextureMap==null){
			this.specialArmorTextureMap = new HashMap();
		}
		ArrayList<String> texturePair = new ArrayList();
		texturePair.add(par1);
		texturePair.add(par2);
		this.specialArmorTextureMap.put(category, texturePair);
		return this;
	}
	
	public UnsagaMaterial setSpecialIcon(EnumUnsagaWeapon category,String name){
		if(specialIconMap==null)this.specialIconMap = new HashMap();
		this.specialIconMap.put(category, name);
		return this;
	}
	
	public UnsagaMaterial setSpecialName(EnumUnsagaWeapon category,String name){
		if(specialNameMap==null)this.specialNameMap = new HashMap();
		this.specialNameMap.put(category, name);
		return this;
	}
	
	public UnsagaMaterial setToolMaterial(EnumToolMaterial par1){
		this.enumTool = Optional.of(par1);
		return this;
	}
}

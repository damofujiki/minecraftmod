package hinasch.mods.unlsaga.item.etc;

import hinasch.mods.unlsaga.Unsaga;
import hinasch.mods.unlsaga.client.model.ModelArmorColored;
import hinasch.mods.unlsaga.core.init.MaterialList;
import hinasch.mods.unlsaga.core.init.UnsagaItems;
import hinasch.mods.unlsaga.core.init.UnsagaMaterial;
import hinasch.mods.unlsaga.misc.translation.Translation;
import hinasch.mods.unlsaga.misc.util.EnumUnsagaWeapon;
import hinasch.mods.unlsaga.misc.util.HelperUnsagaWeapon;
import hinasch.mods.unlsaga.misc.util.IUnsagaMaterial;

import java.util.List;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class ItemArmorUnsaga extends ItemArmor implements IUnsagaMaterial{

	//必要なのかよくわからない
	public static int getRenderIndex(UnsagaMaterial mat){
		if(mat.isChild){
			if(mat.getParentMaterial()==MaterialList.cloth){
				return 0;
			}
		}
		if(mat==MaterialList.liveSilk){
			return 0;
		}
		return 1;
	}
	
	protected String[] armorTextureFiles;
	protected EnumUnsagaWeapon armorType;
	protected HelperUnsagaWeapon helper;
	protected UnsagaMaterial material;
	protected ModelArmorColored modelBiped;
	
	protected String path = Unsaga.domain+":textures/models/armor/";
	
	
	public ItemArmorUnsaga(int par1, EnumArmorMaterial par2EnumArmorMaterial,
			EnumUnsagaWeapon armorType, int armortypeint,UnsagaMaterial material) {
		super(par1, par2EnumArmorMaterial, getRenderIndex(material), armortypeint);
		this.material = material;
		this.armorType = armorType;
		this.armorTextureFiles = new String[2];
		this.armorTextureFiles[0] = path+getArmorTextureFilename(material)+".png";
		this.armorTextureFiles[1] = path+getArmorTextureFilename(material)+"2.png";
		this.helper = new HelperUnsagaWeapon(material, this.itemIcon, armorType);
		if(this.material == MaterialList.crocodileLeather){
			modelBiped = new ModelArmorColored(1.001F);
		}else{
			modelBiped = new ModelArmorColored(1.08F);
		}
		
		UnsagaItems.putItemMap(this.itemID, this.armorType.toString()+"."+material.name);

		// TODO 自動生成されたコンストラクター・スタブ
	}
	
	@Override
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
		helper.addInformation(par1ItemStack, par2EntityPlayer, par3List, par4);
	}
	
	//染める
    @Override
    public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, int armorSlot)
    {
    	modelBiped.bipedHead.showModel = armorSlot == 0;
    	modelBiped.bipedHeadwear.showModel = armorSlot == 0;
    	modelBiped.bipedBody.showModel = armorSlot == 1;
    	modelBiped.bipedCloak.showModel = armorSlot == 1;
    	modelBiped.bipedLeftArm.showModel = armorSlot == 1;
    	modelBiped.bipedRightArm.showModel = armorSlot == 1;
    	modelBiped.bipedRightLeg.showModel = (armorSlot == 3)||(armorSlot == 2);
    	modelBiped.bipedLeftLeg.showModel = (armorSlot == 3)||(armorSlot == 2);
    	
    	modelBiped.setItemStack(itemStack);
        return modelBiped;
    }
    
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, int layer){
		// TODO 自動生成されたメソッド・スタブ
		ItemArmorUnsaga armorunsaga = (ItemArmorUnsaga)stack.getItem();
		EnumUnsagaWeapon type = armorunsaga.armorType;
		UnsagaMaterial mate = HelperUnsagaWeapon.getMaterial(stack);
		for(int i=0;i<2;i++){
			if(mate.getSpecialArmorTexture(type, 0).isPresent()){
				this.armorTextureFiles[i] = path+mate.getSpecialArmorTexture(type, i).get()+".png";
			}
				
		}
		
		if(type==EnumUnsagaWeapon.HELMET || type==EnumUnsagaWeapon.BOOTS || type==EnumUnsagaWeapon.ARMOR)
		{
			return armorTextureFiles[0];
		}
		if(type==EnumUnsagaWeapon.LEGGINS)
		{
			return armorTextureFiles[1];
		}
		Unsaga.debug("Unknown ArmorType???");
		return armorTextureFiles[0];
	}

    public String getArmorTextureFilename(UnsagaMaterial mat){
//		if(getRenderIndex(mat)==0 && armorType.equals("boots")){
//			return "socks";
//		}
		return "armor";
		
	}
    
    @Override
    public int getColor(ItemStack par1ItemStack)
    {
		
		return helper.getColorFromItemStack(par1ItemStack, 0);
    }
    
    @Override
    public int getColorFromItemStack(ItemStack par1ItemStack, int par2)
    {


		return helper.getColorFromItemStack(par1ItemStack, par2);
        
    }
    
	@Override
    public Icon getIconFromDamageForRenderPass(int par1, int par2)
    {
        return this.itemIcon;
    }
	
	
	@Override
    public String getItemDisplayName(ItemStack par1ItemStack)
    {
    	if(HelperUnsagaWeapon.getMaterial(par1ItemStack).getSpecialName(this.armorType, 0).isPresent()){
    		if(Translation.isJapanese()){
    			return HelperUnsagaWeapon.getMaterial(par1ItemStack).getSpecialName(this.armorType, 1).get();
    		}else{
    			if(!HelperUnsagaWeapon.getMaterial(par1ItemStack).getSpecialName(this.armorType, 0).get().equals("*")){
    				return HelperUnsagaWeapon.getMaterial(par1ItemStack).getSpecialName(this.armorType, 0).get();
    			}
    			
    		}
    	}
    	
        return super.getItemDisplayName(par1ItemStack);
    }
	
	@Override
    public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List)
    {
    	helper.getSubItems(par1, par2CreativeTabs, par3List);
        
    }
	
	//無理矢理頭にかぶる
	@Override
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
		if(!par3EntityPlayer.isSneaking())return super.onItemRightClick(par1ItemStack, par2World, par3EntityPlayer);
		
        //int i = EntityLiving.getArmorPosition(par1ItemStack) - 1;
        ItemStack itemstack1 = par3EntityPlayer.getCurrentArmor(0);

        if (itemstack1 == null)
        {
            par3EntityPlayer.setCurrentItemOrArmor(1, par1ItemStack.copy()); //Forge: Vanilla bug fix associated with fixed setCurrentItemOrArmor indexs for players.
            par1ItemStack.stackSize = 0;
        }

        return par1ItemStack;
    }
	
	@Override
	public void registerIcons(IconRegister par1IconRegister)
	{
		if(this.material.getSpecialIcon(this.armorType).isPresent()){
			this.itemIcon = par1IconRegister.registerIcon(Unsaga.domain+":"+this.material.getSpecialIcon(armorType).get());
			return;
		}
		this.itemIcon = par1IconRegister.registerIcon(Unsaga.domain+":"+this.armorType);
	}
	
	@Override
    public boolean requiresMultipleRenderPasses()
    {
        return false;
    }
	
	@Override
	public EnumUnsagaWeapon getCategory() {
		// TODO 自動生成されたメソッド・スタブ
		return this.armorType;
	}

}

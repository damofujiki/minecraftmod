package hinasch.mods.unlsaga.item.weapon;

import hinasch.mods.unlsaga.Unsaga;
import hinasch.mods.unlsaga.core.init.UnsagaItems;
import hinasch.mods.unlsaga.core.init.UnsagaMaterial;
import hinasch.mods.unlsaga.misc.util.EnumUnsagaWeapon;
import hinasch.mods.unlsaga.misc.util.HelperUnsagaWeapon;
import hinasch.mods.unlsaga.misc.util.IUnsagaMaterial;

import java.util.HashMap;
import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.Icon;

public class ItemSwordUnsaga extends ItemSword implements IUnsagaMaterial{

	public final UnsagaMaterial unsMaterial;
	protected final HashMap<String,Icon> iconMap = new HashMap();
	protected final HelperUnsagaWeapon helper;
	protected final Icon[] icons;
	
	public ItemSwordUnsaga(int par1, EnumToolMaterial par2EnumToolMaterial,UnsagaMaterial mat) {
		super(par1, par2EnumToolMaterial);
		this.icons = new Icon[2];
		this.unsMaterial = mat;
		this.helper = new HelperUnsagaWeapon(this.unsMaterial,this.itemIcon,EnumUnsagaWeapon.SWORD);
		Unsaga.proxy.registerSpecialRenderer(this.itemID);
		UnsagaItems.putItemMap(this.itemID,EnumUnsagaWeapon.SWORD.toString()+"."+mat.name);
		//UnsagaItems.registerValidTool(EnumUnsagaWeapon.SWORD, mat, this.itemID);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	
	@Override
    public boolean requiresMultipleRenderPasses()
    {
        return true;
    }
    
	@Override
    public Icon getIconFromDamageForRenderPass(int par1, int par2)
    {
		if(par2==0){
			return this.icons[0];
		}
        return this.icons[1];
    }
	
	@Override
    public int getColorFromItemStack(ItemStack par1ItemStack, int par2)
    {
		return helper.getColorFromItemStack(par1ItemStack, par2);
    }
    
	@Override
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
		helper.addInformation(par1ItemStack, par2EntityPlayer, par3List, par4);
	}
	
    @Override
    public void registerIcons(IconRegister par1IconRegister)
    {
    	//this.itemIcon = par1IconRegister.registerIcon(Unsaga.domain+":"+"sword_"+this.unsMaterial.iconname);

    	this.itemIcon = par1IconRegister.registerIcon(Unsaga.domain+":"+"sword");
    	this.icons[0] = par1IconRegister.registerIcon(Unsaga.domain+":"+"sword_1");
    	this.icons[1] = par1IconRegister.registerIcon(Unsaga.domain+":"+"sword_2");
    	//this.itemIcon = HelperUnsagaWeapon.registerIcons(par1IconRegister, unsMaterial, "sword");
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
    
    
    @Override
    public EnumAction getItemUseAction(ItemStack par1ItemStack)
    {
    	if(HelperUnsagaWeapon.getCurrentWeight(par1ItemStack)>5){
    		return EnumAction.none;
    	}
        return EnumAction.block;
    }
    
    public UnsagaMaterial getMaterial(ItemStack is){
    	return HelperUnsagaWeapon.getMaterial(is);
    }
    
    @Override
    public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List)
    {
    	helper.getSubItems(par1, par2CreativeTabs, par3List);
        
    }
    
//    @Override
//    public Icon getIconIndex(ItemStack par1ItemStack)
//    {
//
//        return helper.getIconIndex(par1ItemStack, this.getIconFromDamage(par1ItemStack.getItemDamage()));
//    }
    
	@Override
	public EnumUnsagaWeapon getCategory() {
		// TODO 自動生成されたメソッド・スタブ
		return EnumUnsagaWeapon.SWORD;
	}
}

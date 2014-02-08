package hinasch.mods.unlsaga.item.weapon;

import hinasch.mods.unlsaga.Unsaga;
import hinasch.mods.unlsaga.core.init.UnsagaItems;
import hinasch.mods.unlsaga.core.init.UnsagaMaterial;
import hinasch.mods.unlsaga.misc.util.EnumUnsagaWeapon;
import hinasch.mods.unlsaga.misc.util.HelperUnsagaWeapon;
import hinasch.mods.unlsaga.misc.util.IUnsagaWeapon;

import java.util.HashMap;
import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;

public class ItemAxeUnsaga extends ItemAxe implements IUnsagaWeapon{

	public final UnsagaMaterial unsMaterial;
	protected final HashMap<String,Icon> iconMap = new HashMap();
	protected final HelperUnsagaWeapon helper;
	protected Icon[] icons;
	
	public ItemAxeUnsaga(int par1, EnumToolMaterial par2EnumToolMaterial,UnsagaMaterial mat) {
		super(par1, par2EnumToolMaterial);
		this.unsMaterial = mat;
		this.icons = new Icon[2];
		this.helper = new HelperUnsagaWeapon(this.unsMaterial,this.itemIcon,this.iconMap);
		Unsaga.proxy.registerSpecialRenderer(this.itemID);
		UnsagaItems.putItemMap(this.itemID,EnumUnsagaWeapon.AXE.toString()+"."+mat.name);
		//UnsagaItems.registerValidTool(EnumUnsagaWeapon.SPEAR, mat, this.itemID);
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
    	this.itemIcon = par1IconRegister.registerIcon(Unsaga.domain+":"+"axe");
    	this.icons[0] = par1IconRegister.registerIcon(Unsaga.domain+":"+"axe_1");
    	this.icons[1] = par1IconRegister.registerIcon(Unsaga.domain+":"+"axe_2");
    }

    @Override
    public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List)
    {
    	helper.getSubItems(par1, par2CreativeTabs, par3List);
        
    }
}

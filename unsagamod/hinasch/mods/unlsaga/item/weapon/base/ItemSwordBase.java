package hinasch.mods.unlsaga.item.weapon.base;


import hinasch.mods.unlsaga.Unsaga;
import hinasch.mods.unlsaga.core.init.UnsagaMaterial;
import hinasch.mods.unlsaga.item.IUnsagaMaterial;
import hinasch.mods.unlsaga.misc.ability.IGainAbility;
import hinasch.mods.unlsaga.misc.util.EnumUnsagaTools;
import hinasch.mods.unlsaga.misc.util.HelperUnsagaWeapon;

import java.util.HashMap;
import java.util.List;

import javax.swing.Icon;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.IIcon;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

public class ItemSwordBase extends ItemSword implements IUnsagaMaterial,IGainAbility{


			
	protected final HelperUnsagaWeapon helper;
	protected final HashMap<String,Icon> iconMap = new HashMap();
	protected final IIcon[] icons;
	public final UnsagaMaterial unsMaterial;
	public final float weapondamage;
	
	public ItemSwordBase(UnsagaMaterial mat) {
		super(mat.getToolMaterial());
		this.weapondamage = 4.0F + mat.getToolMaterial().getDamageVsEntity();
		this.icons = new IIcon[2];
		this.unsMaterial = mat;
		this.helper = new HelperUnsagaWeapon(this.unsMaterial,this.itemIcon,EnumUnsagaTools.SWORD);
	}

        
    
	@Override
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
		helper.addInformation(par1ItemStack, par2EntityPlayer, par3List, par4);
	}
	
	@Override
	public EnumUnsagaTools getCategory() {
		// TODO 自動生成されたメソッド・スタブ
		return EnumUnsagaTools.SWORD;
	}
    
	@Override
    public int getColorFromItemStack(ItemStack par1ItemStack, int par2)
    {
		return helper.getColorFromItemStack(par1ItemStack, par2);
    }

    
	@Override
    public IIcon getIconFromDamageForRenderPass(int par1, int par2)
    {
		if(par2==0){
			return this.icons[0];
		}
        return this.icons[1];
    }
    
	@Override
    public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack)
    {
        return true;
    }
	
    //thanx:http://forum.minecraftuser.jp/viewtopic.php?f=21&t=9494&start=300
	@Override
    public Multimap getItemAttributeModifiers()
    {
        Multimap multimap = HashMultimap.create();//putメソッドを使う際に同じキーの要素は登録されないため．ItemSwordのものを持ってきてはいけない．
        multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Weapon modifier", (double)this.weapondamage, 0));
        return multimap;
    }
    
    
    @Override
    public EnumAction getItemUseAction(ItemStack par1ItemStack)
    {
    	if(HelperUnsagaWeapon.getCurrentWeight(par1ItemStack)>5){
    		return EnumAction.none;
    	}
        return EnumAction.block;
    }


	@Override
	public UnsagaMaterial getMaterial() {
		// TODO 自動生成されたメソッド・スタブ
		return this.unsMaterial;
	}


	@Override
	public int getMaxAbility() {
		// TODO 自動生成されたメソッド・スタブ
		return 1;
	}

	@Override
    public void registerIcons(IIconRegister par1IconRegister)
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
    public boolean requiresMultipleRenderPasses()
    {
        return true;
    }
}

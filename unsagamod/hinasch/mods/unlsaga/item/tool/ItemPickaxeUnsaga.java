package hinasch.mods.unlsaga.item.tool;

import hinasch.mods.unlsaga.Unsaga;
import hinasch.mods.unlsaga.core.init.UnsagaItems;
import hinasch.mods.unlsaga.core.init.UnsagaMaterial;
import hinasch.mods.unlsaga.item.IUnsagaMaterial;
import hinasch.mods.unlsaga.misc.util.EnumUnsagaTools;
import hinasch.mods.unlsaga.misc.util.HelperUnsagaItem;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

public class ItemPickaxeUnsaga extends ItemPickaxe implements IUnsagaMaterial{
	
	protected final HelperUnsagaItem helper;
	protected final float weaponDamage;
	protected IIcon[] icons;
	protected UnsagaMaterial unsagaMaterial;
	
	public ItemPickaxeUnsaga(UnsagaMaterial us) {
		super(us.getToolMaterial());
		this.helper = new HelperUnsagaItem(us,this.itemIcon,EnumUnsagaTools.PICKAXE);
		this.icons = new IIcon[2];
		this.unsagaMaterial = us;
		this.weaponDamage = 2.0F + us.getToolMaterial().getDamageVsEntity();
		UnsagaItems.putItemMap(this,EnumUnsagaTools.PICKAXE.toString()+"."+us.name);
	}

	@Override
	public EnumUnsagaTools getCategory() {
		// TODO 自動生成されたメソッド・スタブ
		return EnumUnsagaTools.PICKAXE;
	}

//	@Override
//    public float getStrVsBlock(ItemStack par1ItemStack, Block par2Block)
//    {
//        return par2Block != null && (par2Block.blockMaterial == Material.iron || par2Block.blockMaterial == Material.anvil || par2Block.blockMaterial == Material.rock) ? this.getEfficiencyOnProperMaterial(par1ItemStack) : super.getStrVsBlock(par1ItemStack, par2Block);
//    }

	@Override
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
		helper.addInformation(par1ItemStack, par2EntityPlayer, par3List, par4);
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
	public void registerIcons(IIconRegister par1IconRegister)
	{
		this.icons[0] = par1IconRegister.registerIcon(Unsaga.domain+":"+"pickaxe_1");
		this.icons[1] = par1IconRegister.registerIcon(Unsaga.domain+":"+"pickaxe_2");
	}
	
	@Override
	public int getColorFromItemStack(ItemStack par1ItemStack, int par2)
	{
		return helper.getColorFromItemStack(par1ItemStack, par2);
	}
	
	@Override
    public Multimap getItemAttributeModifiers()
    {
        Multimap multimap = HashMultimap.create();//putメソッドを使う際に同じキーの要素は登録されないため．ItemSwordのものを持ってきてはいけない．
        multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Weapon modifier", (double)this.weaponDamage, 0));
        return multimap;
    }
	
	@Override
	public UnsagaMaterial getMaterial() {
		// TODO 自動生成されたメソッド・スタブ
		return this.unsagaMaterial;
	}
	
	@Override
	public boolean requiresMultipleRenderPasses()
	{
		return true;
	}
//	protected float getEfficiencyOnProperMaterial(ItemStack par1ItemStack) {
//		// TODO 自動生成されたメソッド・スタブ
//		UnsagaMaterial us = HelperUnsagaWeapon.getMaterial(par1ItemStack);
//		return us.getToolMaterial().getEfficiencyOnProperMaterial();
//	}
}

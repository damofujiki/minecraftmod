package hinasch.mods.unlsaga.item.tool;

import hinasch.mods.unlsaga.Unsaga;
import hinasch.mods.unlsaga.core.init.UnsagaMaterial;
import hinasch.mods.unlsaga.item.IUnsagaMaterial;
import hinasch.mods.unlsaga.misc.util.EnumUnsagaTools;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.util.IIcon;

public class ItemPickaxeUnsaga extends ItemPickaxe implements IUnsagaMaterial{

	protected IIcon[] icons;
	protected UnsagaMaterial unsagaMaterial;
	
	public ItemPickaxeUnsaga(UnsagaMaterial us) {
		super(us.getToolMaterial());
		this.icons = new IIcon[2];
		this.unsagaMaterial = us;
		// TODO 自動生成されたコンストラクター・スタブ
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
	public UnsagaMaterial getMaterial() {
		// TODO 自動生成されたメソッド・スタブ
		return this.unsagaMaterial;
	}
	
//	protected float getEfficiencyOnProperMaterial(ItemStack par1ItemStack) {
//		// TODO 自動生成されたメソッド・スタブ
//		UnsagaMaterial us = HelperUnsagaWeapon.getMaterial(par1ItemStack);
//		return us.getToolMaterial().getEfficiencyOnProperMaterial();
//	}
}

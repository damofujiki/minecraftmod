//package hinasch.mods.unlsaga.block;
//
//import hinasch.lib.HSLibs;
//import hinasch.lib.XYZPos;
//import hinasch.mods.unlsaga.Unsaga;
//import hinasch.mods.unlsaga.core.init.NoFuncItemList;
//import hinasch.mods.unlsaga.core.init.UnsagaBlocks;
//import hinasch.mods.unlsaga.core.init.UnsagaMaterials;
//import hinasch.mods.unlsaga.misc.translation.Translation;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Random;
//
//import net.minecraft.block.Block;
//import net.minecraft.block.BlockOre;
//import net.minecraft.client.renderer.texture.IIconRegister;
//import net.minecraft.creativetab.CreativeTabs;
//import net.minecraft.enchantment.EnchantmentHelper;
//import net.minecraft.entity.player.EntityPlayer;
//import net.minecraft.item.Item;
//import net.minecraft.item.ItemStack;
//import net.minecraft.item.crafting.FurnaceRecipes;
//import net.minecraft.util.IIcon;
//import net.minecraft.util.MathHelper;
//import net.minecraft.world.IBlockAccess;
//import net.minecraft.world.World;
//
//import com.google.common.collect.Lists;
//
//public class BlockOreUnsaga extends BlockOre{
//
//	public IIcon[] icons;
//	public static ArrayList<String> oreDictionaryList = Lists.newArrayList("oreLead","oreRuby","oreSapphire","oreSilver","oreCopper","oreAngelite","oreDemonite");
//	public static ArrayList<String> unlocalizedNames = Lists.newArrayList("lead","ruby","sapphire","silver","copper","angelite","demonite");
//	public static ArrayList<Float> exps = Lists.newArrayList(0.7F,1.0F,1.0F,0.7F,0.7F,1.0F,1.0F);
//	public static ArrayList<Integer> smelted = Lists.newArrayList(5,6,7,4,3,16,17);
//	public ArrayList<String> localizedNameJP = Lists.newArrayList("鉛鉱石","鋼玉鉱石","鋼玉鉱石","銀鉱石","銅鉱石","聖石鉱石","魔石鉱石");
//	public ArrayList<Integer> harvestLevel = Lists.newArrayList(1,2,2,1,1,1,1);
//	public ArrayList<String> localizedNameEN = Lists.newArrayList("Lead Ore","Corundum Ore","Corundum Ore","Silver Ore","Copper Ore","Angelite Ore","Demonite Ore");
//	public ArrayList<Integer> containerItem = Lists.newArrayList(-1,6,7,-1,-1,16,17);
//
//
//	public BlockOreUnsaga() {
//		super();
//
//		this.icons = new IIcon[oreDictionaryList.size()];
//		for(int i=0;i<getSize();i++){
//			this.setHarvestLevel("pickaxe", this.harvestLevel.get(i), i);
//		}
//
//
//		//this.registerLocalized();
//	}
//	
//	public static int getSize(){
//		return oreDictionaryList.size();
//	}
//
//	public static void registerSmeltingAndAssociation(){
//		
//
//
//		for(int i=0;i<getSize();i++){
//			ItemStack blockitem = new ItemStack(UnsagaBlocks.blockOreUnsaga,1,i);
//			ItemStack smelted = NoFuncItemList.getItemStack(1, BlockOreUnsaga.smelted.get(i));			
//			FurnaceRecipes.smelting().func_151394_a(blockitem, smelted, BlockOreUnsaga.exps.get(i));
//		}
//
//		UnsagaMaterials.copperOre.associate(new ItemStack(UnsagaBlocks.blockOreUnsaga,1,oreDictionaryList.indexOf("oreCopper")));
//
//	}
//
//	public void registerLocalized(){
//		Translation tr = Unsaga.translation.getInstance();
//		int index = 0;
//		for(String key:unlocalizedNames){
//			tr.add(key, localizedNameEN.get(index), localizedNameJP.get(index));
//			index +=1;
//		}
//	}
//
//	@Override
//	public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List)
//	{
//		for (int j = 0; j < this.oreDictionaryList.size(); ++j)
//		{
//			par3List.add(new ItemStack(par1, 1, j));
//		}
//	}
//
//	@Override
//	public void registerBlockIcons(IIconRegister par1IconRegister)
//	{
//		//this.blockIcon = par1IconRegister.registerIcon("workbench_side");
//		for(int i=0;i<getSize();i++){
//			this.icons[i] = par1IconRegister.registerIcon(Unsaga.domain+":"+oreDictionaryList.get(i));
//		}
//	}
//
//	@Override
//	public IIcon getIcon(int par1, int par2)
//	{
//		return this.icons[par2];
//	}
//
//
//
//	@Override
//	public Item getItemDropped(int par1, Random par2Random, int par3)
//	{	
//		return this.containerItem.get(par1)==-1? Item.getItemFromBlock(this) : null;
//	}
//
//	@Override
//	public int damageDropped(int par1)
//	{
//		//		if(this.containerItem.get(par1)!=-1){
//		//			return this.containerItem.get(par1);
//		//		}
//		//        return par1;
//		return this.containerItem.get(par1)==-1? par1 : -1;
//	}
//
//	@Override
//	public int quantityDropped(int meta, int fortune, Random random)
//	{
//		Item drop = this.getItemDropped(meta, random, fortune);
//		if (fortune > 0 && this != Block.getBlockFromItem(drop))
//		{
//			int j = random.nextInt(fortune + 2) - 1;
//
//			if (j < 0)
//			{
//				j = 0;
//			}
//
//			return this.quantityDropped(random) * (j + 1);
//		}
//		else
//		{
//			return this.quantityDropped(random);
//		}
//	}
//
//	@Override
//	public void onBlockHarvested(World par1World, int par2, int par3, int par4, int par5, EntityPlayer par6EntityPlayer) {
//		if(this.containerItem.get(par5)!=-1){
//			int fortune = EnchantmentHelper.getFortuneModifier(par6EntityPlayer);
//			ItemStack drop = NoFuncItemList.getItemStack(this.quantityDropped(par5, fortune, par1World.rand), this.containerItem.get(par5));
//			XYZPos p = new XYZPos(par2,par3,par4);
//			if(!par1World.isRemote){
//				HSLibs.dropItem(par1World, drop, p.dx, p.dy, p.dz);
//			}
//
//		}
//	}
//
//
//    private Random rand = new Random();
//	@Override
//	public int getExpDrop(IBlockAccess par1World, int par5, int par7)
//	{
//		if (this.getItemDropped(par5, rand, par7) != Item.getItemFromBlock(this))
//		{
//			int j1 = 0;
//
//			if (this.containerItem.get(par5)!=-1)
//			{
//				j1 = MathHelper.getRandomIntegerInRange(rand, 2, 5);
//			}
//			return j1;
//		}
//
//		return 0;
//	}
//}

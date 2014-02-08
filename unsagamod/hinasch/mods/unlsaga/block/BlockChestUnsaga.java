package hinasch.mods.unlsaga.block;

import hinasch.mods.unlsaga.tileentity.TileEntityChestUnsaga;

import java.util.Random;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockChestUnsaga extends BlockContainer{

    private final Random random = new Random();
    
	public BlockChestUnsaga(int par1,int providepower) {
		super(par1, Material.wood);
		// TODO Auto-generated constructor stub
	}

	
	@Override
    public void registerIcons(IconRegister par1IconRegister)
    {
        this.blockIcon = par1IconRegister.registerIcon("planks_oak");
    }
    
	/**
	 * Called upon block activation (right click on the block.)
	 */
	@Override
	public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
	{

		TileEntityChestUnsaga var10 = (TileEntityChestUnsaga)par1World.getBlockTileEntity(par2, par3, par4);

		if(var10 == null){
			return true;
		}

		boolean chestopen = var10.touchChest(par5EntityPlayer);

		if(chestopen){
			var10.setItemsToChest(par1World.rand);
			chestFunc(par1World, par2, par3, par4, par5EntityPlayer, par6, par7, par8, par9);
		}
		return false;
	}

	
    public boolean chestFunc(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
    {
    	
        if (par1World.isRemote)
        {
            return true;
        }
        else
        {
            IInventory iinventory = this.getInventory(par1World, par2, par3, par4);

            if (iinventory != null)
            {
                par5EntityPlayer.displayGUIChest(iinventory);
            }

            return true;
        }
    }
    
	private IInventory getInventory(World par1World, int par2, int par3,
			int par4) {
		Object object = (TileEntityChest)par1World.getBlockTileEntity(par2, par3, par4);
		return (IInventory)object;
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{

		this.setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);

	}


	@Override
	public TileEntity createNewTileEntity(World par1World)
	{
		TileEntityChestUnsaga var1 = new TileEntityChestUnsaga();
		var1.init(par1World);
		return var1;
	}

	@Override
	public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4)
	{
		int l = 0;

		if (par1World.getBlockId(par2 - 1, par3, par4) == this.blockID)
		{
			++l;
		}

		if (par1World.getBlockId(par2 + 1, par3, par4) == this.blockID)
		{
			++l;
		}

		if (par1World.getBlockId(par2, par3, par4 - 1) == this.blockID)
		{
			++l;
		}

		if (par1World.getBlockId(par2, par3, par4 + 1) == this.blockID)
		{
			++l;
		}

		return l < 1 ;
	}

	@Override
    public boolean isOpaqueCube()
    {
        return false;
    }

	@Override
	public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6)
	{

		TileEntityChestUnsaga var10 = (TileEntityChestUnsaga)par1World.getBlockTileEntity(par2, par3, par4);
		if(var10==null){
			return;
		}

		var10.setItemsToChest(par1World.rand);
		var10.reductionChestContent(par1World.rand);

		this.breakChest(par1World, par2, par3, par4, par5, par6);
		super.breakBlock(par1World, par2, par3, par4, par5, par6);


	}
	
    public void breakChest(World par1World, int par2, int par3, int par4, int par5, int par6)
    {
        TileEntityChest tileentitychest = (TileEntityChest)par1World.getBlockTileEntity(par2, par3, par4);

        if (tileentitychest != null)
        {
            for (int j1 = 0; j1 < tileentitychest.getSizeInventory(); ++j1)
            {
                ItemStack itemstack = tileentitychest.getStackInSlot(j1);

                if (itemstack != null)
                {
                    float f = this.random.nextFloat() * 0.8F + 0.1F;
                    float f1 = this.random.nextFloat() * 0.8F + 0.1F;
                    EntityItem entityitem;

                    for (float f2 = this.random.nextFloat() * 0.8F + 0.1F; itemstack.stackSize > 0; par1World.spawnEntityInWorld(entityitem))
                    {
                        int k1 = this.random.nextInt(21) + 10;

                        if (k1 > itemstack.stackSize)
                        {
                            k1 = itemstack.stackSize;
                        }

                        itemstack.stackSize -= k1;
                        entityitem = new EntityItem(par1World, (double)((float)par2 + f), (double)((float)par3 + f1), (double)((float)par4 + f2), new ItemStack(itemstack.itemID, k1, itemstack.getItemDamage()));
                        float f3 = 0.05F;
                        entityitem.motionX = (double)((float)this.random.nextGaussian() * f3);
                        entityitem.motionY = (double)((float)this.random.nextGaussian() * f3 + 0.2F);
                        entityitem.motionZ = (double)((float)this.random.nextGaussian() * f3);

                        if (itemstack.hasTagCompound())
                        {
                            entityitem.getEntityItem().setTagCompound((NBTTagCompound)itemstack.getTagCompound().copy());
                        }
                    }
                }
            }

            par1World.func_96440_m(par2, par3, par4, par5);
        }

       
    }
    

	@Override
	public int idDropped(int par1, Random par2Random, int par3)
	{
		return -1;
	}

	@Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    /**
     * The type of render function that is called for this block
     */
	@Override
    public int getRenderType()
    {
        return 22;
    }



}

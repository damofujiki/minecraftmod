package hinasch.mods.unlsaga.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockFallStone extends BlockFalling
{
    /** Do blocks fall instantly to where they stop or do they fall over time */
    public static boolean fallInstantly = false;

    
    public BlockFallStone()
    {
        super(Material.sand);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }

    public BlockFallStone(Material par3Material)
    {
        super(par3Material);
    }
    
    public int tickRate(World par1World)
    {
        return 2;
    }
    
//    @Override
//    public TileEntity createTileEntity(World world, int metadata)
//    {
//    	int blockid = Block.cobblestone.blockID;
//    	if(metadata==3){
//    		blockid = Block.dirt.blockID;
//    	}
//    	if(metadata==5){
//    		blockid = Block.netherrack.blockID;
//    	}
//        return new TileEntityShapeMemory(blockid,0,200);
//    }
//    

	
    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister par1IconRegister)
    {
        this.blockIcon = par1IconRegister.registerIcon("stonebrick");

    }
    
    @Override
    public IIcon getIcon(int par1, int par2)
    {
    	if(par2==5){
    		return Blocks.netherrack.getBlockTextureFromSide(0);
    	}
    	if(par2==3){
    		return Blocks.dirt.getBlockTextureFromSide(0);
    	}
        return Blocks.cobblestone.getBlockTextureFromSide(0);
    }

    
    @Override
    public void onBlockAdded(World par1World, int par2, int par3, int par4)
    {
        par1World.scheduleBlockUpdate(par2, par3, par4, this, this.tickRate());
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    @Override
    public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, Block par5)
    {
        par1World.scheduleBlockUpdate(par2, par3, par4, this, this.tickRate());
    }

    /**
     * Ticks the block if it's been scheduled
     */
    @Override
    public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        if (!par1World.isRemote)
        {
            this.tryToFall(par1World, par2, par3, par4);
        }
    }

    /**
     * If there is space to fall below will start this block falling
     */
    
    private void tryToFall(World par1World, int par2, int par3, int par4)
    {
    	int metadata = par1World.getBlockMetadata(par2, par3, par4);
        if (canFallBelow(par1World, par2, par3 - 1, par4) && par3 >= 0)
        {
            byte var8 = 32;

            if (!fallInstantly && par1World.checkChunksExist(par2 - var8, par3 - var8, par4 - var8, par2 + var8, par3 + var8, par4 + var8))
            {
                if (!par1World.isRemote)
                {
                    EntityFallingBlock var9 = new EntityFallingBlock(par1World, (double)((float)par2 + 0.5F), (double)((float)par3 + 0.5F), (double)((float)par4 + 0.5F), this, par1World.getBlockMetadata(par2, par3, par4));
                    this.onStartFalling(var9);
                    par1World.spawnEntityInWorld(var9);
                }
            }
            else
            {
                par1World.setBlockToAir(par2, par3, par4);

                while (canFallBelow(par1World, par2, par3 - 1, par4) && par3 > 0)
                {
                    --par3;
                }

                if (par3 > 0)
                {
                    par1World.setBlock(par2, par3, par4, this, metadata, 3);
                }
            }
        }
    }

    /**
     * Called when the falling block entity for this block is created
     */
    protected void onStartFalling(EntityFallingBlock par1EntityFallingSand) {}

    /**
     * How many world ticks before ticking
     */
    
    public int tickRate()
    {
        return 5;
    }

    /**
     * Checks to see if the sand can fall into the block below it
     */
    public static boolean canFallBelow(World par0World, int par1, int par2, int par3)
    {
        Block var4 = par0World.getBlock(par1, par2, par3);

        if (var4.isAir(par0World, par1, par2, par3))
        {
            return true;
        }
        else if (var4 == Blocks.fire)
        {
            return true;
        }
        else
        {
            Material var5 = var4.getMaterial();
            return var5 == Material.water ? true : var5 == Material.lava;
        }
    }

    /**
     * Called when the falling block entity for this block hits the ground and turns back into a block
     */
    public void onFinishFalling(World par1World, int par2, int par3, int par4, int par5) {
    	
    	if(par1World.getBlockMetadata(par2, par3, par4)==5){
    		par1World.setBlock(par2, par3, par4, Blocks.netherrack, 0, 2);
    		return;
    	}
    	if(par1World.getBlockMetadata(par2, par3, par4)==3){
    		par1World.setBlock(par2, par3, par4, Blocks.dirt, 0, 2);
    		return;
    	}
    	
    		par1World.setBlock(par2, par3, par4, Blocks.cobblestone, 0, 2);
    	
    }
    
    @Override
    public Item getItemDropped(int par1, Random par2Random, int par3)
    {
        return null;
    }

}

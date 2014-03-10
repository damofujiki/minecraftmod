package hinasch.mods.unlsaga.block;

import hinasch.lib.XYZPos;
import hinasch.mods.unlsaga.Unsaga;
import hinasch.mods.unlsaga.network.packet.PacketGuiOpen;
import hinasch.mods.unlsaga.network.packet.PacketSyncChest;
import hinasch.mods.unlsaga.tileentity.TileEntityChestUnsaga;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockChestUnsaga extends BlockContainer{

    private final Random random = new Random();
    
	public BlockChestUnsaga(int providepower) {
		super(Material.wood);
		
		// TODO Auto-generated constructor stub
	}


    
	@Override
    public void registerBlockIcons(IIconRegister par1IconRegister)
    {
        this.blockIcon = par1IconRegister.registerIcon("planks_oak");
    }
    
	/**
	 * Called upon block activation (right click on the block.)
	 */
	@Override
	public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
	{
		Unsaga.debug("アクチベートされてます");

		TileEntityChestUnsaga var10 = (TileEntityChestUnsaga)par1World.getTileEntity(par2, par3, par4);

		if(var10 == null){
			Unsaga.debug("タイルエンチチーがとれない");
			return true;
		}

		if(var10.hasItemSet){
			chestFunc(par1World, par2, par3, par4, par5EntityPlayer, par6, par7, par8, par9);
			return false;
		}

		if(!var10.getWorldObj().isRemote){
			Unsaga.debug("同期します");
			int chestlevel = 0;
			if(!var10.hasSetChestLevel()){
				var10.initChestLevel(par1World);
			}
			chestlevel = var10.getChestLevel();
			PacketSyncChest ps = new PacketSyncChest(new XYZPos(par2,par3,par4),chestlevel);
			Unsaga.packetPipeline.sendTo(ps, (EntityPlayerMP) par5EntityPlayer);
			Unsaga.debug("パケット送ります");
			PacketGuiOpen pg = new PacketGuiOpen(Unsaga.guiNumber.chest,new XYZPos(par2,par3,par4));
			Unsaga.packetPipeline.sendToServer(pg);
			//PacketDispatcher.sendPacketToPlayer(PacketHandler.getChestSyncPacket(chestlevel, par2, par3, par4,false,var10.trapOccured,var10.unlocked,var10.defused,var10.magicLock,var10.hasItemSet), (Player) par5EntityPlayer);
			//PacketDispatcher.sendPacketToServer(PacketHandler.getChestGuiPacket(par2,par3,par4));

		}
		if(var10.getWorldObj().isRemote){

		}


		
		
		
		
//		boolean chestopen = var10.touchChest(par5EntityPlayer);
//
//		if(chestopen){
//			var10.setItemsToChest(par1World.rand);
//			chestFunc(par1World, par2, par3, par4, par5EntityPlayer, par6, par7, par8, par9);
//		}
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
		Object object = (TileEntityChest)par1World.getTileEntity(par2, par3, par4);
		return (IInventory)object;
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{

		this.setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);

	}


	@Override
	public TileEntity createNewTileEntity(World par1World,int var2)
	{
		TileEntityChestUnsaga var1 = new TileEntityChestUnsaga();
		var1.init(par1World);
		return var1;
	}

	@Override
	public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4)
	{
		int l = 0;

		if (par1World.getBlock(par2 - 1, par3, par4) == this)
		{
			++l;
		}

		if (par1World.getBlock(par2 + 1, par3, par4) == this)
		{
			++l;
		}

		if (par1World.getBlock(par2, par3, par4 - 1) == this)
		{
			++l;
		}

		if (par1World.getBlock(par2, par3, par4 + 1) == this)
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
	public void breakBlock(World par1World, int par2, int par3, int par4, Block par5, int par6)
	{

		TileEntityChestUnsaga var10 = (TileEntityChestUnsaga)par1World.getTileEntity(par2, par3, par4);
		if(var10==null){
			return;
		}

		var10.setItemsToChest(par1World.rand);
		var10.reductionChestContent(par1World.rand);

		this.breakChest(par1World, par2, par3, par4, par5, par6);
		super.breakBlock(par1World, par2, par3, par4, par5, par6);


	}
	
    public void breakChest(World par1World, int par2, int par3, int par4, Block par5, int par6)
    {
        TileEntityChest tileentitychest = (TileEntityChest)par1World.getTileEntity(par2, par3, par4);

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
                        entityitem = new EntityItem(par1World, (double)((float)par2 + f), (double)((float)par3 + f1), (double)((float)par4 + f2), new ItemStack(itemstack.getItem(), k1, itemstack.getItemDamage()));
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

            par1World.func_147453_f(par2, par3, par4, par5);
        }

       
    }
    

	@Override
    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
    {
		return null;
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

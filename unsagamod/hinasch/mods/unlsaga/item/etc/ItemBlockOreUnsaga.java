package hinasch.mods.unlsaga.item.etc;

import hinasch.mods.unlsaga.block.BlockDataUnsaga;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockOreUnsaga extends ItemBlock{
	
	public ItemBlockOreUnsaga(Block parent) {
		super(parent);
		this.setHasSubtypes(true);

		
		// TODO Auto-generated constructor stub
	}

	@Override
    public String getUnlocalizedName(ItemStack par1ItemStack)
    {
        int var2 = par1ItemStack.getItemDamage();


        //RTBiomesCore.log(0,"RTBiomes."+super.getItemName() + "." + this.field_82804_b[var2]);
        
        return super.getUnlocalizedName() + "." + BlockDataUnsaga.unlocalizedNames.get(var2);
    }
	
	@Override
    public int getMetadata(int par1)
    {
        return par1;
    }
}

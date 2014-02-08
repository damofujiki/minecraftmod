package hinasch.mods.unlsaga.item;

import hinasch.mods.unlsaga.Unsaga;
import hinasch.mods.unlsaga.core.init.NoFuncItemList;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;

public class ItemIngotsUnsaga extends Item{

	private HashMap<Integer,Icon> iconMap = new HashMap();
	private String[] iconnames;
	private int[] rendercolors;

	
	public ItemIngotsUnsaga(int par1) {
		super(par1);
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
		
		

		// TODO Auto-generated constructor stub
	}
	
	@Override
    public Icon getIconFromDamage(int par1)
    {

		return iconMap.get(par1);
    }
    

    @Override
    public void registerIcons(IconRegister par1IconRegister)
    {
        //this.itemIcon = par1IconRegister.registerIcon(UnsagaCore.domain+"ingot");
        for(Iterator<Integer> ite=NoFuncItemList.getList().keySet().iterator();ite.hasNext();){
        	int meta = ite.next();
        	String icn = NoFuncItemList.getList().get(meta).iconname;
        	if(icn!=null){
        		if(icn.contains("*")){
        			this.iconMap.put(meta, par1IconRegister.registerIcon(icn.substring(1)));
        		}else{
        			this.iconMap.put(meta, par1IconRegister.registerIcon(Unsaga.domain+":"+icn));
        		}
        	}else{
        		this.iconMap.put(meta,par1IconRegister.registerIcon(Unsaga.domain+"ingot"));
        	}
        }
    }
    
	@Override
	public String getUnlocalizedName(ItemStack par1ItemStack)
	{
		int var2 = MathHelper.clamp_int(par1ItemStack.getItemDamage(), 0, NoFuncItemList.length()-1);
		return super.getUnlocalizedName() + NoFuncItemList.getList().get(var2).name;
	}
	
	@Override
    public int getColorFromItemStack(ItemStack par1ItemStack, int par2)
    {

		int meta = par1ItemStack.getItemDamage();
		if(NoFuncItemList.getList().get(meta).rendercolor==0){
			return 16777215;
		}
		return NoFuncItemList.getList().get(meta).rendercolor;



    }

	@Override
	public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List)
	{
		for (int var4 = 0; var4 < NoFuncItemList.length(); ++var4)
		{
				par3List.add(new ItemStack(par1, 1, var4));
			
		}
	}
	
}

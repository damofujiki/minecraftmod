package hinasch.mods.unlsaga.item.etc;

import hinasch.mods.unlsaga.Unsaga;
import hinasch.mods.unlsaga.core.init.NoFunctionItems;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;

public class ItemIngotsUnsaga extends Item{

	private HashMap<Integer,IIcon> iconMap = new HashMap();
	private String[] iconnames;
	private int[] rendercolors;

	
	public ItemIngotsUnsaga() {
		super();
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
		
		

		// TODO Auto-generated constructor stub
	}
	
	@Override
    public IIcon getIconFromDamage(int par1)
    {

		return iconMap.get(par1);
    }
    

    @Override
    public void registerIcons(IIconRegister par1IconRegister)
    {
        //this.itemIcon = par1IconRegister.registerIcon(UnsagaCore.domain+"ingot");
        for(Iterator<Integer> ite=NoFunctionItems.getList().keySet().iterator();ite.hasNext();){
        	int meta = ite.next();
        	String icn = NoFunctionItems.getList().get(meta).iconname;
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
		int var2 = MathHelper.clamp_int(par1ItemStack.getItemDamage(), 0, NoFunctionItems.length()-1);
		return "item.unsaga."+ NoFunctionItems.getList().get(var2).name;
	}
	
	@Override
    public int getColorFromItemStack(ItemStack par1ItemStack, int par2)
    {

		int meta = par1ItemStack.getItemDamage();
		if(NoFunctionItems.getList().get(meta).rendercolor==0){
			return 16777215;
		}
		return NoFunctionItems.getList().get(meta).rendercolor;



    }
	
//	//デバグ用
//	@Override
//    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer ep)
//    {
//		if(ep.inventory.getStackInSlot(0)!=null && ep.capabilities.isCreativeMode){
//			if(ep.inventory.getStackInSlot(0).getItem() instanceof IGainAbility){
//				HelperAbility ab = new HelperAbility(ep.inventory.getStackInSlot(0),ep);
//				ab.forgetSomeAbility(ep.getRNG());
//				ab.gainSomeAbility(itemRand);
//				
//			}
//		}
//		return par1ItemStack;
//    }
	
	@Override
	public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List)
	{
		for (int var4 = 0; var4 < NoFunctionItems.length(); ++var4)
		{
				par3List.add(new ItemStack(par1, 1, var4));
			
		}
	}
	
}

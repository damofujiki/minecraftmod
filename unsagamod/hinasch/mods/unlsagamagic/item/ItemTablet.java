package hinasch.mods.unlsagamagic.item;

import hinasch.lib.HSLibs;
import hinasch.lib.UtilNBT;
import hinasch.mods.unlsaga.Unsaga;
import hinasch.mods.unlsaga.misc.translation.Translation;
import hinasch.mods.unlsagamagic.UnsagaMagic;
import hinasch.mods.unlsagamagic.misc.spell.Spell;
import hinasch.mods.unlsagamagic.misc.spell.SpellRegistry;

import java.util.List;
import java.util.Random;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemTablet extends Item{

	protected static String KEYDF = "Decipherd";
	protected static String KEYID = "Magic.ID";
	
	//protected SpellRegistry sr = UnsagaMagic.spellRegistry;
	
	public static void activateMagicTablet(ItemStack is,Spell spell){
    	UtilNBT.setFreeTag(is, KEYID, spell.number);
    	UtilNBT.setFreeTag(is, KEYDF, false);
    	is.setItemDamage(is.getMaxDamage()-1);
  
    	return;
    }
	
	public static void progressDeciphering(EntityPlayer ep,ItemStack is,int progress){
		
		is.damageItem(-progress, ep);
		if(is.getItemDamage()<=0){
			is.setItemDamage(0);
			setDeciphered(is);
			ep.addChatMessage("finished deciphring the magic tablet.");
		}
		
	}

	public static ItemStack getRandomMagicTablet(Random rand){
    	int magicsize = SpellRegistry.getSize();
    	int drawRand = rand.nextInt(magicsize);
    	Spell spell = SpellRegistry.getSpell(drawRand);
    	ItemStack is = new ItemStack(UnsagaMagic.itemMagicTablet,1);
    	activateMagicTablet(is,spell);
    	return is;
    }
	
    public static int getSpellID(ItemStack is){
    	return UtilNBT.readFreeTag(is, KEYID);
    }
    
    public static Spell getSpell(ItemStack is){
    	return SpellRegistry.getSpell(getSpellID(is));
    }
    
    public static boolean isDeciphered(ItemStack is){
    	return UtilNBT.readFreeTagBool(is, KEYDF);
    }
    
    public static void setDeciphered(ItemStack is){
    	UtilNBT.setFreeTag(is, KEYDF,true);
    }
    
    public ItemTablet(int par1) {
		super(par1);
        this.maxStackSize = 1;
        this.setMaxDamage(50);
       
        this.setNoRepair();
		// TODO 自動生成されたコンストラクター・スタブ
	}
    
	@Override
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
		if(UtilNBT.hasKey(par1ItemStack, KEYID)){
			Spell spell = SpellRegistry.getSpell(ItemTablet.getSpellID(par1ItemStack));
			String spellname = spell.getName(HSLibs.getCurrentLang())+"<"+Translation.translate(spell.element.toString())+">";
			par3List.add(spellname);
			String deciphred = ItemTablet.isDeciphered(par1ItemStack)? Translation.translate("deciphred") : Translation.translate("not deciphred");
			par3List.add(deciphred);
		}
		
	}
	
    @Override
    public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List)
    {
    	for(Spell spell:SpellRegistry.spellMap.values()){
    		ItemStack is = new ItemStack(UnsagaMagic.itemMagicTablet,1);
    		ItemTablet.activateMagicTablet(is, spell);
    		par3List.add(is);
    	}

        
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public boolean hasEffect(ItemStack par1ItemStack)
    {
        return ItemTablet.isDeciphered(par1ItemStack);
    }
    
    @Override
    public void registerIcons(IconRegister par1IconRegister)
    {
        this.itemIcon = par1IconRegister.registerIcon(Unsaga.domain+":tablet");
    }
    
	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{
		if(par3EntityPlayer.isSneaking() && !par2World.isRemote){
			if(isDeciphered(par1ItemStack)){
				boolean flag = ItemSpellBook.writeSpellToBook(par3EntityPlayer, par1ItemStack, getSpell(par1ItemStack));
				if(flag && par2World.rand.nextInt(100)<20){
					par1ItemStack.stackSize --;
					return par1ItemStack;
				}
			}else{
				UnsagaMagic.worldElement.figureElements(par2World, par3EntityPlayer);
				par3EntityPlayer.addChatMessage(UnsagaMagic.worldElement.getWorldElementInfo());
				
			}
			return par1ItemStack;
		}
		
		if(Unsaga.debug.get()){
			par1ItemStack.setItemDamage(getMaxDamage()-1);
			return par1ItemStack;
		}

		return par1ItemStack;
	}
	
	
}

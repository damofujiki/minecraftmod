package hinasch.mods.unlsagamagic.item;

import hinasch.mods.unlsaga.Unsaga;
import hinasch.mods.unlsaga.misc.translation.Translation;
import hinasch.mods.unlsaga.misc.util.ChatMessageHandler;
import hinasch.mods.unlsagamagic.UnsagaMagic;
import hinasch.mods.unlsagamagic.misc.CreativeTabsUnsagaMagic;
import hinasch.mods.unlsagamagic.misc.spell.Spell;
import hinasch.mods.unlsagamagic.misc.spell.Spells;

import java.util.List;
import java.util.Random;

import com.hinasch.lib.HSLibs;
import com.hinasch.lib.UtilNBT;

import net.minecraft.client.renderer.texture.IIconRegister;
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
	
    public boolean isItemTool(ItemStack par1ItemStack)
    {
        return false;
    }
    
	public static void progressDeciphering(EntityPlayer ep,ItemStack is,int progress){
		if(!ep.worldObj.isRemote){
			is.damageItem(-progress, ep);
			if(is.getItemDamage()<=0){
				is.setItemDamage(0);
				setDeciphered(is);
				ChatMessageHandler.sendChatToPlayer(ep, Translation.localize("msg.spell.decipher.finished"));
				//ep.addChatMessage("finished deciphring the magic tablet.");
			}
		}

		
	}

	public static ItemStack getRandomMagicTablet(Random rand){
		List<Spell> spellList = Spells.getValidSpells();
    	int magicsize = spellList.size();
    	int drawRand = rand.nextInt(magicsize);
    	Spell spell = spellList.get(drawRand);
    	ItemStack is = new ItemStack(UnsagaMagic.itemMagicTablet,1);
    	activateMagicTablet(is,spell);
    	return is;
    }
	
	//For CreativeTab's Display
	public static ItemStack getDisplayMagicTablet(CreativeTabsUnsagaMagic tab){
		if(tab==null)return null;
		ItemStack is = new ItemStack(UnsagaMagic.itemMagicTablet,1);
		activateMagicTablet(is,Spells.fireArrow);
		is.setItemDamage(0);
		return is;
		
	}
	
    public static int getSpellID(ItemStack is){
    	return UtilNBT.readFreeTag(is, KEYID);
    }
    
    public static Spell getSpell(ItemStack is){
    	return Spells.getSpell(getSpellID(is));
    }
    
    public static boolean isDeciphered(ItemStack is){
    	return UtilNBT.readFreeTagBool(is, KEYDF);
    }
    
    public static void setDeciphered(ItemStack is){
    	UtilNBT.setFreeTag(is, KEYDF,true);
    	is.setItemDamage(0);
    }
    
    public ItemTablet() {
		super();
        this.maxStackSize = 1;
        this.setMaxDamage(50);
       
        this.setNoRepair();
		// TODO 自動生成されたコンストラクター・スタブ
	}
    
	@Override
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
		if(UtilNBT.hasKey(par1ItemStack, KEYID)){
			Spell spell = Spells.getSpell(ItemTablet.getSpellID(par1ItemStack));
			String spellname = spell.getName(HSLibs.getCurrentLang())+"<"+spell.element.getLocalized()+">";
			par3List.add(spellname);
			String deciphred = ItemTablet.isDeciphered(par1ItemStack)? "tablet.deciphred" : "tablet.notdeciphred";
			par3List.add(Translation.localize(deciphred));
		}
		
	}
	
    @Override
    public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List)
    {
    	for(Spell spell:Spells.spellMap.values()){
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
    public void registerIcons(IIconRegister par1IconRegister)
    {
        this.itemIcon = par1IconRegister.registerIcon(Unsaga.domain+":tablet");
    }
    
	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{
		if(par3EntityPlayer.isSneaking()){
			if(isDeciphered(par1ItemStack)){
				boolean flag = ItemSpellBook.writeSpellToBook(par3EntityPlayer, par1ItemStack, getSpell(par1ItemStack));
//				if(flag && par2World.rand.nextInt(100)<20){
//					par1ItemStack.stackSize --;
//					return par1ItemStack;
//				}
			}else{
				UnsagaMagic.worldElement.figureElements(par2World, par3EntityPlayer);
				ChatMessageHandler.sendChatToPlayer(par3EntityPlayer, UnsagaMagic.worldElement.getWorldElementInfo());
				//par3EntityPlayer.addChatMessage(UnsagaMagic.worldElement.getWorldElementInfo());
				
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

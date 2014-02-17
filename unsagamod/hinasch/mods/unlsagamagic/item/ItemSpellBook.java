package hinasch.mods.unlsagamagic.item;

import hinasch.lib.CSVText;
import hinasch.lib.HSLibs;
import hinasch.lib.UtilNBT;
import hinasch.mods.unlsaga.Unsaga;
import hinasch.mods.unlsaga.core.event.ExtendedPlayerData;
import hinasch.mods.unlsaga.misc.ability.Ability;
import hinasch.mods.unlsaga.misc.ability.AbilityRegistry;
import hinasch.mods.unlsaga.misc.ability.HelperAbility;
import hinasch.mods.unlsaga.misc.debuff.DebuffRegistry;
import hinasch.mods.unlsaga.misc.debuff.LivingDebuff;
import hinasch.mods.unlsaga.misc.debuff.LivingStateTarget;
import hinasch.mods.unlsaga.misc.translation.Translation;
import hinasch.mods.unlsagamagic.UnsagaMagic;
import hinasch.mods.unlsagamagic.misc.element.UnsagaElement.EnumElement;
import hinasch.mods.unlsagamagic.misc.spell.Spell;
import hinasch.mods.unlsagamagic.misc.spell.SpellRegistry;
import hinasch.mods.unlsagamagic.misc.spell.effect.InvokeSpell;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemSpellBook extends Item{


	protected static String AMPKEY = "Unsaga.spellAmp";
	protected static String COSTKEY = "Unsaga.spellCost";
	protected static String MAGICKEY = "Unsaga.spell";
	protected static String MIXKEY = "Unsaga.spellMixed";
	protected static String SPELLSKEY = "Unsaga.spellscontain";
	
	public static ItemStack getAbilityItem(EntityPlayer ep,Ability ab){
		if(ep.getExtendedProperties(ExtendedPlayerData.key)!=null){
			ExtendedPlayerData data = (ExtendedPlayerData)ep.getExtendedProperties(ExtendedPlayerData.key);
			for(ItemStack is:data.getItemStacks()){
				if(is!=null){
					HelperAbility helper = new HelperAbility(is, ep);
					if(helper.hasAbility(ab)){
						return is;
					}
				}

			}
		}
		return null;
	}
	
	public static void setBlendedSpells(ItemStack is,List<Spell> spells){
		if(spells==null)return;
		List<Integer> intlist = new ArrayList();
		for(Spell spell:spells){
			intlist.add(spell.number);
		}
		String str = CSVText.exchangeCollectionToCSV(intlist);
		UtilNBT.setFreeTag(is, SPELLSKEY, str);
	}
	
	public static List<Spell> getBlendedSpells(ItemStack is){
		Unsaga.debug(UtilNBT.readFreeStrTag(is, SPELLSKEY));
		List<Integer> intlist = CSVText.csvToIntList(UtilNBT.readFreeStrTag(is, SPELLSKEY));
		List<Spell> spelllist = new ArrayList();
		for(Integer inte:intlist){
			spelllist.add(SpellRegistry.getSpell(inte));
		}
		return spelllist;
	}
	
	public static void strapOffSpells(ItemStack is,World world,EntityPlayer ep){
		if(UtilNBT.hasKey(is, SPELLSKEY)){
			List<Spell> spellList = getBlendedSpells(is);
			for(Spell spell:spellList){
				ItemStack newstack = new ItemStack(UnsagaMagic.itemSpellBook,1);
				ItemSpellBook.writeSpell(newstack, spell);
				if(!world.isRemote){
					ep.dropPlayerItem(newstack);
				}
				
			}
			
			if(!world.isRemote){
				is.stackSize --;
			}
			
		}
	}
	
	
	public static void setAmp(ItemStack is,float par1){
		UtilNBT.setFreeTag(is, AMPKEY, (float)par1);
	}
	
	public static void setCost(ItemStack is,float par1){
		UtilNBT.setFreeTag(is, COSTKEY, par1);
	}
	
	public static void setBlended(ItemStack is,boolean par1){
		UtilNBT.setFreeTag(is, MIXKEY, par1);
	}
	
	public static float getAmp(ItemStack is){
		if(UtilNBT.hasKey(is, AMPKEY)){
			return MathHelper.clamp_float((float)UtilNBT.readFreeFloat(is, AMPKEY),0.1F,10.0F);
		}
		return 1.0F;
	}
	public static float getCost(ItemStack is){
		if(UtilNBT.hasKey(is, COSTKEY)){
			return MathHelper.clamp_float(UtilNBT.readFreeFloat(is, COSTKEY),0.1F,10.0F);
		}
		return 1.0F;
	}

	public static ItemStack getFistMagicItem(EntityPlayer ep,EnumElement element){
		switch(element){
		case FIRE:
			return getAbilityItem(ep,AbilityRegistry.fire);
		case WOOD:
			return getAbilityItem(ep,AbilityRegistry.wood);
		case WATER:
			return getAbilityItem(ep,AbilityRegistry.water);
		case EARTH:
			return getAbilityItem(ep,AbilityRegistry.earth);
		case METAL:	
			return getAbilityItem(ep,AbilityRegistry.metal);
		case FORBIDDEN:
			return getAbilityItem(ep,AbilityRegistry.forbidden);
		}
		return null;
	}
	
	public static Spell getSpell(ItemStack is){
		int spellnum = UtilNBT.readFreeTag(is, MAGICKEY);
		return SpellRegistry.getSpell(spellnum);
	}
	
	public static boolean hasMixed(ItemStack is){
		if(UtilNBT.hasKey(is, MIXKEY)){
			return UtilNBT.readFreeTagBool(is, MIXKEY);
		}
		return false;
	}
	
	public static void writeSpell(ItemStack is,Spell spell){
		UtilNBT.setFreeTag(is, MAGICKEY, spell.number);
	}
	
	public static boolean writeSpellToBook(EntityPlayer ep,ItemStack is,Spell spell){
		if(ep.inventory.hasItem(Item.book.itemID)){
			if(!ep.worldObj.isRemote){
				ep.inventory.consumeInventoryItem(Item.book.itemID);
				ItemStack newbook = new ItemStack(UnsagaMagic.itemSpellBook,1);
				writeSpell(newbook,spell);
				ep.addChatMessage(Translation.translate("wrote to spell book."));
				ep.dropPlayerItem(newbook);
				

				return true;
				
			}
			
			
		}
		return false;
	}
	
	public ItemSpellBook(int par1) {
		super(par1);
		this.maxStackSize = 1;
		this.setMaxDamage(100);


		this.setNoRepair();

	}
	
	
	@Override
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
		if(UtilNBT.hasKey(par1ItemStack, MAGICKEY)){
			Spell spell = ItemSpellBook.getSpell(par1ItemStack);
			String spellname = spell.getName(HSLibs.getCurrentLang())+"<"+Translation.translate(spell.element.toString())+">";
			par3List.add(spellname);
		}
		if(ItemSpellBook.hasMixed(par1ItemStack)){
			String str = "Amplifier:"+ItemSpellBook.getAmp(par1ItemStack)+"/Cost:"+ItemSpellBook.getCost(par1ItemStack);
			par3List.add(str);
		}
		
	}
	
	@Override
    public Icon getIconIndex(ItemStack par1ItemStack)
    {
        return this.getIconFromDamage(par1ItemStack.getItemDamage());
    }
	
	@Override
	public EnumAction getItemUseAction(ItemStack par1ItemStack) {
		return EnumAction.bow;
	}
	
	@Override
	public int getMaxItemUseDuration(ItemStack par1ItemStack) {
		return 72000;
	}
	
	@Override
    public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List)
    {
    	for(Spell spell:SpellRegistry.spellMap.values()){
    		ItemStack is = new ItemStack(this,1);
    		ItemSpellBook.writeSpell(is, spell);
    		par3List.add(is);
    	}

        
    }
	
	@SideOnly(Side.CLIENT)
	@Override
	public boolean hasEffect(ItemStack par1ItemStack) {
		return ItemSpellBook.hasMixed(par1ItemStack);
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World,
			EntityPlayer par3EntityPlayer) {
		par3EntityPlayer.playSound("mob.wither.shoot", 0.5F,
				1.8F / (par2World.rand.nextFloat() * 0.4F + 1.2F));
		
		par3EntityPlayer.setItemInUse(par1ItemStack,
				this.getMaxItemUseDuration(par1ItemStack));

		return par1ItemStack;
	}
	
	@Override
	public void onPlayerStoppedUsing(ItemStack is, World par2World,
			EntityPlayer par3EntityPlayer, int par4) {
		int var6 = this.getMaxItemUseDuration(is) - par4;
		float var7 = (float) var6 / 20.0F;
		var7 = (var7 * var7 + var7 * 2.0F) / 3.0F;

		if ((double) var7 < 0.1D) {
			return;
		}
		if (var7 > 1.0F) {
			Spell spell = ItemSpellBook.getSpell(is);
			ItemStack magicItem = ItemSpellBook.getFistMagicItem(par3EntityPlayer, spell.element);
			boolean canInvoke = false;
			if(par3EntityPlayer.capabilities.isCreativeMode)canInvoke = true;
			if(!par3EntityPlayer.capabilities.isCreativeMode && magicItem!=null)canInvoke = true;
			
			if(par3EntityPlayer.isSneaking()){
				if(LivingDebuff.getLivingDebuff(par3EntityPlayer, DebuffRegistry.spellTarget).isPresent()){
					LivingStateTarget debuff = (LivingStateTarget) LivingDebuff.getLivingDebuff(par3EntityPlayer, DebuffRegistry.spellTarget).get();
					int targetid = debuff.targetid;
					if(targetid>0){
						EntityLivingBase target = (EntityLivingBase) par2World.getEntityByID(targetid);
						if(target!=null){
							Unsaga.debug(target.getEntityName());
							
							if(!par2World.isRemote  && canInvoke){
								InvokeSpell invokeSpell = new InvokeSpell(spell, par2World, par3EntityPlayer, is,target);
								invokeSpell.setMagicItem(magicItem);
								invokeSpell.run();
							}

						}
					}
					
				}
				
			}else{
				if(!par2World.isRemote  && canInvoke){
					InvokeSpell invokeSpell = new InvokeSpell(spell, par2World, par3EntityPlayer, is);
					invokeSpell.setMagicItem(magicItem);
					invokeSpell.run();
				}

			}
			
		}
	}
	
    @Override
	public void registerIcons(IconRegister par1IconRegister) {
		this.itemIcon = par1IconRegister.registerIcon(Unsaga.domain+":book_spell");

	}
}

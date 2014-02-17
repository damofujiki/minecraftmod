package hinasch.mods.unlsaga.item.etc;

import hinasch.mods.unlsaga.Unsaga;
import hinasch.mods.unlsaga.core.init.UnsagaItems;
import hinasch.mods.unlsaga.core.init.UnsagaMaterial;
import hinasch.mods.unlsaga.misc.ability.IGainAbility;
import hinasch.mods.unlsaga.misc.util.EnumUnsagaWeapon;
import hinasch.mods.unlsaga.misc.util.HelperUnsagaWeapon;
import hinasch.mods.unlsaga.misc.util.IUnsagaMaterial;
import hinasch.mods.unlsaga.network.PacketHandler;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.PacketDispatcher;

public class ItemAccessory extends Item implements IGainAbility,IUnsagaMaterial{

	protected UnsagaMaterial material;
	protected String defaultIcon;
	protected HelperUnsagaWeapon helper;
	
	public ItemAccessory(int par1,UnsagaMaterial par2) {
		super(par1);
		this.material = par2;
        this.maxStackSize = 1;
		this.setMaxDamage(par2.getToolMaterial().getMaxUses());
		this.defaultIcon = "ring";
		this.helper = new HelperUnsagaWeapon(this.material,this.itemIcon, EnumUnsagaWeapon.ACCESSORY);
		// TODO 自動生成されたコンストラクター・スタブ
		UnsagaItems.putItemMap(this.itemID, EnumUnsagaWeapon.ACCESSORY.toString()+"."+material.name);
	}

	@Override
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
		helper.addInformation(par1ItemStack, par2EntityPlayer, par3List, par4);
	}


	
    @Override
    public int getColorFromItemStack(ItemStack par1ItemStack, int par2)
    {
    	return helper.getColorFromItemStack(par1ItemStack, par2);
    }
    
	@Override
	public void registerIcons(IconRegister par1IconRegister)
	{
		if(this.material.getSpecialIcon(EnumUnsagaWeapon.ACCESSORY).isPresent()){
			this.itemIcon = par1IconRegister.registerIcon(Unsaga.domain+":"+this.material.getSpecialIcon(EnumUnsagaWeapon.ACCESSORY).get());
			return;
		}
		this.itemIcon = par1IconRegister.registerIcon(Unsaga.domain+":"+this.defaultIcon);
	}
	
    @Override
    public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List)
    {
    	helper.getSubItems(par1, par2CreativeTabs, par3List);
        
    }

	@Override
	public int getMaxAbility() {
		// TODO 自動生成されたメソッド・スタブ
		return 2;
	}

	@Override
	public EnumUnsagaWeapon getCategory() {
		// TODO 自動生成されたメソッド・スタブ
		return EnumUnsagaWeapon.ACCESSORY;
	}
	
	@Override
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {

        if(par3EntityPlayer.isSneaking()){
	    	  EntityClientPlayerMP clientPlayer = (EntityClientPlayerMP)Minecraft.getMinecraft().thePlayer;

	    	  if(Minecraft.getMinecraft().currentScreen !=null)return par1ItemStack;
	    	  PacketDispatcher.sendPacketToServer(PacketHandler.getEquipGuiPacket(clientPlayer));
        }

        return par1ItemStack;
    }
}

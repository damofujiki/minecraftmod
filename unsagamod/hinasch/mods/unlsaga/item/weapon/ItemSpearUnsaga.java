package hinasch.mods.unlsaga.item.weapon;

import hinasch.mods.unlsaga.Unsaga;
import hinasch.mods.unlsaga.core.init.UnsagaItems;
import hinasch.mods.unlsaga.core.init.UnsagaMaterial;
import hinasch.mods.unlsaga.misc.util.EnumUnsagaWeapon;
import hinasch.mods.unlsaga.misc.util.HelperUnsagaWeapon;
import hinasch.mods.unlsaga.misc.util.IExtendedReach;
import hinasch.mods.unlsaga.misc.util.IUnsagaWeapon;
import hinasch.mods.unlsaga.misc.util.UtilItem;

import java.util.HashMap;
import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class ItemSpearUnsaga extends ItemSword implements IUnsagaWeapon,IExtendedReach {

	public final UnsagaMaterial unsMaterial;
	protected final HashMap<String,Icon> iconMap = new HashMap();
	protected final HelperUnsagaWeapon helper;
	protected Icon[] icons;
	
	public ItemSpearUnsaga(int par1, EnumToolMaterial par2EnumToolMaterial,UnsagaMaterial material) {
		super(par1, par2EnumToolMaterial);
		this.unsMaterial = material;
		this.icons = new Icon[2];
		this.helper = new HelperUnsagaWeapon(this.unsMaterial,this.itemIcon,this.iconMap);
		Unsaga.proxy.registerSpearRenderer(this.itemID);
		UnsagaItems.putItemMap(this.itemID,EnumUnsagaWeapon.SPEAR.toString()+"."+material.name);
		//UnsagaItems.registerValidTool(EnumUnsagaWeapon.SPEAR, mat, this.itemID);
		// TODO 自動生成されたコンストラクター・スタブ
	}
	
	@Override
    public boolean requiresMultipleRenderPasses()
    {
        return true;
    }
    
	@Override
    public Icon getIconFromDamageForRenderPass(int par1, int par2)
    {
		if(par2==0){
			return this.icons[0];
		}
        return this.icons[1];
    }
	
	@Override
    public int getColorFromItemStack(ItemStack par1ItemStack, int par2)
    {
		return helper.getColorFromItemStack(par1ItemStack, par2);
    }
	@Override
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
		helper.addInformation(par1ItemStack, par2EntityPlayer, par3List, par4);
	}
	
    @Override
    public void registerIcons(IconRegister par1IconRegister)
    {
    	this.itemIcon = par1IconRegister.registerIcon(Unsaga.domain+":"+"spear");
    	this.icons[0] = par1IconRegister.registerIcon(Unsaga.domain+":"+"spear_1");
    	this.icons[1] = par1IconRegister.registerIcon(Unsaga.domain+":"+"spear_2");
    }

    @Override
    public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List)
    {
    	helper.getSubItems(par1, par2CreativeTabs, par3List);
        
    }
    
    @Override
    public EnumAction getItemUseAction(ItemStack par1ItemStack)
    {
    	
        return EnumAction.none;
    }
    
	@Override
	public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5) {
		if(par3Entity!=null){
			if(par1ItemStack!=null){
				if(par3Entity instanceof EntityPlayer){
					EntityPlayer ep = (EntityPlayer)par3Entity;
					if(ep.swingProgressInt==1){
						ItemStack is = ep.getCurrentEquippedItem();
						if((is!=null) && (is.getItem() instanceof IExtendedReach)){
							float reach = ((IExtendedReach) is.getItem()).getReach();
							MovingObjectPosition mop = UtilItem.getMouseOver();
							if(mop!=null){
								if(mop.entityHit!=null){
									//System.out.println(mop);
									float dis = ep.getDistanceToEntity(mop.entityHit);
									if(dis<reach){

										if(mop.entityHit.hurtResistantTime==0 ){
											AxisAlignedBB ab = mop.entityHit.boundingBox;
											List<Entity> list = par2World.getEntitiesWithinAABB(Entity.class, ab);

											if(!list.isEmpty()){

												Entity hurtEnt = list.get(0);
												//ep.attackTargetEntityWithCurrentItem(hurtEnt);
												if(hurtEnt!=ep){
													
													UtilItem.playerAttackEntityWithItem(ep, hurtEnt, 0, 0.8F);
												}
											}

										}
									}
								}
							}
						}

						//UtilSkill.setFreeStateNBT(par1ItemStack, "attack", 0);
					}
				}

			}
		}
	}

	@Override
	public float getReach() {
		// TODO Auto-generated method stub
		return 8.0F;
	}

	@Override
    public boolean hitEntity(ItemStack par1ItemStack, EntityLivingBase par2EntityLivingBase, EntityLivingBase par3EntityLivingBase)
    {
        par1ItemStack.damageItem(2, par3EntityLivingBase);
        return true;
    }
}

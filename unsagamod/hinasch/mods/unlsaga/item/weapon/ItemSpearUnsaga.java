package hinasch.mods.unlsaga.item.weapon;


import hinasch.lib.UtilNBT;
import hinasch.lib.XYZPos;
import hinasch.mods.unlsaga.Unsaga;
import hinasch.mods.unlsaga.core.init.UnsagaItems;
import hinasch.mods.unlsaga.core.init.UnsagaMaterial;
import hinasch.mods.unlsaga.misc.ability.AbilityRegistry;
import hinasch.mods.unlsaga.misc.ability.HelperAbility;
import hinasch.mods.unlsaga.misc.ability.IGainAbility;
import hinasch.mods.unlsaga.misc.ability.skill.effect.SkillEffectHelper;
import hinasch.mods.unlsaga.misc.util.EnumUnsagaWeapon;
import hinasch.mods.unlsaga.misc.util.HelperUnsagaWeapon;
import hinasch.mods.unlsaga.misc.util.IExtendedReach;
import hinasch.mods.unlsaga.misc.util.IUnsagaMaterial;
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

public class ItemSpearUnsaga extends ItemSword implements IUnsagaMaterial,IExtendedReach,IGainAbility {

	public final UnsagaMaterial unsMaterial;
	protected final HashMap<String,Icon> iconMap = new HashMap();
	protected final HelperUnsagaWeapon helper;
	protected Icon[] icons;
	
	
	protected final static String KEYisAiming = "unsaga.isAiming";
	protected final int SETAIMING = 0x01;
	protected final int NEUTRAL = 0x00;
	
	public ItemSpearUnsaga(int par1, EnumToolMaterial par2EnumToolMaterial,UnsagaMaterial material) {
		super(par1, par2EnumToolMaterial);
		this.unsMaterial = material;
		this.icons = new Icon[2];
		this.helper = new HelperUnsagaWeapon(this.unsMaterial,this.itemIcon,EnumUnsagaWeapon.SPEAR);
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
	public int getMaxItemUseDuration(ItemStack par1ItemStack)
	{
		if(HelperAbility.hasAbilityFromItemStack(AbilityRegistry.acupuncture, par1ItemStack)){
			return 160000;
		}
		return 72000;
	}
	
	@Override
	public EnumAction getItemUseAction(ItemStack par1ItemStack)
	{
		if(this.isAiming(par1ItemStack)){
			return EnumAction.bow;
		}

		return EnumAction.none;

	}
	
	public boolean isAiming(ItemStack par1ItemStack){
		if(UtilNBT.hasKey(par1ItemStack, KEYisAiming)){
			return UtilNBT.readFreeTagBool(par1ItemStack, KEYisAiming);
		}
		return false;
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer, int par4)
	{
		int ac = 20;
		int j = this.getMaxItemUseDuration(par1ItemStack) - par4;
		if(HelperAbility.hasAbilityFromItemStack(AbilityRegistry.aiming, par1ItemStack)){
			SkillEffectHelper helper = new SkillEffectHelper(par2World, par3EntityPlayer,AbilityRegistry.aiming , par1ItemStack);
			helper.setCharge(j);
			helper.doSkill();
		}
		if(HelperAbility.hasAbilityFromItemStack(AbilityRegistry.acupuncture, par1ItemStack)){
			SkillEffectHelper helper = new SkillEffectHelper(par2World, par3EntityPlayer,AbilityRegistry.acupuncture , par1ItemStack);
			helper.setCharge(j);
			helper.doSkill();
		}
	}
	
	
	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{
		super.onItemRightClick(par1ItemStack, par2World, par3EntityPlayer);

		if(HelperAbility.hasAbilityFromItemStack(AbilityRegistry.swing, par1ItemStack) && par3EntityPlayer.isSneaking()){
			SkillEffectHelper helper = new SkillEffectHelper(par2World, par3EntityPlayer, AbilityRegistry.swing, par1ItemStack);
			helper.doSkill();
		}
		if (HelperAbility.hasAbilityFromItemStack(AbilityRegistry.aiming, par1ItemStack) && par3EntityPlayer.isSneaking())
		{
			UtilNBT.setFreeTag(par1ItemStack, KEYisAiming, true);

		}
		if (HelperAbility.hasAbilityFromItemStack(AbilityRegistry.acupuncture, par1ItemStack) && par3EntityPlayer.isSneaking())
		{
			UtilNBT.setFreeTag(par1ItemStack, KEYisAiming, true);

		}

		return par1ItemStack;
	}
	
	@Override
	public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
	{
		if(HelperAbility.hasAbilityFromItemStack(AbilityRegistry.grassHopper, par1ItemStack) && par2EntityPlayer.isSneaking()){
			SkillEffectHelper helper = new SkillEffectHelper(par3World,par2EntityPlayer,AbilityRegistry.grassHopper,par1ItemStack);

			helper.setUsePoint(new XYZPos(par4,par5,par6));
			helper.doSkill();

		}
		return false;
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

							this.doSpearAttack(is, ep, par2World);
						}
						if(is!=null){
							this.setNeutral(par1ItemStack);
						}

						//UtilSkill.setFreeStateNBT(par1ItemStack, "attack", 0);
					}
				}

			}
		}
	}

	protected void doSpearAttack(ItemStack is,EntityPlayer ep,World par2World){
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
	
	@Override
	public EnumUnsagaWeapon getCategory() {
		// TODO 自動生成されたメソッド・スタブ
		return EnumUnsagaWeapon.SPEAR;
	}
	
	public static void setNeutral(ItemStack is){
		UtilNBT.setFreeTag(is, KEYisAiming, false);
	}

	@Override
	public int getMaxAbility() {
		// TODO 自動生成されたメソッド・スタブ
		return 1;
	}
}

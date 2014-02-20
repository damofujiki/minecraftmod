package hinasch.mods.unlsaga.item.weapon;


import hinasch.lib.UtilNBT;
import hinasch.mods.unlsaga.Unsaga;
import hinasch.mods.unlsaga.core.init.UnsagaItems;
import hinasch.mods.unlsaga.core.init.UnsagaMaterial;
import hinasch.mods.unlsaga.misc.ability.AbilityRegistry;
import hinasch.mods.unlsaga.misc.ability.HelperAbility;
import hinasch.mods.unlsaga.misc.ability.IGainAbility;
import hinasch.mods.unlsaga.misc.ability.skill.effect.SkillEffectHelper;
import hinasch.mods.unlsaga.misc.debuff.DebuffRegistry;
import hinasch.mods.unlsaga.misc.debuff.LivingDebuff;
import hinasch.mods.unlsaga.misc.debuff.LivingState;
import hinasch.mods.unlsaga.misc.util.EnumUnsagaWeapon;
import hinasch.mods.unlsaga.misc.util.HelperUnsagaWeapon;
import hinasch.mods.unlsaga.misc.util.IUnsagaMaterial;

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
import net.minecraft.util.DamageSource;
import net.minecraft.util.Icon;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class ItemSwordUnsaga extends ItemSword implements IUnsagaMaterial,IGainAbility{

	public final UnsagaMaterial unsMaterial;
	protected final HashMap<String,Icon> iconMap = new HashMap();
	protected final HelperUnsagaWeapon helper;
	protected final Icon[] icons;
	public final float weapondamage;
	
	public static String GUSTKEY = "unsaga.gust";
	
	public ItemSwordUnsaga(int par1, EnumToolMaterial par2EnumToolMaterial,UnsagaMaterial mat) {
		super(par1, par2EnumToolMaterial);
		this.weapondamage = 4.0F + par2EnumToolMaterial.getDamageVsEntity();
		this.icons = new Icon[2];
		this.unsMaterial = mat;
		this.helper = new HelperUnsagaWeapon(this.unsMaterial,this.itemIcon,EnumUnsagaWeapon.SWORD);
		Unsaga.proxy.registerSpecialRenderer(this.itemID);
		UnsagaItems.putItemMap(this.itemID,EnumUnsagaWeapon.SWORD.toString()+"."+mat.name);
		//UnsagaItems.registerValidTool(EnumUnsagaWeapon.SWORD, mat, this.itemID);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack is, World world, EntityPlayer ep, int par4)
	{
		int j = this.getMaxItemUseDuration(is) - par4;
		if(HelperAbility.hasAbilityFromItemStack(AbilityRegistry.gust, is) && this.isGust(is)){
			this.setGust(is, false);
			if(j>15){
				SkillEffectHelper helper = new SkillEffectHelper(world,ep,AbilityRegistry.gust,is);
				helper.doSkill();
			}
		}
		if(HelperAbility.hasAbilityFromItemStack(AbilityRegistry.smash, is)){
			List<EntityLivingBase> entlist = world.getEntitiesWithinAABB(EntityLivingBase.class, ep.boundingBox.expand(2.0D, 1.0D, 2.0D));
			ep.swingItem();
			if(entlist!=null && !entlist.isEmpty()){
				for(EntityLivingBase damageentity:entlist){
					//Entity damageentity = i.next();
					if(damageentity!=ep){
						float damage = (float)this.weapondamage + (float)AbilityRegistry.smash.hurtHp;
						damageentity.attackEntityFrom(DamageSource.causePlayerDamage(ep), (int)damage);
						Unsaga.lpHandler.tryHurtLP(damageentity, AbilityRegistry.smash.hurtLp);
						is.damageItem(AbilityRegistry.smash.damageWeapon, ep);
					}
				}
			}
		}
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
    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity)
    {
		if(HelperAbility.hasAbilityFromItemStack(AbilityRegistry.vandalize, stack) && player.isSneaking()){
			SkillEffectHelper helper = new SkillEffectHelper(player.worldObj,player,AbilityRegistry.vandalize,stack);
			if(entity instanceof EntityLivingBase){
				helper.setTarget((EntityLivingBase) entity);
				helper.setCoolingTime(8);
				helper.doSkill();
			}
		}
		if(HelperAbility.hasAbilityFromItemStack(AbilityRegistry.kaleidoscope,stack) && player.isSneaking()){
			SkillEffectHelper helper = new SkillEffectHelper(player.worldObj,player,AbilityRegistry.kaleidoscope,stack);
			if(entity instanceof EntityLivingBase){
				helper.setTarget((EntityLivingBase) entity);
				helper.doSkill();
			}


		}
        return false;
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
    	//this.itemIcon = par1IconRegister.registerIcon(Unsaga.domain+":"+"sword_"+this.unsMaterial.iconname);

    	this.itemIcon = par1IconRegister.registerIcon(Unsaga.domain+":"+"sword");
    	this.icons[0] = par1IconRegister.registerIcon(Unsaga.domain+":"+"sword_1");
    	this.icons[1] = par1IconRegister.registerIcon(Unsaga.domain+":"+"sword_2");
    	//this.itemIcon = HelperUnsagaWeapon.registerIcons(par1IconRegister, unsMaterial, "sword");
//    	if(this.unsMaterial.hasSubMaterials()){
//    		for(Iterator<UnsagaMaterial> ite=unsMaterial.getSubMaterials().values().iterator();ite.hasNext();){
//    			
//    			UnsagaMaterial childMat = ite.next();
//    			this.iconMap.put(childMat.name, par1IconRegister.registerIcon(Unsaga.domain+":"+"sword_"+childMat.iconname));
//    		}
//    	}else{
//    		this.itemIcon = par1IconRegister.registerIcon(Unsaga.domain+":"+"sword_"+this.unsMaterial.iconname);
//    	}
//    	

    	
    }
    
    
    @Override
    public EnumAction getItemUseAction(ItemStack par1ItemStack)
    {
    	if(HelperAbility.hasAbilityFromItemStack(AbilityRegistry.smash, par1ItemStack)){
    		return EnumAction.bow;
    	}
    	if(this.isGust(par1ItemStack)){
    		return EnumAction.bow;
    	}
    	if(HelperUnsagaWeapon.getCurrentWeight(par1ItemStack)>5){
    		return EnumAction.none;
    	}
        return EnumAction.block;
    }
    
    public UnsagaMaterial getMaterial(ItemStack is){
    	return HelperUnsagaWeapon.getMaterial(is);
    }
    
    @Override
    public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List)
    {
    	helper.getSubItems(par1, par2CreativeTabs, par3List);
        
    }
    
    
	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{
		super.onItemRightClick(par1ItemStack, par2World, par3EntityPlayer);
		if(HelperAbility.hasAbilityFromItemStack(AbilityRegistry.roundabout, par1ItemStack)){
			if(LivingDebuff.hasDebuff(par3EntityPlayer, DebuffRegistry.roundabout)){
				
				par3EntityPlayer.playSound("mob.wither.shoot", 0.5F, 1.8F / (par3EntityPlayer.getRNG().nextFloat() * 0.4F + 1.2F));

				Vec3 lookvec = par3EntityPlayer.getLookVec().normalize();
				par3EntityPlayer.addVelocity(lookvec.xCoord*0.7, 0.1, lookvec.zCoord*0.7);
				par1ItemStack.damageItem(1, par3EntityPlayer);
				return par1ItemStack;
			}else{
				if(!par3EntityPlayer.onGround){
					par3EntityPlayer.playSound("mob.wither.shoot", 0.5F, 1.8F / (par3EntityPlayer.getRNG().nextFloat() * 0.4F + 1.2F));

					LivingDebuff.addLivingDebuff(par3EntityPlayer, new LivingState(DebuffRegistry.antiFallDamage,10,true));
					LivingDebuff.addLivingDebuff(par3EntityPlayer, new LivingState(DebuffRegistry.roundabout,5,true));
					par3EntityPlayer.motionY += 0.7;
					par1ItemStack.damageItem(AbilityRegistry.roundabout.damageWeapon, par3EntityPlayer);
				}
			}

		}
		if(HelperAbility.hasAbilityFromItemStack(AbilityRegistry.chargeBlade,par1ItemStack) && par3EntityPlayer.isSprinting()){
			if(!LivingDebuff.hasDebuff(par3EntityPlayer,DebuffRegistry.rushBlade)){
				SkillEffectHelper helper = new SkillEffectHelper(par2World,par3EntityPlayer,AbilityRegistry.chargeBlade,par1ItemStack);
				par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
				helper.setCoolingTime(4);
				helper.doSkill();
				
			}

		}
		if(HelperAbility.hasAbilityFromItemStack(AbilityRegistry.gust,par1ItemStack) && par3EntityPlayer.isSneaking()){
			
			this.setGust(par1ItemStack, true);
			par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
		}
		if(HelperAbility.hasAbilityFromItemStack(AbilityRegistry.smash,par1ItemStack)){
			par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
		}
		return par1ItemStack;
	}
    
	protected void setGust(ItemStack is,boolean par1){
		UtilNBT.setFreeTag(is, GUSTKEY, par1);
	}
	
	protected boolean isGust(ItemStack is){
		return (UtilNBT.hasKey(is, GUSTKEY)? UtilNBT.readFreeTagBool(is, GUSTKEY) : false);
	}
//    @Override
//    public Icon getIconIndex(ItemStack par1ItemStack)
//    {
//
//        return helper.getIconIndex(par1ItemStack, this.getIconFromDamage(par1ItemStack.getItemDamage()));
//    }
    
//	@Override
//	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
//	{
//		if(par3EntityPlayer.isAirBorne){
//			par3EntityPlayer.playSound("mob.wither.shoot", 0.5F, 1.8F / (par3EntityPlayer.getRNG().nextFloat() * 0.4F + 1.2F));
//
//			state.isRoundabout = true;
//			state.registTick = 5;
//			state.causeFallDamage = false;
//			par3EntityPlayer.motionY += 0.7;
//
//			par1ItemStack.damageItem(2, par3EntityPlayer);
//		}
//	}
	@Override
	public EnumUnsagaWeapon getCategory() {
		// TODO 自動生成されたメソッド・スタブ
		return EnumUnsagaWeapon.SWORD;
	}

	@Override
    public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5) {
    	if(par3Entity instanceof EntityPlayer){
    		EntityPlayer ep = (EntityPlayer)par3Entity;
    		if(this.isGust(par1ItemStack) && !ep.isSneaking()){
    			this.setGust(par1ItemStack, false);
    		}
    	}
    }



	@Override
	public int getMaxAbility() {
		// TODO 自動生成されたメソッド・スタブ
		return 1;
	}
}

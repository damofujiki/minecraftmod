package hinasch.mods.unlsaga.item.weapon;

import hinasch.lib.XYZPos;
import hinasch.mods.unlsaga.Unsaga;
import hinasch.mods.unlsaga.core.init.UnsagaItems;
import hinasch.mods.unlsaga.core.init.UnsagaMaterial;
import hinasch.mods.unlsaga.entity.EntityFlyingAxe;
import hinasch.mods.unlsaga.misc.ability.AbilityRegistry;
import hinasch.mods.unlsaga.misc.ability.HelperAbility;
import hinasch.mods.unlsaga.misc.ability.IGainAbility;
import hinasch.mods.unlsaga.misc.ability.skill.HelperSkill;
import hinasch.mods.unlsaga.misc.ability.skill.effect.SkillAxe;
import hinasch.mods.unlsaga.misc.debuff.DebuffRegistry;
import hinasch.mods.unlsaga.misc.debuff.LivingDebuff;
import hinasch.mods.unlsaga.misc.debuff.LivingState;
import hinasch.mods.unlsaga.misc.debuff.LivingStateFlyingAxe;
import hinasch.mods.unlsaga.misc.debuff.LivingStateTarget;
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
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class ItemAxeUnsaga extends ItemAxe implements IUnsagaMaterial,IGainAbility{

	public final UnsagaMaterial unsMaterial;
	protected final HashMap<String,Icon> iconMap = new HashMap();
	protected final HelperUnsagaWeapon helper;
	protected Icon[] icons;
	public final float weapondamage;

	public ItemAxeUnsaga(int par1, EnumToolMaterial par2EnumToolMaterial,UnsagaMaterial mat) {
		super(par1, par2EnumToolMaterial);
		this.unsMaterial = mat;
		this.icons = new Icon[2];
		this.weapondamage = par2EnumToolMaterial.getDamageVsEntity();
		this.helper = new HelperUnsagaWeapon(this.unsMaterial,this.itemIcon,EnumUnsagaWeapon.AXE);
		Unsaga.proxy.registerSpecialRenderer(this.itemID);
		UnsagaItems.putItemMap(this.itemID,EnumUnsagaWeapon.AXE.toString()+"."+mat.name);
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
	public EnumAction getItemUseAction(ItemStack par1ItemStack)
	{

		if(HelperAbility.hasAbilityFromItemStack(AbilityRegistry.tomahawk, par1ItemStack)){

			return EnumAction.bow;
		}
		return EnumAction.none;
	}


	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity)
	{
		HelperSkill helper = new HelperSkill(stack,player);
		if(helper.hasAbility(AbilityRegistry.fujiView) && player.isSneaking() && !LivingDebuff.hasDebuff(player, DebuffRegistry.cooling)){
			SkillAxe fujiView = new SkillAxe();
			if(!player.worldObj.isRemote){
				fujiView.doFujiView(player, entity, player.worldObj);
				stack.damageItem(AbilityRegistry.fujiView.damageWeapon, player);
			}

			LivingDebuff.addDebuff(player, DebuffRegistry.cooling, 10);

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
		this.itemIcon = par1IconRegister.registerIcon(Unsaga.domain+":"+"axe");
		this.icons[0] = par1IconRegister.registerIcon(Unsaga.domain+":"+"axe_1");
		this.icons[1] = par1IconRegister.registerIcon(Unsaga.domain+":"+"axe_2");
	}

	@Override
	public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List)
	{
		helper.getSubItems(par1, par2CreativeTabs, par3List);

	}

	@Override
	public EnumUnsagaWeapon getCategory() {
		// TODO 自動生成されたメソッド・スタブ
		return EnumUnsagaWeapon.AXE;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{
		if(HelperAbility.hasAbilityFromItemStack(AbilityRegistry.tomahawk, par1ItemStack)){
			Unsaga.debug("トマホーク覚えてる");
			par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
		}


		if(!LivingDebuff.hasDebuff(par3EntityPlayer, DebuffRegistry.cooling)){
			if(HelperAbility.hasAbilityFromItemStack(AbilityRegistry.skyDrive, par1ItemStack) && !par3EntityPlayer.onGround){
				if(!LivingDebuff.hasDebuff(par3EntityPlayer, DebuffRegistry.flyingAxe)){

					par3EntityPlayer.motionY += 1.0;
					LivingDebuff.addLivingDebuff(par3EntityPlayer, new LivingStateFlyingAxe(DebuffRegistry.flyingAxe,30,(int)this.damageVsEntity));
					LivingDebuff.addLivingDebuff(par3EntityPlayer, new LivingState(DebuffRegistry.antiFallDamage,30,true));


				}
				if(LivingDebuff.hasDebuff(par3EntityPlayer, DebuffRegistry.flyingAxe) && par3EntityPlayer.isSneaking()){
					EntityLivingBase target = null;
					if(LivingDebuff.getLivingDebuff(par3EntityPlayer, DebuffRegistry.weaponTarget).isPresent()){
						LivingStateTarget state = (LivingStateTarget)LivingDebuff.getLivingDebuff(par3EntityPlayer, DebuffRegistry.weaponTarget).get();
						target = (EntityLivingBase) par2World.getEntityByID(state.targetid);
					}
					LivingDebuff.addDebuff(par3EntityPlayer, DebuffRegistry.cooling, 8);
					LivingDebuff.removeDebuff(par3EntityPlayer, DebuffRegistry.flyingAxe);
					SkillAxe skill = new SkillAxe();
					Unsaga.debug(target);
					skill.doSkydrive((EntityPlayer) par3EntityPlayer, par1ItemStack, (int)this.weapondamage + AbilityRegistry.skyDrive.damageWeapon,target);
				}
			}

		}

		return par1ItemStack;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack par1ItemStack)
	{
		if(HelperAbility.hasAbilityFromItemStack(AbilityRegistry.tomahawk, par1ItemStack)){
			return 72000;
		}
		return 0;
	}

	@Override
	public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
	{
		if(par1ItemStack==null){
			return false;
		}

		if(par2EntityPlayer.isSneaking() && HelperAbility.hasAbilityFromItemStack(AbilityRegistry.woodChopper, par1ItemStack)){
			par2EntityPlayer.swingItem();
			SkillAxe sa = new SkillAxe();
			int damageamount = sa.doWoodChopper(par1ItemStack, par2EntityPlayer, par3World, new XYZPos(par4,par5,par6));
			par1ItemStack.damageItem(damageamount+AbilityRegistry.woodChopper.damageWeapon, par2EntityPlayer);

		}


		return false;
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer, int par4)
	{
		int j = this.getMaxItemUseDuration(par1ItemStack) - par4;

		if(par1ItemStack==null)return;

		if (HelperAbility.hasAbilityFromItemStack(AbilityRegistry.tomahawk, par1ItemStack))
		{
			float f = j / 20.0F;
			f = (f * f + f * 2.0F) / 3.0F;

			if (f < 0.1D)
			{
				return;
			}

			if (f > 1.0F)
			{
				f = 1.0F;
			}

			par1ItemStack.damageItem(AbilityRegistry.tomahawk.damageWeapon, par3EntityPlayer);
			EntityFlyingAxe entityflyingaxe = new EntityFlyingAxe(par2World, par3EntityPlayer, f*1.1F,par1ItemStack,false);
			entityflyingaxe.setDamage(this.damageVsEntity);

			par2World.playSoundAtEntity(par3EntityPlayer, "random.bow", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + f * 0.5F);



			if (!par2World.isRemote)
			{
				par2World.spawnEntityInWorld(entityflyingaxe);
				if(entityflyingaxe.getEntityItem()!=null){
					ItemStack aitemstack = null;
					par3EntityPlayer.inventory.mainInventory[par3EntityPlayer.inventory.currentItem] = aitemstack;
				}

			}


			//par3EntityPlayer.inventory.consumeInventoryItem(par3EntityPlayer.inventory.currentItem);



		}
	}

	@Override
	public int getMaxAbility() {
		// TODO 自動生成されたメソッド・スタブ
		return 1;
	}
}

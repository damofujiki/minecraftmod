package hinasch.mods.unlsaga.item.weapon;

import hinasch.lib.XYZPos;
import hinasch.mods.unlsaga.Unsaga;
import hinasch.mods.unlsaga.core.init.UnsagaItems;
import hinasch.mods.unlsaga.core.init.UnsagaMaterial;
import hinasch.mods.unlsaga.core.init.UnsagaMaterials;
import hinasch.mods.unlsaga.entity.projectile.EntityFlyingAxe;
import hinasch.mods.unlsaga.item.weapon.base.ItemAxeBase;
import hinasch.mods.unlsaga.misc.ability.AbilityRegistry;
import hinasch.mods.unlsaga.misc.ability.HelperAbility;
import hinasch.mods.unlsaga.misc.ability.skill.effect.SkillAxe;
import hinasch.mods.unlsaga.misc.ability.skill.effect.InvokeSkill;
import hinasch.mods.unlsaga.misc.debuff.Debuffs;
import hinasch.mods.unlsaga.misc.debuff.livingdebuff.LivingDebuff;
import hinasch.mods.unlsaga.misc.debuff.livingdebuff.LivingState;
import hinasch.mods.unlsaga.misc.debuff.livingdebuff.LivingStateFlyingAxe;
import hinasch.mods.unlsaga.misc.debuff.livingdebuff.LivingStateTarget;
import hinasch.mods.unlsaga.misc.util.EnumUnsagaTools;
import hinasch.mods.unlsaga.misc.util.HelperUnsagaWeapon;
import hinasch.mods.unlsaga.misc.util.LockOnHelper;
import hinasch.mods.unlsaga.network.packet.PacketSkill;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemAxeUnsaga extends ItemAxeBase{

	
	public ItemAxeUnsaga(UnsagaMaterial mat) {
		super(mat);
		Unsaga.proxy.registerSpecialRenderer(this);
		UnsagaItems.putItemMap(this,EnumUnsagaTools.AXE.toString()+"."+mat.name);
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
	public int getMaxItemUseDuration(ItemStack par1ItemStack)
	{
		if(HelperAbility.hasAbilityFromItemStack(AbilityRegistry.tomahawk, par1ItemStack)){
			return 72000;
		}
		return 0;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{
		if(this.unsMaterial==UnsagaMaterials.failed){
			UnsagaMaterial unsuited = UnsagaItems.getRandomUnsuitedMaterial(UnsagaItems.getUnsuitedMaterial(this.getCategory()), this.itemRand);
			HelperUnsagaWeapon.initWeapon(par1ItemStack, unsuited.name, unsuited.weight);
		}
		if(HelperAbility.hasAbilityFromItemStack(AbilityRegistry.tomahawk, par1ItemStack)){
			Unsaga.debug("トマホーク覚えてる");
			par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
		}


		if(HelperAbility.hasAbilityFromItemStack(AbilityRegistry.skyDrive, par1ItemStack) && !par3EntityPlayer.onGround
				&& !LivingDebuff.isCooling(par3EntityPlayer)){
			if(!LivingDebuff.hasDebuff(par3EntityPlayer, Debuffs.flyingAxe)){
				this.setReadyToSkyDrive(par3EntityPlayer);
			}
			if(LivingDebuff.hasDebuff(par3EntityPlayer, Debuffs.flyingAxe) && par3EntityPlayer.isSneaking()){
				LockOnHelper.searchEntityNear(par3EntityPlayer, Debuffs.weaponTarget);
				ItemStack copyaxe = par1ItemStack.copy();
				InvokeSkill helper = new InvokeSkill(par2World,par3EntityPlayer,AbilityRegistry.skyDrive,copyaxe);
				EntityLivingBase target = null;
				
				if(LivingDebuff.getLivingDebuff(par3EntityPlayer, Debuffs.weaponTarget).isPresent()){
					LivingStateTarget state = (LivingStateTarget)LivingDebuff.getLivingDebuff(par3EntityPlayer, Debuffs.weaponTarget).get();
					target = (EntityLivingBase) par2World.getEntityByID(state.targetid);
				}
				if(target!=null){
					helper.setTarget(target);
				}
				--par1ItemStack.stackSize;
				helper.doSkill();
			}


		}


		return par1ItemStack;
	}
	
	@Override
	public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
	{
		if(par1ItemStack==null){
			return false;
		}

		if(par2EntityPlayer.isSneaking() && HelperAbility.hasAbilityFromItemStack(AbilityRegistry.woodChopper, par1ItemStack)){
			par2EntityPlayer.swingItem();
			InvokeSkill helper = new InvokeSkill(par3World,par2EntityPlayer,AbilityRegistry.woodChopper,par1ItemStack);
			helper.setUsePoint(new XYZPos(par4,par5,par6));
			helper.doSkill();


		}
		
		if(par2EntityPlayer.isSneaking() && !par2EntityPlayer.onGround && HelperAbility.hasAbilityFromItemStack(AbilityRegistry.woodBreakerPhoenix, par1ItemStack)){
			par2EntityPlayer.swingItem();
			PacketSkill ps = new PacketSkill(PacketSkill.PACKETID.WOODBREAKER,new XYZPos(par4,par5,par6));
			Unsaga.packetPipeline.sendToServer(ps);
			
		}


		return false;
	}
	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity)
	{
		//HelperSkill helper = new HelperSkill(stack,player);
		if(HelperAbility.hasAbilityFromItemStack(AbilityRegistry.fujiView, stack) && player.isSneaking() && !LivingDebuff.hasDebuff(player, Debuffs.cooling)){
			SkillAxe fujiView = new SkillAxe();
			if(!player.worldObj.isRemote && entity instanceof EntityLivingBase){
				InvokeSkill helper = new InvokeSkill(player.worldObj,player,AbilityRegistry.fujiView,stack);
				helper.setTarget((EntityLivingBase) entity);
				helper.doSkill();
			}


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
			entityflyingaxe.setDamage(this.unsMaterial.getToolMaterial().getDamageVsEntity());

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



	protected void setReadyToSkyDrive(EntityPlayer ep){
		ep.motionY += 1.0;
		LivingDebuff.addLivingDebuff(ep, new LivingStateFlyingAxe(Debuffs.flyingAxe,30,(int)this.unsMaterial.getToolMaterial().getDamageVsEntity()));
		LivingDebuff.addLivingDebuff(ep, new LivingState(Debuffs.antiFallDamage,30,true));
	}
}

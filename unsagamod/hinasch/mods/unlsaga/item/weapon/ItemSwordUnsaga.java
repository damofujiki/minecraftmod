package hinasch.mods.unlsaga.item.weapon;


import hinasch.lib.UtilNBT;
import hinasch.mods.unlsaga.Unsaga;
import hinasch.mods.unlsaga.core.init.UnsagaItems;
import hinasch.mods.unlsaga.core.init.UnsagaMaterial;
import hinasch.mods.unlsaga.item.weapon.base.ItemSwordBase;
import hinasch.mods.unlsaga.misc.ability.AbilityRegistry;
import hinasch.mods.unlsaga.misc.ability.HelperAbility;
import hinasch.mods.unlsaga.misc.ability.skill.effect.InvokeSkill;
import hinasch.mods.unlsaga.misc.debuff.Debuffs;
import hinasch.mods.unlsaga.misc.debuff.livingdebuff.LivingDebuff;
import hinasch.mods.unlsaga.misc.debuff.livingdebuff.LivingState;
import hinasch.mods.unlsaga.misc.util.EnumUnsagaTools;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class ItemSwordUnsaga extends ItemSwordBase{


	public static String GUSTKEY = "unsaga.gust";
	
	public ItemSwordUnsaga(UnsagaMaterial mat) {
		super(mat);
		Unsaga.proxy.registerSpecialRenderer(this);
		UnsagaItems.putItemMap(this,EnumUnsagaTools.SWORD.toString()+"."+mat.name);

	}

	@Override
	public void onPlayerStoppedUsing(ItemStack is, World world, EntityPlayer ep, int par4)
	{
		int j = this.getMaxItemUseDuration(is) - par4;
		if(HelperAbility.hasAbilityFromItemStack(AbilityRegistry.gust, is) && this.isGust(is)){
			this.setGust(is, false);
			if(j>15){
				InvokeSkill helper = new InvokeSkill(world,ep,AbilityRegistry.gust,is);
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
						InvokeSkill helper = new InvokeSkill(world,ep,AbilityRegistry.smash,is);
						helper.attack(damageentity, null,j);
						is.damageItem(AbilityRegistry.smash.damageWeapon, ep);
					}
				}
			}
		}
	}
	

	public static void hitExplodeByVandalize(LivingHurtEvent e){
		if(e.source.isExplosion()){
			if(e.source.getEntity() instanceof EntityLivingBase){
				EntityLivingBase el = (EntityLivingBase)e.source.getEntity();
				if(el.getHeldItem()!=null){
					if(HelperAbility.hasAbilityFromItemStack(AbilityRegistry.vandalize, el.getHeldItem())){
						e.ammount += AbilityRegistry.vandalize.hurtHp;
						Unsaga.lpHandler.tryHurtLP(e.entityLiving, AbilityRegistry.vandalize.hurtLp);
						Unsaga.debug("ヴァンダライズ炸裂！");
					}
				}

			}
		}
	}
	
	@Override
    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity)
    {
		if(HelperAbility.hasAbilityFromItemStack(AbilityRegistry.vandalize, stack) && player.isSneaking()){
			InvokeSkill helper = new InvokeSkill(player.worldObj,player,AbilityRegistry.vandalize,stack);
			if(entity instanceof EntityLivingBase){
				helper.setTarget((EntityLivingBase) entity);
				helper.setCoolingTime(8);
				helper.doSkill();
			}
		}
		if(HelperAbility.hasAbilityFromItemStack(AbilityRegistry.kaleidoscope,stack) && player.isSneaking()){
			InvokeSkill helper = new InvokeSkill(player.worldObj,player,AbilityRegistry.kaleidoscope,stack);
			if(entity instanceof EntityLivingBase){
				helper.setTarget((EntityLivingBase) entity);
				helper.doSkill();
			}


		}
        return false;
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
    	return super.getItemUseAction(par1ItemStack);
    }
    
    
	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{
		super.onItemRightClick(par1ItemStack, par2World, par3EntityPlayer);
		if(HelperAbility.hasAbilityFromItemStack(AbilityRegistry.roundabout, par1ItemStack)){
			if(LivingDebuff.hasDebuff(par3EntityPlayer, Debuffs.roundabout)){
				
				par3EntityPlayer.playSound("mob.wither.shoot", 0.5F, 1.8F / (par3EntityPlayer.getRNG().nextFloat() * 0.4F + 1.2F));

				Vec3 lookvec = par3EntityPlayer.getLookVec().normalize();
				par3EntityPlayer.addVelocity(lookvec.xCoord*0.7, 0.1, lookvec.zCoord*0.7);
				par1ItemStack.damageItem(1, par3EntityPlayer);
				return par1ItemStack;
			}else{
				if(!par3EntityPlayer.onGround){
					par3EntityPlayer.playSound("mob.wither.shoot", 0.5F, 1.8F / (par3EntityPlayer.getRNG().nextFloat() * 0.4F + 1.2F));

					LivingDebuff.addLivingDebuff(par3EntityPlayer, new LivingState(Debuffs.antiFallDamage,10,true));
					LivingDebuff.addLivingDebuff(par3EntityPlayer, new LivingState(Debuffs.roundabout,5,true));
					par3EntityPlayer.motionY += 0.7;
					par1ItemStack.damageItem(AbilityRegistry.roundabout.damageWeapon, par3EntityPlayer);
				}
			}

		}
		if(HelperAbility.hasAbilityFromItemStack(AbilityRegistry.chargeBlade,par1ItemStack) && par3EntityPlayer.isSprinting()){
			if(!LivingDebuff.hasDebuff(par3EntityPlayer,Debuffs.rushBlade)){
				InvokeSkill helper = new InvokeSkill(par2World,par3EntityPlayer,AbilityRegistry.chargeBlade,par1ItemStack);
				//par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
				//helper.setCoolingTime(4);
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

	
	@Override
    public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5) {
    	if(par3Entity instanceof EntityPlayer){
    		EntityPlayer ep = (EntityPlayer)par3Entity;
    		if(this.isGust(par1ItemStack) && !ep.isSneaking()){
    			this.setGust(par1ItemStack, false);
    		}
    	}
    }

}

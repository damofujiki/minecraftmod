package hinasch.mods.unlsaga.item.weapon;

import hinasch.lib.XYZPos;
import hinasch.mods.unlsaga.Unsaga;
import hinasch.mods.unlsaga.core.init.UnsagaItems;
import hinasch.mods.unlsaga.core.init.UnsagaMaterial;
import hinasch.mods.unlsaga.item.weapon.base.ItemStaffBase;
import hinasch.mods.unlsaga.misc.ability.skill.effect.InvokeSkill;
import hinasch.mods.unlsaga.misc.ability.skill.effect.SkillMelee;
import hinasch.mods.unlsaga.misc.util.EnumUnsagaTools;
import hinasch.mods.unlsaga.misc.util.HelperUnsagaWeapon;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemStaffUnsaga extends ItemStaffBase{


	public ItemStaffUnsaga(UnsagaMaterial uns) {
		super(uns);
		Unsaga.proxy.registerSpecialRenderer(this);
		UnsagaItems.putItemMap(this,EnumUnsagaTools.STAFF.toString()+"."+uns.name);
	}

	
	@Override
    public boolean onItemUse(ItemStack is, EntityPlayer ep, World par3World, int par4, int par5, int par6, int side, float par8, float par9, float par10)
    {
		SkillMelee pickedSkillEffect = HelperUnsagaWeapon.getSkillMelee(SkillMelee.Type.USE, is, ep, par3World, new XYZPos(par4,par5,par6));
		if(pickedSkillEffect!=null){
			InvokeSkill helper = new InvokeSkill(par3World, ep, pickedSkillEffect.getSkill(), is);
			if(helper!=null){
				helper.setUsePoint(new XYZPos(par4,par5,par6));
				helper.doSkill();
				
			}
		}
        return false;
    }

    


    @Override
    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity)
    {

    	if(entity instanceof EntityLivingBase){
    		SkillMelee pickedSkillEffect = HelperUnsagaWeapon.getSkillMelee(SkillMelee.Type.ENTITY_LEFTCLICK, stack, player, player.worldObj, null);
    		if(pickedSkillEffect!=null){
    			InvokeSkill helper = new InvokeSkill(player.worldObj, player, pickedSkillEffect.getSkill(), stack);
    			if(helper!=null){
    				helper.setTarget((EntityLivingBase) entity);
    				helper.doSkill();
    				
    			}
    			return true;
    		}
    		
//    		EntityPlayer ep = player;
//        	if(HelperAbility.hasAbilityFromItemStack(AbilityRegistry.skullCrash, stack) && ep.isSneaking()){
//        		InvokeSkill helper = new InvokeSkill(ep.worldObj,ep,AbilityRegistry.skullCrash,stack);
//        		helper.setTarget((EntityLivingBase) entity);
//        		helper.doSkill();
//        		return true;
//        	}
    	}
        return false;
    }
    
    
}

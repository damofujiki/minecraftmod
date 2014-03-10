package hinasch.mods.unlsaga.item.weapon;

import hinasch.lib.XYZPos;
import hinasch.mods.unlsaga.Unsaga;
import hinasch.mods.unlsaga.core.init.UnsagaItems;
import hinasch.mods.unlsaga.core.init.UnsagaMaterial;
import hinasch.mods.unlsaga.item.weapon.base.ItemStaffBase;
import hinasch.mods.unlsaga.misc.ability.AbilityRegistry;
import hinasch.mods.unlsaga.misc.ability.HelperAbility;
import hinasch.mods.unlsaga.misc.ability.skill.effect.SkillEffectHelper;
import hinasch.mods.unlsaga.misc.util.EnumUnsagaTools;
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
		if(HelperAbility.hasAbilityFromItemStack(AbilityRegistry.gonger, is) && ep.isSneaking() && side==1){
			SkillEffectHelper helper = new SkillEffectHelper(par3World,ep,AbilityRegistry.gonger,is);
			helper.setUsePoint(new XYZPos(par4,par5,par6));
			helper.doSkill();
		}
		if(HelperAbility.hasAbilityFromItemStack(AbilityRegistry.grandSlam, is) && ep.isSneaking() && side==1){
			SkillEffectHelper helper = new SkillEffectHelper(par3World,ep,AbilityRegistry.grandSlam,is);
			//helper.setCoolingTime(13);
			helper.setUsePoint(new XYZPos(par4,par5,par6));
			helper.doSkill();
		}
		if(HelperAbility.hasAbilityFromItemStack(AbilityRegistry.pulvorizer, is) && ep.isSneaking()){
			SkillEffectHelper helper = new SkillEffectHelper(par3World,ep,AbilityRegistry.pulvorizer,is);
			helper.setUsePoint(new XYZPos(par4,par5,par6));
			helper.doSkill();
		}
		if(HelperAbility.hasAbilityFromItemStack(AbilityRegistry.earthDragon, is) && ep.isSneaking() && side==1){
			SkillEffectHelper helper = new SkillEffectHelper(par3World,ep,AbilityRegistry.earthDragon,is);
			helper.setUsePoint(new XYZPos(par4,par5,par6));
			helper.doSkill();
		}
		if(HelperAbility.hasAbilityFromItemStack(AbilityRegistry.rockCrusher, is) && ep.isSneaking()){
			SkillEffectHelper helper = new SkillEffectHelper(par3World,ep,AbilityRegistry.rockCrusher,is);
			helper.setUsePoint(new XYZPos(par4,par5,par6));
			helper.doSkill();
		}
        return false;
    }

    


    @Override
    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity)
    {

    	if(entity instanceof EntityLivingBase){
    		EntityPlayer ep = player;
        	if(HelperAbility.hasAbilityFromItemStack(AbilityRegistry.skullCrash, stack) && ep.isSneaking()){
        		SkillEffectHelper helper = new SkillEffectHelper(ep.worldObj,ep,AbilityRegistry.skullCrash,stack);
        		helper.setTarget((EntityLivingBase) entity);
        		helper.doSkill();
        		return true;
        	}
    	}
        return false;
    }
    
    
}

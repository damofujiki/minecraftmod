package hinasch.mods.unlsaga.misc.smith;

import hinasch.mods.unlsaga.Unsaga;
import hinasch.mods.unlsaga.core.init.UnsagaItems;
import hinasch.mods.unlsaga.core.init.UnsagaMaterial;
import hinasch.mods.unlsaga.misc.ability.HelperAbility;
import hinasch.mods.unlsaga.misc.smith.ValidPayment.EnumPayValues;
import hinasch.mods.unlsaga.misc.util.EnumUnsagaTools;
import hinasch.mods.unlsaga.misc.util.HelperUnsagaItem;

import java.util.Map;
import java.util.Random;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;

import com.google.common.base.Optional;

public class ForgingTool {

	public static final String wellDone = "well";
	public static final String failed = "failed";
	public static final String normal = "good";
	public static enum EnumWorkResult {WELL,FAILED,GOOD};
	
	public UnsagaMaterial materialForged;
	public MaterialInfo base;
	public MaterialInfo sub;
	public int damageForged;
	public UnsagaMaterial baseMaterial;
	public UnsagaMaterial subMaterial;
	public int damageBase;
	public int damageSub;
	public EnumUnsagaTools categoryForge;
	public Random rand;
	public Optional<Map> newenchantMap;
	public int weightForged;
	
	public ForgingTool(EnumUnsagaTools category,MaterialInfo base,MaterialInfo sub,Random rand){
		this.categoryForge = category;
		this.base = base;
		this.sub = sub;
		this.baseMaterial = base.getMaterial().get();
		this.subMaterial = sub.getMaterial().get();
		this.rand = rand;
		this.newenchantMap = Optional.absent();
	}
	public ItemStack getForgedItemStack(){
		ItemStack newstack = UnsagaItems.getItemStack(this.categoryForge, this.materialForged, 1, this.damageForged);
		HelperUnsagaItem.initWeapon(newstack, this.materialForged.name, this.weightForged);
		if(this.newenchantMap.isPresent()){
			EnchantmentHelper.setEnchantments(newenchantMap.get(), newstack);
		}
		int maxdamage = newstack.getMaxDamage();
		int newdamage = maxdamage -1;
		newdamage = newdamage - this.damageForged;
		if(newdamage<0)newdamage = 0;
		newstack.setItemDamage(newdamage);
		return newstack;
	}
	
	public void doForge(EnumPayValues pay){
		this.decideForgedMaterial();
		this.calcForgedDamage();
		this.prepareTransplantEnchant(pay);
		this.calcForgedWeight();
	}
	public void calcForgedWeight(){
		int forged = 0;
		int baseweight = this.base.getWeight();
		int subweight = this.sub.getWeight();
		forged = subweight - baseweight;
		Unsaga.debug(forged+":sub:"+subweight+"baseweight:"+baseweight);
		this.weightForged = baseweight += MathHelper.clamp_int(forged, -2, +2);
		this.weightForged = MathHelper.clamp_int(this.weightForged, 0, 20);
	}

	public void decideForgedMaterial(){
		Optional<UnsagaMaterial> transformed = MaterialTransform.drawTransformed(baseMaterial, subMaterial, rand);
		if(transformed.isPresent()){
			this.materialForged = transformed.get();
			if(!UnsagaItems.isValidItemAsMaterial(this.categoryForge, this.materialForged)){
				this.materialForged = this.baseMaterial;
			}
		}else{
			this.materialForged = this.baseMaterial;
		}
		
	}
	
	public void calcForgedDamage(){
		this.damageForged = base.getPositiveDamage().get() + sub.getPositiveDamage().get();
		Unsaga.debug(this.damageForged);
	}
	

	//エンチャントの移植
	public void prepareTransplantEnchant(EnumPayValues pay){
		
		Map newMap = EnchantmentHelper.getEnchantments(this.base.is);
		if(newMap.isEmpty()){
			this.newenchantMap = Optional.absent();
			return;
		}
		this.newenchantMap = Optional.of(newMap);
		switch(pay){
		case HIGH:break;
		case MID:
			this.forgotSomeEnchant(25);
			break;
		case LOW:
			this.forgotSomeEnchant(60);
			break;
		}
		
	}
	
	//アビリティの移植
	public EnumWorkResult transplantAbilities(ItemStack forged,EntityPlayer ep,EnumPayValues pay){
		if(!HelperAbility.canGainAbility(forged) || !HelperAbility.canGainAbility(this.base.is)){
			return EnumWorkResult.GOOD;
		}
		HelperAbility helperBaseItem = new HelperAbility(this.base.is,ep);
		HelperAbility helperForged = new HelperAbility(forged,ep);
		if(helperBaseItem.getGainedAbilities().isPresent()){
			helperForged.setAbilityListToNBT(helperBaseItem.getGainedAbilities().get());
		}
		
		EnumWorkResult rt = EnumWorkResult.GOOD;
		Random rand = ep.getRNG();
		switch(pay){
		case HIGH:
			if(rand.nextInt(100)<20){
				helperForged.gainSomeAbility(rand);
				rt = EnumWorkResult.WELL;
			}
			
			break;
			
		case MID:
			rt = helperForged.forgetSomeAbilityFromProb(rand, 25) ? EnumWorkResult.FAILED : EnumWorkResult.GOOD;
			break;
		case LOW:
			rt = helperForged.forgetSomeAbilityFromProb(rand, 60) ? EnumWorkResult.FAILED : EnumWorkResult.GOOD;
			break;
		
		
		}
		return rt;
	}
	

	
	protected void forgotSomeEnchant(int prob){
		if(this.rand.nextInt(100)<=prob){
			this.newenchantMap = Optional.absent();
		}
	}
}

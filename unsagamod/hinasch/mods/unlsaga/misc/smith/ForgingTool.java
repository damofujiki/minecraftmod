package hinasch.mods.unlsaga.misc.smith;

import hinasch.mods.unlsaga.Unsaga;
import hinasch.mods.unlsaga.core.init.UnsagaItems;
import hinasch.mods.unlsaga.core.init.UnsagaMaterial;
import hinasch.mods.unlsaga.misc.smith.ValidPayment.EnumPayValues;
import hinasch.mods.unlsaga.misc.util.EnumUnsagaWeapon;
import hinasch.mods.unlsaga.misc.util.HelperUnsagaWeapon;

import java.util.Map;
import java.util.Random;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;

import com.google.common.base.Optional;

public class ForgingTool {

	
	public UnsagaMaterial materialForged;
	public MaterialInfo base;
	public MaterialInfo sub;
	public int damageForged;
	public UnsagaMaterial baseMaterial;
	public UnsagaMaterial subMaterial;
	public int damageBase;
	public int damageSub;
	public EnumUnsagaWeapon categoryForge;
	public Random rand;
	public Optional<Map> newenchantMap;
	public int weightForged;
	
	public ForgingTool(EnumUnsagaWeapon category,MaterialInfo base,MaterialInfo sub,Random rand){
		this.categoryForge = category;
		this.base = base;
		this.sub = sub;
		this.baseMaterial = base.getMaterial().get();
		this.subMaterial = sub.getMaterial().get();
		this.rand = rand;
		this.newenchantMap = Optional.absent();
	}
	public ItemStack getForgedItemStack(){
		ItemStack newstack = UnsagaItems.getItem(this.categoryForge, this.materialForged, 1, this.damageForged);
		HelperUnsagaWeapon.initWeapon(newstack, this.materialForged.name, this.materialForged.weight);
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
	

	
	public void decideForgedMaterial(){
		Optional<UnsagaMaterial> transformed = MaterialTransform.drawTransformed(baseMaterial, subMaterial, rand);
		if(transformed.isPresent()){
			this.materialForged = transformed.get();
			if(!UnsagaItems.isValidItemMaterial(this.categoryForge, this.materialForged)){
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
	

	public void prepareTransplantEnchant(EnumPayValues pay){
		
		Map newMap = EnchantmentHelper.getEnchantments(this.base.is);
		if(newMap.isEmpty()){
			this.newenchantMap = Optional.absent();
			return;
		}
		this.newenchantMap = Optional.of(newMap);
		switch(pay){
		case HIGH:break;
		case MID:this.forgotSomeEnchant(25);
		case LOW:this.forgotSomeEnchant(60);
		}
		
	}
	
	protected void forgotSomeEnchant(int prob){
		if(this.rand.nextInt(100)<=prob){
			this.newenchantMap = Optional.absent();
		}
	}
}

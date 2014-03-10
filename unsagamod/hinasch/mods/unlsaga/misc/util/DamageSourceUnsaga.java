package hinasch.mods.unlsaga.misc.util;

import hinasch.mods.unlsaga.core.FiveElements.EnumElement;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;

public class DamageSourceUnsaga extends EntityDamageSource{

	public float strLPHurt;
	public DamageHelper.Type damageType;
	public EnumElement element;
	public DamageSource parent;
	public String str;
	
	public DamageSourceUnsaga(String par1Str, Entity par2Entity) {
		super(par1Str, par2Entity);
		if(par1Str.equals("") || par1Str==null && par2Entity instanceof EntityLivingBase){
			DamageHelper.getMobPlayerString((EntityLivingBase) par2Entity);
		}
		this.strLPHurt = 0.1F;
		this.element = null;
	}
	
	public DamageSourceUnsaga(String par1Str, Entity par2Entity,float lpHurt,DamageHelper.Type type) {
		super(par1Str, par2Entity);

		this.strLPHurt = 0.1F;
		this.element = null;
		this.strLPHurt = lpHurt;
		this.damageType = type;
	}

	public float getStrengthLPHurt(){
		return this.strLPHurt;
		
	}
	
	public void setElement(EnumElement par1){
		this.element = par1;
	}
	
	public DamageHelper.Type getUnsagaDamageType(){
		return this.damageType;
	}
	
	public EnumElement getElement(){
		return this.element;
		
	}
	
	public DamageSource getParent(){
		return this.parent;
	}
}

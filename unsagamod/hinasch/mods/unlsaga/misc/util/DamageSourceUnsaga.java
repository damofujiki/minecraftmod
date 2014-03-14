package hinasch.mods.unlsaga.misc.util;

import hinasch.mods.unlsaga.core.FiveElements.EnumElement;

import java.util.EnumSet;

import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;

import com.google.common.collect.Lists;

public class DamageSourceUnsaga extends EntityDamageSource{

	public float strLPHurt;
	public EnumSet<DamageHelper.SubType> subTypeSet;
	public DamageHelper.Type damageType;
	public EnumElement element;
	public DamageSource parent;
	public String str;
	protected Entity sourceOfDamage;
	
	public DamageSourceUnsaga(String par1Str, Entity par2Entity) {
		super(DamageHelper.getMobPlayerString(par1Str,par2Entity), par2Entity);

	}
	
	public DamageSourceUnsaga(String par1Str, Entity par2Entity,float lpHurt,DamageHelper.Type type) {
		this(par1Str, par2Entity);

		this.strLPHurt = lpHurt;
		this.damageType = type;
	}
	
	public DamageSourceUnsaga(String par1Str, Entity par2Entity,float lpHurt,DamageHelper.Type type,Entity projectile) {
		this(par1Str, par2Entity,lpHurt,type);

		this.sourceOfDamage = projectile;
		this.setProjectile();
	}
	
	public DamageSourceUnsaga(DamageSource ds){
		super(ds.damageType,ds.getEntity());
		if(ds.isFireDamage()){
			this.setSubDamageType(DamageHelper.SubType.FIRE);
		}
		if(ds.isMagicDamage()){
			this.setMagicDamage();
		}
		if(ds.getSourceOfDamage()!=null){
			this.sourceOfDamage = ds.getSourceOfDamage();
		}
		if(ds.isProjectile()){
			this.setProjectile();
		}
		if(ds.getEntity() instanceof EntityBlaze|| ds.getEntity() instanceof EntityMagmaCube){
			this.setSubDamageType(DamageHelper.SubType.FIRE);
		}
		if(ds==DamageSource.cactus){
			this.setUnsagaDamageType(DamageHelper.Type.SPEAR);;
		}
		if(ds.getSourceOfDamage() instanceof EntityArrow){
			this.setUnsagaDamageType(DamageHelper.Type.SPEAR);
		}
		if(ds.isExplosion()){
			this.setExplosion();
			this.setUnsagaDamageType(DamageHelper.Type.MAGIC);
		}


	}

	@Override
	public Entity getSourceOfDamage(){
		return this.sourceOfDamage;
	}
	
	public float getStrengthLPHurt(){
		return this.strLPHurt;
		
	}
	
	public void setElement(EnumElement par1){
		this.element = par1;
	}
	
	public void setUnsagaDamageType(DamageHelper.Type type){
		this.damageType = type;
	}
	
	public DamageHelper.Type getUnsagaDamageType(){
		if(this.damageType!=null){
			return this.damageType;
		}
		//とりあえず
		return DamageHelper.Type.MAGIC;
	}
	
	public void setSubDamageType(DamageHelper.SubType... subtypes){
		EnumSet<DamageHelper.SubType> rt = EnumSet.copyOf(Lists.newArrayList(subtypes));
		this.subTypeSet = rt;
	}
	public EnumSet<DamageHelper.SubType> getSubDamageType(){
		if(this.subTypeSet==null || this.subTypeSet.isEmpty()){
			return EnumSet.of(DamageHelper.SubType.NONE);
		}
		return this.subTypeSet;
	}
	public EnumElement getElement(){
		return this.element;
		
	}
	
	public DamageSource getParent(){
		return this.parent;
	}
}

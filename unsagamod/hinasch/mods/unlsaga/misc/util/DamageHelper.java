package hinasch.mods.unlsaga.misc.util;

import hinasch.mods.unlsaga.core.FiveElements.EnumElement;
import hinasch.mods.unlsaga.entity.EntityTreasureSlime;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;

public class DamageHelper {

	public static enum Type {SWORD,PUNCH,SPEAR,SWORDPUNCH,MAGIC};

	protected static Map<EnumUnsagaTools,Type> typesNormalAttack;
	protected static Map<EnumUnsagaTools,Float> strsNormalAttack; 
	
	public DamageSource getDamageSourceFromLiving(EntityLivingBase living){
		if(living instanceof EntityPlayer){
			return DamageSource.causePlayerDamage((EntityPlayer) living);
		}
		
		return DamageSource.causeMobDamage(living);
		
	}
	


	public static DamageSourceUnsaga getUnsagaDamageSource(DamageSource ds){
		if(ds.getEntity() instanceof EntityPlayer){
			return new DamageSourceUnsaga("player",ds.getEntity());
		}
		return new DamageSourceUnsaga("mob",ds.getEntity());
	}
	
	public static DamageSourceUnsaga getUnsagaDamageSource(DamageSource ds,EnumUnsagaTools category){
		if(ds.getEntity() instanceof EntityPlayer){
			return new DamageSourceUnsaga("player",ds.getEntity(),getStrLPNormalAttack(category),getTypeNormalAttack(category));
		}
		return new DamageSourceUnsaga("mob",ds.getEntity(),getStrLPNormalAttack(category),getTypeNormalAttack(category));
	}
	
	private static void addMap(EnumUnsagaTools category,Type type,float strlp){
		typesNormalAttack.put(category,type);
		strsNormalAttack.put(category,strlp);
		
	}
	
	public static Type getTypeNormalAttack(EnumUnsagaTools category){
		if(typesNormalAttack.containsKey(category)){
			return typesNormalAttack.get(category);
		}
		return Type.SWORD;
	}
	
	public static float getStrLPNormalAttack(EnumUnsagaTools category){
		if(strsNormalAttack.containsKey(category)){
			return strsNormalAttack.get(category);
		}
		return 0.1F;
	}
	
	public static String getMobPlayerString(EntityLivingBase living){
		if(living instanceof EntityPlayer){
			return "player";
		}else{
			return "mob";
		}
	}
	public static float getDamageModifierFromType(Type type,Entity target,float baseStr){
		float modifier = 0.0F;
		switch(type){
		case SWORD:
			break;
		case PUNCH:
			if(target instanceof EntitySkeleton){
				modifier += baseStr * 0.7F;
			}
			if(target instanceof EntitySlime || target instanceof EntityTreasureSlime){
				modifier = -256;
			}
			break;
		case SWORDPUNCH:
			if(target instanceof EntitySkeleton){
				modifier += baseStr * 0.4F;
			}
			if(target instanceof EntitySlime || target instanceof EntityTreasureSlime){
				modifier -= baseStr * 0.2F;
			}
			break;
		case SPEAR:
			if(target instanceof EntitySkeleton){
				modifier -= baseStr * 0.5F;
			}
			break;
		case MAGIC:
			break;
		}
		return modifier;
	}
	
	public static float getDamageModifierFromElementType(EnumElement type,Entity target,float baseStr){
		float modifier = 0.0F;
		if(target instanceof EntityLivingBase){
			EntityLivingBase living = (EntityLivingBase)target;
			switch(type){
			case FIRE:
				if(living.getCreatureAttribute()==EnumCreatureAttribute.UNDEAD){
					modifier += baseStr * 0.4F;
				}
				if(living.isImmuneToFire()){
					modifier -= baseStr * 0.7F;
				}
				if(living instanceof EntityMagmaCube || living instanceof EntityBlaze){
					modifier -= baseStr * 0.9F;
				}
				break;
			case EARTH:
				break;
			case WOOD:
				break;
			case WATER:
				if(target instanceof EntityMagmaCube){
					modifier += baseStr * 0.4F;
				}
				if(target instanceof EntityBlaze){
					modifier += baseStr * 0.5F;
				}
				break;
			case METAL:
				break;
			case FORBIDDEN:
				break;
			}
		}

		return modifier;
	}
	static{
		typesNormalAttack = new HashMap();
		strsNormalAttack = new HashMap();
		addMap(EnumUnsagaTools.SWORD, Type.SWORD,0.3F);
		addMap(EnumUnsagaTools.SPEAR, Type.SPEAR,0.5F);
		addMap(EnumUnsagaTools.STAFF, Type.PUNCH,0.2F);
		addMap(EnumUnsagaTools.BOW, Type.SPEAR,0.5F);
		addMap(EnumUnsagaTools.AXE, Type.SWORDPUNCH,0.3F);
	}
}

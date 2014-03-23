package hinasch.mods.unlsaga.misc.debuff;

import hinasch.lib.PairObject;
import hinasch.lib.StaticWords;
import hinasch.mods.unlsaga.misc.ability.Ability;
import hinasch.mods.unlsaga.misc.debuff.livingdebuff.LivingDebuff;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;

public class Debuff {

	public final String name;
	protected int particle;
	protected List<Ability> abilitiesAgainst;
	protected PairObject<IAttribute,AttributeModifier> attributeModifier;
	
	public final int number;
	
	protected Debuff(int num,String nameEn){
		this.name = nameEn;
		this.number = num;
		this.particle = -1;
		this.abilitiesAgainst = new ArrayList();

		this.attributeModifier = new PairObject(null,null);
		Debuffs.debuffMap.put(num, this);
	}
	public String toString(){
		return this.name;
	}
	
	
	public LivingDebuff init(String[] strs){
		Debuff debuff = Debuffs.getDebuff(Integer.valueOf(strs[0]));
		int remain = Integer.valueOf(strs[1]); 
		return new LivingDebuff(debuff,remain);
	}
	
	public int getParticleNumber(){
		if(particle!=-1){
			return particle;
		}
		return StaticWords.getParticleNumber(StaticWords.particleSpell);
	}
	
	public Debuff setParticleNumber(int par1){
		this.particle = par1;
		return this;
	}
	
	public Debuff setAttributeModifier(IAttribute ia,AttributeModifier par1){
		this.attributeModifier = new PairObject(ia,par1);
		return this;
	}
	
	public AttributeModifier getAttributeModifier(){
		return this.attributeModifier.right;
	}
	
	public IAttribute getAttributeType(){
		return this.attributeModifier.left;
	}
	
	public List<Ability> getAbilityAgainst(){
		return this.abilitiesAgainst;
	}
	
	public Debuff addAbilityAgainst(Ability par1){
		this.abilitiesAgainst.add(par1);
		return this;
	}
	public Debuff setAbilitiesAgainst(List<Ability> par1){
		this.abilitiesAgainst = par1;
		return this;
	}
}

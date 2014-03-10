package hinasch.mods.unlsagamagic.misc.spell.effect;

import hinasch.lib.HSLibs;
import hinasch.mods.unlsaga.misc.debuff.Buff;
import hinasch.mods.unlsaga.misc.debuff.livingdebuff.LivingBuff;
import hinasch.mods.unlsaga.misc.debuff.livingdebuff.LivingDebuff;

import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class SpellAddBuff extends AbstractSpell{

	protected List<Buff> buffs;
	protected List<Potion> potions;
	
	public SpellAddBuff(){
		
	}
	public SpellAddBuff(World world) {
		super(world);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	public void setBuff(List<Buff> buffs){
		this.buffs = buffs;

	}
	
	public void setPotions(List<Potion> potions){
		this.potions = potions;
	}
	
	public List<Buff> getBuff(){
		return this.buffs;
	}
	@Override
	public void doSpell(InvokeSpell parent) {
		int remain = (int)((float)15 * parent.getAmp());
		int lv =(int)parent.getAmp()-1;
		lv = MathHelper.clamp_int(lv, 0, 5);
		if(parent.getTarget().isPresent()){
			this.doActiveBuffs(parent, parent.getTarget().get(), remain, lv);

			
		}else{
			this.doActiveBuffs(parent, parent.invoker, remain, lv);


		}
		
	}
	
	public void doActiveBuffs(InvokeSpell parent,EntityLivingBase target,int remain,int lv){
		if(buffs!=null && !buffs.isEmpty()){
			for(Buff buff:this.getBuff()){
				LivingDebuff.addLivingDebuff(target, new LivingBuff(buff,remain,1));
			}
		}
		if(potions!=null && !potions.isEmpty()){
			for(Potion potion:potions){
				target.addPotionEffect(new PotionEffect(potion.id,HSLibs.getPotionTime(remain),lv));
			}
			
		}
	}


}

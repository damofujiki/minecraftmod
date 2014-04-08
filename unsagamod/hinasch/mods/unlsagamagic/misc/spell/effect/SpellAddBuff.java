package hinasch.mods.unlsagamagic.misc.spell.effect;

import hinasch.mods.unlsaga.misc.debuff.Buff;
import hinasch.mods.unlsaga.misc.debuff.livingdebuff.LivingBuff;
import hinasch.mods.unlsaga.misc.debuff.livingdebuff.LivingDebuff;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hinasch.lib.HSLibs;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.MathHelper;

public class SpellAddBuff extends SpellBase{

	protected List<Buff> buffs;
	protected List<Potion> potions;
	
	protected Map<Potion,Potion> mobPotionMap;
	
	public SpellAddBuff(){
		this.mobPotionMap = new HashMap();
		this.buffs = new ArrayList();
		this.potions = new ArrayList();
		//プレイヤー以外の処理関係
		this.mobPotionMap.put(Potion.digSpeed, Potion.moveSpeed);
		this.mobPotionMap.put(Potion.digSlowdown, Potion.moveSlowdown);
		this.mobPotionMap.put(Potion.nightVision, Potion.moveSpeed);
		this.mobPotionMap.put(Potion.invisibility, Potion.moveSlowdown);
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
	public void invokeSpell(InvokeSpell parent) {
		int remain = (int)((float)15 * parent.getAmp());
		int lv =(int)parent.getAmp()-1;
		lv = MathHelper.clamp_int(lv, 0, 5);
		if(parent.getTarget().isPresent()){
			this.doActiveBuffs(parent, parent.getTarget().get(), remain, lv);

			
		}else{
			this.doActiveBuffs(parent, parent.getInvoker(), remain, lv);


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
				if(!(parent.getInvoker() instanceof EntityPlayer)){
					Potion potionMob = potion;
					if(this.mobPotionMap.containsKey(potion)){
						potionMob = this.mobPotionMap.get(potion);
					}
					target.addPotionEffect(new PotionEffect(potionMob.id,HSLibs.getPotionTime(remain),lv));
				}else{
					target.addPotionEffect(new PotionEffect(potion.id,HSLibs.getPotionTime(remain),lv));
				}
				
			}
			
		}
	}


}

package hinasch.mods.unlsaga.misc.ability.skill;

import hinasch.lib.UtilList;
import hinasch.mods.unlsaga.Unsaga;
import hinasch.mods.unlsaga.misc.ability.Ability;
import hinasch.mods.unlsaga.misc.ability.HelperAbility;
import hinasch.mods.unlsaga.misc.translation.Translation;
import hinasch.mods.unlsaga.misc.util.ChatUtil;
import hinasch.mods.unlsaga.misc.util.HelperUnsagaWeapon;
import hinasch.mods.unlsaga.network.packet.PacketSound;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;

public class HelperSkill extends HelperAbility{

	protected boolean isHeavy;
	
	public HelperSkill(ItemStack is, EntityLivingBase living) {
		super(is, living);
		if(HelperUnsagaWeapon.getCurrentWeight(is)>5){
			this.isHeavy = true;
		}else{
			this.isHeavy = false;
		}
		// TODO 自動生成されたコンストラクター・スタブ
	}
	
	@Override
	public void gainSomeAbility(Random rand){
		if(this.owner instanceof EntityPlayer && ab.getSkillList(category,this.isHeavy ).isPresent()){
			List<Ability> abList = new ArrayList();

			if(HelperAbility.getGainedAsIntList(this.is).isPresent()){
				List<Integer> gainedAbilityList = HelperAbility.getGainedAsIntList(this.is).get();
				if(gainedAbilityList.size()>=this.maxAbilitySize){
					return;
				}
				List<Integer> baseList = ab.exchangeToInt(ab.getSkillList(category, this.isHeavy).get());
				abList = ab.exchangeToAbilities(UtilList.getListExceptList(baseList, gainedAbilityList));

				
			}else{
				abList = ab.getSkillList(category,this.isHeavy).get();
				
			}
			
			if(!abList.isEmpty() && abList!=null){
				int numgain = this.getRandomIndex(rand, abList.size());
				
				Ability gainab = abList.get(numgain);
				Unsaga.debug(gainab.getName(1)+"を覚えた");
				String mesbase = Translation.localize("msg.gained.skill");
				String formatted = String.format(mesbase, gainab.getName(Translation.getLang()));
				if(!owner.worldObj.isRemote){
					ChatUtil.addMessageNoLocalized(owner, formatted);
					PacketSound ps = new PacketSound(1022);
					Unsaga.packetPipeline.sendTo(ps, (EntityPlayerMP) owner);
				}
					

				this.addAbility(gainab);
			}
		}
		
		
	}

}

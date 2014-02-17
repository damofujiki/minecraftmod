package hinasch.mods.unlsaga.misc.ability.skill;

import hinasch.lib.UtilList;
import hinasch.mods.unlsaga.Unsaga;
import hinasch.mods.unlsaga.misc.ability.Ability;
import hinasch.mods.unlsaga.misc.ability.HelperAbility;
import hinasch.mods.unlsaga.misc.util.HelperUnsagaWeapon;
import hinasch.mods.unlsaga.network.PacketHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;

public class HelperSkill extends HelperAbility{

	protected boolean isHeavy;
	
	public HelperSkill(ItemStack is, EntityPlayer ep) {
		super(is, ep);
		if(HelperUnsagaWeapon.getCurrentWeight(is)>5){
			this.isHeavy = true;
		}else{
			this.isHeavy = false;
		}
		// TODO 自動生成されたコンストラクター・スタブ
	}
	
	@Override
	public void gainSomeAbility(Random rand){
		if(ab.getSkillList(category,this.isHeavy ).isPresent()){
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
				PacketDispatcher.sendPacketToPlayer(PacketHandler.getMessagePacket(1,gainab.number), (Player) this.player);
				PacketDispatcher.sendPacketToPlayer(PacketHandler.getSoundPacket((int)1022),(Player)this.player);
				this.addAbility(gainab);
			}
		}
		
		
	}

}

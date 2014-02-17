package hinasch.mods.unlsaga.misc.ability;

import hinasch.lib.CSVText;
import hinasch.lib.UtilList;
import hinasch.lib.UtilNBT;
import hinasch.mods.unlsaga.Unsaga;
import hinasch.mods.unlsaga.core.event.ExtendedPlayerData;
import hinasch.mods.unlsaga.core.init.UnsagaMaterial;
import hinasch.mods.unlsaga.misc.util.EnumUnsagaWeapon;
import hinasch.mods.unlsaga.misc.util.HelperUnsagaWeapon;
import hinasch.mods.unlsaga.misc.util.IUnsagaMaterial;
import hinasch.mods.unlsaga.network.PacketHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;

import com.google.common.base.Optional;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;

public class HelperAbility {

	
	protected AbilityRegistry ab = Unsaga.abilityRegistry;
	protected ItemStack is;
	protected EnumUnsagaWeapon category;
	protected int maxAbilitySize;
	protected UnsagaMaterial material;
	protected static String KEY = "abilities";
	protected EntityPlayer player;
	
	public HelperAbility (ItemStack is,EntityPlayer ep){
		this.player = ep;
		this.is = is;
		IUnsagaMaterial iu = (IUnsagaMaterial)is.getItem();
		this.category = iu.getCategory();
		this.material = HelperUnsagaWeapon.getMaterial(is);
		this.maxAbilitySize = ((IGainAbility)is.getItem()).getMaxAbility();
	}
	
	public void gainSomeAbility(Random rand){
		if(ab.getAbilities(category, material).isPresent()){
			List<Ability> abList = new ArrayList();

			if(getGainedAsIntList(this.is).isPresent()){
				List<Integer> gainedAbilityList = getGainedAsIntList(this.is).get();
				if(gainedAbilityList.size()>=this.maxAbilitySize){
					return;
				}
				List<Integer> baseList = ab.exchangeToInt(ab.getAbilities(category, material).get());
				abList = ab.exchangeToAbilities(UtilList.getListExceptList(baseList, gainedAbilityList));

				
			}else{
				abList = ab.getAbilities(category, material).get();
				
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
	
	public void drawChanceToGainAbility(Random rand,int par1){
		int prob = MathHelper.clamp_int(par1, 0, 100);
		if(rand.nextInt(100)<=prob){
			this.gainSomeAbility(rand);
		}
	}
	
	public void drawChanceToGainAbility(Random rand,Entity enemy){
		int prob = GainProbFromEnemy.getProbGainSkill(enemy);
		this.drawChanceToGainAbility(rand, prob);
	}
	
	public void addAbility(Ability ab){
		if(UtilNBT.hasKey(is, KEY)){
			List<Integer> gainedList = getGainedAsIntList(this.is).get();
			gainedList.add(ab.number);
			this.setListToNBT(gainedList);
		}else{
			List<Integer> newList = new ArrayList();
			newList.add(ab.number);
			this.setListToNBT(newList);
		}
	}
	
	public void forgetSomeAbility(Random rand){
		if(getGainedAsIntList(this.is).isPresent()){
			List<Ability> ablist = ab.exchangeToAbilities(getGainedAsIntList(this.is).get());
			int indexforgot = this.getRandomIndex(rand, ablist.size());
			ablist.remove(indexforgot);
			if(ablist.isEmpty()){
				this.removeAbilityTag();
			}else{
				this.setAbilityListToNBT(ablist);
			}
			
		}
	}
	
	public void removeAbilityTag(){
		if(UtilNBT.hasKey(is, KEY)){
			UtilNBT.removeTag(is, KEY);
		}
	}
	
	public void setListToNBT(List<Integer> input){
		if(input!=null && !input.isEmpty()){
			UtilNBT.setFreeTag(is, KEY, CSVText.intListToCSV(input));
		}
	}
	
	public void setAbilityListToNBT(List<Ability> input){
		if(input!=null && !input.isEmpty()){
			List<Integer> newlist = ab.exchangeToInt(input);
			this.setListToNBT(newlist);
		}
	}
	
	public boolean hasAbility(Ability ab){
		if(getGainedAsIntList(this.is).isPresent()){
			return getGainedAsIntList(this.is).get().contains(ab.number);
		}
		return false;
	}
	
	public static boolean hasAbilityFromItemStack(Ability ab,ItemStack is){
		if(getGainedAsIntList(is).isPresent()){
			return getGainedAsIntList(is).get().contains(ab.number);
		}
		return false;
	}
	
	public static Optional<List<Integer>> getGainedAsIntList(ItemStack is){
		if(UtilNBT.hasKey(is, KEY)){
			List<Integer> intlist = CSVText.csvToIntList(UtilNBT.readFreeStrTag(is, KEY));
			if(intlist!=null){
				return Optional.of(intlist);
			}
			
		}
		return Optional.absent();
	}
	

	public int getRandomIndex(Random rand,int size){
		if(size==1){
			return 0;
		}
		return rand.nextInt(size);
	}
	
	public static boolean canGainAbility(ItemStack is){
		if(is!=null){
			if(is.getItem() instanceof IGainAbility && is.getItem() instanceof IUnsagaMaterial){
				return true;
			}
		}
		return false;
	}
	
	public Optional<List<Ability>> getGainedAbilities(){
		if(getGainedAsIntList(this.is).isPresent()){
			List<Integer> list = getGainedAsIntList(this.is).get();
			return Optional.of(ab.exchangeToAbilities(list));
		}
		return Optional.absent();
	}
	

	
	public int getHealAmount(){
		List<Ability> list = new ArrayList();
		int healAmount = 0;
		if(this.getGainedAbilities().isPresent()){
			list = this.getGainedAbilities().get();
			for(Ability ab:AbilityRegistry.healUps){
				if(list.contains(ab)){
					healAmount += ab.healPoint;
				}
			}
			for(Ability ab:AbilityRegistry.healDowns){
				if(list.contains(ab)){
					healAmount += ab.healPoint;
				}
			}
			//Unsaga.debug("計算:"+healAmount);
		}
		return healAmount;
	}
	
	public static int hasAbilityPlayer(EntityPlayer ep,Ability ability){
		int amount = 0;
		if(ep.getExtendedProperties(ExtendedPlayerData.key)!=null){
			ExtendedPlayerData data = (ExtendedPlayerData)ep.getExtendedProperties(ExtendedPlayerData.key);
			for(ItemStack is:data.getItemStacks()){
				if(is!=null){
					HelperAbility helper = new HelperAbility(is,ep);
					if(helper.hasAbility(ability)){
						amount += 1;
					}
				}
			}
			for(ItemStack armor:ep.inventory.armorInventory){
				if(armor!=null){
					HelperAbility helper = new HelperAbility(armor,ep);
					if(helper.hasAbility(ability)){
						amount += 1;
					}
				}
			}
		}
		return amount;
	}
}

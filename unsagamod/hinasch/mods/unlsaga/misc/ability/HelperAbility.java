package hinasch.mods.unlsaga.misc.ability;

import hinasch.mods.unlsaga.Unsaga;
import hinasch.mods.unlsaga.core.event.ExtendedPlayerData;
import hinasch.mods.unlsaga.core.init.UnsagaMaterial;
import hinasch.mods.unlsaga.item.IUnsagaMaterial;
import hinasch.mods.unlsaga.misc.translation.Translation;
import hinasch.mods.unlsaga.misc.util.ChatMessageHandler;
import hinasch.mods.unlsaga.misc.util.EnumUnsagaTools;
import hinasch.mods.unlsaga.misc.util.HelperUnsagaItem;
import hinasch.mods.unlsaga.network.packet.PacketSound;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;

import com.google.common.base.Optional;
import com.hinasch.lib.CSVText;
import com.hinasch.lib.HSLibs;
import com.hinasch.lib.UtilList;
import com.hinasch.lib.UtilNBT;

public class HelperAbility {


	protected AbilityRegistry ab = Unsaga.abilityRegistry;
	protected ItemStack is;
	protected EnumUnsagaTools category;
	protected int maxAbilitySize;
	protected UnsagaMaterial material;
	protected static String KEY = "abilities";
	protected EntityLivingBase owner;

	public HelperAbility (ItemStack is,EntityLivingBase living){
		this.owner = living;
		this.is = is;
		IUnsagaMaterial iu = (IUnsagaMaterial)is.getItem();
		this.category = iu.getCategory();
		this.material = HelperUnsagaItem.getMaterial(is);
		this.maxAbilitySize = ((IGainAbility)is.getItem()).getMaxAbility();
	}

	public void gainSomeAbility(Random rand){
		if(owner instanceof EntityPlayer && ab.getAbilities(category, material).isPresent()){
			EntityPlayer player = (EntityPlayer)owner;
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
				Unsaga.debug(gainab.getName(1)+"を覚えた:"+this.owner);
				//msg.gained.ability
				String mesbase = Translation.localize("msg.gained.ability");
				String mes = String.format(mesbase, gainab.getName(Translation.getLang()));
				if(HSLibs.isServer(player.worldObj)){
					ChatMessageHandler.sendChatToPlayer(player, mes);
					
					PacketSound ps = new PacketSound(1022);
					Unsaga.packetPipeline.sendTo(ps, (EntityPlayerMP) player);
				}

				
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
	
	public boolean forgetSomeAbilityFromProb(Random rand,int prob){
		if(rand.nextInt(100)<prob){
			forgetSomeAbility(rand);
			return true;
		}
		return false;
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
		if(Unsaga.abilityRegistry.getInheritAbilities(this.category, this.material).isPresent()){
			return Unsaga.abilityRegistry.getInheritAbilities(this.category, this.material).get().contains(ab);
		}
		return false;
	}

	public static boolean hasAbilityFromItemStack(Ability ab,ItemStack is){

		if(getGainedAsIntList(is).isPresent()){
			return getGainedAsIntList(is).get().contains(ab.number);
		}
		if(is.getItem() instanceof IUnsagaMaterial){
			UnsagaMaterial us = HelperUnsagaItem.getMaterial(is);
			EnumUnsagaTools category = ((IUnsagaMaterial)is.getItem()).getCategory();
			if(Unsaga.abilityRegistry.getInheritAbilities(category, us).isPresent()){
				return Unsaga.abilityRegistry.getInheritAbilities(category, us).get().contains(ab);
			}
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

	public static int hasAbilityLiving(EntityLivingBase el,Ability ability){
		int amount = 0;
		if(el instanceof EntityPlayer){
			EntityPlayer ep = (EntityPlayer)el;
			if(ep.getExtendedProperties(ExtendedPlayerData.key)!=null){
				ExtendedPlayerData data = (ExtendedPlayerData)ep.getExtendedProperties(ExtendedPlayerData.key);
				for(ItemStack is:data.getAccessories()){
					if(is!=null && is.getItem() instanceof IGainAbility){
						HelperAbility helper = new HelperAbility(is,ep);
						if(helper.hasAbility(ability)){
							amount += 1;
						}
					}
				}
			}
			for(ItemStack armor:ep.inventory.armorInventory){
				if(armor!=null && armor.getItem() instanceof IGainAbility){
					HelperAbility helper = new HelperAbility(armor,ep);
					if(helper.hasAbility(ability)){
						amount += 1;
					}
				}
			}


			ItemStack held = ep.getHeldItem();
			if(held!=null && HelperAbility.hasAbilityFromItemStack(ability, held)){
				amount += 1;
			}

		}else{
			//ItemStack currentitem = el.getCurrentItemOrArmor(0);
			//if(currentitem!=null && HelperAbility.hasAbilityFromItemStack(ability, currentitem))amount +=1;
			for(int i=0;i<5;i++){
				ItemStack is = el.getEquipmentInSlot(i);
				if(is!=null && HelperAbility.hasAbilityFromItemStack(ability, is))amount +=1;
			}
		}

		return amount;
	}
}

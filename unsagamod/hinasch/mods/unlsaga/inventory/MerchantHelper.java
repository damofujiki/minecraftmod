package hinasch.mods.unlsaga.inventory;

import hinasch.mods.unlsaga.Unsaga;
import hinasch.mods.unlsaga.core.event.ExtendedMerchantData;
import hinasch.mods.unlsaga.misc.bartering.MerchandiseInfo;

import java.util.Random;

import com.hinasch.lib.XYZPos;

import net.minecraft.entity.IMerchant;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.item.ItemStack;
import net.minecraft.village.Village;

public class MerchantHelper {

	protected InventoryMerchantUnsaga invMerchant;
	
	public MerchantHelper(InventoryMerchantUnsaga par1){
		this.invMerchant = par1;
	}
	
	public void changeMerchandiseStock(Random rand){
		for(int i=0;i<9;++i){
			EntityVillager villager = (EntityVillager)this.invMerchant.theMerchant;
			XYZPos po = XYZPos.entityPosToXYZ(villager);
			Village village = villager.worldObj.villageCollectionObj.findNearestVillage(po.x, po.y, po.z, 300);
			int repu = 1;
			if(village!=null){
				repu = village.getReputationForPlayer(this.invMerchant.theCustomer.getCommandSenderName());
				Unsaga.debug("街の有名度（発展度）："+repu);
			}
			ItemStack mis = MerchandiseInfo.getRandomMerchandise(rand,repu);
			MerchandiseInfo.setBuyPriceTag(mis,MerchandiseInfo.getPrice(mis)*3);
			this.invMerchant.setMerchandise(i, mis);

		}
	}
	
	public void initMerhcnadiseStock(IMerchant merchant){
		EntityVillager villager = (EntityVillager)merchant;
		if(villager.getExtendedProperties(ExtendedMerchantData.VILLAGER)!=null){
			ExtendedMerchantData data = (ExtendedMerchantData) villager.getExtendedProperties(ExtendedMerchantData.VILLAGER);

			//イニシャライズがされてなかったらインヴェントリに詰める。
			//そうでなかったら、一定時間ごとにアイテムを仕入れる
			if(!data.hasMerchandiceInitialized()){
				this.changeMerchandiseStock(villager.getRNG());
				data.recentPurchaseDate = villager.worldObj.getTotalWorldTime();
				data.markMerchandiceInitialized(true);
			}else{
				for(int i=0;i<9;++i){
					this.invMerchant.setMerchandise(i, data.getMerchantInventory(i));

				}
			}


			if((villager.worldObj.getTotalWorldTime() - data.recentPurchaseDate)>=24000){
				this.changeMerchandiseStock(villager.getRNG());
				data.recentPurchaseDate = villager.worldObj.getTotalWorldTime();
				Unsaga.debug(villager.worldObj.getWorldTime());
				Unsaga.debug(data.recentPurchaseDate);
			}
		}
	}
	
	public void registerMerchandiseStock(){
		if(this.invMerchant.villager.getExtendedProperties(this.invMerchant.KEY)==null){
			ExtendedMerchantData newdata = new ExtendedMerchantData();
			for(int i=0;i<9;i++){
				newdata.setMerchantInventory(i, this.invMerchant.getMerchandise(i));
			}

			this.invMerchant.villager.registerExtendedProperties(invMerchant.KEY, newdata);
		}else{
			ExtendedMerchantData data = (ExtendedMerchantData) this.invMerchant.villager.getExtendedProperties(invMerchant.KEY);
			for(int i=0;i<9;i++){
				data.setMerchantInventory(i, this.invMerchant.getMerchandise(i));
				
			}
		}
	}
}

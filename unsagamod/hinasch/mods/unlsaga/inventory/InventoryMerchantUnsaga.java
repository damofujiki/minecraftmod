package hinasch.mods.unlsaga.inventory;



import hinasch.lib.XYZPos;
import hinasch.mods.unlsaga.Unsaga;
import hinasch.mods.unlsaga.core.event.ExtendedMerchantData;
import hinasch.mods.unlsaga.misc.bartering.MerchandiseInfo;

import java.util.Random;

import net.minecraft.entity.IMerchant;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.village.Village;

public class InventoryMerchantUnsaga implements IInventory{

	protected String KEY = ExtendedMerchantData.VILLAGER;

	protected ItemStack[] bartering = new ItemStack[10];
	protected ItemStack[] merchandise = new ItemStack[10];
	protected EntityPlayer theCustomer;
	protected IMerchant theMerchant;
	protected EntityVillager villager;

	public InventoryMerchantUnsaga(EntityPlayer ep,IMerchant merchant){
		this.theCustomer = ep;
		this.theMerchant = merchant;
		//サーバー側だけEntityVillagerを保持
		if(this.theMerchant instanceof EntityVillager){
			this.villager = (EntityVillager)merchant;
		}else{
			this.villager = null;
		}


		//サーバー側だけ情報を保存・読み込み
		if(!ep.worldObj.isRemote){
			EntityVillager villager = (EntityVillager)merchant;
			if(villager.getExtendedProperties(ExtendedMerchantData.VILLAGER)!=null){
				ExtendedMerchantData data = (ExtendedMerchantData) villager.getExtendedProperties(ExtendedMerchantData.VILLAGER);

				if(!data.hasMerchandiceInitialized()){
					this.changeMerchandiseStock(villager.getRNG());
					data.recentPurchaseDate = villager.worldObj.getTotalWorldTime();
					data.markMerchandiceInitialized(true);
				}else{
					for(int i=0;i<9;++i){
						this.setMerchandise(i, data.getMerchantInventory(i));

					}
				}


				if((villager.worldObj.getTotalWorldTime() - data.recentPurchaseDate)>=24000){
					this.changeMerchandiseStock(villager.getRNG());
				}
			}
			
			
		}
		


	}
	
	public void changeMerchandiseStock(Random rand){
		for(int i=0;i<9;++i){
			EntityVillager villager = (EntityVillager)this.theMerchant;
			XYZPos po = XYZPos.entityPosToXYZ(villager);
			Village village = villager.worldObj.villageCollectionObj.findNearestVillage(po.x, po.y, po.z, 300);
			int repu = 1;
			if(village!=null){
				repu = village.getReputationForPlayer(this.theCustomer.getCommandSenderName());
			}
			ItemStack mis = MerchandiseInfo.getRandomMerchandise(rand,repu);
			MerchandiseInfo.setBuyPriceTag(mis,MerchandiseInfo.getPrice(mis)*3);
			this.setMerchandise(i, mis);

		}
	}
	
	public int getCurrentPriceToSell(){
		int price = 0;
		for(int i=0;i<9;i++){
			//途中
			//price = price + this.getBartering(i);
			if(this.getBartering(i)!=null){
				if(MerchandiseInfo.isPossibleToSell(this.getBartering(i))){
					price += MerchandiseInfo.getPrice(this.getBartering(i));

				}
			}
		}
		return price;
	}
	@Override
	public int getSizeInventory() {
		// TODO 自動生成されたメソッド・スタブ
		return this.bartering.length + this.merchandise.length;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		// TODO 自動生成されたメソッド・スタブ

		if(i>9){
			return this.merchandise[i-10];
		}
		if(i<=9){
			return this.bartering[i];
		}
		return null;
	}

	public ItemStack getMerchandise(int par1){
		return this.getStackInSlot(10+par1);					
	}
	public ItemStack getBartering(int par1){
		return this.getStackInSlot(par1);
	}

	public void setMerchandise(int par1,ItemStack is){

		this.setInventorySlotContents(10+par1,is);	

	}
	public void setBartering(int par1,ItemStack is){
		this.setInventorySlotContents(par1,is);
	}
	@Override
	public ItemStack decrStackSize(int i, int request) {
		// TODO 自動生成されたメソッド・スタブ
		Unsaga.debug("decr:"+i);
		if(i<=9){
			if(this.bartering[i]!=null){
				int stack = request;
				ItemStack is;
				if(this.bartering[i].stackSize<=request){
					is = this.bartering[i];
					this.bartering[i] = null;

					return is;
				}else{
					is = this.bartering[i].splitStack(request);
					if(this.bartering[i].stackSize == 0){
						this.bartering[i] = null;
					}

					return is;
				}

			}
		}
		if(i>9){
			int j = i-10;
			if(this.merchandise[j]!=null){
				
				
					ItemStack bought;
					bought = this.merchandise[j];
					this.merchandise[j] = null;
					
					return bought;
				

				
			}
		}
		return null;
	}

	public boolean canBuy(ItemStack is){
		int priceBuy = MerchandiseInfo.getPrice(is);
		int priceSell = this.getCurrentPriceToSell();
		if(priceSell>=priceBuy){
			return true;
		}
		return false;

	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		if(i<=9){
			if(this.bartering[i]!=null){
				ItemStack is = this.bartering[i];
				this.bartering[i] = null;
				return is;
			}
			return null;
		}
		return null;

	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		if(i<=9){
			this.bartering[i] = itemstack;

			if (itemstack != null && itemstack.stackSize > this.getInventoryStackLimit())
			{
				itemstack.stackSize = this.getInventoryStackLimit();
			}
		}
		if(i>9){
			

			this.merchandise[i-10] = itemstack;

			if (itemstack != null && itemstack.stackSize > this.getInventoryStackLimit())
			{
				itemstack.stackSize = this.getInventoryStackLimit();
			}

		}




	}

	@Override
	public String getInvName() {
		// TODO 自動生成されたメソッド・スタブ
		return "unsaga.merchant.inventory";
	}

	@Override
	public boolean isInvNameLocalized() {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		// TODO 自動生成されたメソッド・スタブ
		return 64;
	}

	@Override
	public void onInventoryChanged() {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		// TODO 自動生成されたメソッド・スタブ
		return this.theCustomer == entityplayer;
	}

	@Override
	public void openChest() {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void closeChest() {
		Unsaga.debug("データ書き込みました");
		if(!this.theCustomer.worldObj.isRemote){
			if(this.villager.getExtendedProperties(KEY)==null){
				ExtendedMerchantData newdata = new ExtendedMerchantData();
				for(int i=0;i<9;i++){
					newdata.setMerchantInventory(i, this.getMerchandise(i));
				}

				this.villager.registerExtendedProperties(KEY, newdata);
			}else{
				ExtendedMerchantData data = (ExtendedMerchantData) this.villager.getExtendedProperties(KEY);
				for(int i=0;i<9;i++){
					data.setMerchantInventory(i, this.getMerchandise(i));
				}
			}
		}

	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}

}

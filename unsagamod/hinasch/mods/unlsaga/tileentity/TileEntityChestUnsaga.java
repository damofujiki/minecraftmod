package hinasch.mods.unlsaga.tileentity;



import hinasch.mods.unlsaga.Unsaga;
import hinasch.mods.unlsaga.misc.translation.Translation;
import hinasch.mods.unlsaga.misc.util.HelperChestUnsaga;

import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.DamageSource;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;

public class TileEntityChestUnsaga extends TileEntityChest{

	private int spawnRange = 2;
	public boolean unlocked = true;
	public boolean defused = false;
	public boolean trapOccured = false;
	public boolean magicLock = false;
	public boolean hasItemSet = false;

	public TileEntityChestUnsaga(){

	}

	public void init(World world){

		if(this.doChance(world.rand, 60)){
			this.unlocked = false;
		}
		if(this.doChance(world.rand, 40)){
			this.magicLock = true;
			this.unlocked = true;
		}
	}

	public boolean touchChest(EntityPlayer ep){
		if(!trapOccured && !defused){
			tryDefuse(ep);
			if(!defused){
				activateTrap(ep);
				if(this.trapOccured){
					return false;
				}
			}
		}

		if(!unlocked){
			tryUnlock(ep);
			return false;
		}else{
			if(!magicLock){
				return true;
			}else{
				Unsaga.logc(ep, "chest has locked with Magical Lock.",false);
			}
			return false;
		}

	}



	private void tryUnlock(EntityPlayer par5EntityPlayer) {

		Unsaga.logc(par5EntityPlayer, "chest has locked.",false);
//		if(ItemAccessory.hasAbility(par5EntityPlayer, 8)){
		if(Unsaga.debug.get()){
			if(doChance(this.worldObj.rand,75)){
				Unsaga.logc(par5EntityPlayer, Translation.trJP("Success")+":"+Translation.trJP("Unlock"),false);
				this.unlocked = true;
			}
		}



	}

	private void activateTrap(EntityPlayer ep) {
		if(doChance(this.worldObj.rand,40)){
			switch(this.worldObj.rand.nextInt(3)+1){
			case 1:
				if(!this.worldObj.isRemote){
					this.trapOccured = true;
					this.worldObj.createExplosion(null, this.xCoord, this.yCoord, this.zCoord, 1.5F, true);
				}
				break;
			case 2:
				
				ep.addPotionEffect(new PotionEffect(Potion.poison.id,7*20,0));
				this.trapOccured = true;
				break;
			case 3:
				int damage = this.worldObj.rand.nextInt(4)+1;
				ep.attackEntityFrom(DamageSource.cactus, damage);
				this.trapOccured = true;
				break;
				
			}

		}else{
			this.trapOccured = true;
			Entity var13 = null;
			//			if(doChance(this.worldObj.rand,30)){
			//				var13 = new EntitySlime(this.worldObj);
			//			}else{
			var13 = new EntitySlime(this.worldObj);
			//			}

			System.out.println(var13);
			if(var13!=null){
				double var5 = (double)this.xCoord + (this.worldObj.rand.nextDouble() - this.worldObj.rand.nextDouble()) * (double)spawnRange;
				double var7 = (double)(this.yCoord+ this.worldObj.rand.nextInt(3) - 1);
				double var9 = (double)this.zCoord + (this.worldObj.rand.nextDouble() - this.worldObj.rand.nextDouble()) * (double)spawnRange;
				EntityLiving var11 = var13 instanceof EntityLiving ? (EntityLiving)var13 : null;
				var13.setLocationAndAngles(var5, var7, var9, this.worldObj.rand.nextFloat() * 360.0F, 0.0F);
				System.out.println(var13);
				//if(var11.getCanSpawnHere()){
				if(!this.worldObj.isRemote){
					this.worldObj.spawnEntityInWorld(var13);
				}
				//}
			}


		}

	}

	private void tryDefuse(EntityPlayer ep) {
//		if(ItemAccessory.hasAbility(ep, 9)){
		if(Unsaga.debug.get()){
			if(doChance(this.worldObj.rand,80)){
				this.defused = true;
				Unsaga.logc(ep, Translation.trJP("Success")+":"+Translation.trJP("Defuse"),false);
			}
		}

	}

	public void setItemsToChest(Random random){
		if(!this.hasItemSet){
			HelperChestUnsaga hc = new HelperChestUnsaga();
			WeightedRandomChestContent[] chestcontent = hc.getChestContentsUnsaga();
			if(chestcontent!=null){
				HelperChestUnsaga.generateChestContents(random, chestcontent, this, random.nextInt(5)+1);
			}
			this.hasItemSet = true;
		}
	}

	public void reductionChestContent(Random random){

		if(this!=null){
			for(int i=0;i<this.getSizeInventory();i++){
				ItemStack var1 = this.getStackInSlot(i);
				if(var1!=null){
					if(random.nextInt(5)==0){
						this.setInventorySlotContents(i, null);
					}
				}
			}
		}
		return;
	}

	public void reductionChestContent(Random random,int par1){

		if(this!=null){
			for(int i=0;i<this.getSizeInventory();i++){
				ItemStack var1 = this.getStackInSlot(i);
				if(var1!=null){
					if(random.nextInt(par1)==0){
						this.setInventorySlotContents(i, null);
					}
				}
			}
		}
		return;
	}

	private boolean doChance(Random random,int par1){
		if(random.nextInt(100)<=par1){
			return true;
		}
		return false;
	}

	@Override
	public void readFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.readFromNBT(par1NBTTagCompound);


		this.unlocked = par1NBTTagCompound.getBoolean("Unlocked");
		this.defused = par1NBTTagCompound.getBoolean("Defused");
		this.trapOccured = par1NBTTagCompound.getBoolean("TrapOccured");
		this.magicLock = par1NBTTagCompound.getBoolean("MagicalLock");
		this.hasItemSet = par1NBTTagCompound.getBoolean("HasItemSet");
	}

	@Override
	public void writeToNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.writeToNBT(par1NBTTagCompound);
		par1NBTTagCompound.setBoolean("Unlocked", (boolean)this.unlocked);
		par1NBTTagCompound.setBoolean("Defused", (boolean)this.defused);
		par1NBTTagCompound.setBoolean("TrapOccured", (boolean)this.trapOccured);
		par1NBTTagCompound.setBoolean("MagicalLock", (boolean)this.magicLock);
		par1NBTTagCompound.setBoolean("HasItemSet", (boolean)this.hasItemSet);

	}

	public boolean tryUnlockMagicalLock() {
		if(this.worldObj.rand.nextInt(100)<50){

			this.magicLock = false;
			return true;
		}else{
			return false;
		}

	}
}

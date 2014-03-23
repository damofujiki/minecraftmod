package hinasch.mods.unlsaga.tileentity;



import hinasch.lib.HSLibs;
import hinasch.lib.XYZPos;
import hinasch.mods.unlsaga.Unsaga;
import hinasch.mods.unlsaga.entity.EntityTreasureSlime;
import hinasch.mods.unlsaga.misc.ability.AbilityRegistry;
import hinasch.mods.unlsaga.misc.ability.HelperAbility;
import hinasch.mods.unlsaga.misc.translation.Translation;
import hinasch.mods.unlsaga.misc.util.ChatMessageHandler;
import hinasch.mods.unlsaga.misc.util.HelperChestUnsaga;
import hinasch.mods.unlsaga.network.packet.PacketParticle;

import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;

import com.google.common.base.Optional;



public class TileEntityChestUnsaga extends TileEntityChest{

	private int spawnRange = 2;
	private Optional<Integer> level = Optional.absent();
	public boolean unlocked = true;
	public boolean defused = false;
	public boolean trapOccured = false;
	public boolean magicLock = false;
	public boolean hasItemSet = false;

	public TileEntityChestUnsaga(){


	}

	public void init(World world){




		if(this.doChance(world.rand, 60)){
			//this.unlocked = false;
		}
		if(this.doChance(world.rand, 40)){
			//this.magicLock = true;
			//this.unlocked = true;
		}
		this.initChestLevel(world);
	}

	public void initChestLevel(World world){
		this.level = Optional.of(world.rand.nextInt(99)+1);

	}
	
	public boolean hasSetChestLevel(){
		return this.level.isPresent();
	}

	public boolean touchChest(EntityPlayer ep){
		if(this.level.isPresent()){
			return this.activateChest(ep);
		}else{
			if(this.worldObj.isRemote){

			}

		}

		return false;


	}

	public boolean activateChest(EntityPlayer ep){
		if(!this.worldObj.isRemote){
			ChatMessageHandler.sendChatToPlayer(ep,Translation.localize("msg.touch.chest"));
			//ep.addChatMessage(Translation.localize("msg.touch.chest"));
		}


		if(!trapOccured){
			if(!defused){
				activateTrap(ep);
				if(this.trapOccured){
					return false;
				}
			}
		}

		if(!unlocked){
			ChatMessageHandler.sendChatToPlayer(ep,Translation.localize("msg.chest.locked"));
			//ep.addChatMessage(Translation.localize("msg.chest.locked"));
			return false;
		}else{
			if(!magicLock){
				return true;
			}else{
				ChatMessageHandler.sendChatToPlayer(ep,Translation.localize("msg.chest.magiclocked"));
				//ep.addChatMessage(Translation.localize("msg.chest.magiclocked"));
				return false;
			}
			
		}
	}


	public void sync(boolean trap,boolean unlock,boolean defuse,boolean magiclock,boolean hasitemset){
		this.trapOccured = trap;
		this.unlocked = unlock;
		this.defused = defuse;
		this.magicLock = magiclock;
		this.hasItemSet = hasitemset;
	}
	public void tryUnlock(EntityPlayer par5EntityPlayer) {
		if(!this.trapOccured && !this.defused){
			this.activateTrap(par5EntityPlayer);
		}

		
		//		if(ItemAccessory.hasAbility(par5EntityPlayer, 8)){
		if(HelperAbility.hasAbilityLiving(par5EntityPlayer, AbilityRegistry.unlock)>0){
			if(doChance(this.worldObj.rand,75)){
				ChatMessageHandler.sendChatToPlayer(par5EntityPlayer, Translation.localize("msg.chest.unlocked"));
				this.unlocked = true;
			}else{
				ChatMessageHandler.sendChatToPlayer(par5EntityPlayer, Translation.localize("msg.failed"));
				//par5EntityPlayer.addChatMessage(Translation.localize("msg.failed"));
			}
		}



	}

	private void activateTrap(EntityPlayer ep) {
		if(doChance(this.worldObj.rand,40)){
			switch(this.worldObj.rand.nextInt(3)+1){
			case 1:
				this.occurTrapExplode(ep);
				break;
			case 2:

				ep.addPotionEffect(new PotionEffect(Potion.poison.id,10*(this.level.get()/2+1),1));
				ChatMessageHandler.sendChatToPlayer(ep, Translation.localize("msg.chest.poison"));
				this.trapOccured = true;
				break;
			case 3:
				int damage = this.worldObj.rand.nextInt(MathHelper.clamp_int(this.level.get()/15,3,100))+1;
				damage = MathHelper.clamp_int(damage, 1, 10);
				ep.attackEntityFrom(DamageSource.cactus, damage);
				ChatMessageHandler.sendChatToPlayer(ep, Translation.localize("msg.chest.needle"));
				this.trapOccured = true;
				break;

			}

		}
		this.trapOccured = true;

		this.occurSlimeTrap();

	}
	
	private void occurSlimeTrap(){
		Entity var13 = null;
		if(doChance(this.worldObj.rand,40)){
			var13 = new EntityTreasureSlime(this.worldObj,this.level.get());
		}else{
			var13 = new EntitySlime(this.worldObj);
		}

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

	private void occurTrapExplode(EntityPlayer ep){
		if(!this.worldObj.isRemote){
			this.trapOccured = true;
			float explv = ((float)this.level.get() * 0.06F);
			explv = MathHelper.clamp_float(explv, 1.0F, 4.0F);
			ChatMessageHandler.sendChatToPlayer(ep, Translation.localize("msg.chest.burst"));
			//ep.addChatMessage(Translation.localize("msg.chest.burst"));
			this.worldObj.createExplosion(null, this.xCoord, this.yCoord, this.zCoord, 1.5F*explv, true);
		}
	}
	

	public void tryDefuse(EntityPlayer ep) {
		//		if(ItemAccessory.hasAbility(ep, 9)){
		if(HelperAbility.hasAbilityLiving(ep, AbilityRegistry.defuse)>0){
			if(doChance(this.worldObj.rand,80)){
				this.defused = true;
				ChatMessageHandler.sendChatToPlayer(ep, Translation.localize("msg.chest.defused"));
				//ep.addChatMessage(Translation.localize("msg.chest.defused"));
			}
		}

	}



	public void setItemsToChest(Random random){
		if(!this.hasItemSet){
			HelperChestUnsaga hc = new HelperChestUnsaga(this.level.get());
			WeightedRandomChestContent[] chestcontent = hc.getChestContentsUnsaga();
//			if(chestcontent!=null){
//				HelperChestUnsaga.generateChestContents(random, chestcontent, this, random.nextInt(5)+1);
//			}
//
//			for(int i=0;i<this.getSizeInventory();i++){
//				if(this.getStackInSlot(i)!=null){
//					ItemStack is = this.getStackInSlot(i);
//					is.stackSize = 1;
//					this.setInventorySlotContents(i, is);
//				}
//			}

			this.hasItemSet = true;
		}
	}

	public void reductionChestContent(Random random){

		int lv = this.getChestLevel() / 2;
		lv = MathHelper.clamp_int(lv, 0, 100);
		this.setChestLevel(lv);
//		if(this!=null){
//			for(int i=0;i<this.getSizeInventory();i++){
//				ItemStack var1 = this.getStackInSlot(i);
//				if(var1!=null){
//					if(random.nextInt(5)==0){
//						this.setInventorySlotContents(i, null);
//					}
//				}
//			}
//		}
		return;
	}

	public void reductionChestContent(Random random,int par1){

    	HelperChestUnsaga hc = new HelperChestUnsaga(this.getChestLevel());
    	ItemStack is = hc.getChestItem(random);
    	if(!this.worldObj.isRemote){
    		if(is!=null){
    			HSLibs.dropItem(this.worldObj, is, xCoord, yCoord, zCoord);
    		}else{
    			//ChatMessageHandler.sendChatToPlayer(ep, Translation.localize("msg.chest.empty"));
    		}
    		this.worldObj.setBlockToAir(xCoord, yCoord, zCoord);
    	}
		return;
	}

	private boolean doChance(Random random,int par1){
		if(random.nextInt(100)<par1){
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
		if(par1NBTTagCompound.hasKey("chestLevel")){
			this.level = Optional.of(par1NBTTagCompound.getInteger("chestLevel"));
		}

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
		if(this.level.isPresent()){
			par1NBTTagCompound.setInteger("chestLevel",(int)this.level.get());
		}





	}

	public boolean tryUnlockMagicalLock() {
		if(this.worldObj.rand.nextInt(100)<50){

			this.magicLock = false;
			return true;
		}else{
			return false;
		}

	}

	public void setChestLevel(int chestlevel) {
		this.level = Optional.of(chestlevel);

	}

	public void divination(EntityPlayer openPlayer) {
		int div = HelperAbility.hasAbilityLiving(openPlayer, AbilityRegistry.divination);
		if(div>0){
			int lv =0;
			if(this.worldObj.rand.nextInt(100)<=50+(10*div)){
				ChatMessageHandler.sendChatToPlayer(openPlayer, Translation.localize("msg.chest.divination.succeeded"));
				lv = this.level.get() + worldObj.rand.nextInt(7)+1;

				
			}else{
				
				if(this.worldObj.rand.nextInt(10)<=2){
					ChatMessageHandler.sendChatToPlayer(openPlayer, Translation.localize("msg.chest.divination.catastrophe"));
					lv = 2;
				}else{
					ChatMessageHandler.sendChatToPlayer(openPlayer, Translation.localize("msg.chest.divination.failed"));
					lv = this.level.get() - worldObj.rand.nextInt(7)+1;
				}

				
			}
			this.setChestLevel(MathHelper.clamp_int(lv, 1, 100));
			String str = Translation.localize("msg.chest.divination.levelis");
			String formatted = String.format(str, this.level.get());
			ChatMessageHandler.sendChatToPlayer(openPlayer, Translation.localize(formatted));
			XYZPos xyz = new XYZPos(this.xCoord,this.yCoord,this.zCoord);
			PacketParticle pp = new PacketParticle(xyz,5,3);
			Unsaga.packetPipeline.sendTo(pp, (EntityPlayerMP) openPlayer);
		}
		
	}

	public int getChestLevel() {
		return this.level.get();
	}
	
    public boolean openChest(EntityPlayer ep)
    {
    	this.openInventory();
    	HelperChestUnsaga hc = new HelperChestUnsaga(this.getChestLevel());
    	ItemStack is = hc.getChestItem(this.worldObj.rand);
    	if(!this.worldObj.isRemote){
    		if(is!=null){
    			HSLibs.dropItem(this.worldObj, is, xCoord, yCoord, zCoord);
    		}else{
    			ChatMessageHandler.sendChatToPlayer(ep, Translation.localize("msg.chest.empty"));
    		}
    		//this.worldObj.setBlockToAir(xCoord, yCoord, zCoord);
    	}
    	
//        if (this.worldObj.isRemote)
//        {
//            return true;
//        }
//        else
//        {
//            IInventory iinventory = this;
//            if (iinventory != null)
//            {
//                ep.displayGUIChest(iinventory);
//            }
//
//            return true;
//        }
    	return true;
    }


}

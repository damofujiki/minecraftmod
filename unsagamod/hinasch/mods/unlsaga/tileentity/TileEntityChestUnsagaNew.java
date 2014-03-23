package hinasch.mods.unlsaga.tileentity;

import hinasch.lib.HSLibs;
import hinasch.mods.unlsaga.Unsaga;
import hinasch.mods.unlsaga.entity.EntityTreasureSlime;
import hinasch.mods.unlsaga.misc.translation.Translation;
import hinasch.mods.unlsaga.misc.util.ChatMessageHandler;
import hinasch.mods.unlsaga.misc.util.HelperChestUnsaga;

import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class TileEntityChestUnsagaNew extends TileEntityChest{

	private int spawnRange = 2;
	protected int level;
	protected int trapNumber = 0;
	protected boolean unlocked = true;
	//protected boolean defused;
	protected boolean slimeTrapOccured = false;
	protected boolean trapOccured = true;
	protected boolean magicLock = false;
	protected boolean hasOpened = false;
	protected boolean chestInitialized;

	public TileEntityChestUnsagaNew(){
		//		if(!this.initialized){
		//			this.unlocked = true;
		//			//this.defused = false;
		//			this.trapOccured = true;
		//			this.magicLock = false;
		//			this.hasOpened = false;
		//			this.slimeTrapOccured = false;
		//			this.level = 0;
		//			this.trapNumber = 0;
		//		}

		Unsaga.debug("つくられました");
	}


	public void initChest(World world){







		if(this.hasOpened){
			this.lidAngle = 1.0F;
		}

		Unsaga.debug(this.chestInitialized+":初期化");
		this.initChestLevel(world);
		if(this.getChestLevel()>20){
			if(this.doChance(world.rand, 60)){
				this.unlocked = false;
			}
			if(this.doChance(world.rand, 40)){
				this.magicLock = true;
			}
			if(this.doChance(world.rand,30)){
				this.trapOccured = true;
			}else{
				this.trapOccured = false;
			}
			if(this.doChance(world.rand,20)){
				this.slimeTrapOccured = true;
			}
			this.trapNumber = world.rand.nextInt(3);

		}


		this.chestInitialized = true;
	}

	//	@Override
	//    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
	//    {
	//		
	//		Unsaga.debug("きてる");
	//		if(this.hasOpened){
	//			this.lidAngle = 1.0F;
	//		}
	//    }
	//	

	//    public void updateEntity()
	//    {
	//    	super.updateEntity();
	//    	if(this.hasOpened){
	//    		this.lidAngle = 1.0F;
	//    		this.numPlayersUsing = 1;
	//    	}
	//    }


	public boolean hasInitialized(){
		return this.chestInitialized;
	}
	public boolean isUnlocked(){
		return this.unlocked;
	}

	public boolean isMagicLocked(){
		return this.magicLock;
	}

	public boolean isTrapOccured(){
		return this.trapOccured;
	}

	public boolean hasChestOpened(){
		return this.hasOpened;
	}

	public int getChestLevel(){
		return this.level;
	}

	public void setUnlocked(boolean par1){
		this.unlocked = par1;
	}

	public void setMagicLock(boolean par1){
		this.magicLock = par1;
	}

	public void setDefused(boolean par1){
		this.trapOccured = true;
	}

	public void reductionChestLevel(){
		this.level = (int)((float)this.level * 0.5F);

	}

	public void setChestLevel(int par1){
		this.level = par1;
	}

	public boolean doChance(Random random,int par1){
		if(random.nextInt(100)<par1){
			return true;
		}
		return false;
	}

	public boolean doChance(int par1){
		if(this.worldObj.rand.nextInt(100)<par1){
			return true;
		}
		return false;
	}

	public void initChestLevel(World world){
		this.level = world.rand.nextInt(99)+1;

	}

	@Override
	public Packet getDescriptionPacket() {

		NBTTagCompound nbttagcompound = new NBTTagCompound();
		this.writeToNBT(nbttagcompound);
		return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 0, nbttagcompound);
	}



	public void activateChest(EntityPlayer ep){
		if(!this.hasOpened){
			ChatMessageHandler.sendChatToPlayer(ep,Translation.localize("msg.touch.chest"));
			if(!this.trapOccured){
				this.activateTrap(ep);
			}
			if(this.unlocked){
				if(!this.magicLock){
					this.openChest(ep);
				}else{
					ChatMessageHandler.sendChatToPlayer(ep,Translation.localize("msg.chest.magiclocked"));
				}
			}else{
				ChatMessageHandler.sendChatToPlayer(ep,Translation.localize("msg.chest.locked"));
			}
		}




		//宝箱は空
	}

	private void openChest(EntityPlayer ep) {
		this.openInventory();
		this.obtainItem();
	}


	public void obtainItem() {
		this.hasOpened = true;
		HelperChestUnsaga hc = new HelperChestUnsaga(this.level);
		ItemStack is = hc.getChestItem(this.worldObj.rand);
		//ぬるぽの可能性が…？
		if(!this.worldObj.isRemote && is!=null){
			HSLibs.dropItem(worldObj, is, xCoord, yCoord, zCoord);
		}

	}


	public void activateTrap(EntityPlayer ep) {
		//if(doChance(this.worldObj.rand,40)){
		switch(this.trapNumber){
		case 0:
			this.occurTrapExplode(ep);
			this.trapOccured = true;
			break;
		case 1:

			ep.addPotionEffect(new PotionEffect(Potion.poison.id,10*(this.level/2+1),1));
			ChatMessageHandler.sendChatToPlayer(ep, Translation.localize("msg.chest.poison"));
			this.trapOccured = true;
			break;
		case 2:
			int damage = this.worldObj.rand.nextInt(MathHelper.clamp_int(this.level/15,3,100))+1;
			damage = MathHelper.clamp_int(damage, 1, 10);
			ep.attackEntityFrom(DamageSource.cactus, damage);
			ChatMessageHandler.sendChatToPlayer(ep, Translation.localize("msg.chest.needle"));
			//this.trapOccured = true;
			break;

		}

		//}


		if(!this.slimeTrapOccured){
			this.occurSlimeTrap();
		}


	}

	private void occurSlimeTrap(){
		this.slimeTrapOccured = true;
		Entity var13 = null;
		if(doChance(this.worldObj.rand,40)){
			var13 = new EntityTreasureSlime(this.worldObj,this.level);
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

			float explv = ((float)this.level * 0.06F);
			explv = MathHelper.clamp_float(explv, 1.0F, 4.0F);
			ChatMessageHandler.sendChatToPlayer(ep, Translation.localize("msg.chest.burst"));
			this.worldObj.createExplosion(null, this.xCoord, this.yCoord, this.zCoord, 1.5F*explv, true);
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.readFromNBT(par1NBTTagCompound);



		this.unlocked = par1NBTTagCompound.getBoolean("Unlocked");
		//this.defused = par1NBTTagCompound.getBoolean("Defused");
		this.trapOccured = par1NBTTagCompound.getBoolean("TrapOccured");
		this.magicLock = par1NBTTagCompound.getBoolean("MagicalLock");
		this.hasOpened = par1NBTTagCompound.getBoolean("HasOpened");
		this.level = par1NBTTagCompound.getInteger("chestLevel");
		this.slimeTrapOccured = par1NBTTagCompound.getBoolean("slimeTrap");

		this.chestInitialized = par1NBTTagCompound.getBoolean("initializedChest");




		Unsaga.debug("読み込まれました");
	}

	@Override
	public void writeToNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.writeToNBT(par1NBTTagCompound);
		par1NBTTagCompound.setBoolean("Unlocked", (boolean)this.unlocked);
		//par1NBTTagCompound.setBoolean("Defused", (boolean)this.defused);
		par1NBTTagCompound.setBoolean("TrapOccured", (boolean)this.trapOccured);
		par1NBTTagCompound.setBoolean("MagicalLock", (boolean)this.magicLock);
		par1NBTTagCompound.setBoolean("HasOpened", (boolean)this.hasOpened);
		par1NBTTagCompound.setInteger("chestLevel",(int)this.level);
		par1NBTTagCompound.setBoolean("slimeTrap", this.slimeTrapOccured);

		par1NBTTagCompound.setBoolean("initializedChest", this.chestInitialized);








	}


	public int getTrapNumber() {
		// TODO 自動生成されたメソッド・スタブ
		return this.trapNumber;
	}
	
	public boolean hasOccuredSlimeTrap(){
		return this.slimeTrapOccured;
	}
}

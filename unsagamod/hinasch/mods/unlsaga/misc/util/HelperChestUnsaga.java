package hinasch.mods.unlsaga.misc.util;

import hinasch.lib.XYZPos;
import hinasch.mods.unlsaga.Unsaga;
import hinasch.mods.unlsaga.core.init.NoFuncItemList;
import hinasch.mods.unlsaga.core.init.UnsagaConfigs;
import hinasch.mods.unlsaga.core.init.UnsagaMaterial;
import hinasch.mods.unlsaga.core.init.UnsagaMaterials;
import hinasch.mods.unlsaga.misc.ability.AbilityRegistry;
import hinasch.mods.unlsaga.misc.ability.HelperAbility;
import hinasch.mods.unlsaga.misc.module.HookUnsagaMagic;
import hinasch.mods.unlsaga.misc.translation.Translation;
import hinasch.mods.unlsaga.network.packet.PacketParticle;
import hinasch.mods.unlsaga.tileentity.TileEntityChestUnsagaNew;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.WeightedRandomChestContent;

public class HelperChestUnsaga {

	public List<WeightedRandomChestContent> chestContents;
	public int chestLevel;
	public HookUnsagaMagic hook;
	public TileEntityChestUnsagaNew tileEntityChest;
	
	public HelperChestUnsaga(int chestLevel){
		this.chestContents = new ArrayList();
		this.chestLevel = chestLevel;
		if(UnsagaConfigs.module.isMagicEnabled())this.hook = new HookUnsagaMagic();

		this.buildContents();
	}
	

	public HelperChestUnsaga(TileEntityChestUnsagaNew chest){
		this.tileEntityChest = chest;
	}
	
	protected void buildContents(){
		for(Iterator<UnsagaMaterial> ite=UnsagaMaterials.allMaterialMap.values().iterator();ite.hasNext();){
			UnsagaMaterial us = ite.next();
			if(us.getAssociatedItem().isPresent()){
				ItemStack is = us.getAssociatedItem().get();
				//Unsaga.debug(us.name+" is try to add as chestcontents");
				//ItemStack is = NoFuncItemList.getItemStack(1, us.getItemMeta());
				//Unsaga.debug(us.rank);
				addChestContent(is,us.rank);
			}
		}
		for(NoFuncItem item:NoFuncItemList.getList().values()){
			UnsagaMaterial us = item.associated;
			ItemStack is = NoFuncItemList.getItemStack(1, item.number);
			addChestContent(is,us.rank);
		}
		if(UnsagaConfigs.module.isMagicEnabled()){
			ItemStack is = this.hook.getUnsagaMagicItem();
			addChestContent(is,5);
		}
		
	}
	
	protected void addChestContent(ItemStack is,int rank){
		int var1 = (10-rank)*(this.chestLevel/2+1);
		int var2 = var1/3;
		var1 = MathHelper.clamp_int(var1, 1, 200);
		var2 = MathHelper.clamp_int(var1, 1, 10);
		if(rank>this.chestLevel/8)return;
		Unsaga.debug("add:"+is+":rank>"+rank);
		this.chestContents.add(new WeightedRandomChestContent(is,1,var2,var1));
	}
	
//	public WeightedRandomChestContent getChestContentFromItemStack(ItemStack par1){
//		WeightedRandomChestContent rt = null;
//		for(int i=0;i<this.tmp.length;i++){
//			if(tmp[i].theItemId == par1){
//				rt = tmp[i];
//			}
//			
//		}
//		return rt;
//	}
	
	
	public ItemStack getChestItem(Random rand){
		if(!this.chestContents.isEmpty()){
			WeightedRandomChestContent getItem = (WeightedRandomChestContent) WeightedRandom.getRandomItem(rand, this.chestContents);
			
			return getItem.theItemId;//this.chestContents.get(rand.nextInt(this.chestContents.size()));
		}
		return null;
	}
	
	public WeightedRandomChestContent[] getChestContentsUnsaga(){

		if(!this.chestContents.isEmpty()){
			Unsaga.debug("parsed Hashset to Array.");
			return this.chestContents.toArray(new WeightedRandomChestContent[this.chestContents.size()]);
			
		}else{
			return null;
		}
		
	}
	
	
	public void tryDefuse(EntityPlayer ep) {
		if(HelperAbility.hasAbilityLiving(ep, AbilityRegistry.defuse)>0){
			if(this.tileEntityChest.doChance(ep.getRNG(),80)){
				this.tileEntityChest.setDefused(true);
				ChatMessageHandler.sendChatToPlayer(ep, Translation.localize("msg.chest.defused"));
				
			}else{
				this.tileEntityChest.activateTrap(ep);
			}
		}

	}
	
	public boolean tryUnlockMagicalLock() {
		if(this.tileEntityChest.doChance(50)){

			this.tileEntityChest.setMagicLock(false);
			return true;
		}else{
			return false;
		}

	}
	
	public void tryUnlock(EntityPlayer par5EntityPlayer) {
		if(!this.tileEntityChest.isTrapOccured()){
			this.tileEntityChest.activateTrap(par5EntityPlayer);
		}

		if(HelperAbility.hasAbilityLiving(par5EntityPlayer, AbilityRegistry.unlock)>0){
			if(this.tileEntityChest.doChance(75)){
				ChatMessageHandler.sendChatToPlayer(par5EntityPlayer, Translation.localize("msg.chest.unlocked"));
				this.tileEntityChest.setUnlocked(true);
			}else{
				ChatMessageHandler.sendChatToPlayer(par5EntityPlayer, Translation.localize("msg.failed"));
			}
		}



	}
	
	public void divination(EntityPlayer openPlayer) {
		Random rand = openPlayer.getRNG();
		int div = HelperAbility.hasAbilityLiving(openPlayer, AbilityRegistry.divination);
		if(div>0){
			int lv =0;
			if(rand.nextInt(100)<=50+(10*div)){
				ChatMessageHandler.sendChatToPlayer(openPlayer, Translation.localize("msg.chest.divination.succeeded"));
				lv = this.tileEntityChest.getChestLevel() + rand.nextInt(7)+1;

				
			}else{
				
				if(rand.nextInt(10)<=2){
					ChatMessageHandler.sendChatToPlayer(openPlayer, Translation.localize("msg.chest.divination.catastrophe"));
					lv = 2;
				}else{
					ChatMessageHandler.sendChatToPlayer(openPlayer, Translation.localize("msg.chest.divination.failed"));
					lv = this.tileEntityChest.getChestLevel() - rand.nextInt(7)+1;
				}

				
			}
			this.tileEntityChest.setChestLevel(MathHelper.clamp_int(lv, 1, 100));
			String str = Translation.localize("msg.chest.divination.levelis");
			String formatted = String.format(str, this.tileEntityChest.getChestLevel());
			ChatMessageHandler.sendChatToPlayer(openPlayer, Translation.localize(formatted));
			XYZPos xyz = XYZPos.tileEntityPosToXYZ(tileEntityChest);
			PacketParticle pp = new PacketParticle(xyz,5,3);
			Unsaga.packetPipeline.sendTo(pp, (EntityPlayerMP) openPlayer);
		}
		
	}
//	
//    public static void generateChestContents(Random par0Random, WeightedRandomChestContent[] par1ArrayOfWeightedRandomChestContent, IInventory par2IInventory, int par3)
//    {
//        for (int j = 0; j < par3; ++j)
//        {
//            WeightedRandomChestContent weightedrandomchestcontent = (WeightedRandomChestContent)WeightedRandom.getRandomItem(par0Random, par1ArrayOfWeightedRandomChestContent);
//            int k = weightedrandomchestcontent.theMinimumChanceToGenerateItem + par0Random.nextInt(weightedrandomchestcontent.theMaximumChanceToGenerateItem - weightedrandomchestcontent.theMinimumChanceToGenerateItem + 1);
//
//            ItemStack[] stacks = ChestGenHooks.generateStacks(par0Random, weightedrandomchestcontent.theItemId, weightedrandomchestcontent.theMinimumChanceToGenerateItem, weightedrandomchestcontent.theMaximumChanceToGenerateItem);
//
//            
//            for (ItemStack item : stacks)
//            {
//            	if(item!=null){
////            		if(item.getItem() instanceof ItemMagicTablet){
////            			ItemMagicTablet.doActivateMagicTablet(par0Random, item);
////            		}
////            		if(item.getItem() instanceof ItemAccessory){
////            			int id = par0Random.nextInt(ItemAccessory.abilities.length);
////            			if(par0Random.nextInt(5)<2){
////            				UtilSkill.setAbility(item, 0, id);
////            			}
////            			
////            		}
//            	}
//                par2IInventory.setInventorySlotContents(par0Random.nextInt(par2IInventory.getSizeInventory()), item);
//            }
//        }
//    }
    
//    public static int getChestLevelFromPlayer(EntityPlayer ep){
//    	int lv = ep.experienceLevel *3;
//    	lv += ep.getRNG().nextInt(15)-10;
//    	lv = MathHelper.clamp_int(lv, 1, 100);
//    	return lv;
//    }
}

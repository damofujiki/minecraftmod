package hinasch.mods.unlsaga.misc.util;

import hinasch.mods.unlsaga.Unsaga;
import hinasch.mods.unlsaga.core.init.UnsagaMaterials;
import hinasch.mods.unlsaga.core.init.NoFuncItemList;
import hinasch.mods.unlsaga.core.init.UnsagaConfigs;
import hinasch.mods.unlsaga.core.init.UnsagaMaterial;
import hinasch.mods.unlsaga.misc.module.HookUnsagaMagic;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.common.ChestGenHooks;

public class HelperChestUnsaga {

	public HashSet<WeightedRandomChestContent> chestContents;
	public int chestLevel;
	public HookUnsagaMagic hook;
	
	public HelperChestUnsaga(int chestLevel){
		this.chestContents = new HashSet();
		this.chestLevel = chestLevel;
		if(UnsagaConfigs.module.isMagicEnabled())this.hook = new HookUnsagaMagic();

		this.buildContents();
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
			addChestContent(is,8);
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
	
	public WeightedRandomChestContent[] getChestContentsUnsaga(){

		if(!this.chestContents.isEmpty()){
			Unsaga.debug("parsed Hashset to Array.");
			return this.chestContents.toArray(new WeightedRandomChestContent[this.chestContents.size()]);
			
		}else{
			return null;
		}
		
	}
	
    public static void generateChestContents(Random par0Random, WeightedRandomChestContent[] par1ArrayOfWeightedRandomChestContent, IInventory par2IInventory, int par3)
    {
        for (int j = 0; j < par3; ++j)
        {
            WeightedRandomChestContent weightedrandomchestcontent = (WeightedRandomChestContent)WeightedRandom.getRandomItem(par0Random, par1ArrayOfWeightedRandomChestContent);
            int k = weightedrandomchestcontent.theMinimumChanceToGenerateItem + par0Random.nextInt(weightedrandomchestcontent.theMaximumChanceToGenerateItem - weightedrandomchestcontent.theMinimumChanceToGenerateItem + 1);

            ItemStack[] stacks = ChestGenHooks.generateStacks(par0Random, weightedrandomchestcontent.theItemId, weightedrandomchestcontent.theMinimumChanceToGenerateItem, weightedrandomchestcontent.theMaximumChanceToGenerateItem);

            
            for (ItemStack item : stacks)
            {
            	if(item!=null){
//            		if(item.getItem() instanceof ItemMagicTablet){
//            			ItemMagicTablet.doActivateMagicTablet(par0Random, item);
//            		}
//            		if(item.getItem() instanceof ItemAccessory){
//            			int id = par0Random.nextInt(ItemAccessory.abilities.length);
//            			if(par0Random.nextInt(5)<2){
//            				UtilSkill.setAbility(item, 0, id);
//            			}
//            			
//            		}
            	}
                par2IInventory.setInventorySlotContents(par0Random.nextInt(par2IInventory.getSizeInventory()), item);
            }
        }
    }
    
    public static int getChestLevelFromPlayer(EntityPlayer ep){
    	int lv = ep.experienceLevel *3;
    	lv += ep.getRNG().nextInt(15)-10;
    	lv = MathHelper.clamp_int(lv, 1, 100);
    	return lv;
    }
}

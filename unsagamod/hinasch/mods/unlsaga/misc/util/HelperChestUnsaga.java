package hinasch.mods.unlsaga.misc.util;

import hinasch.mods.unlsaga.Unsaga;
import hinasch.mods.unlsaga.core.init.MaterialList;
import hinasch.mods.unlsaga.core.init.NoFuncItemList;
import hinasch.mods.unlsaga.core.init.UnsagaMaterial;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.common.ChestGenHooks;

public class HelperChestUnsaga {

	public HashSet<WeightedRandomChestContent> chestContents;
	
	public HelperChestUnsaga(){
		this.chestContents = new HashSet();

		this.buildContents();
	}
	
	protected void buildContents(){
		for(Iterator<UnsagaMaterial> ite=MaterialList.allMaterialMap.values().iterator();ite.hasNext();){
			UnsagaMaterial us = ite.next();
			if(us.itemMeta.isPresent()){
				Unsaga.debug(us.name+" is try to add as chestcontents");
				ItemStack is = NoFuncItemList.getItemStack(1, us.getItemMeta());
				Unsaga.debug(us.rank);
				addChestContent(is,us.rank);
			}
		}
		
	}
	
	protected void addChestContent(ItemStack is,int rank){
		if(rank>5)return;
		Unsaga.debug("add:"+is+":rank>"+rank);
		switch(rank){
		case 1:
			this.chestContents.add(new WeightedRandomChestContent(is,1,4,50));
			break;
		case 2:
			this.chestContents.add(new WeightedRandomChestContent(is,1,4,30));
			break;
		case 3:
			this.chestContents.add(new WeightedRandomChestContent(is,1,2,50));
			break;
		case 4:
			this.chestContents.add(new WeightedRandomChestContent(is,1,2,25));
			break;
		case 5:
			this.chestContents.add(new WeightedRandomChestContent(is,1,2,15));
			break;
		default:
			this.chestContents.add(new WeightedRandomChestContent(is,1,4,50));
			break;
		}
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
}

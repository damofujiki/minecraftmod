package com.hinasch.lib;
//package hinasch.lib;
//
//import hinasch.mods.unlsaga.core.init.UnsagaMaterial;
//import hinasch.mods.unlsaga.misc.util.HelperUnsagaWeapon;
//import hinasch.mods.unlsaga.misc.util.IUnsagaMaterial;
//
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//import java.util.Random;
//
//import net.minecraft.enchantment.Enchantment;
//import net.minecraft.enchantment.EnchantmentData;
//import net.minecraft.enchantment.EnchantmentHelper;
//import net.minecraft.item.Item;
//import net.minecraft.item.ItemStack;
//import net.minecraft.util.WeightedRandom;
//
//public class ModifiedEnchantment extends EnchantmentHelper{
//	
//    public static ItemStack addRandomEnchantment(Random par0Random, ItemStack par1ItemStack, int par2)
//    {
//        List list = buildEnchantmentList(par0Random, par1ItemStack, par2);
//        boolean flag = par1ItemStack.itemID == Item.book.itemID;
//
//        if (flag)
//        {
//            par1ItemStack.itemID = Item.enchantedBook.itemID;
//        }
//
//        if (list != null)
//        {
//            Iterator iterator = list.iterator();
//
//            while (iterator.hasNext())
//            {
//                EnchantmentData enchantmentdata = (EnchantmentData)iterator.next();
//
//                if (flag)
//                {
//                    Item.enchantedBook.addEnchantment(par1ItemStack, enchantmentdata);
//                }
//                else
//                {
//                    par1ItemStack.addEnchantment(enchantmentdata.enchantmentobj, enchantmentdata.enchantmentLevel);
//                }
//            }
//        }
//
//        return par1ItemStack;
//    }
//    
//    public static List buildEnchantmentList(Random par0Random, ItemStack par1ItemStack, int par2)
//    {
//        Item item = par1ItemStack.getItem();
//        
//        //バニラのエンチャントが引数なし縛りなので…
//        int j = 0;
//        if(item instanceof IUnsagaMaterial){
//        	UnsagaMaterial us = HelperUnsagaWeapon.getMaterial(par1ItemStack);
//        	j = us.getToolMaterial().getEnchantability();
//        }else{
//        	j = item.getItemEnchantability();
//        }
//       
//        
//
//        if (j <= 0)
//        {
//            return null;
//        }
//        else
//        {
//            j /= 2;
//            j = 1 + par0Random.nextInt((j >> 1) + 1) + par0Random.nextInt((j >> 1) + 1);
//            int k = j + par2;
//            float f = (par0Random.nextFloat() + par0Random.nextFloat() - 1.0F) * 0.15F;
//            int l = (int)((float)k * (1.0F + f) + 0.5F);
//
//            if (l < 1)
//            {
//                l = 1;
//            }
//
//            ArrayList arraylist = null;
//            Map map = mapEnchantmentData(l, par1ItemStack);
//
//            if (map != null && !map.isEmpty())
//            {
//                EnchantmentData enchantmentdata = (EnchantmentData)WeightedRandom.getRandomItem(par0Random, map.values());
//
//                if (enchantmentdata != null)
//                {
//                    arraylist = new ArrayList();
//                    arraylist.add(enchantmentdata);
//
//                    for (int i1 = l; par0Random.nextInt(50) <= i1; i1 >>= 1)
//                    {
//                        Iterator iterator = map.keySet().iterator();
//
//                        while (iterator.hasNext())
//                        {
//                            Integer integer = (Integer)iterator.next();
//                            boolean flag = true;
//                            Iterator iterator1 = arraylist.iterator();
//
//                            while (true)
//                            {
//                                if (iterator1.hasNext())
//                                {
//                                    EnchantmentData enchantmentdata1 = (EnchantmentData)iterator1.next();
//
//                                    if (enchantmentdata1.enchantmentobj.canApplyTogether(Enchantment.enchantmentsList[integer.intValue()]))
//                                    {
//                                        continue;
//                                    }
//
//                                    flag = false;
//                                }
//
//                                if (!flag)
//                                {
//                                    iterator.remove();
//                                }
//
//                                break;
//                            }
//                        }
//
//                        if (!map.isEmpty())
//                        {
//                            EnchantmentData enchantmentdata2 = (EnchantmentData)WeightedRandom.getRandomItem(par0Random, map.values());
//                            arraylist.add(enchantmentdata2);
//                        }
//                    }
//                }
//            }
//
//            return arraylist;
//        }
//    }
//}

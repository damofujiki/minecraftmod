package hinasch.mods.unlsagamagic.misc.element;

import hinasch.lib.ScanHelper;
import hinasch.lib.XYZPos;
import hinasch.mods.unlsaga.Unsaga;
import hinasch.mods.unlsaga.core.FiveElements;
import hinasch.mods.unlsaga.misc.ability.AbilityRegistry;
import hinasch.mods.unlsaga.misc.ability.HelperAbility;
import hinasch.mods.unlsaga.misc.debuff.Debuffs;
import hinasch.mods.unlsaga.misc.debuff.livingdebuff.LivingDebuff;
import hinasch.mods.unlsagamagic.UnsagaMagic;
import hinasch.mods.unlsagamagic.misc.spell.SpellMixTable;

import java.util.Set;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.oredict.OreDictionary;

import com.google.common.collect.Sets;

public class UnsagaElement {
	protected SpellMixTable worldElementTable;


	public UnsagaElement(){
		this.worldElementTable = new SpellMixTable();
	}

	public SpellMixTable getWorldElements(){
		return this.worldElementTable;
	}
	public void figureElements(World world,EntityLivingBase ep){
		this.worldElementTable.reset();
		this.figureFromWorld(world, ep);
		this.figureFromBiomeAndWeather(world, ep);
		this.figureFromEquipment(ep);
		this.figureFromCurrentHeight(world, ep);
		//this.worldElementTable.multiple(0.01F);
		this.figureFromBuff(ep);
		this.worldElementTable.cut(0, 100);


		//this.worldElementTable.cut(0, 100);
	}

	private void figureFromBuff(EntityLivingBase ep) {
		if(LivingDebuff.hasDebuff(ep, Debuffs.fireVeil))this.worldElementTable.add(FiveElements.EnumElement.FIRE,15.0F);
		if(LivingDebuff.hasDebuff(ep, Debuffs.woodVeil))this.worldElementTable.add(FiveElements.EnumElement.WOOD,15.0F);
		if(LivingDebuff.hasDebuff(ep, Debuffs.waterVeil))this.worldElementTable.add(FiveElements.EnumElement.WATER,15.0F);
		if(LivingDebuff.hasDebuff(ep, Debuffs.earthVeil))this.worldElementTable.add(FiveElements.EnumElement.EARTH,15.0F);
		if(LivingDebuff.hasDebuff(ep, Debuffs.metalVeil))this.worldElementTable.add(FiveElements.EnumElement.METAL,15.0F);
	}

	protected void figureFromWorld(World world,EntityLivingBase ep){
		XYZPos epos = XYZPos.entityPosToXYZ(ep);
		ScanHelper scanner = new ScanHelper(ep,10,8);
		boolean flag = false;
		scanner.setWorld(world);
		for(;scanner.hasNext();scanner.next()){
			flag = false;

			if(!scanner.isAirBlock() && scanner.sy>0){

				if(scanner.getBlock() instanceof IUnsagaElements){
					IUnsagaElements iu = (IUnsagaElements)scanner.getBlock();
					SpellMixTable table = iu.getElements();
					this.worldElementTable.add(table);
					flag = true;
				}
				if(UnsagaMagic.elementLibrary.find(scanner.getBlock()).isPresent() && !flag){
					ElementLibraryBook book = (ElementLibraryBook) UnsagaMagic.elementLibrary.find(scanner.getBlock()).get();
					SpellMixTable table = book.table;
					//Unsaga.debug("block:"+table);
					this.worldElementTable.add(table);
					flag = true;
				}
//				Material material = world.getBlockMaterial(scanner.sx, scanner.sy, scanner.sz);
//				if(UnsagaMagic.elementLibrary.findMaterial(material).isPresent() && !flag){
//					SpellMixTable table = UnsagaMagic.elementLibrary.findMaterial(material).get();
//					this.worldElementTable.add(table);
//					//Unsaga.debug("material:"+table);
//					flag = true;
//				}
//				Class classBlock = Block.blocksList[scanner.getID()].getClass();
//				if(UnsagaMagic.elementLibrary.findClass(classBlock).isPresent() && !flag){
//					SpellMixTable table = UnsagaMagic.elementLibrary.findClass(classBlock).get();
//					//nsaga.debug("class:"+table);
//					this.worldElementTable.add(table);
//					flag = true;
//				}
				ItemStack is = new ItemStack(scanner.getBlock(),1);
				if(is!=null){
					String orekey = OreDictionary.getOreName(OreDictionary.getOreID(is));
					if(orekey.contains("ore")){
						this.worldElementTable.add(FiveElements.EnumElement.METAL,0.1F);
						Unsaga.debug("ore");
					}
				}
				//scanner.setBlockHere(Block.blockClay.blockID);
			}
		}


	}

	protected void figureFromBiomeAndWeather(World world,EntityLivingBase ep){
		BiomeGenBase biomegen = world.getBiomeGenForCoords((int)ep.posX, (int)ep.posZ);
		Set<BiomeDictionary.Type> biomeTypes = Sets.newHashSet(BiomeDictionary.getTypesForBiome(biomegen));

		if(biomeTypes.contains(BiomeDictionary.Type.BEACH)){
			this.worldElementTable.add(FiveElements.EnumElement.WATER, 10);
		}
		if(biomeTypes.contains(BiomeDictionary.Type.DESERT)){
			this.worldElementTable.add(FiveElements.EnumElement.FIRE, 10);
			this.worldElementTable.add(FiveElements.EnumElement.WATER, -10);
		}
		if(biomeTypes.contains(BiomeDictionary.Type.FOREST)){
			this.worldElementTable.add(FiveElements.EnumElement.WOOD, 10);
		}
		if(biomeTypes.contains(BiomeDictionary.Type.FROZEN)){
			this.worldElementTable.add(FiveElements.EnumElement.WATER, 10);
		}
		if(biomeTypes.contains(BiomeDictionary.Type.MAGICAL)){
			this.worldElementTable.add(FiveElements.EnumElement.FORBIDDEN, 10);
		}
		if(biomeTypes.contains(BiomeDictionary.Type.WASTELAND)){
			this.worldElementTable.add(FiveElements.EnumElement.EARTH, 20);
		}
		if(biomeTypes.contains(BiomeDictionary.Type.HILLS)){
			this.worldElementTable.add(FiveElements.EnumElement.EARTH, 10);
		}
		if(biomeTypes.contains(BiomeDictionary.Type.MOUNTAIN)){
			this.worldElementTable.add(FiveElements.EnumElement.EARTH, 10);
		}
		if(biomeTypes.contains(BiomeDictionary.Type.JUNGLE)){
			this.worldElementTable.add(FiveElements.EnumElement.WOOD, 10);
		}
		if(biomeTypes.contains(BiomeDictionary.Type.WATER)){
			this.worldElementTable.add(FiveElements.EnumElement.WATER, 30);
		}
		if(biomeTypes.contains(BiomeDictionary.Type.SWAMP)){
			this.worldElementTable.add(FiveElements.EnumElement.WATER, 5);
			this.worldElementTable.add(FiveElements.EnumElement.FORBIDDEN, 10);
		}
		if(world.isRaining()){
			this.worldElementTable.add(FiveElements.EnumElement.WATER, 10);
		}
		if(world.isDaytime()){
			this.worldElementTable.add(FiveElements.EnumElement.FIRE, 10);
		}else{
			this.worldElementTable.add(FiveElements.EnumElement.FORBIDDEN, 10);
		}
		//地獄
		if(biomeTypes.contains(BiomeDictionary.Type.NETHER)){
			this.worldElementTable.add(FiveElements.EnumElement.FORBIDDEN, 30);
		}
		if(biomeTypes.contains(BiomeDictionary.Type.END)){
			this.worldElementTable.add(FiveElements.EnumElement.FORBIDDEN, 40);
		}
	}

	protected void figureFromEquipment(EntityLivingBase ep){
		int fire = HelperAbility.hasAbilityLiving(ep, AbilityRegistry.supportFire)*10;
		int wood = HelperAbility.hasAbilityLiving(ep, AbilityRegistry.supportWood)*10;
		int water = HelperAbility.hasAbilityLiving(ep, AbilityRegistry.supportWater)*10;
		int earth = HelperAbility.hasAbilityLiving(ep, AbilityRegistry.supportEarth)*10;
		int metal = HelperAbility.hasAbilityLiving(ep, AbilityRegistry.supportMetal)*10;
		int forbidden = HelperAbility.hasAbilityLiving(ep, AbilityRegistry.supportForbidden)*10;
		this.worldElementTable.add(new SpellMixTable(fire,earth,metal,water,wood,forbidden));

	}

	protected void figureFromCurrentHeight(World world,EntityLivingBase ep){
		int height = (int)ep.posY;
		if(height<=10){
			this.worldElementTable.add(FiveElements.EnumElement.EARTH,50);
			return;
		}
		if(height<=20){
			this.worldElementTable.add(FiveElements.EnumElement.EARTH,36);
			return;
		}
		if(height<=30){
			this.worldElementTable.add(FiveElements.EnumElement.EARTH,26);
			return;
		}
		if(height<=40){
			this.worldElementTable.add(FiveElements.EnumElement.EARTH,16);
			return;
		}
		if(height<=50){
			this.worldElementTable.add(FiveElements.EnumElement.EARTH,8);
			return;
		}
		if(height<=60){
			this.worldElementTable.add(FiveElements.EnumElement.EARTH,5);
			return;
		}





	}


	public String getWorldElementInfo(){
		return this.worldElementTable.toString();
	}
}

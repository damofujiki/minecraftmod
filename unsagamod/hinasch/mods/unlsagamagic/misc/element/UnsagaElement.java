package hinasch.mods.unlsagamagic.misc.element;

import hinasch.lib.ScanHelper;
import hinasch.lib.XYZPos;
import hinasch.mods.unlsaga.Unsaga;
import hinasch.mods.unlsaga.misc.ability.AbilityRegistry;
import hinasch.mods.unlsaga.misc.ability.HelperAbility;
import hinasch.mods.unlsaga.misc.debuff.DebuffRegistry;
import hinasch.mods.unlsaga.misc.debuff.LivingDebuff;
import hinasch.mods.unlsaga.misc.translation.Translation;
import hinasch.mods.unlsagamagic.UnsagaMagic;
import hinasch.mods.unlsagamagic.misc.spell.SpellMixTable;

import java.util.EnumSet;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenBeach;
import net.minecraft.world.biome.BiomeGenDesert;
import net.minecraft.world.biome.BiomeGenForest;
import net.minecraft.world.biome.BiomeGenOcean;
import net.minecraftforge.oredict.OreDictionary;

public class UnsagaElement {
	public static enum EnumElement {
		FIRE,EARTH,WOOD,METAL,WATER,FORBIDDEN;

		

		public String getUnlocalized(){
		
			switch(this){
			case FIRE:return "element.fire";
			case EARTH:return "element.earth";
			case WOOD:return "element.wood";
			case METAL:return "element.metal";
			case WATER:return "element.water";
			case FORBIDDEN:return "element.forbidden";
			}

			return "";
		}
		
		public String getLocalized(){
			return Translation.localize(this.getUnlocalized());
		}
		

	};
	public static EnumSet<EnumElement> enumElements = EnumSet.of(EnumElement.FIRE,EnumElement.EARTH,EnumElement.WOOD,EnumElement.METAL,EnumElement.WATER,EnumElement.FORBIDDEN);

	protected SpellMixTable worldElementTable;


	public UnsagaElement(){
		this.worldElementTable = new SpellMixTable();
	}

	public SpellMixTable getWorldElements(){
		return this.worldElementTable;
	}
	public void figureElements(World world,EntityPlayer ep){
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

	private void figureFromBuff(EntityPlayer ep) {
		if(LivingDebuff.hasDebuff(ep, DebuffRegistry.fireVeil))this.worldElementTable.add(EnumElement.FIRE,15.0F);
		if(LivingDebuff.hasDebuff(ep, DebuffRegistry.woodVeil))this.worldElementTable.add(EnumElement.WOOD,15.0F);
		if(LivingDebuff.hasDebuff(ep, DebuffRegistry.waterVeil))this.worldElementTable.add(EnumElement.WATER,15.0F);
		if(LivingDebuff.hasDebuff(ep, DebuffRegistry.earthVeil))this.worldElementTable.add(EnumElement.EARTH,15.0F);
		if(LivingDebuff.hasDebuff(ep, DebuffRegistry.metalVeil))this.worldElementTable.add(EnumElement.METAL,15.0F);
	}

	protected void figureFromWorld(World world,EntityPlayer ep){
		XYZPos epos = XYZPos.entityPosToXYZ(ep);
		ScanHelper scanner = new ScanHelper(ep,10,8);
		boolean flag = false;
		scanner.setWorld(world);
		for(;scanner.hasNext();scanner.next()){
			flag = false;

			if(!scanner.isAirBlock() && scanner.sy>0){

				if(UnsagaMagic.elementLibrary.findBlock(Block.blocksList[scanner.getID()]).isPresent()){
					SpellMixTable table = UnsagaMagic.elementLibrary.findBlock(Block.blocksList[scanner.getID()]).get();
					//Unsaga.debug("block:"+table);
					this.worldElementTable.add(table);
					flag = true;
				}
				Material material = world.getBlockMaterial(scanner.sx, scanner.sy, scanner.sz);
				if(UnsagaMagic.elementLibrary.findMaterial(material).isPresent() && !flag){
					SpellMixTable table = UnsagaMagic.elementLibrary.findMaterial(material).get();
					this.worldElementTable.add(table);
					//Unsaga.debug("material:"+table);
					flag = true;
				}
				Class classBlock = Block.blocksList[scanner.getID()].getClass();
				if(UnsagaMagic.elementLibrary.findClass(classBlock).isPresent() && !flag){
					SpellMixTable table = UnsagaMagic.elementLibrary.findClass(classBlock).get();
					//nsaga.debug("class:"+table);
					this.worldElementTable.add(table);
					flag = true;
				}
				ItemStack is = new ItemStack(Block.blocksList[scanner.getID()],1);
				if(is!=null){
					String orekey = OreDictionary.getOreName(OreDictionary.getOreID(is));
					if(orekey.contains("ore")){
						this.worldElementTable.add(EnumElement.METAL,0.1F);
						Unsaga.debug("ore");
					}
				}
				//scanner.setBlockHere(Block.blockClay.blockID);
			}
		}


	}

	protected void figureFromBiomeAndWeather(World world,EntityPlayer ep){
		BiomeGenBase biomegen = world.getBiomeGenForCoords((int)ep.posX, (int)ep.posZ);
		if(biomegen instanceof BiomeGenOcean){
			this.worldElementTable.add(EnumElement.WATER, 30);
		}
		if(biomegen instanceof BiomeGenBeach){
			this.worldElementTable.add(EnumElement.WATER, 10);
		}
		if(biomegen instanceof BiomeGenDesert){
			this.worldElementTable.add(EnumElement.FIRE, 10);
			this.worldElementTable.add(EnumElement.WATER, -10);
		}
		if(biomegen instanceof BiomeGenForest){
			this.worldElementTable.add(EnumElement.WOOD, 10);
		}
		if(world.isRaining()){
			this.worldElementTable.add(EnumElement.WATER, 10);
		}
		if(world.isDaytime()){
			this.worldElementTable.add(EnumElement.FIRE, 10);
		}else{
			this.worldElementTable.add(EnumElement.FORBIDDEN, 10);
		}
		//地獄
		if(world.getWorldInfo().getVanillaDimension()==-1){
			this.worldElementTable.add(EnumElement.FORBIDDEN, 30);
		}
		if(world.getWorldInfo().getVanillaDimension()==1){
			this.worldElementTable.add(EnumElement.FORBIDDEN, 40);
		}
	}

	protected void figureFromEquipment(EntityPlayer ep){
		int fire = HelperAbility.hasAbilityPlayer(ep, AbilityRegistry.supportFire)*10;
		int wood = HelperAbility.hasAbilityPlayer(ep, AbilityRegistry.supportWood)*10;
		int water = HelperAbility.hasAbilityPlayer(ep, AbilityRegistry.supportWater)*10;
		int earth = HelperAbility.hasAbilityPlayer(ep, AbilityRegistry.supportEarth)*10;
		int metal = HelperAbility.hasAbilityPlayer(ep, AbilityRegistry.supportMetal)*10;
		int forbidden = HelperAbility.hasAbilityPlayer(ep, AbilityRegistry.supportForbidden)*10;
		this.worldElementTable.add(new SpellMixTable(fire,earth,metal,water,wood,forbidden));

	}

	protected void figureFromCurrentHeight(World world,EntityPlayer ep){
		int height = (int)ep.posY;
		if(height<=10){
			this.worldElementTable.add(EnumElement.EARTH,50);
			return;
		}
		if(height<=20){
			this.worldElementTable.add(EnumElement.EARTH,36);
			return;
		}
		if(height<=30){
			this.worldElementTable.add(EnumElement.EARTH,26);
			return;
		}
		if(height<=40){
			this.worldElementTable.add(EnumElement.EARTH,16);
			return;
		}
		if(height<=50){
			this.worldElementTable.add(EnumElement.EARTH,8);
			return;
		}
		if(height<=60){
			this.worldElementTable.add(EnumElement.EARTH,5);
			return;
		}





	}


	public String getWorldElementInfo(){
		return this.worldElementTable.toString();
	}
}

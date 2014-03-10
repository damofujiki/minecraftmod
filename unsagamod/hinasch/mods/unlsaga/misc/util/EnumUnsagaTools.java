package hinasch.mods.unlsaga.misc.util;

import hinasch.mods.unlsaga.misc.translation.Translation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;


public enum EnumUnsagaTools {

	SWORD,STAFF,SPEAR,BOW,AXE,ARMOR,HELMET,LEGGINS,BOOTS,ACCESSORY,PICKAXE,GUN;
	
	
	public static final HashSet<String> weaponList = toString(Sets.immutableEnumSet(SWORD,STAFF,SPEAR,BOW,AXE));
	public static final HashSet<String> armorList = toString(Sets.immutableEnumSet(HELMET,LEGGINS,ARMOR,BOOTS));
	public static final HashSet<String> merchandiseList = toString(Sets.immutableEnumSet(SWORD,STAFF,SPEAR,BOW,AXE,ARMOR,HELMET,LEGGINS,BOOTS,ACCESSORY));
	public static final ArrayList<EnumUnsagaTools> toolArray = Lists.newArrayList(SWORD,STAFF,SPEAR,BOW,AXE,ACCESSORY);
	
	public static HashSet<String> toString(Set<EnumUnsagaTools> set){
		HashSet<String> newSet = new HashSet();
		for(Iterator<EnumUnsagaTools> ite=set.iterator();ite.hasNext();){
			newSet.add(ite.next().toString());
		}
		return newSet;
	}
	
	public String getLocalized(){
		return Translation.localize("word."+this.toString().toLowerCase());
	}
}

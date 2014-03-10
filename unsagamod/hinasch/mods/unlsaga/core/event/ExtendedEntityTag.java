package hinasch.mods.unlsaga.core.event;

import hinasch.lib.CSVText;
import hinasch.mods.unlsaga.Unsaga;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class ExtendedEntityTag implements IExtendedEntityProperties{

	protected List<String> taglist;
	protected String KEY = "unsaga.extendedtag";
	public static String tagKEY = "unsaga.extendedEntityTag";
	@Override
	public void saveNBTData(NBTTagCompound compound) {
		// TODO 自動生成されたメソッド・スタブ
		if(!this.taglist.isEmpty() && this.taglist!=null){
			String str = CSVText.exchangeCollectionToCSV(this.taglist);
			compound.setString(KEY, str);
		}

	}

	@Override
	public void loadNBTData(NBTTagCompound compound) {
		// TODO 自動生成されたメソッド・スタブ
		if(compound.hasKey(KEY)){
			String str = compound.getString(KEY);
			if(!str.equals("")){
				this.taglist = CSVText.csvToStrList(str);
			}
		}


	}

	@SubscribeEvent
	public void attachDataEvent(EntityConstructing e){
	
		if(e.entity instanceof EntityArrow){
			Entity ent = (Entity)e.entity;
			ent.registerExtendedProperties(tagKEY, new ExtendedEntityTag());
		}
	}
	
	@Override
	public void init(Entity entity, World world) {
		this.taglist = new ArrayList();
		
	}
	
	public void addTag(String par1){
		this.taglist.add(par1);
	}
	
	public boolean hasTag(String par1){
		for(String str:taglist){
			if(str.equals(par1)){
				return true;
			}
		}
		return false;
	}

	public static boolean hasTag(Entity entity,String par1){
		if(entity.getExtendedProperties(tagKEY)!=null){
			ExtendedEntityTag tag = (ExtendedEntityTag)entity.getExtendedProperties(tagKEY);
			return tag.hasTag(par1);
		}
		return false;
	}
	
	public static void addTagToEntity(Entity entity,String tag){
		
		ExtendedEntityTag tags = (ExtendedEntityTag) entity.getExtendedProperties(tagKEY);
		if(tags!=null){
			Unsaga.debug("タグとれました");
			tags.addTag(tag);
		}else{
			Unsaga.debug("タグとれませんでした、タグつけてみます");
			entity.registerExtendedProperties(tagKEY, new ExtendedEntityTag());
			tags = (ExtendedEntityTag) entity.getExtendedProperties(tagKEY);
			tags.addTag(tag);
		}
	}
	
	public static boolean entityHasTag(Entity entity,String tag){
		ExtendedEntityTag tags = (ExtendedEntityTag) entity.getExtendedProperties(tagKEY);
		if(tags!=null){
			return tags.hasTag(tag);
		}
		return false;
	}
}

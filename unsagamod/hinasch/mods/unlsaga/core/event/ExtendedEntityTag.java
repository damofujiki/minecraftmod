package hinasch.mods.unlsaga.core.event;

import hinasch.lib.CSVText;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;

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

	@ForgeSubscribe
	public void attachDataEvent(EntityConstructing e){
	
		if(e.entity instanceof Entity){
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
		return this.taglist.contains(par1);
	}

	public static boolean hasTag(Entity entity,String par1){
		if(entity.getExtendedProperties(tagKEY)!=null){
			ExtendedEntityTag tag = (ExtendedEntityTag)entity.getExtendedProperties(tagKEY);
			return tag.hasTag(par1);
		}
		return false;
	}
}

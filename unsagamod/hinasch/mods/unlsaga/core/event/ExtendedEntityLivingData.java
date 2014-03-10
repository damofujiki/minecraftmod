package hinasch.mods.unlsaga.core.event;

import hinasch.lib.CSVText;
import hinasch.mods.unlsaga.Unsaga;
import hinasch.mods.unlsaga.misc.debuff.Debuff;
import hinasch.mods.unlsaga.misc.debuff.livingdebuff.LivingDebuff;

import java.util.HashSet;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;

import com.google.common.base.Optional;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class ExtendedEntityLivingData implements IExtendedEntityProperties{

	protected HashSet<LivingDebuff> debuffSet;

	public static String KEY = "Unsaga.Debuffs";
	
	@Override
	public void saveNBTData(NBTTagCompound compound) {
		if(!debuffSet.isEmpty()){
			String str = CSVText.exchangeCollectionToCSV(debuffSet);
			compound.setString(KEY, str);
			return;
		}
		compound.setString(KEY, "");
		
	}

	@Override
	public void loadNBTData(NBTTagCompound compound) {
		// TODO 自動生成されたメソッド・スタブ
		String str = compound.getString(KEY);
		if(str.equals("")){
			this.debuffSet = new HashSet();
			return;
		}
		List<String> strList = CSVText.csvToStrList(str);
		this.debuffSet = strListToDebuffSet(strList);
		
	}
	

	@Override
	public void init(Entity entity, World world) {
		// TODO 自動生成されたメソッド・スタブ
		this.debuffSet = new HashSet();
	
	}

	@SubscribeEvent
	public void attachDataEvent(EntityConstructing e){
	
		if(e.entity instanceof EntityLivingBase){
			EntityLivingBase living = (EntityLivingBase)e.entity;
			living.registerExtendedProperties(KEY, new ExtendedEntityLivingData());
		}
	}
	
	@SubscribeEvent
    public void onLivingUpdate(LivingUpdateEvent e)
    {
        if(e.entityLiving.getExtendedProperties(KEY)!=null){
        	EntityLivingBase living =(EntityLivingBase)e.entityLiving;
        	ExtendedEntityLivingData ldata = (ExtendedEntityLivingData) living.getExtendedProperties(KEY);
        	
        	if(living.ticksExisted % 20 * 12 == 0){
        		ldata.updateAllRemaining(e.entityLiving);
        	}
        	ldata.updateTickAllRemaining(e.entityLiving);
        	
        }
    }

	private void updateTickAllRemaining(EntityLivingBase living) {
		HashSet<LivingDebuff> removes = new HashSet();
		if(this.debuffSet.isEmpty() || this.debuffSet==null)return;
		for(LivingDebuff debuff:this.debuffSet){
			debuff.updateTick(living);
			if(debuff.isExpired()){
				removes.add(debuff);
			}
		}
		if(!removes.isEmpty()){
			for(LivingDebuff debuff:removes){
				this.debuffSet.remove(debuff);
			}
		}
		
	}

	public HashSet<LivingDebuff> strListToDebuffSet(List<String> list){
		Unsaga.debug("復元中");
		HashSet<LivingDebuff> output = new HashSet();
		for(String str:list){
			output.add(LivingDebuff.buildFromString(str));
		}
		return output;
	}
	
	public void updateAllRemaining(EntityLivingBase living){
		if(this.debuffSet.isEmpty() || this.debuffSet==null)return;
		for(LivingDebuff debuff:this.debuffSet){
			debuff.updateRemain(living);
		}
	}
	
	public boolean hasDebuff(Debuff debuff){
		if(this.debuffSet.isEmpty())return false;
		for(LivingDebuff living:this.debuffSet){
			if(living.getDebuff().number==debuff.number)return true;
		}
		return false;
	}
		
	public static void addDebuff(EntityLivingBase living,Debuff debuff,int remain){
		if(living.getExtendedProperties(ExtendedEntityLivingData.KEY)!=null){
			ExtendedEntityLivingData ldata = (ExtendedEntityLivingData) living.getExtendedProperties(ExtendedEntityLivingData.KEY);
			if(!ldata.hasDebuff(debuff)){
				ldata.debuffSet.add(new LivingDebuff(debuff,remain));
			}else{
				removeDebuff(living,debuff);
				ldata.debuffSet.add(new LivingDebuff(debuff,remain));
			}
			
		}
	}
	
	public static void addLivingDebuff(EntityLivingBase living,LivingDebuff livdebuff){
		if(living.getExtendedProperties(ExtendedEntityLivingData.KEY)!=null){
			ExtendedEntityLivingData ldata = (ExtendedEntityLivingData) living.getExtendedProperties(ExtendedEntityLivingData.KEY);
			if(!ldata.hasDebuff(livdebuff.getDebuff())){
				ldata.debuffSet.add(livdebuff);
			}else{
				removeDebuff(living,livdebuff.getDebuff());
				ldata.debuffSet.add(livdebuff);
			}
			
		}
	}

	public static void removeDebuff(EntityLivingBase living, Debuff debuff) {
		if(living.getExtendedProperties(ExtendedEntityLivingData.KEY)!=null){
			ExtendedEntityLivingData ldata = (ExtendedEntityLivingData) living.getExtendedProperties(ExtendedEntityLivingData.KEY);

			if(ldata.debuffSet.isEmpty())return;
			HashSet<LivingDebuff> removes = new HashSet();
			for(LivingDebuff ldebuff:ldata.debuffSet){
				if(ldebuff.getDebuff().number==debuff.number){
					removes.add(ldebuff);
				}
			}
			
			//同期エラーを防ぐ
			if(!removes.isEmpty()){
				for(LivingDebuff remov:removes){
					ldata.debuffSet.remove(remov);
				}
			}

			
		}
		
	}
	
	public static boolean hasDebuff(EntityLivingBase living,Debuff debuff){
		if(living.getExtendedProperties(ExtendedEntityLivingData.KEY)!=null){
			ExtendedEntityLivingData ldata = (ExtendedEntityLivingData) living.getExtendedProperties(ExtendedEntityLivingData.KEY);
			if(ldata.debuffSet.isEmpty())return false;
			for(LivingDebuff ldebuff:ldata.debuffSet){
				if(ldebuff.getDebuff().number==debuff.number){
					return true;
				}
			}
		}
		return false;
	}
	
	public static Optional<LivingDebuff> getDebuff(EntityLivingBase living,Debuff debuff){
		if(living.getExtendedProperties(ExtendedEntityLivingData.KEY)!=null){
			ExtendedEntityLivingData ldata = (ExtendedEntityLivingData) living.getExtendedProperties(ExtendedEntityLivingData.KEY);
			if(ldata.debuffSet.isEmpty())return Optional.absent();
			for(LivingDebuff ldebuff:ldata.debuffSet){
				if(ldebuff.getDebuff().number==debuff.number){
					return Optional.of(ldebuff);
				}
			}
		}
		return Optional.absent();
	}
}

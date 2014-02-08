package hinasch.mods.creativeitems.lib;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import com.google.common.base.Preconditions;

public class UtilNBT {

	protected static String KEY = "FreeState";
	
	public static void initNBTTag(ItemStack is){

		NBTTagCompound nbt = is.getTagCompound();
		if(nbt == null){
			nbt = new NBTTagCompound();
			is.setTagCompound(nbt);
		}

		return;
	}
	
	public static boolean hasTag(ItemStack is){
		Preconditions.checkNotNull(is);
		return is.hasTagCompound();
	}
	
	public static boolean hasKey(ItemStack is,String key){
		Preconditions.checkNotNull(is);
		if(hasTag(is)){
			NBTTagCompound nbt = is.getTagCompound();
			if(nbt.hasKey(key)){
				return true;
			}
		}
		return false;
	}
	
	public static void setFreeTag(ItemStack is,String key,int val){
		Preconditions.checkNotNull(is);
		initNBTTag(is);
		NBTTagCompound nbt = is.getTagCompound();
		nbt.setInteger(key, (int)val);
		return;
	}
	
	public static void setFreeTag(ItemStack is,String key,String val){
		Preconditions.checkNotNull(is);
		initNBTTag(is);
		NBTTagCompound nbt = is.getTagCompound();
		nbt.setString(key, val);
		return;
	}
	
	public static void setFreeTag(ItemStack is,String key,boolean val){
		Preconditions.checkNotNull(is);
		initNBTTag(is);
		NBTTagCompound nbt = is.getTagCompound();
		nbt.setBoolean(key, val);
		return;
	}
	
	public static int readFreeTag(ItemStack is,String key){
		Preconditions.checkNotNull(is);
		NBTTagCompound nbt = is.getTagCompound();
		Preconditions.checkNotNull(nbt);
		if(!nbt.hasKey(key)){
			System.out.println("tag not found key:"+key);
			return -1;
		}
		int rt = (int)nbt.getInteger(key);
		return rt;
	}
	
	public static String readFreeStrTag(ItemStack is,String key){
		Preconditions.checkNotNull(is);
		NBTTagCompound nbt = is.getTagCompound();
		Preconditions.checkNotNull(nbt);
		if(!nbt.hasKey(key)){
			System.out.println("tag not found key:"+key);
			return "";
		}
		String rt = nbt.getString(key);
		return rt;
	}
	
	public static boolean readFreeTagBool(ItemStack is,String key){
		Preconditions.checkNotNull(is);
		NBTTagCompound nbt = is.getTagCompound();
		Preconditions.checkNotNull(nbt);
		if(!nbt.hasKey(key)){
			System.out.println("tag not found key:"+key);
			return false;
		}
		boolean rt = nbt.getBoolean(key);
		return rt;
	}
	
	public static void setState(ItemStack is,int state){
		setFreeTag(is,KEY,state);
	}
	
	public static void setState(ItemStack is,boolean state){
		setFreeTag(is,KEY,state);
	}
	
	public static int readState(ItemStack is){
		int rt = 0;
		rt = readFreeTag(is,KEY);
		return rt;
	}
	
	public static boolean readStateBool(ItemStack is){
		boolean rt = false;
		rt = readFreeTagBool(is,KEY);
		return rt;
	}
}

package hinasch.lib;

import hinasch.lib.Library.EnumSelecter;

import java.util.HashSet;
import java.util.Iterator;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import com.google.common.base.Optional;

public class LibraryFactory {

	public HashSet<Library> libSet = new HashSet();
	
	public Optional<Library> findFromClass(Class input){
		Optional<Library> info = Optional.absent();
		for(Iterator<Library> ite=libSet.iterator();ite.hasNext();){
			Library currentpage = ite.next();
			if(currentpage.key==EnumSelecter._CLASS){
				if(currentpage.classstore.get()==input){
					info = Optional.of(currentpage);
				}
			}

		}
		return info;
	}
	
	public Optional<Library> findEnumInfo(String input){
		Optional<Library> info = Optional.absent();
		for(Iterator<Library> ite=libSet.iterator();ite.hasNext();){
			Library currentpage = ite.next();
			if(currentpage.key==EnumSelecter.TOOLMATERIAL){
				if(currentpage.enumtool.get().toString().equals(input)){
					info = Optional.of(currentpage);
				}
			}
			if(currentpage.key==EnumSelecter.ARMORMATERIAL){
				if(currentpage.enumarmor.get().toString().equals(input)){
					info = Optional.of(currentpage);
				}
			}
		}
		return info;
	}
	
	public Optional<Library> findInfo(ItemStack input){
		Optional<Library> info = Optional.absent();
		Item item = (Item)input.getItem();
		for(Iterator<Library> ite=libSet.iterator();ite.hasNext();){
			Library currentpage = ite.next();
			if(currentpage.key==EnumSelecter.ITEMSTACK){
				if(currentpage.idmeta.get().id==item.itemID && currentpage.idmeta.get().metadata==input.getItemDamage()){
					info = Optional.of(currentpage);
				}
			}
			if(currentpage.key==EnumSelecter.STRING && !info.isPresent()){
				String baseoreid = OreDictionary.getOreName(OreDictionary.getOreID(input));
				//Unsaga.debug(baseoreid);
				if(baseoreid.equals(currentpage.orekey.get())){
					info = Optional.of(currentpage);
				}
				
			}

		}
		return info;
	}
}

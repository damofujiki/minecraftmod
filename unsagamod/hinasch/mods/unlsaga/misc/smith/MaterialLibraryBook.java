package hinasch.mods.unlsaga.misc.smith;

import com.hinasch.lib.LibraryBook;

import hinasch.mods.unlsaga.core.init.UnsagaMaterial;

public class MaterialLibraryBook extends LibraryBook{

	public UnsagaMaterial material;

	public int damage;
	
	public MaterialLibraryBook(Object par1,UnsagaMaterial material,int damage) {
		super(par1);
		this.material = material;
		this.damage = damage;
		// TODO 自動生成されたコンストラクター・スタブ
	}


}

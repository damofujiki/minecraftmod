package hinasch.mods.unlsaga.core.init;

import hinasch.mods.unlsaga.Unsaga;
import hinasch.mods.unlsaga.item.IUnsagaMaterial;
import hinasch.mods.unlsaga.misc.translation.Translation;
import hinasch.mods.unlsaga.misc.util.EnumUnsagaTools;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.hinasch.lib.FileObject;
import com.hinasch.lib.RecipeUtil;
import com.hinasch.lib.RecipeUtil.Recipe;
import com.hinasch.lib.RecipeUtil.Recipe.Shaped;
import com.hinasch.lib.RecipeUtil.Recipe.Shapelss;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class UnsagaToolInitializer {


	protected Map<Integer,UnsagaMaterial> materialAvailable;

	//1.7.2で消える
	public int itemIDStart;

	//自動ローカライズ名用 これをあとでローカライズファイルで上書きする感じで
	protected HashSet<UnsagaMaterial> noParticles;
	protected String wordParticle;
	protected HashSet<UnsagaMaterial> useAltName;
	protected HashSet<UnsagaMaterial> noUseParentHeader;
	protected HashMap<String,String> hooter;
	protected boolean setAutoIDResolver = false;
	protected EnumUnsagaTools unsagaType;
	protected boolean isArmor = false;
	protected boolean useParentLocalized = false;
	protected int armorType;
	
	//デバッグ用
	public FileObject fileObj;


	protected UnsagaToolInitializer(){
		//this.itemIDStart = itemidstart;
		this.hooter = new HashMap();
		this.useAltName = new HashSet();
		this.noParticles = new HashSet();
		this.materialAvailable = new HashMap();
		this.noUseParentHeader = new HashSet();
		this.wordParticle = "の";
	}
	
	protected UnsagaToolInitializer(FileObject fo){
		this();
		this.fileObj = fo;
		
	}

	
	public UnsagaToolInitializer setHooter(String lang,String name){
		this.hooter.put(lang, name);
		return this;
	}

	public void setNoParticles(HashSet<UnsagaMaterial> materials){
		this.noParticles = materials;
	}
	
	public void setUseParentLocalized(boolean par1){
		this.useParentLocalized = par1;
	}
	
	public void setNoUseParentNames(HashSet<UnsagaMaterial> materials){
		this.noUseParentHeader = materials;
	}

	public void setAvailableMaterial(Map<Integer,UnsagaMaterial> materials){
		this.materialAvailable = materials;
	}

	public void setArmorData(EnumUnsagaTools type,int typeint){
		this.isArmor = true;
		this.unsagaType = type;
		this.armorType = typeint;

	}

	public void register(Item[] itemarray,Class classitem,String category){

		for(int i=0;i<this.materialAvailable.size();i++){
			UnsagaMaterial uns = this.materialAvailable.get(i);
			Constructor cons;

			//アイテムID・素材（このMODの）のコンストラクターを必ず用意する
			if(uns.hasSubMaterials()){
				for(UnsagaMaterial child:uns.getSubMaterials().values()){


					cons = this.getConstructor(classitem);
					if(cons==null){
						throw new NullPointerException();
					}

					//int arrayindex = this.getNextArrayIndex(itemarray, 0);
					itemarray[i] = this.getItemInstance(cons, child);
					itemarray[i].setCreativeTab(Unsaga.tabUnsaga).setUnlocalizedName("unsaga."+category+"."+child.name);
					GameRegistry.registerItem(itemarray[i],category+"."+child.name,Unsaga.modid);

					if(fileObj!=null){
						fileObj.write(itemarray[i].getUnlocalizedName()+".name=\r\n");

					}
					
					Unsaga.debug("itemarray:"+i+":"+"-"+"register:"+category+"."+child);

				}
			}else{
				cons = this.getConstructor(classitem);
				//Unsaga.debug(cons);
				if(cons==null){
					throw new NullPointerException();
				}

				//int arrayindex = this.getNextArrayIndex(itemarray, 0);
				itemarray[i] = this.getItemInstance(cons, uns);
				itemarray[i].setCreativeTab(Unsaga.tabUnsaga).setUnlocalizedName("unsaga."+category+"."+uns.name);
				GameRegistry.registerItem(itemarray[i],category+"."+uns.name,Unsaga.modid);

				if(fileObj!=null){
					fileObj.write(itemarray[i].getUnlocalizedName()+".name=\r\n");

				}
				Unsaga.debug("itemarray:"+i+":"+"-"+"register:"+category+"."+uns);
			}





		}
		return ;
	}

	public Constructor getConstructor(Class classitem){
		try {
			if(isArmor){

				return classitem.getConstructor(EnumUnsagaTools.class,int.class,UnsagaMaterial.class);

			}else{
				return classitem.getConstructor(UnsagaMaterial.class);
			}
		} catch (SecurityException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		throw new NullPointerException();

	}

	public Item getItemInstance(Constructor cons,UnsagaMaterial us){
		try {
			if(isArmor){

				return (Item) cons.newInstance(this.unsagaType,this.armorType,us);


			}else{
				return (Item) cons.newInstance(us);

			}
		} catch (IllegalArgumentException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		throw new NullPointerException();

	}

	protected int getNextArrayIndex(Item[] item,int index){
		if(item[index]!=null){
			return getNextArrayIndex(item,index+1);
		}
		return index;
	}

	public void buildLocalizedNames(Item[] items){
		for(Item it:items){
			if(it!=null){
				if(it instanceof IUnsagaMaterial){
					IUnsagaMaterial iu = (IUnsagaMaterial)it;
					if(isArmor){
						if(iu.getCategory()==this.unsagaType){



							this.buildLocalizedName(it, iu.getMaterial());


						}
					}else{

						this.buildLocalizedName(it, iu.getMaterial());


					}
				}
			}
		}
	}
	public void buildLocalizedName(Item item,UnsagaMaterial us){
		String unlocalized = item.getUnlocalizedName();
		Unsaga.debug(unlocalized);
		StringBuilder jaName = new StringBuilder();
		//オルタネイティブネームがある場合それを素材名ヘッダとして使う
		if(Unsaga.translation.hasKey(us.getUnlocalizedName()+".alt", "ja_JP")){
			jaName.append(Unsaga.translation.getLocalized(us.getUnlocalizedName()+".alt", "ja_JP"));
		}else{
			String jpheader = "";
			//親のヘッダを使う場合はそれを使う
			if(us.setContainsThis(noUseParentHeader)){
				jpheader = us.getLocalized("ja_JP");	
			}else{
				jpheader = us.isChild ? us.getParentMaterial().getLocalized("ja_JP") : us.getLocalized("ja_JP");
			}

			
			jaName.append(jpheader);


		}

//		boolean noParticleFlag = false;
//		for(UnsagaMaterial u:this.noParticles){
//			if(us.isChildOwnedBy(u)){
//				noParticleFlag = true;
//			}
//			if(u==us){
//				noParticleFlag = true;
//			}
//		}
		//補語を使う場合
		if(!us.setContainsThis(noParticles)){
			jaName.append(this.wordParticle);
		}
		//フッターを追加して名前完成
		jaName.append(this.hooter.get("ja_JP"));
		
		//ローカライズ（このモッドのローカライズにストアしておいた）が存在しないことを確認、作った名前を追加
		//ちなみに固有のローカライズがある場合はすでに設定済なので、上書きしないようにする
		if(checkLocalizeEmpty(unlocalized,"en_US")){
			String usname = us.isChild ? us.getParentMaterial().getLocalized("en_US") : us.getLocalized("en_US");
			LanguageRegistry.instance().addStringLocalization(unlocalized+".name", "en_US", usname+" "+this.hooter.get("en_US"));
		}
		
		if(checkLocalizeEmpty(unlocalized,"ja_JP")){
			LanguageRegistry.instance().addStringLocalization(unlocalized+".name", "ja_JP",new String(jaName));
		}
		//素材のローカライズが存在する場合はそちらを優先、じゃない場合、親の素材の名前を使う場合それを使用
		//親の名前を使う設定は、イニシャライザー毎に設定してある
		if(checkLocalizeEmpty(unlocalized,"ja_JP") && this.useParentLocalized){
			//親の素材があるかチェック、あったら名前のキーをとってくる
			String parentMaterialName = us.isChild ? us.getParentMaterial().name : null;
			if(parentMaterialName!=null){
				String[] splited = unlocalized.split("\\.");
				String parentUnlocalized = splited[0]+"."+splited[1]+"."+splited[2]+"."+parentMaterialName;
				Unsaga.debug("name from parent:"+parentUnlocalized);
				if(!checkLocalizeEmpty(parentUnlocalized,"ja_JP")){
					String parentLocalized = Translation.getInstance().getLocalized(parentUnlocalized+".name", "ja_JP");
					Unsaga.debug("name from parent2:"+parentLocalized);
					LanguageRegistry.instance().addStringLocalization(unlocalized+".name", "ja_JP",parentLocalized);
				}
				
				
			}
		}

		//HSLibs.langSet(us.getLocalized("en_US")+" "+this.hooter.get("en_US"), new String(jaName), item);
	}

	public boolean checkLocalizeEmpty(String key,String lang){
		return Unsaga.translation.getLocalized(key+".name", lang).equals("")? true : false;
	}

	public Set<UnsagaMaterial> getUnpackedAvailables(){
		Set<UnsagaMaterial> set = new HashSet();
		for(UnsagaMaterial material:this.materialAvailable.values()){
			if(material.hasSubMaterials()){
				for(UnsagaMaterial sub:material.getSubMaterials().values()){
					set.add(sub);
				}
			}else{
				set.add(material);
			}
		}
		return set;
	}
	public void regsiterRecipes(Recipe recipebase,EnumUnsagaTools category){
		Recipe recipe = recipebase;
		Set<UnsagaMaterial> unpacked = this.getUnpackedAvailables();
		for(UnsagaMaterial material:unpacked){
			Unsaga.debug(material+":"+category+"のレシピを登録準備");
			ItemStack is = UnsagaItems.getItemStack(category, material, 1, 0);
			if(is!=null){
				if(material.getAssociatedItem().isPresent()){
					ItemStack mate = material.getAssociatedItem().get();
					Unsaga.debug("レシピを"+mate+"に入れ替えて登録");
					recipe.getChangedRecipe(mate);
					Unsaga.debug(recipe.getChangedRecipe(mate).toString());
					if(recipe instanceof Shaped){
						RecipeUtil.addShapedRecipe(is, ((Shaped) recipe).getChangedRecipe(mate));
					}
					if(recipe instanceof Shapelss){
						RecipeUtil.addShapelessRecipe(is, ((Shapelss) recipe).getChangedRecipe(mate));
					}
				}
			}
		}
	}

}

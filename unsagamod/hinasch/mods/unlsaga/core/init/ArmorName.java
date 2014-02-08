package hinasch.mods.unlsaga.core.init;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.common.collect.Lists;

public class ArmorName {

	public HashMap<UnsagaMaterial,String> helmets;
	public HashMap<UnsagaMaterial,String> armors;
	public HashMap<UnsagaMaterial,String> legs;
	public HashMap<UnsagaMaterial,String> acs;
	
	public ArmorName(){
		helmets = new HashMap();
		armors = new HashMap();
		legs = new HashMap();
		acs = new HashMap();
		
		helmets.put(MaterialList.cloth, "Bandana,バンダナ");
		helmets.put(MaterialList.liveSilk, "Bandana,バンダナ");
		helmets.put(MaterialList.stone, "Stone Mask,ストーンマスク");
		helmets.put(MaterialList.fur, "Fur Cap,毛皮のフード");
		helmets.put(MaterialList.crocodileLeather, "Head Band,ヘッドバンド");
		helmets.put(MaterialList.metal, "Metal Helmet,メタルヘルム");
		helmets.put(MaterialList.steels, "Steel Helmet,スチールヘルム");
		helmets.put(MaterialList.hydra, "Hydra Leather,ねじりヒドラ");
		helmets.put(MaterialList.corundum1, "Corundum Crown,鋼玉の頭冠");
		helmets.put(MaterialList.corundum2, "Corundum Crown,鋼玉の頭冠");
		helmets.put(MaterialList.fairieSilver, "Fairie Circlet,精霊環");
		helmets.put(MaterialList.obsidian, "Obsidian Mask,黒曜石の仮面");
		helmets.put(MaterialList.diamond, "Diamond Crown,金剛石の頭冠");
		helmets.put(MaterialList.damascus, "Black Helmet,ブラックヘルム");
		
		armors.put(MaterialList.cloth, "Cloth Armor,クロースアーマー");
		armors.put(MaterialList.categorywood, "Wood Armor,パッデドメイル");
		armors.put(MaterialList.stone, "Rock Armor,ロックアーマー");
		armors.put(MaterialList.leathers, "Leather Armor,レザースーツ");
		armors.put(MaterialList.metal, "Chain Armor,チェインメイル");
		armors.put(MaterialList.steels, "Steel Helmet,スチールヘルム");
		armors.put(MaterialList.hydra, "Hydra Leather Armor,ヒドラレザー");
		armors.put(MaterialList.bestial, "Bestial Armor,獣石の鎧");
		armors.put(MaterialList.liveSilk, "Live Silk,フラショナル");
		armors.put(MaterialList.fairieSilver, "Fairie Silver Armor,シルバーチェイル");
		armors.put(MaterialList.obsidian, "Obsidian Armor,黒曜石の鎧");
		armors.put(MaterialList.diamond, "Diamond Crown,金剛石の頭冠");
		armors.put(MaterialList.damascus, "Black Armor,ブラックチェイル");
		
		legs.put(MaterialList.cloth, "Socks,ソックス");
		legs.put(MaterialList.categorywood, "Wood Boots,木ぐつ");
		legs.put(MaterialList.leathers, "Leather Boots,ブーツ");
		legs.put(MaterialList.stone, "Stone Leggins,ストーンレガース");
		legs.put(MaterialList.metal, "Metal Greaves,メタルグリーブ");
		legs.put(MaterialList.steels, "Steel Leggins,スチールレッグ");
		legs.put(MaterialList.hydra, "Hydra Leather Boots,ヒドラブーツ");
		legs.put(MaterialList.bestial, "Bestial Boots,ストーンレガース");
		legs.put(MaterialList.liveSilk, "Socks,ソックス");
		legs.put(MaterialList.fairieSilver, "Fairie Silver Boots,精霊のかかと");
		legs.put(MaterialList.obsidian, "Obsidian Boots,黒曜石の靴");
		legs.put(MaterialList.damascus, "Black Greaves,ブラックグリーブ");
		

		acs.put(MaterialList.categorywood, "Wood Breslet,樹木の腕輪");
		acs.put(MaterialList.bone, "Crossbone,骨十字");
		acs.put(MaterialList.stone, "Stone Breslet,石の腕輪");
		acs.put(MaterialList.metal, "Metal Ring,メタルリング");
		acs.put(MaterialList.steels, "Metal Ring,メタルリング");
		acs.put(MaterialList.corundum1, "Corundum Ring,鋼玉の指輪");
		acs.put(MaterialList.corundum2, "Corundum Ring,鋼玉の指輪");
		acs.put(MaterialList.demonite, "Demonite Ring,魔石の指輪");
		acs.put(MaterialList.angelite, "Angelite Ring,聖石の指輪");
		acs.put(MaterialList.bestial, "Bestial Breslet,獣石の腕輪");
		acs.put(MaterialList.diamond, "Diamond Ring,金剛石の指輪");
		acs.put(MaterialList.fairieSilver, "Fairie Silver Breslet,白銀の腕輪");
		acs.put(MaterialList.obsidian, "Obsidian Breslet,黒曜石の腕輪");
		acs.put(MaterialList.damascus, "Damascus Breslet,黒鋼の腕輪");
	}
	
	public List<String> getHelmetName(UnsagaMaterial material){
		ArrayList<String> list = new ArrayList();
		if(helmets.get(material)!=null){
			return Lists.newArrayList(helmets.get(material).split(","));
		}
		return null;
	}
	
	public List<String> getArmorName(UnsagaMaterial material){
		ArrayList<String> list = new ArrayList();
		if(armors.get(material)!=null){
			return Lists.newArrayList(armors.get(material).split(","));
		}
		return null;
	}
	
	public List<String> getLegsName(UnsagaMaterial material){
		ArrayList<String> list = new ArrayList();
		if(legs.get(material)!=null){
			return Lists.newArrayList(legs.get(material).split(","));
		}
		return null;
	}
	
	public List<String> getAcsName(UnsagaMaterial material){
		ArrayList<String> list = new ArrayList();
		if(acs.get(material)!=null){
			return Lists.newArrayList(acs.get(material).split(","));
		}
		return null;
	}
}

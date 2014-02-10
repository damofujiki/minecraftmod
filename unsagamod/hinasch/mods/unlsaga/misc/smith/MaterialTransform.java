package hinasch.mods.unlsaga.misc.smith;

import hinasch.mods.unlsaga.Unsaga;
import hinasch.mods.unlsaga.core.init.MaterialList;
import hinasch.mods.unlsaga.core.init.UnsagaMaterial;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;

import com.google.common.base.Optional;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;

public class MaterialTransform {

	
	public static HashSet<MaterialTransform> store;
	public final HashMap<UnsagaMaterial,Integer> required;
	public final UnsagaMaterial transformto;
	public final int probability;
	
	protected MaterialTransform(UnsagaMaterial m1,UnsagaMaterial m2,UnsagaMaterial tr,int prob){
		this.required = new HashMap();
		if(m1==m2){
			this.required.put(m1, 2);
		}else{
			this.required.put(m1, 1);
			this.required.put(m2, 1);
		}
		
		this.probability = prob;
		this.transformto = tr;
		
	}
	
	public static Optional<UnsagaMaterial> drawTransformed(UnsagaMaterial input1,UnsagaMaterial input2,Random rand){
		Multiset<UnsagaMaterial> inputs = HashMultiset.create();
		inputs.add(input1);
		inputs.add(input2);
		UnsagaMaterial transformed = null;
		for(Iterator<MaterialTransform> ite = store.iterator();ite.hasNext();){
			MaterialTransform trans = ite.next();
			if(matchElement(inputs,trans.required)){
				Unsaga.debug(trans.transformto.jpName+"になりそう");
				if(rand.nextInt(100)<=trans.probability){
					transformed = trans.transformto;
				}
			}
		}
		if(transformed==null)return Optional.absent();
		return Optional.of(transformed);
	}
	
	protected static boolean matchElement(Multiset<UnsagaMaterial> input,HashMap<UnsagaMaterial,Integer> requiredmatter){
		int count;
		int match = 0;
		for(Iterator<UnsagaMaterial> ite=requiredmatter.keySet().iterator();ite.hasNext();){
			count = 0;
			UnsagaMaterial requiredmaterial = ite.next();
			int requiredcount = requiredmatter.get(requiredmaterial);

			if(requiredmaterial.hasSubMaterials()){
				for(Iterator<UnsagaMaterial> itera=requiredmaterial.getSubMaterials().values().iterator();itera.hasNext();){
					count += input.count(itera.next());
				}
			}else{
				count += input.count(requiredmaterial);
			}
			if(count>=requiredcount){
				match += 1;
			}
		}
		if(match>=requiredmatter.size()){
			return true;
		}
		return false;
	}
	

	
	static{
		store = new HashSet();
		store.add(new MaterialTransform(MaterialList.iron,MaterialList.categorywood,MaterialList.steel1,85));
		store.add(new MaterialTransform(MaterialList.steel1,MaterialList.categorywood,MaterialList.steel2,15));
		store.add(new MaterialTransform(MaterialList.iron,MaterialList.categorywood,MaterialList.steel2,15));
		store.add(new MaterialTransform(MaterialList.ironOre,MaterialList.categorywood,MaterialList.steel2,15));
		store.add(new MaterialTransform(MaterialList.silver,MaterialList.bestial,MaterialList.fairieSilver,15));
		store.add(new MaterialTransform(MaterialList.steel2,MaterialList.debris2,MaterialList.damascus,100));
		store.add(new MaterialTransform(MaterialList.debris,MaterialList.debris,MaterialList.debris2,100));
		store.add(new MaterialTransform(MaterialList.debris,MaterialList.bestial,MaterialList.debris2,100));
		store.add(new MaterialTransform(MaterialList.stone,MaterialList.categorywood,MaterialList.debris,15));
		store.add(new MaterialTransform(MaterialList.stone,MaterialList.silver,MaterialList.debris,15));
		store.add(new MaterialTransform(MaterialList.stone,MaterialList.obsidian,MaterialList.debris,15));
		store.add(new MaterialTransform(MaterialList.copperOre,MaterialList.categorywood,MaterialList.copper,100));
		store.add(new MaterialTransform(MaterialList.ironOre,MaterialList.categorywood,MaterialList.iron,100));
		store.add(new MaterialTransform(MaterialList.meteorite,MaterialList.bone,MaterialList.meteoricIron,15));
	}
}

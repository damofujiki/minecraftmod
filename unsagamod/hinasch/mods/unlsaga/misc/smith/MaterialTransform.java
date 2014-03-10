package hinasch.mods.unlsaga.misc.smith;

import hinasch.mods.unlsaga.Unsaga;
import hinasch.mods.unlsaga.core.init.UnsagaMaterials;
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
				Unsaga.debug(trans.transformto.getUnlocalizedName()+"になりそう");
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
		store.add(new MaterialTransform(UnsagaMaterials.iron,UnsagaMaterials.categorywood,UnsagaMaterials.steel1,85));
		store.add(new MaterialTransform(UnsagaMaterials.steel1,UnsagaMaterials.categorywood,UnsagaMaterials.steel2,15));
		store.add(new MaterialTransform(UnsagaMaterials.iron,UnsagaMaterials.categorywood,UnsagaMaterials.steel2,15));
		store.add(new MaterialTransform(UnsagaMaterials.ironOre,UnsagaMaterials.categorywood,UnsagaMaterials.steel2,15));
		store.add(new MaterialTransform(UnsagaMaterials.silver,UnsagaMaterials.bestial,UnsagaMaterials.fairieSilver,15));
		store.add(new MaterialTransform(UnsagaMaterials.steel2,UnsagaMaterials.debris2,UnsagaMaterials.damascus,100));
		store.add(new MaterialTransform(UnsagaMaterials.debris,UnsagaMaterials.debris,UnsagaMaterials.debris2,100));
		store.add(new MaterialTransform(UnsagaMaterials.debris,UnsagaMaterials.bestial,UnsagaMaterials.debris2,100));
		store.add(new MaterialTransform(UnsagaMaterials.stone,UnsagaMaterials.categorywood,UnsagaMaterials.debris,15));
		store.add(new MaterialTransform(UnsagaMaterials.stone,UnsagaMaterials.silver,UnsagaMaterials.debris,15));
		store.add(new MaterialTransform(UnsagaMaterials.stone,UnsagaMaterials.obsidian,UnsagaMaterials.debris,15));
		store.add(new MaterialTransform(UnsagaMaterials.copperOre,UnsagaMaterials.categorywood,UnsagaMaterials.copper,100));
		store.add(new MaterialTransform(UnsagaMaterials.ironOre,UnsagaMaterials.categorywood,UnsagaMaterials.iron,100));
		store.add(new MaterialTransform(UnsagaMaterials.meteorite,UnsagaMaterials.bone,UnsagaMaterials.meteoricIron,15));
		store.add(new MaterialTransform(UnsagaMaterials.dragonHeart,UnsagaMaterials.iron,UnsagaMaterials.dragonHeart,100));
	}
}

package hinasch.mods.unlsagamagic.misc.spell.effect;

import hinasch.lib.WorldHelper;
import net.minecraft.world.World;

public abstract class AbstractSpell {

	public WorldHelper worldHelper;
	public AbstractSpell(){
		
	}
	
	public AbstractSpell(World world){
		this.worldHelper = new WorldHelper(world);
	}
	
	public void setWorldHelper(World world){
		this.worldHelper = new WorldHelper(world);
	}
	abstract public void doSpell(InvokeSpell parent);
	
	//abstract public SpellBase getNewInstance();
}

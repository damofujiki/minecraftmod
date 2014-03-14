package hinasch.mods.unlsaga.entity;

import hinasch.mods.unlsaga.Unsaga;
import hinasch.mods.unlsaga.core.init.UnsagaConfigs;
import hinasch.mods.unlsaga.entity.projectile.EntityArrowUnsaga;
import hinasch.mods.unlsaga.entity.projectile.EntityBarrett;
import hinasch.mods.unlsaga.entity.projectile.EntityBoulder;
import hinasch.mods.unlsaga.entity.projectile.EntityFireArrow;
import hinasch.mods.unlsaga.entity.projectile.EntityFlyingAxe;
import hinasch.mods.unlsaga.entity.projectile.EntitySolutionLiquid;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.Entity;
import cpw.mods.fml.common.registry.EntityRegistry;

public class EntityDataUnsaga {


	public static EntityDataUnsaga arrow = new EntityDataUnsaga(1,"arrow");
	public static EntityDataUnsaga barrett = new EntityDataUnsaga(2,"barrett");
	public static EntityDataUnsaga flyingAxe = new EntityDataUnsaga(4,"flyingAxe");
	public static EntityDataUnsaga fireArrow = new EntityDataUnsaga(3,"fireArrow");
	public static EntityDataUnsaga boulder = new EntityDataUnsaga(6,"boulder");
	public static EntityDataUnsaga solutionLiquid = new EntityDataUnsaga(7,"solutionLiquid");
	public static EntityDataUnsaga treasureSlime = new EntityDataUnsaga(8,"treasureSlime");
	public static EntityDataUnsaga golem = new EntityDataUnsaga(9,"golem");
	

	
	public static List<Class<? extends Entity>> entitySpawnableOnDebug = new ArrayList();
	
	public final int id;
	public final String name;
	public EntityDataUnsaga(int id,String name){
		this.id = id;
		this.name = name;
	}
	
	public static void registerEntities(){
		EntityRegistry.registerModEntity(EntityArrowUnsaga.class, arrow.name, arrow.id, Unsaga.instance, 250, 5, true);
		EntityRegistry.registerModEntity(EntityBarrett.class,barrett.name, barrett.id, Unsaga.instance, 250, 5, true);
		//fireball 3
		EntityRegistry.registerModEntity(EntityBoulder.class, boulder.name, boulder.id, Unsaga.instance, 250, 6, true);
		EntityRegistry.registerModEntity(EntityFireArrow.class, fireArrow.name, fireArrow.id, Unsaga.instance, 250, 6, true);
		EntityRegistry.registerModEntity(EntityFlyingAxe.class, flyingAxe.name, flyingAxe.id, Unsaga.instance, 250, 5, true);
		EntityRegistry.registerModEntity(EntityTreasureSlime.class, treasureSlime.name, treasureSlime.id, Unsaga.instance, 250, 5, true);
		EntityRegistry.registerModEntity(EntityGolemUnsaga.class, golem.name, golem.id, Unsaga.instance, 250, 5, true);
		EntityRegistry.registerModEntity(EntitySolutionLiquid.class, solutionLiquid.name, solutionLiquid.id, Unsaga.instance, 250, 6, true);
		if(UnsagaConfigs.module.isMagicEnabled()){
			Unsaga.getModuleMagicHandler();
		}
		addSpawnableInCreative(EntityTreasureSlime.class);
		addSpawnableInCreative(EntityGolemUnsaga.class);
	}
	
	public static void addSpawnableInCreative(Class<? extends Entity> entity){
		entitySpawnableOnDebug.add(entity);
	}
	

}

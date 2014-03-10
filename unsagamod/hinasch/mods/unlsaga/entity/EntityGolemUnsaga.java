package hinasch.mods.unlsaga.entity;

import hinasch.mods.unlsaga.core.init.UnsagaItems;
import hinasch.mods.unlsaga.core.init.UnsagaMaterials;
import hinasch.mods.unlsaga.misc.util.EnumUnsagaTools;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.world.World;

public class EntityGolemUnsaga extends EntityZombie{

	public EntityGolemUnsaga(World par1World) {
		super(par1World);
		this.setCurrentItemOrArmor(0, UnsagaItems.getItemStack(EnumUnsagaTools.SPEAR,UnsagaMaterials.steel1, 1, 0));
		this.setSize(1.6F, 2.8F);
	}

	
}

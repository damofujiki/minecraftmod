package hinasch.mods.unlsagamagic.misc.spell.effect;

import hinasch.mods.unlsaga.Unsaga;
import hinasch.mods.unlsaga.core.FiveElements;
import hinasch.mods.unlsaga.misc.util.DamageHelper;
import hinasch.mods.unlsaga.misc.util.DamageSourceUnsaga;
import hinasch.mods.unlsagamagic.misc.spell.Spells;

import java.util.List;

import com.hinasch.lib.AbstractScanner;
import com.hinasch.lib.HSLibs;
import com.hinasch.lib.PairID;
import com.hinasch.lib.XYZPos;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class ScannerElectricShock extends AbstractScanner{

	protected EntityLivingBase owner;
	
	public ScannerElectricShock(PairID compareBlock, XYZPos startpoint,EntityLivingBase owner) {
		super(compareBlock, startpoint);
		this.owner = owner;
		// TODO 自動生成されたコンストラクター・スタブ
	}

	@Override
	public void hook(World world, Block currentBlock, XYZPos currentPos) {
		// TODO 自動生成されたメソッド・スタブ
		AxisAlignedBB bb = HSLibs.getBounding(currentPos, 2.0D, 2.0D);
		if(!world.getEntitiesWithinAABB(EntityLivingBase.class, bb).isEmpty()){
			Unsaga.debug("範囲内に発見");
			List<EntityLivingBase> livings = world.getEntitiesWithinAABB(EntityLivingBase.class, bb);
			for(EntityLivingBase living:livings){
				DamageSourceUnsaga ds = new DamageSourceUnsaga(null,this.owner,Spells.thunderCrap.hurtLP,DamageHelper.Type.MAGIC);
				ds.setElement(FiveElements.EnumElement.WOOD);
				living.attackEntityFrom(ds, Spells.thunderCrap.hurtHP);
			}

			
		}
	}

}

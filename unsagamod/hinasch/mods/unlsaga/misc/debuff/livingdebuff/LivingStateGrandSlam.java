//package hinasch.mods.unlsaga.misc.debuff.livingdebuff;
//
//import hinasch.lib.HSLibs;
//import hinasch.lib.XYZPos;
//import hinasch.mods.unlsaga.Unsaga;
//import hinasch.mods.unlsaga.misc.debuff.Debuff;
//
//import java.util.Random;
//
//import net.minecraft.entity.EntityLivingBase;
//import net.minecraft.util.Vec3;
//import net.minecraft.world.World;
//
//public class LivingStateGrandSlam extends LivingState{
//
//
//	public XYZPos up;
//	public int amp;
//
//	public LivingStateGrandSlam(Debuff par1, int par2,XYZPos point,int amp) {
//		super(par1, par2, false);
//		this.amp = amp;
//		this.up = point;
//		// TODO 自動生成されたコンストラクター・スタブ
//
//	}
//
//	public String toString(){
//		return this.debuff.number+":"+this.remain+":"+this.up.x+":"+this.up.y+":"+this.up.z+":"+this.amp;
//	}
//
//	@Override
//	public void updateRemain(EntityLivingBase living){
//		super.updateRemain(living);
//
//	}
//
//	@Override
//	public void updateTick(EntityLivingBase living){
//		this.remain -= 1;
//
//
//		if(this.remain % 10==0 && this.amp>0){
//			
//			Unsaga.debug("grandslam");
//			this.amp -= 1;
//			Random rand = living.getRNG();
//			World world = living.worldObj;
//			Vec3 vec = Vec3.createVectorHelper(2.0D, 2.0D, 2.0D);
//			int bx = rand.nextInt(8)-4;
//			int bz = rand.nextInt(8)-4;
//			//this.up.x += bx;
//			//this.up.z += bz;
//			XYZPos bulgePoint = new XYZPos(up.x+bx,up.y,up.z+bz);
//			//PacketDispatcher.sendPacketToAllPlayers(PacketHandler.getSoundAtPos(bulgePoint, 2));
//			//bulgePoint.setBlockToHere(world, Block.blockClay.blockID);
//			//living.worldObj.playSound(par1, par3, par5, par7Str, par8, par9, par10);
//			if( HSLibs.getHeightFromPoint(world,bulgePoint,15,true).isPresent()){
//				int height = HSLibs.getHeightFromPoint(world, new XYZPos(up.x+bx,up.y,up.z+bz), 15, false).get();
//				//SkillAxe.makeBulge(world, new XYZPos(bulgePoint.x,height,bulgePoint.z));
//			}
//
//
//
//
//		}
//
//
//
//
//
//	}
//}

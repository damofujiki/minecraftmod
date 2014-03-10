//package hinasch.mods.unlsaga.misc.debuff.state;
//
//import hinasch.lib.XYZPos;
//import hinasch.mods.unlsaga.misc.debuff.Debuff;
//import hinasch.mods.unlsaga.misc.debuff.DebuffRegistry;
//import hinasch.mods.unlsaga.misc.debuff.livingdebuff.LivingDebuff;
//import hinasch.mods.unlsaga.misc.debuff.livingdebuff.LivingStateGrandSlam;
//
//public class StateGrandSlam extends State{
//
//	public StateGrandSlam(int num, String nameEn, String nameJp) {
//		super(num, nameEn, nameJp);
//		// TODO 自動生成されたコンストラクター・スタブ
//	}
//	
//	
//	@Override
//	public LivingDebuff init(String[] strs){
//		Debuff debuff = DebuffRegistry.getDebuff(Integer.valueOf(strs[0]));
//		int remain = Integer.valueOf(strs[1]);
//		XYZPos brokenpos = new XYZPos(0,0,0);
//		int amp = 0;
//		if(strs.length>2){
//			brokenpos = new XYZPos(Integer.valueOf(strs[2]),Integer.valueOf(strs[3]),Integer.valueOf(strs[4]));
//			amp = Integer.valueOf(strs[5]);
//		}
//
//		
//		return new LivingStateGrandSlam(debuff,remain,brokenpos,amp);
//	}
//}

package hinasch.mods.unlsaga.core.init;

import hinasch.mods.unlsaga.Unsaga;

public class UnsagaConfigs {

	
	public static int maxPlayerLP = 8;
	public static int maxMobLP = 3;
	
	public static int probGenChest = 2;
	
	public static boolean decipherAtSleep = true;
	protected static boolean isMagicEnabled = false;
	
	
	public static class module{
		public static boolean isLPEnabled(){
			return Unsaga.lpHandler.isLPEnabled();
		}
		
		public static boolean isMagicEnabled(){
			return isMagicEnabled;
		}
		
		public static void setMagicEnabled(boolean par1,Unsaga par2){
			if(par2!=null){
				isMagicEnabled = par1;
			}
		}
	}
	

}

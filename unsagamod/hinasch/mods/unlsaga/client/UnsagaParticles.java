package hinasch.mods.unlsaga.client;

import hinasch.mods.unlsaga.Unsaga;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.event.TextureStitchEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

//thanks:modding wiki japan
public class UnsagaParticles {
	String[] iiconNames = {"stone","leave","bubble"};
	IIcon iicons[];
	
	public static UnsagaParticles INSTANCE;
	
	public UnsagaParticles(){
		
	}
	
	public static UnsagaParticles getInstance(){
		if(INSTANCE==null){
			INSTANCE = new UnsagaParticles();
		}
		return INSTANCE;
	}
	@SubscribeEvent
	public void registerParticlesEvent(TextureStitchEvent.Pre e){
		if(e.map.getTextureType()==1){
			Unsaga.debug("パーティクル登録");
			this.getInstance().registerIcons(e.map);
		}
	}
	
	

	public void registerIcons(IIconRegister par1IconRegister) {
		iicons = new IIcon[iiconNames.length];
		for(int i = 0; i < iicons.length; ++i) {
			iicons[i] = par1IconRegister.registerIcon(Unsaga.domain+":particles/"+iiconNames[i]);
			Unsaga.debug("アイコン登録しました："+iicons[i]);
		}
	}
	

	public IIcon getIcon(String iiconName) {
		for(int i = 0; i < iiconNames.length; ++i) {
			if(iiconName.equalsIgnoreCase(iiconNames[i])) {
				Unsaga.debug("アイコン見つかった");
				return iicons[i];
			}
		}
		return null;
	}
}

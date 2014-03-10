package hinasch.mods.unlsaga.item.etc;


import hinasch.mods.unlsaga.Unsaga;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;

public class ItemBarrett extends Item{

	private String iconname;
	public ItemBarrett(String par2) {
		super();
		this.iconname = par2;
		
		// TODO Auto-generated constructor stub
	}

	@Override
	public void registerIcons(IIconRegister par1){
		this.itemIcon = par1.registerIcon(Unsaga.domain+":"+this.iconname);
	}
}


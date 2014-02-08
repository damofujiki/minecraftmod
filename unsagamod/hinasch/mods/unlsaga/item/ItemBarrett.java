package hinasch.mods.unlsaga.item;


import hinasch.mods.unlsaga.Unsaga;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;

public class ItemBarrett extends Item{

	private String iconname;
	public ItemBarrett(int par1,String par2) {
		super(par1);
		this.iconname = par2;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void registerIcons(IconRegister par1){
		this.itemIcon = par1.registerIcon(Unsaga.domain+":"+this.iconname);
	}
}


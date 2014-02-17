package hinasch.mods.unlsagamagic.item;

import hinasch.mods.unlsaga.Unsaga;
import hinasch.mods.unlsaga.network.PacketHandler;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.PacketDispatcher;

public class ItemBlender extends Item{

	public ItemBlender(int par1) {
		super(par1);
		// TODO 自動生成されたコンストラクター・スタブ
	}

    @Override
	public void registerIcons(IconRegister par1IconRegister) {
		this.itemIcon = par1IconRegister.registerIcon(Unsaga.domain+":book_blender");

	}
    
    
	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World,
			EntityPlayer par3EntityPlayer) {
		PacketDispatcher.sendPacketToServer(PacketHandler.getBlenderGuiPacket());

		return par1ItemStack;
	}
}

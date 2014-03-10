package hinasch.mods.unlsagamagic.item;

import hinasch.mods.unlsaga.Unsaga;
import hinasch.mods.unlsaga.network.packet.PacketGuiOpen;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemBlender extends Item{

	public ItemBlender() {
		super();
		// TODO 自動生成されたコンストラクター・スタブ
	}

    @Override
	public void registerIcons(IIconRegister par1IconRegister) {
		this.itemIcon = par1IconRegister.registerIcon(Unsaga.domain+":book_blender");

	}
    
    
	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World,
			EntityPlayer par3EntityPlayer) {
		PacketGuiOpen pg = new PacketGuiOpen(Unsaga.guiNumber.blender);
		Unsaga.packetPipeline.sendToServer(pg);
		//PacketDispatcher.sendPacketToServer(pg.getPacket());

		return par1ItemStack;
	}
}

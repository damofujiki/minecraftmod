package hinasch.mods.unlsaga.client.gui;

import hinasch.mods.unlsaga.Unsaga;
import hinasch.mods.unlsaga.inventory.ContainerBartering;
import hinasch.mods.unlsaga.misc.translation.Translation;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

public class GuiBartering extends GuiContainer{

	protected final ResourceLocation background = new ResourceLocation(Unsaga.domain + ":textures/gui/shop.png");
	protected ContainerBartering container;
	
	public GuiBartering(IMerchant merchant,World world,EntityPlayer ep) {
		super(new ContainerBartering(world,ep,merchant));
		// TODO 自動生成されたコンストラクター・スタブ
		this.container = (ContainerBartering)this.inventorySlots;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture(background);
		int xStart = width - xSize >> 1;
			int yStart = height - ySize >> 1;
			drawTexturedModalRect(xStart, yStart, 0, 0, xSize, ySize);
		
	}
	

	@Override
	protected void drawGuiContainerForegroundLayer(int par1,int par2)
	{


		//fontRenderer.drawString("Bartering", 8, 5, 0x404040);
		fontRenderer.drawString(Translation.localize("gui.bartering.amount")+this.container.getSellPrice(),8,48,0x404040);
	}
	

	@Override
	public void initGui()
	{
		super.initGui();
		int i = width  - xSize >> 1;
		int j = height - ySize >> 1;
//
//		
//		// ボタンを追加
//		// GuiButton(ボタンID, ボタンの始点X, ボタンの始点Y, ボタンの幅, ボタンの高さ, ボタンに表示する文字列)
//		buttonList.add(new GuiButton(DOFORGE, i + (18*5)+2, j + 16 +(18*2), 30, 19 , "Forge!"));
//		buttonList.add(new GuiButton(CATEGORY, i + (18*3)+2, j + 16 +(18*2), 31, 19 , "Category"));
		for (int k = 0; k < 9; ++k)
		{
			buttonList.add(new GuiButton(k, i+7+(k*18), j+85-(18*3), 18, 18 , " "));
			//this.merchandiseSlot[i] = new SlotMerchandise(this.invMerchant, i+10, 8 + i * 18, 63-(18*3));
			//this.addSlotToContainer(new SlotMerchandise(this.invMerchant, i+10, 8 + i * 18, 63-(18*3)));
		}

	}
	
	@Override
	protected void actionPerformed(GuiButton par1GuiButton)
	{	
		if(par1GuiButton!=null){

			if (!par1GuiButton.enabled)
			{
				return;
			}
			


			container.onButtonPushed(par1GuiButton.id);
			

			
		}
	}
}

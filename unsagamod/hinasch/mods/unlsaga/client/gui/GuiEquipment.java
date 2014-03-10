package hinasch.mods.unlsaga.client.gui;

import hinasch.mods.unlsaga.Unsaga;
import hinasch.mods.unlsaga.inventory.container.ContainerEquipment;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

public class GuiEquipment extends GuiContainer{

	protected EntityPlayer player;
	protected ResourceLocation guiPanel = new ResourceLocation(Unsaga.domain+":textures/gui/equipment.png");
	public GuiEquipment(EntityPlayer player) {
		super(new ContainerEquipment(player.inventory,player));
		this.player = player;
		// TODO 自動生成されたコンストラクター・スタブ
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture(guiPanel);
		int xStart = width - xSize >> 1;
			int yStart = height - ySize >> 1;
			drawTexturedModalRect(xStart, yStart, 0, 0, xSize, ySize);
	}

	@Override
	protected void actionPerformed(GuiButton par1GuiButton)
	{	
//		if(par1GuiButton!=null){
//
//			if (!par1GuiButton.enabled)
//			{
//				return;
//			}
//			
//			container.onButtonPushed(par1GuiButton.id);
//			
//
//			
//		}
	}
	
	@Override
	public void initGui()
	{
		super.initGui();
		int i = width  - xSize >> 1;
		int j = height - ySize >> 1;

		
		// ボタンを追加
		// GuiButton(ボタンID, ボタンの始点X, ボタンの始点Y, ボタンの幅, ボタンの高さ, ボタンに表示する文字列)
		//buttonList.add(new GuiButton(-2, i + (18*4)+2, j + 16 +(18*2), 30, 20 , "Forging"));

	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int par1,int par2)
	{


		fontRendererObj.drawString("Accessory", 8, 6, 0x404040);
		
		//fontRenderer.drawString("Result:"+getSpellStr(), 8, (ySize - 96) + 2, 0x404040);
	}
}

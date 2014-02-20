package hinasch.mods.unlsaga.client.gui;

import hinasch.mods.unlsaga.Unsaga;
import hinasch.mods.unlsaga.inventory.ContainerChestUnsaga;
import hinasch.mods.unlsaga.misc.translation.Translation;
import hinasch.mods.unlsaga.tileentity.TileEntityChestUnsaga;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

public class GuiChest extends GuiContainer{


	protected ResourceLocation guiPanel = new ResourceLocation(Unsaga.domain+":textures/gui/box.png");

	//アクセ情報、宝箱情報同期させる必要性
	public static final int OPEN = 1;
	public static final int UNLOCK = 2;
	public static final int DEFUSE = 3;
	public static final int DIVINATION = 4;

	public TileEntityChestUnsaga theChest;
	public EntityPlayer openPlayer;

	protected ContainerChestUnsaga container;

	public GuiChest(TileEntityChestUnsaga chest,EntityPlayer ep) {
		super(new ContainerChestUnsaga(chest,ep));
		this.container = (ContainerChestUnsaga) this.inventorySlots;
		this.theChest = chest;
		this.openPlayer = ep;
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
	public void initGui()
	{
		super.initGui();
		int i = width  - xSize >> 1;
		int j = height - ySize >> 1;
		//
		//		
		//		// ボタンを追加
		//		// GuiButton(ボタンID, ボタンの始点X, ボタンの始点Y, ボタンの幅, ボタンの高さ, ボタンに表示する文字列)
		buttonList.add(new GuiButton(OPEN, i + 32, j + 16 +(18*1), 30, 19 , Translation.localize("gui.chest.button.open")));
		buttonList.add(new GuiButton(UNLOCK, i + 32, j + 16 +(18*2), 31, 19 ,  Translation.localize("gui.chest.button.unlock")));
		buttonList.add(new GuiButton(DEFUSE, i + 32, j + 16 +(18*3), 30, 19 , Translation.localize("gui.chest.button.defuse")));
		buttonList.add(new GuiButton(DIVINATION, i + 32, j + 16 +(18*4), 30, 19 , Translation.localize("gui.chest.button.divination")));
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int par1,int par2)
	{


		fontRenderer.drawString(Translation.localize("word.chest")+" LV"+this.theChest.getChestLevel(), 8, 6, 0x404040);
		
		//fontRenderer.drawString("Result:"+getSpellStr(), 8, (ySize - 96) + 2, 0x404040);
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

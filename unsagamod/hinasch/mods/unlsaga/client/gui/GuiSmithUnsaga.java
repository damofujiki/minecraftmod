package hinasch.mods.unlsaga.client.gui;

import hinasch.mods.unlsaga.DebugUnsaga;
import hinasch.mods.unlsaga.Unsaga;
import hinasch.mods.unlsaga.inventory.container.ContainerSmithUnsaga;
import hinasch.mods.unlsaga.misc.util.EnumUnsagaTools;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

public class GuiSmithUnsaga extends GuiContainer{

	protected final ResourceLocation background = new ResourceLocation(Unsaga.domain + ":textures/gui/smith.png");
	protected ContainerSmithUnsaga container;
	protected FontRenderer font = Minecraft.getMinecraft().fontRenderer;
	protected DebugUnsaga debug;
	protected byte currentCategory;
	
	public final static int CATEGORY = -2;
	public final static int DOFORGE = -1;
	
	public GuiSmithUnsaga(IMerchant merchant,World world,EntityPlayer ep) {
		super(new ContainerSmithUnsaga(merchant,world,ep));

		this.currentCategory = 0;
		
		this.container = (ContainerSmithUnsaga) this.inventorySlots;
		// TODO 自動生成されたコンストラクター・スタブ
	}

	public int getCurrentCategory(){
		return (int)this.currentCategory;
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
	public void initGui()
	{
		super.initGui();
		int i = width  - xSize >> 1;
		int j = height - ySize >> 1;

		
		// ボタンを追加
		// GuiButton(ボタンID, ボタンの始点X, ボタンの始点Y, ボタンの幅, ボタンの高さ, ボタンに表示する文字列)
		buttonList.add(new GuiButton(DOFORGE, i + (18*5)+2, j + 16 +(18*2), 30, 19 , "Forge!"));
		buttonList.add(new GuiButton(CATEGORY, i + (18*3)+2, j + 16 +(18*2), 31, 19 , "Category"));

	}
	
	@Override
	protected void actionPerformed(GuiButton par1GuiButton)
	{	
		if(par1GuiButton!=null){

			if (!par1GuiButton.enabled)
			{
				return;
			}
			
			if(par1GuiButton.id==CATEGORY){
				this.currentCategory += 1;
				if(this.currentCategory>=EnumUnsagaTools.toolArray.size()){
					this.currentCategory = 0;
				}
			}

			container.onButtonPushed(par1GuiButton.id,this.currentCategory);
			

			
		}
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int par1,int par2)
	{


		fontRendererObj.drawString("Forging Weapons", 8, 5, 0x404040);
		fontRendererObj.drawString(EnumUnsagaTools.toolArray.get(this.currentCategory).toString(), 8, 42, 0x404040);
		//fontRenderer.drawString("Result:"+getSpellStr(), 8, (ySize - 96) + 2, 0x404040);
	}
	
//	protected String getCategoryString(){
//		List<EnumUnsagaWeapon> list = EnumUnsagaWeapon.weaponArray;
//		
//	}
}

package hinasch.mods.unlsagamagic.client.render;


import hinasch.mods.unlsaga.Unsaga;
import hinasch.mods.unlsagamagic.entity.EntityBoulder;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAnvil;
import net.minecraft.block.BlockDragonEgg;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderBoulder extends Render
{
    private RenderBlocks sandRenderBlocks = new RenderBlocks();
    private ResourceLocation resourceBoulder = new ResourceLocation(Unsaga.domain,"textures/items/stone.png");

    public RenderBoulder()
    {
        this.shadowSize = 0.5F;
    }

    /**
     * The actual render method that is used in doRender
     */
    public void doRenderFallingSand(EntityBoulder par1EntityFallingSand, double par2, double par4, double par6, float par8, float par9)
    {
    	int metadata = 0;
        GL11.glPushMatrix();
        GL11.glTranslatef((float)par2, (float)par4, (float)par6);
        Block block = Block.cobblestone;
        World world = par1EntityFallingSand.worldObj;
        GL11.glDisable(GL11.GL_LIGHTING);
        Tessellator tessellator;

        if (block instanceof BlockAnvil && block.getRenderType() == 35)
        {
            this.sandRenderBlocks.blockAccess = world;
            tessellator = Tessellator.instance;
            tessellator.startDrawingQuads();
            tessellator.setTranslation((double)((float)(-MathHelper.floor_double(par1EntityFallingSand.posX)) - 0.5F), (double)((float)(-MathHelper.floor_double(par1EntityFallingSand.posY)) - 0.5F), (double)((float)(-MathHelper.floor_double(par1EntityFallingSand.posZ)) - 0.5F));
            this.sandRenderBlocks.renderBlockAnvilMetadata((BlockAnvil)block, MathHelper.floor_double(par1EntityFallingSand.posX), MathHelper.floor_double(par1EntityFallingSand.posY), MathHelper.floor_double(par1EntityFallingSand.posZ), metadata);
            tessellator.setTranslation(0.0D, 0.0D, 0.0D);
            tessellator.draw();
        }
        else if (block.getRenderType() == 27)
        {
            this.sandRenderBlocks.blockAccess = world;
            tessellator = Tessellator.instance;
            tessellator.startDrawingQuads();
            tessellator.setTranslation((double)((float)(-MathHelper.floor_double(par1EntityFallingSand.posX)) - 0.5F), (double)((float)(-MathHelper.floor_double(par1EntityFallingSand.posY)) - 0.5F), (double)((float)(-MathHelper.floor_double(par1EntityFallingSand.posZ)) - 0.5F));
            this.sandRenderBlocks.renderBlockDragonEgg((BlockDragonEgg)block, MathHelper.floor_double(par1EntityFallingSand.posX), MathHelper.floor_double(par1EntityFallingSand.posY), MathHelper.floor_double(par1EntityFallingSand.posZ));
            tessellator.setTranslation(0.0D, 0.0D, 0.0D);
            tessellator.draw();
        }
        else if (block != null)
        {
            this.sandRenderBlocks.setRenderBoundsFromBlock(block);
            this.sandRenderBlocks.renderBlockSandFalling(block, world, MathHelper.floor_double(par1EntityFallingSand.posX), MathHelper.floor_double(par1EntityFallingSand.posY), MathHelper.floor_double(par1EntityFallingSand.posZ), metadata);
        }

        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glPopMatrix();
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void doRender(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
    {
        this.doRenderFallingSand((EntityBoulder)par1Entity, par2, par4, par6, par8, par9);
    }

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		// TODO 自動生成されたメソッド・スタブ
		return TextureMap.locationBlocksTexture;
	}
}

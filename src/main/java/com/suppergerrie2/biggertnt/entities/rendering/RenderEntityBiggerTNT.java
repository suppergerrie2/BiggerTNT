package com.suppergerrie2.biggertnt.entities.rendering;

import com.suppergerrie2.biggertnt.entities.EntityBiggerTNT;
import com.suppergerrie2.biggertnt.init.ModBlocks;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

//This is an almost exact copy of vanilla, the only difference is thatwe use ModBlocks.ExplosionBlock instead of Blocks.TNT
public class RenderEntityBiggerTNT extends Render<EntityBiggerTNT> {

	public RenderEntityBiggerTNT(RenderManager renderManager) {
		super(renderManager);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityBiggerTNT entity) {
        return TextureMap.LOCATION_BLOCKS_TEXTURE;
	}

	@Override
	public void doRender(EntityBiggerTNT entity, double x, double y, double z, float entityYaw, float partialTicks)
	{
		BlockRendererDispatcher blockrendererdispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
		GlStateManager.pushMatrix();
		GlStateManager.translate((float)x, (float)y + 0.5F, (float)z);

		if ((float)entity.getFuse() - partialTicks + 1.0F < 10.0F)
		{
			//Just before the tnt explodes it grows in size, this line calculates how close to explosion the tnt is (it will be 1 when it explodes)
			float growSizeScale = 1.0F - ((float)entity.getFuse() - partialTicks + 1.0F) / 10.0F;
		
			//Make sure the value is always between 0 and 1
			growSizeScale = MathHelper.clamp(growSizeScale, 0.0F, 1.0F);
			growSizeScale = growSizeScale * growSizeScale;
			growSizeScale = growSizeScale * growSizeScale;
			float scale = 1.0F + growSizeScale * 0.3F;
			GlStateManager.scale(scale, scale, scale);
		}

		float alpha = (1.0F - ((float)entity.getFuse() - partialTicks + 1.0F) / 100.0F) * 0.8F;
		this.bindEntityTexture(entity);
		GlStateManager.rotate(-90.0F, 0.0F, 1.0F, 0.0F);
		GlStateManager.translate(-0.5F, -0.5F, 0.5F);
		blockrendererdispatcher.renderBlockBrightness(ModBlocks.explosionBlock.getDefaultState(), entity.getBrightness());
		GlStateManager.translate(0.0F, 0.0F, 1.0F);

		if (this.renderOutlines)
		{
			GlStateManager.enableColorMaterial();
			GlStateManager.enableOutlineMode(this.getTeamColor(entity));
			blockrendererdispatcher.renderBlockBrightness(ModBlocks.explosionBlock.getDefaultState(), 1.0F);
			GlStateManager.disableOutlineMode();
			GlStateManager.disableColorMaterial();
		}
		//Color blinking
		else if (entity.getFuse() / 5 % 2 == 0)
		{
			GlStateManager.disableTexture2D();
			GlStateManager.disableLighting();
			GlStateManager.enableBlend();
			GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.DST_ALPHA);
			GlStateManager.color(1.0F, 1.0F, 1.0F, alpha);
			GlStateManager.doPolygonOffset(-3.0F, -3.0F);
			GlStateManager.enablePolygonOffset();
			blockrendererdispatcher.renderBlockBrightness(ModBlocks.explosionBlock.getDefaultState(), 1.0F);
			GlStateManager.doPolygonOffset(0.0F, 0.0F);
			GlStateManager.disablePolygonOffset();
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			GlStateManager.disableBlend();
			GlStateManager.enableLighting();
			GlStateManager.enableTexture2D();
		}

		GlStateManager.popMatrix();
		super.doRender(entity, x, y, z, entityYaw, partialTicks);
	}


}

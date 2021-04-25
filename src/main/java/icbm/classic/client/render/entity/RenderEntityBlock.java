package icbm.classic.client.render.entity;

import icbm.classic.content.entity.EntityFlyingBlock;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import javax.annotation.Nullable;

@OnlyIn(Dist.CLIENT)
public class RenderEntityBlock extends Render<EntityFlyingBlock> {

	public RenderEntityBlock(RenderManager renderManager) {
		super(renderManager);
		this.shadowSize = 0.5F;
	}

	@Override
	public void doRender(EntityFlyingBlock entity, double x, double y, double z, float entityYaw, float partialTicks) {
		super.doRender(entity, x, y, z, entityYaw, partialTicks);

		final BlockState blockState = entity.getBlockState();
		final BlockRendererDispatcher blockrendererdispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();

		GlStateManager.pushMatrix();
		GlStateManager.translate((float) x, (float) y + 0.5F, (float) z);

		this.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);

		GlStateManager.rotate(-90.0F, 0.0F, 1.0F, 0.0F);
		GlStateManager.translate(-0.5F, -0.5F, 0.5F);
		blockrendererdispatcher.renderBlockBrightness(blockState, entity.getBrightness());
		GlStateManager.translate(0.0F, 0.0F, 1.0F);

		GlStateManager.popMatrix();
	}

	@Nullable
	@Override
	protected ResourceLocation getEntityTexture(EntityFlyingBlock entity) {
		return TextureMap.LOCATION_BLOCKS_TEXTURE;
	}

}

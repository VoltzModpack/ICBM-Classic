package icbm.classic.client.render.entity;

import icbm.classic.ICBMConstants;
import icbm.classic.client.models.mobs.ModelSkeletonXmas;
import icbm.classic.content.entity.mobs.EntityXmasSkeleton;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

@OnlyIn(Dist.CLIENT)
public class RenderSkeletonXmas extends RenderLiving<EntityXmasSkeleton> {

	private static final ResourceLocation TEXTURE = new ResourceLocation(ICBMConstants.DOMAIN, "textures/entity/skeleton/skeleton.hat.green.png");

	public RenderSkeletonXmas(RenderManager renderManagerIn) {
		super(renderManagerIn, new ModelSkeletonXmas(), 0.5F);
		//this.addLayer(new LayerHeldItemSkeletonXmas(this));
	}

	@Override
	public void transformHeldFull3DItemLayer() {
		GlStateManager.translate(0.09375F, 0.1875F, 0.0F);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityXmasSkeleton entity) {
		return TEXTURE;
	}

}

package icbm.classic.client.render.entity;

import icbm.classic.content.entity.PlayerEntitySeat;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

@OnlyIn(Dist.CLIENT)
public class RenderSeat extends Render<PlayerEntitySeat> {

	public RenderSeat(RenderManager renderManager) {
		super(renderManager);
		this.shadowSize = 0.0F;
	}

	@Override
	public void doRender(PlayerEntitySeat seat, double x, double y, double z, float entityYaw, float partialTicks) {
		super.doRender(seat, x, y, z, entityYaw, partialTicks);
	}

	@Override
	protected ResourceLocation getEntityTexture(PlayerEntitySeat entity) {
		return null;
	}

}

package icbm.classic.content.blocks.emptower;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import icbm.classic.ICBMConstants;
import icbm.classic.client.models.ModelEmpTower;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * Created by Dark(DarkGuardsman, Robert) on 1/10/2017.
 */
public class TESREMPTower extends TileEntityRenderer<TileEMPTower> {

	public static final ResourceLocation TEXTURE_FILE = new ResourceLocation(ICBMConstants.DOMAIN, "textures/models/" + "emp_tower.png");
	public static final ModelEmpTower MODEL = new ModelEmpTower();

	public TESREMPTower(TileEntityRendererDispatcher rendererDispatcherIn) {
		super(rendererDispatcherIn);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void render(TileEMPTower tower, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
		GlStateManager.pushMatrix();
		GlStateManager.translatef(tower.getPos().getX() + 0.5F, tower.getPos().getY() + 1.5F, tower.getPos().getZ() + 0.5F);
		Minecraft.getInstance().getTextureManager().bindTexture(TEXTURE_FILE);
		GlStateManager.rotatef(180F, 0.0F, 0.0F, 1.0F);
		MODEL.render(tower.rotation, 0.0625F);
		GlStateManager.popMatrix();
	}

}

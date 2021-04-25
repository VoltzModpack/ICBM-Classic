package icbm.classic.client.render.entity;

import icbm.classic.content.entity.EntityGrenade;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderItemEntity;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

@OnlyIn(Dist.CLIENT)
public class RenderGrenade extends Render<EntityGrenade> {

	private ItemEntity entityItem;
	private RenderItemEntity renderItemEntity;

	public RenderGrenade(RenderManager renderManagerIn) {
		super(renderManagerIn);
		renderItemEntity = new RenderItemEntity(renderManagerIn, Minecraft.getMinecraft().getRenderItem());
		this.shadowSize = 0.15F;
		this.shadowOpaque = 0.75F;
	}

	@Override
	public void doRender(EntityGrenade entity, double x, double y, double z, float par8, float par9) {
		setupFakeItem(entity);
		renderItemEntity.doRender(entityItem, x, y, z, par8, par9);
	}

	protected void setupFakeItem(EntityGrenade entity) {

		//Create fake item if missing
		if (entityItem == null) {
			entityItem = new ItemEntity(entity.world);
		}

		//Apply data from entity
		entityItem.setWorld(entity.world);
		entityItem.setPosition(entity.getPosX(), entity.getPosY(), entity.getPosZ());
		entityItem.setItem(entity.explosive.toStack());
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityGrenade entity) {
		return TextureMap.LOCATION_BLOCKS_TEXTURE;
	}

}

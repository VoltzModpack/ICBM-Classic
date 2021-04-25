package icbm.classic.content.potion;

import icbm.classic.ICBMClassic;
import icbm.classic.lib.transform.vector.Pos;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.monster.ZombifiedPiglinEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.monster.ZombieVillagerEntity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.Potion;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;

import java.util.List;

public class PoisonContagion extends CustomPotion {

	public static Potion INSTANCE;

	public PoisonContagion(boolean isBadEffect, int color, int id, String name) {
		super(isBadEffect, color, id, name);
		this.setIconIndex(6, 0);
	}

	@Override
	public void performEffect(LivingEntity entityLiving, int amplifier) {
		World world = entityLiving.world;
		if (!(entityLiving instanceof ZombieEntity) && !(entityLiving instanceof ZombifiedPiglinEntity)) {
			entityLiving.attackEntityFrom(DamageSource.MAGIC, 1);
		}

		if (entityLiving.world.rand.nextFloat() > 0.8) {
			int r = 13;
			AxisAlignedBB entitySurroundings = new AxisAlignedBB(entityLiving.posX - r, entityLiving.posY - r, entityLiving.posZ - r, entityLiving.posX + r, entityLiving.posY + r, entityLiving.posZ + r);
			List<LivingEntity> entities = entityLiving.world.getEntitiesWithinAABB(LivingEntity.class, entitySurroundings);

			for (LivingEntity entity : entities) {
				if (entity != null && entity != entityLiving) {
					if (entity instanceof PigEntity) {
						ZombifiedPiglinEntity newEntity = new ZombifiedPiglinEntity(entity.world);
						newEntity.setLocationAndAngles(entity.posX, entity.posY, entity.posZ, entity.rotationYaw, entity.rotationPitch);

						if (!entity.world.isRemote) {
							entity.world.addEntity(newEntity);
						}
						entity.setDead();
					} else if (entity instanceof VillagerEntity) {
						if ((world.getDifficulty() == Difficulty.NORMAL || world.getDifficulty() == Difficulty.HARD)) {

							VillagerEntity entityvillager = (VillagerEntity) entity;
							ZombieVillagerEntity entityzombievillager = new ZombieVillagerEntity(world);
							entityzombievillager.copyLocationAndAnglesFrom(entityvillager);
							world.removeEntity(entityvillager);
							entityzombievillager.onInitialSpawn(world.getDifficultyForLocation(new BlockPos(entityzombievillager)), null);
							entityzombievillager.setProfession(entityvillager.getProfession());
							entityzombievillager.setChild(entityvillager.isChild());
							entityzombievillager.setNoAI(entityvillager.isAIDisabled());

							if (entityvillager.hasCustomName()) {
								entityzombievillager.setCustomNameTag(entityvillager.getCustomNameTag());
								entityzombievillager.setAlwaysRenderNameTag(entityvillager.getAlwaysRenderNameTag());
							}

							world.addEntity(entityzombievillager);
							world.playEvent((PlayerEntity) null, 1026, new BlockPos(entity), 0);
						}
						entity.setDead();
					}

					ICBMClassic.contagios_potion.poisonEntity(new Pos(entity), entity);
				}
			}
		}
	}

	@Override
	public boolean isReady(int duration, int amplifier) {
		if (duration % (20 * 2) == 0) {
			return true;
		}

		return false;
	}

}

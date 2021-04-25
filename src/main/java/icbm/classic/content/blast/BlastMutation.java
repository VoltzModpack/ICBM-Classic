package icbm.classic.content.blast;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.ZombieEntityVillager;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.util.math.AxisAlignedBB;

import java.util.List;

public class BlastMutation extends Blast {

	@Override
	public boolean doExplode(int callCount) {
		if (!this.world().isRemote) {
			AxisAlignedBB bounds = new AxisAlignedBB(location.x() - this.getBlastRadius(), location.y() - this.getBlastRadius(), location.z() - this.getBlastRadius(), location.x() + this.getBlastRadius(), location.y() + this.getBlastRadius(), location.z() + this.getBlastRadius());
			List<LivingEntity> entitiesNearby = world().getEntitiesWithinAABB(LivingEntity.class, bounds);

			for (LivingEntity entity : entitiesNearby) {
				if (entity instanceof EntityPig) {
					EntityPigZombie newEntity = new EntityPigZombie(world());
					newEntity.preventEntitySpawning = true;
					newEntity.setPosition(entity.posX, entity.posY, entity.posZ);
					entity.setDead();
					world().spawnEntity(newEntity);
				} else if (entity instanceof EntityVillager) {
					ZombieEntityVillager newEntity = new ZombieEntityVillager(world());
					newEntity.preventEntitySpawning = true;
					newEntity.setPosition(entity.posX, entity.posY, entity.posZ);
					newEntity.setForgeProfession(((EntityVillager) entity).getProfessionForge());
					entity.setDead();
					world().spawnEntity(newEntity);
				}
			}
		}
		return false;
	}

	@Override //disable the sound for this explosive
	protected void playExplodeSound() {
	}

}

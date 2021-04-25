package icbm.classic.content.potion;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.potion.Potion;
import net.minecraft.util.DamageSource;

public class PoisonToxin extends CustomPotion {

	public static Potion INSTANCE;

	public PoisonToxin(boolean isBadEffect, int color, int id, String name) {
		super(isBadEffect, color, id, name);
		this.setIconIndex(6, 0);
	}

	@Override
	public void performEffect(LivingEntity par1LivingEntity, int amplifier) {
		if (!(par1LivingEntity instanceof ZombieEntity) && !(par1LivingEntity instanceof EntityPigZombie)) {
			par1LivingEntity.attackEntityFrom(DamageSource.MAGIC, 1);
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

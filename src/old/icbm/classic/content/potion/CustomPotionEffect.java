package icbm.classic.content.potion;

import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraft.potion.Potion;

import java.util.ArrayList;
import java.util.List;

public class CustomPotionEffect extends Effect {

	public CustomPotionEffect(EffectType effect, int duration, int amplifier) {
		super(effect, duration, amplifier);
	}

	/**
	 * Creates a potion effect with custom curable items.
	 *
	 * @param curativeItems - ItemStacks that can cure this potion effect
	 */
	public CustomPotionEffect(Potion potionID, int duration, int amplifier, List<ItemStack> curativeItems) {
		super(potionID, duration, amplifier);

		if (curativeItems == null) {
			this.setCurativeItems(new ArrayList<ItemStack>());
		} else {
			this.setCurativeItems(curativeItems);
		}
	}

}

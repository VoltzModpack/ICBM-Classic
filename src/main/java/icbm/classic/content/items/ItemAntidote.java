package icbm.classic.content.items;

import icbm.classic.prefab.item.ItemBase;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemAntidote extends ItemBase {

	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World world, LivingEntity entityLiving) {
		if (!world.isRemote) {
			entityLiving.clearActivePotions();
		}
		if (!(entityLiving instanceof PlayerEntity) || !((PlayerEntity) entityLiving).capabilities.isCreativeMode) {
			stack.shrink(1);
		}
		return stack;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack par1ItemStack) {
		return 32;
	}

	@Override
	public EnumAction getItemUseAction(ItemStack par1ItemStack) {
		return EnumAction.EAT;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, EnumHand handIn) {
		ItemStack itemstack = playerIn.getHeldItem(handIn);
		playerIn.setActiveHand(handIn);
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
	}

}

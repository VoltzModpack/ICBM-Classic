package icbm.classic.content.items;

import icbm.classic.api.events.RemoteTriggerEvent;
import icbm.classic.lib.NBTConstants;
import icbm.classic.lib.radio.RadioRegistry;
import icbm.classic.prefab.FakeRadioSender;
import icbm.classic.prefab.item.ItemICBMElectrical;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import java.util.List;

/**
 * Remotely triggers missile launches on a set frequency, call back ID, and pass key. Will not funciton if any of those
 * data points is missing.
 * <p>
 * <p>
 * Created by Dark(DarkGuardsman, Robert) on 3/26/2016.
 */
public class ItemRemoteDetonator extends ItemICBMElectrical {

	public static final int ENERGY = 1000;

	public ItemRemoteDetonator() {
		super("remoteDetonator");
		this.setHasSubtypes(true);
		this.setMaxStackSize(1);
		this.setNoRepair();
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand handIn) {
		ItemStack stack = player.getHeldItem(handIn);
		if (!world.isRemote) {
			if (!MinecraftForge.EVENT_BUS.post(new RemoteTriggerEvent(world, player, stack))) //event was not canceled
				RadioRegistry.popMessage(world, new FakeRadioSender(player, stack, 2000), getBroadCastHz(stack), "activateLauncher");
		}
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
	}

	@Override
	public boolean doesSneakBypassUse(ItemStack stack, net.minecraft.world.IBlockReader world, BlockPos pos, PlayerEntity player) {
		return true;
	}

	@OnlyIn(Dist.CLIENT)
	public void addInformation(ItemStack stack, PlayerEntity player, List list, boolean b) {
		list.add("Fires missiles remotely");
		list.add("Right click launcher screen to encode");
	}

	/**
	 * Gets the frequency this item broadcasts information on
	 *
	 * @param stack - this item
	 * @return frequency
	 */
	public float getBroadCastHz(ItemStack stack) {
		if (stack.getTagCompound() != null && stack.getTagCompound().contains(NBTConstants.HZ)) {
			return stack.getTagCompound().getFloat(NBTConstants.HZ);
		}
		return 0;
	}

	/**
	 * Sets the frequency of this item
	 *
	 * @param stack - this item
	 * @param hz    - value to set
	 */
	public void setBroadCastHz(ItemStack stack, float hz) {
		if (stack.getTagCompound() == null) {
			stack.putCompound(new CompoundNBT());
		}
		stack.getTagCompound().putFloat(NBTConstants.HZ, hz);
	}

}

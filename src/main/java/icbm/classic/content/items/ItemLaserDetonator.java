package icbm.classic.content.items;

import icbm.classic.ICBMClassic;
import icbm.classic.api.ICBMClassicHelpers;
import icbm.classic.api.events.LaserRemoteTriggerEvent;
import icbm.classic.lib.NBTConstants;
import icbm.classic.lib.network.IPacket;
import icbm.classic.lib.network.IPacketIDReceiver;
import icbm.classic.lib.network.packet.PacketPlayerItem;
import icbm.classic.lib.radio.RadioRegistry;
import icbm.classic.lib.transform.vector.Pos;
import icbm.classic.prefab.FakeRadioSender;
import icbm.classic.prefab.item.ItemICBMElectrical;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import java.util.List;

/**
 * Extended version of {@link ItemRemoteDetonator} that can target blocks in a line of sight.
 * <p>
 * <p>
 * Created by Dark(DarkGuardsman, Robert) on 3/26/2016.
 */
public class ItemLaserDetonator extends ItemICBMElectrical implements IPacketIDReceiver {

	public ItemLaserDetonator() {
		super("laserDetonator");
		this.setHasSubtypes(true);
		this.setMaxStackSize(1);
		this.setNoRepair();
	}

	private final int maxCooldownTicks = 20;
	private int cooldownRemaining = 0;

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, EnumHand handIn) {
		ItemStack stack = player.getHeldItem(handIn);
		if (world.isRemote && cooldownRemaining <= 0) {
			cooldownRemaining = maxCooldownTicks;
			RayTraceResult objectMouseOver = player.rayTrace(200, 1);
			if (objectMouseOver.typeOfHit != RayTraceResult.Type.MISS) // ignore failed raytraces
			{
				TileEntity tileEntity = world.getTileEntity(objectMouseOver.getBlockPos());
				if (!(ICBMClassicHelpers.isLauncher(tileEntity, null))) {
					ICBMClassic.packetHandler.sendToServer(new PacketPlayerItem(player).addData(objectMouseOver.getBlockPos()));
				}
			}// TODO else: add message stating that the raytrace failed
		}
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
	}

	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World world, LivingEntity entityLiving) {

		if (world.isRemote) // when releasing the right mouse button, reset the cooldown to allow immediate reuse of item
			cooldownRemaining = 0;
		return super.onItemUseFinish(stack, world, entityLiving);
	}

	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected) {
		if (world.isRemote && cooldownRemaining > 0) // when holding the right mouse button, trigger item use every second
			cooldownRemaining--;

		super.onUpdate(stack, world, entity, itemSlot, isSelected);
	}

	@Override
	public boolean read(ByteBuf buf, int id, PlayerEntity player, IPacket packet) {
		ItemStack stack = player.inventory.getCurrentItem();
		if (stack != null && stack.getItem() == this) {
			if (!player.world.isRemote) {
				int x = buf.readInt();
				int y = buf.readInt();
				int z = buf.readInt();
				LaserRemoteTriggerEvent event = new LaserRemoteTriggerEvent(player.world, new BlockPos(x, y, z), player);

				if (MinecraftForge.EVENT_BUS.post(event)) //event was canceled
					return false;

				if (event.pos == null) //someone set the pos in the event to null, use original data
					RadioRegistry.popMessage(player.world, new FakeRadioSender(player, stack, 2000), getBroadCastHz(stack), "activateLauncherWithTarget", new Pos(x, y, z));
				else
					RadioRegistry.popMessage(player.world, new FakeRadioSender(player, stack, 2000), getBroadCastHz(stack), "activateLauncherWithTarget", new Pos(event.pos.getX(), event.pos.getY(), event.pos.getZ()));
			} else {
				player.sendMessage(new TextComponentString("Not encoded with launch data! Right click on launcher screen to encode."));
			}
		}
		return true;
	}

	@Override
	public boolean doesSneakBypassUse(ItemStack stack, net.minecraft.world.IBlockAccess world, BlockPos pos, PlayerEntity player) {
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

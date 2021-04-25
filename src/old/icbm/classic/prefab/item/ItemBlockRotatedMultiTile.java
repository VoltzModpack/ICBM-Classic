package icbm.classic.prefab.item;

import icbm.classic.content.blocks.multiblock.MultiBlockHelper;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;
import java.util.function.Function;

/**
 * Handles placement check for asymmetric multi-block structures that need rotation
 * <p>
 * <p>
 * Created by Dark(DarkGuardsman, Robert) on 3/16/2018.
 */
public class ItemBlockRotatedMultiTile extends ItemBlockSubTypes {

	//Wrapper for getting the multi-block data for the rotation
	protected final Function<Direction, List<BlockPos>> multiBlockGetter;

	public ItemBlockRotatedMultiTile(Block block, Function<Direction, List<BlockPos>> multiBlockGetter) {
		super(block);
		this.multiBlockGetter = multiBlockGetter;
	}

	@Override
	protected boolean canPlace(PlayerEntity player, World worldIn, BlockPos pos, ItemStack itemstack, Direction facing, float hitX, float hitY, float hitZ) {
		if (super.canPlace(player, worldIn, pos, itemstack, facing, hitX, hitY, hitZ)) {
			List<BlockPos> multi_blocks = multiBlockGetter.apply(player.getHorizontalFacing());
			if (multi_blocks != null) {
				return MultiBlockHelper.canBuild(worldIn, pos, multi_blocks, true);
			}
		}
		return false;
	}

}

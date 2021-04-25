package icbm.classic.content.blocks.launcher.base;

import icbm.classic.api.EnumTier;
import icbm.classic.api.tile.multiblock.IMultiTileHost;
import icbm.classic.content.blocks.multiblock.MultiBlockHelper;
import icbm.classic.prefab.tile.BlockICBM;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.BlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

/**
 * Created by Dark(DarkGuardsman, Robert) on 1/8/2018.
 */
public class BlockLauncherBase extends BlockICBM {

	public BlockLauncherBase() {
		super("launcherbase", Material.IRON);
		this.setDefaultState(this.blockState.getBaseState().withProperty(ROTATION_PROP, Direction.NORTH).withProperty(TIER_PROP, EnumTier.ONE));
		this.blockHardness = 10f;
		this.blockResistance = 10f;
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, BlockState state, PlayerEntity playerIn, Hand hand, Direction facing, float hitX, float hitY, float hitZ) {
		TileEntity tile = worldIn.getTileEntity(pos);
		if (tile instanceof TileLauncherBase) {
			return ((TileLauncherBase) tile).onPlayerRightClick(playerIn, hand, playerIn.getHeldItem(hand));
		}
		return false;
	}

	@Override
	public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
		items.add(new ItemStack(this, 1, EnumTier.ONE.ordinal()));
		items.add(new ItemStack(this, 1, EnumTier.TWO.ordinal()));
		items.add(new ItemStack(this, 1, EnumTier.THREE.ordinal()));
	}

	@Override
	public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, Direction side) {
		return super.canPlaceBlockOnSide(worldIn, pos, side);
	}

	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		return super.canPlaceBlockAt(worldIn, pos);
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, ROTATION_PROP, TIER_PROP);
	}

	@Override
	public BlockState getStateForPlacement(World world, BlockPos pos, Direction facing, float hitX, float hitY, float hitZ, int meta, LivingEntity placer, Hand hand) {
		BlockState state = super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer, hand);
		ItemStack stack = placer.getHeldItem(hand);

		//Set tier
		state = state.withProperty(TIER_PROP, EnumTier.get(stack.getDamage()));

		return state;
	}

	@Override
	public BlockState getActualState(BlockState state, IBlockReader worldIn, BlockPos pos) {
		EnumTier tier = EnumTier.ONE;
		TileEntity tile = worldIn.getTileEntity(pos);
		if (tile instanceof TileLauncherBase) {
			tier = ((TileLauncherBase) tile)._tier;
		}
		return state.withProperty(TIER_PROP, tier);
	}

	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, BlockState state, LivingEntity entityLiving, ItemStack stack) {
		TileEntity tile = world.getTileEntity(pos);
		if (tile instanceof TileLauncherBase) {
			//Set tier data
			((TileLauncherBase) tile)._tier = EnumTier.get(stack.getDamage());

			//Build multiblock
			MultiBlockHelper.buildMultiBlock(world, (IMultiTileHost) tile, true, true);
			//TODO if can't place, break and drop item
		}
	}

	@Override
	public ItemStack getPickBlock(BlockState state, RayTraceResult target, World world, BlockPos pos, PlayerEntity player) {
		TileEntity tileEntity = world.getTileEntity(pos);

		if (tileEntity instanceof TileLauncherBase) {
			switch (((TileLauncherBase) tileEntity)._tier) {
				case TWO:
					return new ItemStack(this, 1, EnumTier.TWO.ordinal());
				case THREE:
					return new ItemStack(this, 1, EnumTier.THREE.ordinal());
				default:
					return new ItemStack(this, 1, EnumTier.ONE.ordinal());
			}
		}

		return new ItemStack(this, 1, EnumTier.ONE.ordinal());
	}

	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, BlockState state) {

	}

	@Override
	public boolean isOpaqueCube(BlockState state) {
		return false;
	}

	@Override
	public boolean isFullCube(BlockState state) {
		return false;
	}

	@Override
	public int damageDropped(BlockState state) {
		return state.getValue(TIER_PROP).ordinal();
	}

	@Override
	public EnumBlockRenderType getRenderType(BlockState state) {
		return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
	}

	@Nullable
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileLauncherBase();
	}

}

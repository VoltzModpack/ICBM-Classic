package icbm.classic.content.blocks;

import icbm.classic.ICBMConstants;
import net.minecraft.block.BlockPressurePlate;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.BlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.BlockRenderLayer;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

public class BlockGlassPressurePlate extends BlockPressurePlate {

	public BlockGlassPressurePlate() {
		super(Material.GLASS, Sensitivity.EVERYTHING);
		this.setTickRandomly(true);
		this.setResistance(1F);
		this.setHardness(0.3F);
		this.setSoundType(SoundType.GLASS);
		this.setRegistryName(ICBMConstants.PREFIX + "glassPressurePlate");
		this.setTranslationKey(ICBMConstants.PREFIX + "glassPressurePlate");
		this.setCreativeTab(CreativeTabs.REDSTONE);
		this.setDefaultState(getDefaultState().withProperty(POWERED, false));
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public BlockRenderLayer getRenderLayer() {
		return BlockRenderLayer.CUTOUT;
	}

	@Override
	public boolean isOpaqueCube(BlockState state) {
		return false;
	}

}

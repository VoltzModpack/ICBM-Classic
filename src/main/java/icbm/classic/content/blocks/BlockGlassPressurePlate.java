package icbm.classic.content.blocks;

import icbm.classic.ICBMConstants;
import net.minecraft.block.BlockState;
import net.minecraft.block.PressurePlateBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class BlockGlassPressurePlate extends PressurePlateBlock {

	public BlockGlassPressurePlate() {
		super(Material.GLASS, Sensitivity.EVERYTHING);
		this.setTickRandomly(true);
		this.setResistance(1F);
		this.setHardness(0.3F);
		this.setSoundType(SoundType.GLASS);
		this.setRegistryName(ICBMConstants.PREFIX + "glassPressurePlate");
		this.setTranslationKey(ICBMConstants.PREFIX + "glassPressurePlate");
		this.setCreativeTab(CreativeTabs.REDSTONE);
		this.setDefaultState(getDefaultState().with(POWERED, false));
	}

	@Override
	public boolean isOpaqueCube(BlockState state) {
		return false;
	}

}

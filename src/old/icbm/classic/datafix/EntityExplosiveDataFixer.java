package icbm.classic.datafix;

import icbm.classic.api.ICBMClassicAPI;
import icbm.classic.api.caps.IExplosive;
import icbm.classic.api.refs.ICBMEntities;
import icbm.classic.content.reg.BlockReg;
import icbm.classic.lib.NBTConstants;
import icbm.classic.lib.capability.ex.CapabilityExplosiveStack;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.datafix.IFixableData;

public class EntityExplosiveDataFixer implements IFixableData {

	@Override
	public CompoundNBT fixTagCompound(CompoundNBT compound) {
		//Match to entity, we get all entity tags as input
		if (compound.contains(NBTConstants.ID) && compound.getString(NBTConstants.ID).equalsIgnoreCase(ICBMEntities.BLOCK_EXPLOSIVE.toString())) {
			//Fix explosive ID save
			if (compound.contains(NBTConstants.EXPLOSIVE_ID)) {
				int id = compound.getInt(NBTConstants.EXPLOSIVE_ID);

				//Generate stack so we can serialize off it
				final ItemStack stack = new ItemStack(BlockReg.blockExplosive, 1, id);

				//Handle custom explosive data
				final IExplosive ex = stack.getCapability(ICBMClassicAPI.EXPLOSIVE_CAPABILITY, null);
				if (ex instanceof CapabilityExplosiveStack) {
					if (compound.contains(NBTConstants.DATA)) {
						((CapabilityExplosiveStack) ex).setCustomData(compound.getCompound(NBTConstants.DATA));
					}
				}

				//Remove old tags
				compound.remove(NBTConstants.EXPLOSIVE_ID);
				compound.remove(NBTConstants.DATA);

				//Save stack
				compound.put(NBTConstants.EXPLOSIVE_STACK, stack.serializeNBT());
			}
		}
		return compound;
	}

	@Override
	public int getFixVersion() {
		return 1;
	}

}

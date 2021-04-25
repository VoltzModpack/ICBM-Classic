package icbm.classic.datafix;

import icbm.classic.ICBMConstants;
import icbm.classic.content.reg.BlockReg;
import icbm.classic.lib.NBTConstants;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.datafix.IFixableData;

public class TileExplosivesDataFixer implements IFixableData {

	@Override
	public CompoundNBT fixTagCompound(CompoundNBT nbt) {
		String id = nbt.getString(NBTConstants.ID);

		if (id.equals(ICBMConstants.PREFIX + "explosive")) {
			CompoundNBT newNbt = new CompoundNBT();
			int explosiveID = nbt.getInt(NBTConstants.EXPLOSIVE_ID);

			if (explosiveID == 14) //the S-Mine was removed, make it be the default explosive as a fallback
				explosiveID = 0;
			else if (explosiveID > 14) //since it was removed, all the IDs need to move down by one
				explosiveID--;

			newNbt.put(NBTConstants.EXPLOSIVE_STACK, new ItemStack(BlockReg.blockExplosive, 1, explosiveID).serializeNBT());
			newNbt.putInt(NBTConstants.X, nbt.getInt(NBTConstants.X));
			newNbt.putInt(NBTConstants.Y, nbt.getInt(NBTConstants.Y));
			newNbt.putInt(NBTConstants.Z, nbt.getInt(NBTConstants.Z));
			newNbt.putString(NBTConstants.ID, id);
			return newNbt;
		}

		return nbt;
	}

	@Override
	public int getFixVersion() {
		return 1;
	}

}

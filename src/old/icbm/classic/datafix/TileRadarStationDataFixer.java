package icbm.classic.datafix;

import icbm.classic.ICBMConstants;
import icbm.classic.lib.NBTConstants;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.datafix.IFixableData;

public class TileRadarStationDataFixer implements IFixableData {

	@Override
	public CompoundNBT fixTagCompound(CompoundNBT tag) {
		if (tag.contains(NBTConstants.ID) && tag.getString(NBTConstants.ID).equalsIgnoreCase(ICBMConstants.PREFIX + "radarstation")) {
			String firstOldKey = "alarmBanJing";
			String secondOldKey = "safetyBanJing";

			if (tag.contains(firstOldKey)) {
				int alarmRadius = tag.getInt(firstOldKey);

				tag.remove(firstOldKey); //remove the old entry to not have legacy data. the method name may be misleading, but it actually just removes the key from the tag map
				tag.putInt(NBTConstants.ALARM_RADIUS, alarmRadius);
			}

			if (tag.contains(secondOldKey)) {
				int safetyRadius = tag.getInt(secondOldKey);

				tag.remove(secondOldKey); //remove the old entry to not have legacy data. the method name may be misleading, but it actually just removes the key from the tag map
				tag.putInt(NBTConstants.SAFETY_RADIUS, safetyRadius);
			}
		}

		return tag;
	}

	@Override
	public int getFixVersion() {
		return 1;
	}

}

package icbm.classic.lib.capability.ex;

import icbm.classic.api.ICBMClassicAPI;
import icbm.classic.api.caps.IExplosive;
import icbm.classic.api.reg.IExplosiveData;
import icbm.classic.content.reg.BlockReg;
import icbm.classic.lib.NBTConstants;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Used by any item that has an explosive capability Created by Dark(DarkGuardsman, Robert) on 1/7/19.
 */
public class CapabilityExplosiveStack implements IExplosive, ICapabilitySerializable<CompoundNBT> {

	private final ItemStack stack;
	private CompoundNBT custom_ex_data;

	public CapabilityExplosiveStack(ItemStack stack) {
		this.stack = stack;
	}

	protected int getExplosiveID() {
		if (stack == null) {
			return 0;
		}
		return stack.getDamage(); //TODO replace meta usage for 1.14 update
	}

	@Nullable
	@Override
	public IExplosiveData getExplosiveData() {
		return ICBMClassicAPI.EXPLOSIVE_REGISTRY.getExplosiveData(getExplosiveID());
	}

	@Nullable
	@Override
	public CompoundNBT getCustomBlastData() {
		if (custom_ex_data == null) {
			custom_ex_data = new CompoundNBT();
		}
		return custom_ex_data;
	}

	public void setCustomData(CompoundNBT data) {
		this.custom_ex_data = data;
	}

	@Nullable
	@Override
	public ItemStack toStack() {
		if (stack == null) {
			return new ItemStack(BlockReg.blockExplosive, 1, 0);
		}
		final ItemStack re = stack.copy();
		re.setCount(1);
		return re;
	}

	@Override
	public CompoundNBT serializeNBT() {
		//Do not save the stack itself as we are saving to its NBT
		CompoundNBT save = new CompoundNBT();
		if (!getCustomBlastData().isEmpty()) {
			save.setTag(NBTConstants.CUSTOM_EX_DATA, getCustomBlastData());
		}
		return save;
	}

	@Override
	public void deserializeNBT(CompoundNBT nbt) {
		if (nbt.hasKey(NBTConstants.CUSTOM_EX_DATA)) {
			custom_ex_data = nbt.getCompoundTag(NBTConstants.CUSTOM_EX_DATA);
		}
	}

	@Override
	public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
		return capability == ICBMClassicAPI.EXPLOSIVE_CAPABILITY;
	}

	@Nullable
	@Override
	public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
		if (capability == ICBMClassicAPI.EXPLOSIVE_CAPABILITY) {
			return (T) this;
		}
		return null;
	}

}

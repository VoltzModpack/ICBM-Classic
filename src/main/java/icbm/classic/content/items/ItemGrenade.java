package icbm.classic.content.items;

import icbm.classic.api.ICBMClassicAPI;
import icbm.classic.api.reg.IExplosiveData;
import icbm.classic.content.entity.EntityGrenade;
import icbm.classic.lib.capability.ex.CapabilityExplosiveStack;
import icbm.classic.prefab.item.ItemBase;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class ItemGrenade extends ItemBase {

	public static final int MAX_USE_DURATION = 3 * 20; //TODO config

	public ItemGrenade() {
		this.setMaxStackSize(16);
		this.setMaxDamage(0);
		this.setHasSubtypes(true);
	}

	@Override
	@Nullable
	public net.minecraftforge.common.capabilities.ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
		return new CapabilityExplosiveStack(stack);
	}

	@Override
	public EnumAction getItemUseAction(ItemStack par1ItemStack) {
		return EnumAction.BOW;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack par1ItemStack) {
		return MAX_USE_DURATION;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, EnumHand handIn) {
		ItemStack itemstack = playerIn.getHeldItem(handIn);
		playerIn.setActiveHand(handIn);
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack itemStack, World world, LivingEntity entityLiving, int timeLeft) {
		if (!world.isRemote) {
			//Play throw sound
			world.playSound(null, entityLiving.posX, entityLiving.posY, entityLiving.posZ, SoundEvents.ENTITY_TNT_PRIMED, SoundCategory.BLOCKS, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

			//Calculate energy based on player hold time
			final float throwEnergy = (float) (this.getMaxItemUseDuration(itemStack) - timeLeft) / (float) this.getMaxItemUseDuration(itemStack);

			//Create generate entity
			new EntityGrenade(world)
				.setItemStack(itemStack)
				.setThrower(entityLiving)
				.aimFromThrower()
				.setThrowMotion(throwEnergy).spawn();

			//Consume item
			if (!(entityLiving instanceof PlayerEntity) || !((PlayerEntity) entityLiving).capabilities.isCreativeMode) {
				itemStack.shrink(1);
			}
		}
	}

	@Override
	public int getMetadata(int damage) {
		return damage;
	}

	@Override
	public String getTranslationKey(ItemStack itemstack) {
		final IExplosiveData data = ICBMClassicAPI.EXPLOSIVE_REGISTRY.getExplosiveData(itemstack.getDamage());
		if (data != null) {
			return "grenade." + data.getRegistryName();
		}
		return "grenade";
	}

	@Override
	public String getTranslationKey() {
		return "grenade";
	}

	@Override
	protected boolean hasDetailedInfo(ItemStack stack, PlayerEntity player) {
		return true;
	}

	@Override
	protected void getDetailedInfo(ItemStack stack, PlayerEntity player, List list) {
		//TODO ((ItemBlockExplosive) Item.getItemFromBlock(ICBMClassic.blockExplosive)).getDetailedInfo(stack, player, list);
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> list) {
		if (tab == getCreativeTab() || tab == CreativeTabs.SEARCH) {
			for (int id : ICBMClassicAPI.EX_GRENADE_REGISTRY.getExplosivesIDs()) {
				list.add(new ItemStack(this, 1, id));
			}
		}
	}

}

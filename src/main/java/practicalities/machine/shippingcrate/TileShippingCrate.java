package practicalities.machine.shippingcrate;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import practicalities.IItemFilter;
import practicalities.gui.TileSimpleInventory;
import cpw.mods.fml.common.registry.GameRegistry;

public class TileShippingCrate extends TileSimpleInventory {

	public TileShippingCrate() {
		super(55);
		// TODO Auto-generated constructor stub
	}

	public int getFilterSlotIndex() {
		return getSizeInventory() - 1;
	}

	@Override
	public String getName() {
		return getBlockType().getUnlocalizedName();
	}

	@Override
	public int getType() {
		return getBlockMetadata();
	}

	public static void initialize() {
		GameRegistry.registerTileEntity(TileShippingCrate.class, "p2.shippingcrate");
	}

	@Override
	public String getInventoryName() {
		return StatCollector.translateToLocal("tile.shippingcrate.name");
	}

	@Override
	public Object getGuiClient(InventoryPlayer inventory) {
		return new GuiShippingCrate(inventory, this);
	}

	@Override
	public Object getGuiServer(InventoryPlayer inventory) {
		return new ContainerShippingCrate(inventory, this);
	}

	@SuppressWarnings({"unchecked"})
	@Override
	public boolean isItemValidForSlot(int slot, ItemStack item) {
		ItemStack filterCard = getStackInSlot(getFilterSlotIndex());
		if (filterCard != null && filterCard.getItem() instanceof IItemFilter<?>) {
			int filterToApply = slot % 9;
			return ((IItemFilter<ItemStack>)filterCard.getItem()).filter(filterCard, item, filterToApply, true);
		}

		return true;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack item) {
		super.setInventorySlotContents(slot, item);

	}
	
	@Override
	public boolean canPlayerDismantle(EntityPlayer player) {
		return false;
	}

	@Override
	public void writeToNBT(NBTTagCompound nbtTag) {
		super.writeToNBT(nbtTag);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbtTag) {
		super.readFromNBT(nbtTag);
	}

}

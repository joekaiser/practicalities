package practicalities.machines.shippingcrate;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fml.common.registry.GameRegistry;
import practicalities.Logger;
import practicalities.base.TileSimpleInventory;

public class TileShippingCrate extends TileSimpleInventory {

	public TileShippingCrate() {
		super(55);
	}

	public int getFilterSlotIndex() {
		return getSizeInventory() - 1;
	}

	@Override
	public String getName() {
		return getBlockType().getUnlocalizedName();
	}

	public static void initialize() {
		Logger.info("    Registering ShippingCrate");
		GameRegistry.registerTileEntity(TileShippingCrate.class, "p2.shippingcrate");
	}

	@Override
	public Object getGuiClient(InventoryPlayer inventory) {
		// return new GuiShippingCrate(inventory, this);
		return null;
	}

	@Override
	public Object getGuiServer(InventoryPlayer inventory) {
		// return new ContainerShippingCrate(inventory, this);
		return null;
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public boolean isItemValidForSlot(int slot, ItemStack item) {
		// todo: implement when filters are added back in
		
		// ItemStack filterCard = getStackInSlot(getFilterSlotIndex());
		// if (filterCard != null && filterCard.getItem() instanceof
		// IItemFilter<?>) {
		// int filterToApply = slot % 9;
		// return
		// ((IItemFilter<ItemStack>)filterCard.getItem()).filter(filterCard,
		// item, filterToApply, true);
		// }

		return true;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack item) {
		super.setInventorySlotContents(slot, item);

	}

	@Override
	public void writeToNBT(NBTTagCompound nbtTag) {
		super.writeToNBT(nbtTag);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbtTag) {
		super.readFromNBT(nbtTag);
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void openInventory(EntityPlayer player) {

	}

	@Override
	public void closeInventory(EntityPlayer player) {

	}

	@Override
	public int getField(int id) {
		return 0;
	}

	@Override
	public void setField(int id, int value) {

	}

	@Override
	public int getFieldCount() {
		return 0;
	}

	@Override
	public void clear() {

	}

	@Override
	public boolean hasCustomName() {
		return false;
	}

	@Override
	public IChatComponent getDisplayName() {
		return null;
	}

}

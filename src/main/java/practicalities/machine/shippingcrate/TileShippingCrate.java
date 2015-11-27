package practicalities.machine.shippingcrate;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.StatCollector;
import practicalities.IItemFilter;
import practicalities.gui.TileSimpleInventory;
import practicalities.items.ModItems;
import cofh.lib.gui.container.InventoryContainerItemWrapper;
import cpw.mods.fml.common.registry.GameRegistry;

public class TileShippingCrate extends TileSimpleInventory {

	public ItemStack[] filters;

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
			return ((IItemFilter<ItemStack>)filterCard.getItem()).filter(filterCard, item, filterToApply);
		}
//		if (filters != null) {
//			int filterToApply = slot % 9;
//			if (filters[filterToApply] == null) {
//				return true;
//			}
//			if (ItemHelper.itemsEqualWithoutMetadata(item, filters[filterToApply], true)) {
//				return true;
//			} else {
//				return false;
//			}
//
//		}

		return true;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack item) {
		if (slot == getFilterSlotIndex()) {
			if (item != null && item.getItem() == ModItems.filterCard) {
				InventoryContainerItemWrapper itemInventory = new InventoryContainerItemWrapper(null, item);

				filters = new ItemStack[itemInventory.getSizeInventory()];
				for (int i = 0; i < filters.length; i++) {
					filters[i] = itemInventory.getStackInSlot(i);
				}

			} else {
				filters = null;
			}
		}

		super.setInventorySlotContents(slot, item);

	}
	
	@Override
	public boolean canPlayerDismantle(EntityPlayer player) {
		return false;
	}

	@Override
	public void writeToNBT(NBTTagCompound nbtTag) {
		super.writeToNBT(nbtTag);

		if (filters != null) {
			NBTTagList nbttaglist = new NBTTagList();
			for (int i = 0; i < this.filters.length; ++i) {
				if (this.filters[i] != null) {
					NBTTagCompound nbttagcompound1 = new NBTTagCompound();
					nbttagcompound1.setInteger("filters_SlotID", i);
					this.filters[i].writeToNBT(nbttagcompound1);
					nbttaglist.appendTag(nbttagcompound1);
				}
			}
			nbtTag.setTag("filters", nbttaglist);

		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbtTag) {
		super.readFromNBT(nbtTag);

		NBTTagList nbttaglist = nbtTag.getTagList("filters", 10);
		if (nbttaglist != null) {
			filters = new ItemStack[9]; //hack: i know it is 9 but i shouldn't hard-code this
		}

		for (int i = 0; i < nbttaglist.tagCount(); ++i) {
			NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
			int j = nbttagcompound1.getInteger("filters_SlotID");

			if (j >= 0 && j < this.filters.length) {
				this.filters[j] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
			}
		}
	}

}

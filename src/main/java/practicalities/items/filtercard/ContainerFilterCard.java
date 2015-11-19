package practicalities.items.filtercard;

import cofh.lib.gui.container.ContainerInventoryItem;
import cofh.lib.gui.slot.SlotFalseCopy;
import cofh.lib.gui.slot.SlotViewOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerFilterCard extends ContainerInventoryItem {

	
	public ContainerFilterCard(ItemStack item, InventoryPlayer inventory) {
		super(item, inventory);

		int numRows = containerWrapper.getSizeInventory() / 9;

		for (int row = 0; row < numRows; ++row) {
			for (int slot = 0; slot < 9; ++slot) {
				this.addSlotToContainer(
						new SlotFalseCopy(this.containerWrapper, slot + row * 9, 8 + slot * 18, 18 + row * 18));
			}
		}

		addPlayerSlotsToContainer(inventory, 8, 50);
	}

	private void addPlayerSlotsToContainer(InventoryPlayer inventory, int xOff, int yOff) {

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 9; j++) {
				addSlotToContainer(new Slot(inventory, j + i * 9 + 9, xOff + j * 18, yOff + i * 18));
			}
		}

		for (int i = 0; i < 9; i++) {
			if (i == inventory.currentItem) {
				addSlotToContainer(new SlotViewOnly(inventory, i, xOff + i * 18, yOff + 58));
			} else {
				addSlotToContainer(new Slot(inventory, i, xOff + i * 18, yOff + 58));
			}
		}
	}
	
	@Override
	 public ItemStack slotClick(int paramInt1, int paramInt2, int paramInt3, EntityPlayer paramEntityPlayer)
	  {
	    Slot localSlot = paramInt1 < 0 ? null : (Slot)this.inventorySlots.get(paramInt1);
	    if ((localSlot instanceof SlotFalseCopy))
	    {
	      if (paramInt2 == 2)
	      {
	        localSlot.putStack(null);
	        localSlot.onSlotChanged();
	      }
	      else
	      {
	        localSlot.putStack(paramEntityPlayer.inventory.getItemStack() == null ? null : paramEntityPlayer.inventory.getItemStack().copy());
	      }
	      return paramEntityPlayer.inventory.getItemStack();
	    }
	    return super.slotClick(paramInt1, paramInt2, paramInt3, paramEntityPlayer);
	  }

}

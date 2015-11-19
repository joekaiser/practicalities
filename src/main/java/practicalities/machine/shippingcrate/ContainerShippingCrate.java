package practicalities.machine.shippingcrate;

import cofh.lib.gui.slot.SlotAcceptAssignable;
import cofh.lib.gui.slot.SlotAcceptValid;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import practicalities.gui.ContainerBase;
import practicalities.items.filtercard.ItemFilterCard;

public class ContainerShippingCrate extends ContainerBase {

	private TileShippingCrate shippingCrate;

	public ContainerShippingCrate(InventoryPlayer player, TileShippingCrate shippingCrate) {
		this.shippingCrate = shippingCrate;
		bindPlayerInventory(player);

		int numRows = (shippingCrate.getSizeInventory() - 1) / 9;
		
		for (int row = 0; row < numRows; ++row) {
			for (int slot = 0; slot < 9; ++slot) {
				this.addSlotToContainer(new SlotAcceptValid(shippingCrate, slot + row * 9, 8 + slot * 18, 25 + row * 18));
			}
		}
		// filter slot
		this.addSlotToContainer(new SlotAcceptAssignable(shippingCrate, shippingCrate.getFilterSlotIndex(), 152, 5, ItemFilterCard.class));
	}

	@Override
	protected int getPlayerInventoryVerticalOffset() {
		return 140;
	}

	@Override
	protected int getSizeInventory() {
		return shippingCrate.getSizeInventory();
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return true;
	}
	
	

}

package practicalities.machine.shippingcrate;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import practicalities.gui.ContainerBase;
import practicalities.gui.SlotRegion;
import practicalities.items.filtercard.ItemFilterCard;
import cofh.lib.gui.slot.SlotAcceptAssignable;
import cofh.lib.gui.slot.SlotAcceptValid;

public class ContainerShippingCrate extends ContainerBase {

	private TileShippingCrate shippingCrate;
	private SlotRegion mainInv, hotbar, crate, card;
	
	public ContainerShippingCrate(InventoryPlayer player, TileShippingCrate shippingCrate) {
		this.shippingCrate = shippingCrate;
		bindPlayerInventory(player);
		
		int numRows = (shippingCrate.getSizeInventory() - 1) / 9;
		int[][] indexColumns = new int[9][numRows];
		
		for (int row = 0; row < numRows; ++row) {
			for (int slot = 0; slot < 9; ++slot) {
				Slot s = this.addSlotToContainer(new SlotAcceptValid(shippingCrate, slot + row * 9, 8 + slot * 18, 25 + row * 18));
				indexColumns[slot][row] = s.slotNumber;
			}
		}
		
		// filter slot
		Slot cardSlot = this.addSlotToContainer(new SlotAcceptAssignable(shippingCrate, shippingCrate.getFilterSlotIndex(), 152, 5, ItemFilterCard.class));
		
		mainInv = new SlotRegion("mainInventory", 0, 26);
		hotbar  = new SlotRegion("hotbar", 27, 35);
		
		SlotRegion[] cols = new SlotRegion[9];
		for (int i = 0; i < indexColumns.length; i++) {
			int[] column = indexColumns[i];
			cols[i] = new SlotRegion("crate_column-"+i, column);
		}
		crate = new SlotRegion("crate", cols);
		
		card = new SlotRegion("filterCard", new int[] { cardSlot.slotNumber });
		
		hotbar.addShiftTargets(card, crate, mainInv);
		mainInv.addShiftTargets(card, crate, hotbar);
		crate.addShiftTargets(mainInv, hotbar);
		card.addShiftTargets(mainInv, hotbar);
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
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotIndex) {		
		
//		
//		SlotRegion upper = new SlotRegion("upper", crate);
//		SlotRegion lower = new SlotRegion("player", mainInv, hotbar);
		
		if (!supportsShiftClick(player, slotIndex)) {
			return null;
		}

		return SlotRegion.shiftClick(this, slotIndex, player, card, mainInv, hotbar, crate);//stack;
	}

}

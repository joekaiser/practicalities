package practicalities.machine.inventoryfilter;

import cofh.lib.gui.slot.SlotAcceptValid;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import practicalities.gui.ContainerBase;
import practicalities.gui.IContainerButtons;
import practicalities.gui.IContainerField;
import practicalities.gui.SlotRegion;

public class ContainerInventoryFilter extends ContainerBase implements IContainerField, IContainerButtons {

	private TileInventoryFilter tile;
	private IInventory inv;
	private SlotRegion mainInv, hotbar, card;

	public ContainerInventoryFilter(InventoryPlayer player, TileInventoryFilter tile) {
		this.tile = tile;
		bindPlayerInventory(player);
		Slot cardSlot;
		addSlotToContainer(cardSlot = new SlotAcceptValid(inv = tile.getFilterInventory(), 0, 102, 10));
		
		mainInv = new SlotRegion("mainInventory", 0, 26);
		hotbar  = new SlotRegion("hotbar", 27, 35);
		card = new SlotRegion("filterCard", new int[] { cardSlot.slotNumber });
		
		hotbar.addShiftTargets(card, mainInv);
		mainInv.addShiftTargets(card, hotbar);
		card.addShiftTargets(hotbar, mainInv);
	}
	
	@Override
	protected int getPlayerInventoryVerticalOffset() {
		return 41;
	}

	@Override
	protected int getSizeInventory() {
		return 1;
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return true;
	}

	@Override
	public void fieldChange(int field, String text) {
		try {
			if(field == 0) {
				tile.slotStart = Integer.parseInt(text.trim());
				tile.markFilthy();
			}
			
			if(field == 1) {
				tile.slotEnd = Integer.parseInt(text.trim());
				tile.markFilthy();
			}
		} catch(NumberFormatException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void buttonClick(int button, int action) {
		tile.invert = !tile.invert;
		tile.markFilthy();
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotIndex) {
		if (!supportsShiftClick(player, slotIndex)) {
			return null;
		}

		return SlotRegion.shiftClick(this, slotIndex, player, card, mainInv, hotbar);
	}

}

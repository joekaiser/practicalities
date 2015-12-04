package practicalities.machine.slotfilter;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import practicalities.gui.ContainerBase;
import practicalities.gui.IContainerField;

public class ContainerSlotFilter extends ContainerBase implements IContainerField {

	private TileSlotFilter tile;
	
	public ContainerSlotFilter(InventoryPlayer player, TileSlotFilter tile) {
		this.tile = tile;
		addSlotToContainer(new Slot(tile.getFilterInventory(), 0, 9, 53));
	}
	
	@Override
	protected int getPlayerInventoryVerticalOffset() {
		return 140;
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
	public ItemStack transferStackInSlot(EntityPlayer player, int slotIndex) {
		return super.transferStackInSlot(player, slotIndex);
	}

}

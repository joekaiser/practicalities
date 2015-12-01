package practicalities.machine.inventoryfilter;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import practicalities.gui.ContainerBase;
import practicalities.gui.IContainerField;

public class ContainerInventoryFilter extends ContainerBase implements IContainerField {

	private TileInventoryFilter tile;
	
	public ContainerInventoryFilter(InventoryPlayer player, TileInventoryFilter tile) {
		this.tile = tile;
	}
	
	@Override
	protected int getPlayerInventoryVerticalOffset() {
		return 140;
	}

	@Override
	protected int getSizeInventory() {
		return 0;
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

}

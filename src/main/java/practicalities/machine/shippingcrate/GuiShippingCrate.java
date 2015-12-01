package practicalities.machine.shippingcrate;

import cofh.lib.gui.GuiBase;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import practicalities.PracticalitiesMod;

public class GuiShippingCrate extends GuiBase {

	public GuiShippingCrate(InventoryPlayer inventory, TileShippingCrate tile) {
		super(new ContainerShippingCrate(inventory, tile));
		texture = new ResourceLocation(PracticalitiesMod.TEXTURE_BASE + "textures/gui/shippingCrate.png");
		ySize = 222;
		xSize = 176;

		this.name = tile.getInventoryName();
		this.drawInventory=false;
	}
}

package practicalities.items.filtercard;

import cofh.lib.gui.GuiBase;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import practicalities.PracticalitiesMod;

public class GuiFilterCard extends GuiBase {

	public GuiFilterCard(ItemStack item, InventoryPlayer inventory) {
		super(new ContainerFilterCard(item,inventory));
		texture = new ResourceLocation(PracticalitiesMod.TEXTURE_BASE + "textures/gui/filtercard.png");

		this.drawInventory=false;
	}
}

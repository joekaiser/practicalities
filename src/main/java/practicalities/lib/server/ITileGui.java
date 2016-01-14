package practicalities.lib.server;

import net.minecraft.entity.player.InventoryPlayer;

public interface ITileGui {
	
	public Object getGuiClient(InventoryPlayer inventory);
	public Object getGuiServer(InventoryPlayer inventory);
}

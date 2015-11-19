package practicalities.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface IItemGui {
	
	 void openGui(World world, EntityPlayer entityplayer);
	 
	 Object getGuiClient(ItemStack item, InventoryPlayer inventory);
	 
	 Object getGuiServer(ItemStack item, InventoryPlayer inventory);

}

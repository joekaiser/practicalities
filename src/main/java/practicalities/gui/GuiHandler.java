package practicalities.gui;

import cofh.core.block.TileCoFHBase;
import cofh.lib.util.helpers.ItemHelper;
import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import practicalities.items.IItemGui;

public class GuiHandler implements IGuiHandler {

	public static final int TILE_ID = 0;
	public static final int ITEM_ID = 1;

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		switch (ID) {
		case TILE_ID:
			TileEntity tileEntity = world.getTileEntity(x, y, z);
			if (tileEntity instanceof TileCoFHBase) {
				return ((TileCoFHBase) tileEntity).getGuiServer(player.inventory);
			}
			break;
			
		case ITEM_ID:
			if(ItemHelper.isPlayerHoldingItem(IItemGui.class, player)){
				ItemStack item = player.getCurrentEquippedItem();
				return ((IItemGui)item.getItem()).getGuiServer(item, player.inventory);
			}
			break;
			
		}

		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		switch (ID) {
		case TILE_ID:
			TileEntity tileEntity = world.getTileEntity(x, y, z);
			if (tileEntity instanceof TileCoFHBase) {
				return ((TileCoFHBase) tileEntity).getGuiClient(player.inventory);
			}
			break;
			
		case ITEM_ID:
			if(ItemHelper.isPlayerHoldingItem(IItemGui.class, player)){
				ItemStack item = player.getCurrentEquippedItem();
				return ((IItemGui)item.getItem()).getGuiClient(item, player.inventory);
			}
			break;
		}

		return null;
	}
}

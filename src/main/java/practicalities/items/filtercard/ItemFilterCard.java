package practicalities.items.filtercard;

import java.util.List;

import cofh.api.item.IInventoryContainerItem;
import cofh.lib.util.helpers.ItemHelper;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import practicalities.PracticalitiesMod;
import practicalities.gui.GuiHandler;
import practicalities.items.IItemGui;

public class ItemFilterCard extends Item implements IItemGui, IInventoryContainerItem {

	public ItemFilterCard() {
		super();
		String name = "filtercard";
		GameRegistry.registerItem(this, name);
		setUnlocalizedName(name);
		setCreativeTab(PracticalitiesMod.tab);
		setTextureName(PracticalitiesMod.TEXTURE_BASE + name);
		setMaxStackSize(1);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer player) {
		openGui(world, player);
		return item;
	}

	@Override
	public void openGui(World world, EntityPlayer entityplayer) {
		entityplayer.openGui(PracticalitiesMod.instance, GuiHandler.ITEM_ID, world, (int) entityplayer.posX,
				(int) entityplayer.posY, (int) entityplayer.posZ);

	}

	@Override
	public Object getGuiClient(ItemStack item, InventoryPlayer inventory) {
		return new GuiFilterCard(item, inventory);
	}

	@Override
	public Object getGuiServer(ItemStack item, InventoryPlayer inventory) {
		return new ContainerFilterCard(item, inventory);
	}

	@Override
	public int getSizeInventory(ItemStack arg0) {
		return 9;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean p4) {
		try {
			ItemHelper.addAccessibleInventoryInformation(stack, list, 0, Integer.MAX_VALUE);
		} catch (Exception e) {
			//shhh. happens when the filter doesn't have stuff in it
		}

	}
}

package practicalities.items.filtercard;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import practicalities.PracticalitiesMod;
import practicalities.gui.element.ElementButtonToggle;
import cofh.lib.gui.GuiBase;

public class GuiFilterCard extends GuiBase {

	boolean[] data = new boolean[9];
	
	public GuiFilterCard(ItemStack item, InventoryPlayer inventory) {
		super(new ContainerFilterCard(item,inventory));
		texture = new ResourceLocation(PracticalitiesMod.TEXTURE_BASE + "textures/gui/filtercard.png");
		this.ySize = 132;
		NBTTagCompound tag = item.getTagCompound();
		if(tag != null) {
			for (int i = 0; i < data.length; i++) {
				data[i] = tag.getBoolean("fuzzy_"+i);
			}
		}
		this.drawInventory=false;
	}
	
	@Override
	public void initGui() {
		super.initGui();
		for(int i = 0; i < 9; i++) {
			ElementButtonToggle t = new ElementButtonToggle(this, 7 + (18*i), 35, 18, 8, 0, 140, 0, 132, 0, i);
			t.setSelected(data[i]);
			addElement(t);
		}
	}
}

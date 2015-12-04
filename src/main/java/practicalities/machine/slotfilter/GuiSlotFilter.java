package practicalities.machine.slotfilter;

import java.util.regex.Pattern;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import practicalities.PracticalitiesMod;
import practicalities.gui.ElementButtonToggle;
import practicalities.network.Net;
import practicalities.network.TextFieldPacket;
import cofh.lib.gui.GuiBase;
import cofh.lib.gui.element.ElementTextField;
import cofh.lib.gui.element.ElementTextFieldFiltered;

public class GuiSlotFilter extends GuiBase {

	TileSlotFilter tile;
	
	public GuiSlotFilter(InventoryPlayer inventory, TileSlotFilter tile) {
		super(new ContainerSlotFilter(inventory, tile));
		this.tile = tile;
		texture = new ResourceLocation(PracticalitiesMod.TEXTURE_BASE + "textures/gui/slotFilter.png");
		xSize = 90;
		ySize = 36;

		this.name = "";
		this.drawInventory=false;
	}
	
	@Override
	public void initGui() {
		super.initGui();
		
		addElement(new ElementTextFieldFiltered(this, 5, 5,  45, 11) {
			public ElementTextField setFocused(boolean paramBoolean) {
				if(isFocused() && !paramBoolean) {
					onFocusLost();
				}
				return super.setFocused(paramBoolean);
			};
			
			protected boolean onEnter() {
				setFocused(false);
				return false;
			};
			
			protected void onFocusLost() {
				Net.channel.sendToServer(new TextFieldPacket(0, new String(this.text).substring(0, this.textLength) ));
			};
		}.setFilter(Pattern.compile("\\d*"), true).setText(""+tile.slotStart));
		
		addElement(new ElementTextFieldFiltered(this, 5, 20, 45, 11) {
			public ElementTextField setFocused(boolean paramBoolean) {
				if(isFocused() && !paramBoolean) {
					onFocusLost();
				}
				return super.setFocused(paramBoolean);
			};
			
			protected boolean onEnter() {
				setFocused(false);
				return false;
			};
			
			protected void onFocusLost() {
				Net.channel.sendToServer(new TextFieldPacket(1, new String(this.text).substring(0, this.textLength) ));
			};
		}.setFilter(Pattern.compile("\\d*"), true).setText(""+tile.slotEnd));
		
		addElement(new ElementButtonToggle(this, 78, 10, 8, 16, 0, 36, 8, 36, 2, 0));
	}
}

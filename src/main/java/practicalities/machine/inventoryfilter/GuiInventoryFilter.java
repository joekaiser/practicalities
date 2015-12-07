package practicalities.machine.inventoryfilter;

import java.util.regex.Pattern;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import practicalities.PracticalitiesMod;
import practicalities.gui.element.ElementButtonToggle;
import practicalities.network.Net;
import practicalities.network.TextFieldPacket;
import cofh.lib.gui.GuiBase;
import cofh.lib.gui.element.ElementTextField;
import cofh.lib.gui.element.ElementTextFieldFiltered;

public class GuiInventoryFilter extends GuiBase {

	TileInventoryFilter tile;
	
	public GuiInventoryFilter(InventoryPlayer inventory, TileInventoryFilter tile) {
		super(new ContainerInventoryFilter(inventory, tile));
		this.tile = tile;
		texture = new ResourceLocation(PracticalitiesMod.TEXTURE_BASE + "textures/gui/inventoryFilter.png");
		xSize = 176;
		ySize = 123;

		this.name = "";
		this.drawInventory=false;
	}
	
	@Override
	public void initGui() {
		super.initGui();
		
		addElement(new ElementTextFieldFiltered(this, 54, 5,  44, 11) {
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
		
		addElement(new ElementTextFieldFiltered(this, 54, 20, 44, 11) {
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
		
		ElementButtonToggle e = new ElementButtonToggle(this, 126, 10, 8, 16, 0, 123, 8, 123, 2, 0) {
			public void addTooltip(java.util.List<String> paramList) {
				if(this.selected) {
					paramList.add(StatCollector.translateToLocal("gui.tooltip.inventoryfilter.whitelist"));
				} else {
					paramList.add(StatCollector.translateToLocal("gui.tooltip.inventoryfilter.blacklist"));
				}
			};
		};
		addElement(e);
		e.setSelected(!tile.invert);
//		
//		ElementSimpleToolTip tooltip = new ElementSimpleToolTip(this, 0, 0);
//		tooltip.setToolTip("tooltip.inventoryfilter.invert");
//		tooltip.setToolTipLocalized(true);
//		addElement(tooltip);
	}
}

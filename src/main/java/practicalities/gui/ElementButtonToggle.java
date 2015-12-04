package practicalities.gui;

import org.lwjgl.opengl.GL11;

import practicalities.network.ButtonClickPacket;
import practicalities.network.Net;
import cofh.lib.gui.GuiBase;
import cofh.lib.gui.element.ElementButtonBase;

public class ElementButtonToggle extends ElementButtonBase {

	int onU, onV, offU, offV, button, action;
	boolean selected;
	
	public boolean getSelected() {
		return selected;
	}
	public void setSelected(boolean s) {
		selected = s;
	}
	
	public ElementButtonToggle(GuiBase gui, int x, int y, int width, int height, int onU, int onV, int offU, int offV, int button, int action) {
		super(gui, x, y, width, height);
		this.onU = onU;
		this.onV = onV;
		this.offU = offU;
		this.offV = offV;
		this.button = button;
		this.action = action;
	}

	@Override
	public void drawBackground(int mouseX, int mouseY, float paramFloat) {
		int u = selected ? onU : offU;
		int v = selected ? onV : offV;
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glColor4f(1, 1, 1, 1);
		this.drawTexturedModalRect(this.getPosX(), this.getPosY(), u, v, this.getWidth(), this.getHeight());
		GL11.glDisable(GL11.GL_BLEND);
	}

	@Override
	public void drawForeground(int paramInt1, int paramInt2) {
		
	}
	
	@Override
	public void onClick() {
		selected = !selected;
		Net.channel.sendToServer(new ButtonClickPacket(button, action));
		super.onClick();
	}

}

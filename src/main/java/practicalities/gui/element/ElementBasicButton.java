package practicalities.gui.element;

import org.lwjgl.opengl.GL11;

import practicalities.network.ButtonClickPacket;
import practicalities.network.Net;
import cofh.lib.gui.GuiBase;
import cofh.lib.gui.element.ElementButtonBase;

public class ElementBasicButton extends ElementButtonBase {
	int onU, onV, offU, offV, button, action;
	boolean pressed;
	
	public boolean getSelected() {
		return pressed;
	}
	
	public ElementBasicButton(GuiBase gui, int x, int y, int width, int height, int onU, int onV, int offU, int offV, int button, int action) {
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
		int u = pressed ? onU : offU;
		int v = pressed ? onV : offV;
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		this.drawTexturedModalRect(this.getPosX(), this.getPosY(), u, v, this.getWidth(), this.getHeight());
		GL11.glDisable(GL11.GL_BLEND);
	}

	@Override
	public void drawForeground(int mouseX, int mouseY) {}
	
	@Override
	public void onClick() {
		pressed = true;
		Net.channel.sendToServer(new ButtonClickPacket(button, action));
		super.onClick();
	}
	
	public void onMouseReleased(int mouseX, int mouseY) {
		pressed = false;
	}
}

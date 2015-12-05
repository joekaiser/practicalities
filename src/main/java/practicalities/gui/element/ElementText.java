package practicalities.gui.element;

import org.lwjgl.opengl.GL11;

import cofh.lib.gui.GuiBase;
import cofh.lib.gui.element.ElementBase;

public abstract class ElementText<T> extends ElementBase {

	T data;
	
	public ElementText(GuiBase gui, int x, int y, T data) {
		super(gui, x, y);
		this.data = data;
	}
	
	@Override
	public void drawBackground(int paramInt1, int paramInt2, float paramFloat) {}

	@Override
	public void drawForeground(int mouseX, int mouseY) {
		GL11.glPushMatrix();
			double scale = getScale();
			GL11.glScaled(scale, scale, scale);
			gui.fontRendererObj.drawStringWithShadow(getText(), (int)( posX*(1/scale) ), (int)( posY*(1/scale) ), getColor());
		GL11.glPopMatrix();
	}
	
	public int getColor() {
		return 0x808080;
	}
	public double getScale() {
		return 1;
	}
	public boolean hasShadow() {
		return true;
	}
	public abstract String getText();

}

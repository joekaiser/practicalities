package practicalities.book.gui.element;

import org.lwjgl.opengl.GL11;

import practicalities.lib.client.gui.GuiElement;
import practicalities.lib.client.gui.ScreenBase;

public class ElementScale extends GuiElement {

	double scale;
	
	public ElementScale(ScreenBase paramGuiBase, double scale) {
		super(paramGuiBase, 0, 0);
		this.scale = scale;
	}

	@Override
	public void drawBackground(int paramInt1, int paramInt2, float paramFloat) {
		GL11.glScaled(scale, scale, scale);
	}

	@Override
	public void drawForeground(int paramInt1, int paramInt2) {
		GL11.glScaled(scale, scale, scale);
	}

}

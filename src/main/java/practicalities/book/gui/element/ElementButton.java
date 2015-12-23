package practicalities.book.gui.element;

import practicalities.lib.client.gui.GuiElement;
import practicalities.lib.client.gui.ScreenBase;

public abstract class ElementButton extends GuiElement {
	
	int u, v;
	boolean pressing;
	
	public ElementButton(ScreenBase gui, int x, int y, int width, int height, int u, int v) {
		super(gui, x, y, width, height);
		this.u = u; this.v = v;
	}

	@Override
	public void drawBackground(int paramInt1, int paramInt2, float paramFloat) {
		gui.drawTexturedModalRect(posX, posY, u, v + (isEnabled() ? 0 : sizeY), sizeX, sizeY);
	}

	@Override
	public void drawForeground(int paramInt1, int paramInt2) {
		
	}
	
	@Override
	public boolean onMousePressed(int paramInt1, int paramInt2, int paramInt3) {
		pressing = true;
		return true;
	}
	
	@Override
	public void onMouseReleased(int paramInt1, int paramInt2) {
		if(pressing) {
			pressing = false;
			click();
		}
	}

	public abstract void click();
	
}

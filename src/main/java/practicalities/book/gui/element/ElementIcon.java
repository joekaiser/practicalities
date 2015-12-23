package practicalities.book.gui.element;

import net.minecraft.util.ResourceLocation;
import practicalities.lib.client.gui.GuiElement;
import practicalities.lib.client.gui.ScreenBase;

public class ElementIcon extends GuiElement {

	int u, v;
	ResourceLocation tex;
	
	public ElementIcon(ScreenBase gui, int x, int y, int w, int h, int u, int v, ResourceLocation tex) {
		super(gui, x, y, w, h);
		this.u = u; this.v = v; this.tex = tex;
	}

	@Override
	public void drawBackground(int paramInt1, int paramInt2, float paramFloat) {
		gui.bindTexture(tex);
		gui.drawTexturedModalRect(posX, posY, u, v, sizeX, sizeY);
		gui.bindTexture(gui.texture);
	}

	@Override
	public void drawForeground(int paramInt1, int paramInt2) {
		
	}

}

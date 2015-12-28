package practicalities.book.gui.element;

import org.lwjgl.opengl.GL11;

import practicalities.lib.client.gui.GuiElement;
import practicalities.lib.client.gui.ScreenBase;

public class ElementText extends GuiElement {

	String text;
	
	public ElementText(ScreenBase gui, int x, int y, int xSize, int ySize, String text) {
		super(gui, x, y, xSize, ySize);
		this.text = escapeCodes(text, "n", "\n");
	}
	
	public String escapeCodes(String in, String code, String replace) {
		return in.replaceAll("(?<!\\\\)\\\\"+code, replace).replace("\\"+code, code);
	}

	@Override
	public void drawForeground(int mouseX, int mouseY) {
//		Logger.info("Scale: %d", res.);
		double s = 2.0/3.0;
		double S = 1/s;
		GL11.glScaled(s, s, s);
		this.getFontRenderer().drawSplitString(text, (int)((posX)*S), (int)((posY)*S), ((int)(sizeX*S)), 0x000000);
		GL11.glScaled(S, S, S);
	}

	@Override
	public void drawBackground(int paramInt1, int paramInt2, float paramFloat) {}

}

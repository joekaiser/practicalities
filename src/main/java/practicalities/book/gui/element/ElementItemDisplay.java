package practicalities.book.gui.element;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import practicalities.helpers.GuiHelper;
import practicalities.lib.client.gui.GuiElement;
import practicalities.lib.client.gui.ScreenBase;

public class ElementItemDisplay extends GuiElement {

	ItemStack item;
	double scale, scaleI;
	
	public ElementItemDisplay(ScreenBase paramGuiBase, int x, int y, ItemStack stack, double scale) {
		super(paramGuiBase, x, y);
		item = stack;
		this.scale = scale;
		scaleI = 1.0/scale;
	}

	@Override
	public void drawBackground(int paramInt1, int paramInt2, float paramFloat) {
		
	}

	@Override
	public void drawForeground(int paramInt1, int paramInt2) {
		GL11.glPushMatrix();
		GL11.glTranslated(posX, posY, 200);
		GL11.glScaled(scale, scale, 1);
		
		GuiHelper.instance().setupItemRender();
		GuiHelper.instance().drawItemStack(item, 0, 0);
		GuiHelper.instance().cleanupItemRender();
		
		GL11.glPopMatrix();
	}

}

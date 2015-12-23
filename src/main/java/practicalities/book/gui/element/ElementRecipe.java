package practicalities.book.gui.element;

import org.lwjgl.opengl.GL11;

import net.minecraft.item.ItemStack;
import practicalities.helpers.GuiHelper;
import practicalities.lib.client.gui.GuiElement;
import practicalities.lib.client.gui.ScreenBase;

public class ElementRecipe extends GuiElement {
	
	CraftingMatrix matrix;
	int gap, resultX, resultY;
	double scale;
	
	public ElementRecipe(ScreenBase paramGuiBase, int paramInt1, int paramInt2, CraftingMatrix matrix, int gap, double scale, int resultX, int resultY) {
		super(paramGuiBase, paramInt1, paramInt2);
		this.matrix = matrix; this.gap = gap; this.resultX = resultX; this.resultY = resultY; this.scale = scale;
	}

	@Override
	public void drawBackground(int paramInt1, int paramInt2, float paramFloat) {
		
	}

	@Override
	public void drawForeground(int mouseX, int mouseY) {
		
		GuiHelper.instance().setupItemRender();
		
		double s = scale;
		double S = 1.0/s;
		
		int actualMouseX = mouseX, actualMouseY = mouseY;
		
		int posX = (int)(this.posX*S);
		int posY = (int)(this.posY*S);
		int resultX = (int)(this.resultX*S);
		int resultY = (int)(this.resultY*S);
		mouseX = (int)(mouseX*S);
		mouseY = (int)(mouseY*S);
		
		GL11.glScaled(s, s, s);
		
		int dist = (16+gap);
		
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				renderItem(posX+( dist*i ), posY+( dist*j ), matrix.items[i+(j*3)], mouseX, mouseY);
			}
		}
		
		renderItem(posX+resultX, posY+resultY, matrix.result, mouseX, mouseY);
		
		GL11.glScaled(S, S, S);
		
		GuiHelper.instance().cleanupItemRender();
		
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				tryTooltip(posX+( dist*i ), posY+( dist*j ), matrix.items[i+(j*3)], mouseX, mouseY, actualMouseX, actualMouseY);
			}
		}
		
		tryTooltip(posX+resultX, posY+resultY, matrix.result, mouseX, mouseY, actualMouseX, actualMouseY);
		
	}
	
	void tryTooltip(int x, int y, ItemStack stack, int mouseX, int mouseY, int actualMouseX, int actualMouseY) {
		if(	mouseX >= x && mouseX <= x+16 &&
			mouseY >= y && mouseY <= y+16) {
			GuiHelper.instance().drawItemTooltip(stack, actualMouseX, actualMouseY);
		}
	}
	
	void renderItem(int x, int y, ItemStack stack, int mouseX, int mouseY) {
		GuiHelper.instance().drawItemStack(stack, x, y);
	}

}

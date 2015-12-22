package practicalities.book.gui.element;

import java.util.List;

import org.lwjgl.opengl.GL11;

import practicalities.lib.client.gui.GuiElement;
import practicalities.lib.client.gui.ScreenBase;

public class ElementProxy extends GuiElement {
	
	public ElementProxy(ScreenBase gui, int x, int y,
			int sizeX, int sizeY) {
		super(gui, x, y, sizeX, sizeY);
		// TODO Auto-generated constructor stub
	}
	
	public List<GuiElement> toWrap = null;
	
	public List<GuiElement> getWrapped() {
		return toWrap;
	}
	
	
	@Override
	public void drawBackground(int paramInt1, int paramInt2, float paramFloat) {
		if(getWrapped() != null) {
			GL11.glTranslatef(posX, posY, 0);
			for (GuiElement element : getWrapped()) {
				element.drawBackground(paramInt1, paramInt2, paramFloat);				
			}
			GL11.glTranslatef(-posX, -posY, 0);
		}
	}
	
	@Override
	public void drawForeground(int paramInt1, int paramInt2) {
		if(getWrapped() != null) {
			GL11.glTranslatef(posX, posY, 0);
			for (GuiElement element : getWrapped()) {
				element.drawForeground(paramInt1-posX, paramInt2-posY);				
			}
			GL11.glTranslatef(-posX, -posY, 0);
		}
	}
	
	@Override
	public void addTooltip(List<String> paramList, int mouseX, int mouseY) {
		if(getWrapped() != null) {
			for (GuiElement element : getWrapped()) {
				if(element.intersectsWith(mouseX-posX, mouseY-posY)) {
					element.addTooltip(paramList, mouseX-posX, mouseY-posY);
				}
			}
		}
	}
	
	@Override
	public void update() {
		if(getWrapped() != null) {
			for (GuiElement element : getWrapped()) {
				element.update();
			}
		}
	}
	
	@Override
	public void update(int paramInt1, int paramInt2) {
		if(getWrapped() != null) {
			for (GuiElement element : getWrapped()) {
				element.update(paramInt1-posX, paramInt2-posY);
			}
		}
	}
	
	@Override
	public boolean onMousePressed(int paramInt1, int paramInt2, int paramInt3) {
		if(getWrapped() != null) {
			for (GuiElement element : getWrapped()) {
				if(element.intersectsWith(paramInt1-posX, paramInt2-posY) && element.isVisible() && element.isEnabled()) {
					if(element.onMousePressed(paramInt1-posX, paramInt2-posX, paramInt3))
						return true;
				}
			}
		}
		return false;
	}
	
	public void onMouseReleased(int paramInt1, int paramInt2) {
		if(getWrapped() != null) {
			for (GuiElement element : getWrapped()) {
				element.onMouseReleased(paramInt1-posX, paramInt2-posX);
			}
		}
	}
	
	@Override
	public boolean onMouseWheel(int paramInt1, int paramInt2, int paramInt3) {
		if(getWrapped() != null) {
			for (GuiElement element : getWrapped()) {
				if(element.onMouseWheel(paramInt1-posX, paramInt2-posX, paramInt3)) {
					return true;
				}
			}
		}
		return false;
	}
	
	@Override
	public boolean onKeyTyped(char paramChar, int paramInt) {
		if(getWrapped() != null) {
			for (GuiElement element : getWrapped()) {
				if(element.onKeyTyped(paramChar, paramInt)) {
					return true;
				}
			}
		}
		return false;
	}
	
	@Override
	public boolean intersectsWith(int paramInt1, int paramInt2) {
		if(getWrapped() != null) {
			for (GuiElement element : getWrapped()) {
				if(element.intersectsWith(paramInt1-posX, paramInt2-posY)) {
					return true;
				}
			}
		}
		return false;
	}
}

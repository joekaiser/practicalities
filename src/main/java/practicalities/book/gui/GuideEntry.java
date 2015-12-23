package practicalities.book.gui;

import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import practicalities.book.GuideStateManager;
import practicalities.helpers.GuiHelper;
import practicalities.helpers.ItemHelpers;
import practicalities.lib.client.gui.GuiElement;
import practicalities.lib.client.gui.ScreenBase;

public class GuideEntry extends GuiElement {

	public static final int HEIGHT = 11;
	
	boolean mouseIn = false;
	double animateStart = 0;
	int mouseEnterX = 0;
	
	String name;
	ItemStack icon;
	GuideStateManager state;
	GuiHelper h = GuiHelper.instance();
	
	public GuideEntry(ScreenBase gui, int x, int y, GuideStateManager state, String name) {
		super(gui, x, y, GuiGuide.MAIN_SIZE_X, HEIGHT);
		this.state = state;
		this.name = name;
		this.icon = ItemHelpers.parseItemStack(StatCollector.translateToLocal(name.startsWith("list.") ? "guide."+name+".iconStack" : "guide.entry."+name+".list.iconStack"));
	}
	
	public String getTranslatedName() {
		if(name.startsWith("list.")) {
			return StatCollector.translateToLocal("guide."+name+".text");
		} else {
			return StatCollector.translateToLocal("guide.entry." + name + ".list.text");
		}
	}

	@Override
	public void drawBackground(int mouseX, int mouseY, float partialTick) {
		
	}

	@Override
	public void drawForeground(int paramInt1, int paramInt2) {
		
		boolean intersect = intersectsWith(paramInt1, paramInt2) && paramInt2 != posY; // to prevent two of them being hovered over at the same time. intersectsWith uses <= and >=
		
		if(!mouseIn && intersect) {
			mouseIn = true;
			mouseEnterX = paramInt1-posX;
			animateStart = h.getTicks();
		} else if(mouseIn && !intersect) {
			mouseIn = false;
			animateStart = 0; // just for good measure.
		}
		
		int textLeft = 12;
		
		double s = 0.6;
		double S = 1.0/s;
		
		GL11.glScaled(s, s, s);

		h.setupItemRender();
		h.drawItemStack(icon, (int)( (posX+1)*S ), (int)( (posY+1)*S ));
		h.cleanupItemRender();
		
		GL11.glScaled(S, S, S);
		
		s = 2.0/3.0;
		S = 1.0/s;
		
		GL11.glScaled(s, s, s);
		GuiHelper.fontRendererObj.drawString(getTranslatedName(), (int)( ( posX+textLeft )*S ), (int)( ( posY+3 )*S ), 0x00000);
		GL11.glScaled(S, S, S);
		
		GL11.glAlphaFunc(GL11.GL_GREATER, 0);
		
		if(mouseIn) {
			double p = h.tickAnimate(animateStart, 5);
			h.loadInterpolationFraction(Math.min(p, 1));
			
			h.drawColoredRect(posX+h.intr(mouseEnterX, 0), posY, posX+ h.intr(mouseEnterX, sizeX), posY+sizeY, 0,0,0, h.intr(0, 0.1));
		}
	}
	
	@Override
	public boolean onMousePressed(int paramInt1, int paramInt2, int paramInt3) {
//		state.currentPage = null;
		state.pushHistory();
		if(name.startsWith("list.")) {
			state.goToEntryList(name);
		} else {
			state.goToPage(name, 0);
		}
		return true;
	}

}

package practicalities.book.gui;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.util.StatCollector;
import practicalities.book.GuideStateManager;
import practicalities.book.gui.page.PageDisplay;
import practicalities.book.gui.page.PageRecipe;
import practicalities.book.gui.page.PageText;
import practicalities.lib.client.gui.GuiElement;
import practicalities.lib.client.gui.ScreenBase;

public abstract class GuidePage {

	public List<GuiElement> titleElements = new ArrayList<GuiElement>();
	public List<GuiElement> mainElements = new ArrayList<GuiElement>();
	public List<GuiElement> navElements = new ArrayList<GuiElement>();
	
	public abstract void init(ScreenBase gui, GuideStateManager state);
	
	public static GuidePage getPage(ScreenBase gui, GuideStateManager state, String name, int pageNum) {
		String unlocalized = "guide.entry."+name+".page."+pageNum;
		String type = StatCollector.canTranslate(unlocalized+".type") ? StatCollector.translateToLocal(unlocalized+".type"):"text";
		type = type.toLowerCase();
		
		GuidePage page = null;
		if(type.equals("recipe")) {
			page = new PageRecipe(name, pageNum);
		}
		if(type.equals("display")) {
			page = new PageDisplay(name, pageNum);
		}
		if(type.equals("text") || page == null) {
			page = new PageText(name, pageNum);
		}
		
		page.init(gui, state);
		
		return page;
	}
	
}

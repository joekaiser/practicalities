package practicalities.book.gui.page;

import net.minecraft.util.StatCollector;
import practicalities.book.GuideStateManager;
import practicalities.book.gui.GuiGuide;
import practicalities.book.gui.element.ElementText;
import practicalities.lib.client.gui.ScreenBase;

public class PageText extends PageInEntry {

	String text;
	
	public PageText(String pageName, int page) {
		super(pageName, page);
		text = StatCollector.translateToLocal("guide.entry." + pageName + ".page." + page);
	}
	
	@Override
	public void init(ScreenBase gui, GuideStateManager state) {
		super.init(gui, state);
		this.mainElements.add(new ElementText(gui, 3, 2, GuiGuide.MAIN_SIZE_X-6, GuiGuide.MAIN_SIZE_Y, text));
	}

}

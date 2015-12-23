package practicalities.book.gui.page;

import net.minecraft.util.StatCollector;
import practicalities.book.GuideStateManager;
import practicalities.book.gui.GuidePage;
import practicalities.book.gui.element.ElementButton;
import practicalities.book.gui.element.ElementTitle;
import practicalities.lib.client.gui.ScreenBase;

public class PageInEntry extends GuidePage {

	String title;
	int pageNum;
	
	public PageInEntry(String pageName, int page) {
		title = StatCollector.translateToLocal("guide.entry." + pageName + ".title");
		this.pageNum = page;
	}
	
	@Override
	public void init(ScreenBase gui, GuideStateManager state) {
		
		this.titleElements.add(new ElementTitle(gui, 0, 0, title));
		
		final int number = pageNum;
		final GuideStateManager guideState = state;
		
		this.navElements.add(new ElementButton(gui, -13, 1, 12, 9, 0, 206) {
			public void click() {
				guideState.goToPrev();
			}
			@Override
			public boolean isEnabled() {
				return number > 0;
			}
		});
		
		this.navElements.add(new ElementButton(gui, 1, 1, 12, 9, 12, 206) {
			public void click() {
				guideState.goToNext();
			}
			@Override
			public boolean isEnabled() {
				return number < guideState.getMaxPageNum();
			}
		});
		
		this.navElements.add(new ElementButton(gui, -85, 1, 18, 10, 24, 206) {
			public void click() {
				guideState.goToEntryList();
			}
			@Override
			public boolean isEnabled() {
				return true;
			}
		});
	}

}

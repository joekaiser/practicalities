package practicalities.book.gui.page;

import java.util.ArrayList;
import java.util.List;

import practicalities.book.GuideStateManager;
import practicalities.book.gui.GuideEntry;
import practicalities.book.gui.GuidePage;
import practicalities.book.gui.element.ElementButton;
import practicalities.lib.client.gui.ScreenBase;

public class PageEntryList extends GuidePage {

	public List<String> entries = new ArrayList<String>();
	
	@Override
	public void init(ScreenBase gui, GuideStateManager state) {
		mainElements.clear();
		navElements.clear();
		int top = 0;
		for (String entry : entries) {
			mainElements.add(new GuideEntry(gui, 0, top, state, entry));
			top += GuideEntry.HEIGHT;
		}
		
		final GuideStateManager guideState = state;
		this.navElements.add(new ElementButton(gui, -85, 1, 18, 10, 24, 206) {
			public void click() {
				guideState.goToEntryList();
			}
			@Override
			public boolean isEnabled() {
				return guideState.persistance.lastList.size() > 0;
			}
		});
	}

}

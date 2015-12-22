package practicalities.book;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.util.StatCollector;
import practicalities.Logger;
import practicalities.book.gui.GuidePage;
import practicalities.book.gui.page.PageEntryList;
import practicalities.lib.client.gui.ScreenBase;

public class GuideStateManager {

	public GuidePage page;
	private GuidePage newPage;
	
	public static Map<String, PageEntryList> entryLists = new HashMap<String, PageEntryList>();
	
	public static void registerList(String name, String... entries) {
		PageEntryList list = new PageEntryList();
		for (int i = 0; i < entries.length; i++) {
			list.entries.add(entries[i]);
		}
		entryLists.put(name, list);
	}
	
	public static PageEntryList mainPage;
	public static GuidePersistance persistance = GuidePersistance.instance();
	
	ScreenBase gui;
	int xSize, ySize;
	String currentPage;
	int currentPageNum;
	int maxPageNum;
	
	public GuideStateManager(ScreenBase gui, int xSize, int ySize) {
		this.gui = gui; this.xSize = xSize; this.ySize = ySize;
		mainPage = entryLists.get("list.root");
		
		if(persistance.name == null) {
			persistance.name = "list.root";
			persistance.didStopOnEntryList = true;
		}
		
		if(mainPage == null) {
			mainPage = new PageEntryList();
			entryLists.put("list.root", mainPage);
		}
		
		
		if(persistance.didStopOnEntryList) {
			goToEntryList(persistance.name);
		} else {
			goToPage(persistance.name, persistance.page);
		}
	}
	
	public GuidePage getPage() {
		if(page == null) return newPage;
		return page;
	}
	
	public void updatePage() {
		if(newPage != null)
			page = newPage;
		newPage = null;
	}
	
	public void goToEntryList() {
		goToEntryList(persistance.lastList.pop());
	}
	
	public void pushHistory() {
		persistance.lastList.push(persistance.name);
	}
	
	public void goToEntryList(String name) {
		Logger.info("NAME: %s", name);
		
		if(name != null) {
			newPage = entryLists.get(name);
		}
		if(page == null || name == null) {
			newPage = mainPage;
		}
		
		newPage.init(gui, this);
		persistance.didStopOnEntryList = true;
		persistance.name = name;
	}
	
	public void goToPage(String name, int number) {
		if(currentPage == null || !currentPage.equals(name)) {
			this.maxPageNum = getMaxPageNum(name);
		}
		if(number > maxPageNum)
			number = maxPageNum;
		newPage = GuidePage.getPage(gui, this, name, number);
		
//		persistance.lastList.push(persistance.name);
		
		this.currentPage = name;
		this.currentPageNum = number;
		
		persistance.didStopOnEntryList = false;
		persistance.name = name;
		persistance.page = number;
	}
	
	int getMaxPageNum(String name) {
		int i = 0;
		while(StatCollector.canTranslate("guide.entry."+name+".page."+i)) {
			i++;
		}
		i--;
		return i;
	}
	
	public void goToNext() {
		goToPage(currentPage, currentPageNum+1);
	}
	
	public void goToPrev() {
		goToPage(currentPage, currentPageNum-1);
	}
	
	public int getMaxPageNum() {
		return maxPageNum;
	}
}

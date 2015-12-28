package practicalities.registers;

import practicalities.ConfigMan;
import practicalities.book.GuideStateManager;

public class GuideRegister {

	public static void init() {

		page("root", "intro", ConfigMan.showDevBookEntry ? "list.dev" : null, "list.items");
		page("items", "magnet", "transporter", "sitis", "imbuedSword", "imbuedTool", "voidBucket", "netherbane");
		page("dev", "example", "advancedLang");
	}

	private static void page(String name, String... items) {
		GuideStateManager.registerList("list." + name, items);
	}
}

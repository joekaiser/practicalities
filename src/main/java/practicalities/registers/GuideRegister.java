package practicalities.registers;

import practicalities.book.GuideStateManager;

public class GuideRegister {

	public static void init() {

		page("root", "intro", "example", "advancedLang", "list.items");
		page("items", "magnet", "transporter", "sitis", "imbuedSword", "imbuedTool", "voidBucket", "netherbane");

	}

	private static void page(String name, String... items) {
		GuideStateManager.registerList("list." + name, items);
	}
}

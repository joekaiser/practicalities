package practicalities.book.gui.page;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import practicalities.PracticalitiesMod;
import practicalities.book.GuideStateManager;
import practicalities.book.gui.GuiGuide;
import practicalities.book.gui.element.ElementIcon;
import practicalities.book.gui.element.ElementItemDisplay;
import practicalities.book.gui.element.ElementText;
import practicalities.helpers.ItemHelpers;
import practicalities.lib.client.gui.ScreenBase;

public class PageDisplay extends PageInEntry {

	ResourceLocation tex = new ResourceLocation(PracticalitiesMod.TEXTURE_BASE + "textures/gui/guide_display.png");
	
	String text = "";
	ItemStack item;
	
	public PageDisplay(String pageName, int page) {
		super(pageName, page);
		
		String prefix = "guide.entry." + pageName + ".page." + page;
		
		text = StatCollector.translateToLocal(prefix).replaceAll("\\\\n", "\n");
		item = ItemHelpers.parseItemStack( StatCollector.translateToLocal(prefix + ".item") );
		
	}
	
	@Override
	public void init(ScreenBase gui, GuideStateManager state) {
		super.init(gui, state);	
		mainElements.add(new ElementIcon(gui, 0, 0, GuiGuide.MAIN_SIZE_X, GuiGuide.MAIN_SIZE_Y, 0, 0, tex));
		
		mainElements.add(new ElementText(gui, 3, 87, GuiGuide.MAIN_SIZE_X-6, GuiGuide.MAIN_SIZE_Y-87, text));

		mainElements.add(new ElementItemDisplay(gui, 46, 26, item, 3.1));
		
	}

}

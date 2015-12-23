package practicalities.book.gui;

import java.util.List;

import net.minecraft.util.ResourceLocation;
import practicalities.PracticalitiesMod;
import practicalities.book.GuideStateManager;
import practicalities.book.gui.element.ElementIcon;
import practicalities.book.gui.element.ElementProxy;
import practicalities.helpers.GuiHelper;
import practicalities.lib.client.gui.GuiElement;
import practicalities.lib.client.gui.ScreenBase;

public class GuiGuide extends ScreenBase {

	public static int FULL_SIZE_X = 170;
	public static int FULL_SIZE_Y = 193;
	
	public static int MAIN_SIZE_X = 142;
	public static int MAIN_SIZE_Y = 172;
	
	public GuiGuide() {
		super();
		MAIN_SIZE_X = 142;
		MAIN_SIZE_Y = 175;
		this.texture = new ResourceLocation(PracticalitiesMod.TEXTURE_BASE + "textures/gui/guide.png");
		this.xSize = FULL_SIZE_X; this.ySize = FULL_SIZE_Y;
		state = new GuideStateManager(this, MAIN_SIZE_X, MAIN_SIZE_Y);
	}
	
	ElementProxy mainContent, titleContent, navContent;
	
	GuideStateManager state;
	
	@Override
	public void initGui() {
		super.initGui();
		GuiHelper.instance(); // initialize the GuiHelper
		this.guiLeft = (this.width - FULL_SIZE_X) / 2;
        this.guiTop = (this.height - FULL_SIZE_Y) / 2;
        
//        this.elements = new Composite
        
		mainContent = new ElementProxy(this, 14, 8, MAIN_SIZE_X, MAIN_SIZE_Y) {
			public List<GuiElement> getWrapped() {
				return state.getPage().mainElements;
			}
		};
		addElement(mainContent);
		addElement( new ElementIcon(this, FULL_SIZE_X-32, FULL_SIZE_Y-32, 32, 32, 170, 0, this.texture).setZLevel(1_000) );
		addElement( new ElementIcon(this, FULL_SIZE_X-32, 0, 32, 32, 202, 0, this.texture).setZLevel(1_000) );
		
		titleContent = new ElementProxy(this, FULL_SIZE_X/2, 0, 0, 0) {
			public List<GuiElement> getWrapped() {
				return state.getPage().titleElements;
			}
		};
		addElement(titleContent);
		
		navContent = new ElementProxy(this, FULL_SIZE_X/2, FULL_SIZE_Y, 0, 0) {
			public List<GuiElement> getWrapped() {
				return state.getPage().navElements;
			}
		};
		addElement(navContent);
	}
	
	@Override
	public void drawScreen(int paramInt1, int paramInt2, float paramFloat) {
		super.drawScreen(paramInt1, paramInt2, paramFloat);
		state.updatePage();
	}
}

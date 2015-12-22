package practicalities.book.gui;

import java.util.List;

import net.minecraft.util.ResourceLocation;
import practicalities.PracticalitiesMod;
import practicalities.book.GuideStateManager;
import practicalities.book.gui.element.ElementProxy;
import practicalities.helpers.GuiHelper;
import practicalities.lib.client.gui.GuiElement;
import practicalities.lib.client.gui.ScreenBase;

public class GuiGuide extends ScreenBase {

	public static int MAIN_SIZE_X = 170;
	public static int MAIN_SIZE_Y = 193;
	
	boolean inited = false;
	
	public void init() {
		if(inited)
			return;
		
		GuideStateManager.registerList("list.intro", "list.root", "intro", "list.root");
		
		inited = true;
	}
	
	public GuiGuide() {
		super();
		init();
		this.texture = new ResourceLocation(PracticalitiesMod.TEXTURE_BASE + "textures/gui/guide.png");
		this.xSize = MAIN_SIZE_X; this.ySize = MAIN_SIZE_Y;
		state = new GuideStateManager(this, MAIN_SIZE_X, MAIN_SIZE_Y);
	}
	
	ElementProxy mainContent, titleContent, navContent;
	
	GuideStateManager state;
	
	@Override
	public void initGui() {
		super.initGui();
		GuiHelper.instance(); // initialize the GuiHelper
		this.guiLeft = (this.width - MAIN_SIZE_X) / 2;
        this.guiTop = (this.height - MAIN_SIZE_Y) / 2;
        
//        this.elements = new Composite
        
		mainContent = new ElementProxy(this, 0, 0, MAIN_SIZE_X, MAIN_SIZE_Y) {
			public List<GuiElement> getWrapped() {
				return state.getPage().mainElements;
			}
		};
		addElement(mainContent);
		
		titleContent = new ElementProxy(this, MAIN_SIZE_X/2, 0, 0, 0) {
			public List<GuiElement> getWrapped() {
				return state.getPage().titleElements;
			}
		};
		addElement(titleContent);
		
		navContent = new ElementProxy(this, MAIN_SIZE_X/2, MAIN_SIZE_Y, 0, 0) {
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

package practicalities.lib.client.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.FMLClientHandler;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import practicalities.Logger;

public class ScreenBase extends GuiScreen {
	public int guiLeft = 0, guiTop = 0, xSize = 0, ySize = 0;

	public static final SoundHandler guiSoundManager = FMLClientHandler
			.instance().getClient().getSoundHandler();

	protected int mouseX = 0;
	protected int mouseY = 0;
	
	protected List<GuiElement> elements = new ArrayList<GuiElement>();
	protected List<String> tooltip = new LinkedList<String>();
	
	protected String name;
	public ResourceLocation texture;
	protected boolean tooltips = true;

//	public static void playSound(String paramString, float paramFloat1,
//			float paramFloat2) {
//		guiSoundManager.playSound(new SoundBase(paramString, paramFloat1,
//				paramFloat2));
//	}

	public void initGui() {
		super.initGui();
		this.elements.clear();
	}

	public void setZLevel(float z) {
		this.zLevel = z;
	}
	public float getZLevel() {
		return this.zLevel;
	}
	
	public void drawScreen(int paramInt1, int paramInt2, float paramFloat) {
		updateElementInformation();
		super.drawScreen(paramInt1, paramInt2, paramFloat);
		this.drawDefaultBackground();
        this.drawGuiContainerBackgroundLayer(paramFloat, paramInt1, paramInt2);
        
		if ((this.tooltips)
				&& (this.mc.thePlayer.inventory.getItemStack() == null)) {
			addTooltips(this.tooltip);
			drawTooltip(this.tooltip);
		}
		this.mouseX = (paramInt1 - this.guiLeft);
		this.mouseY = (paramInt2 - this.guiTop);
		
        this.drawGuiContainerForegroundLayer(paramInt1, paramInt2);

		updateElements();
	}

	protected void drawGuiContainerForegroundLayer(int paramInt1, int paramInt2) {
		GL11.glPushMatrix();
		GL11.glTranslatef(this.guiLeft, this.guiTop, 0.0F);
		drawElements(0.0F, true);
		GL11.glPopMatrix();
	}

	protected void drawGuiContainerBackgroundLayer(float paramFloat,
			int paramInt1, int paramInt2) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		bindTexture(this.texture);
		drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize,
				this.ySize);
		this.mouseX = (paramInt1 - this.guiLeft);
		this.mouseY = (paramInt2 - this.guiTop);
		GL11.glPushMatrix();
		GL11.glTranslatef(this.guiLeft, this.guiTop, 0.0F);
		drawElements(paramFloat, false);
		GL11.glPopMatrix();
	}

	protected void keyTyped(char paramChar, int paramInt) {
		for (int i = this.elements.size(); i-- > 0;) {
			GuiElement localElementScreenBase = (GuiElement) this.elements.get(i);
			if ((localElementScreenBase.isVisible())
					&& (localElementScreenBase.isEnabled())) {
				if (localElementScreenBase.onKeyTyped(paramChar, paramInt))
					return;
			}
		}
		try {
			super.keyTyped(paramChar, paramInt);
		} catch (IOException e) {
			Logger.warning("Um. the keyTyped method threw an IOException. Why? I don't know.");
			e.printStackTrace();// Why in the world would typing a key throw an IOException. I have no idea.
		}
	}

	public void handleMouseInput() {
		int i = Mouse.getEventX() * this.width / this.mc.displayWidth;
		int j = this.height - Mouse.getEventY() * this.height
				/ this.mc.displayHeight - 1;
		this.mouseX = (i - this.guiLeft);
		this.mouseY = (j - this.guiTop);
		int k = Mouse.getEventDWheel();
		if (k != 0) {
			for (int m = this.elements.size(); m-- > 0;) {
				GuiElement localElementScreenBase = (GuiElement) this.elements
						.get(m);
				if ((localElementScreenBase.isVisible())
						&& (localElementScreenBase.isEnabled())
						&& (localElementScreenBase.intersectsWith(this.mouseX,
								this.mouseY))) {
					if (localElementScreenBase.onMouseWheel(this.mouseX, this.mouseY,
							k))
						return;
				}
			}
		}
		try {
			super.handleMouseInput();
		} catch (IOException e) {
			Logger.warning("Um. the handleMouseInput method threw an IOException. Why? I don't know.");
			e.printStackTrace();// Why in the world would typing a key throw an IOException. I have no idea.
		}
	}

	protected void mouseClicked(int paramInt1, int paramInt2, int paramInt3) {
		paramInt1 -= this.guiLeft;
		paramInt2 -= this.guiTop;
		for (int i = this.elements.size(); i-- > 0;) {
			GuiElement localElementScreenBase = (GuiElement) this.elements.get(i);
			if ((localElementScreenBase.isVisible())
					&& (localElementScreenBase.isEnabled())
					&& (localElementScreenBase.intersectsWith(paramInt1, paramInt2))) {
				if (localElementScreenBase.onMousePressed(paramInt1, paramInt2,
						paramInt3)) {
					return;
				}
			}
		}
		
		try {
			super.mouseClicked(paramInt1, paramInt2, paramInt3);
		} catch (IOException e) {
			Logger.warning("Um. the mouseClicked method threw an IOException. Why? I don't know.");
			e.printStackTrace();// Why in the world would typing a key throw an IOException. I have no idea.
		}
	}
	
	@Override
	protected void mouseReleased(int mouseX, int mouseY, int state) {
		if (state == 0) {
			for (int i = this.elements.size(); i-- > 0;) {
				GuiElement localElementScreenBase = (GuiElement) this.elements
						.get(i);
				if ((localElementScreenBase.isVisible())
						&& (localElementScreenBase.isEnabled())) {
					localElementScreenBase.onMouseReleased(mouseX, mouseY);
				}
			}
		}
		super.mouseReleased(mouseX, mouseY, state);
	}
	
//	protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick)
//		mouseX -= this.guiLeft;
//		mouseY -= this.guiTop;
//		int i;
//		if ((clickedMouseButton >= 0) && (clickedMouseButton <= 2)) {
//			for (i = this.elements.size(); i-- > 0;) {
//				ElementScreenBase localElementScreenBase = (ElementScreenBase) this.elements
//						.get(i);
//				if ((localElementScreenBase.isVisible())
//						&& (localElementScreenBase.isEnabled())) {
//					localElementScreenBase.onMouseReleased(mouseX, mouseY);
//				}
//			}
//		}
//		mouseX += this.guiLeft;
//		mouseY += this.guiTop;
//		
//		super.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
//	}

	protected void mouseClickMove(int paramInt1, int paramInt2, int paramInt3,
			long paramLong) {
		super.mouseClickMove(paramInt1, paramInt2, paramInt3, paramLong);
	}

	protected void drawElements(float paramFloat, boolean paramBoolean) {
		int i;
		GuiElement localElementScreenBase;
		if (paramBoolean) {
			for (i = 0; i < this.elements.size(); i++) {
				localElementScreenBase = (GuiElement) this.elements.get(i);
				if (localElementScreenBase.isVisible()) {
					localElementScreenBase.drawForeground(this.mouseX, this.mouseY);
				}
			}
		} else {
			for (i = 0; i < this.elements.size(); i++) {
				localElementScreenBase = (GuiElement) this.elements.get(i);
				if (localElementScreenBase.isVisible()) {
					localElementScreenBase.drawBackground(this.mouseX, this.mouseY,
							paramFloat);
				}
			}
		}
	}
	
	public List<String> handleTooltip(int paramInt1, int paramInt2,
			List<String> paramList) {
		if (this.mc.thePlayer.inventory.getItemStack() == null) {
			addTooltips(paramList);
		}
		return paramList;
	}

	public void addTooltips(List<String> paramList) {
		GuiElement localElementScreenBase = getElementAtPosition(this.mouseX,
				this.mouseY);
		if ((localElementScreenBase != null) && (localElementScreenBase.isVisible())) {
			localElementScreenBase.addTooltip(paramList, this.mouseX, this.mouseY);
		}
	}

	public GuiElement addElement(GuiElement paramElementScreenBase) {
		this.elements.add(paramElementScreenBase);
		return paramElementScreenBase;
	}
	
	protected GuiElement getElementAtPosition(int paramInt1, int paramInt2) {
		for (int i = this.elements.size(); i-- > 0;) {
			GuiElement localElementScreenBase = (GuiElement) this.elements.get(i);
			if (localElementScreenBase.intersectsWith(paramInt1, paramInt2)) {
				return localElementScreenBase;
			}
		}
		return null;
	}
	
	protected final void updateElements() {
		for (int i = this.elements.size(); i-- > 0;) {
			GuiElement localElementScreenBase = (GuiElement) this.elements.get(i);
			if ((localElementScreenBase.isVisible())
					&& (localElementScreenBase.isEnabled())) {
				localElementScreenBase.update(this.mouseX, this.mouseY);
			}
		}
	}

	protected void updateElementInformation() {
	}

	public void handleElementButtonClick(String paramString, int paramInt) {
	}

	public void bindTexture(ResourceLocation paramResourceLocation) {
		this.mc.renderEngine.bindTexture(paramResourceLocation);
	}

//	public IIcon getIcon(String paramString) {
//		return null;
//	}

//	public void drawButton(IIcon paramIIcon, int paramInt1, int paramInt2,
//			int paramInt3, int paramInt4) {
//		drawIcon(paramIIcon, paramInt1, paramInt2, paramInt3);
//	}
//
//	public void drawButton(String paramString, int paramInt1, int paramInt2,
//			int paramInt3, int paramInt4) {
//		drawButton(getIcon(paramString), paramInt1, paramInt2, paramInt3,
//				paramInt4);
//	}
//
//	public void drawFluid(int paramInt1, int paramInt2,
//			FluidStack paramFluidStack, int paramInt3, int paramInt4) {
//		if ((paramFluidStack == null) || (paramFluidStack.getFluid() == null)) {
//			return;
//		}
//		RenderHelper.setBlockTextureSheet();
//		RenderHelper.setColor3ub(paramFluidStack.getFluid().getColor(
//				paramFluidStack));
//		drawTiledTexture(paramInt1, paramInt2, paramFluidStack.getFluid()
//				.getIcon(paramFluidStack), paramInt3, paramInt4);
//	}
//
//	public void drawTiledTexture(int paramInt1, int paramInt2,
//			IIcon paramIIcon, int paramInt3, int paramInt4) {
//		int i = 0;
//		int j = 0;
//		int k = 0;
//		int m = 0;
//		for (i = 0; i < paramInt3; i += 16) {
//			for (j = 0; j < paramInt4; j += 16) {
//				m = Math.min(paramInt3 - i, 16);
//				k = Math.min(paramInt4 - j, 16);
//				drawScaledTexturedModelRectFromIcon(paramInt1 + i, paramInt2
//						+ j, paramIIcon, m, k);
//			}
//		}
//		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
//	}
//
//	public void drawIcon(IIcon paramIIcon, int paramInt1, int paramInt2,
//			int paramInt3) {
//		if (paramInt3 == 0) {
//			RenderHelper.setBlockTextureSheet();
//		} else {
//			RenderHelper.setItemTextureSheet();
//		}
//		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
//		drawTexturedModelRectFromIcon(paramInt1, paramInt2, paramIIcon, 16, 16);
//	}
//
//	public void drawColorIcon(IIcon paramIIcon, int paramInt1, int paramInt2,
//			int paramInt3) {
//		if (paramInt3 == 0) {
//			RenderHelper.setBlockTextureSheet();
//		} else {
//			RenderHelper.setItemTextureSheet();
//		}
//		drawTexturedModelRectFromIcon(paramInt1, paramInt2, paramIIcon, 16, 16);
//	}
//
//	public void drawIcon(String paramString, int paramInt1, int paramInt2,
//			int paramInt3) {
//		drawIcon(getIcon(paramString), paramInt1, paramInt2, paramInt3);
//	}
//
//	public void drawSizedModalRect(int paramInt1, int paramInt2, int paramInt3,
//			int paramInt4, int paramInt5) {
//		int i;
//		if (paramInt1 < paramInt3) {
//			i = paramInt1;
//			paramInt1 = paramInt3;
//			paramInt3 = i;
//		}
//		if (paramInt2 < paramInt4) {
//			i = paramInt2;
//			paramInt2 = paramInt4;
//			paramInt4 = i;
//		}
//		float f1 = (paramInt5 >> 24 & 0xFF) / 255.0F;
//		float f2 = (paramInt5 >> 16 & 0xFF) / 255.0F;
//		float f3 = (paramInt5 >> 8 & 0xFF) / 255.0F;
//		float f4 = (paramInt5 & 0xFF) / 255.0F;
//		Tessellator localTessellator = Tessellator.instance;
//		GL11.glEnable(3042);
//		GL11.glDisable(3553);
//		GL11.glBlendFunc(770, 771);
//		GL11.glColor4f(f2, f3, f4, f1);
//		localTessellator.startDrawingQuads();
//		localTessellator.addVertex(paramInt1, paramInt4, this.zLevel);
//		localTessellator.addVertex(paramInt3, paramInt4, this.zLevel);
//		localTessellator.addVertex(paramInt3, paramInt2, this.zLevel);
//		localTessellator.addVertex(paramInt1, paramInt2, this.zLevel);
//		localTessellator.draw();
//		GL11.glEnable(3553);
//		GL11.glDisable(3042);
//	}
//
//	public void drawSizedRect(int paramInt1, int paramInt2, int paramInt3,
//			int paramInt4, int paramInt5) {
//		int i;
//		if (paramInt1 < paramInt3) {
//			i = paramInt1;
//			paramInt1 = paramInt3;
//			paramInt3 = i;
//		}
//		if (paramInt2 < paramInt4) {
//			i = paramInt2;
//			paramInt2 = paramInt4;
//			paramInt4 = i;
//		}
//		float f1 = (paramInt5 >> 24 & 0xFF) / 255.0F;
//		float f2 = (paramInt5 >> 16 & 0xFF) / 255.0F;
//		float f3 = (paramInt5 >> 8 & 0xFF) / 255.0F;
//		float f4 = (paramInt5 & 0xFF) / 255.0F;
//		Tessellator localTessellator = Tessellator.instance;
//		GL11.glDisable(3553);
//		GL11.glColor4f(f2, f3, f4, f1);
//		localTessellator.startDrawingQuads();
//		localTessellator.addVertex(paramInt1, paramInt4, this.zLevel);
//		localTessellator.addVertex(paramInt3, paramInt4, this.zLevel);
//		localTessellator.addVertex(paramInt3, paramInt2, this.zLevel);
//		localTessellator.addVertex(paramInt1, paramInt2, this.zLevel);
//		localTessellator.draw();
//		GL11.glEnable(3553);
//	}
//
//	public void drawSizedTexturedModalRect(int paramInt1, int paramInt2,
//			int paramInt3, int paramInt4, int paramInt5, int paramInt6,
//			float paramFloat1, float paramFloat2) {
//		float f1 = 1.0F / paramFloat1;
//		float f2 = 1.0F / paramFloat2;
//		Tessellator localTessellator = Tessellator.instance;
//		localTessellator.startDrawingQuads();
//		localTessellator
//				.addVertexWithUV(paramInt1 + 0, paramInt2 + paramInt6,
//						this.zLevel, (paramInt3 + 0) * f1,
//						(paramInt4 + paramInt6) * f2);
//		localTessellator.addVertexWithUV(paramInt1 + paramInt5, paramInt2
//				+ paramInt6, this.zLevel, (paramInt3 + paramInt5) * f1,
//				(paramInt4 + paramInt6) * f2);
//		localTessellator
//				.addVertexWithUV(paramInt1 + paramInt5, paramInt2 + 0,
//						this.zLevel, (paramInt3 + paramInt5) * f1,
//						(paramInt4 + 0) * f2);
//		localTessellator.addVertexWithUV(paramInt1 + 0, paramInt2 + 0,
//				this.zLevel, (paramInt3 + 0) * f1, (paramInt4 + 0) * f2);
//		localTessellator.draw();
//	}
//
//	public void drawScaledTexturedModelRectFromIcon(int paramInt1,
//			int paramInt2, IIcon paramIIcon, int paramInt3, int paramInt4) {
//		if (paramIIcon == null) {
//			return;
//		}
//		double d1 = paramIIcon.getMinU();
//		double d2 = paramIIcon.getMaxU();
//		double d3 = paramIIcon.getMinV();
//		double d4 = paramIIcon.getMaxV();
//		Tessellator localTessellator = Tessellator.instance;
//		localTessellator.startDrawingQuads();
//		localTessellator.addVertexWithUV(paramInt1 + 0, paramInt2 + paramInt4,
//				this.zLevel, d1, d3 + (d4 - d3) * paramInt4 / 16.0D);
//		localTessellator.addVertexWithUV(paramInt1 + paramInt3, paramInt2
//				+ paramInt4, this.zLevel, d1 + (d2 - d1) * paramInt3 / 16.0D,
//				d3 + (d4 - d3) * paramInt4 / 16.0D);
//		localTessellator.addVertexWithUV(paramInt1 + paramInt3, paramInt2 + 0,
//				this.zLevel, d1 + (d2 - d1) * paramInt3 / 16.0D, d3);
//		localTessellator.addVertexWithUV(paramInt1 + 0, paramInt2 + 0,
//				this.zLevel, d1, d3);
//		localTessellator.draw();
//	}

	public void drawTooltip(List<String> paramList) {
		drawTooltipHoveringText(paramList, this.mouseX + this.guiLeft,
				this.mouseY + this.guiTop, this.fontRendererObj);
		this.tooltip.clear();
	}

	protected void drawTooltipHoveringText(List<String> paramList, int paramInt1,
			int paramInt2, FontRenderer paramFontRenderer) {
		if ((paramList == null) || (paramList.isEmpty())) {
			return;
		}
		GL11.glDisable(32826);
		GL11.glDisable(2896);
		GL11.glDisable(2929);
		int i = 0;
		Iterator<String> localIterator = paramList.iterator();
		while (localIterator.hasNext()) {
			String str1 = (String) localIterator.next();
			int k = paramFontRenderer.getStringWidth(str1);
			if (k > i) {
				i = k;
			}
		}
		int j = paramInt1 + 12;
		int k = paramInt2 - 12;
		int m = 8;
		if (paramList.size() > 1) {
			m += 2 + (paramList.size() - 1) * 10;
		}
		if (j + i > this.width) {
			j -= 28 + i;
		}
		if (k + m + 6 > this.height) {
			k = this.height - m - 6;
		}
		this.zLevel = 300.0F;
		itemRender.zLevel = 300.0F;
		int n = -267386864;
		drawGradientRect(j - 3, k - 4, j + i + 3, k - 3, n, n);
		drawGradientRect(j - 3, k + m + 3, j + i + 3, k + m + 4, n, n);
		drawGradientRect(j - 3, k - 3, j + i + 3, k + m + 3, n, n);
		drawGradientRect(j - 4, k - 3, j - 3, k + m + 3, n, n);
		drawGradientRect(j + i + 3, k - 3, j + i + 4, k + m + 3, n, n);
		int i1 = 1347420415;
		int i2 = (i1 & 0xFEFEFE) >> 1 | i1 & 0xFF000000;
		drawGradientRect(j - 3, k - 3 + 1, j - 3 + 1, k + m + 3 - 1, i1, i2);
		drawGradientRect(j + i + 2, k - 3 + 1, j + i + 3, k + m + 3 - 1, i1, i2);
		drawGradientRect(j - 3, k - 3, j + i + 3, k - 3 + 1, i1, i1);
		drawGradientRect(j - 3, k + m + 2, j + i + 3, k + m + 3, i2, i2);
		for (int i3 = 0; i3 < paramList.size(); i3++) {
			String str2 = (String) paramList.get(i3);
			paramFontRenderer.drawStringWithShadow(str2, j, k, -1);
			if (i3 == 0) {
				k += 2;
			}
			k += 10;
		}
		this.zLevel = 0.0F;
		itemRender.zLevel = 0.0F;
		GL11.glEnable(2896);
		GL11.glEnable(2929);
		GL11.glEnable(32826);
	}

	public void mouseClicked(int paramInt) {
		try {
			super.mouseClicked(this.guiLeft + this.mouseX, this.guiTop
					+ this.mouseY, paramInt);
		} catch (IOException e) {
			Logger.warning("Um. the mouseClicked method threw an IOException. Why? I don't know.");
			e.printStackTrace();// Why in the world would typing a key throw an IOException. I have no idea.
		}
	}

	public FontRenderer getFontRenderer() {
		return this.fontRendererObj;
	}

	protected int getCenteredOffset(String paramString) {
		return getCenteredOffset(paramString, this.xSize);
	}

	protected int getCenteredOffset(String paramString, int paramInt) {
		return (paramInt - this.fontRendererObj.getStringWidth(paramString)) / 2;
	}

	public int getGuiLeft() {
		return this.guiLeft;
	}

	public int getGuiTop() {
		return this.guiTop;
	}

	public int getMouseX() {
		return this.mouseX;
	}

	public int getMouseY() {
		return this.mouseY;
	}

	public void overlayRecipe() {
	}
}

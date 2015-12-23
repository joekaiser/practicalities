package practicalities.lib.client.gui;

import java.util.List;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

public abstract class GuiElement {
	protected ScreenBase gui;
	protected ResourceLocation texture;
	private FontRenderer fontRenderer;
	protected int posX;
	protected int posY;
	protected int sizeX;
	protected int sizeY;
	protected int texW = 256;
	protected int texH = 256;
	protected String name;
	private boolean visible = true;
	private boolean enabled = true;

	public GuiElement(ScreenBase paramGuiBase, int paramInt1,
			int paramInt2) {
		this.gui = paramGuiBase;
		this.posX = paramInt1;
		this.posY = paramInt2;
	}

	public GuiElement(ScreenBase paramGuiBase, int paramInt1,
			int paramInt2, int paramInt3, int paramInt4) {
		this.gui = paramGuiBase;
		this.posX = paramInt1;
		this.posY = paramInt2;
		this.sizeX = paramInt3;
		this.sizeY = paramInt4;
	}

	public GuiElement setName(String paramString) {
		this.name = paramString;
		return this;
	}

	public GuiElement setPosition(int paramInt1, int paramInt2) {
		this.posX = paramInt1;
		this.posY = paramInt2;
		return this;
	}

	public GuiElement setSize(int paramInt1, int paramInt2) {
		this.sizeX = paramInt1;
		this.sizeY = paramInt2;
		return this;
	}

	public GuiElement setTexture(String paramString, int paramInt1,
			int paramInt2) {
		this.texture = new ResourceLocation(paramString);
		this.texW = paramInt1;
		this.texH = paramInt2;
		return this;
	}

	public final GuiElement setVisible(boolean paramBoolean) {
		this.visible = paramBoolean;
		return this;
	}

	public boolean isVisible() {
		return this.visible;
	}

	public final GuiElement setEnabled(boolean paramBoolean) {
		this.enabled = paramBoolean;
		return this;
	}

	public boolean isEnabled() {
		return this.enabled;
	}

	public void update(int paramInt1, int paramInt2) {
		update();
	}

	public void update() {
	}

	public abstract void drawBackground(int paramInt1, int paramInt2,
			float paramFloat);

	public abstract void drawForeground(int paramInt1, int paramInt2);

	public void addTooltip(List<String> paramList, int mouseX, int mouseY) {
	}

	public void drawModalRect(int x, int y, int u, int v, int w, int h) {
		this.gui.drawTexturedModalRect(x, y, u, v, w, h);
	}

	public void drawStencil(int paramInt1, int paramInt2, int paramInt3,
			int paramInt4, int paramInt5) {
		GL11.glDisable(3553);
		GL11.glStencilFunc(519, paramInt5, paramInt5);
		GL11.glStencilOp(0, 0, 7681);
		GL11.glStencilMask(1);
		GL11.glColorMask(false, false, false, false);
		GL11.glDepthMask(false);

		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex3d(paramInt1, paramInt4, 0.0D);
		GL11.glVertex3d(paramInt3, paramInt4, 0.0D);
		GL11.glVertex3d(paramInt3, paramInt2, 0.0D);
		GL11.glVertex3d(paramInt1, paramInt2, 0.0D);
		GL11.glEnd();

		GL11.glEnable(3553);
		GL11.glStencilFunc(514, paramInt5, paramInt5);
		GL11.glStencilMask(0);
		GL11.glColorMask(true, true, true, true);
		GL11.glDepthMask(true);
	}

	public void drawCenteredString(FontRenderer paramFontRenderer,
			String paramString, int paramInt1, int paramInt2, int paramInt3) {
		paramFontRenderer.drawStringWithShadow(paramString, paramInt1
				- paramFontRenderer.getStringWidth(paramString) / 2, paramInt2,
				paramInt3);
	}

	public boolean onMousePressed(int paramInt1, int paramInt2, int paramInt3) {
		return false;
	}

	public void onMouseReleased(int paramInt1, int paramInt2) {
	}

	public boolean onMouseWheel(int paramInt1, int paramInt2, int paramInt3) {
		return false;
	}

	public boolean onKeyTyped(char paramChar, int paramInt) {
		return false;
	}

	public boolean intersectsWith(int paramInt1, int paramInt2) {
		if ((paramInt1 >= this.posX) && (paramInt1 <= this.posX + this.sizeX)
				&& (paramInt2 >= this.posY)
				&& (paramInt2 <= this.posY + this.sizeY)) {
			return true;
		}
		return false;
	}

	public FontRenderer getFontRenderer() {
		return this.fontRenderer == null ? this.gui.getFontRenderer()
				: this.fontRenderer;
	}

	public GuiElement setFontRenderer(FontRenderer paramFontRenderer) {
		this.fontRenderer = paramFontRenderer;
		return this;
	}

	public final String getName() {
		return this.name;
	}

	public final ScreenBase getScreen() {
		return this.gui;
	}

	public final int getPosY() {
		return this.posY;
	}

	public final int getPosX() {
		return this.posX;
	}

	public final int getHeight() {
		return this.sizeY;
	}

	public final int getWidth() {
		return this.sizeX;
	}
}

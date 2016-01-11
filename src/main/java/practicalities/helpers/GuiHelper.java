package practicalities.helpers;

import java.util.Iterator;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.RenderTickEvent;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class GuiHelper {

	private static GuiHelper instance;
	public static GuiHelper instance() {
		if(instance == null) {
			instance = new GuiHelper();
			MinecraftForge.EVENT_BUS.register(instance);
		}
		return instance;
	}
	
	public static Minecraft mc;
	public static FontRenderer fontRendererObj;
	private RenderItem itemRender;
	
	public float zLevel = 0;
	
	public GuiHelper() {
		mc = Minecraft.getMinecraft();
		fontRendererObj = mc.fontRendererObj;
		itemRender = mc.getRenderItem();
	}
	
	public void setupItemRender() {
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		RenderHelper.enableGUIStandardItemLighting();
	}
	
	public void drawItemStack(ItemStack stack, int x, int y) {
		String size = "";
		if(stack != null && stack.stackSize != 1)
			size = "" + stack.stackSize;
		
        itemRender.zLevel = 200.0F;
        FontRenderer font = null;
        if (stack != null) font = stack.getItem().getFontRenderer(stack);
        if (font == null) font = fontRendererObj;
        itemRender.renderItemAndEffectIntoGUI(stack, x, y);
        itemRender.renderItemOverlayIntoGUI(font, stack, x, y, size);
        itemRender.zLevel = 0.0F;
	}
	
	public void cleanupItemRender() {
		RenderHelper.disableStandardItemLighting();
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
	}
	
	public void drawItemTooltip(ItemStack stack, int mouseX, int mouseY) {
		if(stack == null)
			return;
		List<String> list = stack.getTooltip(
				mc.thePlayer,
				mc.gameSettings.advancedItemTooltips);

		for (int k = 0; k < list.size(); ++k) {
			if (k == 0) {
				list.set(k,
						stack.getRarity().rarityColor + (String) list.get(k));
			} else {
				list.set(k, EnumChatFormatting.GRAY + (String) list.get(k));
			}
		}

		FontRenderer font = stack.getItem().getFontRenderer(stack);
		drawHoveringText(list, mouseX, mouseY, (font == null ? fontRendererObj : font));
	}
	
	// vvvv tick handler vvvv
	
	private int ticks;
	private float partialTicks;
	
	@SubscribeEvent
	public void renderTick(RenderTickEvent event) {
		if(event.phase == Phase.START)
			partialTicks = event.renderTickTime;
	}

	@SubscribeEvent
	public void clientTickEnd(ClientTickEvent event) {
		if(event.phase == Phase.END) {
			ticks++;
		}
	}
	
	public double getTicks() {
		return ticks+partialTicks;
	}
	
	public double tickAnimate(double start, double length) {
		return (getTicks()-start)/length;
	}
	
	double intrFrac;
	
	public void loadInterpolationFraction(double frac) {
		intrFrac = frac;
	}
	
	public int intr(int from, int to) {
		return (int)intr((double)from, (double)to);
	}
	
	public double intr(double from, double to) {
		return from + (intrFrac * (to-from) );
	}
	
	// vvvv more obscure/complicated stuff vvvv
	
	public void drawHoveringText(List<String> lines, int x, int y, FontRenderer font)
    {
        if (!lines.isEmpty())
        {
            GL11.glDisable(GL12.GL_RESCALE_NORMAL);
            RenderHelper.disableStandardItemLighting();
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glDisable(GL11.GL_DEPTH_TEST);
            int k = 0;
            Iterator<String> iterator = lines.iterator();

            while (iterator.hasNext())
            {
                String s = (String)iterator.next();
                int l = font.getStringWidth(s);

                if (l > k)
                {
                    k = l;
                }
            }

            int j2 = x + 12;
            int k2 = y - 12;
            int i1 = 8;

            if (lines.size() > 1)
            {
                i1 += 2 + (lines.size() - 1) * 10;
            }

            if (j2 + k > mc.displayWidth)
            {
                j2 -= 28 + k;
            }

            if (k2 + i1 + 6 > mc.displayHeight)
            {
                k2 = mc.displayHeight - i1 - 6;
            }

            this.zLevel = 300.0F;
            itemRender.zLevel = 300.0F;
            int j1 = -267386864;
            this.drawGradientRect(j2 - 3, k2 - 4, j2 + k + 3, k2 - 3, j1, j1);
            this.drawGradientRect(j2 - 3, k2 + i1 + 3, j2 + k + 3, k2 + i1 + 4, j1, j1);
            this.drawGradientRect(j2 - 3, k2 - 3, j2 + k + 3, k2 + i1 + 3, j1, j1);
            this.drawGradientRect(j2 - 4, k2 - 3, j2 - 3, k2 + i1 + 3, j1, j1);
            this.drawGradientRect(j2 + k + 3, k2 - 3, j2 + k + 4, k2 + i1 + 3, j1, j1);
            int k1 = 1347420415;
            int l1 = (k1 & 16711422) >> 1 | k1 & -16777216;
            this.drawGradientRect(j2 - 3, k2 - 3 + 1, j2 - 3 + 1, k2 + i1 + 3 - 1, k1, l1);
            this.drawGradientRect(j2 + k + 2, k2 - 3 + 1, j2 + k + 3, k2 + i1 + 3 - 1, k1, l1);
            this.drawGradientRect(j2 - 3, k2 - 3, j2 + k + 3, k2 - 3 + 1, k1, k1);
            this.drawGradientRect(j2 - 3, k2 + i1 + 2, j2 + k + 3, k2 + i1 + 3, l1, l1);

            for (int i2 = 0; i2 < lines.size(); ++i2)
            {
                String s1 = (String)lines.get(i2);
                font.drawStringWithShadow(s1, j2, k2, -1);

                if (i2 == 0)
                {
                    k2 += 2;
                }

                k2 += 10;
            }

            this.zLevel = 0.0F;
            itemRender.zLevel = 0.0F;
            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glEnable(GL11.GL_DEPTH_TEST);
            RenderHelper.enableStandardItemLighting();
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        }
    }
	
	public void drawColoredRect(int x1, int y1, int x2, int y2, int color) {
		Color c = Color.rgba(color);
		drawColoredRect(x1, y1, x2, y2, c.r, c.g, c.b, c.a);
	}
	
	public void drawColoredRect(int x1, int y1, int x2, int y2, double r, double g, double b, double a) {
		GL11.glColor4d(r, g, b, a);
		GlStateManager.enableBlend();
		GlStateManager.disableTexture2D();
		GL11.glBegin(GL11.GL_QUADS);
		
		GL11.glVertex2d(x1, y1);
		GL11.glVertex2d(x1, y2);
		GL11.glVertex2d(x2, y2);
		GL11.glVertex2d(x2, y1);
		
		GL11.glEnd();
		GlStateManager.enableTexture2D();
	}
	
	public void drawGradientRect(int left, int top, int right, int bottom, int startColor, int endColor)
    {
		float f = (float)(startColor >> 24 & 255) / 255.0F;
        float f1 = (float)(startColor >> 16 & 255) / 255.0F;
        float f2 = (float)(startColor >> 8 & 255) / 255.0F;
        float f3 = (float)(startColor & 255) / 255.0F;
        float f4 = (float)(endColor >> 24 & 255) / 255.0F;
        float f5 = (float)(endColor >> 16 & 255) / 255.0F;
        float f6 = (float)(endColor >> 8 & 255) / 255.0F;
        float f7 = (float)(endColor & 255) / 255.0F;
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.shadeModel(7425);
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
        worldrenderer.pos((double)right, (double)top, (double)this.zLevel).color(f1, f2, f3, f).endVertex();
        worldrenderer.pos((double)left, (double)top, (double)this.zLevel).color(f1, f2, f3, f).endVertex();
        worldrenderer.pos((double)left, (double)bottom, (double)this.zLevel).color(f5, f6, f7, f4).endVertex();
        worldrenderer.pos((double)right, (double)bottom, (double)this.zLevel).color(f5, f6, f7, f4).endVertex();
        tessellator.draw();
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }
	
	public static class Color {
		public double r,g,b,a;
		
		private Color(double r, double g, double b, double a) {
			this.r = r; this.g = g; this.b = b; this.a = a;
		}
		
		private Color(int color) {
			r = (color >> 24) & 0xFF;
			g = (color >> 16) & 0xFF;
			b = (color >> 8) & 0xFF;
			a = (color >> 0) & 0xFF;
			
			r /= 255;
			g /= 255;
			b /= 255;
			a /= 255;
		}
		
		
		public static Color rgb(int rgb) {
			return new Color(rgb << 8);
		}
		public static Color rgb(double r, double g, double b) {
			return new Color(r,g,b,1.0);
		}
		public static Color rgba(int rgba) {
			return new Color(rgba);
		}
		public static Color rgba(double r, double g, double b, double a) {
			return new Color(r,g,b,a);
		}
	}
	
}

package practicalities.client.render;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.shader.TesselatorVertexState;
import net.minecraft.util.IIcon;

public class IconTessellator extends Tessellator {
	public static final IIcon FULL_ICON = new IIcon() {
		public int getIconWidth() { return 1; }
		public int getIconHeight() { return 1; }
		public String getIconName() { return "FULL_ICON"; }

		public float getMinU() { return 0; }
		public float getMaxU() { return 1; }
		public float getInterpolatedU(double d) { return (float)d; }
		
		public float getMinV() { return 0; }
		public float getMaxV() { return 1; }
		public float getInterpolatedV(double d) { return (float)d; }
	};
	public IIcon icon = FULL_ICON;
	
	Tessellator t;
	public IconTessellator() {
		t = Tessellator.instance;
	}
	public void addVertexWithUV(double a, double b, double c, double d, double e) { 
		this.setTextureUV(d, e);
        this.addVertex(a, b, c);
	}

    public void setTextureUV(double u, double v) {
    	t.setTextureUV(icon.getInterpolatedU(u*16), icon.getInterpolatedV(v*16));
    }
	
	public int draw() { return t.draw(); }
    public TesselatorVertexState getVertexState(float a, float b, float c) { return t.getVertexState(a, b, c); }
    public void startDrawingQuads() { t.startDrawingQuads(); }
    public void startDrawing(int p_78371_1_) { t.startDrawing(p_78371_1_); }
    public void setBrightness(int p_78380_1_) { t.setBrightness(p_78380_1_); }
    public void setColorOpaque_F(float p_78386_1_, float p_78386_2_, float p_78386_3_) { t.setColorOpaque_F(p_78386_1_, p_78386_2_, p_78386_3_); }
    public void setColorRGBA_F(float p_78369_1_, float p_78369_2_, float p_78369_3_, float p_78369_4_) { t.setColorRGBA_F(p_78369_1_, p_78369_2_, p_78369_3_, p_78369_4_); }
    public void setColorOpaque(int p_78376_1_, int p_78376_2_, int p_78376_3_) { t.setColorOpaque(p_78376_1_, p_78376_2_, p_78376_3_); }
    public void setColorRGBA(int p_78370_1_, int p_78370_2_, int p_78370_3_, int p_78370_4_) { t.setColorRGBA(p_78370_1_, p_78370_2_, p_78370_3_, p_78370_4_); }
    public void func_154352_a(byte p_154352_1_, byte p_154352_2_, byte p_154352_3_) { t.func_154352_a(p_154352_1_, p_154352_2_, p_154352_3_); }
    public void addVertex(double p_78377_1_, double p_78377_3_, double p_78377_5_) { t.addVertex(p_78377_1_, p_78377_3_, p_78377_5_); }
    public void setColorOpaque_I(int p_78378_1_) { t.setColorOpaque_I(p_78378_1_); }
    public void setColorRGBA_I(int p_78384_1_, int p_78384_2_) { t.setColorRGBA_I(p_78384_1_, p_78384_2_); }
    public void disableColor() { t.disableColor(); }
    public void setNormal(float p_78375_1_, float p_78375_2_, float p_78375_3_) { t.setNormal(p_78375_1_, p_78375_2_, p_78375_3_); }
    public void setTranslation(double p_78373_1_, double p_78373_3_, double p_78373_5_) { t.setTranslation(p_78373_1_, p_78373_3_, p_78373_5_); }
    public void addTranslation(float p_78372_1_, float p_78372_2_, float p_78372_3_) { t.addTranslation(p_78372_1_, p_78372_2_, p_78372_3_); }


}

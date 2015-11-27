package practicalities;

import net.minecraft.util.IIcon;

public class BasicIcon implements IIcon {

	int x,y,width,height,texWidth,texHeight;
	
	public BasicIcon(int x, int y, int width, int height, int texWidth, int texHeight) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.texWidth = texWidth;
		this.texHeight = texHeight;
	}
	
	public BasicIcon(int x, int y, int width, int height, int texSize) {
		this(x,y,width,height,texSize,texSize);
	}
	
	@Override
	public int getIconWidth() {
		return width;
	}

	@Override
	public int getIconHeight() {
		return height;
	}

	@Override
	public float getMinU() {
		return x;
	}

	@Override
	public float getMaxU() {
		return x+width;
	}

	@Override
	public float getInterpolatedU(double per) {
		return (float) ((width*per)+x);
	}

	@Override
	public float getMinV() {
		return y;
	}

	@Override
	public float getMaxV() {
		return y+height;
	}

	@Override
	public float getInterpolatedV(double per) {
		return (float) ((height*per)+y);
	}

	@Override
	public String getIconName() {
		return null;
	}

}

package practicalities.client.render;

public interface ITeslaRenderVars {
	public int getCountdown(int i);
	public Lightning getLighting(int i);
	public void setCountdown(int i, int c);
	public void setLightning(int i, Lightning l);
}

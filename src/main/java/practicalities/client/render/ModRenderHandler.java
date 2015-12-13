package practicalities.client.render;

import net.minecraft.util.IIcon;
import codechicken.lib.vec.Vector3;

public class ModRenderHandler {
	public static RenderModel tesla;
	public static IIcon teslaIcon;

	public static void init() {
		tesla = new RenderModel("tesla") {
			public IIcon getIcon() {
				return ModRenderHandler.teslaIcon;
			}
			public Vector3 blockOffset() {
				return new Vector3(0, 0, 1);
			}
			public Vector3 handOffset() {
				return new Vector3(0, -0.5, 1);
			}
			@Override
			public double handScale() {
				return 0.65;
			}
		};
	}
	
	public static void reloadModels() {
		tesla.loadModel();
	}
}

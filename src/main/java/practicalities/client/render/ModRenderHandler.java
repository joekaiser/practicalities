package practicalities.client.render;

import practicalities.client.render.tesr.TeslaCoilRenderer;
import practicalities.machine.fieldrepeater.TileFieldRepeater;
import practicalities.machine.teslacoil.TileTeslaCoil;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.IIcon;
import codechicken.lib.vec.Vector3;
import cpw.mods.fml.client.registry.ClientRegistry;

public class ModRenderHandler {
	public static RenderModel tesla;
	public static RenderModel fieldRepeater;
	
	public static IIcon teslaIcon;
	public static IIcon fieldRepeaterIcon;

	public static TileEntitySpecialRenderer teslaBoltRenderer;
	
	
	public static void init() {
		tesla = new RenderModel("tesla") {
			public IIcon getIcon() {
				return ModRenderHandler.teslaIcon;
			}
			public Vector3 blockOffset() {
				return new Vector3(0, 0, 0);
			}
			public Vector3 handOffset() {
				return new Vector3(0, -0.5, 0);
			}
			@Override
			public double handScale() {
				return 0.65;
			}
		};
		
		fieldRepeater = new RenderModel("tesla") {
			public IIcon getIcon() {
				return ModRenderHandler.fieldRepeaterIcon;
			}
			public Vector3 blockOffset() {
				return new Vector3(0, 0, 0);
			}
			public Vector3 handOffset() {
				return new Vector3(0, -0.5, 0);
			}
			@Override
			public double handScale() {
				return 0.65;
			}
		};
		
		teslaBoltRenderer = new TeslaCoilRenderer();
		ClientRegistry.bindTileEntitySpecialRenderer(TileTeslaCoil.class,     teslaBoltRenderer);
		ClientRegistry.bindTileEntitySpecialRenderer(TileFieldRepeater.class, teslaBoltRenderer);
	}
	
	public static void reloadModels() {
		tesla.loadModel();
		fieldRepeater.loadModel();
	}
}

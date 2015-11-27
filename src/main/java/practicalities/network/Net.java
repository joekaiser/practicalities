package practicalities.network;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

public class Net {

	public static SimpleNetworkWrapper channel;
	
	public static void init() {
		channel = NetworkRegistry.INSTANCE.newSimpleChannel("practicalities");
		channel.registerMessage(ToggleMagnetPacket.Handler.class, ToggleMagnetPacket.class, 0, Side.SERVER);
		channel.registerMessage(ButtonClickPacket.Handler.class, ButtonClickPacket.class, 1, Side.SERVER);
	}
	
}

package practicalities;

import practicalities.registers.ItemRegister;

public class ProxyClient extends ProxyCommon {

	@Override
	public void init() {
		super.init();
		registerRenders();
	}
	
	@Override
	public void registerRenders() {
		ItemRegister.registerRenders();
	}
}

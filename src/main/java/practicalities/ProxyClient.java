package practicalities;

import practicalities.registers.GuideRegister;
import practicalities.registers.ItemRegister;

public class ProxyClient extends ProxyCommon {

	@Override
	public void init() {
		super.init();
		registerRenders();
		GuideRegister.init();
	}
	
	@Override
	public void registerRenders() {
		ItemRegister.registerRenders();
	}
}

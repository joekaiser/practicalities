package practicalities;

import practicalities.registers.ItemRegister;
import practicalities.registers.RecipeRegister;

public class ProxyCommon {

	public void preInit() {
		ItemRegister.init();
		
	}

	public void init() {
		new RecipeRegister().init();
	}

	public void postInit() {

	}

	public void registerKeyBindings() {

	}

	public void registerRenders() {

	}

}

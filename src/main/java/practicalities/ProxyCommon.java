package practicalities;

import practicalities.registers.ItemRegister;
import practicalities.registers.RecipeRegister;

public class ProxyCommon {

	public void preInit() {
		ItemRegister.init();
		new RecipeRegister().init();
	}

	public void init() {

	}

	public void postInit() {

	}

	public void registerKeyBindings() {

	}

	public void registerRenders() {

	}

}

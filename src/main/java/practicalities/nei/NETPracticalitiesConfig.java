package practicalities.nei;

import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;
import practicalities.PracticalitiesMod;

public class NETPracticalitiesConfig implements IConfigureNEI {

	@Override
	public String getName() {
		return PracticalitiesMod.MODID;
	}

	@Override
	public String getVersion() {
		return PracticalitiesMod.VERSION;
	}

	@Override
	public void loadConfig() {
		//API.hideItem(new ItemStack());
		
	}

}

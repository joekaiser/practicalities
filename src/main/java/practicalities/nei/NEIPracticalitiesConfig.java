package practicalities.nei;

import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import practicalities.PracticalitiesMod;
import practicalities.register.ModBlocks;

public class NEIPracticalitiesConfig implements IConfigureNEI {

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
		API.hideItem(new ItemStack(ModBlocks.multiblockPart,1,OreDictionary.WILDCARD_VALUE));
		API.hideItem(new ItemStack(ModBlocks.fakeTorch));
		
	}

}

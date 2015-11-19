package practicalities.items;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import practicalities.PracticalitiesMod;

//simple wrapper class used for creating items that don't need anything special
public class ItemCraftingBase extends Item{

	public ItemCraftingBase(String name) {
		GameRegistry.registerItem(this, name);
		setUnlocalizedName(name);
		setCreativeTab(PracticalitiesMod.tab);
		setTextureName(PracticalitiesMod.TEXTURE_BASE+name);
	}
}

package practicalities.items;

import practicalities.PracticalitiesMod;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemSitisStick extends Item {
	
	public ItemSitisStick() {
		String name = "sitisStick";
		GameRegistry.registerItem(this, name);
		setUnlocalizedName(name);
		setCreativeTab(PracticalitiesMod.tab);
		setTextureName(PracticalitiesMod.TEXTURE_BASE+name);
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if(!world.isRemote && !player.capabilities.isCreativeMode && world.isRaining()) {
			world.provider.resetRainAndThunder();
			stack.stackSize--;
			if(stack.stackSize == 0)
				stack = null;
		}
		return stack;
	}
}

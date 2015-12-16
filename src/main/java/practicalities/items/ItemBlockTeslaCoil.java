package practicalities.items;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import practicalities.base.ItemBlockBase;
import practicalities.register.ModMultiblocks;

public class ItemBlockTeslaCoil extends ItemBlockBase {

	public ItemBlockTeslaCoil(Block block) {
		super(block);
	}
	
	@Override
	public boolean canPlaceBlockAt(ItemStack stack, EntityPlayer player, World w, int x, int y, int z, int side) {
		return ModMultiblocks.tesla.checkBefore(w, x, y, z, 0);
	}
	
	@Override
	public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int metadata) {
		
		if(!super.placeBlockAt(stack, player, world, x, y, z, side, hitX, hitY, hitZ, metadata)) {
			return false;
		}
		ModMultiblocks.tesla.spawn(world, x, y, z, 0);
		return true;
	}
}

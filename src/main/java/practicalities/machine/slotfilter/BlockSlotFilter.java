package practicalities.machine.slotfilter;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import practicalities.PracticalitiesMod;
import practicalities.blocks.BlockBase;
import practicalities.gui.GuiHandler;

public class BlockSlotFilter extends BlockBase {

	public BlockSlotFilter() {
		super(Material.iron, "slotfilter", 1, null);
		
	}
	
	@Override
	public boolean hasTileEntity() {
		return true;
	}

	@Override
	public TileEntity createTileEntity(World world, int metadata) {
		return new TileSlotFilter();
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int p6, float p7, float p8,
			float p9) {

		player.openGui(PracticalitiesMod.instance, GuiHandler.TILE_ID, world, x, y, z);

		return true;

	}
	
}

package practicalities.machine.inventoryfilter;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import practicalities.PracticalitiesMod;
import practicalities.blocks.BlockBase;
import practicalities.gui.GuiHandler;

public class BlockInventoryFilter extends BlockBase {

	public BlockInventoryFilter() {
		super(Material.iron, "inventoryfilter", 1, null);
		
	}
	
	@Override
	public boolean hasTileEntity() {
		return true;
	}

	@Override
	public TileEntity createTileEntity(World world, int metadata) {
		return new TileInventoryFilter();
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int p6, float p7, float p8,
			float p9) {

		player.openGui(PracticalitiesMod.instance, GuiHandler.TILE_ID, world, x, y, z);

		return true;

	}
	
}

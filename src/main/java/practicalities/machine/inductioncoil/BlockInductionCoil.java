package practicalities.machine.inductioncoil;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import practicalities.blocks.BlockBase;

public class BlockInductionCoil extends BlockBase {

	public BlockInductionCoil() {
		super(Material.rock, "inductionCoil", 1, null);
	}
	
	@Override
	public void onBlockAdded(World w, int x, int y, int z) {
		InductionCoilManager.addCoil(w, x, y, z);
		super.onBlockAdded(w, x, y, z);
	}
	
	@Override
	public void onBlockPreDestroy(World w, int x, int y, int z, int meta) {
		InductionCoilManager.removeCoil(w, x, y, z);
		super.onBlockPreDestroy(w, x, y, z, meta);
	}

	@Override
	public boolean hasTileEntity(int meta) {
		return true;
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileInductionCoil();
	}
	
}

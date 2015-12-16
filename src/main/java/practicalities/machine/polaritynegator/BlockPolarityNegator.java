package practicalities.machine.polaritynegator;

import practicalities.base.BlockBase;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;

public class BlockPolarityNegator extends BlockBase {

	public BlockPolarityNegator() {
		super(Material.rock, "polarityNegator", 1, null);
	}
	
	@Override
	public void onBlockAdded(World w, int x, int y, int z) {
		PolarityNegatorManager.addNegator(w, x, y, z);
		super.onBlockAdded(w, x, y, z);
	}
	
	@Override
	public void onBlockPreDestroy(World w, int x, int y, int z, int meta) {
		PolarityNegatorManager.removeNegator(w, x, y, z);
		super.onBlockPreDestroy(w, x, y, z, meta);
	}
	
	private void updateRedstone(World w, int x, int y, int z) {
		if(w.isBlockIndirectlyGettingPowered(x, y, z)) {
			PolarityNegatorManager.removeNegator(w, x, y, z);
		} else {
			PolarityNegatorManager.addNegator(w, x, y, z);
		}
	}
	
	@Override
	public void onNeighborBlockChange(World w, int x, int y, int z, Block paramBlock) {
		updateRedstone(w, x, y, z);
		super.onNeighborBlockChange(w, x, y, z, paramBlock);
	}

}

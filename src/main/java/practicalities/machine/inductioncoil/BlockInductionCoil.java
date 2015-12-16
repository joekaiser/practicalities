package practicalities.machine.inductioncoil;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import practicalities.base.BlockBase;

public class BlockInductionCoil extends BlockBase {

	IIcon top, bottom, side;
	
	public BlockInductionCoil() {
		super(Material.rock, "inductioncoil", 1, null);
	}
	
	@Override
	public void registerBlockIcons(IIconRegister ir) {
		top = ir.registerIcon(getTexture(name + "/top"));
		bottom = ir.registerIcon(getTexture(name + "/bottom"));
		side = ir.registerIcon(getTexture(name + "/side"));
	}
	
	@Override
	public IIcon getIcon(int side, int meta) {
		
		if(side == ForgeDirection.UP.ordinal() ) {
			return top;
		}
		if(side == ForgeDirection.DOWN.ordinal()) {
			return bottom;
		}
		return this.side;
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

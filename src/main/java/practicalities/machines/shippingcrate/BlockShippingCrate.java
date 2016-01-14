package practicalities.machines.shippingcrate;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import practicalities.PracticalitiesMod;
import practicalities.blocks.BlockBase;

public class BlockShippingCrate extends BlockBase implements ITileEntityProvider {


	public BlockShippingCrate() {
		super(Material.wood, "shippingcrate");
		setStepSound(soundTypeWood);
	}


	@Override
	public boolean hasTileEntity() {
		return true;
	}


	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileShippingCrate();
	}


//	@Override
//	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int p6, float p7, float p8,
//			float p9) {
//
//		player.openGui(PracticalitiesMod.instance, GuiHandler.TILE_ID, world, x, y, z);
//
//		return true;
//
//	}

}

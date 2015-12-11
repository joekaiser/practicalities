package practicalities.machine.teslacoil;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import practicalities.blocks.BlockBase;

public class BlockTeslaCoil extends BlockBase implements ITileEntityProvider {

	public BlockTeslaCoil() {
		super(Material.rock, "teslacoil", 1, null);
		setStepSound(soundTypeStone);
	}
	
	@Override
	public boolean canCreatureSpawn(EnumCreatureType type, IBlockAccess world, int x, int y, int z) {
		return true;
	}

	@Override
	public boolean hasTileEntity() {
		return true;
	}

	@Override
	public TileEntity createTileEntity(World world, int metadata) {
		return new TileTeslaCoil();
	}

//	@Override
//	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int p6, float p7, float p8,
//			float p9) {
//
////		if(!player.isSneaking()){
////			player.openGui(PracticalitiesMod.instance, GuiHandler.TILE_ID, world, x, y, z);
////			return true;
////		}
//		
//		return false;
//		
//	}

}

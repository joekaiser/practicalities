package practicalities.machine.teslacoil;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import practicalities.base.BlockBase;
import practicalities.client.render.ModRenderHandler;
import practicalities.register.ModMultiblocks;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockTeslaCoil extends BlockBase implements ITileEntityProvider {

	
	public BlockTeslaCoil() {
		super(Material.rock, "teslacoil", 1, ItemBlockTeslaCoil.class);
		setStepSound(soundTypeStone);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister ir) {
		ModRenderHandler.teslaIcon = ir.registerIcon(getTexture("model/tesla"));
	}
	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderType() {
		return ModRenderHandler.tesla.getRenderId();
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
	
	@Override
	public void onBlockPreDestroy(World world, int x, int y, int z, int meta) {
		super.onBlockPreDestroy(world, x, y, z, meta);
		ModMultiblocks.tesla.despawn(world, x, y, z, 0, false);
	}
	
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
		super.onNeighborBlockChange(world, x, y, z, block);
		if(!ModMultiblocks.tesla.checkAfter(world, x, y, z, 0)) {
			dropBlockAsItem(world, x, y, z, new ItemStack(this));
			world.setBlockToAir(x, y, z);
		}
	}
}

package practicalities.machine.fieldrepeater;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import practicalities.base.BlockBase;
import practicalities.blocks.IBlockFlair;
import practicalities.client.render.ModRenderHandler;
import practicalities.items.ItemBlockTeslaCoil;
import practicalities.machine.inductioncoil.InductionCoilManager;
import practicalities.register.ModMultiblocks;
import practicalities.utils.ModUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockFieldRepeater extends BlockBase implements IBlockFlair, ITileEntityProvider {

	
	public BlockFieldRepeater() {
		super(Material.rock, "fieldrepeater", 1, ItemBlockTeslaCoil.class);
		setStepSound(soundTypeStone);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister ir) {}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player,
			List<String> list, boolean thing) {
		ModUtils.addFlairToList(list, "block.fieldRepeater");
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
	public void onBlockAdded(World w, int x, int y, int z) {
		InductionCoilManager.addCoil(w, x, y, z);
		super.onBlockAdded(w, x, y, z);
	}
	
	@Override
	public boolean hasTileEntity() {
		return true;
	}

	@Override
	public TileEntity createTileEntity(World world, int metadata) {
		return new TileFieldRepeater();
	}
	
	@Override
	public void onBlockPreDestroy(World world, int x, int y, int z, int meta) {
		super.onBlockPreDestroy(world, x, y, z, meta);
		InductionCoilManager.removeCoil(world, x, y, z);
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

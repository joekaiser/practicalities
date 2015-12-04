package practicalities.machine.inventoryfilter;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import practicalities.Logger;
import practicalities.PracticalitiesMod;
import practicalities.blocks.BlockBase;
import practicalities.gui.GuiHandler;

public class BlockInventoryFilter extends BlockBase {
	
	@SideOnly(Side.CLIENT)
	protected IIcon icon_front;
	@SideOnly(Side.CLIENT)
	protected IIcon icon_side;
	
	
	public BlockInventoryFilter() {
		super(Material.iron, "inventoryfilter", 1, null);
		
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister ir) {
		this.icon_front = ir.registerIcon(getTexture("inventoryFilter/inventoryFilter_front"));
		this.icon_side = ir.registerIcon(getTexture("inventoryFilter/inventoryFilter"));
	}

	@Override
	public IIcon getIcon(int side, int meta) {

		if (side == meta) {
			return this.icon_front;
		}

		return this.icon_side;
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
	
	@Override
	public void updateTick(World p_149674_1_, int p_149674_2_, int p_149674_3_, int p_149674_4_, Random p_149674_5_) {
		Logger.info("block updated!!!!");
	}
	
}

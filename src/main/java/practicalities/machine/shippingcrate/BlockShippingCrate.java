package practicalities.machine.shippingcrate;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import practicalities.PracticalitiesMod;
import practicalities.base.BlockBase;
import practicalities.gui.GuiHandler;

public class BlockShippingCrate extends BlockBase implements ITileEntityProvider {

	@SideOnly(Side.CLIENT)
	protected IIcon icon_top;
	@SideOnly(Side.CLIENT)
	protected IIcon icon_side;

	public BlockShippingCrate() {
		super(Material.wood, "shippingcrate", 1, null);
		setStepSound(soundTypeWood);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister ir) {
		this.icon_top = ir.registerIcon(getTexture("shippingcrate/shippingcrate_top"));
		this.icon_side = ir.registerIcon(getTexture("shippingcrate/shippingcrate_side"));
	}

	@Override
	public IIcon getIcon(int side, int meta) {

		if (side == 1) {
			return this.icon_top;
		}

		return this.icon_side;
	}

	@Override
	public boolean hasTileEntity() {
		return true;
	}

	@Override
	public TileEntity createTileEntity(World world, int metadata) {
		return new TileShippingCrate();
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int p6, float p7, float p8,
			float p9) {

		player.openGui(PracticalitiesMod.instance, GuiHandler.TILE_ID, world, x, y, z);

		return true;

	}

}

package practicalities.machine.vampiricgenerator;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import practicalities.blocks.BlockBase;

public class BlockVampiricGenerator extends BlockBase implements ITileEntityProvider {

	@SideOnly(Side.CLIENT)
	protected IIcon icon_top;
	@SideOnly(Side.CLIENT)
	protected IIcon icon_side;

	public BlockVampiricGenerator() {
		super(Material.rock, "vampiricgenerator", 1, null);
		setStepSound(soundTypeStone);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister ir) {
		this.icon_top = ir.registerIcon(getTexture("vampiricgenerator/vampiricgenerator_top"));
		this.icon_side = ir.registerIcon(getTexture("vampiricgenerator/vampiricgenerator_side"));
	}

	@Override
	public IIcon getIcon(int side, int meta) {

		if (side == 1) {
			return this.icon_top;
		}

		return this.icon_side;
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
		return new TileVampiricGenerator();
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int p6, float p7, float p8,
			float p9) {

//		if(!player.isSneaking()){
//			player.openGui(PracticalitiesMod.instance, GuiHandler.TILE_ID, world, x, y, z);
//			return true;
//		}
		
		return false;
		
	}

}

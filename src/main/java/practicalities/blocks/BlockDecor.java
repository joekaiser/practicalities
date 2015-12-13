package practicalities.blocks;

import practicalities.base.BlockBase;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

public class BlockDecor extends BlockBase {

	@SideOnly(Side.CLIENT)
	protected IIcon iconTexture;
	private String name;

	public BlockDecor(Material material, String name, float hardness, SoundType stepSound) {
		super(material, name, hardness, null);
		this.name=name;
		setStepSound(stepSound);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister ir) {
		this.iconTexture = ir.registerIcon(getTexture("decor/" + name));
	}

	@Override
	public IIcon getIcon(int side, int meta) {

		return this.iconTexture;
	}
	
	@Override
	public boolean isOpaqueCube() {
		return true;
	}
}

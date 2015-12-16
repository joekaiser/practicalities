package practicalities.blocks;

import java.util.ArrayList;

import practicalities.PracticalitiesMod;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockTorch;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class BlockFakeTorch extends BlockTorch {
	
	public BlockFakeTorch() {
		GameRegistry.registerBlock(this, PracticalitiesMod.TEXTURE_BASE + "faketorch");
	}
	
	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z,
			int metadata, int fortune) {
		return new ArrayList<ItemStack>();
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister reg) {
		this.blockIcon = reg.registerIcon(PracticalitiesMod.TEXTURE_BASE + "faketorch");
	}
	
	@Override
	public int getLightValue() {
		return 15;
	}
}

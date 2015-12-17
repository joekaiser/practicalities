package practicalities.blocks.multiblock;

import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import practicalities.base.BlockBase;

public class BlockMultiblockPart extends BlockBase {
	
	public BlockMultiblockPart() {
		super(Material.rock, "multiblockPart" , 1, null);
		setCreativeTab(null);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void getSubBlocks(Item p_149666_1_, CreativeTabs p_149666_2_, List p_149666_3_) {
        for(int i = 0; i < 16; i++) {
        	p_149666_3_.add(new ItemStack(p_149666_1_, 1, i));
        }
	}
	
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
    {
        return AxisAlignedBB.getBoundingBox((double)x + this.minX, (double)y + this.minY, (double)z + this.minZ, (double)x + this.maxX, (double)y + this.maxY, (double)z + this.maxZ);
    }

	public boolean canCollideCheck(int meta, boolean boat_thing_that_is_oddly_specific)
    {
        return true;
    }
	
	public int getRenderType()
    {
		return -1; 
    }
	
	public boolean isOpaqueCube()
    {
        return false;
    }
	
	@Override
	public int getMobilityFlag() {
		return 2;
	}
	
	@Override
	public int getLightOpacity(IBlockAccess world, int x, int y, int z) {
		return 16-world.getBlockMetadata(x, y, z);
	}
	
	public void dropBlockAsItemWithChance(World p_149690_1_, int p_149690_2_, int p_149690_3_, int p_149690_4_, int p_149690_5_, float p_149690_6_, int p_149690_7_) {}
	
}

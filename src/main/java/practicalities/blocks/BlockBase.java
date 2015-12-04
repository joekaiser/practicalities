package practicalities.blocks;

import java.util.ArrayList;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import practicalities.PracticalitiesMod;
import cofh.core.block.BlockCoFHBase;
import cofh.core.util.CoreUtils;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockBase extends BlockCoFHBase {

	protected IIcon icon;
	protected String name;
	
	public BlockBase(Material material, String name, float hardness, Class<? extends ItemBlock> itemBlock) {
		super(material);
		this.name = name;
		
		setBlockName(name);
		setCreativeTab(PracticalitiesMod.tab);
		setBlockTextureName(getTexture(name));
		setHardness(hardness);
		if (itemBlock != null) {
			GameRegistry.registerBlock(this, itemBlock, name);
		} else {
			GameRegistry.registerBlock(this, name);
		}

	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister ir) {
		this.icon = ir.registerIcon(getTexture(name + "/" + name));
	}

	@Override
	public IIcon getIcon(int side, int meta) {
		return this.icon;
	}

	protected BlockBase(Material material, String name, float hardness, String harvestTool, int harvestLevel,
			Class<? extends ItemBlock> itemBlock) {
		this(material, name, hardness, itemBlock);
		setHarvestLevel(harvestTool, harvestLevel);
	}

	protected String getTexture(String name) {
		return PracticalitiesMod.TEXTURE_BASE + name;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean initialize() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean postInit() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ArrayList<ItemStack> dismantleBlock(EntityPlayer player, NBTTagCompound nbt, World world, int x, int y,
			int z, boolean returnDrops, boolean simulate) {

		ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
		int blockMetadata = world.getBlockMetadata(x, y, z);

		ret.add(new ItemStack(getItem(world, x, y, z), 1, blockMetadata));

		if (!simulate) {
			if (returnDrops) {
				TileEntity tile = world.getTileEntity(x, y, z);

				if (tile != null && tile instanceof IInventory) {
					IInventory tileInventory = (IInventory) tile;
					for (int i = 0; i < tileInventory.getSizeInventory(); i++) {
						ItemStack stack = tileInventory.getStackInSlot(i);
						if (stack != null) {
							ret.add(stack);
							tileInventory.setInventorySlotContents(i, null);
						}
					}
				}
			} else {
				for (ItemStack s : ret) {
					CoreUtils.dropItemStackIntoWorld(s, world, x, y, z);
				}
			}

			world.setBlockToAir(x, y, z);
		}

		return ret;
	}

//	 public int determineOrientation(World world, int x, int y, int z, EntityLivingBase entity, boolean allowUpDown) {
//		 if (Math.abs((float) entity.posX - (float) x) < 2.0F && Math.abs((float) entity.posZ - (float) z) < 2.0F && allowUpDown) {
//			 double d0 = entity.posY + 1.82D - (double) entity.yOffset;
//			 
//			 if (d0 - (double) y > 2.0D) {
//				 return 1;
//			 }
//			 
//			 if ((double) y - d0 > 0.0D) {
//				 return 0;
//			 }
//		 }
//	
//		 int l = (int)Math.floor((double) (entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
//		 return l == 0 ? 2 : (l == 1 ? 5 : (l == 2 ? 3 : (l == 3 ? 4 : 0)));
//	 }
//
//	 public int getOrientation(int meta) {
//		 return meta & 7;
//	 }
}

package practicalities.items;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import practicalities.helpers.NBTHelper;

public class ItemMatterTransporter extends ItemBase {

	private static final String HAS_BLOCK = "hasblock";
	private static final String HAS_TILEENTITY = "hasentity";
	private static final String STORED_BLOCK = "block";
	private static final String STORED_TILEENTITY = "tileentity";
	private static final String STORED_METADATA = "metadata";
	private int maxDamage = 50;

	public ItemMatterTransporter() {
		super("matterTransporter");
		setMaxStackSize(1);
		setMaxDamage(maxDamage);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack item) {
		return NBTHelper.getBoolean(item, HAS_BLOCK, false);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean p4) {
		if (itemstack.getTagCompound() == null) {
			return;
		}

		if (NBTHelper.getBoolean(itemstack, HAS_BLOCK, false)) {
			Block block = getBlockFromNbt(itemstack);
			String name = new ItemStack(block).getDisplayName();
			list.add(name);
		}
	}

	@Override
	public boolean onItemUse(ItemStack stackIn, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side,
			float hitX, float hitY, float hitZ) {
		
		if(worldIn.isRemote){
			return false;
		}

		boolean hasBlock = NBTHelper.getBoolean(stackIn, HAS_BLOCK, false);
		boolean hasTE = NBTHelper.getBoolean(stackIn, HAS_TILEENTITY, false);
		

		if (hasBlock) {
			int metadata = NBTHelper.getInt(stackIn, STORED_METADATA, 0);
			Block storedBlock = getBlockFromNbt(stackIn);

			BlockPos targetPos = pos.add(side.getDirectionVec());
			Block targetBlock = worldIn.getBlockState(targetPos).getBlock();

			if ((worldIn.isAirBlock(targetPos)) || targetBlock.isReplaceable(worldIn, targetPos)) {

				if (storedBlock.canPlaceBlockAt(worldIn, targetPos)) {
					
					worldIn.setBlockState(targetPos, storedBlock.getStateFromMeta(metadata)); 
					
					if (hasTE) {
						NBTTagCompound tileEntityNbtData = NBTHelper.getCompoundTag(stackIn, STORED_TILEENTITY);
						TileEntity te = worldIn.getTileEntity(targetPos);
						if(te != null) {
						    tileEntityNbtData.setInteger("x", targetPos.getX());
						    tileEntityNbtData.setInteger("y", targetPos.getY());
						    tileEntityNbtData.setInteger("z", targetPos.getZ());
						    te.readFromNBT(tileEntityNbtData);
						    te.markDirty();
						    worldIn.markBlockForUpdate(targetPos);
						}
					}
					
					NBTHelper.setBoolean(stackIn, HAS_BLOCK, false);
					NBTHelper.setBoolean(stackIn, HAS_TILEENTITY, false);

					if (storedBlock.getLocalizedName().toLowerCase().contains("spawner")) {
						stackIn.damageItem(maxDamage / 4, playerIn);
					} else {
						stackIn.damageItem(1, playerIn);
					}
					worldIn.notifyBlockOfStateChange(targetPos, storedBlock);
					worldIn.playSoundAtEntity(playerIn, "random.pop", 0.4F, 0.7F);
					

				}
			}
		} else if (!worldIn.isAirBlock(pos)) { 
			IBlockState targetBlockState = worldIn.getBlockState(pos);
			Block targetBlock = targetBlockState.getBlock(); 
			int metadata = targetBlock.getMetaFromState(targetBlockState);
			

			if (targetBlock.blockParticleGravity == -1.0F
					|| targetBlock.getBlockHardness(worldIn, pos) < 0) {
				return false;
			}
			if (worldIn.getTileEntity(pos) != null) {
				NBTTagCompound savedTE = new NBTTagCompound();
				TileEntity te = worldIn.getTileEntity(pos);
				te.writeToNBT(savedTE);

				NBTHelper.setBoolean(stackIn, HAS_TILEENTITY, true);
				NBTHelper.setTag(stackIn, STORED_TILEENTITY, savedTE);
				
			}

			NBTTagCompound blockNbt = new NBTTagCompound();
			ItemStack i = new ItemStack(targetBlock);
			
			i.writeToNBT(blockNbt);
			NBTHelper.setTag(stackIn, STORED_BLOCK, blockNbt);
			NBTHelper.setInt(stackIn, STORED_METADATA, metadata);

			// some blocks (like redstone) crash to client
			// so before we do the deed, make sure we can recover later
			try {
				getBlockFromNbt(stackIn);
			} catch (Exception e) {
				return false;
			}

			worldIn.removeTileEntity(pos);
			worldIn.setBlockToAir(pos);

			worldIn.playSoundAtEntity(playerIn, "random.pop", 1.0F, 1.0F);
			NBTHelper.setBoolean(stackIn, HAS_BLOCK, true);

		}
		return true;
	}

	@Override
	public void onCreated(ItemStack itemstack, World world, EntityPlayer player) {
		NBTHelper.setBoolean(itemstack, HAS_BLOCK, false);
		NBTHelper.setBoolean(itemstack, HAS_TILEENTITY, false);

	}

	private Block getBlockFromNbt(ItemStack itemstack) {

		NBTTagCompound storedBlock = NBTHelper.getCompoundTag(itemstack, STORED_BLOCK);
		ItemStack storedStack = ItemStack.loadItemStackFromNBT(storedBlock);
		Block block = Block.getBlockFromItem(storedStack.getItem());
		return block;

	}
}

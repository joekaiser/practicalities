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
	public boolean onItemUse(ItemStack itemstack, EntityPlayer player, World world, BlockPos pos, EnumFacing side,
			float hitX, float hitY, float hitZ) {
		
		if(world.isRemote){
			return false;
		}

		boolean hasBlock = NBTHelper.getBoolean(itemstack, HAS_BLOCK, false);
		boolean hasTE = NBTHelper.getBoolean(itemstack, HAS_TILEENTITY, false);
		

		if (hasBlock) {

			BlockPos targetPos = pos.add(side.getDirectionVec());
			Block targetBlock = world.getBlockState(targetPos).getBlock();
			

			if ((world.isAirBlock(targetPos)) || targetBlock.isReplaceable(world, targetPos)) {
				NBTTagCompound tileEntityNbtData = NBTHelper.getCompoundTag(itemstack, STORED_TILEENTITY);
				Block storedBlock = getBlockFromNbt(itemstack);
				int metadata = NBTHelper.getInt(itemstack, STORED_METADATA, 0);

				if (storedBlock.canPlaceBlockAt(world, targetPos)) {
					
					world.setBlockState(targetPos, storedBlock.getStateFromMeta(metadata)); 
					if (hasTE) {
						world.setTileEntity(targetPos, TileEntity.createAndLoadEntity(tileEntityNbtData));
					}
					NBTHelper.setBoolean(itemstack, HAS_BLOCK, false);
					NBTHelper.setBoolean(itemstack, HAS_TILEENTITY, false);

					if (storedBlock.getLocalizedName().toLowerCase().contains("spawner")) {
						itemstack.damageItem(maxDamage / 4, player);
					} else {
						itemstack.damageItem(1, player);
					}
					world.notifyBlockOfStateChange(targetPos, storedBlock);
					world.playSoundAtEntity(player, "random.pop", 0.4F, 0.7F);
				}
			}
		} else if (!world.isAirBlock(pos)) { 
			IBlockState targetBlockState = world.getBlockState(pos);
			Block targetBlock = targetBlockState.getBlock(); 
			int metadata = targetBlock.getMetaFromState(targetBlockState);
			

			if (targetBlock.blockParticleGravity == -1.0F
					|| targetBlock.getBlockHardness(world, pos) < 0) {
				return false;
			}
			if (world.getTileEntity(pos) != null) {
				NBTTagCompound savedTE = new NBTTagCompound();
				TileEntity te = world.getTileEntity(pos);
				te.writeToNBT(savedTE);

				NBTHelper.setBoolean(itemstack, HAS_TILEENTITY, true);
				NBTHelper.setTag(itemstack, STORED_TILEENTITY, savedTE);
				
			}

			NBTTagCompound blockNbt = new NBTTagCompound();
			ItemStack i = new ItemStack(targetBlock);
			
			i.writeToNBT(blockNbt);
			NBTHelper.setTag(itemstack, STORED_BLOCK, blockNbt);
			NBTHelper.setInt(itemstack, STORED_METADATA, metadata);

			// some blocks (like redstone) crash to client
			// so before we do the deed, make sure we can recover later
			try {
				getBlockFromNbt(itemstack);
			} catch (Exception e) {
				return false;
			}

			world.removeTileEntity(pos);
			world.setBlockToAir(pos);

			world.playSoundAtEntity(player, "random.pop", 1.0F, 1.0F);
			NBTHelper.setBoolean(itemstack, HAS_BLOCK, true);

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

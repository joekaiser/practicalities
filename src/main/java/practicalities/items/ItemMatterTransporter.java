package practicalities.items;

import java.util.List;

import cofh.lib.util.helpers.NBTHelper;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import practicalities.PracticalitiesMod;

public class ItemMatterTransporter extends Item {

	private static final String HAS_BLOCK="hasblock";
	private static final String HAS_TILEENTITY="hasentity";
	private static final String STORED_BLOCK="block";
	private static final String STORED_TILEENTITY="tileentity";
	private static final String STORED_META="blockdata";
	private int maxDamage = 50;
	
	public ItemMatterTransporter(){
		super();
		String name="matterTransporter";
		GameRegistry.registerItem(this, name);
		setUnlocalizedName(name);
		setCreativeTab(PracticalitiesMod.tab);
		setTextureName(PracticalitiesMod.TEXTURE_BASE+name);
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
		if (itemstack.stackTagCompound == null) {
			return;
		}
		
		if(NBTHelper.getBoolean(itemstack, HAS_BLOCK, false)){
			Block block = getBlockFromNbt(itemstack);
			String name = new ItemStack(block).getDisplayName();
			list.add(name);
		}
	}
	
	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer player, World world, int x, int y,
			int z, int side, float hitX, float hitY, float hitZ) {

		
		boolean hasBlock = NBTHelper.getBoolean(itemstack, HAS_BLOCK, false);
		boolean hasTE = NBTHelper.getBoolean(itemstack, HAS_TILEENTITY, false);
		
		
		if (hasBlock) {
			if (side == 0) {
				y--;
			}
			if (side == 1) {
				y++;
			}
			if (side == 2) {
				z--;
			}
			if (side == 3) {
				z++;
			}
			if (side == 4) {
				x--;
			}
			if (side == 5) {
				x++;
			}

			Block targetBlock = world.getBlock(x, y, z);
			if ((world.isAirBlock(x, y, z)) || targetBlock.isReplaceable(world, x, y, z)) {
				int metadata = NBTHelper.getInt(itemstack, STORED_META, 0);
				NBTTagCompound tileEntityNbtData = NBTHelper.getCompoundTag(itemstack, STORED_TILEENTITY);
				Block storedBlock = getBlockFromNbt(itemstack);

				if (storedBlock.canPlaceBlockAt(world, x, y, z)) {

					world.setBlock(x, y, z, storedBlock, metadata, 2);
					world.setBlockMetadataWithNotify(x, y, z, metadata, 2);
					if (hasTE) {
						world.setTileEntity(x, y, z,
								TileEntity.createAndLoadEntity(tileEntityNbtData));
					}
					NBTHelper.setBoolean(itemstack, HAS_BLOCK, false);
					NBTHelper.setBoolean(itemstack, HAS_TILEENTITY, false);
					
					if (storedBlock.getLocalizedName().toLowerCase().contains("spawner")) {
						itemstack.damageItem(maxDamage / 4, player);
					} else {
						itemstack.damageItem(1, player);
					}
					world.notifyBlockChange(x, y, z, storedBlock);
					world.playSoundAtEntity(player, "random.pop", 0.4F, 0.7F);
				}
			}
		} else if ((!world.isAirBlock(x, y, z)) && (!world.isRemote)) {
			Block targetBlock = world.getBlock(x, y, z);
			int metadata = world.getBlockMetadata(x, y, z);

			if (targetBlock.blockParticleGravity == -1.0F
					|| targetBlock.getBlockHardness(world, x, y, z) <0){
				return false;
			}
			if (world.getTileEntity(x, y, z) != null) {
				NBTTagCompound savedTE = new NBTTagCompound();
				TileEntity te = world.getTileEntity(x, y, z);
				te.writeToNBT(savedTE);
				
				NBTHelper.setBoolean(itemstack,HAS_TILEENTITY,true);
				NBTHelper.setTag(itemstack, STORED_TILEENTITY, savedTE);
			}

			NBTTagCompound blockNbt = new NBTTagCompound();
			ItemStack i = new ItemStack(targetBlock);
			i.writeToNBT(blockNbt);
			NBTHelper.setTag(itemstack, STORED_BLOCK, blockNbt);
			NBTHelper.setInt(itemstack, STORED_META, metadata);

			//some blocks (like redstone) crash to client
			//so before we do the deed, make sure we can recover later
			try {
				getBlockFromNbt(itemstack);
			} catch (Exception e) {
				return false;
			}

			world.removeTileEntity(x, y, z);
			world.setBlockToAir(x, y, z);

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
		//NBTTagCompound storedNBT = NBTHelper.getCompoundTag(itemstack, STORED_NBT);
		ItemStack storedStack = ItemStack.loadItemStackFromNBT(storedBlock);
		Block block = Block.getBlockFromItem(storedStack.getItem());
		return block;
		
		
	}
}

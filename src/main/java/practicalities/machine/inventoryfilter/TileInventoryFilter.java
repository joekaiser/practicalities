package practicalities.machine.inventoryfilter;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import cofh.api.tileentity.IReconfigurableFacing;
import cofh.core.block.TileCoFHBase;
import cpw.mods.fml.common.registry.GameRegistry;

public class TileInventoryFilter extends TileCoFHBase implements ISidedInventory, IReconfigurableFacing{

	ForgeDirection facing = ForgeDirection.UP;
	
	int slotStart = 0;
	int slotEnd = 65536;
	
	public void markFilthy() {
		this.markDirty();
		this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}
	
	@Override
	public Object getGuiClient(InventoryPlayer inventory) {
		return new GuiInventoryFilter(inventory, this);
	}

	@Override
	public Object getGuiServer(InventoryPlayer inventory) {
		return new ContainerInventoryFilter(inventory, this);
	}
	
	@Override
	public String getName() {
		return null;
	}

	@Override
	public int getType() {
		return 0;
	}
	
	public static void initialize() {
		GameRegistry.registerTileEntity(TileInventoryFilter.class, "p2.inventoryfilter");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		tag.setInteger("facing", facing.ordinal());
		tag.setInteger("start", slotStart);
		tag.setInteger("end", slotEnd);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		facing = ForgeDirection.getOrientation(tag.getInteger("facing"));
		slotStart = tag.getInteger("start");
		slotEnd = tag.getInteger("end");
	}
	
	
	
	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound syncData = new NBTTagCompound();
		this.writeToNBT(syncData);
		return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 1, syncData);
	}
	
	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
		readFromNBT(pkt.func_148857_g());
	}
	
	// IInventory

	private IInventory getInventoryFacing() {
		TileEntity te = this.worldObj.getTileEntity(this.xCoord+facing.offsetX, this.yCoord+facing.offsetY, this.zCoord+facing.offsetZ);
		if(te instanceof IInventory) {
			return (IInventory)te;
		}
		return null;
	}
	private boolean isFacingInventory() {
		return getInventoryFacing() != null;
	}
	
	@Override
	public int getSizeInventory() {
		if(!isFacingInventory()) return 0;
		return getInventoryFacing().getSizeInventory();
	}

	@Override
	public ItemStack getStackInSlot(int p_70301_1_) {
		if(!isFacingInventory()) return null;
		return getInventoryFacing().getStackInSlot(p_70301_1_);
	}

	@Override
	public ItemStack decrStackSize(int p_70298_1_, int p_70298_2_) {
		if(!isFacingInventory()) return null;
		return getInventoryFacing().decrStackSize(p_70298_1_, p_70298_2_);
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int p_70304_1_) {
		if(!isFacingInventory()) return null;
		return getInventoryFacing().getStackInSlotOnClosing(p_70304_1_);
	}

	@Override
	public void setInventorySlotContents(int p_70299_1_, ItemStack p_70299_2_) {
		if(!isFacingInventory()) return;
		getInventoryFacing().setInventorySlotContents(p_70299_1_, p_70299_2_);
	}

	@Override
	public String getInventoryName() {
		if(!isFacingInventory()) return "";
		return getInventoryFacing().getInventoryName();
	}

	@Override
	public boolean hasCustomInventoryName() {
		if(!isFacingInventory()) return false;
		return getInventoryFacing().hasCustomInventoryName();
	}

	@Override
	public int getInventoryStackLimit() {
		if(!isFacingInventory()) return 64;
		return getInventoryFacing().getInventoryStackLimit();
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer p_70300_1_) {
		if(!isFacingInventory()) return false;
		return getInventoryFacing().isUseableByPlayer(p_70300_1_);
	}

	@Override
	public void openInventory() {
		if(!isFacingInventory()) return;
		getInventoryFacing().openInventory();
	}

	@Override
	public void closeInventory() {
		if(!isFacingInventory()) return;
		getInventoryFacing().closeInventory();
	}

	@Override
	public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_) {
		if(!isFacingInventory()) return false;
		return getInventoryFacing().isItemValidForSlot(p_94041_1_, p_94041_2_);
	}

	// IReconfigurableFacing
	
	@Override
	public int getFacing() {
		return facing.ordinal();
	}

	@Override
	public boolean allowYAxisFacing() {
		return true;
	}

	@Override
	public boolean rotateBlock() {
		return false;
	}

	@Override
	public boolean setFacing(int dir) {
		facing = ForgeDirection.getOrientation(dir).getOpposite();
		this.markFilthy();
		worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, facing.ordinal(), 2);
		return true;
	}
	
	@Override
	public boolean canPlayerDismantle(EntityPlayer player) {
		return false;
	}

	// ISidedInventory
	
	@Override
	public int[] getAccessibleSlotsFromSide(int p_94128_1_) {
		if(!isFacingInventory()) return new int[0];
		IInventory inv = getInventoryFacing();
		
		int[] slots;
		if(inv instanceof ISidedInventory) {
			slots = ((ISidedInventory)inv).getAccessibleSlotsFromSide(facing.getOpposite().ordinal());
		} else {
			slots = new int[inv.getSizeInventory()];
			for (int i = 0; i < slots.length; i++) {
				slots[i] = i;
			}
		}
		
		List<Integer> finalSlots = new ArrayList<Integer>(slots.length);
		
		for (int i = 0; i < slots.length; i++) {
			if(slots[i] >= slotStart && slots[i] <= slotEnd) {
				finalSlots.add(i);
			}
		}
		
		int[] newSlots = new int[finalSlots.size()];
		int i = 0;
		for (int val : finalSlots) {
			newSlots[i++] = val;
		}
		
		return newSlots;
	}

	@Override
	public boolean canInsertItem(int p_102007_1_, ItemStack p_102007_2_, int p_102007_3_) {
		if(!isFacingInventory()) return false;
		if(getInventoryFacing() instanceof ISidedInventory)
			((ISidedInventory)getInventoryFacing()).canInsertItem(p_102007_1_, p_102007_2_, facing.getOpposite().ordinal());
		return true;
	}

	@Override
	public boolean canExtractItem(int p_102008_1_, ItemStack p_102008_2_, int p_102008_3_) {
		if(!isFacingInventory()) return false;
		if(getInventoryFacing() instanceof ISidedInventory)
			((ISidedInventory)getInventoryFacing()).canExtractItem(p_102008_1_, p_102008_2_, facing.getOpposite().ordinal());
		return true;
	}

}

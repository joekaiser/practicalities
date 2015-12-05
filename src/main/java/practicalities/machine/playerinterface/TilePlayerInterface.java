package practicalities.machine.playerinterface;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.UUID;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.server.MinecraftServer;
import cofh.core.block.TileCoFHBase;
import cpw.mods.fml.common.registry.GameRegistry;

public class TilePlayerInterface extends TileCoFHBase implements IInventory {

	private WeakReference<EntityPlayer> player;
	private UUID uuid;
	private String lastName;
	
	private int timeout = 0;
	
	public TilePlayerInterface() {
		super();
	}
	
	public UUID getUUID() {
		return uuid;
	}
	public String getLastName() {
		return lastName;
	}
	
	public void setPlayer(EntityPlayer player) {
		uuid = player.getUniqueID();
		updatePlayer(true);
	}
	
	public void setUUID(UUID uuid) {
		this.uuid = uuid;
		this.updatePlayer(true);
	}
	
	@SuppressWarnings("unchecked")
	public void updatePlayer(boolean force) {
		if(this.worldObj.isRemote)
			return;
		
		if(force || !hasPlayer() ) {
			List<EntityPlayer> playerList = MinecraftServer.getServer().getConfigurationManager().playerEntityList;
			
			for (EntityPlayer entityPlayer : playerList) {
				if(entityPlayer.getUniqueID().equals(uuid)) {
					player = new WeakReference<EntityPlayer>(entityPlayer);
					lastName = entityPlayer.getDisplayName();
					this.markDirty();
					worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
					break;
				}
			}
			timeout = 10; // ten ticks before it will look again, no need to try any faster and will decrease performance impact
		}
	}
	
	@Override
	public void updateEntity() {
		super.updateEntity();
		if(timeout > 0) {
			timeout--;
		} else {
			updatePlayer(false);
		}
	}
	@Override
	public boolean canUpdate() {
		return true;
	}

	@Override
	public String getName() {
		return getBlockType().getUnlocalizedName();
	}

	@Override
	public int getType() {
		return getBlockMetadata();
	}

	public static void initialize() {
		GameRegistry.registerTileEntity(TilePlayerInterface.class, "p2.playerinterface");
	}
	
	@Override
	public boolean canPlayerDismantle(EntityPlayer player) {
		return false;
	}

	@Override
	public void writeToNBT(NBTTagCompound nbtTag) {
		super.writeToNBT(nbtTag);
		writeSyncableDataToNBT(nbtTag);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbtTag) {
		super.readFromNBT(nbtTag);
		readSyncableDataFromNBT(nbtTag);
	}
	
	public void writeSyncableDataToNBT(NBTTagCompound nbtTag) {
		nbtTag.setString("playerUUID", uuid == null ? "!NULL" : uuid.toString());
		nbtTag.setString("playerName", lastName == null ? "!NULL" : lastName);
	}
	
	public void readSyncableDataFromNBT(NBTTagCompound nbtTag) {
		uuid = null;
		lastName = null;
		String uuidString = nbtTag.getString("playerUUID");
		String lastNameString = nbtTag.getString("playerName");
		if(!uuidString.equals("!NULL")) {
			uuid = UUID.fromString(uuidString);
		}
		if(!lastNameString.equals("!NULL")) {
			lastName = lastNameString;
		}
	}
	
	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound syncData = new NBTTagCompound();
		this.writeSyncableDataToNBT(syncData);
		return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 1, syncData);
	}
	
	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
		readSyncableDataFromNBT(pkt.func_148857_g());
	}
	
	@Override
	public void blockBroken() {
		player = null; // prevent drops
	}
	
	// Inventory code

	public boolean hasPlayer() {
		return player != null && player.get() != null;
	}
	
	@Override
	public int getSizeInventory() {
		if(!hasPlayer()) {
			return 0;
		}
		return player.get().inventory.getSizeInventory()-9;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		slot+=9;
		if(!hasPlayer()) {
			return null;
		}
		return player.get().inventory.getStackInSlot(slot);
	}

	@Override
	public ItemStack decrStackSize(int slot, int amt) {
		slot+=9;
		if(!hasPlayer()) {
			return null;
		}
		return player.get().inventory.decrStackSize(slot, amt);
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int p_70304_1_) {
		return null;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		slot+=9;
		if(!hasPlayer()) {
			return;
		}
		player.get().inventory.setInventorySlotContents(slot, stack);
	}

	@Override
	public int getInventoryStackLimit() {
		if(!hasPlayer()) {
			return 64;
		}
		return player.get().inventory.getInventoryStackLimit();
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer playerAccessing) {
		if(!hasPlayer()) {
			return false;
		}
		return player.get().inventory.isUseableByPlayer(playerAccessing);
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		slot+=9;
		if(!hasPlayer()) {
			return false;
		}
		return player.get().inventory.isItemValidForSlot(slot, stack);
	}

	// static inventory methods
	
	@Override
	public void openInventory() {}

	@Override
	public void closeInventory() {}

	@Override
	public String getInventoryName() {
		return "container.practicalities.playerinterface";
	}

	@Override
	public boolean hasCustomInventoryName() {
		return false;
	}
}

package practicalities.base;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import cofh.core.block.TileCoFHBase;

public abstract class TileBase extends TileCoFHBase {

	@Override
	public void writeToNBT(NBTTagCompound nbtTag) {
		super.writeToNBT(nbtTag);
		writeSyncableDataToNBT(nbtTag);
		writeServerDataToNBT(nbtTag);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbtTag) {
		super.readFromNBT(nbtTag);
		readSyncableDataFromNBT(nbtTag);
		readServerDataToNBT(nbtTag);
	}
	
	public abstract void writeSyncableDataToNBT(NBTTagCompound nbtTag);
	
	public abstract void readSyncableDataFromNBT(NBTTagCompound nbtTag);
	
	
	public abstract void writeServerDataToNBT(NBTTagCompound nbtTag);
	
	public abstract void readServerDataToNBT(NBTTagCompound nbtTag);
	
	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound syncData = new NBTTagCompound();
		writeSyncableDataToNBT(syncData);
		return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 1, syncData);
	}
	
	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
		readSyncableDataFromNBT(pkt.func_148857_g());
	}
	
	public abstract boolean canUpdate();
}

package practicalities.machine.inductioncoil;

import practicalities.machine.teslacoil.IFieldReceiver;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyProvider;
import cofh.api.energy.IEnergyReceiver;
import cofh.core.block.TileCoFHBase;
import cpw.mods.fml.common.registry.GameRegistry;

public class TileInductionCoil extends TileCoFHBase implements IEnergyProvider, IFieldReceiver {

	private static final int storageBase = 10000;

	private EnergyStorage energy;

	public TileInductionCoil() {
		super();
		energy = new EnergyStorage(storageBase, 100);
	}

	@Override
	public int getDraw()    { return 10; }
	@Override
	public int getDeposit() { return 8; }
	@Override
	public boolean isRepeater() { return false; }
	
	@Override
	public boolean canFitRF() {
		return ( energy.getMaxEnergyStored()-energy.getEnergyStored() ) > getDeposit();
	}

	@Override
	public void reciveRF() {
		energy.receiveEnergy(getDeposit(), false);
		markDirty();
		markChunkDirty();
	}
	
	public static void initialize() {
		GameRegistry.registerTileEntity(TileInductionCoil.class, "p2.inductioncoil");
	}

	@Override
	public String getName() {
		return getBlockType().getUnlocalizedName();
	}

	@Override
	public int getType() {
		return getBlockMetadata();
	}

	@Override
	public boolean canPlayerDismantle(EntityPlayer player) {
		return false;
	}

	@Override
	public boolean canConnectEnergy(ForgeDirection from) {
		if (from == ForgeDirection.UP) {
			return false;
		}
		return true;
	}

	@Override
	public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {
		return energy.extractEnergy(maxExtract, simulate);
	}

	@Override
	public int getEnergyStored(ForgeDirection from) {
		return energy.getEnergyStored();
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection from) {
		return energy.getMaxReceive();
	}

	@Override
	public void updateEntity() {
		if (worldObj.isRemote)
			return;
//		this.energy.receiveEnergy(100, false);
		pushRfOut();
	}

	private void pushRfOut() {
		for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
			int targetX = xCoord + dir.offsetX;
			int targetY = yCoord + dir.offsetY;
			int targetZ = zCoord + dir.offsetZ;

			TileEntity tile = worldObj.getTileEntity(targetX, targetY, targetZ);
			if (tile instanceof IEnergyReceiver) {
				IEnergyReceiver receiver = (IEnergyReceiver) tile;

				if (receiver.canConnectEnergy(dir.getOpposite())) {
					int tosend = extractEnergy(dir, energy.getMaxExtract(), true);
					int used = ((IEnergyReceiver) tile).receiveEnergy(dir.getOpposite(), tosend, false);
					if (used > 0) {
						this.markDirty();
					}
					extractEnergy(dir, used, false);
				}

			}

		}
	}

	@Override
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);

		NBTTagCompound energyTag = new NBTTagCompound();
		this.energy.writeToNBT(energyTag);
		tag.setTag("energy", energyTag);
	}

	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);

		NBTTagCompound energyTag = tag.getCompoundTag("energy");
		this.energy.readFromNBT(energyTag);
	}

}

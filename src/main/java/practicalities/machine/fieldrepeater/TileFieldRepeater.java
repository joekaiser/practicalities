package practicalities.machine.fieldrepeater;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import practicalities.ConfigMan;
import practicalities.base.TileSimpleInventory;
import practicalities.machine.inductioncoil.InductionCoilManager;
import practicalities.machine.teslacoil.IFieldReceiver;
import codechicken.lib.vec.BlockCoord;
import cofh.api.energy.EnergyStorage;
import cpw.mods.fml.common.registry.GameRegistry;

public class TileFieldRepeater extends TileSimpleInventory implements IFieldReceiver {

	public static int RANGE = 64;
	private int storageBase = 10000;
	private EnergyStorage energy;

	public TileFieldRepeater() {
		super(1);
		energy = new EnergyStorage(storageBase, 1000);

	}
	
	@Override
	public int getDraw() {
		// TODO Auto-generated method stub
		return 1000;
	}
	
	@Override
	public int getDeposit() {
		return 900;
	}
	
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

	@Override
	public boolean isRepeater() {
		return true;
	}
	
	public static void initialize() {
		GameRegistry.registerTileEntity(TileFieldRepeater.class, "p2.fieldrepeater");
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
	public String getInventoryName() {
		return "";
	}
	
	@Override
	public void updateEntity() {
		if (worldObj.isRemote)
			return;
		
		int draw = 10;
		
		if(energy.getEnergyStored() < draw)
			return;
		
		List<BlockCoord> coils = InductionCoilManager.getCoilsInRange(worldObj, xCoord, yCoord, zCoord, ConfigMan.teslaRange);
		for (BlockCoord coord : coils) {
			TileEntity entity = worldObj.getTileEntity(coord.x, coord.y, coord.z);
			if(entity instanceof IFieldReceiver) {
				IFieldReceiver coil = (IFieldReceiver)entity;
				if(energy.getEnergyStored() >= coil.getDraw() && coil.canFitRF() && !coil.isRepeater()) {
					coil.reciveRF();
					energy.extractEnergy(draw, false);
					markDirty();
					markChunkDirty();
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

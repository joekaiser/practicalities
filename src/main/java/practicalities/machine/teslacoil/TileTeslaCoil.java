package practicalities.machine.teslacoil;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.util.ForgeDirection;
import practicalities.gui.TileSimpleInventory;
import practicalities.machine.inductioncoil.InductionCoilManager;
import practicalities.machine.inductioncoil.TileInductionCoil;
import codechicken.lib.vec.BlockCoord;
import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyReceiver;
import cpw.mods.fml.common.registry.GameRegistry;

public class TileTeslaCoil extends TileSimpleInventory implements IEnergyReceiver {

	private int storageBase = 10000;

	private EnergyStorage energy;

	public TileTeslaCoil() {
		super(1);
		energy = new EnergyStorage(storageBase, 1000);

	}

	public static void initialize() {
		GameRegistry.registerTileEntity(TileTeslaCoil.class, "p2.teslacoil");
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
	public int receiveEnergy(ForgeDirection side, int amt, boolean simulate) {
		return energy.receiveEnergy(amt, simulate);
	}
	
	@Override
	public int getEnergyStored(ForgeDirection from) {
		return energy.getEnergyStored();
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection from) {
		return energy.getMaxEnergyStored();
	}

	@Override
	public String getInventoryName() {
		return StatCollector.translateToLocal("tile.vampiricgenerator.name");
	}

	@SuppressWarnings("unchecked")
	@Override
	public void updateEntity() {
		if (worldObj.isRemote)
			return;
		
		int draw = 10;
		int send = 8;
		
		if(energy.getEnergyStored() < draw)
			return;
		
		List<BlockCoord> coils = InductionCoilManager.getCoilsInRange(worldObj, xCoord, yCoord, zCoord, 10);
		for (BlockCoord coord : coils) {
			if(energy.getEnergyStored() < draw)
				break;
			
			TileEntity entity = worldObj.getTileEntity(coord.x, coord.y, coord.z);
			if(entity instanceof TileInductionCoil) {
				TileInductionCoil coil = (TileInductionCoil)entity;
				EnergyStorage storage = coil.getEnergyStorage();
				
				int amtCanFill = storage.receiveEnergy(send, true);
				if(amtCanFill == send) {
					storage.receiveEnergy(send, false);
					energy.extractEnergy(draw, false);
					coil.markDirty();
					coil.markChunkDirty();
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

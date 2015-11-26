package practicalities.machine.vampiricgenerator;

import java.util.List;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyProvider;
import cofh.api.energy.IEnergyReceiver;
import cofh.lib.util.TimeTracker;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.util.ForgeDirection;
import practicalities.ModUtils;
import practicalities.gui.TileSimpleInventory;

public class TileVampiricGenerator extends TileSimpleInventory implements IEnergyProvider {

	private static final int generateBase = 30;
	private static final int storageBase = 10000;
	private static final int maxTransfer = 100;
	private static final float maxLifeSuckPerOperation = 2;
	private static final float maxFuelReserve = 1000;
	private static final float fuelUsedPerTick = .1f;
	private static final int sleepTime = 100;

	private EnergyStorage energy;
	private TimeTracker timer;
	private float currentFuel;
	private List<EntityLivingBase> entities = null;

	public TileVampiricGenerator() {
		super(1);
		energy = new EnergyStorage(storageBase, maxTransfer);
		timer = new TimeTracker();

	}

	public static void initialize() {
		GameRegistry.registerTileEntity(TileVampiricGenerator.class, "p2.vampiricgenerator");
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

	private int getProductionRate() {
		return generateBase;
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

		if (timer.hasDelayPassed(worldObj, sleepTime)) {
			entities = ModUtils.getEntitiesInRange(EntityLivingBase.class, worldObj, xCoord, yCoord + 1, zCoord, .5);
		}

		if (entities != null && worldObj.getTotalWorldTime() % 6 == 0) {

			for (EntityLivingBase entity : entities) {
				if (entity.isEntityAlive()) {
					feedFromEntity(entity);
				}
			}
		}

		produceRF();
		pushRfOut();

	}

	private void feedFromEntity(EntityLivingBase entity) {

		if (entity.attackEntityFrom(DamageSource.wither, maxLifeSuckPerOperation)) {
			float lpDrained = entity.prevHealth - entity.getHealth();
			currentFuel = (int) Math.min(currentFuel + lpDrained, maxFuelReserve);
		}
	}

	private void produceRF() {
		
		if(energy.getEnergyStored() == energy.getMaxEnergyStored()){
			return;
		}
		
		if (currentFuel >= fuelUsedPerTick) {
			currentFuel -= fuelUsedPerTick;

			energy.receiveEnergy(getProductionRate(), false);
		}

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
					int tosend = energy.extractEnergy(maxTransfer, true);
					int used = ((IEnergyReceiver) tile).receiveEnergy(dir.getOpposite(), tosend, false);
					if (used > 0) {
						this.markDirty();
					}
					energy.extractEnergy(used, false);
				}

			}

		}
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tag)
	{
		super.writeToNBT(tag);
		tag.setFloat("currentFuel", (float) this.currentFuel);

		NBTTagCompound energyTag = new NBTTagCompound();
		this.energy.writeToNBT(energyTag);
		tag.setTag("energy", energyTag);
		
	}

	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
		super.readFromNBT(tag);
		
		this.currentFuel = tag.getFloat("currentFuel");
		
		NBTTagCompound energyTag = tag.getCompoundTag("energy");
		this.energy.readFromNBT(energyTag);
	}
}

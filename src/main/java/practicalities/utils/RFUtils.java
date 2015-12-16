package practicalities.utils;

import cofh.api.energy.IEnergyProvider;
import cofh.api.energy.IEnergyReceiver;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class RFUtils {

	public static boolean pushRfOut(IEnergyProvider provider, World worldObj, int xCoord, int yCoord, int zCoord, int maxTransferAmount) {
		boolean result=false;
		for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
			int targetX = xCoord + dir.offsetX;
			int targetY = yCoord + dir.offsetY;
			int targetZ = zCoord + dir.offsetZ;

			TileEntity tile = worldObj.getTileEntity(targetX, targetY, targetZ);
			if (tile instanceof IEnergyReceiver) {
				IEnergyReceiver receiver = (IEnergyReceiver) tile;

				if (receiver.canConnectEnergy(dir.getOpposite())) {
					int tosend = provider.extractEnergy(dir,maxTransferAmount, true);
					int used = ((IEnergyReceiver) tile).receiveEnergy(dir.getOpposite(), tosend, false);
					if (used > 0) {
						result=true;
					}
					provider.extractEnergy(dir, used, false);
				}
			}
		}
		
		return result;
	}
}

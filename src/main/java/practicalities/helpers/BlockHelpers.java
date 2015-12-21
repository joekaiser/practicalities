package practicalities.helpers;

import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;

public final class BlockHelpers {
	public static BlockPos getBlockPosFromXYZ(double x, double y, double z) {
		return new BlockPos(new Vec3(x, y, z));
	}
}

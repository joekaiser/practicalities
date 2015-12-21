package practicalities.helpers;

import java.util.List;

import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public final class EntityHelpers {
	public static List getEntitiesInRange(Class entityType, World world, double x, double y, double z, double radius) {
		return getEntitesInTange(entityType, world, x - radius, y - radius, z - radius, x + radius, y + radius,
				z + radius);
	}

	public static List getEntitesInTange(Class entityType, World world, double x, double y, double z, double x2,
			double y2, double z2) {
		return world.getEntitiesWithinAABB(entityType, AxisAlignedBB.fromBounds(x, y, z, x2, y2, z2));
	}
}

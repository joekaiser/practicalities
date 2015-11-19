package practicalities;

import java.util.List;

import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class ModUtils {
	@SuppressWarnings({ "rawtypes" })
	public static List getEntitiesInRange(Class entityType, World world, double x, double y, double z, double distance)
	{
		return world.getEntitiesWithinAABB(entityType,
				AxisAlignedBB.getBoundingBox(x - distance, y - distance, z - distance, x + distance, y + distance, z + distance));
	}
}

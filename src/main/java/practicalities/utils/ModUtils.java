package practicalities.utils;

import java.util.List;

import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class ModUtils {
	@SuppressWarnings({ "rawtypes" })
	public static List getEntitiesInRange(Class entityType, World world, double x, double y, double z, double distance)
	{
		return getEntitiesInRange(entityType, world, x, y, z, distance, distance, distance);
	}
	
	@SuppressWarnings("rawtypes")
	public static List getEntitiesInRange(Class entityType, World world, double x, double y, double z, double x2,double y2, double z2)
	{
		return world.getEntitiesWithinAABB(entityType,
				AxisAlignedBB.getBoundingBox(x - x2, y - y2, z - z2, x + x2, y + y2, z + z2));
	}
	
	public static void addFlairToList(List<String> list, String flairName) {
		String flair = "flair." + flairName;
		if( StatCollector.canTranslate(flair) ) {
			list.add( StatCollector.translateToLocal(flair) );
		} else {
			int i = 0;
			while(StatCollector.canTranslate(flair+"."+i)) {
				list.add( StatCollector.translateToLocal(flair+"."+i));
				i++;
			}
		}
	}
}

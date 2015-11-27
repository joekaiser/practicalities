package practicalities.machine.polaritynegator;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import practicalities.PracticalitesWorldData;
import codechicken.lib.vec.BlockCoord;

public class PolarityNegatorManager {
	public static double RANGE = 2; // distance from the sides of the block
	
	public static void addNegator(World w, int x, int y, int z) {
		PracticalitesWorldData data = PracticalitesWorldData.get(w);
		BlockCoord coord = new BlockCoord(x,y,z);
		if(!data.magnetStoppers.contains( coord )) {
			data.magnetStoppers.add(coord);
			data.markDirty();
		}
	}
	
	public static void removeNegator(World w, int x, int y, int z) {
		PracticalitesWorldData data = PracticalitesWorldData.get(w);
		BlockCoord coord = new BlockCoord(x,y,z);
		if(data.magnetStoppers.contains( coord )) {
			data.magnetStoppers.remove(coord);
			data.markDirty();
		}
	}
	private static PracticalitesWorldData cache = null;
	
	public static void cacheWorldData(World w) {
		cache = PracticalitesWorldData.get(w);
	}
	public static void cleareWorldDataCache() {
		cache = null;
	}
	
	/**
	 * Tests if entity is within {@link RANGE} of a polarity negator in the cached world (cubic distance)
	 * @param e Entity to test
	 * @return
	 */
	public static boolean isEntityCloseToNegator(Entity e) {
		for (BlockCoord coord : cache.magnetStoppers) {
			if( Math.abs( e.posX - ( coord.x + 0.5 ) ) < RANGE+0.5 &&
			    Math.abs( e.posY - ( coord.y + 0.5 ) ) < RANGE+0.5 &&
			    Math.abs( e.posZ - ( coord.z + 0.5 ) ) < RANGE+0.5
			  ) {
				return true;
			}
		}
		return false;
	}
}

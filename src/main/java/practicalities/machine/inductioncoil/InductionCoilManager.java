package practicalities.machine.inductioncoil;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.world.World;
import practicalities.Logger;
import practicalities.PracticalitesWorldData;
import codechicken.lib.vec.BlockCoord;
import codechicken.lib.vec.Vector3;

public class InductionCoilManager {	
	public static void addCoil(World w, int x, int y, int z) {
		PracticalitesWorldData data = PracticalitesWorldData.get(w);
		BlockCoord coord = new BlockCoord(x,y,z);
		if(!data.inductionCoils.contains( coord )) {
			Logger.info("Adding coil @ (%d, %d, %d)", x, y, z);
			data.inductionCoils.add(coord);
			data.markDirty();
		}
	}
	
	public static void removeCoil(World w, int x, int y, int z) {
		PracticalitesWorldData data = PracticalitesWorldData.get(w);
		BlockCoord coord = new BlockCoord(x,y,z);
		if(data.inductionCoils.contains( coord )) {
			Logger.info("Removing coil @ (%d, %d, %d)", x, y, z);
			data.inductionCoils.remove(coord);
			data.markDirty();
		}
	}
	
	public static List<BlockCoord> getCoilsInRange(World w, int x, int y, int z, int distance) {
		PracticalitesWorldData data = PracticalitesWorldData.get(w);
		List<BlockCoord> coords = new ArrayList<BlockCoord>();
		
		for (BlockCoord coord : data.inductionCoils) {
			if( coord.x <= x+distance && coord.x > x-distance &&
				coord.y <= y+distance && coord.y > y-distance &&
				coord.z <= z+distance && coord.z > z-distance ) {
				coords.add(coord);
			}
		}
		
		return coords;
	}
}

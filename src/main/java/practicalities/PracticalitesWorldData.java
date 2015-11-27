package practicalities;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import codechicken.lib.vec.BlockCoord;

public class PracticalitesWorldData extends WorldSavedData {

	public static final String IDENTIFIER = "practicalities";
	
	public List<BlockCoord> magnetStoppers = new ArrayList<BlockCoord>();
	
	public PracticalitesWorldData() {
		super(IDENTIFIER);
	}
	
	public PracticalitesWorldData(String name) {
		super(name);
	}

	@Override
	public void readFromNBT(NBTTagCompound tag) {
		
		// v polarity negators
		int[] Xs = tag.getIntArray("magnetStoppers_Xs");
		int[] Ys = tag.getIntArray("magnetStoppers_Ys");
		int[] Zs = tag.getIntArray("magnetStoppers_Zs");
		int length = Math.min(Math.min(Xs.length, Ys.length),Zs.length);
		
		magnetStoppers = new ArrayList<BlockCoord>();
		for(int i = 0; i < length; i++) {
			magnetStoppers.add(new BlockCoord(Xs[i], Ys[i], Zs[i]));
		}
		// ^ polarity negators

	}

	@Override
	public void writeToNBT(NBTTagCompound tag) {
		
		// v polarity negators
		int[] Xs = new int[magnetStoppers.size()];
		int[] Ys = new int[magnetStoppers.size()];
		int[] Zs = new int[magnetStoppers.size()];
		
		for (int i = 0; i < magnetStoppers.size(); i++) {
			Xs[i] = magnetStoppers.get(i).x;
			Ys[i] = magnetStoppers.get(i).y;
			Zs[i] = magnetStoppers.get(i).z;
		}
		
		tag.setIntArray("magnetStoppers_Xs", Xs);
		tag.setIntArray("magnetStoppers_Ys", Ys);
		tag.setIntArray("magnetStoppers_Zs", Zs);
		// ^ polarity negators
		
	}
	
	public static PracticalitesWorldData get(World world) {
		PracticalitesWorldData data = (PracticalitesWorldData)world.loadItemData(PracticalitesWorldData.class, IDENTIFIER);
		if (data == null) {
			data = new PracticalitesWorldData();
			world.setItemData(IDENTIFIER, data);
		}
		return data;
	}

}

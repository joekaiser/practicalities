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
	public List<BlockCoord> inductionCoils = new ArrayList<BlockCoord>();
	
	public PracticalitesWorldData() {
		super(IDENTIFIER);
	}
	
	public PracticalitesWorldData(String name) {
		super(name);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tag) {
		magnetStoppers = readCoordArray(tag, "magnetStoppers");
		inductionCoils = readCoordArray(tag, "inductionCoils");
	}

	@Override
	public void writeToNBT(NBTTagCompound tag) {
		writeCoordArray(magnetStoppers, tag, "magnetStoppers");
		writeCoordArray(inductionCoils, tag, "inductionCoils");
	}
	
	public void writeCoordArray(List<BlockCoord> list, NBTTagCompound tag, String name) {
		int[] Xs = new int[list.size()];
		int[] Ys = new int[list.size()];
		int[] Zs = new int[list.size()];
		
		for (int i = 0; i < list.size(); i++) {
			Xs[i] = list.get(i).x;
			Ys[i] = list.get(i).y;
			Zs[i] = list.get(i).z;
		}
		NBTTagCompound newTag = new NBTTagCompound();
		newTag.setIntArray("Xs", Xs);
		newTag.setIntArray("Ys", Ys);
		newTag.setIntArray("Zs", Zs);
		tag.setTag(name, newTag);
	}
	
	public List<BlockCoord> readCoordArray(NBTTagCompound tag, String name) {
		NBTTagCompound dataTag = tag.getCompoundTag(name);
		int[] Xs = dataTag.getIntArray("Xs");
		int[] Ys = dataTag.getIntArray("Ys");
		int[] Zs = dataTag.getIntArray("Zs");
		int length = Math.min(Math.min(Xs.length, Ys.length),Zs.length);
		
		List<BlockCoord> coords = new ArrayList<BlockCoord>();
		for(int i = 0; i < length; i++) {
			coords.add(new BlockCoord(Xs[i], Ys[i], Zs[i]));
		}
		return coords;
	}
	
	///////////////////////////////////////////////////////////////////////////
	
	public static PracticalitesWorldData get(World world) {
		PracticalitesWorldData data = (PracticalitesWorldData)world.loadItemData(PracticalitesWorldData.class, IDENTIFIER);
		if (data == null) {
			data = new PracticalitesWorldData();
			world.setItemData(IDENTIFIER, data);
		}
		return data;
	}

}

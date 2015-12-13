package practicalities.blocks.multiblock;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import practicalities.register.ModBlocks;

// TODO: Rotations... Ugh. :P

public class Multiblock {
	
	public static BlockMatch[] placeholders;
	public static void init() {
		placeholders = new BlockMatch[] {
			new BlockMatch(Blocks.air),
			new BlockMatch(ModBlocks.multiblockPart, 0),
			new BlockMatch(ModBlocks.multiblockPart, 16)
		};
	}
	
	public BlockMatch[][][] before;
	public int[][][] after; // -1 - don't touch, 0 - air, 1 - solid
	public boolean rotate = false;
	public int centerX, centerY, centerZ;
	
	public Multiblock setCenter(int x, int y, int z) {
		centerX = x; centerY = y; centerZ = z;
		return this;
	}
	
	public Multiblock(String[][] before, Object[] mapping, String[][] after) {
		
		Map<Character, BlockMatch> map = new HashMap<Character, BlockMatch>();
		Character c = null;
		BlockMatch match = null;
		
		for (int i = 0; i < mapping.length; i++) {
			if(mapping[i] instanceof Character) {
				if(c != null && match != null) {
					map.put(c, match);
				}
				c = (Character)mapping[i];
				match = null;
			}
			if(mapping[i] instanceof Block) {
				if(match == null)
					match = new BlockMatch();
				match.block = (Block)mapping[i];
			}
			if(mapping[i] instanceof Integer && match != null) {
				match.meta = (Integer)mapping[i];
			}
		}
		if(c != null && match != null) {
			map.put(c, match);
		}
		
		char[][][] bChr = make3dCharArray(before);
		this.before = new BlockMatch[bChr.length][bChr[0].length][bChr[0][0].length];

		for (int x = 0; x < bChr.length; x++) {
			for (int y = 0; y < bChr[x].length; y++) {
				for (int z = 0; z < bChr[x][y].length; z++) {
					if(bChr[x][y][z] == ' ') {
						this.before[x][y][z] = null;
					} else {
						this.before[x][y][z] = map.get(bChr[x][y][z]);
					}
				}
			}
		}
		
		char[][][] aChr = make3dCharArray(after);
		this.after = new int[aChr.length][aChr[0].length][aChr[0][0].length];
		for (int x = 0; x < aChr.length; x++) {
			for (int y = 0; y < aChr[x].length; y++) {
				for (int z = 0; z < aChr[x][y].length; z++) {
					int index = 0;
					switch(aChr[x][y][z]) {
					case ' ':
						index = -1; break;
					case '-':
						index = 0; break;
					case '#':
						index = 1; break;
					case 'o':
						index = 2; break;
					}
					this.after[x][y][z] = index;
				}
			}
		}
	}
	
	private char[][][] make3dCharArray(String[][] strs) {
		int xSize=0, ySize=strs.length, zSize=0;
		
		for (int idx = 0; idx < strs.length; idx++) {
			String[] layer = (String[])strs[idx];
			
			for (int i = 0; i < layer.length; i++) {
				xSize = Math.max(xSize, layer[i].length());
			}
			zSize = Math.max(zSize, layer.length);
			idx++;
		}
		
		char[][][] arr = new char[xSize][ySize][zSize];
		
		for (int y = 0; y < ySize; y++) {
			String[] layer = strs[y];
			for(int z = 0; z < layer.length; z++) {
				String row = layer[z];
				for (int x = 0; x < row.length(); x++) {
					arr[x][y][z] = row.charAt(x);
				}
			}
		}
		
		return arr;
	}
	
	public boolean checkBefore(World w, int checkX, int checkY, int checkZ, int rotation) {
		int x_ = checkX-centerX, y_ = checkY-centerY, z_ = checkZ-centerZ;
		for (int x = 0; x < before.length; x++) {
		for (int y = 0; y < before[x].length; y++) {
		for (int z = 0; z < before[x][y].length; z++) {
			BlockMatch match = before[x][y][z];
			if(match != null) {
				if( w.getBlock(x+x_, y+y_, z+z_) != match.block ||
					w.getBlockMetadata(x+x_, y+y_, z+z_) != match.meta) {
					return false;
				}
			}
		}
		}
		}
		return true;
	}
	
	public boolean checkAfter(World w, int checkX, int checkY, int checkZ, int rotation) {
		int x_ = checkX-centerX, y_ = checkY-centerY, z_ = checkZ-centerZ;
		for (int x = 0; x < after.length; x++) {
		for (int y = 0; y < after[x].length; y++) {
		for (int z = 0; z < after[x][y].length; z++) {
			int match = after[x][y][z];
			if(match >= 0) {
				if( w.getBlock(x+x_, y+y_, z+z_) != placeholders[match].block ||
					w.getBlockMetadata(x+x_, y+y_, z+z_) != placeholders[match].meta) {
					return false;
				}
			}
		}
		}
		}
		return true;
	}
	
	public void spawn(World w, int spawnX, int spawnY, int spawnZ, int rotation) {
		spawn(w, spawnX, spawnY, spawnZ, rotation, false);
	}
	public void spawn(World w, int spawnX, int spawnY, int spawnZ, int rotation, boolean update) {
		if(!checkBefore(w, spawnX, spawnY, spawnZ, rotation))
			return;
		
		int x_ = spawnX-centerX, y_ = spawnY-centerY, z_ = spawnZ-centerZ;
		for (int x = 0; x < after.length; x++) {
		for (int y = 0; y < after[x].length; y++) {
		for (int z = 0; z < after[x][y].length; z++) {
			int match = after[x][y][z];
			if(match >= 0) {				
				w.setBlock(x+x_, y+y_, z+z_, placeholders[match].block, placeholders[match].meta, update ? 3 : 2);
			}
		}
		}
		}
	}
	
	public void despawn(World w, int spawnX, int spawnY, int spawnZ, int rotation) {
		despawn(w, spawnX, spawnY, spawnZ, rotation, true);
	}
	public void despawn(World w, int spawnX, int spawnY, int spawnZ, int rotation, boolean update) {
		if(!checkAfter(w, spawnX, spawnY, spawnZ, rotation))
			return;
		
		int x_ = spawnX-centerX, y_ = spawnY-centerY, z_ = spawnZ-centerZ;
		for (int x = 0; x < before.length; x++) {
		for (int y = 0; y < before[x].length; y++) {
		for (int z = 0; z < before[x][y].length; z++) {
			BlockMatch match = before[x][y][z];
			if(match != null) {
				w.setBlock(x+x_, y+y_, z+z_, match.block, match.meta, update ? 3 : 2);
			}
		}
		}
		}
	}

	
	public static class BlockMatch {
		public Block block;
		public int meta = 0;
		
		public BlockMatch() {}
		
		public BlockMatch(Block block) {
			this.block = block;
		}
		
		public BlockMatch(Block block, int meta) {
			this.block = block;
			this.meta = meta;
		}
	}
	
}

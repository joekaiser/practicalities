package practicalities.registers;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import practicalities.Logger;
import practicalities.blocks.BlockBase;
import practicalities.machines.shippingcrate.BlockShippingCrate;
import practicalities.machines.shippingcrate.TileShippingCrate;

public final class BlockRegister {
	
	public static BlockBase shippingcrate;
	
	private static void initTileEntities(){
		Logger.info("Registering TileEntities");
		TileShippingCrate.initialize();
	}
	
	public static void init(){
		shippingcrate = new BlockShippingCrate();
		
		initTileEntities();
	}
	
	@SideOnly(Side.CLIENT)
	public static void registerRenders(){
		Logger.info("Registering Blocks");
		
		shippingcrate.initModel();
	}

}

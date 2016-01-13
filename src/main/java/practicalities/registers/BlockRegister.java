package practicalities.registers;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import practicalities.Logger;
import practicalities.blocks.BlockBase;
import practicalities.machines.shippingcrate.BlockShippingCrate;

public final class BlockRegister {
	
	public static BlockBase shippingcrate;
	
	public static void init(){
		shippingcrate = new BlockShippingCrate();
	}
	
	@SideOnly(Side.CLIENT)
	public static void registerRenders(){
		Logger.info("Registering Blocks");
		
		shippingcrate.initModel();
	}

}

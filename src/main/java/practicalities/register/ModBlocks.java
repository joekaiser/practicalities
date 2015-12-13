package practicalities.register;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import practicalities.ConfigMan;
import practicalities.blocks.BlockDecor;
import practicalities.blocks.multiblock.BlockMultiblockPart;
import practicalities.machine.playerinterface.BlockPlayerInterface;
import practicalities.machine.fieldrepeater.BlockFieldRepeater;
import practicalities.machine.inductioncoil.BlockInductionCoil;
import practicalities.machine.inventoryfilter.BlockInventoryFilter;
import practicalities.machine.polaritynegator.BlockPolarityNegator;
import practicalities.machine.shippingcrate.BlockShippingCrate;
import practicalities.machine.teslacoil.BlockTeslaCoil;
import practicalities.machine.vampiricgenerator.BlockVampiricGenerator;

public class ModBlocks {
	public static Block shippingCrate;
	public static Block stoneWall;
	public static Block vampiricGenerator;
	public static Block polarityNegator;
	public static Block playerInterface;
	public static Block inventoryFilter;
	public static Block teslaCoil;
	public static Block inductionCoil;
	public static Block fieldRepeater;
	
	public static Block multiblockPart;
	
	public static void init() {
		
		stoneWall = new BlockDecor(Material.rock, "stonewall", 1, Block.soundTypeStone);
		
		if(ConfigMan.enablePlayerInterface)
			playerInterface = new BlockPlayerInterface();
		
		if(ConfigMan.enableTeslaCoil) {
			teslaCoil = new BlockTeslaCoil();
			inductionCoil = new BlockInductionCoil();
			fieldRepeater = new BlockFieldRepeater();
		}
		
		if(ConfigMan.enableInventoryFilter)
			inventoryFilter = new BlockInventoryFilter();
		
		if(ConfigMan.enableShippingCrate)
			shippingCrate = new BlockShippingCrate();
		
		if(ConfigMan.enableVampiricGenerator)
			vampiricGenerator = new BlockVampiricGenerator();
		
		if(ConfigMan.enableMagnet)
			polarityNegator = new BlockPolarityNegator();
		
		multiblockPart = new BlockMultiblockPart();
	}
}

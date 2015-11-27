package practicalities;

import cofh.lib.util.helpers.ItemHelper;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import practicalities.blocks.ModBlocks;
import practicalities.items.ModItems;

public class CraftingRecipes {

	// vanilla - resources
	ItemStack lapis = new ItemStack(Items.dye, 1, 4);
	ItemStack redstone = new ItemStack(Items.redstone);
	ItemStack ironIngot = new ItemStack(Items.iron_ingot);
	ItemStack enderPearl = new ItemStack(Items.ender_pearl);
	ItemStack diamond = new ItemStack(Items.diamond);
	String goldNugget="nuggetGold";

	// vanilla - blocks
	ItemStack cobblestone = new ItemStack(Blocks.cobblestone);
	String woodSlab = "slabWood";
	String log = "logWood";

	// vanilla - other
	ItemStack chest = new ItemStack(Blocks.chest);
	ItemStack web = new ItemStack(Blocks.web);
	String stick = "stickWood";
	ItemStack netherStar = new ItemStack(Items.nether_star);

	// vanilla - mob drops
	ItemStack string = new ItemStack(Items.string);
	ItemStack blazeRod = new ItemStack(Items.blaze_rod);
	
	// mod items
	ItemStack machineCore = new ItemStack(ModItems.machineCore);
	ItemStack voidBucket = new ItemStack(ModItems.voidBucket);
	ItemStack diamondRod = new ItemStack(ModItems.diamondRod);
	ItemStack magnet = new ItemStack(ModItems.magnet);
	ItemStack imbuedCore = new ItemStack(ModItems.imbuedCore);

	public void init() {
		shapedRecipes();
	}

	private void shapedRecipes() {
		ItemHelper.addShapedOreRecipe(ModItems.machineCore,
				"nin",
				" e ",
				"nin",    'n', goldNugget, 'i', ironIngot, 'e', enderPearl);
		ItemHelper.addShapedRecipe(ModItems.voidBucket, 
				"iei",
				" i ",    'i', ironIngot, 'e', enderPearl);
		ItemHelper.addShapedOreRecipe(ModItems.matterTransporter,
				"e e",
				" d ",
				" s ",    'e', enderPearl, 'd', diamond, 's', stick);
		ItemHelper.addShapedOreRecipe(ModBlocks.shippingCrate,
				"ili",
				"lcl",
				"ili",    'i', ironIngot, 'l', log, 'c', chest);
		ItemHelper.addShapedOreRecipe(ModItems.magnet, 
				"l r", 
				"i i",
				" m ",    'l', lapis, 'r', redstone, 'i', ironIngot, 'm', machineCore);
		ItemHelper.addShapedOreRecipe(new ItemStack(ModBlocks.stoneWall,3),
				"ppp",
				"ccc",    'p', woodSlab,'c',cobblestone);
		ItemHelper.addShapedOreRecipe(ModItems.filterCard, 
				"www",
				" r ",
				"sss",    's', stick, 'w', web, 'r', redstone);
		ItemHelper.addShapedOreRecipe(Blocks.web,
				"s s",
				" s ",
				"s s",    's', string);
		ItemHelper.addShapedOreRecipe(new ItemStack(ModItems.sitisStick),
				"  d",
				" c ",
				"r  ",    'd', diamond, 'c', machineCore, 'r', diamondRod);

		ItemHelper.addShapedOreRecipe(ModItems.diamondRod, 
				"d",
				"b",
				"d",	'd',diamond,'b',blazeRod);
		ItemHelper.addShapedOreRecipe(ModBlocks.polarityNegator, 
				"imi",
				"ici",
				"ivi",	'i',ironIngot,'m',magnet,'c',machineCore,'v',voidBucket);
		
		ItemHelper.addShapedOreRecipe(ModItems.imbuedCore,
				"nnn",
				"ndn",
				"nnn", 	'd', diamond, 'n', netherStar);

	}
}

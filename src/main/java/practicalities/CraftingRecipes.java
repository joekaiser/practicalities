package practicalities;

import cofh.lib.util.helpers.ItemHelper;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
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
	ItemStack skull = new ItemStack(Items.skull,1,2);
	ItemStack ironBars = new ItemStack(Blocks.iron_bars);
	ItemStack hopper = new ItemStack(Blocks.hopper);
	ItemStack redstoneTorch = new ItemStack(Blocks.redstone_torch);
	
	// vanilla - other
	ItemStack chest = new ItemStack(Blocks.chest);
	ItemStack web = new ItemStack(Blocks.web);
	String stick = "stickWood";
	ItemStack netherStar = new ItemStack(Items.nether_star);
	ItemStack rottenFlesh = new ItemStack(Items.rotten_flesh);
	ItemStack spiderEye = new ItemStack(Items.spider_eye);
	

	// vanilla - mob drops
	ItemStack string = new ItemStack(Items.string);
	ItemStack blazeRod = new ItemStack(Items.blaze_rod);
	
	// mod items
	ItemStack machineCore = new ItemStack(ModItems.machineCore);
	ItemStack diamondRod = new ItemStack(ModItems.diamondRod);
	ItemStack magnet = new ItemStack(ModItems.magnet);
	ItemStack imbuedCore = new ItemStack(ModItems.imbuedCore);
	ItemStack imbuedRod = new ItemStack(ModItems.imbuedRod);
	ItemStack machinePlate = new ItemStack(ModItems.machinePlate);
	ItemStack filterCard = new ItemStack(ModItems.filterCard);

	public void init() {
		shapedRecipes();
	}

	private void shapedRecipes() {
		
		addShapedOreRecipe(true, ModItems.machineCore,
				"nin",
				" e ",
				"nin",    'n', goldNugget, 'i', ironIngot, 'e', enderPearl);
		

		addShapedOreRecipe(true, ModItems.voidBucket, 
				"iei",
				" i ",     'i', ironIngot, 'e', enderPearl);
		
		addShapedOreRecipe(ConfigMan.enableMatterTransporter, ModItems.matterTransporter,
				"e e",
				" d ",
				" s ",    'e', enderPearl, 'd', diamond, 's', stick);
		
		addShapedOreRecipe(ConfigMan.enableShippingCrate,ModBlocks.shippingCrate,
				"ili",
				"lcl",
				"ili",    'i', ironIngot, 'l', log, 'c', chest);
		
		addShapedOreRecipe(ConfigMan.enableMagnet,ModItems.magnet, 
				"l r", 
				"i i",
				" m ",    'l', lapis, 'r', redstone, 'i', ironIngot, 'm', machineCore);
		addShapedOreRecipe(true,new ItemStack(ModBlocks.stoneWall,3),
				"ppp",
				"ccc",    'p', woodSlab,'c',cobblestone);
		addShapedOreRecipe(true,ModItems.filterCard, 
				"www",
				" r ",
				"sss",    's', stick, 'w', web, 'r', redstone);
		addShapedOreRecipe(true,Blocks.web,
				"s s",
				" s ",
				"s s",    's', string);
		addShapedOreRecipe(ConfigMan.enableSitisStick,new ItemStack(ModItems.sitisStick),
				"  d",
				" c ",
				"r  ",    'd', diamond, 'c', machineCore, 'r', diamondRod);

		addShapedOreRecipe(true,ModItems.diamondRod, 
				"d",
				"b",
				"d",		'd',diamond,'b',blazeRod);
		addShapedOreRecipe(ConfigMan.enableMagnet, ModBlocks.polarityNegator, 
				"pmp",
				" c ",
				"prp",		'm',magnet,'c',machineCore,'p',machinePlate, 'r',redstone);
		
		addShapedOreRecipe(true,ModItems.imbuedCore,
				"nnn",
				"ndn",
				"nnn", 		'd', diamond, 'n', netherStar);
		
		addShapedOreRecipe(true,new ItemStack(ModItems.machinePlate,4),
				"i i",
				" c ",
				"i i", 		'i', ironIngot, 'c', machineCore);
		addShapedOreRecipe(ConfigMan.enableVampiricGenerator,ModBlocks.vampiricGenerator,
				"ese",
				"zcz",
				"ppp", 		'p', machinePlate,'c',machineCore,'z',rottenFlesh,'s',skull,'e',spiderEye);
		
		addShapedOreRecipe(ConfigMan.enableInventoryFilter,ModBlocks.inventoryFilter,
				" i ",
				"rhr",
				"pmp", 		'i', ironBars,'r',redstoneTorch, 'h',hopper,'p',machinePlate,'m',machineCore);
		
		addShapedOreRecipe(true,ModItems.imbuedRod, 
				"i",
				"i",
				"i", 		'i',imbuedCore);
		
		addShapedOreRecipe(ConfigMan.enableImbuedItems, ModItems.imbuedSword, 
				"c",
				"c",
				"r",		'c',imbuedCore,'r',imbuedRod);
		
		addShapedOreRecipe(ConfigMan.enableImbuedItems, ModItems.imbuedTool, 
				"ccc",
				"crc",
				" r ",		'c',imbuedCore,'r',imbuedRod);
		
		addShapedOreRecipe(ConfigMan.enablePlayerInterface, ModBlocks.playerInterface, 
				"pcp",
				"cnc",
				"pcp",		'p',machinePlate,'c',machineCore,'n',netherStar);
		

	}
	
	private void addShapedOreRecipe(boolean condition, Item out, Object... recipe){
		if(condition){
			ItemHelper.addShapedOreRecipe(out, recipe);
		}
	}
	
	private void addShapedOreRecipe(boolean condition, Block out, Object... recipe){
		if(condition){
			ItemHelper.addShapedOreRecipe(out, recipe);
		}
	}
	
	private void addShapedOreRecipe(boolean condition, ItemStack out, Object... recipe){
		if(condition){
			ItemHelper.addShapedOreRecipe(out, recipe);
		}
	}
}

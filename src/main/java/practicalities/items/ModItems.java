package practicalities.items;

import net.minecraft.item.Item;
import practicalities.ConfigMan;
import practicalities.items.filtercard.ItemFilterCard;

public class ModItems {

	// tools
	public static Item voidBucket;
	public static Item matterTransporter;
	public static Item magnet;
	public static Item sitisStick;
	
	// crafting components
	public static Item diamondRod;
	public static Item machineCore;
	public static Item imbuedCore;
	public static Item machinePlate;

	// other
	public static Item filterCard;
	
	public static void init(){
		// tools
		if(ConfigMan.enableVoidBucket)
			voidBucket = new ItemVoidBucket();
		
		if(ConfigMan.enableMatterTransporter)
			matterTransporter = new ItemMatterTransporter();
		
		if(ConfigMan.enableMagnet)
			magnet = new ItemMagnet();
		
		if(ConfigMan.enableSitisStick)
			sitisStick = new ItemSitisStick();
				
		// crafting components
		machineCore = new ItemCraftingBase("machineCore");
		diamondRod = new ItemCraftingBase("diamondRod");
		imbuedCore = new ItemCraftingBase("imbuedCore");
		machinePlate = new ItemCraftingBase("machinePlate");

		// other
		filterCard = new ItemFilterCard();
	}
}

package practicalities.items;

import net.minecraft.item.Item;
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

	// other
	public static Item filterCard;
	
	public static void init(){
		// tools
		voidBucket = new ItemVoidBucket();
		matterTransporter = new ItemMatterTransporter();
		magnet = new ItemMagnet();
		sitisStick = new ItemSitisStick();
				
		// crafting components
		machineCore = new ItemCraftingBase("machineCore");
		diamondRod = new ItemCraftingBase("diamondRod");
		imbuedCore = new ItemCraftingBase("imbuedCore");

		// other
		filterCard = new ItemFilterCard();
	}
}

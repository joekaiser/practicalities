package practicalities.items;

import net.minecraft.item.Item;
import practicalities.items.filtercard.ItemFilterCard;

public class ModItems {

	public static Item voidBucket;
	public static Item matterTransporter;
	public static Item magnet;
	public static Item machineCore;
	public static Item filterCard;
	public static Item sitisStick;
	
	public static void init(){
		voidBucket = new ItemVoidBucket();
		matterTransporter = new ItemMatterTransporter();
		magnet = new ItemMagnet();
		machineCore = new ItemCraftingBase("machineCore");
		filterCard = new ItemFilterCard();
		sitisStick = new ItemSitisStick();
	}
}

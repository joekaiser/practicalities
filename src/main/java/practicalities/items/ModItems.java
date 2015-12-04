package practicalities.items;

import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import practicalities.ConfigMan;
import practicalities.items.filtercard.ItemFilterCard;
import net.minecraftforge.common.util.EnumHelper;

public class ModItems {
	public static final ToolMaterial imbuedToolMaterial =
			EnumHelper.addToolMaterial("imbued", 
					100, 10000, 6000, 10, 40);

	public static final ToolMaterial imbuedWeaponMaterial = 
			EnumHelper.addToolMaterial("imbued", 
					6,10000, 20, 40, 40);

	// tools
	public static Item voidBucket;
	public static Item matterTransporter;
	public static Item magnet;
	public static Item sitisStick;
	public static Item imbuedTool;
	public static Item imbuedSword;
	
	// crafting components
	public static Item diamondRod;
	public static Item machineCore;
	public static Item imbuedCore;
	public static Item machinePlate;
	public static Item imbuedRod;

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
		
		if(ConfigMan.enabledImbuedItems){
			imbuedSword = new ItemImbuedSword();
			imbuedTool = new ItemImbuedTool();
		}
				
		// crafting components
		machineCore = new ItemCraftingBase("machineCore");
		diamondRod = new ItemCraftingBase("diamondRod");
		imbuedCore = new ItemCraftingBase("imbuedCore");
		imbuedRod = new ItemCraftingBase("imbuedRod");
		machinePlate = new ItemCraftingBase("machinePlate");

		// other
		filterCard = new ItemFilterCard();
	}
}

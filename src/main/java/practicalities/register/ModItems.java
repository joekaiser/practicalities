package practicalities.register;

import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import practicalities.ConfigMan;
import practicalities.items.ItemCraftingBase;
import practicalities.items.ItemImbuedSword;
import practicalities.items.ItemImbuedTool;
import practicalities.items.ItemMagnet;
import practicalities.items.ItemMatterTransporter;
import practicalities.items.ItemSitisStick;
import practicalities.items.ItemVoidBucket;
import practicalities.items.filtercard.ItemFilterCard;
import practicalities.items.netherbane.ItemNetherbane;
import net.minecraftforge.common.util.EnumHelper;

public class ModItems {
	public static final ToolMaterial imbuedToolMaterial =
			EnumHelper.addToolMaterial("imbued", 
					100, 10000, 6000, 10, 40);

	public static final ToolMaterial imbuedWeaponMaterial = 
			EnumHelper.addToolMaterial("imbued", 
					6,10000, 20, 40, 40);
	
	public static final ToolMaterial netherBaneMaterial =
			EnumHelper.addToolMaterial("netherBane",4, 5000, 10.0F, 4.0F, 16);

	// tools
	public static Item voidBucket;
	public static Item matterTransporter;
	public static Item magnet;
	public static Item sitisStick;
	public static Item imbuedTool;
	public static Item imbuedSword;
	public static Item netherBlade;
	
	// crafting components
	public static Item diamondRod;
	public static Item machineCore;
	public static Item imbuedCore;
	public static Item machinePlate;
	public static Item imbuedRod;
	public static Item inductiveWrap;

	// other
	public static Item filterCard;
	public static Item blockFakeTorch;
	
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
		
		if(ConfigMan.enableImbuedItems){
			imbuedSword = new ItemImbuedSword();
			imbuedTool = new ItemImbuedTool();
		}
		
		if(ConfigMan.enableNetherBlade){
			netherBlade = new ItemNetherbane();
		}
				
		// crafting components
		machineCore = new ItemCraftingBase("machineCore");
		diamondRod = new ItemCraftingBase("diamondRod");
		imbuedCore = new ItemCraftingBase("imbuedCore");
		imbuedRod = new ItemCraftingBase("imbuedRod");
		machinePlate = new ItemCraftingBase("machinePlate");
		inductiveWrap = new ItemCraftingBase("inductiveWrap");

		// other
		filterCard = new ItemFilterCard();
	}
}

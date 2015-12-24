package practicalities.registers;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import practicalities.Logger;
import practicalities.PracticalitiesMod;
import practicalities.items.ItemBase;
import practicalities.items.ItemCraftingPiece;
import practicalities.items.ItemImbuedSword;
import practicalities.items.ItemImbuedTool;
import practicalities.items.ItemMagnet;
import practicalities.items.ItemMatterTransporter;
import practicalities.items.ItemPracticalGuide;
import practicalities.items.ItemSitisStick;
import practicalities.items.ItemVoidBucket;
import practicalities.items.netherbane.ItemNetherbane;

public class ItemRegister {
	
	public static final ToolMaterial imbuedSwordMaterial = 
			EnumHelper.addToolMaterial("imbued", 100, 15, 100, 35, 30);
	public static final ToolMaterial imbuedToolMaterial = 
			EnumHelper.addToolMaterial("imbued", 100, 5000, 100, 15, 30);
	public static final ToolMaterial netherBaneMaterial =
			EnumHelper.addToolMaterial("netherbane",4, 5000, 10.0F, 4.0F, 16);
	
	public static ItemCraftingPiece craftingPieces;
	public static Item magnet;
	public static Item voidBucket;
	public static Item matterTransporter;
	public static Item practicalGuide;
	public static Item sitisStick;
	public static Item imbuedTool;
	public static Item imbuedSword;
	public static Item netherbane;
	public static Item hamCheese;
	
	public static void init() {
		magnet = new ItemMagnet();
		voidBucket = new ItemVoidBucket();
		matterTransporter = new ItemMatterTransporter();
		practicalGuide = new ItemPracticalGuide();
		sitisStick = new ItemSitisStick();
		imbuedTool = new ItemImbuedTool();
		imbuedSword = new ItemImbuedSword();
		netherbane = new ItemNetherbane();
		hamCheese= new ItemFood(10, 1f, true).setUnlocalizedName("hamCheese").setCreativeTab(PracticalitiesMod.tab);
		GameRegistry.registerItem(hamCheese, "hamCheese");
		
		craftingPieces = new ItemCraftingPiece();
		craftingPieces.addItem("machineCore");
		craftingPieces.addItem("machinePlate");
		craftingPieces.addItem("imbuedCore");
		craftingPieces.addItem("imbuedRod");
		craftingPieces.addItem("magneticNorth");
		craftingPieces.addItem("magneticSouth");

	}

	@SideOnly(Side.CLIENT)
	public static void registerRenders() {
		Logger.info("Registering Items");

		for (int i = 0; i < craftingPieces.subItemList.size(); i++) {
			registerRender(craftingPieces, i, true);
		}

		registerRender((ItemBase) magnet);
		registerRender((ItemBase) voidBucket);
		registerRender((ItemBase) matterTransporter);
		registerRender((ItemBase) practicalGuide);
		registerRender((ItemBase) sitisStick);
		registerRender(imbuedTool,0,false);
		registerRender(imbuedSword,0,false);
		registerRender(netherbane,0,false);
		registerRender(hamCheese,0,false);
	}

	@SideOnly(Side.CLIENT)
	private static void registerRender(Item item, int meta, boolean addVariant) {
		
		String name = item.getUnlocalizedName(new ItemStack(item, 1, meta)).substring(5);
		String resourceLocation = PracticalitiesMod.MODID + ":" + name;
		
		Logger.info("    %s", name);

		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, meta,
				new ModelResourceLocation(resourceLocation, "inventory"));

		if (addVariant) {
			ModelBakery.addVariantName(craftingPieces, resourceLocation);
		}

	}

	@SideOnly(Side.CLIENT)
	private static void registerRender(ItemBase item) {
		Logger.info("    %s", item.getUnlocalizedName().substring(5));
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, (ItemMeshDefinition) item);
	}
	
	

}

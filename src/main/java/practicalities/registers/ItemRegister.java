package practicalities.registers;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
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

	public static final ToolMaterial imbuedSwordMaterial = EnumHelper.addToolMaterial("imbued", 100, 15, 100, 35, 30);
	public static final ToolMaterial imbuedToolMaterial = EnumHelper.addToolMaterial("imbued", 100, 5000, 100, 15, 30);
	public static final ToolMaterial netherBaneMaterial = EnumHelper.addToolMaterial("netherbane", 4, 5000, 10.0F, 4.0F,
			16);

	public static ItemCraftingPiece craftingPieces;
	public static ItemBase magnet;
	public static ItemBase voidBucket;
	public static ItemBase matterTransporter;
	public static ItemBase practicalGuide;
	public static ItemBase sitisStick;
	public static ItemImbuedTool imbuedTool;
	public static ItemImbuedSword imbuedSword;
	public static ItemNetherbane netherbane;
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
		hamCheese = new ItemFood(10, 1f, true).setUnlocalizedName("hamCheese").setCreativeTab(PracticalitiesMod.tab);
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

		magnet.initModel();
		voidBucket.initModel();
		matterTransporter.initModel();
		practicalGuide.initModel();
		sitisStick.initModel();
		craftingPieces.initModel();
		imbuedSword.initModel();
		imbuedTool.initModel();
		netherbane.initModel();
		registerGenericItem(hamCheese, 0);

		return;

	}

	/***
	 * Used for items that extend Minecraft.Item instead of ItemBase
	 * 
	 * @param item
	 * @param meta
	 */
	@SideOnly(Side.CLIENT)
	private static void registerGenericItem(Item item, int meta) {
		String name = item.getUnlocalizedName().substring(5);
		Logger.info("    Registering model for %s", name);
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(PracticalitiesMod.TEXTURE_BASE + name, "inventory"));

	}

}

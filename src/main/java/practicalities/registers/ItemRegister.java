package practicalities.registers;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import practicalities.Logger;
import practicalities.PracticalitiesMod;
import practicalities.items.ItemBase;
import practicalities.items.ItemPracticalGuide;
import practicalities.items.ItemCraftingPiece;
import practicalities.items.ItemMagnet;
import practicalities.items.ItemMatterTransporter;
import practicalities.items.ItemVoidBucket;

public class ItemRegister {
	public static ItemCraftingPiece craftingPieces;
	public static Item magnet;
	public static Item voidBucket;
	public static Item matterTransporter;
	public static Item practicalGuide;
	
	public static void init() {
		magnet = new ItemMagnet();
		voidBucket = new ItemVoidBucket();
		matterTransporter = new ItemMatterTransporter();
		practicalGuide = new ItemPracticalGuide();
		
		
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

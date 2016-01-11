package practicalities.registers;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import practicalities.Logger;
import practicalities.PracticalitiesMod;

public final class BlockRegister {
	
	
	public static void init(){
	}
	
	@SideOnly(Side.CLIENT)
	public static void registerRenders(){
		
		
		Logger.info("Registering Blocks");
	}
	
	@SideOnly(Side.CLIENT)
	private static void registerRender(Block block, int meta) {
		
		Item item = Item.getItemFromBlock(block);
		String name = item.getUnlocalizedName().substring(5);
		String resourceLocation = PracticalitiesMod.MODID + ":" + name;
		
		Logger.info("    %s", name);

		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, meta,
				new ModelResourceLocation(resourceLocation, "inventory"));

	
	}

}

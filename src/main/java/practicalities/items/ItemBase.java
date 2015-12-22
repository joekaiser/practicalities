package practicalities.items;

import java.util.List;

import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import practicalities.PracticalitiesMod;
import practicalities.helpers.ItemHelpers;

public class ItemBase extends Item implements ItemMeshDefinition {

	public ItemBase(String name) {
		setUnlocalizedName(name);
		GameRegistry.registerItem(this, name);
		setCreativeTab(PracticalitiesMod.tab);
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {

	}

	@Override
	@SideOnly(Side.CLIENT)
	public ModelResourceLocation getModelLocation(ItemStack stack) {
		return ItemHelpers.getModelLocation(stack);
	}

}

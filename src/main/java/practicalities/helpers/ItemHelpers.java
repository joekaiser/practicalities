package practicalities.helpers;

import java.util.List;

import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import practicalities.Logger;
import practicalities.PracticalitiesMod;

public final class ItemHelpers {
	
	public static void addFlairToList(List<String> tooltip, String flairName) {
		String flair = "flair." + flairName;
		if (StatCollector.canTranslate(flair)) {
			tooltip.add(StatCollector.translateToLocal(flair));
		} else {
			int i = 0;
			while (StatCollector.canTranslate(flair + "." + i)) {
				tooltip.add(StatCollector.translateToLocal(flair + "." + i));
				i++;
			}
		}
	}

//	@SideOnly(Side.CLIENT)
//	public static ModelResourceLocation getModelLocation(ItemStack stack) {
//		return new ModelResourceLocation(PracticalitiesMod.TEXTURE_BASE + stack.getUnlocalizedName().substring(5),"inventory");
//	}
	
	public static ItemStack parseItemStack(String str) {
		String[] parts = str.split("\\|");
		
		Item item = null;
		int meta = 0;
		int quantity = 1;
		NBTTagCompound tag = null;
		
		item = parseItem(parts[0]);
		
		if(parts.length > 1)
			quantity = parseNum(parts[1]);
		if(parts.length > 2)
			meta = parseNum(parts[2]);
		if(parts.length > 3)
			tag = parseTag(parts[3]);
		
		if(item == null)
			return null;
		
		ItemStack stack = new ItemStack(item, quantity, meta);
		stack.setTagCompound(tag);
		
		return stack;
	}
	
	public static Item parseItem(String str) {
		ResourceLocation resourcelocation = new ResourceLocation(str);
        Item item = (Item)Item.itemRegistry.getObject(resourcelocation);
		if(item == null) {
			Logger.warning("ITEM NOT FOUND, RETURNING NULL! '%s'", str);
		}
		return item;
	}
	
	public static int parseNum(String str) {
		int meta = 0;
		try {
			meta = Integer.parseInt(str);
		} catch (NumberFormatException numberformatexception) {
			;
		}
		return meta;
	}
	
	public static NBTTagCompound parseTag(String str) {
		NBTBase nbtbase;
		try {
			nbtbase = JsonToNBT.getTagFromJson(str);
		} catch (NBTException e) {
			
			Logger.warning("ERROR LOADING NBT TAG! Error: '%s' Json: '%s'", e.getMessage(), str);
			return null;
		}

		if (!(nbtbase instanceof NBTTagCompound)) {
			Logger.warning("INVALID NBT TAG! Json: '%s'", str);
			return null;
		}
		
		return (NBTTagCompound)nbtbase;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static boolean isPlayerHolding(Class c, EntityPlayer p) {
		return p.getHeldItem() != null && p.getHeldItem().getItem() != null &&
				c.isAssignableFrom(p.getHeldItem().getItem().getClass());
	}
}

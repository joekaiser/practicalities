package practicalities.helpers;

import java.util.List;

import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import practicalities.PracticalitiesMod;
import scala.collection.mutable.StringBuilder;

public final class ItemHelpers {
	public static void addFlairToList(List<String> list, String flairName) {
		String flair = "flair." + flairName;
		if (StatCollector.canTranslate(flair)) {
			list.add(StatCollector.translateToLocal(flair));
		} else {
			int i = 0;
			while (StatCollector.canTranslate(flair + "." + i)) {
				list.add(StatCollector.translateToLocal(flair + "." + i));
				i++;
			}
		}
	}

	@SideOnly(Side.CLIENT)
	public static ModelResourceLocation getModelLocation(ItemStack stack) {
		return new ModelResourceLocation("practicalities:" + stack.getUnlocalizedName().substring(5),"inventory");
	}
}

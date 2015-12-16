package practicalities.blocks;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public interface IBlockFlair {

	void addInformation(ItemStack stack, EntityPlayer player, List<String> list, boolean thing);

}

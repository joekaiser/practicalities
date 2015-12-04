package practicalities.items.netherbane;

import java.util.List;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.StatCollector;
import practicalities.PracticalitiesMod;
import practicalities.items.ModItems;

public class ItemNetherbane extends ItemSword {

	public ItemNetherbane() {
		super(ModItems.netherBaneMaterial);
		String name="netherBane";
		GameRegistry.registerItem(this, name);
		setUnlocalizedName(name);
		setCreativeTab(PracticalitiesMod.tab);
		setTextureName(PracticalitiesMod.TEXTURE_BASE+name);
		setMaxStackSize(1);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void addInformation(ItemStack itemStack, EntityPlayer player, List list,
			boolean p_77624_4_) {
		list.add(StatCollector.translateToLocal("tooltip.netherBane.flair"));
	}
	
}
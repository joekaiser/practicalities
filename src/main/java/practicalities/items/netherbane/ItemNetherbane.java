package practicalities.items.netherbane;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import practicalities.PracticalitiesMod;
import practicalities.register.ModItems;
import practicalities.utils.ModUtils;
import cpw.mods.fml.common.registry.GameRegistry;

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
		ModUtils.addFlairToList(list, "item.netherBane");
//		list.add(StatCollector.translateToLocal("tooltip.netherBane.flair"));
	}
	
}
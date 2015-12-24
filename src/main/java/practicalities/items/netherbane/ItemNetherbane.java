package practicalities.items.netherbane;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraftforge.fml.common.registry.GameRegistry;
import practicalities.PracticalitiesMod;
import practicalities.helpers.ItemHelpers;
import practicalities.registers.ItemRegister;

public class ItemNetherbane extends ItemSword {

	public ItemNetherbane() {
		super(ItemRegister.netherBaneMaterial);
		String name="netherbane";
		GameRegistry.registerItem(this, name);
		setUnlocalizedName(name);
		setCreativeTab(PracticalitiesMod.tab);
		setMaxStackSize(1);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void addInformation(ItemStack itemStack, EntityPlayer player, List list,
			boolean p_77624_4_) {
		ItemHelpers.addFlairToList(list, "item.netherBane");
	}
	
}
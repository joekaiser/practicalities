package practicalities.items;

import java.util.List;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import practicalities.PracticalitiesMod;

public class ItemImbuedSword extends ItemSword {

	public ItemImbuedSword() {
		super(ModItems.imbuedWeaponMaterial);
		String name="imbuedSword";
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
		list.add(StatCollector.translateToLocal("tooltip.imbuedSword.flair"));
	}

	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase p_77644_2_,
			EntityLivingBase p_77644_3_) {
		setDamage(stack, 0);
		return true;
	}

	@Override
	public boolean onBlockDestroyed(ItemStack stack, World p_150894_2_, Block p_150894_3_,
			int p_150894_4_, int p_150894_5_, int p_150894_6_, EntityLivingBase p_150894_7_) {
		setDamage(stack, 0);
		return true;
	}
	
	@Override
	public void setDamage(ItemStack stack, int damage) {
		super.setDamage(stack, 0);
	}
}
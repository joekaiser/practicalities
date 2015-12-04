package practicalities.items;

import java.util.List;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import practicalities.PracticalitiesMod;

public class ItemImbuedTool extends ItemPickaxe {

	public ItemImbuedTool() {
		super(ModItems.imbuedToolMaterial);
		String name="imbuedTool";
		GameRegistry.registerItem(this, name);
		setUnlocalizedName(name);
		setCreativeTab(PracticalitiesMod.tab);
		setTextureName(PracticalitiesMod.TEXTURE_BASE+name);
		setMaxStackSize(1);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void addInformation(ItemStack itemStack, EntityPlayer player, List list,
			boolean p_77624_4_) {
		list.add(StatCollector.translateToLocal("tooltip.imbuedTool.flair"));
	}


	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase entity,
			EntityLivingBase entity2) {
		setDamage(stack, 0);
		return true;
	}
	
	@Override
	public void setDamage(ItemStack stack, int damage) {
		super.setDamage(stack, 0);
	}

	@Override
	public boolean onBlockDestroyed(ItemStack stack, World world, Block block, int x, int y,
			int z, EntityLivingBase player) {
		setDamage(stack, 0);
		return true;
	}

	@Override
	public float getDigSpeed(ItemStack stack, Block block, int meta) {
		return efficiencyOnProperMaterial;
	}
}
package practicalities.items;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import practicalities.PracticalitiesMod;
import practicalities.register.ModItems;
import cpw.mods.fml.common.registry.GameRegistry;

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
	public boolean onItemUse(ItemStack a, EntityPlayer b, World c, int d, int e, int f, int g, float h, float i, float j) {
		return ModItems.blockFakeTorch.onItemUse(new ItemStack(ModItems.blockFakeTorch, 1), b, c, d, e, f, g, h, i, j);
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
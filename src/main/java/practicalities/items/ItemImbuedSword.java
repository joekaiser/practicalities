package practicalities.items;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import practicalities.Logger;
import practicalities.PracticalitiesMod;
import practicalities.helpers.ItemHelpers;
import practicalities.registers.ItemRegister;

public class ItemImbuedSword extends ItemSword {

	public ItemImbuedSword() {
		super(ItemRegister.imbuedSwordMaterial);
		String name="imbuedSword";
		GameRegistry.registerItem(this, name);
		setUnlocalizedName(name);
		setCreativeTab(PracticalitiesMod.tab);
		setMaxStackSize(1);
	}
	
	@SideOnly(Side.CLIENT)
    public void initModel() {
    	Logger.info("    Registering model for imbuedSword");
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void addInformation(ItemStack itemStack, EntityPlayer player, List list,
			boolean p_77624_4_) {
		ItemHelpers.addFlairToList(list, "item.imbuedSword");
	}

	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase p_77644_2_,
			EntityLivingBase p_77644_3_) {
		setDamage(stack, 0);
		return true;
	}

	@Override
	public boolean onBlockDestroyed(ItemStack stack, World worldIn, Block blockIn, BlockPos pos,
			EntityLivingBase playerIn) {
		setDamage(stack, 0);
		return true;
	}
	
	@Override
	public void setDamage(ItemStack stack, int damage) {
		super.setDamage(stack, 0);
	}
	
	@Override
	public float getDamageVsEntity() {
		// TODO Auto-generated method stub
		return super.getDamageVsEntity();
	}
}
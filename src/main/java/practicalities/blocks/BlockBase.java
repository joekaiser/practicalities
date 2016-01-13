package practicalities.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import practicalities.Logger;
import practicalities.PracticalitiesMod;

public class BlockBase extends Block {
	public BlockBase(Material material, String name) {
		super(material);
		setUnlocalizedName(name);
		setRegistryName(name);
		GameRegistry.registerBlock(this);
		setCreativeTab(PracticalitiesMod.tab);
		
	}
	
	public String getSimpleName(){
		return getUnlocalizedName().substring(5);
	}
	
    @SideOnly(Side.CLIENT)
    public void initModel() {
    	Logger.info("    Registering model for %s",getSimpleName());
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }
}

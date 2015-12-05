package practicalities.machine.playerinterface;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.World;
import practicalities.PracticalitiesMod;
import practicalities.blocks.BlockBase;

public class BlockPlayerInterface extends BlockBase implements ITileEntityProvider {

	public BlockPlayerInterface() {
		super(Material.iron, "playerinterface", 1, null);
		setStepSound(soundTypeMetal);
	}
	
	@Override
	public boolean hasTileEntity() {
		return true;
	}

	@Override
	public TileEntity createTileEntity(World world, int metadata) {
		return new TilePlayerInterface();
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int p6, float p7, float p8, float p9) {
		
		if(world.isRemote)
			return true;
		
		TilePlayerInterface tile = (TilePlayerInterface)world.getTileEntity(x, y, z);

		player.addChatComponentMessage(new ChatComponentTranslation("chat.playerinterface.info", tile.getLastName(), tile.getUUID().toString()));
		
		return true;

	}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
		super.onBlockPlacedBy(world, x, y, z, entity, stack);
		if(!( entity instanceof EntityPlayer )) {
			return;
		}
		TilePlayerInterface tile = (TilePlayerInterface)world.getTileEntity(x, y, z);
		tile.setUUID(entity.getUniqueID());
	}

}

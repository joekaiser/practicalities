package practicalities.items;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import practicalities.helpers.BlockHelpers;

public class ItemVoidBucket extends ItemBase {

	public ItemVoidBucket(){
		super("voidBucket");
		setMaxStackSize(1);
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer player) {
	
		MovingObjectPosition movingobjectposition = this.getMovingObjectPositionFromPlayer(world,
				player, true);
		

		if (movingobjectposition !=null){
			if (movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
				BlockPos blockPos =movingobjectposition.getBlockPos();


				if (!world.canMineBlockBody(player, blockPos)) {
					return itemstack;
				}

				if (!player.canPlayerEdit(blockPos, movingobjectposition.sideHit, itemstack)) {
					return itemstack;
				}

				if (player.isSneaking()) {
					deleteLiquid(world, blockPos);

				} else {
					
					int i = blockPos.getX();
					int j = blockPos.getY();
					int k = blockPos.getZ();

					for (int j2 = j - 1; j2 < j + 1; j2++) {

						for (int i2 = -2; i2 <= 3; i2++) {
							int count = 2;
							if (i2 < 0)
								count = 2 + i2;
							else if (i2 > 0)
								count = 2 - i2;

							for (int k2 = 0; k2 <= count; k2++) {
								
								deleteLiquid(world, BlockHelpers.getBlockPosFromXYZ(i + i2, j2, k + k2));
								deleteLiquid(world, BlockHelpers.getBlockPosFromXYZ(i + i2, j2, k - k2));
							}
						}
					}
				}

			}
		}

		return itemstack;
	}

	private void deleteLiquid(World world, BlockPos pos) {
		Material material = world.getBlockState(pos).getBlock().getMaterial();

		if (material == Material.water) {
			world.setBlockToAir(pos);
		}

		if (material == Material.lava) {
			world.setBlockToAir(pos);
		}
		if (material.isLiquid()) {
			world.setBlockToAir(pos);
		}
	}

}
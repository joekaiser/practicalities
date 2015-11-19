package practicalities.items;

 import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import practicalities.PracticalitiesMod;

public class ItemVoidBucket extends Item {

	public ItemVoidBucket(){
		super();
		String name="voidBucket";
		GameRegistry.registerItem(this, name);
		setUnlocalizedName(name);
		setCreativeTab(PracticalitiesMod.tab);
		setTextureName(PracticalitiesMod.TEXTURE_BASE+name);
		setMaxStackSize(1);
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer player) {
	
		MovingObjectPosition movingobjectposition = this.getMovingObjectPositionFromPlayer(world,
				player, true);
		

		if (movingobjectposition !=null){
			if (movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
				int i = movingobjectposition.blockX;
				int j = movingobjectposition.blockY;
				int k = movingobjectposition.blockZ;

				if (!world.canMineBlock(player, i, j, k)) {
					return itemstack;
				}

				if (!player.canPlayerEdit(i, j, k, movingobjectposition.sideHit, itemstack)) {
					return itemstack;
				}

				if (player.isSneaking()) {
					deleteLiquid(world, i, j, k);

				} else {

					for (int j2 = j - 1; j2 < j + 1; j2++) {

						for (int i2 = -2; i2 <= 3; i2++) {
							int count = 2;
							if (i2 < 0)
								count = 2 + i2;
							else if (i2 > 0)
								count = 2 - i2;

							for (int k2 = 0; k2 <= count; k2++) {
								deleteLiquid(world, i + i2, j2, k + k2);
								deleteLiquid(world, i + i2, j2, k - k2);
							}
						}
					}
				}

			}
		}

		return itemstack;
	}

	private void deleteLiquid(World world, int i, int j, int k) {
		Material material = world.getBlock(i, j, k).getMaterial();
		

		if (material == Material.water) {
			world.setBlockToAir(i, j, k);
		}

		if (material == Material.lava) {
			world.setBlockToAir(i, j, k);
		}
		if (material.isLiquid()) {
			world.setBlockToAir(i, j, k);
		}
	}

}

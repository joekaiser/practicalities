package practicalities.items;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import practicalities.PracticalitiesMod;
import practicalities.helpers.BlockHelpers;
import practicalities.helpers.ItemHelpers;

public class ItemSitisStick extends ItemBase {
	private int iconCount = 3;
	private int useLength = 20;
	
	public ItemSitisStick() {
		super("sitisStick");
		setMaxStackSize(1);
		setMaxDamage(16);
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
		ItemHelpers.addFlairToList(tooltip, "item.sitisStick" );
	}
	
	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
		return EnumAction.BOW;
	}
	
	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return 10000;
	}
	
	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World world, EntityPlayer player, int time) {
		int j = useLength - ( getMaxItemUseDuration(stack) - time );
		if(j < 0) {
			boolean sky = world.canBlockSeeSky(BlockHelpers.getBlockPosFromXYZ(MathHelper.floor_double(player.posX), MathHelper.floor_double(player.posY), MathHelper.floor_double(player.posZ)));
			if(!world.isRemote && world.isRaining() && sky) {
				world.provider.resetRainAndThunder();
				world.playSoundAtEntity(player, "random.orb", 1, 1);
				player.addChatMessage(new ChatComponentTranslation("chat.sitis.clear"));
	            if(!player.capabilities.isCreativeMode)
	            	stack.damageItem(1, player);
			} else {
				world.playSoundAtEntity(player, "random.fizz", 1, 1);
			}
			
			if(!world.isRemote) {
				if(!sky) {
					player.addChatMessage(new ChatComponentTranslation("chat.sitis.noSky"));
				}
				else if(!world.isRaining()) {
					player.addChatMessage(new ChatComponentTranslation("chat.sitis.noRain"));
				}
			}
		}
	}
	
//	@Override
//	public ItemStack onEaten(ItemStack stack, World world, EntityPlayer player) {
//		stack = super.onEaten(stack, world, player);
//		stack.stackSize--;
//		if(stack.stackSize == 0)
//			stack = null;
//		return stack;
//	}
	
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		player.setItemInUse(stack, this.getMaxItemUseDuration(stack));
		return stack;
	}
	
//    @SideOnly(Side.CLIENT)
//    public IIcon getItemIconForUseDuration(int p_94599_1_)
//    {
//        return this.iconArray[p_94599_1_];
//    }
}

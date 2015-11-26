package practicalities.items;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import practicalities.PracticalitiesMod;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemSitisStick extends Item {
	private IIcon[] iconArray;
	private int iconCount = 3;
	private int useLength = 20;
	
	public ItemSitisStick() {
		String name = "sitisStick";
		GameRegistry.registerItem(this, name);
		setMaxStackSize(1);
		setMaxDamage(10);
		setUnlocalizedName(name);
		setCreativeTab(PracticalitiesMod.tab);
		setTextureName(PracticalitiesMod.TEXTURE_BASE+name);
	}
	
	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
		return EnumAction.bow;
	}
	
	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return 10000;
	}
	
	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World world, EntityPlayer player, int time) {
		int j = useLength - ( getMaxItemUseDuration(stack) - time );
		if(j < 0) {
			boolean sky = world.canBlockSeeTheSky(MathHelper.floor_double(player.posX), MathHelper.floor_double(player.posY), MathHelper.floor_double(player.posZ));
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
	
	@Override
	public ItemStack onEaten(ItemStack stack, World world, EntityPlayer player) {
		stack = super.onEaten(stack, world, player);
		stack.stackSize--;
		if(stack.stackSize == 0)
			stack = null;
		return stack;
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		player.setItemInUse(stack, this.getMaxItemUseDuration(stack));
		return stack;
	}
	
	@SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister ir)
    {
        this.itemIcon = ir.registerIcon(this.getIconString());
        this.iconArray = new IIcon[iconCount];

        for (int i = 0; i < iconArray.length; ++i)
        {
            this.iconArray[i] = ir.registerIcon(this.getIconString() + (i == 0 ? "" : "_" + i ) );
        }
    }
	
	@Override
	public IIcon getIcon(ItemStack stack, int renderPass, EntityPlayer player, ItemStack usingItem, int useRemaining) {
		if(useRemaining == 0) {
			return getItemIconForUseDuration(0);
		}
		int useDone = useLength - Math.max(0, useLength - ( getMaxItemUseDuration(stack) - useRemaining ) );
		double useDoneFrac = useDone / (double)useLength;
		int stage = (int)Math.floor( useDoneFrac * ( iconCount - 1 ) );
		return getItemIconForUseDuration(stage);
	}

    @SideOnly(Side.CLIENT)
    public IIcon getItemIconForUseDuration(int p_94599_1_)
    {
        return this.iconArray[p_94599_1_];
    }
}

package practicalities.items;

import java.util.Iterator;
import java.util.List;

import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import practicalities.PracticalitiesMod;
import practicalities.machine.polaritynegator.PolarityNegatorManager;
import practicalities.utils.ModUtils;

public class ItemMagnet extends Item {

	private int distanceFromPlayer;

	public ItemMagnet() {
		super();
		String name = "magnet";
		GameRegistry.registerItem(this, name);
		setUnlocalizedName(name);
		setCreativeTab(PracticalitiesMod.tab);
		setTextureName(PracticalitiesMod.TEXTURE_BASE + name);
		setMaxStackSize(1);
		this.distanceFromPlayer = 5;
		canRepair = false;
		setMaxDamage(0);

	}

	@Override
	public boolean hasEffect(ItemStack item) {
		return isActivated(item);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer player) {
		if (!world.isRemote && player.isSneaking()) {
			item.setItemDamage(item.getItemDamage() == 0 ? 1 : 0);
		}
		return item;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void onUpdate(ItemStack item, World world, Entity entity, int i, boolean f) {
		if (world.isRemote)
			return;
		if (!isActivated(item))
			return;
		if (!(entity instanceof EntityPlayer))
			return;
		
		PolarityNegatorManager.cacheWorldData(world);
		
		EntityPlayer player = (EntityPlayer) entity;

		// items
		Iterator iterator = ModUtils.getEntitiesInRange(EntityItem.class, world, player.posX, player.posY, player.posZ,
				this.distanceFromPlayer).iterator();
		while (iterator.hasNext()) {
			EntityItem itemToGet = (EntityItem) iterator.next();
			if( PolarityNegatorManager.isEntityCloseToNegator(itemToGet) )
				continue;
			itemToGet.delayBeforeCanPickup = 50;

			EntityItemPickupEvent pickupEvent = new EntityItemPickupEvent(player, itemToGet);
			MinecraftForge.EVENT_BUS.post(pickupEvent);
			ItemStack itemStackToGet = itemToGet.getEntityItem();
			int stackSize = itemStackToGet.stackSize;

			if (pickupEvent.getResult() == Result.ALLOW || stackSize <= 0
					|| player.inventory.addItemStackToInventory(itemStackToGet)) {
				player.onItemPickup(itemToGet, stackSize);

				world.playSoundAtEntity(player, "random.pop", 0.15F,
						((world.rand.nextFloat() - world.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
			}
		}

		// xp
		iterator = ModUtils.getEntitiesInRange(EntityXPOrb.class, world, player.posX, player.posY, player.posZ,
				this.distanceFromPlayer).iterator();
		while (iterator.hasNext()) {
			EntityXPOrb xpToGet = (EntityXPOrb) iterator.next();
			if( PolarityNegatorManager.isEntityCloseToNegator(xpToGet) )
				continue;
			
			if (xpToGet.isDead || xpToGet.isInvisible()) {
				continue;
			}
			int xpAmount = xpToGet.xpValue;
			xpToGet.xpValue = 0;
			player.xpCooldown = 0;
			player.addExperience(xpAmount);
			xpToGet.setDead();
			xpToGet.setInvisible(true);
			world.playSoundAtEntity(player, "random.orb", 0.08F,
					0.5F * ((world.rand.nextFloat() - world.rand.nextFloat()) * 0.7F + 1.8F));
		}
		
		PolarityNegatorManager.cleareWorldDataCache();
	}

	protected boolean isActivated(ItemStack item) {
		return item.getItemDamage() == 1;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void addInformation(ItemStack itemStack, EntityPlayer player, List info, boolean extended) {
		info.add(StatCollector.translateToLocal("tooltip.itemMagnet.flair"));
	}

}
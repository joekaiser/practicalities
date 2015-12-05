package practicalities.network;


import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import practicalities.blocks.ModBlocks;
import practicalities.items.ModItems;
import practicalities.items.netherbane.EntityNetherbane;
import practicalities.machine.vampiricgenerator.TileVampiricGenerator;

public class Proxy {
	

	public void preInit() {
		ModItems.init();
		ModBlocks.init();
		Net.init();

	}

	public void registerKeyBinds() {

	}

	public void registerPacketInformation() {

	}

	public void registerRenderInformation() {

	}

	public void registerTickHandlers() {
		MinecraftForge.EVENT_BUS.register(this);
		FMLCommonHandler.instance().bus().register(this);
	}

	public boolean isClient() {

		return false;
	}

	public boolean isServer() {

		return true;
	}

	@SubscribeEvent
	public void onLivingDrops(LivingDropsEvent event) {
		if(event.entity.worldObj.isRemote) 
			return;
		
		if (event.source == TileVampiricGenerator.vampiricDamage) {
			event.setCanceled(true);
		}
	}

	@SubscribeEvent
	public void onItemToss(ItemTossEvent event) {
		if(event.entity.worldObj.isRemote) 
			return;
		
		if (event.entityItem.dimension == -1) {
			ItemStack itemTossed = event.entityItem.getEntityItem();
			if (itemTossed.getDisplayName().equals("Netherbane") && itemTossed.getItem().equals(Items.diamond_sword)) {
				event.entity.worldObj.spawnEntityInWorld(EntityNetherbane.convert(event.entityItem));
				event.setCanceled(true);
				
			}
		}
	}

}

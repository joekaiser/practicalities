package practicalities.network;


import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import practicalities.items.netherbane.EntityNetherbane;
import practicalities.machine.vampiricgenerator.TileVampiricGenerator;
import practicalities.register.ModBlocks;
import practicalities.register.ModItems;
import practicalities.register.ModMultiblocks;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class Proxy {
	

	public void preInit() {
		ModItems.init();
		ModBlocks.init();
		ModMultiblocks.init();
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

package practicalities;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import practicalities.blocks.ModBlocks;
import practicalities.items.ModItems;
import practicalities.machine.vampiricgenerator.TileVampiricGenerator;
import practicalities.network.Net;

public class Proxy {
	
	public void preInit(){
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
	
	}
	public boolean isClient() {

		return false;
	}

	public boolean isServer() {

		return true;
	}
	
	@SubscribeEvent
	public void onLivingDrops(LivingDropsEvent event){
		if(event.source==TileVampiricGenerator.vampiricDamage){
			event.setCanceled(true);
		}
	}
	

}

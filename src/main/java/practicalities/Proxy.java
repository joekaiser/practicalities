package practicalities;

import practicalities.blocks.ModBlocks;
import practicalities.items.ModItems;

public class Proxy {
	
	public void preInit(){
		ModItems.init();
    	ModBlocks.init();
    	
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
	

}

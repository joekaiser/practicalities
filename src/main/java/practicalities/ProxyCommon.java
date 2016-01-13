package practicalities;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import practicalities.items.netherbane.EntityNetherbane;
import practicalities.registers.BlockRegister;
import practicalities.registers.ItemRegister;
import practicalities.registers.RecipeRegister;

public class ProxyCommon {

	public void preInit() {
		ItemRegister.init();
		BlockRegister.init();
		
	}

	public void init() {
		
	}

	public void postInit() {
		new RecipeRegister().init();
		registerTickHandlers();
	}

	public void registerKeyBindings() {

	}

	public void registerRenders() {

	}
	
	public void registerTickHandlers() {
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	/*Events*/
	
	@SubscribeEvent
	public void onLivingDrops(LivingDropsEvent event) {
		if(event.entity.worldObj.isRemote) 
			return;
		
//		if (event.source == TileVampiricGenerator.vampiricDamage) {
//			event.setCanceled(true);
//		}
		
		if(event.entityLiving instanceof EntityPig){
			if(event.entity.worldObj.rand.nextDouble() <.0002){
				EntityItem hamCheese = new EntityItem(event.entityLiving.worldObj,
						event.entityLiving.posX,event.entityLiving.posY,event.entityLiving.posZ,
						new ItemStack(ItemRegister.hamCheese));
				hamCheese.dimension = event.entityLiving.dimension;
				
				event.drops.add(hamCheese);
				
			}
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

package practicalities;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import practicalities.registers.GuiHandler;
import practicalities.registers.ItemRegister;

@Mod(modid = PracticalitiesMod.MODID, version = PracticalitiesMod.VERSION, name = PracticalitiesMod.MODNAME)
public class PracticalitiesMod {
	public static final String MODID = "practicalities";
	public static final String MODNAME = "Practicalities";
	public static final String VERSION = "2.0.0-b6";
	public static final String TEXTURE_BASE = MODID + ":";


	@Instance(PracticalitiesMod.MODID)
	public static PracticalitiesMod instance;
	
	public static GuiHandler handler = new GuiHandler();

	@SidedProxy(clientSide = "practicalities.ProxyClient", serverSide = "practicalities.ProxyCommon")
	public static ProxyCommon proxy;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		proxy.preInit();
		ConfigMan.init(new Configuration(event.getSuggestedConfigurationFile()));
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.init();
		NetworkRegistry.INSTANCE.registerGuiHandler(instance, handler);
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		proxy.postInit();
	}

	public static CreativeTabs tab = new CreativeTabs("tabPracticalities") {
		@Override
		@SideOnly(Side.CLIENT)
		public Item getTabIconItem() {
			return ItemRegister.craftingPieces.getSubItem("machineCore").getItem();
		}
	};

}

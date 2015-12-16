package practicalities;

import net.minecraftforge.common.config.Configuration;

public class ConfigMan
{
	/* I don't think it is worth adding an option to disable 
	 * crafting components (like the imbued core).
	 * Sure, they will take up an ID for no reason
	 * but I feel that is an ok trade off for not having
	 * to worry about it when making recipes
	 */		
		 
    private static Configuration config;
    
    //blocks
    public static boolean enableShippingCrate;
    public static boolean enableVampiricGenerator;
    public static boolean enablePlayerInterface;
    public static boolean enableInventoryFilter;
    public static boolean enableTeslaCoil;
    
    //items
    public static boolean enableMagnet;
    public static boolean enableVoidBucket;
    public static boolean enableMatterTransporter;
    public static boolean enableSitisStick;
    public static boolean enableImbuedItems;
    public static boolean enableNetherBlade;

    public static int teslaRange;
    
    public static void init(Configuration configuration)
    {
        config = configuration;
        config.load();
        hydrateConifg();
        config.save();
    }

    public static void hydrateConifg()
    {
    	//blocks
    	enableMagnet = config.getBoolean("enableMagnet", "general", true, "");
    	enableShippingCrate = config.getBoolean("enableShippingCrate", "general", true, "");
    	enableVampiricGenerator = config.getBoolean("enableVampiricGenerator", "general", true, "");
    	enablePlayerInterface = config.getBoolean("enablePlayerInterface", "general", true, "");
    	enableInventoryFilter = config.getBoolean("enableInventoryFilter", "general", true, "");
    	enableTeslaCoil = config.getBoolean("enableTeslaCoil", "general", true, "");
    	
    	//items
    	enableVoidBucket = config.getBoolean("enableVoidBucket", "general", true, "");
    	enableMatterTransporter = config.getBoolean("enableMatterTransporter", "general", true, "");
    	enableSitisStick = config.getBoolean("enableSitisStick", "general", true, "");
    	enableImbuedItems = config.getBoolean("enableImbuedItems", "general", true, "");
    	enableNetherBlade = config.getBoolean("enableNetherBlade", "general", true, "");
    	
    	teslaRange = config.getInt("teslaCoilRange", "general", 16, 1, 64, "");
    }
}

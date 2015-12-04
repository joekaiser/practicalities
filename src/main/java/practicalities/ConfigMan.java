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
    public static boolean enableInventoryFilter;
    
    //items
    public static boolean enableMagnet;
    public static boolean enableVoidBucket;
    public static boolean enableMatterTransporter;
    public static boolean enableSitisStick;
    public static boolean enabledImbuedItems;

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
    	enableInventoryFilter = config.getBoolean("enableInventoryFilter", "general", true, "");
    	
    	//items
    	enableVoidBucket = config.getBoolean("enableVoidBucket", "general", true, "");
    	enableMatterTransporter = config.getBoolean("enableMatterTransporter", "general", true, "");
    	enableSitisStick = config.getBoolean("enableSitisStick", "general", true, "");
    	enabledImbuedItems = config.getBoolean("enableImbuedItems", "general", true, "");
    	
    }
}

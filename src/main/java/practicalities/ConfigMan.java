package practicalities;

import net.minecraftforge.common.config.Configuration;

public class ConfigMan
{
    private static Configuration config;


    public static void init(Configuration configuration)
    {
        config = configuration;
        config.load();
        hydrateConifg();
        config.save();
    }

    public static void hydrateConifg()
    {
    }
}

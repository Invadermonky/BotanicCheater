package com.invadermonky.botaniccheater.handlers;

import com.invadermonky.botaniccheater.BotanicCheater;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(modid = BotanicCheater.MOD_ID)
public class ConfigHandlerBC {
    @Config.RequiresMcRestart
    @Config.Name("Cheaty Endoflame")
    @Config.Comment("Endoflames can be fed solid fuel by the Botanic Cheater.")
    public static boolean cheaterEndoflame = true;

    @Config.RequiresMcRestart
    @Config.Name("Cheaty Entropinnnyum")
    @Config.Comment("Entropinnnyum can be fed TNT by the Botanic Cheater.")
    public static boolean cheaterEntropinnnyum = true;

    @Config.RequiresMcRestart
    @Config.Name("Cheaty Gourmaryllis")
    @Config.Comment("Gourmaryllis can be fed food by the Botanic Cheater.")
    public static boolean cheaterGourmaryllis = true;

    @Config.Ignore
    @Config.RequiresMcRestart
    @Config.Name("Cheaty Spectrolus")
    @Config.Comment("Spectrolus can be fed wool by the Botanic Cheater.")
    public static boolean cheaterSpectrolus = false;

    @Config.RequiresMcRestart
    @Config.Name("Cheaty Thermalily")
    @Config.Comment("Thermalily can be fed lava by the Botanic Cheater.")
    public static boolean cheaterThermalily = true;

    @Config.RangeInt(min = 1, max = 100)
    @Config.Name("Maximum Flower")
    @Config.Comment("The maximum number of flowers that can be fed during each update.")
    public static int maxFlowers = 6;

    @Config.RangeInt(min = 1, max = 24)
    @Config.Name("Search Radius")
    @Config.Comment("The radius the Botanic Cheater will cheater will search for nearby flowers.")
    public static int searchRadius = 4;

    @Config.RangeInt(min = 20, max = 600)
    @Config.Name("Update Interval")
    @Config.Comment("The interval, in ticks, that the Botanic Cheater will search for nearby flowers.")
    public static int updateInterval = 60;

    @Mod.EventBusSubscriber(modid = BotanicCheater.MOD_ID)
    public static class ConfigChangeListener {
        @SubscribeEvent
        public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
            if(event.getModID().equals(BotanicCheater.MOD_ID)) {
                ConfigManager.sync(BotanicCheater.MOD_ID, Config.Type.INSTANCE);
            }
        }
    }
}

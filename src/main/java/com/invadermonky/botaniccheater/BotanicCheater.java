package com.invadermonky.botaniccheater;

import com.invadermonky.botaniccheater.handlers.NetworkHandlerBC;
import com.invadermonky.botaniccheater.handlers.RegistryHandlerBC;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(
        modid = BotanicCheater.MOD_ID,
        name = BotanicCheater.MOD_NAME,
        version = BotanicCheater.MOD_VERSION,
        acceptedMinecraftVersions = BotanicCheater.MC_VERSION,
        dependencies = BotanicCheater.DEPENDENCIES
)
public class BotanicCheater {
    public static final String MOD_ID = Tags.MOD_ID;
    public static final String MOD_NAME = Tags.MOD_NAME;
    public static final String MOD_VERSION = Tags.VERSION;
    public static final String MC_VERSION = "[1.12.2]";
    public static final String DEPENDENCIES = "required-after:botania;required-after:mixinbooter@[10.2,)";

    public static final Logger LOGGER = LogManager.getLogger(Tags.MOD_NAME);

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        NetworkHandlerBC.preInit();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        RegistryHandlerBC.registerLexiconEntry();
    }
}

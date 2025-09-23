package com.invadermonky.botaniccheater.handlers;

import com.invadermonky.botaniccheater.BotanicCheater;
import com.invadermonky.botaniccheater.handlers.packets.PacketFXParticleBeam;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class NetworkHandlerBC {
    public static final SimpleNetworkWrapper INSTANCE;

    public static void preInit() {
        INSTANCE.registerMessage(PacketFXParticleBeam.class, PacketFXParticleBeam.class, 0, Side.CLIENT);
    }

    static {
        INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(BotanicCheater.MOD_ID);
    }
}

package com.invadermonky.botaniccheater.handlers.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import vazkii.botania.common.core.helper.Vector3;
import vazkii.botania.common.item.ItemTwigWand;

public class PacketFXParticleBeam implements IMessage, IMessageHandler<PacketFXParticleBeam, IMessage> {
    public BlockPos origin;
    public BlockPos destination;

    public PacketFXParticleBeam(BlockPos origin, BlockPos destination) {
        this.origin = origin;
        this.destination = destination;
    }

    public PacketFXParticleBeam() {
        this(new BlockPos(0,0,0), new BlockPos(0,0,0));
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.origin = BlockPos.fromLong(buf.readLong());
        this.destination = BlockPos.fromLong(buf.readLong());
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeLong(this.origin.toLong());
        buf.writeLong(this.destination.toLong());
    }

    @Override
    public IMessage onMessage(PacketFXParticleBeam message, MessageContext ctx) {
        Minecraft.getMinecraft().addScheduledTask(() -> {
            Vector3 orig = new Vector3(message.origin.getX() + 0.5f, message.origin.getY() + 0.5f, message.origin.getZ() + 0.5f);
            Vector3 dest = new Vector3(message.destination.getX() + 0.5f, message.destination.getY() + 0.5f, message.destination.getZ() + 0.5f);
            ItemTwigWand.doParticleBeam(Minecraft.getMinecraft().player.world, orig, dest);
        });
        return null;
    }
}

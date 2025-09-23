package com.invadermonky.botaniccheater.core.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import vazkii.botania.common.block.subtile.generating.SubTileEndoflame;

@Mixin(value = SubTileEndoflame.class, remap = false)
public interface SubTileEndoflameAccessor {
    @Accessor(value = "burnTime")
    int getBurnTime();

    @Accessor(value = "burnTime")
    void setBurnTime(int burnTime);
}

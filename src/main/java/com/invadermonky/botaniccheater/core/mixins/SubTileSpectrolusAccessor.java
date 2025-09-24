package com.invadermonky.botaniccheater.core.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import vazkii.botania.common.block.subtile.generating.SubTileSpectrolus;

@Mixin(value = SubTileSpectrolus.class, remap = false)
public interface SubTileSpectrolusAccessor {
    @Accessor(value = "nextColor")
    int getNextColor();

    @Accessor(value = "nextColor")
    void setNextColor(int nextColor);
}

package com.invadermonky.botaniccheater.core.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import vazkii.botania.common.block.subtile.generating.SubTileHydroangeas;

@Mixin(value = SubTileHydroangeas.class, remap = false)
public interface SubTileHydroangeasAccessor {
    @Accessor(value = "burnTime")
    int getBurnTime();

    @Accessor(value = "burnTime")
    void setBurnTime(int burnTime);

    @Accessor(value = "cooldown")
    int getConsumeCooldown();

    @Accessor(value = "cooldown")
    void setConsumeCooldown(int cooldown);
}

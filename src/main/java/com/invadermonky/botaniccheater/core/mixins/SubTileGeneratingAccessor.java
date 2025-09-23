package com.invadermonky.botaniccheater.core.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import vazkii.botania.api.subtile.SubTileGenerating;

@Mixin(value = SubTileGenerating.class, remap = false)
public interface SubTileGeneratingAccessor {
    @Accessor(value = "mana")
    int getMana();

    @Accessor(value = "mana")
    void setMana(int mana);
}

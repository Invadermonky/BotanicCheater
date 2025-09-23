package com.invadermonky.botaniccheater.core.mixins;

import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import vazkii.botania.common.block.subtile.generating.SubTileGourmaryllis;

@Mixin(value = SubTileGourmaryllis.class, remap = false)
public interface SubTileGourmaryllisAccessor {
    @Accessor(value = "cooldown")
    int getCooldown();

    @Accessor(value = "cooldown")
    void setCooldown(int cooldown);

    @Accessor(value = "digestingMana")
    int getDigestingMana();

    @Accessor(value = "digestingMana")
    void setDigestingMana(int digestingMana);

    @Accessor(value = "lastFood")
    ItemStack getLastFood();

    @Accessor(value = "lastFood")
    void setLastFood(ItemStack stack);

    @Accessor(value = "lastFoodCount")
    int getLastFoodCount();

    @Accessor(value = "lastFoodCount")
    void setLastFoodCount(int lastFoodCount);
}

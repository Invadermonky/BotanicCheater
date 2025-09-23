package com.invadermonky.botaniccheater.helpers;

import com.invadermonky.botaniccheater.core.mixins.SubTileEndoflameAccessor;
import com.invadermonky.botaniccheater.core.mixins.SubTileGeneratingAccessor;
import com.invadermonky.botaniccheater.core.mixins.SubTileGourmaryllisAccessor;
import com.invadermonky.botaniccheater.core.mixins.SubTileHydroangeasAccessor;
import net.minecraft.item.ItemStack;
import vazkii.botania.api.subtile.SubTileGenerating;
import vazkii.botania.common.block.subtile.generating.SubTileEndoflame;
import vazkii.botania.common.block.subtile.generating.SubTileGourmaryllis;
import vazkii.botania.common.block.subtile.generating.SubTileThermalily;

public class FlowerHelper {
    public static int getFlowerMana(SubTileGenerating subTile) {
        return ((SubTileGeneratingAccessor) subTile).getMana();
    }

    public static void setFlowerMana(SubTileGenerating subTile, int mana) {
        ((SubTileGeneratingAccessor) subTile).setMana(mana);
    }

    public static int getEndoflameBurnTime(SubTileEndoflame endoflame) {
        return ((SubTileEndoflameAccessor) endoflame).getBurnTime();
    }

    public static void setEndoflameBurnTime(SubTileEndoflame endoflame, int burnTime) {
        ((SubTileEndoflameAccessor) endoflame).setBurnTime(burnTime);
    }

    public static int getGourmaryllisCooldown(SubTileGourmaryllis gourmaryllis) {
        return ((SubTileGourmaryllisAccessor) gourmaryllis).getCooldown();
    }

    public static void setGourmaryllisCooldown(SubTileGourmaryllis gourmaryllis, int cooldown) {
        ((SubTileGourmaryllisAccessor) gourmaryllis).setCooldown(cooldown);
    }

    public static int getGourmaryllisDigestedMana(SubTileGourmaryllis gourmaryllis) {
        return ((SubTileGourmaryllisAccessor) gourmaryllis).getDigestingMana();
    }

    public static void setGourmaryllisDigestedMana(SubTileGourmaryllis gourmaryllis, int digestedMana) {
        ((SubTileGourmaryllisAccessor) gourmaryllis).setDigestingMana(digestedMana);
    }

    public static ItemStack getGourmaryllisLastFood(SubTileGourmaryllis gourmaryllis) {
        return ((SubTileGourmaryllisAccessor) gourmaryllis).getLastFood();
    }

    public static void setGourmaryllisLastFood(SubTileGourmaryllis gourmaryllis, ItemStack lastFood) {
        ((SubTileGourmaryllisAccessor) gourmaryllis).setLastFood(lastFood);
    }

    public static int getGourmaryllisLastFoodCount(SubTileGourmaryllis gourmaryllis) {
        return ((SubTileGourmaryllisAccessor) gourmaryllis).getLastFoodCount();
    }

    public static void setGourmaryllisLastFoodCount(SubTileGourmaryllis gourmaryllis, int lastFoodCount) {
        ((SubTileGourmaryllisAccessor) gourmaryllis).setLastFoodCount(lastFoodCount);
    }

    public static int getThermalilyBurnTime(SubTileThermalily thermalily) {
        return ((SubTileHydroangeasAccessor) thermalily).getBurnTime();
    }

    public static void setThermalilyBurnTime(SubTileThermalily thermalily, int burnTime) {
        ((SubTileHydroangeasAccessor) thermalily).setBurnTime(burnTime);
    }

    public static int getThermalilyCooldown(SubTileThermalily thermalily) {
        return ((SubTileHydroangeasAccessor) thermalily).getConsumeCooldown();
    }

    public static void setThermalilyCooldown(SubTileThermalily thermalily, int cooldown) {
        ((SubTileHydroangeasAccessor) thermalily).setConsumeCooldown(cooldown);
    }

}

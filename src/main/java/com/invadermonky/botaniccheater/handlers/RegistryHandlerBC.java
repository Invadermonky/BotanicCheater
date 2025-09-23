package com.invadermonky.botaniccheater.handlers;

import com.invadermonky.botaniccheater.BotanicCheater;
import com.invadermonky.botaniccheater.block.BlockBotanicCheater;
import com.invadermonky.botaniccheater.block.TileBotanicCheater;
import com.invadermonky.botaniccheater.util.BasicLexiconEntryBC;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.lexicon.LexiconEntry;
import vazkii.botania.common.lexicon.page.PageCraftingRecipe;
import vazkii.botania.common.lexicon.page.PageText;

@SuppressWarnings("ConstantConditions")
@Mod.EventBusSubscriber(modid = BotanicCheater.MOD_ID)
public class RegistryHandlerBC {
    public static Block BOTANIC_CHEATER = new BlockBotanicCheater();
    public static LexiconEntry ENTRY_BOTANIC_CHEATER;

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        event.getRegistry().register(BOTANIC_CHEATER);
        GameRegistry.registerTileEntity(TileBotanicCheater.class, BOTANIC_CHEATER.getRegistryName());
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(new ItemBlock(BOTANIC_CHEATER).setRegistryName(BOTANIC_CHEATER.getRegistryName()));
    }

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        ModelResourceLocation loc = new ModelResourceLocation(BOTANIC_CHEATER.getRegistryName(), "inventory");
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(BOTANIC_CHEATER), 0, loc);
    }

    public static void registerLexiconEntry() {
        ENTRY_BOTANIC_CHEATER = new BasicLexiconEntryBC(BlockBotanicCheater.NAME, BotaniaAPI.categoryDevices);
        ENTRY_BOTANIC_CHEATER.setIcon(new ItemStack(RegistryHandlerBC.BOTANIC_CHEATER));
        ENTRY_BOTANIC_CHEATER.setLexiconPages(
                new PageText("0"),
                new PageText("1"),
                new PageCraftingRecipe("2", RegistryHandlerBC.BOTANIC_CHEATER.getRegistryName())
        );
    }
}

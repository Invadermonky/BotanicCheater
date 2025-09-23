package com.invadermonky.botaniccheater.block;

import com.invadermonky.botaniccheater.BotanicCheater;
import com.invadermonky.botaniccheater.handlers.RegistryHandlerBC;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import vazkii.botania.api.lexicon.ILexiconable;
import vazkii.botania.api.lexicon.LexiconEntry;
import vazkii.botania.common.core.BotaniaCreativeTab;

public class BlockBotanicCheater extends BlockContainer implements ILexiconable {
    public static final String NAME = "botanic_cheater";

    public BlockBotanicCheater() {
        super(Material.ROCK);
        this.setRegistryName(BotanicCheater.MOD_ID, NAME);
        this.setTranslationKey(this.getRegistryName().toString());
        this.setCreativeTab(BotaniaCreativeTab.MISC);
        this.setHardness(2.0f);
        this.setResistance(10.0f);
        this.setSoundType(SoundType.STONE);
        this.setDefaultState(this.blockState.getBaseState());
    }

    @Override
    public boolean onBlockActivated(@NotNull World world, @NotNull BlockPos pos, @NotNull IBlockState state, @NotNull EntityPlayer player, @NotNull EnumHand hand, @NotNull EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack heldStack = player.getHeldItem(hand);
        if(!heldStack.isEmpty() && heldStack.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null)) {
            if (FluidUtil.interactWithFluidHandler(player, hand, world, pos, facing)) {
                TileEntity tile = world.getTileEntity(pos);
                if (tile != null) {
                    tile.markDirty();
                }
            }
            return true;
        }
        return super.onBlockActivated(world, pos, state, player, hand, facing, hitX, hitY, hitZ);
    }

    @Override
    public @Nullable TileEntity createNewTileEntity(@NotNull World worldIn, int meta) {
        return new TileBotanicCheater();
    }

    @Override
    public boolean hasTileEntity(@NotNull IBlockState state) {
        return true;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean hasComparatorInputOverride(@NotNull IBlockState state) {
        return true;
    }

    @SuppressWarnings("deprecation")
    @Override
    public int getComparatorInputOverride(@NotNull IBlockState blockState, World worldIn, @NotNull BlockPos pos) {
        TileEntity tile = worldIn.getTileEntity(pos);
        if(tile instanceof TileBotanicCheater) {
            return ItemHandlerHelper.calcRedstoneFromInventory(((TileBotanicCheater) tile).stackHandler);
        }
        return 0;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean canProvidePower(@NotNull IBlockState state) {
        return false;
    }

    @Override
    public void breakBlock(World worldIn, @NotNull BlockPos pos, @NotNull IBlockState state) {
        if(!worldIn.isRemote) {
            TileEntity tile = worldIn.getTileEntity(pos);
            if(tile instanceof TileBotanicCheater) {
                IItemHandler handler = ((TileBotanicCheater) tile).stackHandler;
                for(int i = 0; i < handler.getSlots(); i++) {
                    ItemStack stack = handler.extractItem(i, handler.getSlotLimit(i), false);
                    if(!stack.isEmpty()) {
                        InventoryHelper.spawnItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), stack);
                    }
                }
            }
        }
        super.breakBlock(worldIn, pos, state);
    }

    @SuppressWarnings("deprecation")
    @SideOnly(Side.CLIENT)
    @Override
    public @NotNull EnumBlockRenderType getRenderType(@NotNull IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    public LexiconEntry getEntry(World world, BlockPos blockPos, EntityPlayer entityPlayer, ItemStack itemStack) {
        return RegistryHandlerBC.ENTRY_BOTANIC_CHEATER;
    }
}

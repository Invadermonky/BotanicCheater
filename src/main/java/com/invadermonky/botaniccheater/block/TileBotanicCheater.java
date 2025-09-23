package com.invadermonky.botaniccheater.block;

import com.invadermonky.botaniccheater.handlers.ConfigHandlerBC;
import com.invadermonky.botaniccheater.handlers.NetworkHandlerBC;
import com.invadermonky.botaniccheater.handlers.packets.PacketFXParticleBeam;
import com.invadermonky.botaniccheater.helpers.FlowerHelper;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import vazkii.botania.api.subtile.SubTileEntity;
import vazkii.botania.common.block.ModBlocks;
import vazkii.botania.common.block.subtile.generating.SubTileEndoflame;
import vazkii.botania.common.block.subtile.generating.SubTileEntropinnyum;
import vazkii.botania.common.block.subtile.generating.SubTileGourmaryllis;
import vazkii.botania.common.block.subtile.generating.SubTileThermalily;
import vazkii.botania.common.block.tile.TileSpecialFlower;
import vazkii.botania.common.core.handler.ModSounds;

public class TileBotanicCheater extends TileEntity implements ITickable {
    private static final int SLOT_FUEL = 0;
    private static final int SLOT_TNT = 1;
    private static final int SLOT_FOOD_1 = 2;
    private static final int SLOT_FOOD_2 = 3;

    public ItemStackHandler stackHandler = new ItemStackHandler(4) {
        @Override
        public @NotNull ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
            return !this.isItemValid(slot, stack) ? stack : super.insertItem(slot, stack, simulate);
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return (slot == SLOT_FUEL && !stack.getItem().hasContainerItem(stack) && TileEntityFurnace.isItemFuel(stack))
                    || ((slot == SLOT_FOOD_1 || slot == SLOT_FOOD_2) && stack.getItem() instanceof ItemFood)
                    || (slot == SLOT_TNT && Block.getBlockFromItem(stack.getItem()) == Blocks.TNT);
        }
    };
    public FluidTank tank = new FluidTank(8000) {
        @Override
        public boolean canFillFluidType(FluidStack fluid) {
            return fluid != null && fluid.getFluid() == FluidRegistry.LAVA;
        }
    };

    public TileBotanicCheater() {
        this.tank.setTileEntity(this);
    }

    @Override
    public void readFromNBT(@NotNull NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.stackHandler.deserializeNBT(compound.getCompoundTag("inv"));
        this.tank = this.tank.readFromNBT(compound.getCompoundTag("tank"));
    }

    @Override
    public @NotNull NBTTagCompound writeToNBT(@NotNull NBTTagCompound compound) {
        compound.setTag("inv", this.stackHandler.serializeNBT());
        compound.setTag("tank", this.tank.writeToNBT(new NBTTagCompound()));
        return super.writeToNBT(compound);
    }

    @Override
    public void update() {
        boolean did = false;
        if(!this.world.isRemote && this.world.getTotalWorldTime() % (long) ConfigHandlerBC.updateInterval == 0) {
            if(this.world.getRedstonePowerFromNeighbors(this.pos) > 0) return;
            int radius = ConfigHandlerBC.searchRadius;
            int flowers = 0;
            int maxFlowers = ConfigHandlerBC.maxFlowers;
            outer:
            for(int x = -radius; x <= radius; x++) {
                for(int y = -radius; y <= radius; y++) {
                    for(int z = -radius; z <= radius; z++) {
                        TileEntity tile = this.world.getTileEntity(this.pos.add(x, y, z));
                        if(tile instanceof TileSpecialFlower) {
                            SubTileEntity subTile = ((TileSpecialFlower) tile).getSubTile();
                            if(subTile instanceof SubTileEndoflame) {
                                if(handleEndoflame((SubTileEndoflame) subTile)) {
                                    flowers++;
                                    did = true;
                                }
                            } else if(subTile instanceof SubTileEntropinnyum) {
                                if(handleEntropinnyum((SubTileEntropinnyum) subTile)) {
                                    flowers++;
                                    did = true;
                                }
                            } else if(subTile instanceof SubTileGourmaryllis) {
                                if(handleGourmaryllis((SubTileGourmaryllis) subTile)) {
                                    flowers++;
                                    did = true;
                                }
                            } else if(subTile instanceof SubTileThermalily) {
                                if(handleThermalily((SubTileThermalily) subTile)) {
                                    flowers++;
                                    did = true;
                                }
                            }
                        }
                        if(flowers >= maxFlowers) {
                            break outer;
                        }
                    }
                }
            }
        }
        if(did) {
            this.markDirty();
        }
    }

    protected boolean handleEndoflame(SubTileEndoflame endoflame) {
        if(!ConfigHandlerBC.cheaterEndoflame) return false;

        if(FlowerHelper.getFlowerMana(endoflame) < endoflame.getMaxMana() && FlowerHelper.getEndoflameBurnTime(endoflame) <= 0) {
            ItemStack stack = this.stackHandler.getStackInSlot(SLOT_FUEL);
            if(!stack.isEmpty() && !stack.getItem().hasContainerItem(stack)) {
                int burnTime = stack.getItem() == Item.getItemFromBlock(ModBlocks.spreader) ? 0 : TileEntityFurnace.getItemBurnTime(stack);
                if(burnTime > 0) {
                    this.stackHandler.extractItem(SLOT_FUEL, 1, false);
                    FlowerHelper.setEndoflameBurnTime(endoflame, burnTime);
                    this.world.playSound(null, endoflame.getPos(), ModSounds.endoflame, SoundCategory.BLOCKS, 0.2F, 1.0F);
                    this.spawnBeam(endoflame.getPos());
                    endoflame.sync();
                    return true;
                }
            }
        }
        return false;
    }

    protected boolean handleEntropinnyum(SubTileEntropinnyum entropinnyum) {
        if(!ConfigHandlerBC.cheaterEntropinnnyum) return false;

        if(FlowerHelper.getFlowerMana(entropinnyum) <= 0) {
                ItemStack stack = this.stackHandler.getStackInSlot(SLOT_TNT);
                if(!stack.isEmpty() && Block.getBlockFromItem(stack.getItem()) == Blocks.TNT) {
                    this.stackHandler.extractItem(SLOT_TNT, 1, false);
                    FlowerHelper.setFlowerMana(entropinnyum, entropinnyum.getMaxMana());
                    this.world.playSound(null, entropinnyum.getPos(), SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.BLOCKS, 0.2F, (1.0F + (this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 0.2F) * 0.7F);
                    this.spawnBeam(entropinnyum.getPos());
                    entropinnyum.sync();
                    return true;
            }
        }
        return false;
    }

    protected boolean handleGourmaryllis(SubTileGourmaryllis gourmaryllis) {
        if(!ConfigHandlerBC.cheaterGourmaryllis) return false;

        if(FlowerHelper.getGourmaryllisCooldown(gourmaryllis) <= 0 && FlowerHelper.getFlowerMana(gourmaryllis) < gourmaryllis.getMaxMana()) {
            int slot = -1;
            ItemStack lastFood = FlowerHelper.getGourmaryllisLastFood(gourmaryllis);
            for(int i = SLOT_FOOD_1; i <= SLOT_FOOD_2; i++) {
                ItemStack stack = this.stackHandler.getStackInSlot(i);
                if(!stack.isEmpty() && stack.getItem() instanceof ItemFood) {
                    if(!ItemHandlerHelper.canItemStacksStack(lastFood, stack)) {
                        slot = i;
                        break;
                    } else if(slot < 0) {
                        slot = i;
                    }
                }
            }
            if(slot >= 0) {
                ItemStack extracted = this.stackHandler.extractItem(slot, 1, false);
                if(extracted.isEmpty() || !(extracted.getItem() instanceof ItemFood)) {
                    return false;
                }
                int lastFoodCount = FlowerHelper.getGourmaryllisLastFoodCount(gourmaryllis);
                if(ItemHandlerHelper.canItemStacksStack(lastFood, extracted)) {
                    FlowerHelper.setGourmaryllisLastFoodCount(gourmaryllis,  lastFoodCount + 1);
                } else {
                    FlowerHelper.setGourmaryllisLastFood(gourmaryllis, extracted.copy());
                    FlowerHelper.setGourmaryllisLastFoodCount(gourmaryllis, 1);
                }

                int val = Math.min(12, ((ItemFood)extracted.getItem()).getHealAmount(extracted));
                int digestingMana = val * val * 70;
                digestingMana = (int)((float) digestingMana * (1.0F / (float) lastFoodCount));
                FlowerHelper.setGourmaryllisDigestedMana(gourmaryllis, digestingMana);
                FlowerHelper.setGourmaryllisCooldown(gourmaryllis, val * 10);
                this.world.playSound(null, gourmaryllis.getPos(), SoundEvents.ENTITY_GENERIC_EAT, SoundCategory.BLOCKS, 0.2F, 0.6F);
                this.spawnBeam(gourmaryllis.getPos());
                gourmaryllis.sync();
                return true;
            }
        }
        return false;
    }

    protected boolean handleThermalily(SubTileThermalily thermalily) {
        if(!ConfigHandlerBC.cheaterThermalily) return false;

        if(this.tank.getFluid() != null && this.tank.getFluid().getFluid() == FluidRegistry.LAVA && this.tank.getFluidAmount() >= 1000) {
            if(FlowerHelper.getThermalilyCooldown(thermalily) <= 0 && FlowerHelper.getFlowerMana(thermalily) < thermalily.getMaxMana()) {
                this.tank.drainInternal(1000, true);
                FlowerHelper.setThermalilyBurnTime(thermalily, thermalily.getBurnTime());
                FlowerHelper.setThermalilyCooldown(thermalily, thermalily.getCooldown());
                thermalily.playSound();
                this.spawnBeam(thermalily.getPos());
                thermalily.sync();
                return true;
            }
        }
        return false;
    }

    protected void spawnBeam(BlockPos destPos) {
        NetworkHandlerBC.INSTANCE.sendToAllAround(
                new PacketFXParticleBeam(this.pos, destPos),
                new NetworkRegistry.TargetPoint(this.world.provider.getDimension(), pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, 32
        ));
    }

    @Override
    public boolean hasCapability(@NotNull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY;
    }

    @Override
    @Nullable
    public <T> T getCapability(@NotNull Capability<T> capability, @Nullable EnumFacing facing) {
        if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(this.stackHandler);
        } else if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(this.tank);
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public boolean shouldRefresh(@NotNull World world, @NotNull BlockPos pos, IBlockState oldState, IBlockState newState) {
        return oldState.getBlock() != newState.getBlock();
    }

    @Override
    public @NotNull NBTTagCompound getUpdateTag() {
        return this.writeToNBT(new NBTTagCompound());
    }

    @Override
    public @Nullable SPacketUpdateTileEntity getUpdatePacket() {
        NBTTagCompound tag = new NBTTagCompound();
        this.writeToNBT(tag);
        return new SPacketUpdateTileEntity(this.pos, -999, tag);
    }

    @Override
    public void onDataPacket(@NotNull NetworkManager net, @NotNull SPacketUpdateTileEntity pkt) {
        super.onDataPacket(net, pkt);
        this.readFromNBT(pkt.getNbtCompound());
    }

    @Override
    public void markDirty() {
        super.markDirty();
        IBlockState state = this.world.getBlockState(this.pos);
        this.world.notifyBlockUpdate(this.pos, state, state, 2);
    }
}

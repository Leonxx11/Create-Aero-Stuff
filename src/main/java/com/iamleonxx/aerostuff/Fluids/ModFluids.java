package com.iamleonxx.aerostuff.Fluids;

import com.iamleonxx.aerostuff.Blocks.ModBlocks;
import com.iamleonxx.aerostuff.aerostuff;
import com.iamleonxx.aerostuff.Items.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FlowingFluid;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class ModFluids {
    private static final String JET_A1_DESCRIPTION = "fluid." + aerostuff.MOD_ID + ".jet_a1";
    private static final String JET_JP7_DESCRIPTION = "fluid." + aerostuff.MOD_ID + ".jet_a1";

    public static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(NeoForgeRegistries.Keys.FLUID_TYPES, aerostuff.MOD_ID);
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(Registries.FLUID, aerostuff.MOD_ID);

    public static final DeferredHolder<FluidType, FluidType> JET_A1_TYPE = FLUID_TYPES.register("jet_a1_type",
            () -> new FluidType(FluidType.Properties.create().descriptionId(JET_A1_DESCRIPTION).density(500).viscosity(1000)));

    public static final DeferredHolder<FluidType, FluidType> JET_JP7_TYPE = FLUID_TYPES.register("jet_jp7_type",
            () -> new FluidType(FluidType.Properties.create().descriptionId(JET_JP7_DESCRIPTION).density(500).viscosity(1000)));

    public static final DeferredHolder<Fluid, BaseFlowingFluid.Source> JET_A1 = FLUIDS.register("jet_a1",
            () -> new ProtectedFlowingFluid.Source(jet_a1Properties()));
    public static final DeferredHolder<Fluid, BaseFlowingFluid.Flowing> FLOWING_JET_A1 = FLUIDS.register("flowing_jet_a1",
            () -> new ProtectedFlowingFluid.Flowing(jet_a1Properties()));

    public static final DeferredHolder<Fluid, BaseFlowingFluid.Source> JET_JP7 = FLUIDS.register("jet_jp7",
            () -> new ProtectedFlowingFluid.Source(jet_jp7Properties()));
    public static final DeferredHolder<Fluid, BaseFlowingFluid.Flowing> FLOWING_JET_JP7 = FLUIDS.register("flowing_jet_jp7",
            () -> new ProtectedFlowingFluid.Flowing(jet_jp7Properties()));

    public static final DeferredBlock<LiquidBlock> JET_A1_BLOCK = ModBlocks.BLOCKS.register("jet_a1",
            () -> new LiquidBlock((FlowingFluid) JET_A1.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER).noLootTable()));

    public static final DeferredBlock<LiquidBlock> JET_JP7_BLOCK = ModBlocks.BLOCKS.register("jet_jp7",
            () -> new LiquidBlock((FlowingFluid) JET_JP7.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER).noLootTable()));

    public static void register(IEventBus modBus) {
        FLUID_TYPES.register(modBus);
        FLUIDS.register(modBus);
    }

    private static BaseFlowingFluid.Properties jet_a1Properties() {
        return new BaseFlowingFluid.Properties(
                JET_A1_TYPE,
                JET_A1,
                FLOWING_JET_A1
        ).bucket(ModItems.JET_A1_BUCKET)
                .block(JET_A1_BLOCK)
                .levelDecreasePerBlock(1)
                .tickRate(7)
                .slopeFindDistance(3)
                .explosionResistance(100f);
    }

    private static BaseFlowingFluid.Properties jet_jp7Properties() {
        return new BaseFlowingFluid.Properties(
                JET_JP7_TYPE,
                JET_JP7,
                FLOWING_JET_JP7
        ).bucket(ModItems.JET_JP7_BUCKET)
                .block(JET_JP7_BLOCK)
                .levelDecreasePerBlock(1)
                .tickRate(7)
                .slopeFindDistance(3)
                .explosionResistance(100f);
    }
}
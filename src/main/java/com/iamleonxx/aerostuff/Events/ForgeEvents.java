package com.iamleonxx.aerostuff.Events;

import com.iamleonxx.aerostuff.aerostuff;
import com.iamleonxx.aerostuff.Fluids.ModFluids;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.BlockEvent;

@SuppressWarnings("removal")
@EventBusSubscriber(modid = aerostuff.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class ForgeEvents {

    // Fluids-lava interaction

    @SubscribeEvent
    public static void onNeighborBlockUpdate(BlockEvent.NeighborNotifyEvent event) {
        LevelAccessor level = event.getLevel();
        BlockPos pos = event.getPos();
        BlockState state = level.getBlockState(pos);

        if (state.getFluidState().isEmpty()) {
            return;
        }

        boolean isJET_A1 = state.getFluidState().is(ModFluids.JET_A1.get());
        boolean isLava = state.getFluidState().is(Fluids.LAVA) || state.getFluidState().is(Fluids.FLOWING_LAVA);

        if (!isJET_A1 && !isLava) {
            return;
        }

        for (Direction dir : Direction.values()) {
            BlockPos neighborPos = pos.relative(dir);
            FluidState neighborFluid = level.getFluidState(neighborPos);

            if (isJET_A1 && (neighborFluid.is(Fluids.LAVA) || neighborFluid.is(Fluids.FLOWING_LAVA))) {
                level.setBlock(pos, Blocks.STONE.defaultBlockState(), 3);
                return;
            }

            if (isLava && neighborFluid.is(ModFluids.JET_A1.get())) {
                level.setBlock(neighborPos, Blocks.STONE.defaultBlockState(), 3);
                return;
            }
        }
        if (state.getFluidState().isEmpty()) {
            return;
        }

        boolean isJET_JP7 = state.getFluidState().is(ModFluids.JET_JP7.get());

        if (!isJET_JP7 && !isLava) {
            return;
        }

        for (Direction dir : Direction.values()) {
            BlockPos neighborPos = pos.relative(dir);
            FluidState neighborFluid = level.getFluidState(neighborPos);

            if (isJET_A1 && (neighborFluid.is(Fluids.LAVA) || neighborFluid.is(Fluids.FLOWING_LAVA))) {
                level.setBlock(pos, Blocks.STONE.defaultBlockState(), 3);
                return;
            }

            if (isLava && neighborFluid.is(ModFluids.JET_JP7.get())) {
                level.setBlock(neighborPos, Blocks.STONE.defaultBlockState(), 3);
                return;
            }
        }
    }
}

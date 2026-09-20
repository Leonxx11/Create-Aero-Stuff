package com.iamleonxx.aerostuff.Events;

import com.iamleonxx.aerostuff.aerostuff;
import com.iamleonxx.aerostuff.Fluids.ModFluids;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.BlockEvent;

@SuppressWarnings("removal")
@EventBusSubscriber(modid = aerostuff.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class ForgeEvents {

    @SubscribeEvent
    public static void onNeighborBlockUpdate(BlockEvent.NeighborNotifyEvent event) {
        LevelAccessor level = event.getLevel();
        BlockPos pos = event.getPos();
        FluidState fluid = level.getFluidState(pos);

        if (fluid.isEmpty()) {
            return;
        }

        boolean isLava =
                fluid.is(Fluids.LAVA) ||
                        fluid.is(Fluids.FLOWING_LAVA);

        boolean isJetA1 =
                fluid.is(ModFluids.JET_A1.get()) ||
                        fluid.is(ModFluids.FLOWING_JET_A1.get());

        boolean isJetJP7 =
                fluid.is(ModFluids.JET_JP7.get()) ||
                        fluid.is(ModFluids.FLOWING_JET_JP7.get());

        if (!isLava && !isJetA1 && !isJetJP7) {
            return;
        }

        for (Direction direction : Direction.values()) {
            BlockPos neighborPos = pos.relative(direction);
            FluidState neighborFluid = level.getFluidState(neighborPos);

            boolean neighborIsLava =
                    neighborFluid.is(Fluids.LAVA) ||
                            neighborFluid.is(Fluids.FLOWING_LAVA);

            boolean neighborIsJetA1 =
                    neighborFluid.is(ModFluids.JET_A1.get()) ||
                            neighborFluid.is(ModFluids.FLOWING_JET_A1.get());

            boolean neighborIsJetJP7 =
                    neighborFluid.is(ModFluids.JET_JP7.get()) ||
                            neighborFluid.is(ModFluids.FLOWING_JET_JP7.get());

            // Jet A-1 / flowing Jet A-1 + lava / flowing lava -> Stone
            if (isJetA1 && neighborIsLava) {
                level.setBlock(pos, Blocks.STONE.defaultBlockState(), 3);
                return;
            }

            if (isLava && neighborIsJetA1) {
                level.setBlock(neighborPos, Blocks.STONE.defaultBlockState(), 3);
                return;
            }

            // Jet JP-7 / flowing Jet JP-7 + lava / flowing lava -> Andesite
            if (isJetJP7 && neighborIsLava) {
                level.setBlock(pos, Blocks.ANDESITE.defaultBlockState(), 3);
                return;
            }

            if (isLava && neighborIsJetJP7) {
                level.setBlock(neighborPos, Blocks.ANDESITE.defaultBlockState(), 3);
                return;
            }
        }
    }
}
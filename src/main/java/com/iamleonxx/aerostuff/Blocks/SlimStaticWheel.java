package com.iamleonxx.aerostuff.Blocks;

import com.mojang.serialization.MapCodec;
import com.simibubi.create.content.equipment.wrench.IWrenchable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;

public class SlimStaticWheel extends HorizontalDirectionalBlock implements IWrenchable {

    public static final MapCodec<SlimStaticWheel> CODEC =
            simpleCodec(SlimStaticWheel::new);

    @Override
    public MapCodec<? extends HorizontalDirectionalBlock> codec() {
        return CODEC;
    }

    // 12px centered thickness (2 → 14)
    private static final VoxelShape SHAPE_NS = Shapes.box(
            2.0D / 16.0D,
            0.0D,
            0.0D,
            14.0D / 16.0D,
            1.0D,
            1.0D
    );

    // rotated for east/west
    private static final VoxelShape SHAPE_EW = Shapes.box(
            0.0D,
            0.0D,
            2.0D / 16.0D,
            1.0D,
            1.0D,
            14.0D / 16.0D
    );

    public SlimStaticWheel(BlockBehaviour.Properties properties) {
        super(properties);

        // default state (important for debug stick + worldgen safety)
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    // REQUIRED: register FACING property
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<net.minecraft.world.level.block.Block, BlockState> builder) {
        builder.add(FACING);
    }

    // 🔥 NEW: placement faces player
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState()
                .setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    private VoxelShape getShapeFor(Direction dir) {
        return switch (dir) {
            case NORTH, SOUTH -> SHAPE_NS;
            case EAST, WEST -> SHAPE_EW;
            default -> SHAPE_NS;
        };
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return getShapeFor(state.getValue(FACING));
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return getShapeFor(state.getValue(FACING));
    }
}
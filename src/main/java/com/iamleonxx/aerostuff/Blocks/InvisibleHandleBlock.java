package com.iamleonxx.aerostuff.Blocks;

import com.mojang.serialization.MapCodec;

import dev.simulated_team.simulated.content.blocks.handle.HandleBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class InvisibleHandleBlock extends HandleBlock {

	public static final MapCodec<InvisibleHandleBlock> CODEC = simpleCodec(InvisibleHandleBlock::new);

	private static final VoxelShape THIN_SHAPE_NORTH = Block.box(0, 0, 15, 16, 16, 16);
	private static final VoxelShape THIN_SHAPE_SOUTH = Block.box(0, 0, 0, 16, 16, 1);
	private static final VoxelShape THIN_SHAPE_WEST  = Block.box(15, 0, 0, 16, 16, 16);
	private static final VoxelShape THIN_SHAPE_EAST  = Block.box(0, 0, 0, 1, 16, 16);
	private static final VoxelShape THIN_SHAPE_UP    = Block.box(0, 0, 0, 16, 1, 16);
	private static final VoxelShape THIN_SHAPE_DOWN  = Block.box(0, 15, 0, 16, 16, 16);

	public InvisibleHandleBlock(Properties properties) {
		super(properties, null, Variant.IRON);
	}

	@Override
	public BlockEntityType getBlockEntityType() {
		return ModBlockEntities.INVISIBLE_HANDLE.get();
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		Direction facing = state.getValue(FACING);
		return switch (facing) {
			case NORTH -> THIN_SHAPE_NORTH;
			case SOUTH -> THIN_SHAPE_SOUTH;
			case WEST  -> THIN_SHAPE_WEST;
			case EAST  -> THIN_SHAPE_EAST;
			case UP    -> THIN_SHAPE_UP;
			case DOWN  -> THIN_SHAPE_DOWN;
		};
	}

	@Override
	protected VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return Shapes.empty();
	}

	@Override
	protected VoxelShape getOcclusionShape(BlockState state, BlockGetter level, BlockPos pos) {
		return Shapes.empty();
	}

	@Override
	protected VoxelShape getVisualShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return Shapes.empty();
	}

	@Override
	protected RenderShape getRenderShape(BlockState state) {
		return RenderShape.INVISIBLE;
	}

	@Override
	protected boolean skipRendering(BlockState state, BlockState adjacentState, Direction direction) {
		return true;
	}

	@Override
	protected float getShadeBrightness(BlockState state, BlockGetter level, BlockPos pos) {
		return 1.0F;
	}

	@Override
	protected boolean propagatesSkylightDown(BlockState state, BlockGetter level, BlockPos pos) {
		return true;
	}

	@Override
	public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
		return true;
	}

	@Override
	public void neighborChanged(BlockState state, Level worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
	}

	@Override
	protected MapCodec<? extends DirectionalBlock> codec() {
		return CODEC;
	}
}
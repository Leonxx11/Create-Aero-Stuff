package com.iamleonxx.aerostuff.Blocks;

import java.util.List;
import java.util.function.Predicate;

import com.simibubi.create.content.decoration.copycat.CopycatPanelBlock;

import net.createmod.catnip.placement.IPlacementHelper;
import net.createmod.catnip.placement.PlacementHelpers;
import net.createmod.catnip.placement.PlacementOffset;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class AntiblastCopycatPanelBlock extends CopycatPanelBlock {

	private static final int PLACEMENT_HELPER_ID = PlacementHelpers.register(new PlacementHelper());

	public AntiblastCopycatPanelBlock(Properties properties) {
		super(properties);
	}

	@Override
	public BlockEntityType getBlockEntityType() {
		return ModBlockEntities.ANTIBLAST_COPYCAT_PANEL.get();
	}

	@Override
	public float getExplosionResistance(BlockState state, BlockGetter level, BlockPos pos, Explosion explosion) {
		return Blocks.OBSIDIAN.getExplosionResistance();
	}

	@Override
	public float getDestroyProgress(BlockState state, Player player, BlockGetter level, BlockPos pos) {
		return Blocks.OBSIDIAN.defaultBlockState().getDestroyProgress(player, level, pos);
	}

	@Override
	public boolean canHarvestBlock(BlockState state, BlockGetter level, BlockPos pos, Player player) {
		return Blocks.OBSIDIAN.defaultBlockState().canHarvestBlock(level, pos, player);
	}

	@Override
	public boolean canEntityDestroy(BlockState state, BlockGetter level, BlockPos pos, Entity entity) {
		return Blocks.OBSIDIAN.defaultBlockState().canEntityDestroy(level, pos, entity);
	}

	@Override
	protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
		if (!player.isShiftKeyDown() && player.mayBuild()) {
			IPlacementHelper placementHelper = PlacementHelpers.get(PLACEMENT_HELPER_ID);
			if (placementHelper.matchesItem(stack)) {
				placementHelper.getOffset(player, level, state, pos, hitResult)
						.placeInWorld(level, (BlockItem) stack.getItem(), player, hand, hitResult);
				return ItemInteractionResult.SUCCESS;
			}
		}

		return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return super.getShape(state, level, pos, context);
	}

	@Override
	public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return Shapes.empty();
	}

	@MethodsReturnNonnullByDefault
	private static class PlacementHelper implements IPlacementHelper {

		@Override
		public Predicate<ItemStack> getItemPredicate() {
			return ModBlocks.ANTIBLAST_COPYCAT_PANEL::isIn;
		}

		@Override
		public Predicate<BlockState> getStatePredicate() {
			return ModBlocks.ANTIBLAST_COPYCAT_PANEL::has;
		}

		@Override
		public PlacementOffset getOffset(Player player, Level world, BlockState state, BlockPos pos,
		                                 BlockHitResult ray) {
			List<Direction> directions = IPlacementHelper.orderedByDistanceExceptAxis(pos, ray.getLocation(),
					state.getValue(FACING)
							.getAxis(),
					dir -> world.getBlockState(pos.relative(dir))
							.canBeReplaced());

			if (directions.isEmpty())
				return PlacementOffset.fail();
			else {
				return PlacementOffset.success(pos.relative(directions.get(0)),
						s -> s.setValue(FACING, state.getValue(FACING)));
			}
		}
	}
}
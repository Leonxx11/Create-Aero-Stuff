package com.iamleonxx.aerostuff.Blocks;

import java.util.function.Predicate;

import org.jetbrains.annotations.NotNull;

import com.simibubi.create.content.decoration.copycat.CopycatStepBlock;
import com.simibubi.create.foundation.placement.PoleHelper;

import dev.simulated_team.simulated.content.blocks.handle.HandleBlock;
import dev.simulated_team.simulated.index.SimClickInteractions;
import net.createmod.catnip.placement.IPlacementHelper;
import net.createmod.catnip.placement.PlacementHelpers;
import net.createmod.catnip.placement.PlacementOffset;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class CopycatGrabHandleBlock extends CopycatStepBlock {

	private static final int PLACEMENT_HELPER_ID = PlacementHelpers.register(new PlacementHelper());

	public CopycatGrabHandleBlock(Properties properties) {
		super(properties);
	}

	@Override
	public BlockEntityType<? extends CopycatGrabHandleBlockEntity> getBlockEntityType() {
		return ModBlockEntities.COPYCAT_GRABHANDLE.get();
	}

	@Override
	public BlockEntityTicker getTicker(Level level, BlockState state, BlockEntityType type) {
		return (lvl, pos, st, be) -> {
			if (be instanceof CopycatGrabHandleBlockEntity handle)
				handle.tick();
		};
	}

	@Override
	protected boolean hasAnalogOutputSignal(BlockState state) {
		return true;
	}

	@Override
	protected int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos) {
		if (level.getBlockEntity(pos) instanceof CopycatGrabHandleBlockEntity be)
			return be.hasPlayer() ? 15 : 0;
		return 0;
	}

	@Override
	protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player,
			InteractionHand hand, BlockHitResult hitResult) {
		if (!player.isShiftKeyDown() && player.mayBuild()) {
			IPlacementHelper helper = PlacementHelpers.get(PLACEMENT_HELPER_ID);
			if (helper.matchesItem(stack))
				return helper.getOffset(player, level, state, pos, hitResult)
						.placeInWorld(level, (BlockItem) stack.getItem(), player, hand, hitResult);
		}

		if (HandleBlock.canInteractWithHandle(player)
				&& getAcceptedBlockState(level, pos, stack, hitResult.getDirection()) == null) {
			if (level.isClientSide && player.isLocalPlayer())
				SimClickInteractions.HANDLE_HANDLER.startHold(level, player, pos);
			return ItemInteractionResult.SUCCESS;
		}

		return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
	}

	private static class PlacementHelper extends PoleHelper<Direction> {

		public PlacementHelper() {
			super(ModBlocks.COPYCAT_GRABHANDLE::has, state -> state.getValue(FACING)
					.getClockWise()
					.getAxis(), FACING);
		}

		@Override
		public @NotNull Predicate<ItemStack> getItemPredicate() {
			return ModBlocks.COPYCAT_GRABHANDLE::isIn;
		}

		@Override
		public @NotNull PlacementOffset getOffset(Player player, Level world, BlockState state, BlockPos pos,
				BlockHitResult ray) {
			PlacementOffset offset = super.getOffset(player, world, state, pos, ray);
			if (offset.isSuccessful())
				offset.withTransform(offset.getTransform()
						.andThen(s -> s.setValue(HALF, state.getValue(HALF))));
			return offset;
		}
	}
}

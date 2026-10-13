package com.iamleonxx.aerostuff.Blocks;

import com.simibubi.create.content.decoration.copycat.CopycatPanelBlock;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class AntiblastCopycatPanelBlock extends CopycatPanelBlock {

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
}

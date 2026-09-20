package com.iamleonxx.aerostuff.Blocks;

import com.copycatsplus.copycats.foundation.copycat.model.CopycatModelCore;
import com.copycatsplus.copycats.foundation.copycat.model.assembly.CopycatRenderContext;
import net.createmod.catnip.data.Iterate;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.copycatsplus.copycats.foundation.copycat.model.assembly.CopycatRenderContext.*;
import static com.copycatsplus.copycats.foundation.copycat.model.assembly.MutableCullFace.*;
import static com.iamleonxx.aerostuff.Blocks.CopycatSheetBlock.byDirection;

public class CopycatSheetModelCore extends CopycatModelCore {

    private static final double T = 16.0 / 64.0;

    private static int i(boolean b) {
        return b ? 1 : 0;
    }

    @Override
    public void registerModels(List<ModelEntry> entries) {
        registerForMultiState(entries, ModBlocks.COPYCAT_SHEET.get(), false);
    }

    @Override
    public void emitCopycatQuads(String key, BlockState state, CopycatRenderContext context, BlockState material) {
        Map<Direction, Boolean> sides = new HashMap<>();
        for (Direction direction : Iterate.directions) {
            sides.put(direction, state.getValue(byDirection(direction)));
        }

        Direction direction = Direction.byName(key.toLowerCase(Locale.ROOT));
        if (direction == null || !sides.get(direction)) return;

        if (direction.getAxis().isVertical()) {
            context.assemblePiece(
                    t -> t.flipY(direction == Direction.UP),
                    vec3(0, 0, 0),
                    aabb(16, T, 16),
                    cull((NORTH * i(sides.get(Direction.NORTH))) |
                            (SOUTH * i(sides.get(Direction.SOUTH))) |
                            (EAST * i(sides.get(Direction.EAST))) |
                            (WEST * i(sides.get(Direction.WEST)))
                    ));
        } else {
            Direction right = direction.getClockWise();
            Direction left = direction.getCounterClockWise();
            context.assemblePiece(
                    t -> t.rotateY((int) direction.toYRot() + 180),
                    vec3(0, 0, 0),
                    aabb(16, 16, T),
                    cull((UP * i(sides.get(Direction.UP))) |
                            (DOWN * i(sides.get(Direction.DOWN))) |
                            (EAST * i(sides.get(right))) |
                            (WEST * i(sides.get(left)))
                    ));
        }
    }
}
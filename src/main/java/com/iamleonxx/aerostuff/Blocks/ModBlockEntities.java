package com.iamleonxx.aerostuff.Blocks;

import com.copycatsplus.copycats.foundation.copycat.multistate.MultiStateCopycatBlockEntity;
import com.iamleonxx.aerostuff.aerostuff;
import com.tterrag.registrate.util.entry.BlockEntityEntry;

public class ModBlockEntities {

    public static final BlockEntityEntry<MultiStateCopycatBlockEntity> COPYCAT_SHEET =
            aerostuff.REGISTRATE
                    .blockEntity(
                            "copycat_sheet",
                            MultiStateCopycatBlockEntity::new
                    )
                    .validBlocks(ModBlocks.COPYCAT_SHEET)
                    .register();

    public static void register() {
    }
}
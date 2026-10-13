package com.iamleonxx.aerostuff.Blocks;

import com.copycatsplus.copycats.foundation.copycat.multistate.MultiStateCopycatBlockEntity;
import com.iamleonxx.aerostuff.aerostuff;
import com.simibubi.create.content.decoration.copycat.CopycatBlockEntity;
import com.tterrag.registrate.util.entry.BlockEntityEntry;

import dev.simulated_team.simulated.content.blocks.handle.HandleBlockEntity;

public class ModBlockEntities {

	public static final BlockEntityEntry<MultiStateCopycatBlockEntity> COPYCAT_SHEET =
			aerostuff.REGISTRATE
					.blockEntity("copycat_sheet", MultiStateCopycatBlockEntity::new)
					.validBlocks(ModBlocks.COPYCAT_SHEET)
					.register();

	public static final BlockEntityEntry<HandleBlockEntity> INVISIBLE_HANDLE =
			aerostuff.REGISTRATE
					.blockEntity("invisible_handle", HandleBlockEntity::new)
					.validBlocks(ModBlocks.INVISIBLE_HANDLE)
					.register();

	public static final BlockEntityEntry<CopycatBlockEntity> ANTIBLAST_COPYCAT_PANEL =
			aerostuff.REGISTRATE
					.blockEntity("antiblast_copycat_panel", CopycatBlockEntity::new)
					.validBlocks(ModBlocks.ANTIBLAST_COPYCAT_PANEL)
					.register();

	public static void register() {
	}
}

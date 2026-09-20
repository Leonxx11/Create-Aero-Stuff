package com.iamleonxx.aerostuff.Blocks;

import com.copycatsplus.copycats.CCBuilderTransformers;
import com.copycatsplus.copycats.CCCustomModels;
import com.copycatsplus.copycats.datagen.CCLootGen;
import com.copycatsplus.copycats.foundation.tooltip.CopycatCharacteristics;
import com.copycatsplus.copycats.foundation.tooltip.CopycatDescription;
import com.iamleonxx.aerostuff.Items.ModItems;
import com.iamleonxx.aerostuff.aerostuff;
import com.simibubi.create.foundation.item.TooltipModifier;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.nullness.NonNullConsumer;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

import static com.simibubi.create.foundation.data.ModelGen.customItemModel;

public class ModBlocks {

    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(aerostuff.MOD_ID);

    public static final BlockEntry<CopycatSheetBlock> COPYCAT_SHEET =
            aerostuff.REGISTRATE.block(
                            "copycat_sheet",
                            CopycatSheetBlock::new
                    )
                    .transform(CCBuilderTransformers.multiCopycat())
                    .onRegister(CCCustomModels.createBlockModel(
                            CopycatSheetModelCore::new
                    ))
                    .loot(CCLootGen.build(
                            CCLootGen.lootForDirections()
                    ))
                    .item()
                    .onRegister(CopycatDescription.register(
                            CopycatCharacteristics.COPYCAT,
                            CopycatCharacteristics.CT_TOGGLE,
                            CopycatCharacteristics.MULTI_STATE
                    ))
                    .onRegister(item -> TooltipModifier.REGISTRY.register(
                            item,
                            CopycatDescription.create(item)
                    ))
                    .transform(customItemModel("copycat_base", "sheet"))
                    .register();

    public static final DeferredBlock<Block> STATIC_WHEEL = registerBlock(
            "static_wheel",
            () -> new StaticWheel(
                    BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK)
            )
    );

    public static final DeferredBlock<Block> SLIM_STATIC_WHEEL = registerBlock(
            "slim_static_wheel",
            () -> new SlimStaticWheel(
                    BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK)
            )
    );

    private static <T extends Block> NonNullConsumer<T> onClient(
            Supplier<NonNullConsumer<T>> supplier
    ) {
        return FMLEnvironment.dist == Dist.CLIENT
                ? supplier.get()
                : b -> {};
    }

    private static <T extends Block> DeferredBlock<T> registerBlock(
            String name,
            Supplier<T> block
    ) {
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(
            String name,
            DeferredBlock<T> block
    ) {
        ModItems.ITEMS.register(
                name,
                () -> new BlockItem(
                        block.get(),
                        new Item.Properties()
                )
        );
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
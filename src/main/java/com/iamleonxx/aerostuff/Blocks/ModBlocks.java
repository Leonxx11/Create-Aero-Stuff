package com.iamleonxx.aerostuff.Blocks;

import com.iamleonxx.aerostuff.aerostuff;
import com.iamleonxx.aerostuff.Items.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(aerostuff.MOD_ID);

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

    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block) {
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
package com.iamleonxx.aerostuff.Items;

import com.iamleonxx.aerostuff.Fluids.ModFluids;
import com.iamleonxx.aerostuff.aerostuff;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(aerostuff.MOD_ID);

    public static final DeferredHolder<Item, Item> JET_A1_BUCKET =
            ITEMS.register("jet_a1_bucket", () -> new net.minecraft.world.item.BucketItem(ModFluids.JET_A1.get(), new Item.Properties().craftRemainder(net.minecraft.world.item.Items.BUCKET).stacksTo(1)));

    public static final DeferredHolder<Item, Item> JET_JP7_BUCKET =
            ITEMS.register("jet_jp7_bucket", () -> new net.minecraft.world.item.BucketItem(ModFluids.JET_JP7.get(), new Item.Properties().craftRemainder(net.minecraft.world.item.Items.BUCKET).stacksTo(1)));

    public static void register(IEventBus modBus) {
        ITEMS.register(modBus);
    }
}
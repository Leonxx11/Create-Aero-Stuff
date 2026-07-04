package com.iamleonxx.aerostuff;

import com.iamleonxx.aerostuff.Items.ModItems;
import com.iamleonxx.aerostuff.Blocks.ModBlocks;
import dev.simulated_team.simulated.registrate.SimulatedRegistrate;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import java.util.function.Supplier;

public class AeroStuffCreativeTab {
    private static final ResourceLocation MAIN_SECTION = ResourceLocation.fromNamespaceAndPath(aerostuff.MOD_ID, "aerostuff_main");
    private static boolean sectionsInitialized = false;

    public static synchronized void registerAeronauticsSections() {
        if (sectionsInitialized) {
            return;
        }

        registerSectionItem(MAIN_SECTION, "jet_a1_bucket", () -> ModItems.JET_A1_BUCKET.get());
        registerSectionItem(MAIN_SECTION, "jet_jp7_bucket", () -> ModItems.JET_JP7_BUCKET.get());
        registerSectionItem(MAIN_SECTION, "static_wheel", () -> ModBlocks.STATIC_WHEEL.get().asItem());
        registerSectionItem(MAIN_SECTION, "slim_static_wheel", () -> ModBlocks.SLIM_STATIC_WHEEL.get().asItem());

        sectionsInitialized = true;
    }

    private static void registerSectionItem(ResourceLocation sectionId, String itemPath, Supplier<Item> itemSupplier) {
        SimulatedRegistrate.TAB_ITEMS.add(itemSupplier);
        SimulatedRegistrate.ITEM_TO_SECTION.put(ResourceLocation.fromNamespaceAndPath(aerostuff.MOD_ID, itemPath), sectionId);
    }
}
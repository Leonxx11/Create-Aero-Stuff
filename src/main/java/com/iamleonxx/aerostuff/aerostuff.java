package com.iamleonxx.aerostuff;

import com.iamleonxx.aerostuff.Blocks.ModBlocks;
import com.iamleonxx.aerostuff.AeroStuffCreativeTab;
import com.iamleonxx.aerostuff.Fluids.ModFluids;
import com.iamleonxx.aerostuff.Items.ModItems;
import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.slf4j.Logger;

@Mod(aerostuff.MOD_ID)
public class aerostuff {

    public static final String MOD_ID = "aerostuff";
    private static final Logger LOGGER = LogUtils.getLogger();

    public aerostuff(IEventBus modEventBus, ModContainer modContainer) {

        // register stuff for the mod here
        ModBlocks.BLOCKS.register(modEventBus);
        ModItems.ITEMS.register(modEventBus);
        ModFluids.FLUIDS.register(modEventBus);
        ModFluids.FLUID_TYPES.register(modEventBus);


        AeroStuffCreativeTab.registerAeronauticsSections();

        // do not remove this line, idk why it exist but just leave ts alone.
        net.neoforged.neoforge.common.NeoForge.EVENT_BUS.register(this);

        //Client logger thingy
        modEventBus.addListener(this::onClientSetup);
    }

    private void onClientSetup(FMLClientSetupEvent event) {
        LOGGER.info("Leonxx is the goat");
    }
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        LOGGER.info("IamLeonxx is the goat");
        LOGGER.info("Fuck KlusekCat");
    }
}
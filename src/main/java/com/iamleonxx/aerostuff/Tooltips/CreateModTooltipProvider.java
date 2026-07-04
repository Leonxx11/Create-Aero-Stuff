package com.iamleonxx.aerostuff.Tooltips;

import com.simibubi.create.foundation.item.TooltipHelper;
import com.iamleonxx.aerostuff.aerostuff;
import net.createmod.catnip.lang.FontHelper.Palette;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

import java.util.List;

public final class CreateModTooltipProvider implements ITooltipProvider {
    @Override
    public void addText(final ItemTooltipEvent event, final List<Component> tooltipList) {
        final Item item = event.getItemStack().getItem();
        final ResourceLocation id = BuiltInRegistries.ITEM.getKey(item);
        if (id == null || !aerostuff.MOD_ID.equals(id.getNamespace())) {
            return;
        }

        final String path = aerostuff.MOD_ID + "." + id.getPath();

        // Priority: Specific item tooltip -> fallback Create logic -> generic shared summary
        String summaryKey = "item." + aerostuff.MOD_ID + "." + id.getPath() + ".tooltip.summary";
        if (!I18n.exists(summaryKey)) {
            summaryKey = path + ".tooltip.summary";
            if (!I18n.exists(summaryKey)) {
                summaryKey = aerostuff.MOD_ID + ".tooltip.shared." + id.getPath() + "_summary";
                if (!I18n.exists(summaryKey)) {
                    return;
                }
            }
        }

        final String finalSummaryKey = summaryKey;
        TooltipHandler.wrapShiftHoldText(tooltipList, "create.tooltip.holdForDescription", () -> {
            tooltipList.addAll(TooltipHelper.cutStringTextComponent(
                    Component.translatable(finalSummaryKey).getString(),
                    Palette.STANDARD_CREATE
            ));
        });
    }
}
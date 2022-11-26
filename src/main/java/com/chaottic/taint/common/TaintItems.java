package com.chaottic.taint.common;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;

public final class TaintItems {
    public static final Item TAINTED_LOG = new BlockItem(TaintBlocks.TAINTED_LOG, createProperties());
    public static final Item TAINT_GEYSER = new BlockItem(TaintBlocks.TAINT_GEYSER, createProperties());
    public static final Item TAINT_FIBRE = new BlockItem(TaintBlocks.TAINT_FIBRE, createProperties());

    private TaintItems() {}

    public static void register(Registrant<Item> registrant) {
        registrant.register("tainted_log", TAINTED_LOG);
        registrant.register("taint_geyser", TAINT_GEYSER);
        registrant.register("taint_fibre", TAINT_FIBRE);
    }

    private static Item.Properties createProperties() {
        return new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS);
    }
}

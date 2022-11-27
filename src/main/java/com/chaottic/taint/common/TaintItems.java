package com.chaottic.taint.common;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;

public final class TaintItems {
    public static final Item TAINTED_SOIL = new BlockItem(TaintBlocks.TAINTED_SOIL, createProperties());
    public static final Item TAINTED_LOG = new BlockItem(TaintBlocks.TAINTED_LOG, createProperties());
    public static final Item TAINT_GEYSER = new BlockItem(TaintBlocks.TAINT_GEYSER, createProperties());
    public static final Item TAINT_CRUST = new BlockItem(TaintBlocks.TAINT_CRUST, createProperties());
    public static final Item TAINT_FIBRE = new BlockItem(TaintBlocks.TAINT_FIBRE, createProperties());
    public static final Item TAINT_FAN = new BlockItem(TaintBlocks.TAINT_FAN, createProperties());

    private TaintItems() {}

    public static void register(Registrant<Item> registrant) {
        registrant.register("tainted_soil", TAINTED_SOIL);
        registrant.register("tainted_log", TAINTED_LOG);
        registrant.register("taint_geyser", TAINT_GEYSER);
        registrant.register("taint_crust", TAINT_CRUST);
        registrant.register("taint_fibre", TAINT_FIBRE);
        registrant.register("taint_fan", TAINT_FAN);
    }

    private static Item.Properties createProperties() {
        return new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS);
    }
}

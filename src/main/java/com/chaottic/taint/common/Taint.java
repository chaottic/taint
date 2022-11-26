package com.chaottic.taint.common;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;

public final class Taint implements ModInitializer {
    private static final String MOD_ID = "taint";

    @Override
    public void onInitialize(ModContainer mod) {
        TaintBlocks.register((name, block) -> Registry.register(Registry.BLOCK, createResourceLocation(name), block));
        TaintItems.register((name, item) -> Registry.register(Registry.ITEM, createResourceLocation(name), item));
    }

    public static ResourceLocation createResourceLocation(String path) {
        return new ResourceLocation(MOD_ID, path);
    }
}

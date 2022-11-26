package com.chaottic.taint.client;

import com.chaottic.taint.common.TaintBlocks;
import net.minecraft.client.renderer.RenderType;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;
import org.quiltmc.qsl.block.extensions.api.client.BlockRenderLayerMap;

public final class TaintClient implements ClientModInitializer {

    @Override
    public void onInitializeClient(ModContainer mod) {
        BlockRenderLayerMap.put(RenderType.cutout(), TaintBlocks.TAINT_FIBRE);
    }
}

package com.chaottic.taint.common;

import net.minecraft.core.Registry;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public final class TaintBlockTags {
    public static final TagKey<Block> TAINTED_BLOCKS = TagKey.create(Registry.BLOCK_REGISTRY, Taint.createResourceLocation("tainted_blocks"));

    private TaintBlockTags() {}
}

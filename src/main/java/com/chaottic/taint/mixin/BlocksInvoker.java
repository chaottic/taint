package com.chaottic.taint.mixin;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.material.MaterialColor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Blocks.class)
public interface BlocksInvoker {

    @Invoker("log")
    static RotatedPillarBlock log(MaterialColor top, MaterialColor side) {
        throw new AssertionError();
    }
}

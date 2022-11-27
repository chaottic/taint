package com.chaottic.taint.mixin;

import com.chaottic.taint.common.TaintBlockTags;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.ColorResolver;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BiomeColors.class)
public class BiomeColorsMixin {
    private static final int BIT_MASK = 0xFF;

    @Inject(method = "getAverageColor", at = @At("RETURN"), cancellable = true)
    private static void getAverageColor(BlockAndTintGetter blockAndTintGetter, BlockPos blockPos, ColorResolver colorResolver, CallbackInfoReturnable<Integer> cir) {
        var r = 4;

        var mutableBlockPos = new BlockPos.MutableBlockPos();

        int i = 0;
        for (int x = -r; x < r; x++) {
            for (int y = -r; y < r; y++) {
                for (int z = -r; z < r; z++) {

                    if (x * x + y * y + z * z < r * r && blockAndTintGetter.getBlockState(mutableBlockPos.setWithOffset(blockPos, x, y, z)).is(TaintBlockTags.TAINTED_BLOCKS)) {
                        i++;
                    }
                }
            }
        }

        cir.setReturnValue(lerp(cir.getReturnValueI(), 0x7930A0, Math.min(0.0F + 0.025F * i, 1.0F)));
    }

    private static int lerp(int i, int j, float f) {
        int r = (i >> 16) & BIT_MASK;
        int g = (i >> 8) & BIT_MASK;
        int b = i & BIT_MASK;

        r = (int) Mth.lerp(f, r, (j >> 16) & BIT_MASK);
        g = (int) Mth.lerp(f, g, (j >> 8) & BIT_MASK);
        b = (int) Mth.lerp(f, b, j & BIT_MASK);

        return ((r & BIT_MASK) << 16) | ((g & BIT_MASK) << 8) | (b & BIT_MASK);
    }
}

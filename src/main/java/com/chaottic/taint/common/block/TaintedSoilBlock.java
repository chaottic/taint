package com.chaottic.taint.common.block;

import com.chaottic.taint.common.TaintBlockTags;
import com.chaottic.taint.common.TaintBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.MultifaceBlock;
import net.minecraft.world.level.block.state.BlockState;

@SuppressWarnings("deprecation")
public final class TaintedSoilBlock extends Block {

    public TaintedSoilBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void randomTick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
        spread(blockPos, randomSource, serverLevel);
    }

    public static void spread(BlockPos blockPos, RandomSource randomSource, ServerLevel serverLevel) {
        var r = 4;

        for (BlockPos pos : BlockPos.betweenClosed(blockPos.offset(-r, -r, -r), blockPos.offset(r, r, r))) {
            if (randomSource.nextInt(25) == 0) {
                // TODO
            }
        }
    }
}

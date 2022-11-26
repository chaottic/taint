package com.chaottic.taint.common.block;

import com.chaottic.taint.common.TaintBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.MultifaceBlock;
import net.minecraft.world.level.block.MultifaceSpreader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.Material;

import java.util.Map;

@SuppressWarnings("deprecation")
public final class TaintFibreBlock extends MultifaceBlock {
    private final MultifaceSpreader spreader = new MultifaceSpreader(new MultifaceSpreader.DefaultSpreaderConfig(this) {

        @Override
        protected boolean stateCanBeReplaced(BlockGetter blockGetter, BlockPos blockPos, BlockPos blockPos2, Direction direction, BlockState blockState) {
            return blockState.isAir() || blockState.is(block) || blockState.getMaterial() == Material.REPLACEABLE_PLANT;
        }
    });

    public TaintFibreBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void randomTick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {

        if (randomSource.nextInt(25) == 0) {
            spreader.spreadFromRandomFaceTowardRandomDirection(blockState, serverLevel, blockPos, randomSource);
        }

        if (randomSource.nextInt(25) == 0) {
            for (Direction direction : availableFaces(blockState)) {
                var neighbour = serverLevel.getBlockState(blockPos = blockPos.relative(direction));

                Block tainted = null;
                if (neighbour.is(BlockTags.LOGS)) {
                    tainted = getLogOrGeyser(blockPos, serverLevel, randomSource);
                }

                if (tainted != null) {
                    serverLevel.setBlock(blockPos, copyValues(tainted.defaultBlockState(), neighbour), Block.UPDATE_ALL);
                }
            }
        }
    }

    @Override
    public boolean canBeReplaced(BlockState blockState, BlockPlaceContext blockPlaceContext) {
        return false;
    }

    @Override
    public MultifaceSpreader getSpreader() {
        return spreader;
    }

    private static Block getLogOrGeyser(BlockPos blockPos, BlockGetter blockGetter, RandomSource randomSource) {
        var values = blockGetter.getBlockState(blockPos).getValues();

        return randomSource.nextInt(25) == 0 && values.containsKey(BlockStateProperties.AXIS) && BlockStateProperties.AXIS.getValueClass().cast(values.get(BlockStateProperties.AXIS)) == Direction.Axis.Y && isAirOrLeaves(blockGetter.getBlockState(blockPos.above())) ? TaintBlocks.TAINT_GEYSER : TaintBlocks.TAINTED_LOG;
    }

    private static boolean isAirOrLeaves(BlockState blockState) {
        return blockState.isAir() || blockState.is(BlockTags.LEAVES);
    }

    @SuppressWarnings("unchecked")
    private static <T extends Comparable<T>, V extends T> BlockState copyValues(BlockState blockState, BlockState b) {
        if (blockState.getProperties().containsAll(b.getProperties())) {
            for (Map.Entry<Property<?>, Comparable<?>> entry : b.getValues().entrySet()) {
                blockState = blockState.setValue((Property<T>) entry.getKey(), (V) entry.getValue());
            }
        }

        return blockState;
    }
}


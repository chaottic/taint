package com.chaottic.taint.common.block;

import com.chaottic.taint.common.TaintBlockTags;
import com.chaottic.taint.common.TaintBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
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
            return canReplace(blockState) || blockState.is(block);
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
                var blockPos1 = blockPos.relative(direction);

                var blockState1 = serverLevel.getBlockState(blockPos1);
                if (blockState1.is(TaintBlockTags.TAINTED_BLOCKS)) {
                    continue;
                }

                Block result = null;

                if (blockState1.is(BlockTags.DIRT)) {
                    result = TaintBlocks.TAINTED_SOIL;
                } else if (blockState1.is(BlockTags.LOGS)) {
                    result = getLogOrGeyser(blockPos1, serverLevel, randomSource);
                } else if (blockState1.is(BlockTags.LEAVES)) {
                    result = Blocks.AIR;
                } else if (blockState1.getMaterial() == Material.WOOD) {
                    result = TaintBlocks.TAINT_CRUST;
                }

                if (result != null) {
                    serverLevel.setBlock(blockPos1, copyValues(result.defaultBlockState(), blockState1), Block.UPDATE_ALL);
                }
            }
        }
    }

    @Override
    public BlockState updateShape(BlockState blockState, Direction direction, BlockState blockState2, LevelAccessor levelAccessor, BlockPos blockPos, BlockPos blockPos2) {
        if (!canSurvive(blockState, levelAccessor, blockPos)) {
            return Blocks.AIR.defaultBlockState();
        }
        return super.updateShape(blockState, direction, blockState2, levelAccessor, blockPos, blockPos2);
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

    public static boolean canReplace(BlockState blockState) {
        return blockState.isAir() || blockState.getMaterial() == Material.REPLACEABLE_PLANT;
    }
}


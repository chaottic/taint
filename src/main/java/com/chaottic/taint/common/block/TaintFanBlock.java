package com.chaottic.taint.common.block;

import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;

public final class TaintFanBlock extends Block {
    private static final EnumProperty<Part> PART = EnumProperty.create("part", Part.class);

    public TaintFanBlock(Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState().setValue(PART, Part.MIDDLE));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(PART);
    }

    private enum Part implements StringRepresentable {
        MIDDLE("middle"),
        MIDDLE_BOTTOM("middle_bottom"),
        RIGHT("right"),
        RIGHT_BOTTOM("right_bottom"),
        LEFT("left"),
        LEFT_BOTTOM("left_bottom");

        private final String serializedName;

        Part(String serializedName) {
            this.serializedName = serializedName;
        }

        @Override
        public String getSerializedName() {
            return serializedName;
        }
    }
}

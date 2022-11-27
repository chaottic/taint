package com.chaottic.taint.common;

import com.chaottic.taint.common.block.TaintFanBlock;
import com.chaottic.taint.common.block.TaintFibreBlock;
import com.chaottic.taint.common.block.TaintedSoilBlock;
import com.chaottic.taint.mixin.BlocksInvoker;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.MaterialColor;

public final class TaintBlocks {
    public static final Block TAINTED_SOIL = new TaintedSoilBlock(Properties.copy(Blocks.DIRT).randomTicks());
    public static final Block TAINTED_LOG = BlocksInvoker.log(MaterialColor.PODZOL, MaterialColor.COLOR_BLACK);
    public static final Block TAINT_GEYSER = new Block(Properties.copy(Blocks.OAK_PLANKS));
    public static final Block TAINT_CRUST = new Block(Properties.copy(Blocks.OAK_PLANKS));
    public static final Block TAINT_FIBRE = new TaintFibreBlock(Properties.copy(Blocks.DIRT).noOcclusion().noCollission().randomTicks());
    public static final Block TAINT_FAN = new TaintFanBlock(Properties.copy(Blocks.OAK_PLANKS).noOcclusion().noCollission());

    private TaintBlocks() {}

    public static void register(Registrant<Block> registrant) {
        registrant.register("tainted_soil", TAINTED_SOIL);
        registrant.register("tainted_log", TAINTED_LOG);
        registrant.register("taint_geyser", TAINT_GEYSER);
        registrant.register("taint_crust", TAINT_CRUST);
        registrant.register("taint_fibre", TAINT_FIBRE);
        registrant.register("taint_fan", TAINT_FAN);
    }
}

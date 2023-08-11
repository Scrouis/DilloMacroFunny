package com.dillo.main.scan;

import net.minecraft.util.BlockPos;

import java.util.function.Predicate;

public enum CrystalHollowQuarter {
    // expand by 4 chunks to make sure we don't miss any structures
    JUNGLE(blockPos -> blockPos.getX() <= 576 && blockPos.getZ() <= 576),
    PRECURSOR_REMNANTS(blockPos -> blockPos.getX() > 448 && blockPos.getZ() > 448),
    GOBLIN_HOLDOUT(blockPos -> blockPos.getX() <= 576 && blockPos.getZ() > 448),
    MITHRIL_DEPOSITS(blockPos -> blockPos.getX() > 448 && blockPos.getZ() <= 576),
    MAGMA_FIELDS(blockPos -> blockPos.getY() < 100),
    ANY(blockPos -> true);

    private final Predicate<? super BlockPos> predicate;

    CrystalHollowQuarter(Predicate<? super BlockPos> predicate) {
        this.predicate = predicate;
    }

    public boolean testPredicate(BlockPos blockPos) {
        return predicate.test(blockPos);
    }
}

package com.sorrowmist.useless.mixin.mek;

import mekanism.api.math.FloatingLong;
import mekanism.api.math.MathUtils;
import mekanism.common.tile.interfaces.IUpgradeTile;
import mekanism.common.util.MekanismUtils;
import com.sorrowmist.useless.utils.MekUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Mixin for MekanismUtils to modify upgrade calculations
 */
@Mixin(value = MekanismUtils.class, remap = false)
public class MekanismUtilsMixin {

    @Inject(method = "getTicks", at = @At("RETURN"), cancellable = true)
    private static void modifyTicks(IUpgradeTile tile, int def, CallbackInfoReturnable<Double> cir) {
        if (tile.supportsUpgrades()) {
            cir.setReturnValue(cir.getReturnValue() * MekUtils.time(tile));
        }
    }

    @Inject(method = "getEnergyPerTick", at = @At("RETURN"), cancellable = true)
    private static void modifyEnergyPerTick(IUpgradeTile tile, FloatingLong def, CallbackInfoReturnable<FloatingLong> cir) {
        if (tile.supportsUpgrades()) {
            cir.setReturnValue(
                FloatingLong.create(
                    MekUtils.ceilToLong(cir.getReturnValue().doubleValue() * MekUtils.electricity(tile))
                )
            );
        }
    }

    @Inject(method = "getMaxEnergy", at = @At("RETURN"), cancellable = true)
    private static void modifyMaxEnergy(IUpgradeTile tile, FloatingLong def, CallbackInfoReturnable<FloatingLong> cir) {
        if (tile.supportsUpgrades()) {
            cir.setReturnValue(
                FloatingLong.create(
                    MekUtils.ceilToLong(cir.getReturnValue().doubleValue() * MekUtils.capacity(tile))
                )
            );
        }
    }
}

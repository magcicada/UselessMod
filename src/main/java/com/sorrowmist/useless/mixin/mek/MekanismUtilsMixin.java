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

    @Inject(method = "getTicks", at = @At("HEAD"), cancellable = true)
    private static void onGetTicks(IUpgradeTile tile, int def, CallbackInfoReturnable<Integer> cir) {
        if (tile.supportsUpgrades()) {
            double d = (double) def * MekUtils.time(tile);
            int result = d >= 1.0 ? MathUtils.clampToInt(d) : MathUtils.clampToInt(1.0 / d) * -1;
            cir.setReturnValue(result);
        } else {
            cir.setReturnValue(def);
        }
    }

    @Inject(method = "getEnergyPerTick", at = @At("HEAD"), cancellable = true)
    private static void onGetEnergyPerTick(IUpgradeTile tile, FloatingLong def, CallbackInfoReturnable<FloatingLong> cir) {
        FloatingLong result = tile.supportsUpgrades() ? def.multiply(MekUtils.electricity(tile)) : def;
        cir.setReturnValue(result);
    }

    @Inject(method = "getMaxEnergy", at = @At("HEAD"), cancellable = true)
    private static void onGetMaxEnergy(IUpgradeTile tile, FloatingLong def, CallbackInfoReturnable<FloatingLong> cir) {
        FloatingLong result = tile.supportsUpgrades() ? def.multiply(MekUtils.capacity(tile)) : def;
        cir.setReturnValue(result);
    }
}

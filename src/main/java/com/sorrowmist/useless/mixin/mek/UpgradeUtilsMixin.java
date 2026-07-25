package com.sorrowmist.useless.mixin.mek;

import mekanism.api.Upgrade;
import mekanism.common.MekanismLang;
import mekanism.common.config.MekanismConfig;
import mekanism.common.tile.interfaces.IUpgradeTile;
import mekanism.common.util.UpgradeUtils;
import com.sorrowmist.useless.utils.MekUtils;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

/**
 * Mixin for UpgradeUtils to modify upgrade information display
 */
@Mixin(value = UpgradeUtils.class, remap = false)
public class UpgradeUtilsMixin {

    @Inject(method = "getExpScaledInfo", at = @At("HEAD"), cancellable = true)
    private static void onGetExpScaledInfo(IUpgradeTile tile, Upgrade upgrade, CallbackInfoReturnable<List<Component>> cir) {
        List<Component> ret = new ArrayList<>();
        if (tile.supportsUpgrades() && upgrade.getMax() > 1) {
            ret.add(MekanismLang.UPGRADES_EFFECT.translate(MekUtils.time(tile)));
        }
        cir.setReturnValue(ret);
    }

    @Inject(method = "getMultScaledInfo", at = @At("HEAD"), cancellable = true)
    private static void onGetMultScaledInfo(IUpgradeTile tile, Upgrade upgrade, CallbackInfoReturnable<List<Component>> cir) {
        List<Component> ret = new ArrayList<>();
        if (tile.supportsUpgrades() && upgrade.getMax() > 1) {
            double effect = upgrade == Upgrade.ENERGY ? MekUtils.capacity(tile) :
                    (upgrade == Upgrade.SPEED ? MekUtils.time(tile) :
                            Math.pow(MekanismConfig.general.maxUpgradeMultiplier.get(),
                                    (double) tile.getComponent().getUpgrades(upgrade) / (double) upgrade.getMax()));
            ret.add(MekanismLang.UPGRADES_EFFECT.translate(MekUtils.exponential(effect)));
        }
        cir.setReturnValue(ret);
    }
}

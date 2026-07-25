package com.sorrowmist.useless.utils;

import mekanism.api.math.MathUtils;

/**
 * Math utilities complementing Mekanism's MathUtils for 1.20.1 compatibility.
 */
public class MathUtils {

    private MathUtils() {
        // 私有构造函数，防止被实例化
    }

    /**
     * 结合了 Math.ceil 与 Mekanism 原生的 MathUtils.clampToLong。
     * 先进行向上取整，再截断限制在 Long.MAX_VALUE 以内，防止数值膨胀导致溢出变成负数。
     *
     * @param d 需要取整并限制范围的 double 值
     * @return 向上取整且不超过 Long.MAX_VALUE 的 long 值
     */
    public static long ceilToLong(double d) {
        return MathUtils.clampToLong(Math.ceil(d));
    }
}
